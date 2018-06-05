package pokefenn.totemic.apiimpl.music;

import static pokefenn.totemic.Totemic.logger;

import java.util.Comparator;
import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pokefenn.totemic.api.TotemicCapabilities;
import pokefenn.totemic.api.music.MusicAPI;
import pokefenn.totemic.api.music.MusicAcceptor;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.tileentity.totem.TotemState;
import pokefenn.totemic.util.EntityUtil;

public class MusicApiImpl implements MusicAPI
{
    @Override
    public boolean playMusic(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr)
    {
        return playMusic(world, x, y, z, entity, instr, DEFAULT_RANGE, instr.getBaseOutput());
    }

    @Override
    public boolean playMusic(Entity entity, MusicInstrument instr)
    {
        return playMusic(entity.world, entity.posX, entity.posY, entity.posZ, entity, instr, DEFAULT_RANGE, instr.getBaseOutput());
    }

    @Override
    public boolean playMusic(World world, BlockPos pos, @Nullable Entity entity, MusicInstrument instr)
    {
        return playMusic(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, entity, instr);
    }

    @Deprecated
    @Override
    public boolean playMusic0(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr, int range, int amount)
    {
        return playMusic(world, x, y, z, entity, instr, range, amount);
    }

    @Override
    public boolean playMusic(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr, int range, int amount)
    {
        //TODO: Temporary
        if(range == 0 || amount == 0)
            logger.error("", new IllegalArgumentException("The meanings of the playMusic parameters have changed, they are no longer bonus range and amount."));
        return getClosestAcceptor(world, x, y, z, range, range)
                .map(acc -> acc.acceptMusic(instr, amount, x, y, z, entity))
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
    public boolean playSelector(World world, BlockPos pos, @Nullable Entity entity, MusicInstrument instr)
    {
        return playSelector(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, entity, instr);
    }

    @Override
    public boolean playSelector(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr, int range)
    {
        Optional<TotemState> totemState = getClosestAcceptor(world, x, y, z, range, range)
                .filter(acc -> acc instanceof TotemState)
                .map(acc -> (TotemState) acc)
                .filter(TotemState::canSelect);
        totemState.ifPresent(t -> t.addSelector(entity, instr));
        return totemState.isPresent();
    }

    @Override
    public Optional<MusicAcceptor> getClosestAcceptor(World world, double x, double y, double z, int horizontalRadius, int verticalRadius)
    {
        return EntityUtil.getTileEntitiesInRange(TileEntity.class, world, new BlockPos(x, y, z), horizontalRadius, verticalRadius).stream()
                .peek(tile -> {
                    //TODO: Temporary.
                    //Log an error message if someone uses MusicAcceptor wrongly.
                    if(tile instanceof MusicAcceptor && !tile.hasCapability(TotemicCapabilities.MUSIC_ACCEPTOR, null))
                        logger.error("Directly implementing MusicAcceptor is no longer supported! Expose it as Capability instead, or in addition. Affected tile entity: {}", tile);
                })
                .filter(tile -> tile.hasCapability(TotemicCapabilities.MUSIC_ACCEPTOR, null))
                .min(Comparator.comparing(tile -> tile.getDistanceSq(x, y, z)))
                .map(tile -> tile.getCapability(TotemicCapabilities.MUSIC_ACCEPTOR, null));
    }
}
