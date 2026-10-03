package pokefenn.totemic.neoforge.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Blocks;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.client.renderer.item.properties.IsMedicineBagOpen;
import pokefenn.totemic.client.renderer.special.WindChimeSpecialRenderer;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModItems;

public class TotemicModelProvider extends ModelProvider {
    public TotemicModelProvider(PackOutput output) {
        super(output, TotemicAPI.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators bm, ItemModelGenerators im) {
        //Blocks
        bm.woodProvider(ModBlocks.cedar_log.get())
            .logWithHorizontal(ModBlocks.cedar_log.get())
            .wood(ModBlocks.cedar_wood.get());
        bm.woodProvider(ModBlocks.stripped_cedar_log.get())
            .logWithHorizontal(ModBlocks.stripped_cedar_log.get())
            .wood(ModBlocks.stripped_cedar_wood.get());
        bm.createTrivialBlock(ModBlocks.cedar_leaves.get(), TexturedModel.LEAVES);
        TexturedModel.LEAVES.createWithSuffix(ModBlocks.cedar_leaves.get(), "_opaque", bm.modelOutput);
        bm.createPlantWithDefaultItem(ModBlocks.cedar_sapling.get(), ModBlocks.potted_cedar_sapling.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        bm.createNonTemplateModelBlock(ModBlocks.drum.get());
        createWindChime(bm);
        bm.family(ModBlocks.cedar_planks.get()).generateFor(TotemicRecipeProvider.CEDAR_FAMILY);
        bm.createHangingSign(ModBlocks.stripped_cedar_log.get(), ModBlocks.cedar_hanging_sign.get(), ModBlocks.cedar_wall_hanging_sign.get());
        bm.createNonTemplateModelBlock(ModBlocks.totem_torch.get()); // TODO: Transformations for Totem Torch item model
        bm.createAirLikeBlock(ModBlocks.dummy_tipi.get(), TextureMapping.getBlockTexture(Blocks.WHITE_WOOL));
        // dynamic_totem_pole and dynamic_totem_base are in the neoforge resources. AFAIK, Fabric allows hooking into model loading without requiring a model JSON to be present.
        bm.createNonTemplateHorizontalBlock(ModBlocks.totem_pole.get());
        bm.createNonTemplateHorizontalBlock(ModBlocks.totem_base.get());
        // createTotemWoodTypes(bm); TODO: Totem wood type models

        //Items
        im.generateFlatItem(ModItems.flute.get(), ModelTemplates.FLAT_ITEM);
        im.generateFlatItem(ModItems.infused_flute.get(), ModelTemplates.FLAT_ITEM);
        im.generateFlatItem(ModItems.jingle_dress.get(), ModelTemplates.FLAT_ITEM);
        im.generateFlatItem(ModItems.rattle.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        im.generateFlatItem(ModItems.eagle_bone_whistle.get(), ModelTemplates.FLAT_ITEM);
        im.generateFlatItem(ModItems.totem_whittling_knife.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        im.generateFlatItem(ModItems.totemic_staff.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        im.generateFlatItem(ModItems.ceremony_cheat.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        // im.spawnEggItem(ModItems.buffalo_spawn_egg.get());
        // im.spawnEggItem(ModItems.bald_eagle_spawn_egg.get()); TODO: Spawn eggs
        // im.spawnEggItem(ModItems.baykok_spawn_egg.get());
        im.generateFlatItem(ModItems.buffalo_meat.get(), ModelTemplates.FLAT_ITEM);
        im.generateFlatItem(ModItems.cooked_buffalo_meat.get(), ModelTemplates.FLAT_ITEM);
        im.generateFlatItem(ModItems.buffalo_tooth.get(), ModelTemplates.FLAT_ITEM);
        im.generateFlatItem(ModItems.buffalo_hide.get(), ModelTemplates.FLAT_ITEM);
        im.generateFlatItem(ModItems.iron_bells.get(), ModelTemplates.FLAT_ITEM);
        im.generateFlatItem(ModItems.eagle_bone.get(), ModelTemplates.FLAT_ITEM);
        im.generateFlatItem(ModItems.eagle_feather.get(), ModelTemplates.FLAT_ITEM);
        im.generateBow(ModItems.baykok_bow.get());
        ModelTemplates.FLAT_ITEM.create(modLocation("totempedia"), TextureMapping.layer0(new Material(modLocation("item/totempedia"))), im.modelOutput);
        createMedicineBag(im);
    }

    private void createWindChime(BlockModelGenerators bm) {
        bm.createParticleOnlyBlock(ModBlocks.wind_chime.get(), Blocks.WHITE_TERRACOTTA);
        var template = ModelTemplates.createItem("template_wind_chime", TextureSlot.PARTICLE)
                .extend()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, builder -> builder.rotation(75, 45, 0).translation(0, 1.25F, 0).scale(0.375F))
                .transform(ItemDisplayContext.GUI, builder -> builder.rotation(30, 225, 0).translation(0, 0, 0).scale(0.875F))
                .build();
        var itemModelBase = template.create(ModItems.wind_chime.get(), TextureMapping.particle(Blocks.WHITE_TERRACOTTA), bm.modelOutput);
        var itemModel = ItemModelUtils.specialModel(itemModelBase, new WindChimeSpecialRenderer.Unbaked());
        bm.itemModelOutput.accept(ModItems.wind_chime.get(), itemModel);
    }

//    private void createTotemWoodTypes(BlockModelGenerators bm) {
//        //Generate Totem Base and Totem Pole model files for each wood type
//        ModContent.WOOD_TYPES.getEntries().forEach(woodType -> {
//            var woodTypeId = woodType.getRegistryName();
//            var namespace = woodTypeId.getPath().equals("cedar") ? "totemic" : "minecraft";
//
//            var poleModel = models().getBuilder(woodTypeId.toString() + "_totem_pole"); //the pole model has no parent, it only specifies the textures
//            var baseModel = models().withExistingParent(woodTypeId.toString() + "_totem_base", modLoc("totem_base"));
//            setTotemTextures(poleModel, namespace, woodTypeId.getPath());
//            setTotemTextures(baseModel, namespace, woodTypeId.getPath());
//        });
//    }
//
//    private BlockModelBuilder setTotemTextures(BlockModelBuilder model, String namespace, String woodType) {
//        return model
//                .texture("wood", Identifier.fromNamespaceAndPath(namespace, "block/stripped_" + woodType + "_log"))
//                .texture("bark", Identifier.fromNamespaceAndPath(namespace, "block/" + woodType + "_log"))
//                .texture("top", Identifier.fromNamespaceAndPath(namespace, "block/stripped_" + woodType + "_log_top"))
//                .texture("particle", Identifier.fromNamespaceAndPath(namespace, "block/stripped_" + woodType + "_log"));
//    }

    private void createMedicineBag(ItemModelGenerators im) {
        var medBagModel = ItemModelUtils.plainModel(im.createFlatItemModel(ModItems.medicine_bag.get(), ModelTemplates.FLAT_ITEM));
        var openMedBagModel = ItemModelUtils.plainModel(im.createFlatItemModel(ModItems.medicine_bag.get(), "_open", ModelTemplates.FLAT_ITEM));
        im.itemModelOutput.accept(ModItems.medicine_bag.get(),
                ItemModelUtils.conditional(new IsMedicineBagOpen(), openMedBagModel, medBagModel));
        im.itemModelOutput.copy(ModItems.medicine_bag.get(), ModItems.creative_medicine_bag.get());
    }
}
