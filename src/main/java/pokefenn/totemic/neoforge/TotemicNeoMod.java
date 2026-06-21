package pokefenn.totemic.neoforge;

import com.electronwill.nightconfig.core.Config;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.registries.RegisterEvent.RegisterHelper;
import pokefenn.totemic.PlatformRegistryHelper;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.TotemicConfig;
import pokefenn.totemic.advancements.ModCriteriaTriggers;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.registry.RegistryAPI;
import pokefenn.totemic.api.totem.TotemWoodType;
import pokefenn.totemic.apiimpl.registry.RegistryApiImpl;
import pokefenn.totemic.block.totem.entity.TotemBaseBlockEntity;
import pokefenn.totemic.client.network.ClientPacketHandler;
import pokefenn.totemic.compat.kubejs.TotemicKubeEventHandler;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModDataComponents;
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
        modBus.addListener(this::register);
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

        modBus.addListener(ModCriteriaTriggers::init);
        modBus.addListener(ModDataMapTypes::init);
        modBus.addListener(ModEntityTypes::registerAttributes);
        modBus.addListener(RegistryApiImpl::registerRegistries);

        modBus.addListener((BlockEntityTypeAddBlocksEvent event) -> ModBlocks.addBlockEntityValidBlocks(event::modify));
        modBus.addListener(TotemBaseBlockEntity::registerCapability);

        TotemicConfig.register(container);
    }

    private void registerToModBus(PlatformRegistryHelper<?> registryHelper, IEventBus modBus) {
        ((NeoPlatformRegistry<?>) registryHelper).deferredRegister().register(modBus);
    }

    private void register(RegisterEvent event) {
        event.register(Registries.CREATIVE_MODE_TAB, Totemic.resloc("totemic"), ModItems::makeCreativeTab);
        event.register(RegistryAPI.WOOD_TYPE_REGISTRY, this::registerCustomWoodTypes);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
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

    private void registerCustomWoodTypes(RegisterHelper<TotemWoodType> reg) {
        // TODO: Wood types should really be made a datapack registry
        for(Config entry: TotemicConfig.STARTUP.customTotemWoodTypes.get()) {
            if(entry.isEmpty())
                continue; //ignore empty default value

            String idStr = entry.get("id");
            if(idStr == null)
                throw new IllegalArgumentException("Invalid custom Totem Wood Type: Missing entry 'id'. Please check your 'totemic-startup.toml' config file.");
            try {
                String logsStr = entry.get("logs");
                if(logsStr == null)
                    throw new IllegalArgumentException("Missing entry 'logs'");
                if(!logsStr.startsWith("#"))
                    throw new IllegalArgumentException("'logs' value must be a valid block tag key starting with '#'");
                //Note that there is no way for us to check if the tag key actually exists since tags are not loaded until server start
                int woodColorIndex = entry.getIntOrElse("woodColor", MapColor.WOOD.id);
                int barkColorIndex = entry.getIntOrElse("barkColor", MapColor.PODZOL.id);

                var id = ResourceLocation.parse(idStr);
                var logTagKey = TagKey.create(Registries.BLOCK, ResourceLocation.parse(logsStr.substring(1)));
                var woodColor = MapColor.byId(woodColorIndex);
                var barkColor = MapColor.byId(barkColorIndex);

                reg.register(id, new TotemWoodType(woodColor, barkColor, logTagKey));
                Totemic.logger.debug("Added custom Totem Wood Type with ID '" + id + "'");
            }
            catch(Exception e) {
                throw new IllegalArgumentException("Invalid custom Totem Wood Type with ID '" + idStr + "': " + e.getLocalizedMessage() + "\nPlease check your 'totemic-startup.toml' config file.", e);
            }
        }
    }
}
