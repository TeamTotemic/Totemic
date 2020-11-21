package pokefenn.totemic.tags;

import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraftforge.common.data.ExistingFileHelper;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.init.ModBlocks;

public final class ModBlockTags {
    public static final INamedTag<Block> TOTEM_BASES = BlockTags.makeWrapperTag("totemic:totem_bases");
    public static final INamedTag<Block> TOTEM_POLES = BlockTags.makeWrapperTag("totemic:totem_poles");

    public static final class Provider extends BlockTagsProvider {
        public Provider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
            super(generator, Totemic.MOD_ID, existingFileHelper);
        }

        @Override
        protected void registerTags() {
            ModBlocks.getTotemBases().values().forEach(getOrCreateBuilder(TOTEM_BASES)::add);
            ModBlocks.getTotemPoles().values().forEach(getOrCreateBuilder(TOTEM_POLES)::add);
        }

        @Override
        public String getName() {
            return "Totemic Block tags";
        }
    }
}
