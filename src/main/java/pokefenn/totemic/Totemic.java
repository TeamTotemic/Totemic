package pokefenn.totemic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.TotemicRegistries;
import pokefenn.totemic.api.music.MusicAcceptor;
import pokefenn.totemic.apiimpl.TotemicApiImpl;
import pokefenn.totemic.apiimpl.ceremony.CeremonyAPIImpl;
import pokefenn.totemic.data.TotemicBlockStateProvider;
import pokefenn.totemic.data.TotemicBlockTagsProvider;
import pokefenn.totemic.data.TotemicLootTableProvider;
import pokefenn.totemic.handler.ClientInteract;
import pokefenn.totemic.handler.ModBlockColors;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModEffects;
import pokefenn.totemic.init.ModItems;
import pokefenn.totemic.init.ModTileEntities;
import pokefenn.totemic.network.NetworkHandler;

@Mod(TotemicAPI.MOD_ID)
public final class Totemic {
    public static final Logger logger = LogManager.getLogger(Totemic.class);

    public static final CreativeModeTab creativeTab = new CreativeModeTab(TotemicAPI.MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.flute.get());
        }
    };

    public Totemic() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        modBus.addListener(this::commonSetup);
        modBus.addListener(this::registerCapabilities);
        modBus.addListener(this::gatherData);

        ModBlocks.REGISTER.register(modBus);
        ModItems.REGISTER.register(modBus);
        ModEffects.REGISTER.register(modBus);
        ModTileEntities.REGISTER.register(modBus);

        modBus.addListener(TotemicRegistries::createRegistries);
        modBus.register(ModBlocks.class);
        modBus.register(ModItems.class);
        modBus.register(ModContent.class);

        if(FMLEnvironment.dist.isClient()) {
            modBus.addListener(this::clientSetup);

            modBus.register(ModBlockColors.class);
        }

        // Instance field is private, need reflection
        try {
            ObfuscationReflectionHelper.findField(TotemicAPI.class, "instance").set(null, new TotemicApiImpl());
        }
        catch(Exception e) {
            throw new RuntimeException("Could not set API field", e);
        }
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(ModBlocks::setFireInfo);

        ModBlocks.checkRegisteredTotemEffects();
        CeremonyAPIImpl.INSTANCE.computeSelectorsToCeremonyMap();
        NetworkHandler.init();

        //IEventBus eventBus = MinecraftForge.EVENT_BUS;
    }

    private void clientSetup(FMLClientSetupEvent event) {
        IEventBus eventBus = MinecraftForge.EVENT_BUS;
        eventBus.register(ClientInteract.class);
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(MusicAcceptor.class);
    }

    private void gatherData(GatherDataEvent event) {
        if(event.includeServer()) {
            event.getGenerator().addProvider(true, new TotemicBlockTagsProvider(event.getGenerator(), event.getExistingFileHelper()));
            event.getGenerator().addProvider(true, new TotemicLootTableProvider(event.getGenerator()));
        }
        if(event.includeClient()) {
            event.getGenerator().addProvider(true, new TotemicBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
        }
    }
}
