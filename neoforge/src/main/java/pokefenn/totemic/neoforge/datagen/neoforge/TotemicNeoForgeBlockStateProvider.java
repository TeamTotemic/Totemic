package pokefenn.totemic.neoforge.datagen.neoforge;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.client.model.generators.loaders.ObjModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.util.TransformationHelper.TransformOrigin;
import pokefenn.totemic.block.TipiBlock;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.neoforge.datagen.TotemicBlockStateProvider;

public class TotemicNeoForgeBlockStateProvider extends TotemicBlockStateProvider {
    public TotemicNeoForgeBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // Generate Tipi block and item models here, since they're using Neo's OBJ loader
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

        // use a separate item model rather than having the Tipi block model as parent,
        // because the transforms are not compatible with the above root transform
        var im = itemModels();
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
    }
}
