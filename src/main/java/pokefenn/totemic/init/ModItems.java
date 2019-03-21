package pokefenn.totemic.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ObjectHolder;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.item.music.ItemFlute;
import pokefenn.totemic.item.music.ItemInfusedFlute;

@ObjectHolder(Totemic.MOD_ID)
public final class ModItems {
    public static final ItemFlute flute = null;
    public static final ItemInfusedFlute infused_flute = null;

    @SubscribeEvent
    public static void init(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
            new ItemFlute(new Properties().maxStackSize(1).group(Totemic.itemGroup)).setRegistryName("flute"),
            new ItemInfusedFlute(new Properties().maxStackSize(1).group(Totemic.itemGroup)).setRegistryName("infused_flute"),

            makeItemBlock(ModBlocks.oak_totem_base),
            makeItemBlock(ModBlocks.spruce_totem_base),
            makeItemBlock(ModBlocks.birch_totem_base),
            makeItemBlock(ModBlocks.jungle_totem_base),
            makeItemBlock(ModBlocks.acacia_totem_base),
            makeItemBlock(ModBlocks.dark_oak_totem_base),
            makeItemBlock(ModBlocks.cedar_totem_base)
        );
    }

    private static Item makeItemBlock(Block block) {
        return new ItemBlock(block, new Properties().group(Totemic.itemGroup)).setRegistryName(block.getRegistryName());
    }
}
