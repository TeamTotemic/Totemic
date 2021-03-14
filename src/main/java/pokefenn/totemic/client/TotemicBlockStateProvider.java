package pokefenn.totemic.client;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
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
            horizontalBlock(block, models().getExistingFile(blockTexture(block)));
        }
    }
}
