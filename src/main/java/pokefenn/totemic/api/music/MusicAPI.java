package pokefenn.totemic.api.music;

import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * Provides access to music-related functionality which is commonly used by music instrument blocks and items.
 * Use {@code TotemicAPI.get().music()} to get an instance of this interface.
 */
public interface MusicAPI
{
    /**
     * Plays music from an instrument located at the given position (which might differ from the entity's position)
     * to the closest nearby music acceptor.
     * <p>Note that this method may only be called on the server side.
     * @param entity the entity playing the instrument. May be {@code null} if the instrument is not driven by an entity (e.g. Wind Chime).
     * @param instr the instrument
     * @param bonusRadius additional radius
     * @param bonusMusicAmount additional music amount
     */
    void playMusic(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr, int bonusRadius, int bonusMusicAmount);

    /**
     * Plays music from an instrument from the entity's position to the closest nearby music acceptor.
     * @param entity the entity playing the instrument
     * @param instr the instrument
     * @param bonusRadius additional radius
     * @param bonusMusicAmount additional music amount
     */
    void playMusic(Entity entity, MusicInstrument instr, int bonusRadius, int bonusMusicAmount);

    /**
     * Plays music from an instrument located at the given position (which might differ from the entity's position)
     * to the closest nearby Totem Base to select a ceremony.
     * Usually this is triggered by playing the instrument while sneaking.
     * <p>Note that this method may only be called on the server side.
     * @param entity the entity playing the instrument. May be {@code null} if the instrument is not driven by an entity (e.g. Wind Chime).
     * @param instr the instrument
     * @param bonusRadius additional radius
     */
    void playMusicForSelector(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr, int bonusRadius);

    /**
     * Plays music from an instrument from the entity's position to the closest nearby Totem Base to select a ceremony.
     * Usually this is triggered by playing the instrument while sneaking.
     * <p>Note that this method may only be called on the server side.
     * @param entity the entity playing the instrument
     * @param instr the instrument
     * @param bonusRadius additional radius
     */
    void playMusicForSelector(Entity entity, MusicInstrument instr, int bonusRadius);

    /**
     * Finds the closest MusicAcceptor within range, if there is any.
     * <p>Note that this method may only be called on the server side.
     * @return an {@link Optional} containing the closest MusicAcceptor from that position
     */
    Optional<MusicAcceptor> getClosestAcceptor(World world, double x, double y, double z, int horizontalRadius, int verticalRadius);

    /**
     * Adds music to the given music acceptor tile entity and spawns particles at its location
     * @param entity the entity the music originates from. May be {@code null} if it does not come from an entity (e.g. Wind Chime).
     */
    void addMusic(MusicAcceptor tile, @Nullable Entity entity, MusicInstrument instr, int musicAmount);
}
