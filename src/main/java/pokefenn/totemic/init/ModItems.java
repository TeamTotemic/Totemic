package pokefenn.totemic.init;

import java.util.EnumMap;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import pokefenn.totemic.PlatformRegistryHelper;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.item.BaykokBowItem;
import pokefenn.totemic.item.CeremonyCheatItem;
import pokefenn.totemic.item.CreativeMedicineBagItem;
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
    public static final PlatformRegistryHelper<Item> REGISTER = Totemic.platform().createRegistryHelper(Registries.ITEM);

    public static final FoodProperties buffalo_meat_food = new FoodProperties.Builder().nutrition(3).saturationModifier(0.35F).build();
    public static final FoodProperties cooked_buffalo_meat_food = new FoodProperties.Builder().nutrition(9).saturationModifier(0.9F).build();

    public static final Supplier<FluteItem> flute = REGISTER.register("flute", () -> new FluteItem(new Properties().stacksTo(1)));
    public static final Supplier<InfusedFluteItem> infused_flute = REGISTER.register("infused_flute", () -> new InfusedFluteItem(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final Supplier<JingleDressItem> jingle_dress = REGISTER.register("jingle_dress", () -> new JingleDressItem(new Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(5)).component(ModDataComponents.JINGLE_DRESS_CHARGE, 0)));
    public static final Supplier<RattleItem> rattle = REGISTER.register("rattle", () -> new RattleItem(new Properties().stacksTo(1)));
    public static final Supplier<EagleBoneWhistleItem> eagle_bone_whistle = REGISTER.register("eagle_bone_whistle", () -> new EagleBoneWhistleItem(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final Supplier<TotemKnifeItem> totem_whittling_knife = REGISTER.register("totem_whittling_knife", () -> new TotemKnifeItem(new Properties().stacksTo(1).durability(250)));
    public static final Supplier<TotemicStaffItem> totemic_staff = REGISTER.register("totemic_staff", () -> new TotemicStaffItem(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final Supplier<CeremonyCheatItem> ceremony_cheat = REGISTER.register("ceremony_cheat", () -> new CeremonyCheatItem(new Properties().stacksTo(1).rarity(Rarity.EPIC)));
    public static final Supplier<SpawnEggItem> buffalo_spawn_egg = REGISTER.register("buffalo_spawn_egg", () -> Totemic.platform().createSpawnEgg(ModEntityTypes.buffalo, 0x2A1C12, 0x885F3E, new Properties()));
    public static final Supplier<SpawnEggItem> bald_eagle_spawn_egg = REGISTER.register("bald_eagle_spawn_egg", () -> Totemic.platform().createSpawnEgg(ModEntityTypes.bald_eagle, 0x4B4136, 0xF5E6A3, new Properties()));
    public static final Supplier<SpawnEggItem> baykok_spawn_egg = REGISTER.register("baykok_spawn_egg", () -> Totemic.platform().createSpawnEgg(ModEntityTypes.baykok, 0xE0E0E0, 0xF8DAD2, new Properties()));
    public static final Supplier<Item> buffalo_meat = REGISTER.register("buffalo_meat", () -> new Item(new Properties().food(buffalo_meat_food)));
    public static final Supplier<Item> cooked_buffalo_meat = REGISTER.register("cooked_buffalo_meat", () -> new Item(new Properties().food(cooked_buffalo_meat_food)));
    public static final Supplier<Item> buffalo_tooth = REGISTER.register("buffalo_tooth", () -> new Item(new Properties()));
    public static final Supplier<Item> buffalo_hide = REGISTER.register("buffalo_hide", () -> new Item(new Properties()));
    public static final Supplier<Item> iron_bells = REGISTER.register("iron_bells", () -> new Item(new Properties()));
    public static final Supplier<Item> eagle_bone = REGISTER.register("eagle_bone", () -> new Item(new Properties()));
    public static final Supplier<Item> eagle_feather = REGISTER.register("eagle_feather", () -> new Item(new Properties()));
    public static final Supplier<BaykokBowItem> baykok_bow = REGISTER.register("baykok_bow", () -> new BaykokBowItem(new Properties().durability(576).rarity(Rarity.RARE)));
    public static final Supplier<MedicineBagItem> medicine_bag = REGISTER.register("medicine_bag", () -> new MedicineBagItem(new Properties().stacksTo(1).component(ModDataComponents.OPEN, false).component(ModDataComponents.MEDICINE_BAG_CHARGE, 0)));
    public static final Supplier<CreativeMedicineBagItem> creative_medicine_bag = REGISTER.register("creative_medicine_bag", () -> new CreativeMedicineBagItem(new Properties().stacksTo(1).rarity(Rarity.EPIC).component(ModDataComponents.OPEN, false)));
    //Blocks with custom item blocks
    public static final Supplier<BlockItem> wind_chime = REGISTER.register("wind_chime", () -> new BlockItem(ModBlocks.wind_chime.get(), new Properties()));
    public static final Supplier<SignItem> cedar_sign = REGISTER.register("cedar_sign", () -> new SignItem(new Properties().stacksTo(16), ModBlocks.cedar_sign.get(), ModBlocks.cedar_wall_sign.get()));
    public static final Supplier<HangingSignItem> cedar_hanging_sign = REGISTER.register("cedar_hanging_sign", () -> new HangingSignItem(ModBlocks.cedar_hanging_sign.get(), ModBlocks.cedar_wall_hanging_sign.get(), new Properties().stacksTo(16)));
    public static final Supplier<TotemBaseItem> totem_base = REGISTER.register("totem_base", () -> new TotemBaseItem(ModBlocks.totem_base.get(), new Properties()));
    public static final Supplier<TotemPoleItem> totem_pole = REGISTER.register("totem_pole", () -> new TotemPoleItem(ModBlocks.totem_pole.get(), new Properties()));

    public static final PlatformRegistryHelper<ArmorMaterial> ARMOR_MATERIALS = Totemic.platform().createRegistryHelper(Registries.ARMOR_MATERIAL);
    public static final Holder<ArmorMaterial> JINGLE_DRESS_MATERIAL = ARMOR_MATERIALS.registerForHolder("jingle_dress", () -> new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 1);
                map.put(ArmorItem.Type.LEGGINGS, 1);
                map.put(ArmorItem.Type.CHESTPLATE, 1);
                map.put(ArmorItem.Type.HELMET, 1);
                map.put(ArmorItem.Type.BODY, 1);
            }),
            15,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            () -> Ingredient.of(ModItems.buffalo_hide.get()),
            List.of(new ArmorMaterial.Layer(Totemic.resloc("jingle_dress"))),
            0.0F,
            0.0F));

    @SubscribeEvent
    public static void init(RegisterEvent event) {
        event.register(Registries.ITEM, registry -> {
            //Register item blocks
            // TODO: This is messy, figure out a better way of doing that.
            // For example, a "registerWithItem" method on a special subinterface of PlatformRegistryHelper<Block>,
            // or simply registering item blocks manually in this class. The latter approach would be better for adding
            // the items to the creative tab.
            final Set<Block> blocksWithoutItem = Set.of(ModBlocks.potted_cedar_sapling.get(), ModBlocks.wind_chime.get(), ModBlocks.cedar_sign.get(), ModBlocks.cedar_wall_sign.get(), ModBlocks.cedar_hanging_sign.get(), ModBlocks.cedar_wall_hanging_sign.get(), ModBlocks.dummy_tipi.get(), ModBlocks.totem_base.get(), ModBlocks.totem_pole.get());
            ModBlocks.REGISTER.getEntries()
                    .filter(block -> !blocksWithoutItem.contains(block))
                    .forEach(block -> {
                ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
                registry.register(id, new BlockItem(block, new Properties()));
            });
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
        final Set<Block> blocksNotInCreativeTab = Set.of(ModBlocks.potted_cedar_sapling.get(), ModBlocks.wind_chime.get(), ModBlocks.cedar_sign.get(), ModBlocks.cedar_wall_sign.get(), ModBlocks.cedar_hanging_sign.get(), ModBlocks.cedar_wall_hanging_sign.get(), ModBlocks.dummy_tipi.get());
        ModBlocks.REGISTER.getEntries()
                .filter(ro -> !blocksNotInCreativeTab.contains(ro))
                .forEach(out::accept);
        ModItems.REGISTER.getEntries()
                .forEach(out::accept);
    }
}
