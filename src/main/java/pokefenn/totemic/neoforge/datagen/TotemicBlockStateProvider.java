package pokefenn.totemic.neoforge.datagen;

import net.minecraft.client.renderer.block.model.BlockModel.GuiLight;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.loaders.ObjModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.util.TransformationHelper.TransformOrigin;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.block.TipiBlock;
import pokefenn.totemic.block.totem.TotemBaseBlock;
import pokefenn.totemic.block.totem.TotemPoleBlock;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModItems;

public final class TotemicBlockStateProvider extends BlockStateProvider {
    public TotemicBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, TotemicAPI.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //Blocks
        logBlock(ModBlocks.cedar_log.get());
        logBlock(ModBlocks.stripped_cedar_log.get());
        axisBlock(ModBlocks.cedar_wood.get(), blockTexture(ModBlocks.cedar_log.get()), blockTexture(ModBlocks.cedar_log.get()));
        axisBlock(ModBlocks.stripped_cedar_wood.get(), blockTexture(ModBlocks.stripped_cedar_log.get()), blockTexture(ModBlocks.stripped_cedar_log.get()));
        simpleBlock(ModBlocks.cedar_leaves.get(), models().withExistingParent("totemic:cedar_leaves", "block/leaves").texture("all", "totemic:block/cedar_leaves"));
        models().withExistingParent("totemic:cedar_leaves_opaque", "block/leaves").texture("all", "totemic:block/cedar_leaves_opaque");
        simpleBlock(ModBlocks.cedar_sapling.get(), models().withExistingParent(key(ModBlocks.cedar_sapling.get()).toString(), "block/cross").texture("cross", blockTexture(ModBlocks.cedar_sapling.get())).renderType("cutout"));
        simpleBlock(ModBlocks.drum.get(), models().getExistingFile(modLoc("drum")));
        simpleBlock(ModBlocks.wind_chime.get(), blockEntityRenderer(ModBlocks.wind_chime.get(), mcLoc("block/white_terracotta"))
                .transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
                    .rotation(75, 45, 0)
                    .translation(0, 1.25F, 0)
                    .scale(0.375F)
                    .end()
                .transform(ItemDisplayContext.GUI)
                    .rotation(30, 225, 0)
                    .translation(0, 0, 0)
                    .scale(0.875F)
                    .end()
                .end());
        simpleBlock(ModBlocks.cedar_planks.get());
        var cedarPlankTex = blockTexture(ModBlocks.cedar_planks.get());
        buttonBlock(ModBlocks.cedar_button.get(), cedarPlankTex);
        fenceBlock(ModBlocks.cedar_fence.get(), cedarPlankTex);
        fenceGateBlock(ModBlocks.cedar_fence_gate.get(), cedarPlankTex);
        pressurePlateBlock(ModBlocks.cedar_pressure_plate.get(), cedarPlankTex);
        signBlock(ModBlocks.cedar_sign.get(), ModBlocks.cedar_wall_sign.get(), cedarPlankTex);
        hangingSignBlock(ModBlocks.cedar_hanging_sign.get(), ModBlocks.cedar_wall_hanging_sign.get(), blockTexture(ModBlocks.stripped_cedar_log.get()));
        slabBlock(ModBlocks.cedar_slab.get(), key(ModBlocks.cedar_planks.get()), cedarPlankTex);
        stairsBlock(ModBlocks.cedar_stairs.get(), cedarPlankTex);
        doorBlock(ModBlocks.cedar_door.get(), modLoc("block/cedar_door_bottom"), modLoc("block/cedar_door_top"));
        trapdoorBlock(ModBlocks.cedar_trapdoor.get(), modLoc("block/cedar_trapdoor"), true);
        simpleBlock(ModBlocks.potted_cedar_sapling.get(), models().singleTexture(key(ModBlocks.potted_cedar_sapling.get()).toString(), mcLoc("block/flower_pot_cross"), "plant", blockTexture(ModBlocks.cedar_sapling.get())).renderType("cutout"));
        simpleBlock(ModBlocks.totem_torch.get(), models().getExistingFile(modLoc("totem_torch")));
        horizontalBlockIgnoringProperties(ModBlocks.tipi.get(), models().getBuilder(key(ModBlocks.tipi.get()).toString())
                .customLoader(ObjModelBuilder::begin).modelLocation(modLoc("models/block/tipi.obj")).end()
                .texture("particle", mcLoc("block/white_wool"))
                .rootTransforms()
                    .origin(TransformOrigin.CORNER)
                    .translation(0, 0.95F, 0)
                    .scale(2.85F)
                    .end(),
                0, //angle offset of 0, rotates the model by 180°
                TipiBlock.OCCUPIED);
        simpleBlock(ModBlocks.dummy_tipi.get(), models().getBuilder(key(ModBlocks.dummy_tipi.get()).toString()).texture("particle", mcLoc("block/white_wool")));
        horizontalBlockIgnoringProperties(ModBlocks.totem_pole.get(), models().getExistingFile(modLoc("dynamic_totem_pole")), TotemPoleBlock.WATERLOGGED);
        horizontalBlockIgnoringProperties(ModBlocks.totem_base.get(), models().getExistingFile(modLoc("dynamic_totem_base")), TotemBaseBlock.WATERLOGGED);
        totemWoodTypes();

        //Items
        var im = itemModels();
        im.basicItem(ModItems.flute.get());
        im.basicItem(ModItems.infused_flute.get());
        im.basicItem(ModItems.jingle_dress.get());
        basicItemWithParent(ModItems.rattle.get(), mcLoc("item/handheld"));
        im.basicItem(ModItems.eagle_bone_whistle.get());
        basicItemWithParent(ModItems.totem_whittling_knife.get(), mcLoc("item/handheld"));
        basicItemWithParent(ModItems.totemic_staff.get(), mcLoc("item/handheld"));
        basicItemWithParent(ModItems.ceremony_cheat.get(), mcLoc("item/handheld"));
        im.spawnEggItem(ModItems.buffalo_spawn_egg.get());
        im.spawnEggItem(ModItems.bald_eagle_spawn_egg.get());
        im.spawnEggItem(ModItems.baykok_spawn_egg.get());
        im.basicItem(ModItems.buffalo_meat.get());
        im.basicItem(ModItems.cooked_buffalo_meat.get());
        im.basicItem(ModItems.buffalo_tooth.get());
        im.basicItem(ModItems.buffalo_hide.get());
        im.basicItem(ModItems.iron_bells.get());
        im.basicItem(ModItems.eagle_bone.get());
        im.basicItem(ModItems.eagle_feather.get());
        var baykokBow = im.withExistingParent(key(ModItems.baykok_bow.get()).toString(), "item/bow").texture("layer0", modLoc("item/baykok_bow"));
        baykokBow.override().predicate(mcLoc("pulling"), 1).model(im.basicItem(modLoc("baykok_bow_pulling_0")).parent(baykokBow)).end();
        baykokBow.override().predicate(mcLoc("pulling"), 1).predicate(mcLoc("pull"), 0.65F).model(im.basicItem(modLoc("baykok_bow_pulling_1")).parent(baykokBow)).end();
        baykokBow.override().predicate(mcLoc("pulling"), 1).predicate(mcLoc("pull"), 0.9F).model(im.basicItem(modLoc("baykok_bow_pulling_2")).parent(baykokBow)).end();
        im.basicItem(modLoc("totempedia"));
        var medBagOpen = im.basicItem(modLoc("medicine_bag_open"));
        var medBag = im.basicItem(ModItems.medicine_bag.get()).override().predicate(modLoc("open"), 1).model(medBagOpen).end();
        im.getBuilder(key(ModItems.creative_medicine_bag.get()).toString()).parent(medBag).override().predicate(modLoc("open"), 1).model(medBagOpen).end();

        //Block items
        im.simpleBlockItem(ModBlocks.stripped_cedar_log.get());
        im.simpleBlockItem(ModBlocks.cedar_log.get());
        im.simpleBlockItem(ModBlocks.stripped_cedar_wood.get());
        im.simpleBlockItem(ModBlocks.cedar_wood.get());
        im.simpleBlockItem(ModBlocks.cedar_leaves.get());
        im.singleTexture(key(ModBlocks.cedar_sapling.get()).toString(), mcLoc("item/generated"), "layer0", blockTexture(ModBlocks.cedar_sapling.get()));
        im.simpleBlockItem(ModBlocks.cedar_planks.get());
        im.singleTexture(key(ModBlocks.cedar_button.get()).toString(), mcLoc("block/button_inventory"), "texture", cedarPlankTex);
        im.singleTexture(key(ModBlocks.cedar_fence.get()).toString(), mcLoc("block/fence_inventory"), "texture", cedarPlankTex);
        im.simpleBlockItem(ModBlocks.cedar_fence_gate.get());
        im.simpleBlockItem(ModBlocks.cedar_pressure_plate.get());
        im.basicItem(key(ModBlocks.cedar_sign.get()));
        im.basicItem(key(ModBlocks.cedar_hanging_sign.get()));
        im.simpleBlockItem(ModBlocks.cedar_slab.get());
        im.simpleBlockItem(ModBlocks.cedar_stairs.get());
        im.basicItem(key(ModBlocks.cedar_door.get()));
        im.withExistingParent(key(ModBlocks.cedar_trapdoor.get()).toString(), modLoc("block/cedar_trapdoor_bottom"));
        im.simpleBlockItem(ModBlocks.drum.get());
        im.simpleBlockItem(ModBlocks.wind_chime.get());
        im.withExistingParent(key(ModBlocks.totem_torch.get()).toString(), modLoc("block/totem_torch"))
                .transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
                    .rotation(0, 45, 0)
                    .translation(0, 1.5F, 1.0F)
                    .scale(0.25F)
                    .end()
                .transform(ItemDisplayContext.GUI)
                    .rotation(30, 225, 0)
                    .translation(0, -1.0F, 0)
                    .scale(0.625F)
                    .end()
                .transform(ItemDisplayContext.FIXED)
                    .translation(0, 0, 0.25F)
                    .scale(0.5F)
                    .end()
                .end();
        //use a separate item model because the transforms are not compatible with the tipi's root transform
        im.getBuilder(key(ModBlocks.tipi.get()).toString())
                .customLoader(ObjModelBuilder::begin).modelLocation(modLoc("models/block/tipi.obj")).end()
                .transforms()
                .transform(ItemDisplayContext.GUI)
                    .rotation(30, 225, 0)
                    .translation(0, -2.5F, 0)
                    .scale(0.4F)
                    .end()
                .transform(ItemDisplayContext.GROUND)
                    .scale(0.25F)
                    .end()
                .transform(ItemDisplayContext.FIXED)
                    .translation(0, -2.5F, 0)
                    .scale(0.4F)
                    .end()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
                    .rotation(75, 45, 0)
                    .translation(0, 0.05F, 0)
                    .scale(0.25F)
                    .end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND)
                    .rotation(75, 45, 0)
                    .translation(0, 0.05F, 0)
                    .scale(0.25F)
                    .end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
                    .rotation(0, 45, 0)
                    .scale(0.25F)
                    .end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
                    .rotation(0, 225, 0)
                    .scale(0.25F)
                    .end()
                .end();
        im.withExistingParent(key(ModBlocks.totem_base.get()).toString(), modLoc("block/dynamic_totem_base"));
        im.withExistingParent(key(ModBlocks.totem_pole.get()).toString(), modLoc("block/dynamic_totem_pole"));
    }

    private void totemWoodTypes() {
        //Generate Totem Base and Totem Pole model files for each wood type
        ModContent.WOOD_TYPES.getEntries().forEach(woodType -> {
            var woodTypeId = woodType.getRegistryName();
            var namespace = woodTypeId.getPath().equals("cedar") ? "totemic" : "minecraft";

            var poleModel = models().getBuilder(woodTypeId.toString() + "_totem_pole"); //the pole model has no parent, it only specifies the textures and is being loaded in TotemPoleModel.getMaterials.
            var baseModel = models().withExistingParent(woodTypeId.toString() + "_totem_base", modLoc("totem_base"));
            setTotemTextures(poleModel, namespace, woodTypeId.getPath());
            setTotemTextures(baseModel, namespace, woodTypeId.getPath());
        });
    }

    private BlockModelBuilder setTotemTextures(BlockModelBuilder model, String namespace, String woodType) {
        return model
                .texture("wood", ResourceLocation.fromNamespaceAndPath(namespace, "block/stripped_" + woodType + "_log"))
                .texture("bark", ResourceLocation.fromNamespaceAndPath(namespace, "block/" + woodType + "_log"))
                .texture("top", ResourceLocation.fromNamespaceAndPath(namespace, "block/stripped_" + woodType + "_log_top"))
                .texture("particle", ResourceLocation.fromNamespaceAndPath(namespace, "block/stripped_" + woodType + "_log"));
    }

    // Why is this not accessible in Neo?
    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    private ResourceLocation key(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }

    private void horizontalBlockIgnoringProperties(Block block, ModelFile model, Property<?>... ignored) {
        horizontalBlockIgnoringProperties(block, model, 180, ignored);
    }

    private void horizontalBlockIgnoringProperties(Block block, ModelFile model, int angleOffset, Property<?>... ignored) {
        getVariantBuilder(block)
            .forAllStatesExcept(state -> ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + angleOffset) % 360)
                    .build(),
            ignored);
    }

    private void basicItemWithParent(Item item, ResourceLocation parent) {
        var id = key(item);
        itemModels().withExistingParent(id.toString(), parent)
                .texture("layer0", id.withPath("item/" + id.getPath()));
    }

    private BlockModelBuilder blockEntityRenderer(Block block, ResourceLocation particleTexture) {
        return models().getBuilder(key(block).toString())
                .parent(new ModelFile.UncheckedModelFile("builtin/entity"))
                .texture("particle", particleTexture)
                .guiLight(GuiLight.SIDE)
                .transforms() //Values copied from models/block/block.json, as we cannot use block/block as the parent
                .transform(ItemDisplayContext.GUI)
                    .rotation(30, 225, 0)
                    .translation(0, 0, 0)
                    .scale(0.625F)
                    .end()
                .transform(ItemDisplayContext.GROUND)
                    .rotation(0, 0, 0)
                    .translation(0, 3, 0)
                    .scale(0.25F)
                    .end()
                .transform(ItemDisplayContext.FIXED)
                    .rotation(0, 0, 0)
                    .translation(0, 0, 0)
                    .scale(0.5F)
                    .end()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
                    .rotation(75, 45, 0)
                    .translation(0, 2.5F, 0)
                    .scale(0.375F)
                    .end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
                    .rotation(0, 45, 0)
                    .translation(0, 0, 0)
                    .scale(0.4F)
                    .end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
                    .rotation(0, 225, 0)
                    .translation(0, 0, 0)
                    .scale(0.4F)
                    .end()
                .end();
    }
}
