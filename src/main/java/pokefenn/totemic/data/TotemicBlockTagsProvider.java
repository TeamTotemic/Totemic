package pokefenn.totemic.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.TotemicBlockTags;
import pokefenn.totemic.init.ModBlocks;

public final class TotemicBlockTagsProvider extends BlockTagsProvider {
    public TotemicBlockTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, TotemicAPI.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags() {
        //Totemic tags
        ModBlocks.getTotemBases().values().stream()
                .map(RegistryObject::get)
                .forEach(tag(TotemicBlockTags.TOTEM_BASES)::add);
        ModBlocks.getTotemPoles().values().stream()
                .map(RegistryObject::get)
                .forEach(tag(TotemicBlockTags.TOTEM_POLES)::add);
        tag(TotemicBlockTags.CEDAR_LOGS).add(ModBlocks.cedar_log.get(), ModBlocks.stripped_cedar_log.get(), ModBlocks.cedar_wood.get(), ModBlocks.stripped_cedar_wood.get());

        //Minecraft and Forge tags
        tag(BlockTags.PLANKS).add(ModBlocks.cedar_planks.get());
        tag(BlockTags.WOODEN_BUTTONS).add(ModBlocks.cedar_button.get());
        tag(BlockTags.WOODEN_STAIRS).add(ModBlocks.cedar_stairs.get());
        tag(BlockTags.WOODEN_SLABS).add(ModBlocks.cedar_slab.get());
        tag(BlockTags.WOODEN_FENCES).add(ModBlocks.cedar_fence.get());
        tag(BlockTags.FENCE_GATES).add(ModBlocks.cedar_fence_gate.get());
        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.cedar_pressure_plate.get());
        tag(BlockTags.LOGS_THAT_BURN).addTag(TotemicBlockTags.CEDAR_LOGS);
        tag(BlockTags.LEAVES).add(ModBlocks.cedar_leaves.get());
        tag(BlockTags.SAPLINGS).add(ModBlocks.cedar_sapling.get());
        tag(BlockTags.FLOWER_POTS).add(ModBlocks.potted_cedar_sapling.get());
        tag(BlockTags.MINEABLE_WITH_AXE).addTags(TotemicBlockTags.TOTEM_BASES, TotemicBlockTags.TOTEM_POLES).add(ModBlocks.drum.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.wind_chime.get());
        tag(BlockTags.MINEABLE_WITH_HOE).add(ModBlocks.cedar_leaves.get());

        tag(Tags.Blocks.FENCE_GATES_WOODEN).add(ModBlocks.cedar_fence_gate.get());
    }

    @Override
    public String getName() {
        return "Totemic Block tags";
    }
}