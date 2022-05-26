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
import pokefenn.totemic.block.totem.TotemBaseBlock;
import pokefenn.totemic.block.totem.TotemPoleBlock;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModContent;

public class TotemicBlockStateProvider extends BlockStateProvider {
    public TotemicBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Totemic.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //TODO: It would be nice if those models and block states could be loaded dynamically rather than generated
        for(TotemBaseBlock block: ModBlocks.getTotemBases().values()) {
            ResourceLocation blockName = block.getRegistryName();
            ModelFile blockModel = models().getExistingFile(new ResourceLocation(blockName.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + blockName.getPath()));
            //Block state
            waterloggedHorizontalBlock(block, blockModel);
            //Item model
            itemModels().getBuilder(block.getRegistryName().toString()).parent(blockModel);
        }
        for(TotemPoleBlock block: ModBlocks.getTotemPoles().values()) {
            ResourceLocation blockName = block.getRegistryName();
            ModelFile parentModel = models().getExistingFile(new ResourceLocation(blockName.getNamespace(), ModelProvider.BLOCK_FOLDER + "/totem_pole_" + block.effect.getRegistryName().getPath()));
            BlockModelBuilder blockModel = models().getBuilder(blockName.getNamespace() + ":" + ModelProvider.BLOCK_FOLDER + "/" + blockName.getPath())
                    .parent(parentModel)
                    .texture("wood", block.woodType.getWoodTexture())
                    .texture("bark", block.woodType.getBarkTexture())
                    .texture("top", block.woodType.getTopTexture())
                    .texture("particle", block.woodType.getParticleTexture());

            //Block state
            if(block.effect == ModContent.buffalo) //FIXME: Buffalo Totem Pole model is still rotated incorrectly
                waterloggedHorizontalBlock(block, blockModel, 270);
            else
                waterloggedHorizontalBlock(block, blockModel);
            //Item model
            itemModels().getBuilder(block.getRegistryName().toString()).parent(blockModel);
        }
    }

    // The same as BlockStateProvider#horizontalBlock, but ignoring the Waterlogged property
    private void waterloggedHorizontalBlock(Block block, ModelFile model, int angleOffset) {
        getVariantBuilder(block)
            .forAllStatesExcept(state -> ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + angleOffset) % 360)
                    .build(),
            BlockStateProperties.WATERLOGGED);
    }

    private void waterloggedHorizontalBlock(Block block, ModelFile model) {
        waterloggedHorizontalBlock(block, model, 180);
    }
}
