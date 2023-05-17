package pokefenn.totemic.handler;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.init.ModBlocks;

public class RemapHandler {
    @SubscribeEvent
    public static void onMissingMappings(MissingMappingsEvent event) {
        remapBlocksAndItems(event, (key, remapper) -> {
            if(key.getPath().endsWith("_totem_base"))
                remapper.accept(ModBlocks.totem_base.get());
            else if(key.getPath().endsWith("_totem_pole") || key.getPath().contains("_totem_pole_"))
                remapper.accept(ModBlocks.totem_pole.get());
        });
    }

    //Helper function to remap Blocks and their corresponding Items at once
    private static void remapBlocksAndItems(MissingMappingsEvent event, BiConsumer<ResourceLocation, Consumer<Block>> remapFunction) {
        for(var mapping: event.getMappings(ForgeRegistries.Keys.BLOCKS, TotemicAPI.MOD_ID)) {
            remapFunction.accept(mapping.getKey(), block -> {
                mapping.remap(block);
                Totemic.logger.debug("Remapping block {} -> {}", mapping.getKey(), block);
            });
        }
        for(var mapping: event.getMappings(ForgeRegistries.Keys.ITEMS, TotemicAPI.MOD_ID)) {
            remapFunction.accept(mapping.getKey(), block -> {
                mapping.remap(block.asItem());
                Totemic.logger.debug("Remapping item {} -> {}", mapping.getKey(), block.asItem());
            });
        }
    }
}
