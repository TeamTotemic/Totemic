package pokefenn.totemic.test;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestAssertPosException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import pokefenn.totemic.TotemicConfig;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.block.totem.entity.TotemBaseBlockEntity;
import pokefenn.totemic.block.totem.entity.TotemPoleBlockEntity;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModItems;
import pokefenn.totemic.item.TotemKnifeItem;

/**
 * Tests for the config files.
 *
 * These tests will generally only succeed if the config files from the folder "run-gameTestServer" are being used.
 * It may be necessary to restore the config files from the git repository in case they got corrupted.
 */
@GameTestHolder(TotemicAPI.MOD_ID)
@PrefixGameTestTemplate(false)
public final class ConfigTests {
    @GameTest(batch = "totemic.config", template = "config/custom_totem_wood")
    public static void testCustomTotemWoodConfig(GameTestHelper h) {
        final String testWoodType1ID = "gametest:test_wood_type_1";
        final String testWoodType2ID = "gametest:test_wood_type_2";

        //Test if the custom wood types are present in the config.
        //This should also ensure that the config hasn't been reset to default
        if(TotemicConfig.COMMON.customTotemWoodTypes.get().stream()
                .noneMatch(c -> testWoodType1ID.equals(c.get("id"))))
            throw new GameTestAssertException("Expected wood type " + testWoodType1ID + " to be present in the config");
        if(TotemicConfig.COMMON.customTotemWoodTypes.get().stream()
                .noneMatch(c -> testWoodType2ID.equals(c.get("id"))))
            throw new GameTestAssertException("Expected wood type " + testWoodType2ID + " to be present in the config");

        //Test if the custom wood types are registered correctly
        var woodTypeReg = TotemicAPI.get().registry().woodTypes();
        if(!woodTypeReg.containsKey(ResourceLocation.parse(testWoodType1ID)))
            throw new GameTestAssertException("Expected wood type " + testWoodType1ID + " to be registered");
        if(!woodTypeReg.containsKey(ResourceLocation.parse(testWoodType2ID)))
            throw new GameTestAssertException("Expected wood type " + testWoodType2ID + " to be registered");

        var testWoodType1 = woodTypeReg.get(ResourceLocation.parse(testWoodType1ID));
        if(!testWoodType1.getLogTag().equals(BlockTags.CRIMSON_STEMS)
        || !testWoodType1.getWoodColor().equals(MapColor.CRIMSON_STEM)
        || !testWoodType1.getBarkColor().equals(MapColor.CRIMSON_HYPHAE))
            throw new GameTestAssertException("Wood type " + testWoodType1ID + " has incorrect attributes");
        var testWoodType2 = woodTypeReg.get(ResourceLocation.parse(testWoodType2ID));
        if(!testWoodType2.getLogTag().equals(BlockTags.WARPED_STEMS)
        || !testWoodType2.getWoodColor().equals(MapColor.WOOD)
        || !testWoodType2.getBarkColor().equals(MapColor.PODZOL))
            throw new GameTestAssertException("Wood type " + testWoodType2ID + " has incorrect attributes");

        //Test if carving the blocks works as expected (TODO: Should probably move that to a general test for the Totem Whittling Knife)

        //Totem Bases
        var totemKnife = new ItemStack(ModItems.totem_whittling_knife.get());
        final BlockPos base1Pos = new BlockPos(0, 1, 0);
        final BlockPos base2Pos = new BlockPos(1, 1, 0);
        final BlockPos base3Pos = new BlockPos(2, 1, 0);
        GeneralTests.useItem(h, totemKnife, base1Pos, Direction.NORTH);
        h.assertBlockPresent(ModBlocks.totem_base.get(), base1Pos);
        var base1 = (TotemBaseBlockEntity) h.getBlockEntity(base1Pos);
        if(base1.getWoodType() != testWoodType1)
            throw new GameTestAssertPosException("Incorrect wood type", h.absolutePos(base1Pos), base1Pos, h.getTick());
        if(!getWoodTypeLoc(base1).equals(ResourceLocation.parse(testWoodType1ID)))
            throw new GameTestAssertPosException("Incorrect saved wood type ResourceLocation", h.absolutePos(base1Pos), base1Pos, h.getTick());
        GeneralTests.useItem(h, totemKnife, base2Pos, Direction.NORTH);
        h.assertBlockPresent(ModBlocks.totem_base.get(), base2Pos);
        var base2 = (TotemBaseBlockEntity) h.getBlockEntity(base2Pos);
        if(base2.getWoodType() != testWoodType2)
            throw new GameTestAssertPosException("Incorrect wood type", h.absolutePos(base2Pos), base2Pos, h.getTick());
        if(!getWoodTypeLoc(base2).equals(ResourceLocation.parse(testWoodType2ID)))
            throw new GameTestAssertPosException("Incorrect saved wood type ResourceLocation", h.absolutePos(base2Pos), base2Pos, h.getTick());
        GeneralTests.useItem(h, totemKnife, base3Pos, Direction.NORTH);
        h.assertBlockNotPresent(ModBlocks.totem_base.get(), base3Pos);

        //Totem Poles
        totemKnife.getOrCreateTag().putString(TotemKnifeItem.KNIFE_CARVING_KEY, ModContent.blaze.get().getRegistryName().toString());
        final BlockPos pole1Pos = new BlockPos(0, 2, 0);
        final BlockPos pole2Pos = new BlockPos(1, 2, 0);
        final BlockPos pole3Pos = new BlockPos(2, 2, 0);
        GeneralTests.useItem(h, totemKnife, pole1Pos, Direction.NORTH);
        h.assertBlockPresent(ModBlocks.totem_pole.get(), pole1Pos);
        var pole1 = (TotemPoleBlockEntity) h.getBlockEntity(pole1Pos);
        if(pole1.getWoodType() != testWoodType1)
            throw new GameTestAssertPosException("Incorrect wood type", h.absolutePos(pole1Pos), pole1Pos, h.getTick());
        if(!getWoodTypeLoc(pole1).equals(ResourceLocation.parse(testWoodType1ID)))
            throw new GameTestAssertPosException("Incorrect saved wood type ResourceLocation", h.absolutePos(pole1Pos), pole1Pos, h.getTick());
        GeneralTests.useItem(h, totemKnife, pole2Pos, Direction.NORTH);
        h.assertBlockPresent(ModBlocks.totem_pole.get(), pole2Pos);
        var pole2 = (TotemPoleBlockEntity) h.getBlockEntity(pole2Pos);
        if(pole2.getWoodType() != testWoodType2)
            throw new GameTestAssertPosException("Incorrect wood type", h.absolutePos(pole2Pos), pole2Pos, h.getTick());
        if(!getWoodTypeLoc(pole2).equals(ResourceLocation.parse(testWoodType2ID)))
            throw new GameTestAssertPosException("Incorrect saved wood type ResourceLocation", h.absolutePos(pole2Pos), pole2Pos, h.getTick());
        GeneralTests.useItem(h, totemKnife, pole3Pos, Direction.NORTH);
        h.assertBlockNotPresent(ModBlocks.totem_pole.get(), pole3Pos);

        h.succeed();
    }

    private static ResourceLocation getWoodTypeLoc(Object poleOrBase) {
        try {
            var woodTypeLocField = poleOrBase.getClass().getDeclaredField("woodTypeLoc");
            woodTypeLocField.setAccessible(true);
            return (ResourceLocation) woodTypeLocField.get(poleOrBase);
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
