package pokefenn.totemic.apiimpl.music;

import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import pokefenn.totemic.api.music.MusicAPI;
import pokefenn.totemic.api.music.MusicAcceptor;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.util.TotemUtil;

public class MusicApiImpl implements MusicAPI
{
    @Override
    public void playMusicForSelector(World world, double x, double y, double z, MusicInstrument instr, int bonusRadius)
    {
        TotemUtil.playMusicForSelector(world, x, y, z, instr, bonusRadius);
    }

    @Override
    public void playMusic(World world, double x, double y, double z, MusicInstrument instr, int bonusRadius, int bonusMusicAmount)
    {
        TotemUtil.playMusic(world, x, y, z, instr, bonusRadius, bonusMusicAmount);
    }

    @Override
    public MusicAcceptor getClosestAcceptor(World world, double x, double y, double z, int horizontalRadius, int verticalRadius)
    {
        return TotemUtil.getClosestAcceptor((WorldServer) world, x, y, z, horizontalRadius, verticalRadius).orElse(null);
    }

    @Override
    public void addMusic(MusicAcceptor tile, MusicInstrument instr, int musicAmount)
    {
        TotemUtil.addMusic(tile, instr, musicAmount);
    }
}
