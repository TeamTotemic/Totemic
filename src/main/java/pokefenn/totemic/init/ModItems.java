package pokefenn.totemic.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemWoodType;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.block.totem.TotemBaseBlock;
import pokefenn.totemic.block.totem.TotemPoleBlock;
import pokefenn.totemic.item.TotemKnifeItem;
import pokefenn.totemic.item.TotemPoleItem;
import pokefenn.totemic.item.music.FluteItem;
import pokefenn.totemic.item.music.InfusedFluteItem;

public final class ModItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, TotemicAPI.MOD_ID);

    public static final RegistryObject<FluteItem> flute = REGISTER.register("flute", () -> new FluteItem(new Properties().stacksTo(1).tab(Totemic.creativeTab)));
    public static final RegistryObject<InfusedFluteItem> infused_flute = REGISTER.register("infused_flute", () -> new InfusedFluteItem(new Properties().stacksTo(1).tab(Totemic.creativeTab)));
    public static final RegistryObject<TotemKnifeItem> totem_whittling_knife = REGISTER.register("totem_whittling_knife", () -> new TotemKnifeItem(new Properties().stacksTo(1).durability(250).tab(Totemic.creativeTab)));

    @SubscribeEvent
    public static void init(RegisterEvent event) {
        if(!event.getRegistryKey().equals(ForgeRegistries.Keys.ITEMS))
            return;

        for(var blockO: ModBlocks.REGISTER.getEntries()) {
            Block block = blockO.get();
            event.getForgeRegistry().register(blockO.getId(), new BlockItem(block, new Properties().tab(Totemic.creativeTab)));
        }

        for(var blockO: ModBlocks.getTotemBases().values()) {
            TotemBaseBlock block = blockO.get();
            event.getForgeRegistry().register(blockO.getId(), new BlockItem(block, new Properties().tab(block.woodType == TotemWoodType.CEDAR ? Totemic.creativeTab : null)));
        }
        for(var blockO: ModBlocks.getTotemPoles().values()) {
            TotemPoleBlock block = blockO.get();
            event.getForgeRegistry().register(blockO.getId(), new TotemPoleItem(block, new Properties().tab(block.woodType == TotemWoodType.CEDAR ? Totemic.creativeTab : null)));
        }
    }
}
