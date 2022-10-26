package pokefenn.totemic.init;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
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
import pokefenn.totemic.item.CeremonyCheatItem;
import pokefenn.totemic.item.CustomRenderedBlockItem;
import pokefenn.totemic.item.TotemKnifeItem;
import pokefenn.totemic.item.TotemPoleItem;
import pokefenn.totemic.item.TotemicStaffItem;
import pokefenn.totemic.item.music.FluteItem;
import pokefenn.totemic.item.music.InfusedFluteItem;
import pokefenn.totemic.item.music.JingleDressItem;
import pokefenn.totemic.item.music.RattleItem;

public final class ModItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, TotemicAPI.MOD_ID);

    public static final FoodProperties buffalo_meat_food = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.35F).meat().build();
    public static final FoodProperties cooked_buffalo_meat_food = (new FoodProperties.Builder()).nutrition(9).saturationMod(0.9F).meat().build();

    public static final RegistryObject<FluteItem> flute = REGISTER.register("flute", () -> new FluteItem(new Properties().stacksTo(1).tab(Totemic.creativeTab)));
    public static final RegistryObject<InfusedFluteItem> infused_flute = REGISTER.register("infused_flute", () -> new InfusedFluteItem(new Properties().stacksTo(1).tab(Totemic.creativeTab).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<JingleDressItem> jingle_dress = REGISTER.register("jingle_dress", () -> new JingleDressItem(new Properties().tab(Totemic.creativeTab)));
    public static final RegistryObject<RattleItem> rattle = REGISTER.register("rattle", () -> new RattleItem(new Properties().stacksTo(1).tab(Totemic.creativeTab)));
    public static final RegistryObject<TotemKnifeItem> totem_whittling_knife = REGISTER.register("totem_whittling_knife", () -> new TotemKnifeItem(new Properties().stacksTo(1).durability(250).tab(Totemic.creativeTab)));
    public static final RegistryObject<TotemicStaffItem> totemic_staff = REGISTER.register("totemic_staff", () -> new TotemicStaffItem(new Properties().stacksTo(1).tab(Totemic.creativeTab).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<CeremonyCheatItem> ceremony_cheat = REGISTER.register("ceremony_cheat", () -> new CeremonyCheatItem(new Properties().stacksTo(1).tab(Totemic.creativeTab)));
    public static final RegistryObject<SpawnEggItem> buffalo_spawn_egg = REGISTER.register("buffalo_spawn_egg", () -> new ForgeSpawnEggItem(ModEntityTypes.buffalo, 0x2A1C12, 0x885F3E, new Properties().tab(Totemic.creativeTab)));
    public static final RegistryObject<Item> buffalo_meat = REGISTER.register("buffalo_meat", () -> new Item(new Properties().tab(Totemic.creativeTab).food(buffalo_meat_food)));
    public static final RegistryObject<Item> cooked_buffalo_meat = REGISTER.register("cooked_buffalo_meat", () -> new Item(new Properties().tab(Totemic.creativeTab).food(cooked_buffalo_meat_food)));
    public static final RegistryObject<Item> buffalo_tooth = REGISTER.register("buffalo_tooth", () -> new Item(new Properties().tab(Totemic.creativeTab)));
    public static final RegistryObject<Item> buffalo_hide = REGISTER.register("buffalo_hide", () -> new Item(new Properties().tab(Totemic.creativeTab)));
    public static final RegistryObject<CustomRenderedBlockItem> wind_chime = REGISTER.register("wind_chime", () -> new CustomRenderedBlockItem(ModBlocks.wind_chime.get(), new Properties().tab(Totemic.creativeTab)));

    @SubscribeEvent
    public static void init(RegisterEvent event) {
        if(!event.getRegistryKey().equals(ForgeRegistries.Keys.ITEMS))
            return;

        //Register item blocks
        for(var blockO: ModBlocks.REGISTER.getEntries()) {
            if(blockO.getId().equals(wind_chime.getId())) //We have a custom BlockItem for the wind chime
                continue;

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
