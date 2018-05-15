package pokefenn.totemic.api.music;

import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Provides access to music-related functionality which is commonly used by music instrument blocks and items.
 * Use {@code TotemicAPI.get().music()} to get an instance of this interface.
 */
public interface MusicAPI
{
    /**
     * The default range of music instruments.
     */
    static final int DEFAULT_RANGE = 5;

    /**
     * Plays music from an instrument located at the given position (which might differ from the entity's position, e.g. Drum)
     * to the closest nearby music acceptor.
     * <p>The range and amount are the default values given by {@link #DEFAULT_RANGE} and {@link MusicInstrument#getBaseOutput}.
     * <p>May only be called on the server side.
     * @param entity the entity playing the instrument. May be {@code null} if the instrument is not driven by an entity (e.g. Wind Chime).
     * @param instr the instrument.
     * @return {@code true} if this call had any effect (i.e. a music acceptor was found within range and {@link MusicAcceptor#addMusic} returned {@code true}).
     */
    boolean playMusic(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr);

    /**
     * Plays music from an instrument from the entity's position to the closest nearby music acceptor.
     * <p>The range and amount are the default values given by {@link #DEFAULT_RANGE} and {@link MusicInstrument#getBaseOutput}.
     * <p>May only be called on the server side.
     * @param entity the entity playing the instrument.
     * @param instr the instrument.
     * @return {@code true} if this call had any effect (i.e. a music acceptor was found within range and {@link MusicAcceptor#addMusic} returned {@code true}).
     */
    boolean playMusic(Entity entity, MusicInstrument instr);

    /**
     * Plays music from an instrument located at the given position (which might differ from the entity's position, e.g. Drum)
     * to the closest nearby music acceptor.
     * <p>The range and amount are the default values given by {@link #DEFAULT_RANGE} and {@link MusicInstrument#getBaseOutput}.
     * <p>May only be called on the server side.
     * @param entity the entity playing the instrument. May be {@code null} if the instrument is not driven by an entity (e.g. Wind Chime).
     * @param instr the instrument.
     * @return {@code true} if this call had any effect (i.e. a music acceptor was found within range and {@link MusicAcceptor#addMusic} returned {@code true}).
     */
    boolean playMusic(World world, BlockPos pos, @Nullable Entity entity, MusicInstrument instr);

    /**
     * @deprecated Renamed to {@link #playMusic(World,double,double,double,Entity,MusicInstrument,int,int)}.
     */
    @Deprecated
    boolean playMusic0(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr, int range, int amount);

    /**
     * <p>Plays music from an instrument located at the given position (which might differ from the entity's position, e.g. Drum)
     * to the closest nearby music acceptor.
     * <p>May only be called on the server side.
     * @param entity the entity playing the instrument. May be {@code null} if the instrument is not driven by an entity (e.g. Wind Chime).
     * @param instr the instrument.
     * @param range the range. The default value is given by {@link #DEFAULT_RANGE}.
     * @param amount the amount of music to play. The default value is given by {@link MusicInstrument#getBaseOutput}.
     * @return {@code true} if this call had any effect (i.e. a music acceptor was found within range and {@link MusicAcceptor#addMusic} returned {@code true}).
     */
    boolean playMusic(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr, int range, int amount);

    /**
     * If the nearest music acceptor within range from the given position is a Totem Base, attempts to add the given instrument as selector to it.
     * Usually this is triggered when playing the instrument while sneaking.
     * <p>The range is the default value given by {@link #DEFAULT_RANGE}.
     * <p>May only be called on the server side.
     * @param entity the entity playing the instrument. May be {@code null} if the instrument is not driven by an entity (e.g. Wind Chime).
     * @param instr the instrument.
     * @return {@code true} if this call had any effect (i.e. the closest music acceptor within range is a Totem Base which was not already doing a ceremony).
     */
    boolean playSelector(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr);

    /**
     * If the nearest music acceptor within range from the given entity is a Totem Base, attempts to add the given instrument as selector to it.
     * Usually this is triggered when playing the instrument while sneaking.
     * <p>The range is the default value given by {@link #DEFAULT_RANGE}.
     * <p>May only be called on the server side.
     * @param entity the entity playing the instrument.
     * @param instr the instrument.
     * @return {@code true} if this call had any effect (i.e. the closest music acceptor within range is a Totem Base which was not already doing a ceremony).
     */
    boolean playSelector(Entity entity, MusicInstrument instr);

    /**
     * If the nearest music acceptor within range from the given position is a Totem Base, attempts to add the given instrument as selector to it.
     * Usually this is triggered when playing the instrument while sneaking.
     * <p>The range is the default value given by {@link #DEFAULT_RANGE}.
     * <p>May only be called on the server side.
     * @param entity the entity playing the instrument. May be {@code null} if the instrument is not driven by an entity (e.g. Wind Chime).
     * @param instr the instrument.
     * @return {@code true} if this call had any effect (i.e. the closest music acceptor within range is a Totem Base which was not already doing a ceremony).
     */
    boolean playSelector(World world, BlockPos pos, @Nullable Entity entity, MusicInstrument instr);

    /**
     * If the nearest music acceptor within range from the given position is a Totem Base, attempts to add the given instrument as selector to it.
     * Usually this is triggered when playing the instrument while sneaking.
     * <p>May only be called on the server side.
     * @param entity the entity playing the instrument. May be {@code null} if the instrument is not driven by an entity (e.g. Wind Chime).
     * @param instr the instrument.
     * @param range the range. The default value is given by {@link #DEFAULT_RANGE}.
     * @return {@code true} if this call had any effect (i.e. the closest music acceptor within range is a Totem Base which was not already doing a ceremony).
     */
    boolean playSelector(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr, int range);

    /**
     * Finds the closest music acceptor within range.
     * @return an {@link Optional} containing the closest MusicAcceptor within range, or an empty {@link Optional} if there is none.
     */
    Optional<MusicAcceptor> getClosestAcceptor(World world, double x, double y, double z, int horizontalRadius, int verticalRadius);
}
