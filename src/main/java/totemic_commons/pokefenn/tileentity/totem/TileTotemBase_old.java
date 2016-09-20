package totemic_commons.pokefenn.tileentity.totem;

import static totemic_commons.pokefenn.Totemic.logger;

import java.util.ArrayList;
import java.util.Arrays;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicAcceptor;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.api.totem.TotemBase;
import totemic_commons.pokefenn.api.totem.TotemEffect;
import totemic_commons.pokefenn.block.totem.BlockTotemBase;
import totemic_commons.pokefenn.handler.GameOverlay;
import totemic_commons.pokefenn.network.NetworkHandler;
import totemic_commons.pokefenn.network.client.PacketCeremonyStartup;
import totemic_commons.pokefenn.network.client.PacketTotemEffectMusic;
import totemic_commons.pokefenn.tileentity.TileTotemic;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 29/01/14
 * Time: 20:22
 */
public class TileTotemBase_old extends TileTotemic implements MusicAcceptor, TotemBase, ITickable
{
    public static final int MAX_HEIGHT = 5;
    public static final int MAX_EFFECT_MUSIC = 128;

    private boolean firstTick = true;
    private int dancingEfficiency = 0;
    public int musicForTotemEffect = 0;
    private int totemPoleSize = 0;
    private boolean musicChanged = false;
    private final TotemEffect[] effects = new TotemEffect[MAX_HEIGHT];
    private final TObjectIntMap<TotemEffect> repetitionBonus = new TObjectIntHashMap<>(Totemic.api.registry().getTotems().size(), 0.75f);
    private int totemWoodBonus = 0;

    public boolean isCeremony = false;
    public final ArrayList<MusicInstrument> musicSelector = new ArrayList<>(Ceremony.MAX_SELECTORS);
    public final TObjectIntMap<MusicInstrument> ceremonyMusic = new TObjectIntHashMap<>(Totemic.api.registry().getInstruments().size(), 0.75f);
    public final TObjectIntMap<MusicInstrument> timesPlayed = new TObjectIntHashMap<>(Totemic.api.registry().getInstruments().size(), 0.75f);
    public int totalCeremonyMelody = 0;
    public Ceremony startupCeremony = null;
    public Ceremony currentCeremony = null;
    public int ceremonyStartupTimer = 0;
    public int ceremonyEffectTimer = 0;
    public int continueTimer = 0;
    public boolean isDoingEndingEffect = false;

    public TileTotemBase_old()
    {

    }

    @Override
    public void update()
    {
        //Can't use onLoad() because the chunk is not yet loaded when it is called
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

        diminishMelody();

        if(!worldObj.isRemote) //SERVER
        {
            if(isCeremony)
            {
                doCeremonyCode();
            }
            else
            {
                if(worldObj.getTotalWorldTime() % (20L * 30) == 0)
                {
                    timesPlayed.clear();
                }
                totemEffect();
                if(worldObj.getTotalWorldTime() % 20L == 0)
                {
                    syncMelody();
                }
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

    private void totemEffect()
    {
        if(totemPoleSize > 0)
        {
            for(TotemEffect effect: effects)
            {
                if(effect != null)
                {
                    int[] ranges = getRanges(effect);
                    effect.effect(worldObj, pos, this, repetitionBonus.get(effect), ranges[0], ranges[1]);
                }
            }
        }
    }

    private void spawnParticles()
    {
        for(int i = 0; i < musicForTotemEffect / 16; i++)
        {
            float dx = 2 * worldObj.rand.nextFloat() - 1;
            float dz = 2 * worldObj.rand.nextFloat() - 1;
            worldObj.spawnParticle(EnumParticleTypes.NOTE, pos.getX() + 0.5 + dx, pos.getY(), pos.getZ() + 0.5 + dz, 0, 0.5, 0);
        }
    }

    private void spawnParticlesCeremony()
    {
        for(int i = 0; i < totalCeremonyMelody / 16; i++)
        {
            float dx = 2 * worldObj.rand.nextFloat() - 1;
            float dz = 2 * worldObj.rand.nextFloat() - 1;
            worldObj.spawnParticle(EnumParticleTypes.NOTE, pos.getX() + 0.5 + dx, pos.getY(), pos.getZ() + 0.5 + dz, 0, 0.5, 0);
        }

    }

    private void calculateTotemWoodBonus()
    {
        IBlockState state = worldObj.getBlockState(pos);
        Biome biome = worldObj.getBiomeGenForCoords(pos);
        if(biome == null) //assume some default if that happens
            biome = Biomes.PLAINS;

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
            if(biome.getTempCategory() == Biome.TempCategory.COLD)
            {
                if(biome.getEnableSnow())
                {
                    totemWoodBonus += 2;
                }
                if(biome.getTemperature() < 0.4F)
                {
                    totemWoodBonus += 3;
                }
            }
            break;

        case BIRCH:
            //birch effect
            if(BiomeDictionary.isBiomeOfType(biome, Type.FOREST))
            {
                totemWoodBonus += 2;
            }
            break;

        case JUNGLE:
            //jungle effect
            if(biome.getTempCategory() == Biome.TempCategory.WARM && !biome.getEnableSnow())
            {
                if(biome.getTemperature() > 1.0F)
                {
                    totemWoodBonus += 4;
                }
            }
            break;

        case ACACIA:
            //acacia effect
            if(biome.getTempCategory() == Biome.TempCategory.WARM)
            {
                totemWoodBonus += 3;
            }
            if(BiomeDictionary.isBiomeOfType(biome, Type.SPARSE))
            {
                totemWoodBonus += 2;
            }
            break;

        case DARK_OAK:
            //dark oak effect
            if(BiomeDictionary.isBiomeOfType(biome, Type.SPOOKY))
            {
                totemWoodBonus += 4;
            }
            break;

        case CEDAR:
            //cedar effect
            totemWoodBonus += 5;
            if(BiomeDictionary.isBiomeOfType(biome, Type.MAGICAL))
            {
                totemWoodBonus += 2;
            }
            break;

        default:
            break;
        }
    }

    private void doCeremonyCode()
    {
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
                isDoingEndingEffect = currentCeremony.getEffectTime() != Ceremony.INSTANT;
                ((WorldServer)worldObj).spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 24, 0.6D, 0.5D, 0.6D, 1.0D);
                markForUpdate();
                markDirty();
            } else
                startupMain(startupCeremony);

            ceremonyStartupTimer++;

            if(worldObj.getTotalWorldTime() % 20L == 0)
            {
                NetworkHandler.sendAround(new PacketCeremonyStartup(pos, ceremonyMusic, ceremonyStartupTimer), this, 16);
            }
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
    private void doCeremonyClient()
    {
        if(currentCeremony != null)
        {
            currentCeremony.effect(worldObj, pos, ceremonyEffectTimer);
            if(currentCeremony.getEffectTime() == Ceremony.INSTANT)
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

    private void doCeremonyEffect(Ceremony cer)
    {
        if(cer.getEffectTime() == Ceremony.INSTANT)
        {
            cer.effect(worldObj, pos, 0);
            resetAfterCeremony(true);
        }
        else
        {
            if(ceremonyEffectTimer < cer.getEffectTime() && drainCeremonyMelody(cer))
            {
                cer.effect(worldObj, pos, ceremonyEffectTimer);
                ceremonyEffectTimer++;
            }
            else
            {
                resetAfterCeremony(true);
            }
        }
    }

    public void addSelector(MusicInstrument instr)
    {
        if(!canMusicSelect())
            return;

        isCeremony = true;
        musicSelector.add(instr);

        ((WorldServer) worldObj).spawnParticle(EnumParticleTypes.NOTE, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 6, 0.5, 0.5, 0.5, 0.0);
        markForUpdate();
        markDirty();

        if(musicSelector.size() < Ceremony.MIN_SELECTORS)
            return; //less than minimum possible number of instruments

        for(Ceremony ceremony : Totemic.api.registry().getCeremonies().values())
        {
            if(selectorsMatch(ceremony.getInstruments()))
            {
                ((WorldServer)worldObj).spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16, 0.7D, 0.5D, 0.7D, 0.0D);
                startupCeremony = ceremony;
                resetSelector();
                return;
            }
        }

        //No match found - but only reset if the maximum number of selectors is reached
        if(musicSelector.size() >= Ceremony.MAX_SELECTORS)
        {
            ((WorldServer)worldObj).spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16, 0.6D, 0.5D, 0.6D, 0.0D);
            resetAfterCeremony(true);
        }
    }

    private boolean selectorsMatch(MusicInstrument[] instrs)
    {
        return musicSelector.size() == instrs.length && musicSelector.equals(Arrays.asList(instrs));
    }

    private void diminishMelody()
    {
        if(musicForTotemEffect > 0)
        {
            if(worldObj.getTotalWorldTime() % 47L == 0)
            {
                musicForTotemEffect--;
                musicChanged = true;
                markDirty();
            }
        }
    }

    public int[] getRanges(TotemEffect totem)
    {
        int horiz = totem.getHorizontalRange();
        int vert = totem.getVerticalRange();

        if(musicForTotemEffect >= 10 && musicForTotemEffect < 32)
        {
            horiz += 1;
            vert += 1;
        } else if(musicForTotemEffect >= 32 && musicForTotemEffect < 64)
        {
            horiz += 2;
            vert += 2;
        } else if(musicForTotemEffect >= 64 && musicForTotemEffect < 96)
        {
            horiz += 3;
            vert += 3;
        } else if(musicForTotemEffect >= 96 && musicForTotemEffect < 115)
        {
            horiz += 4;
            vert += 4;
        } else if(musicForTotemEffect >= 115)
        {
            horiz += 5;
            vert += 5;
        }

        horiz += totemWoodBonus / 3;
        vert += totemWoodBonus / 3;

        if(totemPoleSize == 5)
        {
            horiz += 2;
            vert += 2;
        }

        return new int[] {horiz, vert};
    }

    public void syncMelody()
    {
        if(musicChanged)
        {
            NetworkHandler.sendAround(new PacketTotemEffectMusic(pos, musicForTotemEffect), this, 32);
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
            resetSelector();
        markForUpdate();
        markDirty();
    }

    public void resetSelector()
    {
        musicSelector.clear();
    }

    private boolean drainCeremonyMelody(Ceremony cer)
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

    private void startupMain(Ceremony trying)
    {
        if(ceremonyStartupTimer > trying.getAdjustedMaxStartupTime(worldObj.getDifficulty()))
        {
            ((WorldServer)worldObj).spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16, 0.6D, 0.5D, 0.6D, 0.0D);
            resetAfterCeremony(true);
        }

        if(worldObj.getTotalWorldTime() % 5L == 0)
        {
            danceLikeAMonkey(trying);
        }
    }

    private void danceLikeAMonkey(Ceremony trying)
    {
        //TODO
        /*if(worldObj.getClosestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 8) != null)
        {
            EntityPlayer player = worldObj.getClosestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 8);

            if(dancingEfficiency < 50)
                if((int) player.posX != (int) player.prevPosX && (int) player.posY != (int) player.prevPosY)
                {
                    dancingEfficiency++;
                }
        }*/
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
        int y;
        for(y = 0; y < MAX_HEIGHT; y++)
        {
            Block block = worldObj.getBlockState(pos.up(1+y)).getBlock();
            if(block != ModBlocks.totemPole)
                break;
        }
        return y;
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);

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
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        tag = super.writeToNBT(tag);

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
        return tag;
    }

    @Override
    public boolean addMusic(MusicInstrument instr, int amount)
    {
        boolean added;
        if(!isCeremony)
        {
            timesPlayed.adjustOrPutValue(instr, 1, 1);
            int prevVal = musicForTotemEffect;
            musicForTotemEffect = Math.min(prevVal + amount / 2, MAX_EFFECT_MUSIC);
            added = musicForTotemEffect > prevVal;
        }
        else if(isDoingStartup())
        {
            timesPlayed.adjustOrPutValue(instr, 1, 1);
            int prevVal = ceremonyMusic.get(instr);
            amount = getDiminishedMusic(instr, amount);
            int newVal = Math.min(prevVal + amount, instr.getMusicMaximum());
            ceremonyMusic.put(instr, newVal);
            added = newVal > prevVal;
        }
        else
            added = false;

        if(added)
        {
            musicChanged = true;
            markDirty();
        }
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

    @Override
    public int getTotemEffectMusic()
    {
        return musicForTotemEffect;
    }

    @Override
    public int getPoleSize()
    {
        return totemPoleSize;
    }

    @Override
    public int getRepetition(TotemEffect effect)
    {
        return repetitionBonus.get(effect);
    }

    @Override
    public TotemEffect[] getEffects()
    {
        return effects.clone();
    }

    @Override
    public int getWoodBonus()
    {
        return totemWoodBonus;
    }
}
