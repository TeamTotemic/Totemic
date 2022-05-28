package pokefenn.totemic.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemWoodType;
import pokefenn.totemic.block.totem.TotemBaseBlock;
import pokefenn.totemic.block.totem.TotemPoleBlock;
import pokefenn.totemic.init.ModBlocks;

public class TotemicBlockStateProvider extends BlockStateProvider {
    public TotemicBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Totemic.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //TODO: It would be nice if those models and block states could be loaded dynamically rather than generated
        ModelFile totemBaseModel = models().getExistingFile(new ResourceLocation(Totemic.MOD_ID, ModelProvider.BLOCK_FOLDER + "/totem_base"));
        for(TotemBaseBlock block: ModBlocks.getTotemBases().values()) {
            ResourceLocation blockName = block.getRegistryName();

            //Block model
            BlockModelBuilder blockModel = models().getBuilder(blockName.toString()).parent(totemBaseModel);
            setTextures(blockModel, block.woodType);

            //Block state
            waterloggedHorizontalBlock(block, blockModel);
            //Item model
            itemModels().getBuilder(block.getRegistryName().toString()).parent(blockModel);
        }
        for(TotemPoleBlock block: ModBlocks.getTotemPoles().values()) {
            ResourceLocation blockName = block.getRegistryName();
            ResourceLocation effectName = block.effect.getRegistryName();
            ModelFile parentModel = models().getExistingFile(new ResourceLocation(effectName.getNamespace(), ModelProvider.BLOCK_FOLDER + "/totem_pole_" + effectName.getPath()));

            //Block model
            BlockModelBuilder blockModel = models().getBuilder(blockName.toString()).parent(parentModel);
            setTextures(blockModel, block.woodType);

            //Block state
            waterloggedHorizontalBlock(block, blockModel);
            //Item model
            itemModels().getBuilder(block.getRegistryName().toString()).parent(blockModel);
        }
    }

    private BlockModelBuilder setTextures(BlockModelBuilder model, TotemWoodType woodType) {
        return model
                .texture("wood", woodType.getWoodTexture())
                .texture("bark", woodType.getBarkTexture())
                .texture("top", woodType.getTopTexture())
                .texture("particle", woodType.getParticleTexture());
    }

    // The same as BlockStateProvider#horizontalBlock, but ignoring the Waterlogged property
    private void waterloggedHorizontalBlock(Block block, ModelFile model) {
        getVariantBuilder(block)
            .forAllStatesExcept(state -> ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
                    .build(),
            BlockStateProperties.WATERLOGGED);
    }
}
