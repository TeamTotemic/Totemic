package pokefenn.totemic.init;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.*;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.item.*;
import pokefenn.totemic.item.equipment.ItemBarkStripper;
import pokefenn.totemic.item.equipment.ItemMedicineBag;
import pokefenn.totemic.item.equipment.ItemTotemWhittlingKnife;
import pokefenn.totemic.item.equipment.ItemTotemicStaff;
import pokefenn.totemic.item.equipment.music.*;
import pokefenn.totemic.item.equipment.weapon.ItemBaykokBow;
import pokefenn.totemic.lib.Strings;
import pokefenn.totemic.lib.WoodVariant;

@EventBusSubscriber(modid = Totemic.MOD_ID)
@ObjectHolder(Totemic.MOD_ID)
public final class ModItems
{
    public static final ItemFlute flute = null;
    public static final ItemRattle rattle = null;
    public static final ItemJingleDress jingle_dress = null;
    public static final ItemEagleBoneWhistle eagle_bone_whistle = null;
    public static final ItemTotemWhittlingKnife totem_whittling_knife = null;
    public static final ItemBarkStripper bark_stripper = null;
    public static final ItemTotemicStaff totemic_staff = null;
    public static final ItemTotemicItems sub_items = null;
    public static final ItemTotempedia totempedia = null;
    public static final ItemBuffaloDrops buffalo_items = null;
    public static final ItemFood buffalo_meat = null;
    public static final ItemFood cooked_buffalo_meat = null;
    public static final ItemBaykokBow baykok_bow = null;
    public static final ItemMedicineBag medicine_bag = null;
    public static final ItemCeremonyCheat ceremony_cheat = null;
    public static final ItemEagleDrops eagle_drops = null;
    public static final ItemNetherPipe nether_pipe = null;

    @SubscribeEvent
    public static void init(RegistryEvent.Register<Item> event)
    {
        ItemMultiTexture.Mapper pillar_mapper = stack -> {
            int meta = stack.getMetadata();
            return (((meta & 1) == 1) ? "stripped_" : "") + WoodVariant.fromID(meta >> 1).getName();
        };

        event.getRegistry().registerAll(
            makeItemBlock(ModBlocks.cedar_log),
            makeItemBlock(ModBlocks.stripped_cedar_log),
            makeItemBlock(ModBlocks.cedar_plank),
            makeItemBlock(ModBlocks.cedar_sapling),
            makeItemBlock(ModBlocks.cedar_leaves),
            makeItemBlock(ModBlocks.cedar_stairs),
            new ItemSlab(ModBlocks.cedar_slab, ModBlocks.cedar_slab, ModBlocks.double_cedar_slab).setRegistryName(ModBlocks.cedar_slab.getRegistryName()),
            makeItemBlock(ModBlocks.cedar_fence),
            makeItemBlock(ModBlocks.cedar_fence_gate),
            makeItemBlock(ModBlocks.totem_base).setHasSubtypes(true),
            makeItemBlock(ModBlocks.totem_pole).setHasSubtypes(true),
            makeItemBlock(ModBlocks.totem_torch),
            makeItemBlock(ModBlocks.drum),
            makeItemBlock(ModBlocks.wind_chime),
            new ItemTipi(ModBlocks.tipi).setRegistryName(ModBlocks.tipi.getRegistryName()),
            new ItemMultiTexture(ModBlocks.wooden_pillar, ModBlocks.wooden_pillar, pillar_mapper).setRegistryName(ModBlocks.wooden_pillar.getRegistryName()),
            new ItemMultiTexture(ModBlocks.wooden_pillar_base, ModBlocks.wooden_pillar_base, pillar_mapper).setRegistryName(ModBlocks.wooden_pillar_base.getRegistryName()),

            new ItemFlute(),
            new ItemRattle(),
            new ItemJingleDress(),
            new ItemEagleBoneWhistle(),
            new ItemTotemWhittlingKnife(),
            new ItemBarkStripper(),
            new ItemTotemicStaff(),
            new ItemTotemicItems(),
            new ItemTotempedia(),
            new ItemBuffaloDrops(),
            new ItemFood(3, 0.35F, true).setRegistryName(Strings.BUFFALO_MEAT_NAME).setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.BUFFALO_MEAT_NAME).setCreativeTab(Totemic.tabsTotem),
            new ItemFood(9, 0.9F, true).setRegistryName(Strings.COOKED_BUFFALO_MEAT_NAME).setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.COOKED_BUFFALO_MEAT_NAME).setCreativeTab(Totemic.tabsTotem),
            new ItemBaykokBow(),
            new ItemMedicineBag(),
            new ItemCeremonyCheat(),
            new ItemEagleDrops(),
            new ItemNetherPipe());

    }

    private static ItemBlock makeItemBlock(Block block)
    {
        return (ItemBlock) new ItemBlock(block).setRegistryName(block.getRegistryName());
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void setItemModels(ModelRegistryEvent event)
    {
        setDefaultModel(ModBlocks.cedar_log);
        setDefaultModel(ModBlocks.stripped_cedar_log);
        setDefaultModel(ModBlocks.cedar_plank);
        setDefaultModel(ModBlocks.cedar_sapling);
        setDefaultModel(ModBlocks.cedar_leaves);
        setDefaultModel(ModBlocks.cedar_stairs);
        setDefaultModel(ModBlocks.cedar_slab);
        setDefaultModel(ModBlocks.cedar_fence);
        setDefaultModel(ModBlocks.cedar_fence_gate);
        setDefaultModel(ModBlocks.totem_torch);
        setDefaultModel(ModBlocks.drum);
        setDefaultModel(ModBlocks.wind_chime);
        setDefaultModel(ModBlocks.tipi);

        for(WoodVariant var: WoodVariant.values())
        {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.totem_base), var.getID(),
                    new ModelResourceLocation(ModBlocks.totem_base.getRegistryName(), "facing=north,wood=" + var.getName()));
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.totem_pole), var.getID(),
                    new ModelResourceLocation(ModBlocks.totem_pole.getRegistryName(), "facing=north,wood=" + var.getName()));

            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.wooden_pillar), 2*var.getID(),
                    new ModelResourceLocation(ModBlocks.wooden_pillar.getRegistryName(), "axis=y,stripped=false,wood=" + var.getName()));
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.wooden_pillar), 2*var.getID() + 1,
                    new ModelResourceLocation(ModBlocks.wooden_pillar.getRegistryName(), "axis=y,stripped=true,wood=" + var.getName()));
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.wooden_pillar_base), 2*var.getID(),
                    new ModelResourceLocation(ModBlocks.wooden_pillar_base.getRegistryName(), "facing=up,stripped=false,wood=" + var.getName()));
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.wooden_pillar_base), 2*var.getID() + 1,
                    new ModelResourceLocation(ModBlocks.wooden_pillar_base.getRegistryName(), "facing=up,stripped=true,wood=" + var.getName()));
        }

        setDefaultModel(flute);
        setModel(flute, 1, flute.getRegistryName().toString());
        setDefaultModel(rattle);
        setDefaultModel(jingle_dress);
        setDefaultModel(eagle_bone_whistle);
        setDefaultModel(totem_whittling_knife);
        setDefaultModel(bark_stripper);
        setDefaultModel(totemic_staff);
        setDefaultModel(totempedia);
        setDefaultModel(buffalo_meat);
        setDefaultModel(cooked_buffalo_meat);
        setDefaultModel(baykok_bow);
        setModel(medicine_bag, 0, medicine_bag.getRegistryName().toString() + "_closed");
        setModel(medicine_bag, 1, medicine_bag.getRegistryName().toString() + "_open");
        setDefaultModel(ceremony_cheat);
        setDefaultModel(nether_pipe);

        setModel(sub_items, 1, Strings.RESOURCE_PREFIX + ItemTotemicItems.Type.iron_bells.toString());

        for(ItemBuffaloDrops.Type t: ItemBuffaloDrops.Type.values())
            setModel(buffalo_items, t.ordinal(), Strings.RESOURCE_PREFIX + t.toString());

        for(ItemEagleDrops.Type t: ItemEagleDrops.Type.values())
            setModel(eagle_drops, t.ordinal(), Strings.RESOURCE_PREFIX + t.toString());
    }

    @SideOnly(Side.CLIENT)
    private static void setModel(Item item, int meta, String modelName)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(modelName, "inventory"));
    }

    @SideOnly(Side.CLIENT)
    private static void setDefaultModel(Item item)
    {
        setModel(item, 0, item.getRegistryName().toString());
    }

    @SideOnly(Side.CLIENT)
    private static void setDefaultModel(Block block)
    {
        setDefaultModel(Item.getItemFromBlock(block));
    }

}
