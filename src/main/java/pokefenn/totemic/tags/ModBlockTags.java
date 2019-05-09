package pokefenn.totemic.tags;

import java.nio.file.Path;
import java.util.Collection;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.TagsProvider;
import net.minecraft.tags.BlockTags.Wrapper;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.GameData;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.init.ModBlocks;

public final class ModBlockTags {
    public static final Tag<Block> TOTEM_BASES = new Wrapper(new ResourceLocation(Totemic.MOD_ID, "totem_bases"));
    public static final Tag<Block> TOTEM_POLES = new Wrapper(new ResourceLocation(Totemic.MOD_ID, "totem_poles"));

    public static final class Provider extends TagsProvider<Block> {
        protected Provider(DataGenerator generator) {
            super(generator, GameData.getWrapperDefaulted(Block.class));
        }

        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        protected void registerTags() {
            getBuilder(TOTEM_BASES).addAll((Collection) ModBlocks.getTotemBases().values());
            getBuilder(TOTEM_POLES).addAll((Collection) ModBlocks.getTotemPoles().values());
        }

        @Override
        public String getName() {
            return "Totemic Block tags";
        }

        @Override
        protected void setCollection(TagCollection<Block> colectionIn) {
            // TODO Auto-generated method stub
        }

        @Override
        protected Path makePath(ResourceLocation id) {
            return this.generator.getOutputFolder().resolve("data/" + id.getNamespace() + "/tags/blocks/" + id.getPath() + ".json");
        }

    }
}
