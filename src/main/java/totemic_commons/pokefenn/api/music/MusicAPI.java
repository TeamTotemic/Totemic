package totemic_commons.pokefenn.api.music;

import net.minecraft.world.World;

/**
 * Provides access to music-related functionality which is commonly used by music instrument blocks and items.
 * Use <tt>TotemicAPI.get().music()</tt> to get an instance of this interface.
 */
public interface MusicAPI
{
    /**
     * Plays music from an instrument located at the given position to the closest nearby music acceptor.
     * Note that this method may only be called on the server side.
     * @param instr the instrument
     * @param bonusRadius additional radius
     * @param bonusMusicAmount additional music amount
     */
    void playMusic(World world, double x, double y, double z, MusicInstrument instr, int bonusRadius, int bonusMusicAmount);

    /**
     * Plays music from an instrument located at the given position to the closest nearby Totem Base to select a ceremony.
     * Usually this is triggered by playing the instrument while sneaking.
     * Note that this method may only be called on the server side.
     * @param instr the instrument
     * @param bonusRadius additional radius
     */
    void playMusicForSelector(World world, double x, double y, double z, MusicInstrument instr, int bonusRadius);

    /**
     * Finds the closest MusicAcceptor within range.
     * Note that this method may only be called on the server side.
     * @return the closest MusicAcceptor from that position, or null if there is none in range
     */
    MusicAcceptor getClosestAcceptor(World world, double x, double y, double z, int horizontalRadius, int verticalRadius);

    /**
     * Adds music to the given music acceptor tile entity and spawns particles at its location
     */
    void addMusic(MusicAcceptor tile, MusicInstrument instr, int musicAmount, int musicMaximum);
}
