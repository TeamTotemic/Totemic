package pokefenn.totemic.data;

import org.jetbrains.annotations.Nullable;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import pokefenn.totemic.init.ModBlockTags;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModItemTags;
import pokefenn.totemic.init.ModItems;

public final class TotemicItemTagsProvider extends ItemTagsProvider {
    public TotemicItemTagsProvider(DataGenerator pGenerator, BlockTagsProvider pBlockTagsProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, pBlockTagsProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        //Totemic tags
        tag(ModItemTags.FLUTES).add(ModItems.flute.get(), ModItems.infused_flute.get());

        copy(ModBlockTags.TOTEM_BASES, ModItemTags.TOTEM_BASES);
        copy(ModBlockTags.TOTEM_POLES, ModItemTags.TOTEM_POLES);
        copy(ModBlockTags.CEDAR_LOGS, ModItemTags.CEDAR_LOGS);

        //Minecraft tags
        tag(ItemTags.LOGS_THAT_BURN).addTag(ModItemTags.CEDAR_LOGS);
        tag(ItemTags.LEAVES).add(ModBlocks.cedar_leaves.get().asItem());
        tag(ItemTags.SAPLINGS).add(ModBlocks.cedar_sapling.get().asItem());
    }

    @Override
    public String getName() {
        return "Totemic Item tags";
    }
}
