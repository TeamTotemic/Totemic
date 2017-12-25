package pokefenn.totemic;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.oredict.OreDictionary;
import pokefenn.totemic.advancements.ModCriteriaTriggers;
import pokefenn.totemic.api.TotemicRegistries;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.configuration.ModConfig;
import pokefenn.totemic.datafix.CamelCaseNamesItems;
import pokefenn.totemic.datafix.CamelCaseNamesTiles;
import pokefenn.totemic.datafix.VanillaIronNugget;
import pokefenn.totemic.entity.animal.EntityBaldEagle;
import pokefenn.totemic.entity.animal.EntityBuffalo;
import pokefenn.totemic.entity.boss.EntityBaykok;
import pokefenn.totemic.entity.projectile.EntityInvisArrow;
import pokefenn.totemic.handler.EntityFall;
import pokefenn.totemic.handler.EntitySpawn;
import pokefenn.totemic.handler.EntityUpdate;
import pokefenn.totemic.handler.PlayerInteract;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModItems;
import pokefenn.totemic.item.ItemBuffaloDrops;
import pokefenn.totemic.item.ItemTotemicItems;
import pokefenn.totemic.lib.Resources;
import pokefenn.totemic.lib.Strings;
import pokefenn.totemic.network.GuiHandler;
import pokefenn.totemic.network.NetworkHandler;
import pokefenn.totemic.tileentity.TileTipi;
import pokefenn.totemic.tileentity.music.TileDrum;
import pokefenn.totemic.tileentity.music.TileWindChime;
import pokefenn.totemic.tileentity.totem.TileTotemBase;
import pokefenn.totemic.tileentity.totem.TileTotemPole;
import pokefenn.totemic.util.MiscUtil;
import pokefenn.totemic.world.ComponentMedicineWheel;
import pokefenn.totemic.world.ComponentTipi;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
        registerTileEntities();
        ModCriteriaTriggers.init();
    }

    public void init(FMLInitializationEvent event)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(Totemic.instance, new GuiHandler());
        NetworkHandler.init();
        oreDictionary();
        furnaceRecipes();
        registerEventHandlers();
        registerStructures();
        registerDataFixers();
    }

    public void postInit(FMLPostInitializationEvent event)
    {
        checkCeremonySelectors();
    }

    @SubscribeEvent
    public void registerEntities(RegistryEvent.Register<EntityEntry> event)
    {
        event.getRegistry().registerAll(
            EntityEntryBuilder.create().entity(EntityBuffalo.class).id(Strings.BUFFALO_NAME, 0).name(Strings.RESOURCE_PREFIX + Strings.BUFFALO_NAME).tracker(80, 3, true).egg(0x2A1C12, 0x885F3E).build(),
            EntityEntryBuilder.create().entity(EntityBaykok.class).id(Strings.BAYKOK_NAME, 1).name(Strings.RESOURCE_PREFIX + Strings.BAYKOK_NAME).tracker(80, 3, true).egg(0xE0E0E0, 0xF8DAD2).build(),
            EntityEntryBuilder.create().entity(EntityInvisArrow.class).id(Strings.INVIS_ARROW_NAME, 2).name(Strings.RESOURCE_PREFIX + Strings.INVIS_ARROW_NAME).tracker(64, 20, true).build(),
            EntityEntryBuilder.create().entity(EntityBaldEagle.class).id(Strings.BALD_EAGLE_NAME, 3).name(Strings.RESOURCE_PREFIX + Strings.BALD_EAGLE_NAME).tracker(80, 3, true).egg(0x875E3E, 0xF5F5DE).build());
    }

    private void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileTotemBase.class, Strings.RESOURCE_PREFIX + Strings.TOTEM_BASE_NAME);
        GameRegistry.registerTileEntity(TileTotemPole.class, Strings.RESOURCE_PREFIX + Strings.TOTEM_POLE_NAME);
        GameRegistry.registerTileEntity(TileDrum.class, Strings.RESOURCE_PREFIX + Strings.DRUM_NAME);
        GameRegistry.registerTileEntity(TileWindChime.class, Strings.RESOURCE_PREFIX + Strings.WIND_CHIME_NAME);
        GameRegistry.registerTileEntity(TileTipi.class, Strings.RESOURCE_PREFIX + Strings.TIPI_NAME);
    }

    private void oreDictionary()
    {
        OreDictionary.registerOre("treeLeaves", new ItemStack(ModBlocks.cedar_leaves, 1));
        OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.cedar_log, 1, 0));
        OreDictionary.registerOre("plankWood", new ItemStack(ModBlocks.cedar_plank, 1, 0));
        OreDictionary.registerOre("bellsIron", new ItemStack(ModItems.sub_items, 1, ItemTotemicItems.Type.iron_bells.ordinal()));
        OreDictionary.registerOre("listAllmeatraw", new ItemStack(ModItems.buffalo_meat));
        OreDictionary.registerOre("listAllbeefraw", new ItemStack(ModItems.buffalo_meat));
        OreDictionary.registerOre("listAllbuffaloraw", new ItemStack(ModItems.buffalo_meat));
        OreDictionary.registerOre("listAllmeatcooked", new ItemStack(ModItems.cooked_buffalo_meat));
        OreDictionary.registerOre("listAllbeefcooked", new ItemStack(ModItems.cooked_buffalo_meat));
        OreDictionary.registerOre("listAllbuffalocooked", new ItemStack(ModItems.cooked_buffalo_meat));
        OreDictionary.registerOre("hideBuffalo", new ItemStack(ModItems.buffalo_items, 1, ItemBuffaloDrops.Type.hide.ordinal()));
        OreDictionary.registerOre("teethBuffalo", new ItemStack(ModItems.buffalo_items, 1, ItemBuffaloDrops.Type.teeth.ordinal()));
    }

    private void furnaceRecipes()
    {
        GameRegistry.addSmelting(ModBlocks.stripped_cedar_log, new ItemStack(Items.COAL, 1, 1), 0.5F);
        GameRegistry.addSmelting(ModBlocks.cedar_log, new ItemStack(Items.COAL, 1, 1), 0.5F);
        GameRegistry.addSmelting(ModItems.buffalo_meat, new ItemStack(ModItems.cooked_buffalo_meat), 0.35F);
    }

    private void registerStructures()
    {
        VillagerRegistry.instance().registerVillageCreationHandler(new ComponentTipi.CreationHandler());
        MapGenStructureIO.registerStructureComponent(ComponentTipi.class, Resources.PREFIX_MOD + "ViTi");
        VillagerRegistry.instance().registerVillageCreationHandler(new ComponentMedicineWheel.CreationHandler());
        MapGenStructureIO.registerStructureComponent(ComponentMedicineWheel.class, Resources.PREFIX_MOD + "ViCer");
    }

    private void registerDataFixers()
    {
        ModFixs fixes = FMLCommonHandler.instance().getDataFixer().init(Totemic.MOD_ID, 1011);
        fixes.registerFix(FixTypes.ITEM_INSTANCE, new VanillaIronNugget());
        fixes.registerFix(FixTypes.BLOCK_ENTITY, new CamelCaseNamesTiles());
        fixes.registerFix(FixTypes.ITEM_INSTANCE, new CamelCaseNamesItems());
    }

    protected void registerEventHandlers()
    {
        MinecraftForge.EVENT_BUS.register(new EntityUpdate());
        MinecraftForge.EVENT_BUS.register(new EntityFall());
        MinecraftForge.EVENT_BUS.register(new PlayerInteract());
        if(ModConfig.general.skeletonsShouldAttackBuffalos)
            MinecraftForge.EVENT_BUS.register(new EntitySpawn());
    }

    private void checkCeremonySelectors()
    {
        //Search for ambiguous selectors
        //The selectors for ceremonies have to be prefix-free in order to ensure
        //that every ceremony can actually be selected
        for(Ceremony ceremony1: TotemicRegistries.ceremonies())
            for(Ceremony ceremony2: TotemicRegistries.ceremonies())
            {
                if(ceremony1 != ceremony2 && MiscUtil.isPrefix(ceremony1.getSelectors(), ceremony2.getSelectors()))
                {
                    throw new IllegalStateException(String.format(
                        "The selectors of Ceremony %1$s are prefixing the selectors of %2$s. This would make selecting %2$s impossible.\n%3$s prefixes %4$s",
                        ceremony1.getRegistryName(), ceremony2.getRegistryName(), ceremony1.getSelectors(), ceremony2.getSelectors()));
                }
            }
    }
}
