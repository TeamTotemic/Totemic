package pokefenn.totemic.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.init.ModBlockTags;
import pokefenn.totemic.init.ModBlocks;

public final class TotemicBlockTagsProvider extends BlockTagsProvider {
    public TotemicBlockTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Totemic.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        ModBlocks.getTotemBases().values().forEach(tag(ModBlockTags.TOTEM_BASES)::add);
        ModBlocks.getTotemPoles().values().forEach(tag(ModBlockTags.TOTEM_POLES)::add);
    }

    @Override
    public String getName() {
        return "Totemic Block tags";
    }
}