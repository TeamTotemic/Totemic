package pokefenn.totemic.util;

import java.util.stream.Stream;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileUtil {
    public static <T extends TileEntity> Stream<T> getTileEntitiesInRange(Class<? extends T> type, World world, BlockPos pos, int range) {
        return getTileEntitiesIn(type, world, pos.add(-range, -range, -range), pos.add(range + 1, range + 1, range + 1));
    }

    public static <T extends TileEntity> Stream<T> getTileEntitiesIn(Class<? extends T> type, World world, BlockPos min, BlockPos max) {
        // TODO
        return Stream.empty();
    }
}
