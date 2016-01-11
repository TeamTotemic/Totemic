package totemic_commons.pokefenn.tileentity.totem;

import static totemic_commons.pokefenn.Totemic.logger;

import java.util.Arrays;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.StatCollector;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.ceremony.CeremonyTime;
import totemic_commons.pokefenn.api.music.MusicAcceptor;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.api.totem.TotemEffect;
import totemic_commons.pokefenn.block.totem.BlockTotemBase;
import totemic_commons.pokefenn.event.GameOverlay;
import totemic_commons.pokefenn.network.PacketHandler;
import totemic_commons.pokefenn.network.client.PacketTotemMusic;
import totemic_commons.pokefenn.tileentity.TileTotemic;
import totemic_commons.pokefenn.util.TotemUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 29/01/14
 * Time: 20:22
 */
public class TileTotemBase extends TileTotemic implements MusicAcceptor, ITickable
{
    public static final int MAX_HEIGHT = 5;
    public static final int MAX_EFFECT_MUSIC = 128;

    public boolean firstTick = true;
    public int tier = 1;
    public int dancingEfficiency = 0;
    public int musicForTotemEffect = 0;
    public int totemPoleSize = 0;
    public boolean musicChanged = false;
    public final TotemEffect[] effects = new TotemEffect[MAX_HEIGHT];
    public final TObjectIntMap<TotemEffect> repetitionBonus = new TObjectIntHashMap<>(Totemic.api.registry().getTotems().size(), 0.75f);
    public int totemWoodBonus = 0;

    public boolean isCeremony = false;
    public final MusicInstrument[] musicSelector = new MusicInstrument[Ceremony.NUM_SELECTORS];
    public final TObjectIntMap<MusicInstrument> ceremonyMusic = new TObjectIntHashMap<>(Totemic.api.registry().getInstruments().size(), 0.75f);
    public final TObjectIntMap<MusicInstrument> timesPlayed = new TObjectIntHashMap<>(Totemic.api.registry().getInstruments().size(), 0.75f);
    public int totalCeremonyMelody = 0;
    public Ceremony startupCeremony = null;
    public Ceremony currentCeremony = null;
    public int ceremonyStartupTimer = 0;
    public int ceremonyEffectTimer = 0;
    public int continueTimer = 0;
    public boolean isDoingEndingEffect = false;

    public TileTotemBase()
    {

    }

    @Override
    public void update()
    {
        if(firstTick)
        {
            calculateTotemWoodBonus();
            firstTick = false;
        }

        if(worldObj.getTotalWorldTime() % 80L == 0)
        {
            totemPoleSize = calculateTotemPoleAmount();
            calculateEffects();
        }

        deprecateMelody();

        if(!worldObj.isRemote) //SERVER
        {
            if(!isCeremony)
                if(worldObj.getTotalWorldTime() % (20L * 30) == 0)
                {
                    timesPlayed.clear();
                }

            if(isCeremony)
            {
                doCeremonyCode();
            }

            if(!isCeremony)
            {
                totemEffect();
            }

            if(worldObj.getTotalWorldTime() % 20L == 0)
            {
                syncMelody();
            }
        }
        else //CLIENT
        {
            if(isCeremony)
                doCeremonyClient();
            else
                totemEffect();

            if(worldObj.getTotalWorldTime() % 40 == 0)
            {
                if(!isCeremony)
                    spawnParticles();
                else
                    spawnParticlesCeremony();
            }
        }
    }

    public void totemEffect()
    {
        if(totemPoleSize > 0)
        {
            for(TotemEffect effect: effects)
            {
                if(effect != null)
                {
                    int[] ranges = getRanges(effect);
                    effect.effect(worldObj, pos, totemPoleSize, ranges[0], ranges[1],
                            musicForTotemEffect, totemWoodBonus, repetitionBonus.get(effect));
                }
            }
        }
    }

    public void spawnParticles()
    {
        for(int i = 0; i < musicForTotemEffect / 16; i++)
        {
            float dx = 2 * worldObj.rand.nextFloat() - 1;
            float dz = 2 * worldObj.rand.nextFloat() - 1;
            worldObj.spawnParticle(EnumParticleTypes.NOTE, pos.getX() + 0.5 + dx, pos.getY(), pos.getZ() + 0.5 + dz, 0, 0.5, 0);
        }
    }

    public void spawnParticlesCeremony()
    {
        for(int i = 0; i < totalCeremonyMelody / 16; i++)
        {
            float dx = 2 * worldObj.rand.nextFloat() - 1;
            float dz = 2 * worldObj.rand.nextFloat() - 1;
            worldObj.spawnParticle(EnumParticleTypes.NOTE, pos.getX() + 0.5 + dx, pos.getY(), pos.getZ() + 0.5 + dz, 0, 0.5, 0);
        }

    }

    public void calculateTotemWoodBonus()
    {
        IBlockState state = worldObj.getBlockState(pos);
        BiomeGenBase biomeGenBase = worldObj.getBiomeGenForCoords(pos);
        if(biomeGenBase == null) //assume some default if that happens
            biomeGenBase = BiomeGenBase.plains;

        totemWoodBonus = 0;

        switch(state.getValue(BlockTotemBase.WOOD))
        {
        case OAK:
            //oak effect
            totemWoodBonus += 2;
            break;

        case SPRUCE:
            //TODO better numbers, just temp for now.
            //spruce effect
            if(biomeGenBase.getTempCategory() == BiomeGenBase.TempCategory.COLD)
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
            if(BiomeDictionary.isBiomeOfType(biomeGenBase, Type.FOREST))
            {
                totemWoodBonus += 2;
            }
            break;

        case JUNGLE:
            //jungle effect
            if(biomeGenBase.getTempCategory() == BiomeGenBase.TempCategory.WARM && !biomeGenBase.getEnableSnow())
            {
                if(biomeGenBase.temperature > 1.0F)
                {
                    totemWoodBonus += 4;
                }
            }
            break;

        case ACACIA:
            //acacia effect
            if(biomeGenBase.getTempCategory() == BiomeGenBase.TempCategory.WARM)
            {
                totemWoodBonus += 3;
            }
            if(BiomeDictionary.isBiomeOfType(biomeGenBase, Type.SPARSE))
            {
                totemWoodBonus += 2;
            }
            break;

        case DARK_OAK:
            //dark oak effect
            if(BiomeDictionary.isBiomeOfType(biomeGenBase, Type.SPOOKY))
            {
                totemWoodBonus += 4;
            }
            break;

        case CEDAR:
            //cedar effect
            totemWoodBonus += 5;
            if(BiomeDictionary.isBiomeOfType(biomeGenBase, Type.MAGICAL))
            {
                totemWoodBonus += 2;
            }
            break;

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

        if(worldObj.getTotalWorldTime() % 20L == 0)
        {
            recalculateMelody();
        }

        if(isDoingStartup())
        {
            if(canStartCeremony(startupCeremony))
            {
                currentCeremony = startupCeremony;
                startupCeremony = null;
                isDoingEndingEffect = currentCeremony.getEffectTime() != CeremonyTime.INSTANT;
                TotemUtil.particlePacket(worldObj, EnumParticleTypes.VILLAGER_HAPPY, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 24, 0.6D, 0.5D, 0.6D, 1.0D);
                markForUpdate();
                markDirty();
            } else
                startupMain(startupCeremony);

            ceremonyStartupTimer++;
        }

        if(currentCeremony != null)
        {
            doCeremonyEffect(currentCeremony);
        }

        if(canMusicSelect() && worldObj.getTotalWorldTime() % (20 * 60) == 0)
        {
            resetAfterCeremony(true);
        }
    }

    @SideOnly(Side.CLIENT)
    public void doCeremonyClient()
    {
        if(currentCeremony != null)
        {
            currentCeremony.effect(worldObj, pos);
            if(currentCeremony.getEffectTime() == CeremonyTime.INSTANT)
                resetAfterCeremony(true);
            else
                ceremonyEffectTimer++;
        }
        else if(startupCeremony != null)
        {
            ceremonyStartupTimer++;
        }

        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if(getDistanceSq(player.posX, player.posY, player.posZ) <= 8*8)
        {
            GameOverlay.activeTotem = this;
        }
        else
        {
            if(GameOverlay.activeTotem == this)
                GameOverlay.activeTotem = null;
        }
    }

    public void doCeremonyEffect(Ceremony cer)
    {
        if(cer.getEffectTime() == CeremonyTime.INSTANT)
        {
            cer.effect(worldObj, pos);
            resetAfterCeremony(true);
        }
        else
        {
            ceremonyEffectTimer++;
            if(ceremonyEffectTimer <= cer.getEffectTime() && drainCeremonyMelody(cer))
            {
                cer.effect(worldObj, pos);
            }
            else
            {
                resetAfterCeremony(true);
            }
        }
    }

    public void selectorHandling()
    {
        if(musicSelector[0] != null && musicSelector[1] != null)
        {
            for(Ceremony ceremony : Totemic.api.registry().getCeremonies().values())
            {
                MusicInstrument[] ids = ceremony.getInstruments();
                if(ids[0] == musicSelector[0] && ids[1] == musicSelector[1])
                {
                    TotemUtil.particlePacket(worldObj, EnumParticleTypes.FIREWORKS_SPARK, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16, 0.7D, 0.5D, 0.7D, 0.0D);
                    startupCeremony = ceremony;
                    resetSelector();
                    markForUpdate();
                    markDirty();
                    return;
                }
            }
            //No match found
            TotemUtil.particlePacket(worldObj, EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16, 0.6D, 0.5D, 0.6D, 0.0D);
            resetAfterCeremony(true);
        }
    }

    public void deprecateMelody()
    {
        if(musicForTotemEffect > 0)
        {
            if(worldObj.getTotalWorldTime() % 47L == 0)
                musicForTotemEffect--;
        }
    }

    public static String getMusicName(int i)
    {
        if(i < 10)
        {
            return StatCollector.translateToLocal("totemic.melodyName.incrediblyLow");
        } else if(i >= 10 && i < 32)
        {
            return StatCollector.translateToLocal("totemic.melodyName.weak");
        } else if(i >= 32 && i < 64)
        {
            return StatCollector.translateToLocal("totemic.melodyName.low");
        } else if(i >= 64 && i < 96)
        {
            return StatCollector.translateToLocal("totemic.melodyName.sufficient");
        } else if(i >= 96 && i < 115)
        {
            return StatCollector.translateToLocal("totemic.melodyName.high");
        } else
        {
            return StatCollector.translateToLocal("totemic.melodyName.maximum");
        }
    }

    public int[] getRanges(TotemEffect totem)
    {
        int horiz = totem.getHorizontalRange();
        int vert = totem.getVerticalRange();

        if(musicForTotemEffect > 10 && musicForTotemEffect < 32)
        {
            horiz += 1;
            vert += 1;
        } else if(musicForTotemEffect > 32 && musicForTotemEffect < 64)
        {
            horiz += 2;
            vert += 2;
        } else if(musicForTotemEffect > 64 && musicForTotemEffect < 96)
        {
            horiz += 3;
            vert += 3;
        } else if(musicForTotemEffect > 96 && musicForTotemEffect < 115)
        {
            horiz += 4;
            vert += 4;
        } else if(musicForTotemEffect > 115)
        {
            horiz += 6;
            vert += 6;
        }

        //FIXME: These are always zero
        //horiz += totemWoodBonus / 5;
        //vert += totemWoodBonus / 5;

        if(totemPoleSize == 5)
        {
            horiz += 2;
            vert += 2;
        }

        //horiz += totemPoleSize / 8;
        //vert += totemPoleSize / 8;

        return new int[] {horiz, vert};
    }

    public void syncMelody()
    {
        if(musicChanged)
        {
            if(isCeremony)
                PacketHandler.sendAround(new PacketTotemMusic(pos, ceremonyMusic), this);
            else
                PacketHandler.sendAround(new PacketTotemMusic(pos, musicForTotemEffect), this);
        }
        musicChanged = false;
    }

    public void recalculateMelody()
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
        ceremonyStartupTimer = 0;
        ceremonyEffectTimer = 0;
        isDoingEndingEffect = false;

        dancingEfficiency = 0;

        ceremonyMusic.clear();
        totalCeremonyMelody = 0;
        timesPlayed.clear();
        if(doResetMusicSelector)
            Arrays.fill(musicSelector, null);
        markForUpdate();
        markDirty();
    }

    public void resetSelector()
    {
        Arrays.fill(musicSelector, null);
    }

    public boolean drainCeremonyMelody(Ceremony cer)
    {
        continueTimer++;
        if(continueTimer > 20 * 5)
        {
            continueTimer = 0;
            totalCeremonyMelody = Math.max(totalCeremonyMelody - cer.getMusicPer5(), 0);
        }

        return totalCeremonyMelody >= cer.getMusicPer5();
    }


    public boolean canStartCeremony(Ceremony trying)
    {
        //TODO: Other possible preconditions, such as the presence of some item

        return totalCeremonyMelody >= (trying.getMusicNeeded() - (dancingEfficiency / 4));
    }

    public void startupMain(Ceremony trying)
    {
        if(ceremonyStartupTimer > trying.getMaxStartupTime())
        {
            TotemUtil.particlePacket(worldObj, EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16, 0.6D, 0.5D, 0.6D, 0.0D);
            resetAfterCeremony(true);
        }

        if(worldObj.getTotalWorldTime() % 5L == 0)
        {
            danceLikeAMonkey(trying);
        }
    }

    public void danceLikeAMonkey(Ceremony trying)
    {
        //TODO
        if(worldObj.getClosestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 8) != null)
        {
            EntityPlayer player = worldObj.getClosestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 8);

            if(dancingEfficiency < 50)
                if((int) player.posX != (int) player.prevPosX && (int) player.posY != (int) player.prevPosY)
                {
                    dancingEfficiency++;
                }
        }
    }

    protected void calculateEffects()
    {
        repetitionBonus.clear();
        for(int i = 0; i < totemPoleSize; i++)
        {
            effects[i] = getTotemEffect(i);
            repetitionBonus.adjustOrPutValue(effects[i], 1, 1);
        }
        Arrays.fill(effects, totemPoleSize, effects.length, null);
    }

    protected TotemEffect getTotemEffect(int yOffset)
    {
        TileEntity tileEntity = this.worldObj.getTileEntity(pos.up(1 + yOffset));

        return tileEntity instanceof TileTotemPole ? (((TileTotemPole) tileEntity).getTotemEffect()) : null;
    }

    protected int calculateTotemPoleAmount()
    {
        int y = 0;
        for(; y < MAX_HEIGHT; y++)
        {
            Block block = worldObj.getBlockState(pos.up(1+y)).getBlock();
            if(block != ModBlocks.totemPole)
                break;
        }
        return y;
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(pos, 0, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);

        tier = tag.getInteger("tier");
        dancingEfficiency = tag.getInteger("dancingEfficiency");
        musicForTotemEffect = tag.getInteger("musicForTotemEffect");
        totemPoleSize = tag.getInteger("totemPoleSize");

        NBTTagList totemIdsTag = tag.getTagList("effects", Constants.NBT.TAG_STRING);
        Arrays.fill(effects, null);
        for(int i = 0; i < totemIdsTag.tagCount(); i++)
            effects[i] = Totemic.api.registry().getTotem(totemIdsTag.getStringTagAt(i));

        isCeremony = tag.getBoolean("isCeremony");
        if(isCeremony)
        {
            NBTTagCompound ceremonyMusicTag = tag.getCompoundTag("ceremonyMusic");
            ceremonyMusic.clear();
            for(String key: ceremonyMusicTag.getKeySet())
            {
                MusicInstrument instr = Totemic.api.registry().getInstrument(key);
                if(instr != null)
                    ceremonyMusic.put(instr, ceremonyMusicTag.getInteger(key));
                else
                    logger.warn("Instrument {} does not exist", key);
            }
            recalculateMelody();

            startupCeremony = Totemic.api.registry().getCeremony(tag.getString("tryingCeremonyID"));
            currentCeremony = Totemic.api.registry().getCeremony(tag.getString("currentCeremony"));
            ceremonyStartupTimer = tag.getInteger("ceremonyStartupTimer");
            ceremonyEffectTimer = tag.getInteger("ceremonyEffectTimer");
            continueTimer = tag.getInteger("continueTimer");
            isDoingEndingEffect = tag.getBoolean("isDoingEndingEffect");
        }
        else
        {
            startupCeremony = null;
            currentCeremony = null;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);

        tag.setInteger("tier", tier);
        tag.setInteger("dancingEfficiency", dancingEfficiency);
        tag.setInteger("musicForTotemEffect", musicForTotemEffect);
        tag.setInteger("totemPoleSize", totemPoleSize);

        NBTTagList totemIdsTag = new NBTTagList();
        for(TotemEffect effect: effects)
            totemIdsTag.appendTag(new NBTTagString(String.valueOf(effect)));
        tag.setTag("effects", totemIdsTag);

        tag.setBoolean("isCeremony", isCeremony);
        if(isCeremony)
        {
            NBTTagCompound ceremonyMusicTag = new NBTTagCompound();
            ceremonyMusic.forEachEntry((instr, amount) -> {
                ceremonyMusicTag.setInteger(instr.getName(), amount);
                return true;
            });
            tag.setTag("ceremonyMusic", ceremonyMusicTag);
            if(startupCeremony != null)
                tag.setString("tryingCeremonyID", startupCeremony.getName());
            if(currentCeremony != null)
                tag.setString("currentCeremony", currentCeremony.getName());
            tag.setInteger("ceremonyStartupTimer", ceremonyStartupTimer);
            tag.setInteger("ceremonyEffectTimer", ceremonyEffectTimer);
            tag.setInteger("continueTimer", continueTimer);
            tag.setBoolean("isDoingEndingEffect", isDoingEndingEffect);
        }
    }

    @Override
    public int addMusic(MusicInstrument instr, int amount)
    {
        int added;
        if(!isCeremony)
        {
            timesPlayed.adjustOrPutValue(instr, 1, 1);
            int prevVal = musicForTotemEffect;
            musicForTotemEffect = Math.min(prevVal + amount / 2, MAX_EFFECT_MUSIC);
            added = musicForTotemEffect - prevVal;
        }
        else if(isDoingStartup())
        {
            timesPlayed.adjustOrPutValue(instr, 1, 1);
            int prevVal = ceremonyMusic.get(instr);
            amount = getDiminishedMusic(instr, amount);
            int newVal = Math.min(prevVal + amount, instr.getMusicMaximum());
            ceremonyMusic.put(instr, newVal);
            added = newVal - prevVal;
        }
        else
            added = 0;

        if(added != 0)
            musicChanged = true;
        return added;
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

    public boolean canMusicSelect()
    {
        return !isDoingStartup() && !isDoingCeremonyEffect();
    }
}
