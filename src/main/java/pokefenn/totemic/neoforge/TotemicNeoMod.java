package pokefenn.totemic.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import pokefenn.totemic.PlatformRegistryHelper;
import pokefenn.totemic.TotemicConfig;
import pokefenn.totemic.advancements.ModCriteriaTriggers;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.apiimpl.registry.RegistryApiImpl;
import pokefenn.totemic.block.totem.entity.TotemBaseBlockEntity;
import pokefenn.totemic.client.network.ClientPacketHandler;
import pokefenn.totemic.compat.kubejs.TotemicKubeEventHandler;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModDataComponents;
import pokefenn.totemic.init.ModDataMapTypes;
import pokefenn.totemic.init.ModEntityTypes;
import pokefenn.totemic.init.ModItems;
import pokefenn.totemic.init.ModMobEffects;
import pokefenn.totemic.init.ModSounds;
import pokefenn.totemic.neoforge.datagen.TotemicAdvancementProvider;
import pokefenn.totemic.neoforge.datagen.TotemicBlockStateProvider;
import pokefenn.totemic.neoforge.datagen.TotemicBlockTagsProvider;
import pokefenn.totemic.neoforge.datagen.TotemicDamageTypeTagsProvider;
import pokefenn.totemic.neoforge.datagen.TotemicDataMapProvider;
import pokefenn.totemic.neoforge.datagen.TotemicDatapackEntryProvider;
import pokefenn.totemic.neoforge.datagen.TotemicEntityTypeTagsProvider;
import pokefenn.totemic.neoforge.datagen.TotemicItemTagsProvider;
import pokefenn.totemic.neoforge.datagen.TotemicLootTableProvider;
import pokefenn.totemic.neoforge.datagen.TotemicRecipeProvider;
import pokefenn.totemic.neoforge.handler.PlayerInteract;
import pokefenn.totemic.network.ClientboundPacketStartupMusic;
import pokefenn.totemic.network.ClientboundPacketTotemEffectMusic;
import pokefenn.totemic.network.ServerPacketHandler;
import pokefenn.totemic.network.ServerboundPacketMouseWheel;

@Mod(TotemicAPI.MOD_ID)
public final class TotemicNeoMod {
    public TotemicNeoMod(IEventBus modBus, ModContainer container) {
        modBus.addListener(this::commonSetup);
        modBus.addListener(this::registerPackets);
        modBus.addListener(this::gatherData);

        registerToModBus(ModBlocks.REGISTER, modBus);
        registerToModBus(ModItems.REGISTER, modBus);
        registerToModBus(ModItems.ARMOR_MATERIALS, modBus);
        registerToModBus(ModDataComponents.REGISTER, modBus);
        registerToModBus(ModMobEffects.REGISTER, modBus);
        registerToModBus(ModBlockEntities.REGISTER, modBus);
        registerToModBus(ModEntityTypes.REGISTER, modBus);
        registerToModBus(ModSounds.REGISTER, modBus);
        registerToModBus(ModContent.INSTRUMENTS, modBus);
        registerToModBus(ModContent.WOOD_TYPES, modBus);
        registerToModBus(ModContent.CARVINGS, modBus);
        registerToModBus(ModContent.CEREMONIES, modBus);

        modBus.addListener(ModItems::init);
        modBus.addListener(ModCriteriaTriggers::init);
        modBus.addListener(ModDataMapTypes::init);
        modBus.addListener(ModEntityTypes::registerAttributes);
        modBus.addListener(RegistryApiImpl::registerRegistries);
        modBus.addListener(ModContent::registerCustomWoodTypes);

        modBus.addListener((BlockEntityTypeAddBlocksEvent event) -> ModBlocks.addBlockEntityValidBlocks(event::modify));
        modBus.addListener(TotemBaseBlockEntity::registerCapability);

        TotemicConfig.register(container);
    }

    private void registerToModBus(PlatformRegistryHelper<?> registryHelper, IEventBus modBus) {
        ((NeoPlatformRegistry<?>) registryHelper).deferredRegister().register(modBus);
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

    private void registerPackets(RegisterPayloadHandlersEvent event) {
        final var networkVersion = "5";
        var reg = event.registrar(networkVersion);

        reg.playToClient(ClientboundPacketStartupMusic.TYPE, ClientboundPacketStartupMusic.STREAM_CODEC, (payload, context) -> ClientPacketHandler.handle(payload));
        reg.playToClient(ClientboundPacketTotemEffectMusic.TYPE, ClientboundPacketTotemEffectMusic.STREAM_CODEC, (payload, context) -> ClientPacketHandler.handle(payload));

        reg.playToServer(ServerboundPacketMouseWheel.TYPE, ServerboundPacketMouseWheel.STREAM_CODEC, (payload, context) -> ServerPacketHandler.handle(payload, context.player()));
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
