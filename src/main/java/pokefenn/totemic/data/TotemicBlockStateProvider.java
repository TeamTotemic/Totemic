package pokefenn.totemic.data;

import java.util.Set;

import net.minecraft.client.renderer.block.model.BlockModel.GuiLight;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockModelBuilder.RootTransformBuilder.TransformOrigin;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.loaders.ObjModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import pokefenn.totemic.api.TotemWoodType;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.block.TipiBlock;
import pokefenn.totemic.block.totem.TotemBaseBlock;
import pokefenn.totemic.block.totem.TotemPoleBlock;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModItems;

public class TotemicBlockStateProvider extends BlockStateProvider {
    public TotemicBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, TotemicAPI.MOD_ID, exFileHelper);
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
        simpleBlock(ModBlocks.cedar_sapling.get(), models().withExistingParent(ModBlocks.cedar_sapling.getId().toString(), "block/cross").texture("cross", blockTexture(ModBlocks.cedar_sapling.get())).renderType("cutout"));
        simpleBlock(ModBlocks.drum.get(), models().getExistingFile(modLoc("drum")));
        simpleBlock(ModBlocks.wind_chime.get(), blockEntityRenderer(ModBlocks.wind_chime, mcLoc("block/white_terracotta"))
                .transforms()
                .transform(TransformType.THIRD_PERSON_RIGHT_HAND)
                    .rotation(75, 45, 0)
                    .translation(0, 1.25F, 0)
                    .scale(0.375F)
                    .end()
                .transform(TransformType.GUI)
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
        //signBlock(ModBlocks.cedar_sign.get(), ModBlocks.cedar_wall_sign.get(), cedarPlankTex);
        slabBlock(ModBlocks.cedar_slab.get(), ModBlocks.cedar_planks.getId(), cedarPlankTex, cedarPlankTex, cedarPlankTex);
        stairsBlock(ModBlocks.cedar_stairs.get(), cedarPlankTex);
        //doorBlock(...)
        //trapdoorBlock(...)
        simpleBlock(ModBlocks.potted_cedar_sapling.get(), models().singleTexture(ModBlocks.potted_cedar_sapling.getId().toString(), mcLoc("block/flower_pot_cross"), "plant", blockTexture(ModBlocks.cedar_sapling.get())).renderType("cutout"));
        simpleBlock(ModBlocks.totem_torch.get(), models().getExistingFile(modLoc("totem_torch")));
        horizontalBlockIgnoringProperties(ModBlocks.tipi.get(), models().getBuilder(ModBlocks.tipi.getId().toString())
                .customLoader(ObjModelBuilder::begin).modelLocation(modLoc("models/block/tipi.obj")).end()
                .texture("particle", mcLoc("block/white_wool"))
                .rootTransform()
                    .origin(TransformOrigin.CENTER)
                    .translation(0, 0.95F, 0)
                    .scale(2.85F)
                    .end(),
                0, //angle offset of 0, rotates the model by 180°
                TipiBlock.OCCUPIED);
        simpleBlock(ModBlocks.dummy_tipi.get(), models().withExistingParent(ModBlocks.dummy_tipi.getId().toString(), "block/air").texture("particle", mcLoc("block/white_wool")));

        //Item Blocks
        var im = itemModels();
        final Set<ResourceLocation> blocksWithCustomItemModel = Set.of(ModBlocks.cedar_sapling.getId(), ModBlocks.cedar_button.getId(), ModBlocks.cedar_fence.getId()/*, ModBlocks.cedar_sign.getId(), ModBlocks.cedar_wall_sign.getId()*/, ModBlocks.potted_cedar_sapling.getId(), ModBlocks.totem_torch.getId(), ModBlocks.tipi.getId(), ModBlocks.dummy_tipi.getId(), ModBlocks.totem_base.getId(), ModBlocks.totem_pole.getId());
        for(var blockO: ModBlocks.REGISTER.getEntries()) {
            if(blocksWithCustomItemModel.contains(blockO.getId()))
                continue;

            existingBlockItem(blockO);
        }

        im.singleTexture(ModBlocks.cedar_sapling.getId().toString(), mcLoc("item/generated"), "layer0", blockTexture(ModBlocks.cedar_sapling.get()));
        im.withExistingParent(ModBlocks.cedar_button.getId().toString(), "block/button_inventory").texture("texture", cedarPlankTex);
        im.withExistingParent(ModBlocks.cedar_fence.getId().toString(), "block/fence_inventory").texture("texture", cedarPlankTex);
        //im.basicItem(ModBlocks.cedar_sign.getId());
        im.withExistingParent(ModBlocks.totem_torch.getId().toString(), modLoc("block/totem_torch"))
                .transforms()
                .transform(TransformType.THIRD_PERSON_RIGHT_HAND)
                    .rotation(0, 45, 0)
                    .translation(0, 1.5F, 1.0F)
                    .scale(0.25F)
                    .end()
                .transform(TransformType.GUI)
                    .rotation(30, 225, 0)
                    .translation(0, -1.0F, 0)
                    .scale(0.625F)
                    .end()
                .transform(TransformType.FIXED)
                    .translation(0, 0, 0.25F)
                    .scale(0.5F)
                    .end()
                .end();
        //im.withExistingParent(ModBlocks.tipi.getId().toString(), modLoc("block/tipi"))
        //use a separate item model because the transforms are not compatible with the tipi's root transform
        im.getBuilder(ModBlocks.tipi.getId().toString())
                .customLoader(ObjModelBuilder::begin).modelLocation(modLoc("models/block/tipi.obj")).end()
                .transforms()
                .transform(TransformType.GUI)
                    .rotation(30, 225, 0)
                    .translation(0, -2.5F, 0)
                    .scale(0.4F)
                    .end()
                .transform(TransformType.GROUND)
                    .scale(0.25F)
                    .end()
                .transform(TransformType.FIXED)
                    .translation(0, -2.5F, 0)
                    .scale(0.4F)
                    .end()
                .transform(TransformType.THIRD_PERSON_RIGHT_HAND)
                    .rotation(75, 45, 0)
                    .translation(0, 0.05F, 0)
                    .scale(0.25F)
                    .end()
                .transform(TransformType.THIRD_PERSON_LEFT_HAND)
                    .rotation(75, 45, 0)
                    .translation(0, 0.05F, 0)
                    .scale(0.25F)
                    .end()
                .transform(TransformType.FIRST_PERSON_RIGHT_HAND)
                    .rotation(0, 45, 0)
                    .scale(0.25F)
                    .end()
                .transform(TransformType.FIRST_PERSON_LEFT_HAND)
                    .rotation(0, 225, 0)
                    .scale(0.25F)
                    .end()
                .end();
        im.withExistingParent(ModBlocks.totem_base.getId().toString(), modLoc("block/dynamic_totem_base"));
        im.withExistingParent(ModBlocks.totem_pole.getId().toString(), modLoc("block/dynamic_totem_pole"));

        //Items
        im.basicItem(ModItems.flute.getId());
        im.basicItem(ModItems.infused_flute.getId());
        im.basicItem(ModItems.jingle_dress.getId());
        basicItemWithParent(ModItems.rattle, mcLoc("item/handheld"));
        im.basicItem(ModItems.eagle_bone_whistle.getId());
        basicItemWithParent(ModItems.totem_whittling_knife, mcLoc("item/handheld"));
        basicItemWithParent(ModItems.totemic_staff, mcLoc("item/handheld"));
        basicItemWithParent(ModItems.ceremony_cheat, mcLoc("item/handheld"));
        im.withExistingParent(ModItems.buffalo_spawn_egg.getId().toString(), "item/template_spawn_egg");
        im.withExistingParent(ModItems.bald_eagle_spawn_egg.getId().toString(), "item/template_spawn_egg");
        im.withExistingParent(ModItems.baykok_spawn_egg.getId().toString(), "item/template_spawn_egg");
        im.basicItem(ModItems.buffalo_meat.getId());
        im.basicItem(ModItems.cooked_buffalo_meat.getId());
        im.basicItem(ModItems.buffalo_tooth.getId());
        im.basicItem(ModItems.buffalo_hide.getId());
        im.basicItem(ModItems.iron_bells.getId());
        im.basicItem(ModItems.eagle_bone.getId());
        im.basicItem(ModItems.eagle_feather.getId());
        var baykokBow = im.withExistingParent(ModItems.baykok_bow.getId().toString(), "item/bow").texture("layer0", modLoc("item/baykok_bow"));
        baykokBow.override().predicate(mcLoc("pulling"), 1).model(im.basicItem(modLoc("baykok_bow_pulling_0")).parent(baykokBow)).end();
        baykokBow.override().predicate(mcLoc("pulling"), 1).predicate(mcLoc("pull"), 0.65F).model(im.basicItem(modLoc("baykok_bow_pulling_1")).parent(baykokBow)).end();
        baykokBow.override().predicate(mcLoc("pulling"), 1).predicate(mcLoc("pull"), 0.9F).model(im.basicItem(modLoc("baykok_bow_pulling_2")).parent(baykokBow)).end();
        im.basicItem(modLoc("totempedia"));
        var medBagOpen = im.basicItem(modLoc("medicine_bag_open"));
        var medBag = im.basicItem(ModItems.medicine_bag.getId()).override().predicate(modLoc("open"), 1).model(medBagOpen).end();
        im.getBuilder(ModItems.creative_medicine_bag.getId().toString()).parent(medBag).override().predicate(modLoc("open"), 1).model(medBagOpen).end();

        totemPoleModels();
    }

    private void totemPoleModels() {
        //Block states
        horizontalBlockIgnoringProperties(ModBlocks.totem_pole.get(), models().getExistingFile(modLoc("dynamic_totem_pole")), TotemPoleBlock.WATERLOGGED);
        horizontalBlockIgnoringProperties(ModBlocks.totem_base.get(), models().getExistingFile(modLoc("dynamic_totem_base")), TotemBaseBlock.WATERLOGGED);

        for(var woodType: TotemWoodType.getWoodTypes()) {
            //Model for each wood type
            var poleModel = models().getBuilder("totemic:" + woodType.getName() + "_totem_pole");
            setTotemTextures(poleModel, woodType);

            var baseModel = models().withExistingParent("totemic:" + woodType.getName() + "_totem_base", modLoc("totem_base"));
            setTotemTextures(baseModel, woodType);
        }
    }

    private BlockModelBuilder setTotemTextures(BlockModelBuilder model, TotemWoodType woodType) {
        return model
                .texture("wood", woodType.getWoodTexture())
                .texture("bark", woodType.getBarkTexture())
                .texture("top", woodType.getTopTexture())
                .texture("particle", woodType.getParticleTexture());
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

    private void existingBlockItem(RegistryObject<? extends Block> block) {
        simpleBlockItem(block.get(), models().getExistingFile(block.getId()));
    }

    private void basicItemWithParent(RegistryObject<? extends Item> item, ResourceLocation parent) {
        var id = item.getId();
        itemModels().withExistingParent(id.toString(), parent)
                .texture("layer0", new ResourceLocation(id.getNamespace(), "item/" + id.getPath()));
    }

    private BlockModelBuilder blockEntityRenderer(RegistryObject<? extends Block> block, ResourceLocation particleTexture) {
        return models().getBuilder(block.getId().toString())
                .parent(new ModelFile.UncheckedModelFile("builtin/entity"))
                .texture("particle", particleTexture)
                .guiLight(GuiLight.SIDE)
                .transforms() //Values copied from models/block/block.json, as we cannot use block/block as the parent
                .transform(TransformType.GUI)
                    .rotation(30, 225, 0)
                    .translation(0, 0, 0)
                    .scale(0.625F)
                    .end()
                .transform(TransformType.GROUND)
                    .rotation(0, 0, 0)
                    .translation(0, 3, 0)
                    .scale(0.25F)
                    .end()
                .transform(TransformType.FIXED)
                    .rotation(0, 0, 0)
                    .translation(0, 0, 0)
                    .scale(0.5F)
                    .end()
                .transform(TransformType.THIRD_PERSON_RIGHT_HAND)
                    .rotation(75, 45, 0)
                    .translation(0, 2.5F, 0)
                    .scale(0.375F)
                    .end()
                .transform(TransformType.FIRST_PERSON_RIGHT_HAND)
                    .rotation(0, 45, 0)
                    .translation(0, 0, 0)
                    .scale(0.4F)
                    .end()
                .transform(TransformType.FIRST_PERSON_LEFT_HAND)
                    .rotation(0, 225, 0)
                    .translation(0, 0, 0)
                    .scale(0.4F)
                    .end()
                .end();
    }
}
