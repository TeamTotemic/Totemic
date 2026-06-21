package pokefenn.totemic.test;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModItems;

@GameTestHolder(TotemicAPI.MOD_ID)
@PrefixGameTestTemplate(false)
public final class GeneralTests {
    @GameTest(batch = "totemic.general", template = "general/tipi_place")
    public static void testTipiPlace(GameTestHelper h) {
        var tipiItem = new ItemStack(ModBlocks.tipi.get());
        var placePos = new BlockPos(1, 1, 1);
        useItem(h, tipiItem, placePos, Direction.UP);
        checkForTipi(h, placePos.above(), Direction.NORTH);
        h.succeed();
    }

    @GameTest(batch = "totemic.general", template = "general/tipi_place_invalid_surface")
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

    @GameTest(batch = "totemic.general", template = "general/tipi_place_obstructed")
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

    @GameTest(batch = "totemic.general", template = "general/tipi_destroy")
    public static void testTipiDestroy(GameTestHelper h) {
        final BlockPos tipi1Pos = new BlockPos(1, 2, 1);
        final BlockPos dummy2Pos = new BlockPos(4, 3, 2);
        var level = h.getLevel();
        var player = h.makeMockPlayer(GameType.SURVIVAL);

        var tipi1State = h.getBlockState(tipi1Pos);
        tipi1State.getBlock().playerWillDestroy(level, h.absolutePos(tipi1Pos), tipi1State, player);
        tipi1State.onDestroyedByPlayer(level, h.absolutePos(tipi1Pos), player, true, level.getFluidState(h.absolutePos(tipi1Pos)));

        var dummy2State = h.getBlockState(dummy2Pos);
        dummy2State.getBlock().playerWillDestroy(level, h.absolutePos(dummy2Pos), dummy2State, player);
        dummy2State.onDestroyedByPlayer(level, h.absolutePos(dummy2Pos), player, true, level.getFluidState(h.absolutePos(dummy2Pos)));

        h.forEveryBlockInStructure(pos -> {
            if(pos.getY() >= 2)
                h.assertBlockPresent(Blocks.AIR, pos);
        });
        h.succeed();
    }

    @GameTest(batch = "totemic.general", template = "general/cedar_flower_pot")
    public static void testCedarFlowerPot(GameTestHelper h) {
        final BlockPos pos = new BlockPos(0, 1, 0);
        var apos = h.absolutePos(pos);
        var level = h.getLevel();

        h.assertBlockPresent(Blocks.FLOWER_POT, pos);

        var player = h.makeMockPlayer(GameType.SURVIVAL);
        var stack = new ItemStack(ModBlocks.cedar_sapling.get());
        player.setItemInHand(InteractionHand.MAIN_HAND, stack);
        h.getBlockState(pos).useItemOn(stack, h.getLevel(), player, InteractionHand.MAIN_HAND,
                new BlockHitResult(Vec3.atCenterOf(apos), Direction.NORTH, apos, true));
        h.assertBlockPresent(ModBlocks.potted_cedar_sapling.get(), pos);
        level.destroyBlock(apos, true);
        h.assertBlockPresent(Blocks.AIR, pos);
        h.assertItemEntityPresent(Blocks.FLOWER_POT.asItem(), pos, 1.0);
        h.assertItemEntityPresent(ModBlocks.cedar_sapling.get().asItem(), pos, 1.0);
        h.succeed();
    }

    @GameTest(batch = "totemic.general", template = "general/cedar_signs")
    public static void testCedarSigns(GameTestHelper h) {
        h.assertTrue(BlockEntityType.SIGN.isValid(ModBlocks.cedar_sign.get().defaultBlockState()),
                "cedar_sign not valid for Sign block entity type");
        h.assertTrue(BlockEntityType.SIGN.isValid(ModBlocks.cedar_wall_sign.get().defaultBlockState()),
                "cedar_wall_sign not valid for Sign block entity type");
        h.assertTrue(BlockEntityType.HANGING_SIGN.isValid(ModBlocks.cedar_hanging_sign.get().defaultBlockState()),
                "cedar_hanging_sign not valid for Hanging Sign block entity type");
        h.assertTrue(BlockEntityType.HANGING_SIGN.isValid(ModBlocks.cedar_wall_hanging_sign.get().defaultBlockState()),
                "cedar_wall_hanging_sign not valid for Hanging Sign block entity type");

        useItem(h, new ItemStack(ModItems.cedar_sign.get()), new BlockPos(1, 1, 0), Direction.UP);
        h.assertBlockPresent(ModBlocks.cedar_sign.get(), new BlockPos(1, 2, 0));
        h.assertBlockEntityData(new BlockPos(1, 2, 0), be -> be instanceof SignBlockEntity, () -> "Expected Sign block entity");

        useItem(h, new ItemStack(ModItems.cedar_sign.get()), new BlockPos(1, 3, 1), Direction.NORTH);
        h.assertBlockPresent(ModBlocks.cedar_wall_sign.get(), new BlockPos(1, 3, 0));
        h.assertBlockEntityData(new BlockPos(1, 3, 0), be -> be instanceof SignBlockEntity, () -> "Expected Sign block entity");

        useItem(h, new ItemStack(ModItems.cedar_hanging_sign.get()), new BlockPos(0, 4, 0), Direction.DOWN);
        h.assertBlockPresent(ModBlocks.cedar_hanging_sign.get(), new BlockPos(0, 3, 0));
        h.assertBlockEntityData(new BlockPos(0, 3, 0), be -> be instanceof HangingSignBlockEntity, () -> "Expected Hanging Sign block entity");

        useItem(h, new ItemStack(ModItems.cedar_hanging_sign.get()), new BlockPos(1, 4, 1), Direction.NORTH);
        h.assertBlockPresent(ModBlocks.cedar_wall_hanging_sign.get(), new BlockPos(1, 4, 0));
        h.assertBlockEntityData(new BlockPos(1, 4, 0), be -> be instanceof HangingSignBlockEntity, () -> "Expected Hanging Sign block entity");

        h.succeed();
    }

    @GameTest(batch = "totemic.general", template = "general/cedar_logs")
    public static void testCedarLogStripping(GameTestHelper h) {
        final BlockPos pos1 = new BlockPos(0, 1, 0);
        useItem(h, new ItemStack(Items.WOODEN_AXE), pos1, Direction.UP);
        h.assertBlockPresent(ModBlocks.stripped_cedar_log.get(), pos1);

        final BlockPos pos2 = new BlockPos(1, 1, 0);
        useItem(h, new ItemStack(Items.WOODEN_AXE), pos2, Direction.UP);
        h.assertBlockPresent(ModBlocks.stripped_cedar_wood.get(), pos2);

        h.succeed();
    }

    public static void useItem(GameTestHelper h, ItemStack item, BlockPos pos, Direction dir) {
        var apos = h.absolutePos(pos);
        item.useOn(new UseOnContext(h.getLevel(), h.makeMockPlayer(GameType.SURVIVAL), InteractionHand.MAIN_HAND, item,
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
