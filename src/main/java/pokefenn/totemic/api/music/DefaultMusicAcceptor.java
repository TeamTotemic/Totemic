package pokefenn.totemic.api.music;

import java.util.Objects;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.INBTSerializable;
import pokefenn.totemic.api.TotemicAPI;

/**
 * Simple default implementation of {@link MusicAcceptor}.
 *
 * <p>
 * This implementation stores the music it accepts, broken down into the instruments. It will only accept up to the maximum specified by each instrument.
 *
 * <p>
 * The behavior is similar (but simplified) to a Totem Base while starting up a ceremony.
 */
public class DefaultMusicAcceptor implements MusicAcceptor, INBTSerializable<CompoundTag> {
    private Vec3 position;
    private final Object2IntMap<MusicInstrument> music = new Object2IntOpenHashMap<>(TotemicAPI.get().registry().instruments().size());
    private int totalMusic = 0;

    public DefaultMusicAcceptor(Vec3 position) {
        this.position = position;
    }

    public DefaultMusicAcceptor() {
        this(Vec3.ZERO);
    }

    /**
     * Accepts and stores music from the given instrument, up to the maximum specified by the instrument.
     *
     * @return {@code true} if any music was accepted.
     */
    @Override
    public MusicResult acceptMusic(MusicInstrument instr, int amount, Vec3 from, @Nullable Entity entity) {
        int oldVal = music.getInt(instr);
        int newVal = Math.min(oldVal + amount, instr.getMusicMaximum()); //implicit null check on instr
        if(newVal != oldVal) {
            music.put(instr, newVal);
            totalMusic += (newVal - oldVal);
            return (newVal == oldVal + amount) ? MusicResult.SUCCESS : MusicResult.SUCCESS_SATURATED;
        }
        else
            return MusicResult.SATURATED;
    }

    /**
     * @return the amount of music stored from the given instrument.
     */
    public int getMusicAmount(MusicInstrument instr)
    {
        return music.getInt(instr);
    }

    /**
     * Sets the amount of music for the given instrument. This method does not check if the amount exceeds the maximum.
     */
    public void setMusicAmount(MusicInstrument instr, int amount)
    {
        Objects.requireNonNull(instr);
        int oldVal = music.getInt(instr);
        if(amount != oldVal) {
            music.put(instr, amount);
            totalMusic += (amount - oldVal);
        }
    }

    /**
     * @return the total amount of music stored from all instruments.
     */
    public int getTotalMusic() {
        return totalMusic;
    }

    @Override
    public Vec3 getPosition() {
        return position;
    }

    public void setPosition(Vec3 position) {
        this.position = position;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();

        for(Entry<MusicInstrument> entry: music.object2IntEntrySet())
            nbt.putInt(entry.getKey().toString(), entry.getIntValue());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        music.clear();
        totalMusic = 0;
        for(String key: tag.getAllKeys()) {
            MusicInstrument instr = TotemicAPI.get().registry().instruments().get(new ResourceLocation(key));

            if(instr != null) {
                int amount = tag.getInt(key);
                music.put(instr, amount);
                totalMusic += amount;
            }
            else
                LogManager.getLogger().warn("Unknown music instrument: {}", key);
        }
    }
}
