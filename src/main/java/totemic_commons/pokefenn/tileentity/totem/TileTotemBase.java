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
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.api.ceremony.ICeremonyEffect;
import totemic_commons.pokefenn.api.ceremony.TimeStateEnum;
import totemic_commons.pokefenn.api.music.IMusicAcceptor;
import totemic_commons.pokefenn.api.music.MusicEnum;
import totemic_commons.pokefenn.api.recipe.CeremonyRegistry;
import totemic_commons.pokefenn.api.recipe.TotemRegistry;
import totemic_commons.pokefenn.block.totem.BlockTotemPole;
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
    public int maxEssence;
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
    public boolean isCeremonyAwakening;
    public int musicForEffect;
    public static int maximumMusic = 128;
    public int[] repetitionBonus;
    public boolean isDoingEndingEffect;
    public String bindedPlayer;

    ItemStack[] totems;

    public TileTotemBase()
    {
        totems = new ItemStack[10];
        maxEssence = 500;
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
        isCeremonyAwakening = false;
        musicForEffect = 0;
        totemPoleSize = 0;
        repetitionBonus = new int[TotemRegistry.getRecipes().size() + 1];
        isDoingEndingEffect = false;
        bindedPlayer = "";
    }

    public void updateEntity()
    {
        super.updateEntity();

        int currentInput = worldObj.getBlockPowerInput(xCoord, yCoord, zCoord);

        if(!worldObj.isRemote)
        {
            deprecateMelody();

            if(worldObj.getWorldTime() % 80L == 0)
            {
                totemPoleSize = setTotemPoleAmounts();
                scanArea();
            }

            if(isCeremony)
            {
                doCeremonyCode();
            }

            if(!(currentInput >= 1))
            {
                totemEffect();
            }
        }
    }

    public void totemEffect()
    {
        if(totemPoleSize > 0)
        {
            for(int i = 1; i <= totemPoleSize; i++)
            {
                if(totems[i] != null)
                {
                    //resetRepetition();
                    for(TotemRegistry totemRegistry : TotemRegistry.getRecipes())
                    {
                        if(totems[i] != null && totems[i].getItem() == totemRegistry.getTotem().getItem() && totems[i].getItemDamage() == totemRegistry.getTotem().getItemDamage())
                        {
                            totemRegistry.getEffect().effect(this, totemPoleSize, totemRegistry, getRanges(totemRegistry)[0], getRanges(totemRegistry)[1], musicForEffect);
                        }
                    }
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
        if(isDoingEffect && CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getCeremonyActivation().getTimeState() == TimeStateEnum.OVER_TIME)
            ceremonyEffectTimer++;
        if(ceremonyEffectTimer > CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getCeremonyActivation().getMaximumTicksForEffect().getTime())
            resetAfterCeremony(true);

        ICeremonyEffect effect = CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getCeremonyEffect().getCeremonyEffect();

        if(effect != null && !isDoingEffect && !isCeremonyAwakening)
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
        if(musicForEffect > 0)
        {
            if(worldObj.getWorldTime() % 47L == 0)
            {
                if(musicForEffect - 1 >= 0)
                    musicForEffect--;
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
        array[1] = totemRegistry.getVerticalHight();

        if(musicForEffect > 10 && musicForEffect < 32)
        {
            array[0] += 1;
            array[1] += 1;
        } else if(musicForEffect > 32 && musicForEffect < 64)
        {
            array[0] += 2;
            array[1] += 2;
        } else if(musicForEffect > 64 && musicForEffect < 96)
        {
            array[0] += 3;
            array[1] += 3;
        } else if(musicForEffect > 96 && musicForEffect < 115)
        {
            array[0] += 4;
            array[1] += 4;
        } else if(musicForEffect > 115)
        {
            array[0] += 6;
            array[1] += 6;
        }
        array[0] += totemPoleSize % 3;
        array[1] += totemPoleSize % 3;

        return array;
    }

    public void particleAroundTotemUpwards(String particle)
    {
        MinecraftServer.getServer().worldServerForDimension(worldObj.provider.dimensionId).func_147487_a(particle, (double) xCoord + 1, (double) yCoord, (double) zCoord + 0.5D, 16, 0.0D, 0.5D, 0.0D, 0.0D);
        MinecraftServer.getServer().worldServerForDimension(worldObj.provider.dimensionId).func_147487_a(particle, (double) xCoord - 1, (double) yCoord, (double) zCoord + 0.5D, 16, 0.0D, 0.5D, 0.0D, 0.0D);
        MinecraftServer.getServer().worldServerForDimension(worldObj.provider.dimensionId).func_147487_a(particle, (double) xCoord, (double) yCoord + 1, (double) zCoord + 0.5D, 16, 0.0D, 0.5D, 0.0D, 0.0D);
        MinecraftServer.getServer().worldServerForDimension(worldObj.provider.dimensionId).func_147487_a(particle, (double) xCoord, (double) yCoord - 1, (double) zCoord + 0.5D, 16, 0.0D, 0.5D, 0.0D, 0.0D);

    }

    public int getMusicFromCeremony()
    {
        int j = 0;

        for(int i = 0; i < musicCeremony.length; i++)
        {
            j += musicCeremony[i];
        }
        return j;
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
        isCeremonyAwakening = false;
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
        workOutEfficiency();
        return totalMelody >= CeremonyRegistry.ceremonyRegistry.get(tryingCeremonyID - 1).getCeremonyActivation().getMusicNeeded() - (dancingEfficiency % 50);
    }

    public void workOutEfficiency()
    {
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

            if((int) player.posX != (int) player.prevPosX && (int) player.posY != (int) player.prevPosY)
            {
                if(player.getCurrentArmor(1) != null && player.getCurrentArmor(1).getItem() == ModItems.totemArmourLeg)
                    dancingEfficiency += 2;

                dancingEfficiency++;
            }
        }
    }

    protected void scanArea()
    {
        for(int i = 1; i <= totemPoleSize; i++)
        {
            if(totemPoleSize <= 6)
            {
                if(getTotemPoleItemStack(i) != null)
                {
                    totems[i] = getTotemPoleItemStack(i);

                } else
                    totems[i] = null;
            }
        }
    }

    public boolean canUpdate()
    {
        return true;
    }

    protected ItemStack getTotemPoleItemStack(int par1)
    {
        TileEntity tileEntity = this.worldObj.getTileEntity(this.xCoord, this.yCoord + par1, this.zCoord);

        return tileEntity instanceof TileTotemPole ? ((TileTotemPole) tileEntity).getStackInSlot(TileTotemPole.SLOT_ONE) : null;
    }

    protected int setTotemPoleAmounts()
    {
        Block block1 = worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord);
        Block block2 = worldObj.getBlock(this.xCoord, this.yCoord + 2, this.zCoord);
        Block block3 = worldObj.getBlock(this.xCoord, this.yCoord + 3, this.zCoord);
        Block block4 = worldObj.getBlock(this.xCoord, this.yCoord + 4, this.zCoord);
        Block block5 = worldObj.getBlock(this.xCoord, this.yCoord + 5, this.zCoord);
        Block block6 = worldObj.getBlock(this.xCoord, this.yCoord + 6, this.zCoord);
        Block block7 = worldObj.getBlock(this.xCoord, this.yCoord + 7, this.zCoord);
        Block block8 = worldObj.getBlock(this.xCoord, this.yCoord + 8, this.zCoord);
        Block block9 = worldObj.getBlock(this.xCoord, this.yCoord + 9, this.zCoord);
        Block block10 = worldObj.getBlock(this.xCoord, this.yCoord + 10, this.zCoord);


        if(block1 instanceof BlockTotemPole && block2 != ModBlocks.totemPole)
        {
            return 1;
        } else if(block1 instanceof BlockTotemPole && block2 instanceof BlockTotemPole && block3 != ModBlocks.totemPole)
        {
            return 2;
        } else if(block1 instanceof BlockTotemPole && block2 instanceof BlockTotemPole && block3 instanceof BlockTotemPole && block4 != ModBlocks.totemPole)
        {
            return 3;
        } else if(block1 instanceof BlockTotemPole && block2 instanceof BlockTotemPole && block3 instanceof BlockTotemPole && block4 instanceof BlockTotemPole && block5 != ModBlocks.totemPole)
        {
            return 4;
        } else if(block1 instanceof BlockTotemPole && block2 instanceof BlockTotemPole && block3 instanceof BlockTotemPole && block4 instanceof BlockTotemPole && block5 instanceof BlockTotemPole && block6 != ModBlocks.totemPole)
        {
            return 5;
        } else if(block1 instanceof BlockTotemPole && block2 instanceof BlockTotemPole && block3 instanceof BlockTotemPole && block4 instanceof BlockTotemPole && block5 instanceof BlockTotemPole && block6 instanceof BlockTotemPole && block7 != ModBlocks.totemPole)
        {
            return 6;
        } /*else if(block1 instanceof BlockTotemPole && block2 instanceof BlockTotemPole && block3 instanceof BlockTotemPole && block4 instanceof BlockTotemPole && block5 instanceof BlockTotemPole && block6 instanceof BlockTotemPole && block7 instanceof BlockTotemPole && block8 != ModBlocks.totemPole)
        {
            return 7;
        } else if(block1 instanceof BlockTotemPole && block2 instanceof BlockTotemPole && block3 instanceof BlockTotemPole && block4 instanceof BlockTotemPole && block5 instanceof BlockTotemPole && block6 instanceof BlockTotemPole && block7 instanceof BlockTotemPole && block8 instanceof BlockTotemPole && block9 != ModBlocks.totemPole)
        {
            return 8;
        } else if(block1 instanceof BlockTotemPole && block2 instanceof BlockTotemPole && block3 instanceof BlockTotemPole && block4 instanceof BlockTotemPole && block5 instanceof BlockTotemPole && block6 instanceof BlockTotemPole && block7 instanceof BlockTotemPole && block8 instanceof BlockTotemPole && block9 instanceof BlockTotemPole && block10 != ModBlocks.totemPole)
        {
            return 9;
        } else if(block1 instanceof BlockTotemPole && block2 instanceof BlockTotemPole && block3 instanceof BlockTotemPole && block4 instanceof BlockTotemPole && block5 instanceof BlockTotemPole && block6 instanceof BlockTotemPole && block7 instanceof BlockTotemPole && block8 instanceof BlockTotemPole && block9 instanceof BlockTotemPole && block10 instanceof BlockTotemPole)
        {
            return 10;
        }
        */

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
        isCeremonyAwakening = nbtTagCompound.getBoolean("isCeremonyAwakening");
        musicForEffect = nbtTagCompound.getInteger("musicForEffect");
        isDoingEndingEffect = nbtTagCompound.getBoolean("isDoingEndingEffect");
        bindedPlayer = nbtTagCompound.getString("bindedPlayer");
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
        nbtTagCompound.setBoolean("isCeremonyAwakening", isCeremonyAwakening);
        nbtTagCompound.setInteger("musicForEffect", musicForEffect);
        nbtTagCompound.setBoolean("isDoingEndingEffect", isDoingEffect);
        nbtTagCompound.setString("bindedPlayer", bindedPlayer);
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
        return musicForEffect;
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
