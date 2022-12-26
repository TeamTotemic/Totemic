package pokefenn.totemic.data;

import org.jetbrains.annotations.Nullable;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import pokefenn.totemic.api.TotemicBlockTags;
import pokefenn.totemic.api.TotemicItemTags;
import pokefenn.totemic.init.ModItems;

public final class TotemicItemTagsProvider extends ItemTagsProvider {
    public TotemicItemTagsProvider(DataGenerator pGenerator, BlockTagsProvider pBlockTagsProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, pBlockTagsProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        //Totemic tags
        copy(TotemicBlockTags.TOTEM_BASES, TotemicItemTags.TOTEM_BASES);
        copy(TotemicBlockTags.TOTEM_POLES, TotemicItemTags.TOTEM_POLES);
        copy(TotemicBlockTags.CEDAR_LOGS, TotemicItemTags.CEDAR_LOGS);

        //Minecraft and Forge tags
        tag(Tags.Items.LEATHER).add(ModItems.buffalo_hide.get());
        tag(Tags.Items.TOOLS_BOWS).add(ModItems.baykok_bow.get());

        copy(BlockTags.PLANKS, ItemTags.PLANKS);
        copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        copy(BlockTags.LEAVES, ItemTags.LEAVES);
        copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);

        copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);
    }

    @Override
    public String getName() {
        return "Totemic Item tags";
    }
}
