package pokefenn.totemic.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.init.ModBlockTags;
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
                .forEach(tag(ModBlockTags.TOTEM_BASES)::add);
        ModBlocks.getTotemPoles().values().stream()
                .map(RegistryObject::get)
                .forEach(tag(ModBlockTags.TOTEM_POLES)::add);
        tag(ModBlockTags.CEDAR_LOGS).add(ModBlocks.cedar_log.get(), ModBlocks.stripped_cedar_log.get(), ModBlocks.cedar_wood.get(), ModBlocks.stripped_cedar_wood.get());

        //Minecraft tags
        tag(BlockTags.LOGS_THAT_BURN).addTag(ModBlockTags.CEDAR_LOGS);
        tag(BlockTags.LEAVES).add(ModBlocks.cedar_leaves.get());
        tag(BlockTags.SAPLINGS).add(ModBlocks.cedar_sapling.get());
        tag(BlockTags.MINEABLE_WITH_AXE).addTags(ModBlockTags.TOTEM_BASES, ModBlockTags.TOTEM_POLES).add(ModBlocks.drum.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.wind_chime.get());
        tag(BlockTags.MINEABLE_WITH_HOE).add(ModBlocks.cedar_leaves.get());
    }

    @Override
    public String getName() {
        return "Totemic Block tags";
    }
}