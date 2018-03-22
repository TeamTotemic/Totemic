package pokefenn.totemic.apiimpl.music;

import java.util.Comparator;
import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
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
    public boolean playMusic(World world, BlockPos pos, @Nullable Entity entity, MusicInstrument instr)
    {
        return playMusic(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, entity, instr);
    }

    @Override
    public boolean playMusic0(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr, int range, int amount)
    {
        MusicAcceptor acc = getClosestAcceptor(world, x, y, z, range, amount).orElse(null);
        if(acc != null)
        {
            boolean ret = acc.addMusic(instr, amount);
            spawnMusicParticles(world, acc, ret ? EnumParticleTypes.NOTE : EnumParticleTypes.CLOUD);
            return ret;
        }
        else
            return false;
    }

    private void spawnMusicParticles(World world, MusicAcceptor acc, EnumParticleTypes type)
    {
        BlockPos pos = ((TileEntity) acc).getPos();
        ((WorldServer) world).spawnParticle(type, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 6, 0.5, 0.5, 0.5, 0.0);
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
    public boolean playSelector(World world, BlockPos pos, @Nullable Entity entity, MusicInstrument instr)
    {
        return playSelector(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, entity, instr);
    }

    @Override
    public boolean playSelector(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr, int range)
    {
        Optional<TileTotemBase> totem = getClosestAcceptor(world, x, y, z, range, range)
                .filter(acc -> acc instanceof TileTotemBase)
                .map(acc -> (TileTotemBase) acc)
                .filter(TileTotemBase::canSelect);
        totem.ifPresent(t -> t.addSelector(entity, instr));
        return totem.isPresent();
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
