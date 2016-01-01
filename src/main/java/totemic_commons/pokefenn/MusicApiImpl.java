package totemic_commons.pokefenn;

import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import totemic_commons.pokefenn.api.music.MusicAPI;
import totemic_commons.pokefenn.api.music.MusicAcceptor;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.util.TotemUtil;

public class MusicApiImpl implements MusicAPI
{
    @Override
    public void playMusicForSelector(World world, int x, int y, int z, MusicInstrument instr, int bonusRadius)
    {
        TotemUtil.playMusicForSelector(world, x, y, z, instr, bonusRadius);
    }

    @Override
    public void playMusic(World world, int x, int y, int z, MusicInstrument instr, int bonusRadius, int bonusMusicAmount)
    {
        TotemUtil.playMusic(world, x, y, z, instr, bonusRadius, bonusMusicAmount);
    }

    @Override
    public MusicAcceptor getClosestAcceptor(World world, int x, int y, int z, int horizontalRadius, int verticalRadius)
    {
        return TotemUtil.getClosestAcceptor((WorldServer) world, x, y, z, horizontalRadius, verticalRadius);
    }

    @Override
    public void addMusic(MusicAcceptor tile, MusicInstrument instr, int musicAmount, int musicMaximum)
    {
        TotemUtil.addMusic(tile, instr, musicAmount, musicMaximum);
    }

    @Override
    public void particlePacket(World world, String name, double x, double y, double z, int num,
            double spreadX, double spreadY, double spreadZ, double vel)
    {
        TotemUtil.particlePacket(world, name, x, y, z, num, spreadX, spreadY, spreadZ, vel);
    }
}
