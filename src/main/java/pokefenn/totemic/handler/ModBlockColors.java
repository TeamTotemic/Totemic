package pokefenn.totemic.handler;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import pokefenn.totemic.block.totem.TotemPoleBlock;
import pokefenn.totemic.init.ModBlocks;

public class ModBlockColors {
    @SubscribeEvent
    public static void registerBlockColors(ColorHandlerEvent.Block event) {
        event.getBlockColors().register((state, tintGetter, pos, tintIndex) -> TotemPoleBlock.getBlockColor(tintIndex),
                ModBlocks.getTotemPoles().values().toArray(Block[]::new));
    }

    @SubscribeEvent
    public static void registerItemColors(ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, tintIndex) -> TotemPoleBlock.getBlockColor(tintIndex),
                ModBlocks.getTotemPoles().values().toArray(ItemLike[]::new));
    }
}
