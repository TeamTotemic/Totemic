package pokefenn.totemic.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import pokefenn.totemic.TotemicConfig;
import pokefenn.totemic.advancements.ModCriteriaTriggers;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.apiimpl.registry.RegistryApiImpl;
import pokefenn.totemic.block.totem.entity.TotemBaseBlockEntity;
import pokefenn.totemic.compat.kubejs.TotemicKubeEventHandler;
import pokefenn.totemic.data.TotemicAdvancementProvider;
import pokefenn.totemic.data.TotemicBlockStateProvider;
import pokefenn.totemic.data.TotemicBlockTagsProvider;
import pokefenn.totemic.data.TotemicDamageTypeTagsProvider;
import pokefenn.totemic.data.TotemicDataMapProvider;
import pokefenn.totemic.data.TotemicDatapackEntryProvider;
import pokefenn.totemic.data.TotemicEntityTypeTagsProvider;
import pokefenn.totemic.data.TotemicItemTagsProvider;
import pokefenn.totemic.data.TotemicLootTableProvider;
import pokefenn.totemic.data.TotemicRecipeProvider;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModDataComponents;
import pokefenn.totemic.init.ModDataMapTypes;
import pokefenn.totemic.init.ModEntityTypes;
import pokefenn.totemic.init.ModItems;
import pokefenn.totemic.init.ModMobEffects;
import pokefenn.totemic.init.ModSounds;
import pokefenn.totemic.neoforge.handler.PlayerInteract;
import pokefenn.totemic.network.NetworkHandler;

@Mod(TotemicAPI.MOD_ID)
public final class TotemicNeoMod {
    public TotemicNeoMod(IEventBus modBus, ModContainer container) {
        modBus.addListener(this::commonSetup);
        modBus.addListener(this::gatherData);

        ModBlocks.REGISTER.register(modBus);
        ModItems.REGISTER.register(modBus);
        ModItems.ARMOR_MATERIALS.register(modBus);
        ModDataComponents.REGISTER.register(modBus);
        ModMobEffects.REGISTER.register(modBus);
        ModBlockEntities.REGISTER.register(modBus);
        ModEntityTypes.REGISTER.register(modBus);
        ModSounds.REGISTER.register(modBus);
        ModContent.INSTRUMENTS.register(modBus);
        ModContent.WOOD_TYPES.register(modBus);
        ModContent.CARVINGS.register(modBus);
        ModContent.CEREMONIES.register(modBus);

        modBus.addListener(ModItems::init);
        modBus.addListener(ModCriteriaTriggers::init);
        modBus.addListener(ModDataMapTypes::init);
        modBus.addListener(ModEntityTypes::registerAttributes);
        modBus.addListener(RegistryApiImpl::registerRegistries);
        modBus.addListener(ModContent::registerCustomWoodTypes);

        modBus.addListener(ModBlocks::addCedarSignToSignBlockEntityType);
        modBus.addListener(TotemBaseBlockEntity::registerCapability);
        modBus.addListener(NetworkHandler::init);

        TotemicConfig.register(container);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModBlocks.addPlantsToFlowerPot();
            ModBlocks.setFireInfo();
        });

        IEventBus eventBus = NeoForge.EVENT_BUS;
        eventBus.register(PlayerInteract.class);

        if(ModList.get().isLoaded("kubejs")) {
            eventBus.register(TotemicKubeEventHandler.class);
            TotemicKubeEventHandler.postModificationEvents();
        }

        RegistryApiImpl.createSelectorsToCeremonyMap();
    }

    private void gatherData(GatherDataEvent event) {
        var gen = event.getGenerator();
        var efh = event.getExistingFileHelper();
        var out = gen.getPackOutput();

        var datapackProvider = gen.addProvider(event.includeServer(), new TotemicDatapackEntryProvider(out, event.getLookupProvider()));
        var lookup = datapackProvider.getRegistryProvider();

        var blockTP = gen.addProvider(event.includeServer(), new TotemicBlockTagsProvider(out, lookup, efh));
        gen.addProvider(event.includeServer(), new TotemicItemTagsProvider(out, lookup, blockTP.contentsGetter(), efh));
        gen.addProvider(event.includeServer(), new TotemicEntityTypeTagsProvider(out, lookup, efh));
        gen.addProvider(event.includeServer(), new TotemicLootTableProvider(out, lookup));
        gen.addProvider(event.includeServer(), new TotemicAdvancementProvider(out, lookup, efh));
        gen.addProvider(event.includeServer(), new TotemicRecipeProvider(out, lookup));
        gen.addProvider(event.includeServer(), new TotemicDamageTypeTagsProvider(out, lookup, efh));
        gen.addProvider(event.includeServer(), new TotemicDataMapProvider(out, lookup));

        gen.addProvider(event.includeClient(), new TotemicBlockStateProvider(out, efh));
    }
}
