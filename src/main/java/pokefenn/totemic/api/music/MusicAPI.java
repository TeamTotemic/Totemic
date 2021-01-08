package pokefenn.totemic.api.music;

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Provides access to music-related functionality which is commonly used by music instrument blocks and items. Use {@code TotemicAPI.get().music()} to get an
 * instance of this interface.
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
     * May only be called on the server side.
     *
     * @param entity the entity playing the instrument. May be {@code null} if the instrument is not driven by an entity (e.g. Wind Chime).
     * @param instr  the instrument.
     * @return {@code true} if this call had any effect (i.e. a music acceptor was found within range and {@link MusicAcceptor#addMusic} returned {@code true}).
     */
    boolean playMusic(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr);

    /**
     * Plays music from an instrument from the entity's position to all nearby music acceptors (prioritizing higher priority acceptors, and evenly splitting
     * between acceptors with equal priority).
     * <p>
     * The range and amount are the default values given by {@link #DEFAULT_RANGE} and {@link MusicInstrument#getBaseOutput}.
     * <p>
     * May only be called on the server side.
     *
     * @param entity the entity playing the instrument.
     * @param instr  the instrument.
     * @return {@code true} if this call had any effect (i.e. a music acceptor was found within range and {@link MusicAcceptor#addMusic} returned {@code true}).
     */
    boolean playMusic(@Nonnull Entity entity, MusicInstrument instr);

    /**
     * Plays music from an instrument located at the given position (which might differ from the entity's position, e.g. Drum) to all nearby music acceptors
     * (prioritizing higher priority acceptors, and evenly splitting between acceptors with equal priority).
     * <p>
     * The range and amount are the default values given by {@link #DEFAULT_RANGE} and {@link MusicInstrument#getBaseOutput}.
     * <p>
     * May only be called on the server side.
     *
     * @param entity the entity playing the instrument. May be {@code null} if the instrument is not driven by an entity (e.g. Wind Chime).
     * @param instr  the instrument.
     * @return {@code true} if this call had any effect (i.e. a music acceptor was found within range and {@link MusicAcceptor#addMusic} returned {@code true}).
     */
    boolean playMusic(World world, BlockPos pos, @Nullable Entity entity, MusicInstrument instr);

    /**
     * <p>
     * Plays music from an instrument located at the given position (which might differ from the entity's position, e.g. Drum) to all nearby music acceptors
     * (prioritizing higher priority acceptors, and evenly splitting between acceptors with equal priority).
     * <p>
     * May only be called on the server side.
     *
     * @param entity the entity playing the instrument. May be {@code null} if the instrument is not driven by an entity (e.g. Wind Chime).
     * @param instr  the instrument.
     * @param range  the range. The default value is given by {@link #DEFAULT_RANGE}.
     * @param amount the amount of music to play. The default value is given by {@link MusicInstrument#getBaseOutput}.
     * @return {@code true} if this call had any effect (i.e. a music acceptor was found within range and {@link MusicAcceptor#addMusic} returned {@code true}).
     */
    boolean playMusic(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr, int range, double amount);

    /**
     * Attempts to add the given instrument as selector to the closest nearby Totem Base. Usually this is triggered when playing the instrument while sneaking.
     * <p>
     * The range is the default value given by {@link #DEFAULT_RANGE}.
     * <p>
     * May only be called on the server side.
     *
     * @param entity the entity playing the instrument. This is usually a PlayerEntity and should not be {@code null}.
     * @param instr  the instrument.
     * @return {@code true} if this call had any effect (i.e. the closest music acceptor within range is a Totem Base which was not already doing a ceremony).
     */
    boolean playSelector(World world, double x, double y, double z, @Nonnull Entity entity, MusicInstrument instr);

    /**
     * Attempts to add the given instrument as selector to the closest nearby Totem Base. Usually this is triggered when playing the instrument while sneaking.
     * <p>
     * The range is the default value given by {@link #DEFAULT_RANGE}.
     * <p>
     * May only be called on the server side.
     *
     * @param entity the entity playing the instrument. This is usually a PlayerEntity.
     * @param instr  the instrument.
     * @return {@code true} if this call had any effect (i.e. the closest music acceptor within range is a Totem Base which was not already doing a ceremony).
     */
    boolean playSelector(@Nonnull Entity entity, MusicInstrument instr);

    /**
     * Attempts to add the given instrument as selector to the closest nearby Totem Base. Usually this is triggered when playing the instrument while sneaking.
     * <p>
     * The range is the default value given by {@link #DEFAULT_RANGE}.
     * <p>
     * May only be called on the server side.
     *
     * @param entity the entity playing the instrument. This is usually a PlayerEntity and should not be {@code null}.
     * @param instr  the instrument.
     * @return {@code true} if this call had any effect (i.e. the closest music acceptor within range is a Totem Base which was not already doing a ceremony).
     */
    boolean playSelector(World world, BlockPos pos, @Nonnull Entity entity, MusicInstrument instr);

    /**
     * Attempts to add the given instrument as selector to the closest nearby Totem Base. Usually this is triggered when playing the instrument while sneaking.
     * <p>
     * May only be called on the server side.
     *
     * @param entity the entity playing the instrument. This is usually a PlayerEntity and should not be {@code null}.
     * @param instr  the instrument.
     * @param range  the range. The default value is given by {@link #DEFAULT_RANGE}.
     * @return {@code true} if this call had any effect (i.e. the closest music acceptor within range is a Totem Base which was not already doing a ceremony).
     */
    boolean playSelector(World world, double x, double y, double z, @Nonnull Entity entity, MusicInstrument instr, int range);

    /**
     * Finds the closest music acceptor within range.
     *
     * @return an {@link Optional} containing the closest MusicAcceptor within range, or an empty {@link Optional} if there is none.
     * @deprecated By the new mechanics, music is no longer just added to the nearest acceptor but evenly split between acceptors of equal priority.
     */
    @Deprecated
    Optional<MusicAcceptor> getClosestAcceptor(World world, double x, double y, double z, int range);
}
