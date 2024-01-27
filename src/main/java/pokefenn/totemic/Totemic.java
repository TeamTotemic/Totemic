package pokefenn.totemic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import pokefenn.totemic.advancements.ModCriteriaTriggers;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.apiimpl.registry.RegistryApiImpl;
import pokefenn.totemic.block.totem.entity.TotemBaseBlockEntity;
import pokefenn.totemic.client.ModModelLayers;
import pokefenn.totemic.data.TotemicBlockStateProvider;
import pokefenn.totemic.data.TotemicBlockTagsProvider;
import pokefenn.totemic.data.TotemicDamageTypeTagsProvider;
import pokefenn.totemic.data.TotemicDatapackEntryProvider;
import pokefenn.totemic.data.TotemicEntityTypeTagsProvider;
import pokefenn.totemic.data.TotemicItemTagsProvider;
import pokefenn.totemic.data.TotemicLootTableProvider;
import pokefenn.totemic.data.TotemicRecipeProvider;
import pokefenn.totemic.handler.ClientInitHandlers;
import pokefenn.totemic.handler.ClientInteract;
import pokefenn.totemic.handler.ClientRenderHandler;
import pokefenn.totemic.handler.PlayerInteract;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModEntityTypes;
import pokefenn.totemic.init.ModItems;
import pokefenn.totemic.init.ModMobEffects;
import pokefenn.totemic.init.ModSounds;
import pokefenn.totemic.network.NetworkHandler;

@Mod(TotemicAPI.MOD_ID)
public final class Totemic {
    public static final Logger logger = LogManager.getLogger(Totemic.class);

    public Totemic(IEventBus modBus) {
        modBus.addListener(this::commonSetup);
        modBus.addListener(this::gatherData);

        ModBlocks.REGISTER.register(modBus);
        ModItems.REGISTER.register(modBus);
        ModMobEffects.REGISTER.register(modBus);
        ModBlockEntities.REGISTER.register(modBus);
        ModEntityTypes.REGISTER.register(modBus);
        ModSounds.REGISTER.register(modBus);

        modBus.register(ModItems.class);
        modBus.register(ModEntityTypes.class);
        modBus.register(RegistryApiImpl.class);
        modBus.register(ModContent.class);

        modBus.addListener(TotemBaseBlockEntity::registerCapability);
        modBus.addListener(ModCriteriaTriggers::init);
        modBus.addListener(NetworkHandler::init);

        if(FMLEnvironment.dist.isClient()) {
            modBus.addListener(this::clientSetup);

            modBus.register(ClientInitHandlers.class);
            modBus.register(ModModelLayers.class);
        }

        ModConfig.register(ModLoadingContext.get());
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModBlocks.addPlantsToFlowerPot();
            ModBlocks.setFireInfo();
            ModBlocks.addCedarSignToSignBlockEntityType();
        });

        //PatchouliIntegration.init();

        IEventBus eventBus = NeoForge.EVENT_BUS;
        eventBus.register(PlayerInteract.class);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ModItems.baykok_bow.get().registerItemProperties();
            ModItems.medicine_bag.get().registerItemProperties();
            Sheets.addWoodType(ModBlocks.CEDAR_WOOD_TYPE);
        });

        IEventBus eventBus = NeoForge.EVENT_BUS;
        eventBus.register(ClientInteract.class);
        eventBus.register(ClientRenderHandler.class);
    }

    private void gatherData(GatherDataEvent event) {
        var gen = event.getGenerator();
        var efh = event.getExistingFileHelper();
        var lookup = event.getLookupProvider();
        var out = gen.getPackOutput();

        var blockTP = gen.addProvider(event.includeServer(), new TotemicBlockTagsProvider(out, lookup, efh));
        gen.addProvider(event.includeServer(), new TotemicItemTagsProvider(out, lookup, blockTP.contentsGetter(), efh));
        gen.addProvider(event.includeServer(), new TotemicEntityTypeTagsProvider(out, lookup, efh));
        gen.addProvider(event.includeServer(), new TotemicLootTableProvider(out));
        gen.addProvider(event.includeServer(), new TotemicRecipeProvider(out, lookup));
        gen.addProvider(event.includeServer(), new TotemicDatapackEntryProvider(out, lookup));
        gen.addProvider(event.includeServer(), new TotemicDamageTypeTagsProvider(out, lookup, efh));

        gen.addProvider(event.includeClient(), new TotemicBlockStateProvider(out, efh));
    }

    public static ResourceLocation resloc(String path) {
        return new ResourceLocation(TotemicAPI.MOD_ID, path);
    }
}
