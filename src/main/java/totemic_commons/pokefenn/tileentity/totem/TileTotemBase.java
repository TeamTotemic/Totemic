package totemic_commons.pokefenn.tileentity.totem;

import java.util.Random;

import gnu.trove.iterator.TIntIntIterator;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.api.ceremony.CeremonyRegistry;
import totemic_commons.pokefenn.api.ceremony.ICeremonyEffect;
import totemic_commons.pokefenn.api.ceremony.TimeStateEnum;
import totemic_commons.pokefenn.api.music.IMusicAcceptor;
import totemic_commons.pokefenn.api.music.MusicHandler;
import totemic_commons.pokefenn.api.totem.TotemRegistry;
import totemic_commons.pokefenn.lib.WoodVariant;
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
    public int totalCeremonyMelody;
    public boolean isMusicSelecting;
    public int ceremonyStartupTimer;
    public boolean isCeremony;
    public int continueTimer;
    public int musicForTotemEffect;
    public static int maximumMusic = 128;
    public TIntIntMap repetitionBonus;
    public boolean isDoingEndingEffect;
    public String bindedPlayer;
    public int[] totemIds;
    public int totemWoodBonus;
    public int[] musicPlayed;

    public TileTotemBase()
    {
        rangeUpgrades = 0;
        musicCeremony = new int[MusicHandler.musicHandler.size()];
        tier = 1;
        efficiencyFromCeremony = 0;
        isDoingEffect = false;
        currentCeremony = 0;
        ceremonyEffectTimer = 0;
        dancingEfficiency = 0;
        musicSelector = new int[5];
        isDoingStartup = false;
        tryingCeremonyID = 0;
        totalCeremonyMelody = 0;
        isMusicSelecting = true;
        ceremonyStartupTimer = 0;
        isCeremony = false;
        continueTimer = 0;
        musicForTotemEffect = 0;
        totemPoleSize = 0;
        repetitionBonus = new TIntIntHashMap();
        for(TotemRegistry totem : TotemRegistry.getTotemList())
            repetitionBonus.put(totem.getTotemId(), 0);
        musicPlayed = new int[MusicHandler.musicHandler.size()];
        isDoingEndingEffect = false;
        bindedPlayer = "";
        totemIds = new int[5];
        totemWoodBonus = 0;
    }

    @Override
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
                if(!isCeremony)
                    spawnParticles();
                else
                    spawnParticlesCeremony();
            }

            if(worldObj.getWorldTime() % (20L * 30) == 0)
            {
                for(int i = 0; i < musicPlayed.length; i++)
                {
                    musicPlayed[i] = 0;
                }
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
                    TotemRegistry totem = TotemRegistry.fromId(totemIds[i]);
                    totem.getEffect().effect(this, totemPoleSize, totem, getRanges(totem)[0], getRanges(totem)[1], musicForTotemEffect, totemWoodBonus, repetitionBonus.get(totemIds[i]));
                }
            }
        }
    }

    public void resetRepetition()
    {
        TIntIntIterator it = repetitionBonus.iterator();
        while(it.hasNext())
        {
            it.advance();
            it.setValue(0);
        }

        for(int totemId : totemIds)
        {
            if(totemId != 0)
            {
                repetitionBonus.increment(totemId);
            }
        }
    }

    public void spawnParticles()
    {
        if(musicForTotemEffect != 0)
        {
            for(int i = 0; i < musicForTotemEffect / 16; i++)
            {
                Random random = new Random();
                int plusminus1 = random.nextBoolean() ? 1 : -1;
                int plusminus2 = random.nextBoolean() ? 1 : -1;
                ((WorldServer)worldObj).func_147487_a("note", (double) xCoord + (random.nextFloat() * plusminus1), yCoord, (double) zCoord + (random.nextFloat() * plusminus2), 4, 0.0D, 0.5D, 0.0D, 0.0D);
            }
        }
    }

    public void spawnParticlesCeremony()
    {
        int j = 0;

        for(int aMusicCeremony : musicCeremony)
        {
            j += aMusicCeremony;
        }

        for(int i = 0; i < j / 16; i++)
        {
            Random random = new Random();
            int plusminus1 = random.nextBoolean() ? 1 : -1;
            int plusminus2 = random.nextBoolean() ? 1 : -1;
            ((WorldServer)worldObj).func_147487_a("note", (double) xCoord + (random.nextFloat() * plusminus1), yCoord, (double) zCoord + (random.nextFloat() * plusminus2), 4, 0.0D, 0.5D, 0.0D, 0.0D);
        }

    }

    public void getTotemWoodBonus()
    {
        int metadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        BiomeGenBase biomeGenBase = worldObj.getBiomeGenForCoords(xCoord, zCoord);

        totemWoodBonus = 0;

        switch(WoodVariant.values()[metadata])
        {
        case OAK:
            //oak effect
            totemWoodBonus += 2;
            break;

        case SPRUCE:
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
            break;

        case BIRCH:
            //birch effect
            break;

        case JUNGLE:
            //jungle effect
            if(biomeGenBase != null && biomeGenBase.getTempCategory() == BiomeGenBase.TempCategory.WARM && !biomeGenBase.getEnableSnow())
            {
                if(biomeGenBase.temperature > 1.0F)
                {
                    totemWoodBonus += 4;
                }
            }
            break;

        case ACACIA:
        case DARK_OAK:
        case CEDAR:
        default:
                break;
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
            if(canStartCeremony(CeremonyRegistry.fromId(tryingCeremonyID)))
            {
                currentCeremony = tryingCeremonyID;
                tryingCeremonyID = 0;
                isDoingStartup = false;
                isDoingEffect = true;
                markForUpdate();
            } else
                startupMain(CeremonyRegistry.fromId(tryingCeremonyID));

            ceremonyStartupTimer++;
        }

        if(currentCeremony != 0)
        {
            doCeremonyEffect(CeremonyRegistry.fromId(currentCeremony));
        }

        if(isMusicSelecting && worldObj.getWorldTime() % (20 * 60) == 0)
        {
            isCeremony = false;
            resetAfterCeremony(true);
        }
    }

    public void doCeremonyEffect(CeremonyRegistry cer)
    {
        if(isDoingEndingEffect && cer.getCeremonyActivation().getTimeState() == TimeStateEnum.ENDING_EFFECT)
            ceremonyEffectTimer++;
        if(ceremonyEffectTimer > cer.getCeremonyActivation().getMaximumTicksForEffect().getTime()) {
            resetAfterCeremony(true);
            return;
        }

        ICeremonyEffect effect = cer.getCeremonyEffect().getCeremonyEffect();

        if(effect != null)
        {
            if(cer.getCeremonyActivation().getTimeState() == TimeStateEnum.INSTANT)
            {
                effect.effect(this);
                resetAfterCeremony(true);
            }
            else
            {
                isDoingEndingEffect = true;
                if(canContinueCeremony(cer))
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
        for(CeremonyRegistry ceremonyRegistry : CeremonyRegistry.getCeremonyList())
        {
            if(musicSelector[0] != 0 && musicSelector[1] != 0 && musicSelector[2] != 0 && musicSelector[3] != 0)
            {
                int[] ids = ceremonyRegistry.getCeremonyEffect().getMusicIds();
                if(ids[0] + 1 == musicSelector[0] && ids[1] + 1 == musicSelector[1] && ids[2] + 1 == musicSelector[2] && ids[3] + 1 == musicSelector[3])
                {
                    particleAroundTotemUpwards("fireworksSpark");
                    tryingCeremonyID = ceremonyRegistry.getCeremonyID();
                    isDoingStartup = true;
                    isMusicSelecting = false;
                    resetSelector();
                    markForUpdate();
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
        ((WorldServer)worldObj).func_147487_a(particle, xCoord + 1D, yCoord, zCoord + 0.5D, 4, 0.0D, 0.5D, 0.0D, 0.0D);
        ((WorldServer)worldObj).func_147487_a(particle, xCoord - 1D, yCoord, zCoord + 0.5D, 4, 0.0D, 0.5D, 0.0D, 0.0D);
        ((WorldServer)worldObj).func_147487_a(particle, xCoord, yCoord, zCoord + 1D, 4, 0.0D, 0.5D, 0.0D, 0.0D);
        ((WorldServer)worldObj).func_147487_a(particle, xCoord, yCoord, zCoord - 1D, 4, 0.0D, 0.5D, 0.0D, 0.0D);
    }

    public void resetMelody()
    {
        totalCeremonyMelody = 0;
        int m = 0;

        for(int musicu : musicCeremony)
        {
            m += musicu;
        }
        totalCeremonyMelody = m;
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
        markForUpdate();
    }

    public void resetSelector()
    {
        for(int i = 0; i < 4; i++)
        {
            musicSelector[i] = 0;
        }
    }

    public boolean canContinueCeremony(CeremonyRegistry cer)
    {
        continueTimer++;
        if(continueTimer > 20 * 5)
        {
            continueTimer = 0;
            if(totalCeremonyMelody - cer.getCeremonyActivation().getMelodyPer5After() < 0)
                totalCeremonyMelody = 0;
            else
                totalCeremonyMelody -= cer.getCeremonyActivation().getMelodyPer5After();

            if(totalCeremonyMelody < cer.getCeremonyActivation().getMelodyPer5After())
            {
                resetAfterCeremony(true);
            }
        }

        return totalCeremonyMelody - cer.getCeremonyActivation().getMelodyPer5After() >= 0;
    }


    public boolean canStartCeremony(CeremonyRegistry trying)
    {
        if(trying.getCeremonyActivation().getDoesNeedItems())
        {
            for(Entity entity : EntityUtil.getEntitiesInRange(worldObj, xCoord, yCoord, zCoord, 6, 6))
            {
                if(entity instanceof EntityItem)
                {
                    EntityItem item = (EntityItem)entity;
                    if(item.getEntityItem().getItem() == trying.getCeremonyActivation().getItemStack().getItem()
                            && item.getEntityItem().getItemDamage() == trying.getCeremonyActivation().getItemStack().getItemDamage()
                            && item.getEntityItem().stackSize >= trying.getCeremonyActivation().getItemStack().stackSize)
                    {
                        item.setEntityItemStack(new ItemStack(item.getEntityItem().getItem(), item.getEntityItem().stackSize - trying.getCeremonyActivation().getItemStack().stackSize, item.getEntityItem().getItemDamage()));
                        break;
                    }
                }
            }
        }

        resetMelody();
        return totalCeremonyMelody >= (trying.getCeremonyActivation().getMusicNeeded() - (dancingEfficiency / 4));
    }

    public void startupMain(CeremonyRegistry trying)
    {
        if(ceremonyStartupTimer > trying.getCeremonyActivation().getMaximumStartupTime().getTime())
        {
            resetAfterCeremony(true);
        }

        if(worldObj.getWorldTime() % 5L == 0)
        {
            danceLikeAMonkey(trying);
        }
    }

    public void danceLikeAMonkey(CeremonyRegistry trying)
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
        totalCeremonyMelody = nbtTagCompound.getInteger("totalCeremonyMelody");
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
        nbtTagCompound.setInteger("totalCeremonyMelody", totalCeremonyMelody);
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
