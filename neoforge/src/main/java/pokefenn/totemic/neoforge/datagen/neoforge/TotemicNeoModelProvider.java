package pokefenn.totemic.neoforge.datagen.neoforge;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.client.model.generators.loaders.ObjModelBuilder;
import net.neoforged.neoforge.common.util.TransformationHelper.TransformOrigin;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModItems;

public class TotemicNeoModelProvider extends ModelProvider {
    public TotemicNeoModelProvider(PackOutput output) {
        super(output, TotemicAPI.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators bm, ItemModelGenerators im) {
        // Generate Tipi block and item models here, since they're using Neo's OBJ loader
        var tipiTemplate = ModelTemplates.create("tipi", TextureSlot.PARTICLE)
                .extend()
                .customLoader(ObjModelBuilder::new, builder -> builder.modelLocation(modLocation("models/block/tipi.obj")))
                .rootTransforms(builder -> builder.origin(TransformOrigin.CORNER).translation(0, 0.95F, 0).scale(2.85F))
                .build();
        var tipiModel = BlockModelGenerators.plainVariant(tipiTemplate.create(ModBlocks.tipi.get(), TextureMapping.particle(Blocks.WHITE_WOOL), bm.modelOutput));
        bm.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(ModBlocks.tipi.get(), tipiModel));

        // use a separate item model rather than having the Tipi block model as parent,
        // because the transforms are not compatible with the above root transform
        var tipiItemTemplate = ModelTemplates.createItem("tipi", TextureSlot.PARTICLE)
                .extend()
                .customLoader(ObjModelBuilder::new, builder -> builder.modelLocation(modLocation("models/block/tipi.obj")))
                .transform(ItemDisplayContext.GUI, builder -> builder.rotation(30, 225, 0).translation(0, -2.5F, 0).scale(0.4F))
                .transform(ItemDisplayContext.GROUND, builder -> builder.scale(0.25F))
                .transform(ItemDisplayContext.FIXED, builder -> builder.translation(0, -2.5F, 0).scale(0.4F))
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, builder -> builder.rotation(75, 45, 0).translation(0, 0.05F, 0).scale(0.25F))
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND, builder -> builder.rotation(75, 45, 0).translation(0, 0.05F, 0).scale(0.25F))
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND, builder -> builder.rotation(0, 45, 0).scale(0.25F))
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND, builder -> builder.rotation(0, 225, 0).scale(0.25F))
                .build();
        var tipiItemModel = tipiItemTemplate.createWithSuffix(ModBlocks.tipi.get(), "_inventory", TextureMapping.particle(Blocks.WHITE_WOOL), bm.modelOutput);
        bm.registerSimpleItemModel(ModItems.tipi.get(), tipiItemModel);
    }

    @Override
    public String getName() {
        return super.getName() + " - Neo-specific";
    }
}
