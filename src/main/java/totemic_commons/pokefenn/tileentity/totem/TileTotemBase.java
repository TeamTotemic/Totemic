package totemic_commons.pokefenn.tileentity.totem;

import java.util.Arrays;
import java.util.Random;
import java.util.Set;

import gnu.trove.iterator.TObjectIntIterator;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.Constants;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.TotemEffect;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.ceremony.CeremonyTime;
import totemic_commons.pokefenn.api.music.MusicAcceptor;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.lib.WoodVariant;
import totemic_commons.pokefenn.tileentity.TileTotemic;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 29/01/14
 * Time: 20:22
 */
public class TileTotemBase extends TileTotemic implements MusicAcceptor
{
    public static final int MAX_HEIGHT = 5;
    public static final int MAX_EFFECT_MUSIC = 128;

    public int totemPoleSize = 0;
    public int tier = 1;
    public int ceremonyEffectTimer = 0;
    public int dancingEfficiency = 0;
    public final TObjectIntMap<MusicInstrument> ceremonyMusic = new TObjectIntHashMap<>(Totemic.api.getInstruments().size(), 0.75f);
    public final MusicInstrument[] musicSelector = new MusicInstrument[Ceremony.NUM_SELECTORS];
    public Ceremony startupCeremony = null;
    public Ceremony currentCeremony = null;
    public int totalCeremonyMelody = 0;
    public boolean isMusicSelecting = true;
    public int ceremonyStartupTimer = 0;
    public boolean isCeremony = false;
    public int continueTimer = 0;
    public int musicForTotemEffect = 0;
    public final TObjectIntMap<TotemEffect> repetitionBonus = new TObjectIntHashMap<>(Totemic.api.getTotems().size(), 0.75f);
    public boolean isDoingEndingEffect = false;
    public TotemEffect[] effects = new TotemEffect[MAX_HEIGHT];
    public int totemWoodBonus = 0;
    public final TObjectIntMap<MusicInstrument> timesPlayed = new TObjectIntHashMap<>(Totemic.api.getInstruments().size(), 0.75f);

    public TileTotemBase()
    {

    }

    @Override
    public void updateEntity()
    {
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

            if(!isCeremony)
                if(worldObj.getWorldTime() % (20L * 30) == 0)
                {
                    timesPlayed.clear();
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
                if(effects[i] != null)
                {
                    int[] ranges = getRanges(effects[i]);
                    effects[i].effect(this, totemPoleSize, ranges[0], ranges[1], musicForTotemEffect, totemWoodBonus, repetitionBonus.get(effects[i]));
                }
            }
        }
    }

    public void resetRepetition()
    {
        repetitionBonus.clear();
        for(TotemEffect totem : effects)
        {
            if(totem != null)
            {
                repetitionBonus.adjustOrPutValue(totem, 1, 1);
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
        for(int i = 0; i < totalCeremonyMelody / 16; i++)
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
        if(!isDoingStartup())
        {
            selectorHandling();
        }

        if(worldObj.getWorldTime() % 30L == 0)
        {
            resetMelody();
        }

        if(isDoingStartup())
        {
            if(canStartCeremony(startupCeremony))
            {
                currentCeremony = startupCeremony;
                startupCeremony = null;
                markForUpdate();
            } else
                startupMain(startupCeremony);

            ceremonyStartupTimer++;
        }

        if(currentCeremony != null)
        {
            doCeremonyEffect(currentCeremony);
        }

        if(isMusicSelecting && worldObj.getWorldTime() % (20 * 60) == 0)
        {
            resetAfterCeremony(true);
        }
    }

    public void doCeremonyEffect(Ceremony cer)
    {
        if(isDoingEndingEffect && cer.getEffectTime() != CeremonyTime.INSTANT)
            ceremonyEffectTimer++;
        if(ceremonyEffectTimer > cer.getEffectTime().getTime()) {
            resetAfterCeremony(true);
            return;
        }

        if(cer.getEffectTime() == CeremonyTime.INSTANT)
        {
            cer.effect(worldObj, xCoord, yCoord, zCoord);
            resetAfterCeremony(true);
        }
        else
        {
            isDoingEndingEffect = true;
            if(canContinueCeremony(cer))
            {
                cer.effect(worldObj, xCoord, yCoord, zCoord);
            } else
            {
                resetAfterCeremony(true);
            }
        }
    }

    public void selectorHandling()
    {
        for(Ceremony ceremony : Totemic.api.getCeremonies().values())
        {
            if(musicSelector[0] != null && musicSelector[1] != null)
            {
                MusicInstrument[] ids = ceremony.getInstruments();
                if(ids[0] == musicSelector[0] && ids[1] == musicSelector[1])
                {
                    particleAroundTotemUpwards("fireworksSpark");
                    startupCeremony = ceremony;
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

    public int[] getRanges(TotemEffect totem)
    {
        int[] array = new int[2];

        array[0] = totem.getHorizontalRange();
        array[1] = totem.getVerticalRange();

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
        for(int value: ceremonyMusic.values())
            totalCeremonyMelody += value;
    }

    public void resetAfterCeremony(boolean doResetMusicSelector)
    {
        currentCeremony = null;
        isCeremony = false;
        startupCeremony = null;
        isMusicSelecting = true;
        ceremonyStartupTimer = 0;
        ceremonyEffectTimer = 0;
        isDoingEndingEffect = false;

        dancingEfficiency = 0;

        ceremonyMusic.clear();
        timesPlayed.clear();
        if(doResetMusicSelector)
            Arrays.fill(musicSelector, null);
        markForUpdate();
    }

    public void resetSelector()
    {
        Arrays.fill(musicSelector, null);
    }

    public boolean canContinueCeremony(Ceremony cer)
    {
        continueTimer++;
        if(continueTimer > 20 * 5)
        {
            continueTimer = 0;
            totalCeremonyMelody = Math.max(totalCeremonyMelody - cer.getMusicPer5(), 0);

            if(totalCeremonyMelody < cer.getMusicPer5())
            {
                resetAfterCeremony(true);
            }
        }

        return totalCeremonyMelody - cer.getMusicPer5() >= 0;
    }


    public boolean canStartCeremony(Ceremony trying)
    {
        /*if(trying.getCeremonyActivation().getDoesNeedItems())
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
        }*/
        //TODO

        resetMelody();
        return totalCeremonyMelody >= (trying.getMusicNeeded() - (dancingEfficiency / 4));
    }

    public void startupMain(Ceremony trying)
    {
        if(ceremonyStartupTimer > trying.getMaxStartupTime().getTime())
        {
            resetAfterCeremony(true);
        }

        if(worldObj.getWorldTime() % 5L == 0)
        {
            danceLikeAMonkey(trying);
        }
    }

    public void danceLikeAMonkey(Ceremony trying)
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
            effects[i] = getTotemEffect(i);
        }
    }

    @Override
    public boolean canUpdate()
    {
        return true;
    }

    protected TotemEffect getTotemEffect(int yOffset)
    {
        TileEntity tileEntity = this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1 + yOffset, this.zCoord);

        return tileEntity instanceof TileTotemPole ? (((TileTotemPole) tileEntity).getTotemEffect()) : null;
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
        currentCeremony = Totemic.api.getCeremony(nbtTagCompound.getString("currentCeremony"));
        dancingEfficiency = nbtTagCompound.getInteger("dancingEfficiency");
        ceremonyEffectTimer = nbtTagCompound.getInteger("ceremonyEffectTimer");
        ceremonyMusic.clear();
        NBTTagCompound ceremonyMusicTag = nbtTagCompound.getCompoundTag("ceremonyMusic");
        for(String key: (Set<String>)ceremonyMusicTag.func_150296_c())
        {
            MusicInstrument instr = Totemic.api.getInstrument(key);
            if(instr != null)
                ceremonyMusic.put(instr, ceremonyMusicTag.getInteger(key));
        }
        startupCeremony = Totemic.api.getCeremony(nbtTagCompound.getString("tryingCeremonyID"));
        totalCeremonyMelody = nbtTagCompound.getInteger("totalCeremonyMelody");
        isMusicSelecting = nbtTagCompound.getBoolean("isMusicSelecting");
        ceremonyStartupTimer = nbtTagCompound.getInteger("ceremonyStartupTimer");
        isCeremony = nbtTagCompound.getBoolean("isCeremony");
        continueTimer = nbtTagCompound.getInteger("continueTimer");
        musicForTotemEffect = nbtTagCompound.getInteger("musicForTotemEffect");
        isDoingEndingEffect = nbtTagCompound.getBoolean("isDoingEndingEffect");
        NBTTagList totemIdsTag = nbtTagCompound.getTagList("effects", Constants.NBT.TAG_STRING);
        effects = new TotemEffect[MAX_HEIGHT];
        for(int i = 0; i < totemIdsTag.tagCount(); i++)
            effects[i] = Totemic.api.getTotem(totemIdsTag.getStringTagAt(i));
        totemWoodBonus = nbtTagCompound.getInteger("totemWoodBonus");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);

        NBTTagCompound ceremonyMusicTag = new NBTTagCompound();
        for(TObjectIntIterator<MusicInstrument> it = ceremonyMusic.iterator(); it.hasNext();)
        {
            it.advance();
            ceremonyMusicTag.setInteger(it.key().getName(), it.value());
        }
        nbtTagCompound.setTag("ceremonyMusic", ceremonyMusicTag);
        nbtTagCompound.setInteger("totemPoleSize", totemPoleSize);
        nbtTagCompound.setInteger("tier", tier);
        if(currentCeremony != null)
            nbtTagCompound.setString("currentCeremony", currentCeremony.getName());
        nbtTagCompound.setInteger("ceremonyEffectTimer", ceremonyEffectTimer);
        nbtTagCompound.setInteger("dancingEfficiency", dancingEfficiency);
        if(startupCeremony != null)
            nbtTagCompound.setString("tryingCeremonyID", startupCeremony.getName());
        nbtTagCompound.setInteger("totalCeremonyMelody", totalCeremonyMelody);
        nbtTagCompound.setBoolean("isMusicSelecting", isMusicSelecting);
        nbtTagCompound.setInteger("ceremonyStartupTimer", ceremonyStartupTimer);
        nbtTagCompound.setBoolean("isCeremony", isCeremony);
        nbtTagCompound.setInteger("continueTimer", continueTimer);
        nbtTagCompound.setInteger("musicForTotemEffect", musicForTotemEffect);
        nbtTagCompound.setBoolean("isDoingEndingEffect", isDoingEndingEffect);
        NBTTagList totemIdsTag = new NBTTagList();
        for(TotemEffect effect: effects)
            totemIdsTag.appendTag(new NBTTagString(String.valueOf(effect)));
        nbtTagCompound.setTag("effects", totemIdsTag);
        nbtTagCompound.setInteger("totemWoodBonus", totemWoodBonus);
    }

    @Override
    public int addMusic(MusicInstrument instr, int amount)
    {
        if(!isCeremony)
        {
            timesPlayed.adjustOrPutValue(instr, 1, 1);
            int prevVal = musicForTotemEffect;
            musicForTotemEffect = Math.min(prevVal + amount / 2, MAX_EFFECT_MUSIC);
            return musicForTotemEffect - prevVal;
        }
        else if(isDoingStartup())
        {
            timesPlayed.adjustOrPutValue(instr, 1, 1);
            int prevVal = ceremonyMusic.get(instr);
            amount = getDiminishedMusic(instr, amount);
            int newVal = Math.min(prevVal + amount, instr.getMusicMaximum());
            ceremonyMusic.put(instr, newVal);
            return newVal - prevVal;
        }
        else
            return 0;
    }

    public int getDiminishedMusic(MusicInstrument instr, int amount)
    {
        if(timesPlayed.get(instr) >= amount)
            return amount * 3 / 4;
        else
            return amount;
    }

    public boolean isDoingStartup()
    {
        return startupCeremony != null;
    }

    public boolean isDoingCeremonyEffect()
    {
        return currentCeremony != null;
    }
}
