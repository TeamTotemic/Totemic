package pokefenn.totemic.init;

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

            new ItemBlock(ModBlocks.totem_base, new Properties().group(Totemic.itemGroup)).setRegistryName(ModBlocks.totem_base.getRegistryName())
        );
    }
}
