package pokefenn.totemic.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ObjectHolder;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemWoodType;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.block.totem.TotemBaseBlock;
import pokefenn.totemic.block.totem.TotemPoleBlock;
import pokefenn.totemic.item.TotemKnifeItem;
import pokefenn.totemic.item.TotemPoleItem;
import pokefenn.totemic.item.music.FluteItem;
import pokefenn.totemic.item.music.InfusedFluteItem;

@ObjectHolder(TotemicAPI.MOD_ID)
public final class ModItems {
    public static final FluteItem flute = null;
    public static final InfusedFluteItem infused_flute = null;
    public static final TotemKnifeItem totem_whittling_knife = null;

    @SubscribeEvent
    public static void init(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(new FluteItem(new Properties().stacksTo(1).tab(Totemic.creativeTab)).setRegistryName("flute"),
                new InfusedFluteItem(new Properties().stacksTo(1).tab(Totemic.creativeTab)).setRegistryName("infused_flute"),
                new TotemKnifeItem(new Properties().stacksTo(1).durability(250).tab(Totemic.creativeTab)).setRegistryName("totem_whittling_knife"));

        for(Block block: ModBlocks.getBlocksWithItemBlock())
            event.getRegistry().register(makeItemBlock(block));
    }

    private static Item makeItemBlock(Block block) {
        //TODO: Possibly there is a better solution
        if(block instanceof TotemPoleBlock pole) {
            //Make only the oak variants of the Totem Poles and Bases appear in the creative tab
            return new TotemPoleItem(pole, new Properties().tab(pole.woodType == TotemWoodType.OAK ? Totemic.creativeTab : null)).setRegistryName(block.getRegistryName());
        }
        else if(block instanceof TotemBaseBlock base) {
            return new BlockItem(base, new Properties().tab(base.woodType == TotemWoodType.OAK ? Totemic.creativeTab : null)).setRegistryName(block.getRegistryName());
        }
        else
            return new BlockItem(block, new Properties().tab(Totemic.creativeTab)).setRegistryName(block.getRegistryName());
    }
}
