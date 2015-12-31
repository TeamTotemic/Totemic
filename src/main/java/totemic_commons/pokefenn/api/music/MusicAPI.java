package totemic_commons.pokefenn.api.music;

import net.minecraft.world.World;

/**
 * Provides access to music-related functionality which is usually used by music instruments
 */
public interface MusicAPI
{
    /**
     * Adds music to the given music acceptor tile entity and spawns particles at its location
     */
    public void addMusic(MusicAcceptor tile, MusicInstrument instr, int musicAmount, int musicMaximum);

    /**
     * Plays music from an instrument located at the given position to nearby music acceptors
     * @param instr the instrument
     * @param bonusRadius additional radius
     * @param bonusMusicAmount additional music amount
     */
    public void playMusic(World world, int x, int y, int z, MusicInstrument instr, int bonusRadius, int bonusMusicAmount);

    /**
     * Plays music from an instrument located at the given position to nearby Totem bases to select a ceremony.
     * Usually this is triggered by playing the instrument while sneaking.
     * @param instr the instrument
     * @param bonusRadius additional radius
     */
    public void playMusicForSelector(World world, int x, int y, int z, MusicInstrument instr, int bonusRadius);

    /**
     * Sends a packet to the client, spawning a cloud of particles.
     *
     * Internally, calls WorldServer.func_147487_a().
     * @param world the world. Must be an instance of WorldServer.
     * @param name the name of the particle
     * @param x the x-position
     * @param y the y-position
     * @param z the z-position
     * @param num how many particles to spawn. Can also be zero, then only one
     * particle is spawned and the next three parameters give the velocity rather than the spread.
     * @param spreadX how much the cloud is spread out in x-direction
     * @param spreadY how much the cloud is spread out in y-direction
     * @param spreadZ how much the cloud is spread out in z-direction
     * @param vel the velocity of the particles
     */
    public void particlePacket(World world, String name, double x, double y, double z, int num,
            double spreadX, double spreadY, double spreadZ, double vel);
}
