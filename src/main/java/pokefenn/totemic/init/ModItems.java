package pokefenn.totemic.init;

import java.util.Set;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.item.BaykokBowItem;
import pokefenn.totemic.item.CeremonyCheatItem;
import pokefenn.totemic.item.CreativeMedicineBagItem;
import pokefenn.totemic.item.CustomRenderedBlockItem;
import pokefenn.totemic.item.MedicineBagItem;
import pokefenn.totemic.item.TotemBaseItem;
import pokefenn.totemic.item.TotemKnifeItem;
import pokefenn.totemic.item.TotemPoleItem;
import pokefenn.totemic.item.TotemicStaffItem;
import pokefenn.totemic.item.music.EagleBoneWhistleItem;
import pokefenn.totemic.item.music.FluteItem;
import pokefenn.totemic.item.music.InfusedFluteItem;
import pokefenn.totemic.item.music.JingleDressItem;
import pokefenn.totemic.item.music.RattleItem;

public final class ModItems {
    public static final DeferredRegister.Items REGISTER = DeferredRegister.createItems(TotemicAPI.MOD_ID);

    public static final FoodProperties buffalo_meat_food = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.35F).meat().build();
    public static final FoodProperties cooked_buffalo_meat_food = (new FoodProperties.Builder()).nutrition(9).saturationMod(0.9F).meat().build();

    public static final DeferredItem<FluteItem> flute = REGISTER.register("flute", () -> new FluteItem(new Properties().stacksTo(1)));
    public static final DeferredItem<InfusedFluteItem> infused_flute = REGISTER.register("infused_flute", () -> new InfusedFluteItem(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<JingleDressItem> jingle_dress = REGISTER.register("jingle_dress", () -> new JingleDressItem(new Properties()));
    public static final DeferredItem<RattleItem> rattle = REGISTER.register("rattle", () -> new RattleItem(new Properties().stacksTo(1)));
    public static final DeferredItem<EagleBoneWhistleItem> eagle_bone_whistle = REGISTER.register("eagle_bone_whistle", () -> new EagleBoneWhistleItem(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<TotemKnifeItem> totem_whittling_knife = REGISTER.register("totem_whittling_knife", () -> new TotemKnifeItem(new Properties().stacksTo(1).durability(250)));
    public static final DeferredItem<TotemicStaffItem> totemic_staff = REGISTER.register("totemic_staff", () -> new TotemicStaffItem(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<CeremonyCheatItem> ceremony_cheat = REGISTER.register("ceremony_cheat", () -> new CeremonyCheatItem(new Properties().stacksTo(1).rarity(Rarity.EPIC)));
    public static final DeferredItem<SpawnEggItem> buffalo_spawn_egg = REGISTER.register("buffalo_spawn_egg", () -> new DeferredSpawnEggItem(ModEntityTypes.buffalo, 0x2A1C12, 0x885F3E, new Properties()));
    public static final DeferredItem<SpawnEggItem> bald_eagle_spawn_egg = REGISTER.register("bald_eagle_spawn_egg", () -> new DeferredSpawnEggItem(ModEntityTypes.bald_eagle, 0x4B4136, 0xF5E6A3, new Properties()));
    public static final DeferredItem<SpawnEggItem> baykok_spawn_egg = REGISTER.register("baykok_spawn_egg", () -> new DeferredSpawnEggItem(ModEntityTypes.baykok, 0xE0E0E0, 0xF8DAD2, new Properties()));
    public static final DeferredItem<Item> buffalo_meat = REGISTER.register("buffalo_meat", () -> new Item(new Properties().food(buffalo_meat_food)));
    public static final DeferredItem<Item> cooked_buffalo_meat = REGISTER.register("cooked_buffalo_meat", () -> new Item(new Properties().food(cooked_buffalo_meat_food)));
    public static final DeferredItem<Item> buffalo_tooth = REGISTER.register("buffalo_tooth", () -> new Item(new Properties()));
    public static final DeferredItem<Item> buffalo_hide = REGISTER.register("buffalo_hide", () -> new Item(new Properties()));
    public static final DeferredItem<Item> iron_bells = REGISTER.register("iron_bells", () -> new Item(new Properties()));
    public static final DeferredItem<Item> eagle_bone = REGISTER.register("eagle_bone", () -> new Item(new Properties()));
    public static final DeferredItem<Item> eagle_feather = REGISTER.register("eagle_feather", () -> new Item(new Properties()));
    public static final DeferredItem<BaykokBowItem> baykok_bow = REGISTER.register("baykok_bow", () -> new BaykokBowItem(new Properties().durability(576).rarity(Rarity.RARE)));
    public static final DeferredItem<MedicineBagItem> medicine_bag = REGISTER.register("medicine_bag", () -> new MedicineBagItem(new Properties().stacksTo(1)));
    public static final DeferredItem<CreativeMedicineBagItem> creative_medicine_bag = REGISTER.register("creative_medicine_bag", () -> new CreativeMedicineBagItem(new Properties().stacksTo(1).rarity(Rarity.EPIC)));
    //Blocks with custom item blocks
    public static final DeferredItem<CustomRenderedBlockItem> wind_chime = REGISTER.register("wind_chime", () -> new CustomRenderedBlockItem(ModBlocks.wind_chime.get(), new Properties()));
    public static final DeferredItem<SignItem> cedar_sign = REGISTER.register("cedar_sign", () -> new SignItem(new Properties().stacksTo(16), ModBlocks.cedar_sign.get(), ModBlocks.cedar_wall_sign.get()));
    public static final DeferredItem<TotemBaseItem> totem_base = REGISTER.register("totem_base", () -> new TotemBaseItem(ModBlocks.totem_base.get(), new Properties()));
    public static final DeferredItem<TotemPoleItem> totem_pole = REGISTER.register("totem_pole", () -> new TotemPoleItem(ModBlocks.totem_pole.get(), new Properties()));

    @SubscribeEvent
    public static void init(RegisterEvent event) {
        event.register(Registries.ITEM, registry -> {
            //Register item blocks
            final Set<ResourceLocation> blocksWithoutItem = Set.of(ModBlocks.potted_cedar_sapling.getId(), ModBlocks.wind_chime.getId(), ModBlocks.cedar_sign.getId(), ModBlocks.cedar_wall_sign.getId(), ModBlocks.dummy_tipi.getId(), ModBlocks.totem_base.getId(), ModBlocks.totem_pole.getId());
            for(var blockO: ModBlocks.REGISTER.getEntries()) {
                if(blocksWithoutItem.contains(blockO.getId()))
                    continue;

                Block block = blockO.get();
                registry.register(blockO.getId(), new BlockItem(block, new Properties()));
            }
        });

        //Register the creative tab
        event.register(Registries.CREATIVE_MODE_TAB, Totemic.resloc("totemic"), () ->
                CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.totemic"))
                .icon(() -> new ItemStack(ModBlocks.tipi.get()))
                .displayItems(ModItems::addItemsToCreativeTab)
                .build());
    }

    //TODO: Manual adding might be better to define a better ordering of the items
    private static void addItemsToCreativeTab(CreativeModeTab.ItemDisplayParameters params, CreativeModeTab.Output out) {
        final Set<ResourceLocation> blocksNotInCreativeTab = Set.of(ModBlocks.potted_cedar_sapling.getId(), ModBlocks.wind_chime.getId(), ModBlocks.cedar_sign.getId(), ModBlocks.cedar_wall_sign.getId(), ModBlocks.dummy_tipi.getId());
        ModBlocks.REGISTER.getEntries().stream()
                .filter(ro -> !blocksNotInCreativeTab.contains(ro.getId()))
                .map(DeferredHolder::get)
                .forEach(out::accept);
        ModItems.REGISTER.getEntries().stream()
                .map(DeferredHolder::get)
                .forEach(out::accept);
    }
}
