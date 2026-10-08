package pokefenn.totemic.neoforge.datagen;

import java.util.Set;
import java.util.stream.Stream;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.client.renderer.item.properties.IsMedicineBagOpen;
import pokefenn.totemic.client.renderer.special.WindChimeSpecialRenderer;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModContent;
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
        // The totem_pole and totem_base block state JSONs are not generated
        createTotemWoodTypes(bm);

        //Items
        im.generateFlatItem(ModItems.flute.get(), ModelTemplates.FLAT_ITEM);
        im.generateFlatItem(ModItems.infused_flute.get(), ModelTemplates.FLAT_ITEM);
        im.generateFlatItem(ModItems.jingle_dress.get(), ModelTemplates.FLAT_ITEM);
        im.generateFlatItem(ModItems.rattle.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        im.generateFlatItem(ModItems.eagle_bone_whistle.get(), ModelTemplates.FLAT_ITEM);
        im.generateFlatItem(ModItems.totem_whittling_knife.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        im.generateFlatItem(ModItems.totemic_staff.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        im.generateFlatItem(ModItems.ceremony_cheat.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        im.generateFlatItem(ModItems.buffalo_spawn_egg.get(), ModelTemplates.FLAT_ITEM);
        im.generateFlatItem(ModItems.bald_eagle_spawn_egg.get(), ModelTemplates.FLAT_ITEM); // TODO: Spawn egg textures
        im.generateFlatItem(ModItems.baykok_spawn_egg.get(), ModelTemplates.FLAT_ITEM);
        im.generateFlatItem(ModItems.buffalo_meat.get(), ModelTemplates.FLAT_ITEM);
        im.generateFlatItem(ModItems.cooked_buffalo_meat.get(), ModelTemplates.FLAT_ITEM);
        im.generateFlatItem(ModItems.buffalo_tooth.get(), ModelTemplates.FLAT_ITEM);
        im.generateFlatItem(ModItems.buffalo_hide.get(), ModelTemplates.FLAT_ITEM);
        im.generateFlatItem(ModItems.iron_bells.get(), ModelTemplates.FLAT_ITEM);
        im.generateFlatItem(ModItems.eagle_bone.get(), ModelTemplates.FLAT_ITEM);
        im.generateFlatItem(ModItems.eagle_feather.get(), ModelTemplates.FLAT_ITEM);
        im.createFlatItemModel(ModItems.baykok_bow.get(), ModelTemplates.BOW);
        im.generateBow(ModItems.baykok_bow.get()); // generates the pulling models and the client item
        ModelTemplates.FLAT_ITEM.create(modLocation("totempedia"), TextureMapping.layer0(new Material(modLocation("item/totempedia"))), im.modelOutput);
        createMedicineBag(im);
        // TODO: Special item models for Totem Base and Pole
    }

    private void createWindChime(BlockModelGenerators bm) {
        bm.createParticleOnlyBlock(ModBlocks.wind_chime.get(), Blocks.WHITE_TERRACOTTA);
        var template = ModelTemplates.create(TextureSlot.PARTICLE)
                .extend()
                .transform(ItemDisplayContext.GUI, builder -> builder.rotation(30, 225, 0).translation(0, 0, 0).scale(0.875F))
                .transform(ItemDisplayContext.GROUND, builder -> builder.translation(0, 3, 0).scale(0.375F))
                .transform(ItemDisplayContext.FIXED, builder -> builder.scale(0.75F))
                .transform(ItemDisplayContext.ON_SHELF, builder -> builder.rotation(0, 180, 0).scale(1.5F))
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, builder -> builder.rotation(75, 45, 0).translation(0, 1.25F, 0).scale(0.375F))
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND, builder -> builder.rotation(0, 45, 0).scale(0.4F))
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND, builder -> builder.rotation(0, 225, 0).scale(0.4F))
                .build();
        var itemModelBase = template.create(ModItems.wind_chime.get(), TextureMapping.particle(Blocks.WHITE_TERRACOTTA), bm.modelOutput);
        var itemModel = ItemModelUtils.specialModel(itemModelBase, new WindChimeSpecialRenderer.Unbaked());
        bm.itemModelOutput.accept(ModItems.wind_chime.get(), itemModel);
    }

    private void createTotemWoodTypes(BlockModelGenerators bm) {
        var woodSlot = TextureSlot.create("wood");
        var barkSlot = TextureSlot.create("bark");
        var topSlot = TextureSlot.create("top");
        var particleSlot = TextureSlot.PARTICLE;
        var poleTemplate = ModelTemplates.create(woodSlot, barkSlot, topSlot, particleSlot);
        var baseTemplate = ModelTemplates.create("totemic:totem_base", woodSlot, barkSlot, topSlot, particleSlot);
        //Generate Totem Base and Totem Pole model files for each wood type
        ModContent.WOOD_TYPES.getEntries().forEach(woodType -> {
            var woodTypeName = woodType.getRegistryName().getPath();
            var namespace = (woodType == ModContent.cedar.get()) ? "totemic" : "minecraft";
            var textures = new TextureMapping()
                    .put(woodSlot, new Material(Identifier.fromNamespaceAndPath(namespace, "block/stripped_" + woodTypeName + "_log")))
                    .put(barkSlot, new Material(Identifier.fromNamespaceAndPath(namespace, "block/" + woodTypeName + "_log")))
                    .put(topSlot, new Material(Identifier.fromNamespaceAndPath(namespace, "block/stripped_" + woodTypeName + "_log_top")))
                    .put(particleSlot, new Material(Identifier.fromNamespaceAndPath(namespace, "block/stripped_" + woodTypeName + "_log")));
            poleTemplate.create(Totemic.resloc("block/" + woodTypeName + "_totem_pole"), textures, bm.modelOutput);
            baseTemplate.create(Totemic.resloc("block/" + woodTypeName + "_totem_base"), textures, bm.modelOutput);
        });
    }

    private void createMedicineBag(ItemModelGenerators im) {
        var medBagModel = ItemModelUtils.plainModel(im.createFlatItemModel(ModItems.medicine_bag.get(), ModelTemplates.FLAT_ITEM));
        var openMedBagModel = ItemModelUtils.plainModel(im.createFlatItemModel(ModItems.medicine_bag.get(), "_open", ModelTemplates.FLAT_ITEM));
        im.itemModelOutput.accept(ModItems.medicine_bag.get(),
                ItemModelUtils.conditional(new IsMedicineBagOpen(), openMedBagModel, medBagModel));
        im.itemModelOutput.copy(ModItems.medicine_bag.get(), ModItems.creative_medicine_bag.get());
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        // The block states for the Tipi are generated in TotemicNeoModelProvider, and those for the Totem Base and Pole are not generated
        var nonGeneratedBlockStates = Set.of(ModBlocks.tipi.get(), ModBlocks.totem_base.get(), ModBlocks.totem_pole.get());
        return super.getKnownBlocks().filter(holder -> !nonGeneratedBlockStates.contains(holder.value()));
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        // The Tipi client item is generated in TotemicNeoModelProvider
        return super.getKnownItems().filter(holder -> holder.value() != ModItems.tipi.get());
    }
}
