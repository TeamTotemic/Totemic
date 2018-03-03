package pokefenn.totemic.apiimpl.music;

import java.util.Comparator;
import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pokefenn.totemic.api.music.MusicAPI;
import pokefenn.totemic.api.music.MusicAcceptor;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.tileentity.totem.TileTotemBase;
import pokefenn.totemic.util.EntityUtil;
import pokefenn.totemic.util.TotemUtil;

public class MusicApiImpl implements MusicAPI
{
    @Override
    public boolean playMusic(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr)
    {
        return playMusic0(world, x, y, z, entity, instr, DEFAULT_RANGE, instr.getBaseOutput());
    }

    @Override
    public boolean playMusic(Entity entity, MusicInstrument instr)
    {
        return playMusic0(entity.world, entity.posX, entity.posY, entity.posZ, entity, instr, DEFAULT_RANGE, instr.getBaseOutput());
    }

    @Override
    public boolean playMusic0(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr, int range, int amount)
    {
        return getClosestAcceptor(world, x, y, z, range, range)
                .map(acc -> acc.addMusic(instr, amount))
                .orElse(false);
    }

    @Override
    public boolean playSelector(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr)
    {
        return playSelector(world, x, y, z, entity, instr, DEFAULT_RANGE);
    }

    @Override
    public boolean playSelector(Entity entity, MusicInstrument instr)
    {
        return playSelector(entity.world, entity.posX, entity.posY, entity.posZ, entity, instr, DEFAULT_RANGE);
    }

    @Override
    public boolean playSelector(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr, int range)
    {
        Optional<TileTotemBase> res = getClosestAcceptor(world, x, y, z, range, range)
                .filter(acc -> acc instanceof TileTotemBase)
                .map(acc -> (TileTotemBase) acc)
                .filter(TileTotemBase::canSelect);
        res.ifPresent(totem -> totem.addSelector(entity, instr));
        return res.isPresent();
    }

    @Override
    public Optional<MusicAcceptor> getClosestAcceptor(World world, double x, double y, double z, int horizontalRadius, int verticalRadius)
    {
        return EntityUtil.getTileEntitiesInRange(TileEntity.class, world, new BlockPos(x, y, z), horizontalRadius, verticalRadius).stream()
                .filter(te -> te instanceof MusicAcceptor)
                .min(Comparator.comparing(te -> te.getDistanceSq(x, y, z)))
                .map(te -> (MusicAcceptor) te);
    }

    //Overrides of deprecated methods below
    @SuppressWarnings("deprecation")
    @Override
    public void playMusicForSelector(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr, int bonusRadius)
    {
        playSelector(world, x, y, z, entity, instr, instr.getBaseRange() + bonusRadius);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void playMusicForSelector(Entity entity, MusicInstrument instr, int bonusRadius)
    {
        playSelector(entity.world, entity.posX, entity.posY, entity.posZ, entity, instr, instr.getBaseRange() + bonusRadius);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void playMusic(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr, int bonusRadius, int bonusMusicAmount)
    {
        playMusic0(world, x, y, z, entity, instr, instr.getBaseRange() + bonusRadius, instr.getBaseOutput() + bonusMusicAmount);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void playMusic(Entity entity, MusicInstrument instr, int bonusRadius, int bonusMusicAmount)
    {
        playMusic0(entity.world, entity.posX, entity.posY, entity.posZ, entity, instr, instr.getBaseRange() + bonusRadius, instr.getBaseOutput() + bonusMusicAmount);
    }
    @Override
    public void addMusic(MusicAcceptor tile, @Nullable Entity entity, MusicInstrument instr, int musicAmount)
    {
        TotemUtil.addMusic(tile, entity, instr, musicAmount);
    }
}
