package pokefenn.totemic.init;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.UseCooldown;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.level.block.Block;
import pokefenn.totemic.PlatformRegistryHelper;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicItemTags;
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

    public static final ArmorMaterial JINGLE_DRESS_MATERIAL = new ArmorMaterial(
            15,
            new EnumMap<>(Map.of(
                    ArmorType.BOOTS, 1,
                    ArmorType.LEGGINGS, 1,
                    ArmorType.CHESTPLATE, 1,
                    ArmorType.HELMET, 1,
                    ArmorType.BODY, 1)),
            15,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            0.0F,
            0.0F,
            TotemicItemTags.REAPIRS_JINGLE_DRESS,
            ResourceKey.create(EquipmentAssets.ROOT_ID, Totemic.resloc("jingle_dress"))
    );

    public static final FoodProperties buffalo_meat_food = new FoodProperties.Builder().nutrition(3).saturationModifier(0.35F).build();
    public static final FoodProperties cooked_buffalo_meat_food = new FoodProperties.Builder().nutrition(9).saturationModifier(0.9F).build();

    public static final Supplier<FluteItem> flute = register(
            "flute",
            FluteItem::new,
            () -> new Properties().stacksTo(1).component(DataComponents.USE_COOLDOWN, new UseCooldown(1.0F, Optional.of(Totemic.resloc("flute"))))
    );
    public static final Supplier<InfusedFluteItem> infused_flute = register(
            "infused_flute",
            InfusedFluteItem::new,
            () -> new Properties().stacksTo(1).rarity(Rarity.UNCOMMON).component(DataComponents.USE_COOLDOWN, new UseCooldown(1.0F, Optional.of(Totemic.resloc("flute"))))
    );
    public static final Supplier<JingleDressItem> jingle_dress = register(
            "jingle_dress",
            JingleDressItem::new,
            () -> new Properties().humanoidArmor(JINGLE_DRESS_MATERIAL, ArmorType.LEGGINGS).component(ModDataComponents.JINGLE_DRESS_CHARGE.get(), 0)
    );
    public static final Supplier<RattleItem> rattle = register(
            "rattle",
            RattleItem::new,
            () -> new Properties().stacksTo(1).useCooldown(0.8F)
    );
    public static final Supplier<EagleBoneWhistleItem> eagle_bone_whistle = register(
            "eagle_bone_whistle",
            EagleBoneWhistleItem::new,
            () -> new Properties().stacksTo(1).rarity(Rarity.UNCOMMON).useCooldown(1.0F)
    );
    public static final Supplier<TotemKnifeItem> totem_whittling_knife = register("totem_whittling_knife", TotemKnifeItem::new, () -> new Properties().stacksTo(1).durability(250));
    public static final Supplier<TotemicStaffItem> totemic_staff = register("totemic_staff", TotemicStaffItem::new, () -> new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    public static final Supplier<CeremonyCheatItem> ceremony_cheat = register("ceremony_cheat", CeremonyCheatItem::new, () -> new Properties().stacksTo(1).rarity(Rarity.EPIC));
    public static final Supplier<SpawnEggItem> buffalo_spawn_egg = register("buffalo_spawn_egg", SpawnEggItem::new, () -> new Properties().spawnEgg(ModEntityTypes.buffalo.get()));
    public static final Supplier<SpawnEggItem> bald_eagle_spawn_egg = register("bald_eagle_spawn_egg", SpawnEggItem::new, () -> new Properties().spawnEgg(ModEntityTypes.bald_eagle.get()));
    public static final Supplier<SpawnEggItem> baykok_spawn_egg = register("baykok_spawn_egg", SpawnEggItem::new, () -> new Properties().spawnEgg(ModEntityTypes.baykok.get()));
    public static final Supplier<Item> buffalo_meat = register("buffalo_meat", Item::new, () -> new Properties().food(buffalo_meat_food));
    public static final Supplier<Item> cooked_buffalo_meat = register("cooked_buffalo_meat", Item::new, () -> new Properties().food(cooked_buffalo_meat_food));
    public static final Supplier<Item> buffalo_tooth = register("buffalo_tooth");
    public static final Supplier<Item> buffalo_hide = register("buffalo_hide");
    public static final Supplier<Item> iron_bells = register("iron_bells");
    public static final Supplier<Item> eagle_bone = register("eagle_bone");
    public static final Supplier<Item> eagle_feather = register("eagle_feather");
    public static final Supplier<BaykokBowItem> baykok_bow = register("baykok_bow", BaykokBowItem::new, () -> new Properties().durability(576).enchantable(5).rarity(Rarity.RARE));
    public static final Supplier<MedicineBagItem> medicine_bag = register(
            "medicine_bag",
            MedicineBagItem::new,
            () -> new Properties().stacksTo(1).component(ModDataComponents.OPEN.get(), false).component(ModDataComponents.MEDICINE_BAG_CHARGE.get(), 0)
    );
    public static final Supplier<CreativeMedicineBagItem> creative_medicine_bag = register(
            "creative_medicine_bag",
            CreativeMedicineBagItem::new,
            () -> new Properties().stacksTo(1).rarity(Rarity.EPIC).component(ModDataComponents.OPEN.get(), false)
    );

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
    public static final Supplier<SignItem> cedar_sign = register(
            "cedar_sign",
            p -> new SignItem(ModBlocks.cedar_wall_sign.get(), ModBlocks.cedar_sign.get(), p),
            () -> new Properties().stacksTo(16).useBlockDescriptionPrefix()
    );
    // no item for cedar_wall_sign
    public static final Supplier<HangingSignItem> cedar_hanging_sign = register(
            "cedar_hanging_sign",
            p -> new HangingSignItem(ModBlocks.cedar_hanging_sign.get(), ModBlocks.cedar_wall_hanging_sign.get(), p),
            () -> new Properties().stacksTo(16).useBlockDescriptionPrefix()
    );
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
    public static final Supplier<TotemBaseItem> totem_base = register("totem_base", p -> new TotemBaseItem(ModBlocks.totem_base.get(), p), () -> new Properties().useBlockDescriptionPrefix());
    public static final Supplier<TotemPoleItem> totem_pole = register("totem_pole", p -> new TotemPoleItem(ModBlocks.totem_pole.get(), p), () -> new Properties().useBlockDescriptionPrefix());

    private static <T extends Item> Supplier<T> register(String name, Function<Properties, ? extends T> factory, Supplier<Properties> properties) {
        var key = ResourceKey.create(Registries.ITEM, Totemic.resloc(name));
        return REGISTER.register(name, () -> factory.apply(properties.get().setId(key)));
    }

    private static Supplier<Item> register(String name) {
        return register(name, Item::new, Properties::new);
    }

    private static Supplier<BlockItem> blockItem(String name, Supplier<? extends Block> block) {
        return register(name, p -> new BlockItem(block.get(), p), () -> new Properties().useBlockDescriptionPrefix());
    }

    public static CreativeModeTab makeCreativeTab() {
        return Totemic.platform().creativeTabBuilder()
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
