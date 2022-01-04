package pokefenn.totemic.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.block.totem.TotemBaseBlock;
import pokefenn.totemic.init.ModBlocks;

public class TotemicBlockStateProvider extends BlockStateProvider {
    public TotemicBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Totemic.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for(TotemBaseBlock block: ModBlocks.getTotemBases().values()) {
            ResourceLocation blockName = block.getRegistryName();
            ModelFile blockModel = models().getExistingFile(new ResourceLocation(blockName.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + blockName.getPath()));
            //Block state
            horizontalBlock(block, blockModel);
            //Item model
            itemModels().withExistingParent(block.getRegistryName().toString(), blockModel.getLocation());
        }
    }
}
