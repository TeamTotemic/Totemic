package pokefenn.totemic.tags;

import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags.Wrapper;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.init.ModBlocks;

public final class ModBlockTags {
    public static final Tag<Block> TOTEM_BASES = new Wrapper(new ResourceLocation(Totemic.MOD_ID, "totem_bases"));
    public static final Tag<Block> TOTEM_POLES = new Wrapper(new ResourceLocation(Totemic.MOD_ID, "totem_poles"));

    public static final class Provider extends BlockTagsProvider {
        public Provider(DataGenerator generator) {
            super(generator);
        }

        @Override
        protected void registerTags() {
            ModBlocks.getTotemBases().values().forEach(getBuilder(TOTEM_BASES)::add);
            ModBlocks.getTotemPoles().values().forEach(getBuilder(TOTEM_POLES)::add);
        }

        @Override
        public String getName() {
            return "Totemic Block tags";
        }
    }
}
