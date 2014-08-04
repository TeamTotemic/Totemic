package totemic_commons.pokefenn.tileentity.totem;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.biome.BiomeGenBase;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.api.ceremony.ICeremonyEffect;
import totemic_commons.pokefenn.api.ceremony.TimeStateEnum;
import totemic_commons.pokefenn.api.music.IMusicAcceptor;
import totemic_commons.pokefenn.api.music.MusicEnum;
import totemic_commons.pokefenn.api.recipe.CeremonyRegistry;
import totemic_commons.pokefenn.api.recipe.TotemRegistry;
import totemic_commons.pokefenn.tileentity.TileTotemic;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 29/01/14
 * Time: 20:22
 */
public class TileTotemBase extends TileTotemic implements IMusicAcceptor
{
    public int totemPoleSize;
    public int rangeUpgrades;
    public int tier;
    public int efficiencyFromCeremony;
    public boolean isDoingEffect;
    public int currentCeremony;
    public int ceremonyEffectTimer;
    public int dancingEfficiency;
    public int[] musicCeremony;
    public int[] musicSelector;
    public boolean isDoingStartup;
    public int tryingCeremonyID;
    public int totalMelody;
    public boolean isMusicSelecting;
    public int ceremonyStartupTimer;
    public boolean isCeremony;
    public int continueTimer;
    public int musicForTotemEffect;
    public static int maximumMusic = 128;
    public int[] repetitionBonus;
    public boolean isDoingEndingEffect;
    public String bindedPlayer;
    public int[] totemIds;
    public int totemWoodBonus;

    public TileTotemBase()
    {
        rangeUpgrades = 0;
        musicCeremony = new int[MusicEnum.values().length];
        tier = 1;
        efficiencyFromCeremony = 0;
        isDoingEffect = false;
        currentCeremony = 0;
        ceremonyEffectTimer = 0;
        dancingEfficiency = 0;
        musicSelector = new int[5];
        isDoingStartup = false;
        tryingCeremonyID = 0;
        totalMelody = 0;
        isMusicSelecting = true;
        ceremonyStartupTimer = 0;
        isCeremony = false;
        continueTimer = 0;
        musicForTotemEffect = 0;
        totemPoleSize = 0;
        repetitionBonus = new int[TotemRegistry.getRecipes().size()];
        isDoingEndingEffect = false;
        bindedPlayer = "";
        totemIds = new int[5];
        totemWoodBonus = 0;
    }

    public void updateEntity()
    {
        super.updateEntity();

        //int currentInput = worldObj.getBlockPowerInput(xCoord, yCoord, zCoord);

        if(!worldObj.isRemote)
        {
            deprecateMelody();
            if(worldObj.getWorldTime() % 40L == 0)
            {
                getTotemWoodBonus();
                resetRepetition();
            }

            if(worldObj.getWorldTime() % 80L == 0)
            {
                totemPoleSize = setTotemPoleAmounts();
                scanArea();
            }

            if(isCeremony)
            {
                doCeremonyCode();
            }

            if(!isCeremony/* && currentInput == 0*/)
            {
                totemEffect();
            }
        }
    }

    public void totemEffect()
    {
        if(totemPoleSize > 0)
        {
            for(int i = 0; i < totemPoleSize; i++)
            {
                if(totemIds[i] != 0)
                {
                    TotemRegistry totemRegistry = TotemRegistry.getRecipes().get(totemIds[i]);
                    totemRegistry.getEffect().effect(this, totemPoleSize, totemRegistry, getRanges(totemRegistry)[0], getRanges(totemRegistry)[1], musicForTotemEffect, totemWoodBonus, repetitionBonus);
                }
            }
        }
    }

    public void resetRepetition()
    {
        for(int i = 0; i < repetitionBonus.length; i++)
        {
            repetitionBonus[i] = 0;
        }

        for(int totemId : totemIds)
        {
            repetitionBonus[totemId]++;
        }
    }

    public void getTotemWoodBonus()
    {
        int metadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        BiomeGenBase biomeGenBase = worldObj.getBiomeGenForCoords(xCoord, zCoord);

        totemWoodBonus = 0;

        if(metadata == 0)
        {
            //oak effect
            totemWoodBonus += 2;
        } else if(metadata == 1)
        {
            //birch effect
        } else if(metadata == 2)
        {
            //TODO better numbers, just temp for now.
            //spruce effect
            if(biomeGenBase != null && biomeGenBase.getTempCategory() == BiomeGenBase.TempCategory.COLD)
            {
                if(biomeGenBase.getEnableSnow())
                {
                    totemWoodBonus += 3;
                }
                if(biomeGenBase.temperature < 0.4F)
                {
                    totemWoodBonus += 2;
                }
            }
        } else if(metadata == 3)
        {
            //jungle effect
            if(biomeGenBase != null && biomeGenBase.getTempCategory() == BiomeGenBase.TempCategory.WARM && !biomeGenBase.getEnableSnow())
            {
                if(biomeGenBase.temperature > 1.0F)
                {
                    totemWoodBonus += 4;
                }
            }
        } else if(metadata == 4)
        {
            //cedar effect
        }
    }

    public void doCeremonyCode()
    {
        if(!isDoingStartup)
        {
            selectorHandling();
        }

        if(worldObj.getWorldTime() % 30L == 0)
        {
            resetMelody();
        }

        if(isDoingStartup && tryingCeremonyID != 0)
        {
            if(canStartCeremony())
            {
                currentCeremony = tryingCeremonyID;
                tryingCeremonyID = 0;
                isDoingStartup = false;
            }

            ceremonyStartupTimer++;
            startupMain();
        }

        if(currentCeremony <= CeremonyRegistry.ceremonyRegistry.size() && currentCeremony != 0)
        {
            doCeremonyEffect();
        }

        if(isMusicSelecting && worldObj.getWorldTime() % (20 * 60) == 0)
        {
            isCeremony = false;
            resetAfterCeremony(true);
        }
    }

    public void doCeremonyEffect()
    {
        if(isDoingEndingEffect && CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getCeremonyActivation().getTimeState() == TimeStateEnum.ENDING_EFFECT)
            ceremonyEffectTimer++;
        if(ceremonyEffectTimer > CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getCeremonyActivation().getMaximumTicksForEffect().getTime())
            resetAfterCeremony(true);

        ICeremonyEffect effect = CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getCeremonyEffect().getCeremonyEffect();

        if(effect != null)
        {
            if(CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getCeremonyActivation().getTimeState() == TimeStateEnum.INSTANT)
            {
                effect.effect(this);
                resetAfterCeremony(true);
            } else
            {
                isDoingEndingEffect = true;
                if(canContinueCeremony())
                {
                    effect.effect(this);
                } else
                {
                    resetAfterCeremony(true);
                }
            }
        }
    }

    public void selectorHandling()
    {
        for(CeremonyRegistry ceremonyRegistry : CeremonyRegistry.ceremonyRegistry)
        {
            if(musicSelector[0] != 0 && musicSelector[1] != 0 && musicSelector[2] != 0 && musicSelector[3] != 0)
            {
                MusicEnum[] enums = ceremonyRegistry.getCeremonyEffect().getMusicEnums();
                if(enums[0].ordinal() + 1 == musicSelector[0] && enums[1].ordinal() + 1 == musicSelector[1] && enums[2].ordinal() + 1 == musicSelector[2] && enums[3].ordinal() + 1 == musicSelector[3])
                {
                    particleAroundTotemUpwards("fireworksSpark");
                    tryingCeremonyID = ceremonyRegistry.getCeremonyID();
                    isDoingStartup = true;
                    isMusicSelecting = false;
                    resetSelector();
                }
            }
        }
    }

    public void deprecateMelody()
    {
        if(musicForTotemEffect > 0)
        {
            if(worldObj.getWorldTime() % 47L == 0)
            {
                if(musicForTotemEffect - 1 != 0)
                    musicForTotemEffect--;
            }
        }
    }

    public static String getMusicName(int i)
    {
        if(i < 10)
        {
            return StatCollector.translateToLocal("totemic.melodyName.incrediblyLow");
        } else if(i > 10 && i < 32)
        {
            return StatCollector.translateToLocal("totemic.melodyName.weak");
        } else if(i > 32 && i < 64)
        {
            return StatCollector.translateToLocal("totemic.melodyName.low");
        } else if(i > 64 && i < 96)
        {
            return StatCollector.translateToLocal("totemic.melodyName.sufficient");
        } else if(i > 96 && i < 115)
        {
            return StatCollector.translateToLocal("totemic.melodyName.high");
        } else if(i > 115)
        {
            return StatCollector.translateToLocal("totemic.melodyName.maximum");
        }
        return "lolbroked";
    }

    public int[] getRanges(TotemRegistry totemRegistry)
    {
        int[] array = new int[2];

        array[0] = totemRegistry.getHorizontal();
        array[1] = totemRegistry.getVerticalHeight();

        if(musicForTotemEffect > 10 && musicForTotemEffect < 32)
        {
            array[0] += 1;
            array[1] += 1;
        } else if(musicForTotemEffect > 32 && musicForTotemEffect < 64)
        {
            array[0] += 2;
            array[1] += 2;
        } else if(musicForTotemEffect > 64 && musicForTotemEffect < 96)
        {
            array[0] += 3;
            array[1] += 3;
        } else if(musicForTotemEffect > 96 && musicForTotemEffect < 115)
        {
            array[0] += 4;
            array[1] += 4;
        } else if(musicForTotemEffect > 115)
        {
            array[0] += 6;
            array[1] += 6;
        }

        array[0] += totemWoodBonus / 5;
        array[0] += totemWoodBonus / 5;

        if(totemPoleSize == 5)
        {
            array[0] += 2;
            array[1] += 2;
        }

        array[0] += totemPoleSize / 8;
        array[1] += totemPoleSize / 8;

        return array;
    }

    public void particleAroundTotemUpwards(String particle)
    {
        MinecraftServer.getServer().worldServerForDimension(worldObj.provider.dimensionId).func_147487_a(particle, (double) xCoord + 1D, (double) yCoord, (double) zCoord + 0.5D, 4, 0.0D, 0.5D, 0.0D, 0.0D);
        MinecraftServer.getServer().worldServerForDimension(worldObj.provider.dimensionId).func_147487_a(particle, (double) xCoord - 1D, (double) yCoord, (double) zCoord + 0.5D, 4, 0.0D, 0.5D, 0.0D, 0.0D);
        MinecraftServer.getServer().worldServerForDimension(worldObj.provider.dimensionId).func_147487_a(particle, (double) xCoord, (double) yCoord, (double) zCoord + 1D, 4, 0.0D, 0.5D, 0.0D, 0.0D);
        MinecraftServer.getServer().worldServerForDimension(worldObj.provider.dimensionId).func_147487_a(particle, (double) xCoord, (double) yCoord, (double) zCoord - 1D, 4, 0.0D, 0.5D, 0.0D, 0.0D);
    }

    public void resetMelody()
    {
        totalMelody = 0;
        int m = 0;

        for(int musicu : musicCeremony)
        {
            m += musicu;
        }
        totalMelody = m;
    }

    public void resetAfterCeremony(boolean doResetMusicSelector)
    {
        currentCeremony = 0;
        tryingCeremonyID = 0;
        isMusicSelecting = true;
        isDoingEffect = false;
        ceremonyStartupTimer = 0;
        isDoingStartup = false;
        isDoingEndingEffect = false;

        dancingEfficiency = 0;

        for(int i = 0; i < musicCeremony.length; i++)
        {
            musicCeremony[i] = 0;
        }
        if(doResetMusicSelector)
            for(int i = 0; i < 4; i++)
            {
                musicSelector[i] = 0;
            }
    }

    public void resetSelector()
    {
        for(int i = 0; i < 4; i++)
        {
            musicSelector[i] = 0;
        }
    }

    public boolean canContinueCeremony()
    {
        continueTimer++;
        if(continueTimer > 20 * 5)
        {
            continueTimer = 0;
            if(totalMelody - CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getCeremonyActivation().getMelodyPer5After() < 0)
                totalMelody = 0;
            else
                totalMelody -= CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getCeremonyActivation().getMelodyPer5After();

            if(totalMelody < CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getCeremonyActivation().getMelodyPer5After())
            {
                resetAfterCeremony(true);
            }
        }

        return totalMelody - CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getCeremonyActivation().getMelodyPer5After() >= 0;
    }


    public boolean canStartCeremony()
    {
        if(CeremonyRegistry.ceremonyRegistry.get(tryingCeremonyID - 1).getCeremonyActivation().getDoesNeedItems())
        {
            if(EntityUtil.getEntitiesInRange(worldObj, xCoord, yCoord, zCoord, 6, 6) != null)
            {
                for(Entity entity : EntityUtil.getEntitiesInRange(worldObj, xCoord, yCoord, zCoord, 6, 6))
                {
                    if(entity instanceof EntityItem)
                    {
                        if(((EntityItem) entity).getEntityItem().getItem() == CeremonyRegistry.ceremonyRegistry.get(tryingCeremonyID - 1).getCeremonyActivation().getItemStack().getItem() && ((EntityItem) entity).getEntityItem().getItemDamage() == CeremonyRegistry.ceremonyRegistry.get(tryingCeremonyID - 1).getCeremonyActivation().getItemStack().getItemDamage() && ((EntityItem) entity).getEntityItem().stackSize >= CeremonyRegistry.ceremonyRegistry.get(tryingCeremonyID - 1).getCeremonyActivation().getItemStack().stackSize)
                        {
                            ((EntityItem) entity).setEntityItemStack(new ItemStack(((EntityItem) entity).getEntityItem().getItem(), ((EntityItem) entity).getEntityItem().stackSize - CeremonyRegistry.ceremonyRegistry.get(tryingCeremonyID - 1).getCeremonyActivation().getItemStack().stackSize, ((EntityItem) entity).getEntityItem().getItemDamage()));
                            break;
                        }
                    }
                }
            }
        }

        resetMelody();
        return totalMelody >= (CeremonyRegistry.ceremonyRegistry.get(tryingCeremonyID - 1).getCeremonyActivation().getMusicNeeded() - (dancingEfficiency / 4));
    }

    public void startupMain()
    {
        if(ceremonyStartupTimer > CeremonyRegistry.ceremonyRegistry.get(tryingCeremonyID - 1).getCeremonyActivation().getMaximumStartupTime().getTime())
        {
            resetAfterCeremony(true);
        }

        if(worldObj.getWorldTime() % 5L == 0)
        {
            danceLikeAMonkey();
        }
    }

    public void danceLikeAMonkey()
    {
        //TODO
        if(worldObj.getClosestPlayer(xCoord, yCoord, zCoord, 8) != null)
        {
            EntityPlayer player = worldObj.getClosestPlayer(xCoord, yCoord, zCoord, 8);

            if(dancingEfficiency < 50)
                if((int) player.posX != (int) player.prevPosX && (int) player.posY != (int) player.prevPosY)
                {
                    dancingEfficiency++;
                }
        }
    }

    protected void scanArea()
    {
        for(int i = 0; i < totemPoleSize; i++)
        {
            if(getTotemId(i) != 0)
            {
                totemIds[i] = getTotemId(i);

            } else
                totemIds[i] = 0;
        }
    }

    @Override
    public boolean canUpdate()
    {
        return true;
    }

    protected int getTotemId(int yOffset)
    {
        TileEntity tileEntity = this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1 + yOffset, this.zCoord);

        return tileEntity instanceof TileTotemPole ? (((TileTotemPole) tileEntity).getTotemId()) : 0;
    }

    protected int setTotemPoleAmounts()
    {
        Block block1 = worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord);
        Block block2 = worldObj.getBlock(this.xCoord, this.yCoord + 2, this.zCoord);
        Block block3 = worldObj.getBlock(this.xCoord, this.yCoord + 3, this.zCoord);
        Block block4 = worldObj.getBlock(this.xCoord, this.yCoord + 4, this.zCoord);
        Block block5 = worldObj.getBlock(this.xCoord, this.yCoord + 5, this.zCoord);

        if(block1 == ModBlocks.totemPole && block2 != ModBlocks.totemPole)
        {
            return 1;
        } else if(block1 == ModBlocks.totemPole && block2 == ModBlocks.totemPole && block3 != ModBlocks.totemPole)
        {
            return 2;
        } else if(block1 == ModBlocks.totemPole && block2 == ModBlocks.totemPole && block3 == ModBlocks.totemPole && block4 != ModBlocks.totemPole)
        {
            return 3;
        } else if(block1 == ModBlocks.totemPole && block2 == ModBlocks.totemPole && block3 == ModBlocks.totemPole && block4 == ModBlocks.totemPole && block5 != ModBlocks.totemPole)
        {
            return 4;
        } else if(block1 == ModBlocks.totemPole && block2 == ModBlocks.totemPole && block3 == ModBlocks.totemPole && block4 == ModBlocks.totemPole && block5 == ModBlocks.totemPole)
        {
            return 5;
        }

        return 0;
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.func_148857_g());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        totemPoleSize = nbtTagCompound.getInteger("totemPoleSize");
        tier = nbtTagCompound.getInteger("tier");
        efficiencyFromCeremony = nbtTagCompound.getInteger("efficiencyFromCeremony");
        isDoingEffect = nbtTagCompound.getBoolean("isDoingEffect");
        currentCeremony = nbtTagCompound.getInteger("currentCeremony");
        dancingEfficiency = nbtTagCompound.getInteger("dancingEfficiency");
        ceremonyEffectTimer = nbtTagCompound.getInteger("ceremonyEffectTimer");
        musicCeremony = nbtTagCompound.getIntArray("musicCeremony");
        musicSelector = nbtTagCompound.getIntArray("musicSelector");
        isDoingStartup = nbtTagCompound.getBoolean("isDoingStartup");
        tryingCeremonyID = nbtTagCompound.getInteger("tryingCeremonyID");
        totalMelody = nbtTagCompound.getInteger("totalMelody");
        isMusicSelecting = nbtTagCompound.getBoolean("isMusicSelecting");
        ceremonyStartupTimer = nbtTagCompound.getInteger("ceremonyStartupTimer");
        isCeremony = nbtTagCompound.getBoolean("isCeremony");
        continueTimer = nbtTagCompound.getInteger("continueTimer");
        musicForTotemEffect = nbtTagCompound.getInteger("musicForTotemEffect");
        isDoingEndingEffect = nbtTagCompound.getBoolean("isDoingEndingEffect");
        bindedPlayer = nbtTagCompound.getString("bindedPlayer");
        totemIds = nbtTagCompound.getIntArray("totemIds");
        totemWoodBonus = nbtTagCompound.getInteger("totemWoodBonus");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setIntArray("musicCeremony", musicCeremony);
        nbtTagCompound.setInteger("totemPoleSize", totemPoleSize);
        nbtTagCompound.setInteger("tier", tier);
        nbtTagCompound.setInteger("efficiencyFromCeremony", efficiencyFromCeremony);
        nbtTagCompound.setBoolean("isDoingEffect", isDoingEffect);
        nbtTagCompound.setInteger("currentCeremony", currentCeremony);
        nbtTagCompound.setInteger("ceremonyEffectTimer", ceremonyEffectTimer);
        nbtTagCompound.setInteger("dancingEfficiency", dancingEfficiency);
        nbtTagCompound.setIntArray("musicSelector", musicSelector);
        nbtTagCompound.setBoolean("isDoingStartup", isDoingStartup);
        nbtTagCompound.setInteger("tryingCeremonyID", tryingCeremonyID);
        nbtTagCompound.setInteger("totalMelody", totalMelody);
        nbtTagCompound.setBoolean("isMusicSelecting", isMusicSelecting);
        nbtTagCompound.setInteger("ceremonyStartupTimer", ceremonyStartupTimer);
        nbtTagCompound.setBoolean("isCeremony", isCeremony);
        nbtTagCompound.setInteger("continueTimer", continueTimer);
        nbtTagCompound.setInteger("musicForTotemEffect", musicForTotemEffect);
        nbtTagCompound.setBoolean("isDoingEndingEffect", isDoingEndingEffect);
        nbtTagCompound.setString("bindedPlayer", bindedPlayer);
        nbtTagCompound.setIntArray("totemIds", totemIds);
        nbtTagCompound.setInteger("totemWoodBonus", totemWoodBonus);
    }

    @Override
    public int[] getMusicArray()
    {
        return musicCeremony;
    }

    @Override
    public int[] getMusicSelector()
    {
        return musicSelector;
    }

    @Override
    public boolean doesMusicSelect()
    {
        return true;
    }

    @Override
    public boolean isMusicSelecting()
    {
        return isMusicSelecting;
    }

    @Override
    public int getMusicForEffect()
    {
        return musicForTotemEffect;
    }

    @Override
    public boolean getIsCeremony()
    {
        return isCeremony;
    }

    @Override
    public boolean isDoingEndingEffect()
    {
        //TODO
        return isDoingEndingEffect;
    }
}
