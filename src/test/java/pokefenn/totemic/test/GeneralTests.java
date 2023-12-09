package pokefenn.totemic.test;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.gametest.GameTestHolder;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.init.ModBlocks;

@GameTestHolder(value = "totemic.general", namespace = TotemicAPI.MOD_ID)
public final class GeneralTests {
    @GameTest(template = "general/tipi_place")
    public static void testTipiPlace(GameTestHelper h) {
        var tipiItem = new ItemStack(ModBlocks.tipi.get());
        var placePos = new BlockPos(1, 1, 1);
        useItem(h, tipiItem, placePos, Direction.UP);
        checkForTipi(h, placePos.above(), Direction.NORTH);
        h.succeed();
    }

    @GameTest(template = "general/tipi_place_invalid_surface")
    public static void testTipiPlaceInvalidSurface(GameTestHelper h) {
        var tipiItem = new ItemStack(ModBlocks.tipi.get());
        var placePos = new BlockPos(1, 1, 1);
        useItem(h, tipiItem, placePos, Direction.UP);
        h.forEveryBlockInStructure(pos -> {
            if(pos.getY() >= 2)
                h.assertBlockPresent(Blocks.AIR, pos);
        });
        h.succeed();
    }

    @GameTest(template = "general/tipi_place_obstructed")
    public static void testTipiPlaceObstructed(GameTestHelper h) {
        var tipiItem = new ItemStack(ModBlocks.tipi.get());
        var placePos = new BlockPos(1, 1, 1);
        var obstructPos = new BlockPos(1, 4, 2);
        useItem(h, tipiItem, placePos, Direction.UP);
        h.forEveryBlockInStructure(pos -> {
            if(pos.getY() >= 2)
                h.assertBlockPresent(pos.equals(obstructPos) ? Blocks.STONE : Blocks.AIR, pos);
        });
        h.succeed();
    }

    @GameTest(template = "general/tipi_destroy")
    public static void testTipiDestroy(GameTestHelper h) {
        final BlockPos tipi1Pos = new BlockPos(1, 2, 1);
        final BlockPos dummy2Pos = new BlockPos(4, 3, 2);
        var level = h.getLevel();
        var player = h.makeMockPlayer();
        h.getBlockState(tipi1Pos).onDestroyedByPlayer(level, h.absolutePos(tipi1Pos), player, true, level.getFluidState(h.absolutePos(tipi1Pos)));
        h.getBlockState(dummy2Pos).onDestroyedByPlayer(level, h.absolutePos(dummy2Pos), player, true, level.getFluidState(h.absolutePos(dummy2Pos)));

        h.forEveryBlockInStructure(pos -> {
            if(pos.getY() >= 2)
                h.assertBlockPresent(Blocks.AIR, pos);
        });
        h.succeed();
    }

    private static void useItem(GameTestHelper h, ItemStack item, BlockPos pos, Direction dir) {
        var apos = h.absolutePos(pos);
        item.useOn(new UseOnContext(h.getLevel(), h.makeMockPlayer(), InteractionHand.MAIN_HAND, item,
                new BlockHitResult(Vec3.atCenterOf(apos), dir, apos, true)));
    }

    private static void checkForTipi(GameTestHelper h, BlockPos pos, Direction facing) {
        h.assertBlockPresent(ModBlocks.tipi.get(), pos);
        //bottom part
        for(int y = 0; y < 2; y++) {
            for(var dir: Direction.Plane.HORIZONTAL) {
                var p = pos.relative(dir).above(y);
                if(dir != facing)
                    h.assertBlockPresent(ModBlocks.dummy_tipi.get(), p);
                else
                    h.assertBlockNotPresent(ModBlocks.dummy_tipi.get(), p);
            }
        }
        //top part
        for(int y = 3; y < 6; y++) {
            var p = pos.above(y);
            h.assertBlockPresent(ModBlocks.dummy_tipi.get(), p);
        }
    }
}
