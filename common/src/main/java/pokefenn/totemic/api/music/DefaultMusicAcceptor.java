package pokefenn.totemic.api.music;

import java.util.Objects;

import javax.annotation.Nullable;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.api.TotemicAPI;

/**
 * Simple default implementation of {@link MusicAcceptor}.
 *
 * <p>
 * This implementation stores the music it accepts, broken down into the instruments. It will only accept up to the maximum specified by each instrument.
 *
 * <p>
 * The behavior is the same as a Totem Base while starting up a ceremony.
 */
public class DefaultMusicAcceptor implements MusicAcceptor {
    private Vec3 position;
    private final Object2IntMap<MusicInstrument> music = new Object2IntOpenHashMap<>(TotemicAPI.get().registry().instruments().size());
    private int totalMusic = 0;

    /**
     * Creates a new DefaultMusicAcceptor whose {@link #getPosition()} method returns the given position.
     */
    public DefaultMusicAcceptor(Vec3 position) {
        this.position = position;
    }

    /**
     * Creates a new DefaultMusicAcceptor whose {@link #getPosition()} method returns the zero vector.
     */
    public DefaultMusicAcceptor() {
        this(Vec3.ZERO);
    }

    /**
     * Accepts and stores music from the given instrument, up to the maximum specified by the instrument.
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
     * Returns the amount of music stored from the given instrument.
     */
    public int getMusicAmount(MusicInstrument instr) {
        return music.getInt(instr);
    }

    /**
     * Sets the amount of music for the given instrument. This method does not check if the amount exceeds the maximum.
     */
    public void setMusicAmount(MusicInstrument instr, int amount) {
        Objects.requireNonNull(instr);
        int oldVal = music.getInt(instr);
        if(amount != oldVal) {
            music.put(instr, amount);
            totalMusic += (amount - oldVal);
        }
    }

    /**
     * Returns the total amount of music stored from all instruments.
     */
    public int getTotalMusic() {
        return totalMusic;
    }

    @Override
    public Vec3 getPosition() {
        return position;
    }

    /**
     * Sets the position returned by the {@link #getPosition()} method.
     */
    public void setPosition(Vec3 position) {
        this.position = position;
    }

    /**
     * Serializes the stored music values into a ValueOutputList (as you can get from {@link ValueOutput#childrenList(String)}).
     * <p>
     * Note that the serialization format is different from 1.21.1 and before (a list of compounds with "id" and "amount" keys,
     * rather than a single compound where the keys are the instrument IDs).
     */
    public void save(ValueOutput.ValueOutputList outList) {
        // this storage format is somewhat inefficient, as each entry is saved as a separate compound tag with "id" and "amount" keys,
        // but more idiomatic I guess, since ValueInput does not provide access to the stored keys.
        music.forEach((instr, amount) -> {
            var child = outList.addChild();
            child.store("id", TotemicAPI.get().registry().instruments().byNameCodec(), instr);
            child.putInt("amount", amount);
        });
    }

    /**
     * Deserializes the music values from the given ValueInputList (as you can get from {@link ValueInput#childrenListOrEmpty(String)}).
     * <p>
     * Note that the serialization format is different from 1.21.1 and before (a list of compounds with "id" and "amount" keys,
     * rather than a single compound where the keys are the instrument IDs).
     */
    public void load(ValueInput.ValueInputList inList) {
        music.clear();
        totalMusic = 0;
        for(var child: inList) {
            var optInstr = child.read("id", TotemicAPI.get().registry().instruments().byNameCodec());
            int amount = child.getIntOr("amount", 0);
            if(optInstr.isPresent()) {
                music.put(optInstr.get(), amount);
                totalMusic += amount;
            }
        }
    }
}
