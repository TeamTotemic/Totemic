package pokefenn.totemic.apiimpl.music;

import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import pokefenn.totemic.api.music.MusicAPI;
import pokefenn.totemic.api.music.MusicAcceptor;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.util.TotemUtil;

public class MusicApiImpl implements MusicAPI
{
    @Override
    public void playMusicForSelector(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr, int bonusRadius)
    {
        TotemUtil.playMusicForSelector(world, x, y, z, entity, instr, bonusRadius);
    }

    @Override
    public void playMusicForSelector(Entity entity, MusicInstrument instr, int bonusRadius)
    {
        TotemUtil.playMusicForSelector(entity, instr, bonusRadius);
    }

    @Override
    public void playMusic(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr, int bonusRadius, int bonusMusicAmount)
    {
        TotemUtil.playMusic(world, x, y, z, entity, instr, bonusRadius, bonusMusicAmount);
    }

    @Override
    public void playMusic(Entity entity, MusicInstrument instr, int bonusRadius, int bonusMusicAmount)
    {
        TotemUtil.playMusic(entity, instr, bonusRadius, bonusMusicAmount);
    }

    @Override
    public Optional<MusicAcceptor> getClosestAcceptor(World world, double x, double y, double z, int horizontalRadius, int verticalRadius)
    {
        return TotemUtil.getClosestAcceptor((WorldServer) world, x, y, z, horizontalRadius, verticalRadius);
    }

    @Override
    public void addMusic(MusicAcceptor tile, @Nullable Entity entity, MusicInstrument instr, int musicAmount)
    {
        TotemUtil.addMusic(tile, entity, instr, musicAmount);
    }
}
