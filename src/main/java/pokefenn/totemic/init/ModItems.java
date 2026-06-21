package pokefenn.totemic.init;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
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

    // Block items
    public static final Supplier<BlockItem> stripped_cedar_log = blockItem("stripped_cedar_log", ModBlocks.stripped_cedar_log);
    public static final Supplier<BlockItem> cedar_log = blockItem("cedar_log", ModBlocks.cedar_log);
    public static final Supplier<BlockItem> stripped_cedar_wood = blockItem("stripped_cedar_wood", ModBlocks.stripped_cedar_wood);
    public static final Supplier<BlockItem> cedar_wood = blockItem("cedar_wood", ModBlocks.cedar_wood);
    public static final Supplier<BlockItem> cedar_leaves = blockItem("cedar_leaves", ModBlocks.cedar_leaves);
    public static final Supplier<BlockItem> cedar_sapling = blockItem("cedar_sapling", ModBlocks.cedar_sapling);
    public static final Supplier<BlockItem> cedar_planks = blockItem("cedar_planks", ModBlocks.cedar_planks);
    public static final Supplier<BlockItem> cedar_button = blockItem("cedar_button", ModBlocks.cedar_button);
    public static final Supplier<BlockItem> cedar_fence = blockItem("cedar_fence", ModBlocks.cedar_fence);
    public static final Supplier<BlockItem> cedar_fence_gate = blockItem("cedar_fence_gate", ModBlocks.cedar_fence_gate);
    public static final Supplier<BlockItem> cedar_pressure_plate = blockItem("cedar_pressure_plate", ModBlocks.cedar_pressure_plate);
    public static final Supplier<SignItem> cedar_sign = REGISTER.register("cedar_sign", () -> new SignItem(new Properties().stacksTo(16), ModBlocks.cedar_sign.get(), ModBlocks.cedar_wall_sign.get()));
    // no item for cedar_wall_sign
    public static final Supplier<HangingSignItem> cedar_hanging_sign = REGISTER.register("cedar_hanging_sign", () -> new HangingSignItem(ModBlocks.cedar_hanging_sign.get(), ModBlocks.cedar_wall_hanging_sign.get(), new Properties().stacksTo(16)));
    // no item for cedar_wall_hanging_sign
    public static final Supplier<BlockItem> cedar_slab = blockItem("cedar_slab", ModBlocks.cedar_slab);
    public static final Supplier<BlockItem> cedar_stairs = blockItem("cedar_stairs", ModBlocks.cedar_stairs);
    public static final Supplier<BlockItem> cedar_door = blockItem("cedar_door", ModBlocks.cedar_door);
    public static final Supplier<BlockItem> cedar_trapdoor = blockItem("cedar_trapdoor", ModBlocks.cedar_trapdoor);
    // no item for potted_cedar_sapling
    public static final Supplier<BlockItem> drum = blockItem("drum", ModBlocks.drum);
    public static final Supplier<BlockItem> wind_chime = blockItem("wind_chime", ModBlocks.wind_chime);
    public static final Supplier<BlockItem> totem_torch = blockItem("totem_torch", ModBlocks.totem_torch);
    public static final Supplier<BlockItem> tipi = blockItem("tipi", ModBlocks.tipi);
    // no item for dummy_tipi
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

    private static Supplier<BlockItem> blockItem(String name, Supplier<? extends Block> block) {
        return REGISTER.register(name, () -> new BlockItem(block.get(), new Properties()));
    }

    public static CreativeModeTab makeCreativeTab() {
        return CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.totemic"))
                .icon(() -> new ItemStack(tipi.get()))
                .displayItems(ModItems::addItemsToCreativeTab)
                .build();
    }

    //TODO: Manual adding might be better to define a better ordering of the items
    private static void addItemsToCreativeTab(CreativeModeTab.ItemDisplayParameters params, CreativeModeTab.Output out) {
        REGISTER.getEntries().forEach(out::accept);
    }
}
