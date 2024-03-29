package pokefenn.totemic.api.music;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * Provides access to music-related functionality which is commonly used by music instrument blocks and items.
 * <p>
 * Use {@code TotemicAPI.get().music()} to get an instance of this interface.
 */
public interface MusicAPI {
    /**
     * The default range of music instruments.
     */
    static final int DEFAULT_RANGE = 5;

    /**
     * Plays music from an instrument located at the given position (which might differ from the entity's position, e.g. Drum) to all nearby music acceptors
     * (prioritizing higher priority acceptors, and evenly splitting between acceptors with equal priority).
     * <p>
     * The range and amount are the default values given by {@link #DEFAULT_RANGE} and {@link MusicInstrument#getBaseOutput}.
     * <p>
     * May be called on client and server side. This method takes care of playing the SoundEvent and spawning particles.
     *
     * @param entity the entity playing the instrument. May be {@code null} if the instrument is not driven by an entity (e.g. Wind Chime).
     * @param instr  the instrument.
     */
    void playMusic(Level level, Vec3 pos, @Nullable Entity entity, MusicInstrument instr);

    /**
     * Plays music from an instrument from the entity's position to all nearby music acceptors (prioritizing higher priority acceptors, and evenly splitting
     * between acceptors with equal priority).
     * <p>
     * The range and amount are the default values given by {@link #DEFAULT_RANGE} and {@link MusicInstrument#getBaseOutput}.
     * <p>
     * May be called on client and server side. This method takes care of playing the SoundEvent and spawning particles.
     *
     * @param entity the entity playing the instrument.
     * @param instr  the instrument.
     */
    void playMusic(@Nonnull Entity entity, MusicInstrument instr);

    /**
     * Plays music from an instrument located at the given position (which might differ from the entity's position, e.g. Drum) to all nearby music acceptors
     * (prioritizing higher priority acceptors, and evenly splitting between acceptors with equal priority).
     * <p>
     * The range and amount are the default values given by {@link #DEFAULT_RANGE} and {@link MusicInstrument#getBaseOutput}.
     * <p>
     * May be called on client and server side. This method takes care of playing the SoundEvent and spawning particles.
     *
     * @param entity the entity playing the instrument. May be {@code null} if the instrument is not driven by an entity (e.g. Wind Chime).
     * @param instr  the instrument.
     */
    void playMusic(Level level, BlockPos pos, @Nullable Entity entity, MusicInstrument instr);

    /**
     * Plays music from an instrument located at the given position (which might differ from the entity's position, e.g. Drum) to all nearby music acceptors
     * (prioritizing higher priority acceptors, and evenly splitting between acceptors with equal priority).
     * <p>
     * May be called on client and server side. This method takes care of playing the SoundEvent and spawning particles.
     *
     * @param entity the entity playing the instrument. May be {@code null} if the instrument is not driven by an entity (e.g. Wind Chime).
     * @param instr  the instrument.
     * @param range  the range. The default value is given by {@link #DEFAULT_RANGE}.
     * @param amount the amount of music to play. The default value is given by {@link MusicInstrument#getBaseOutput}.
     */
    void playMusic(Level level, Vec3 pos, @Nullable Entity entity, MusicInstrument instr, int range, int amount);

    /**
     * Attempts to add the given instrument as selector to the closest nearby Totem Base. Usually this is triggered when playing the instrument while sneaking.
     * <p>
     * The range is the default value given by {@link #DEFAULT_RANGE}.
     * <p>
     * May be called on client and server side. This method takes care of playing the SoundEvent and spawning particles.
     *
     * @param entity the entity playing the instrument. This is usually a Player and should not be {@code null}.
     * @param instr  the instrument.
     */
    void playSelector(Level level, Vec3 pos, @Nonnull Entity entity, MusicInstrument instr);

    /**
     * Attempts to add the given instrument as selector to the closest nearby Totem Base. Usually this is triggered when playing the instrument while sneaking.
     * <p>
     * The range is the default value given by {@link #DEFAULT_RANGE}.
     * <p>
     * May be called on client and server side. This method takes care of playing the SoundEvent and spawning particles.
     *
     * @param entity the entity playing the instrument. This is usually a Player.
     * @param instr  the instrument.
     */
    void playSelector(@Nonnull Entity entity, MusicInstrument instr);

    /**
     * Attempts to add the given instrument as selector to the closest nearby Totem Base. Usually this is triggered when playing the instrument while sneaking.
     * <p>
     * The range is the default value given by {@link #DEFAULT_RANGE}.
     * <p>
     * May be called on client and server side. This method takes care of playing the SoundEvent and spawning particles.
     *
     * @param entity the entity playing the instrument. This is usually a Player and should not be {@code null}.
     * @param instr  the instrument.
     */
    void playSelector(Level level, BlockPos pos, @Nonnull Entity entity, MusicInstrument instr);

    /**
     * Attempts to add the given instrument as selector to the closest nearby Totem Base. Usually this is triggered when playing the instrument while sneaking.
     * <p>
     * May be called on client and server side. This method takes care of playing the SoundEvent and spawning particles.
     *
     * @param entity the entity playing the instrument. This is usually a PlayerEntity and should not be {@code null}.
     * @param instr  the instrument.
     * @param range  the range. The default value is given by {@link #DEFAULT_RANGE}.
     */
    void playSelector(Level level, Vec3 pos, @Nonnull Entity entity, MusicInstrument instr, int range);
}
