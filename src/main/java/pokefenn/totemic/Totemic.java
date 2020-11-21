package pokefenn.totemic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.apiimpl.TotemicApiImpl;
import pokefenn.totemic.client.ModelBakeHandler;
import pokefenn.totemic.client.TotemicBlockStateProvider;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModItems;
import pokefenn.totemic.init.ModTileEntities;
import pokefenn.totemic.tags.ModBlockTags;

@Mod(Totemic.MOD_ID)
public final class Totemic {
    public static final String MOD_ID = "totemic";
    public static final String MOD_NAME = "Totemic";

    public static final Logger logger = LogManager.getLogger(Totemic.class);

    public static final ItemGroup itemGroup = new ItemGroup(Totemic.MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.flute);
        }
    };

    public Totemic() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        modBus.addListener(this::commonSetup);
        modBus.addListener(this::clientSetup);
        modBus.addListener(this::gatherData);

        modBus.register(ModBlocks.class);
        modBus.register(ModItems.class);
        modBus.register(ModContent.class);
        modBus.register(ModTileEntities.class);

        //Instance field is private, need reflection
        //ObfuscationReflectionHelper.setPrivateValue(TotemicAPI.class, null, new TotemicApiImpl(), "instance");
        try {
            ObfuscationReflectionHelper.findField(TotemicAPI.class, "instance").set(null, new TotemicApiImpl());
        }
        catch (Exception e) {
            throw new RuntimeException("Could not set API field");
        }
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        ModBlocks.checkRegisteredTotemEffects();
    }

    private void clientSetup(FMLClientSetupEvent event) {
        //ModelLoaderRegistry.registerLoader(ModelTotemPoleLoader.INSTANCE);
        MinecraftForge.EVENT_BUS.register(ModelBakeHandler.class);
    }

    private void gatherData(GatherDataEvent event) {
        if(event.includeServer()) {
            event.getGenerator().addProvider(new ModBlockTags.Provider(event.getGenerator(), event.getExistingFileHelper()));
        }
        if(event.includeClient()) {
            event.getGenerator().addProvider(new TotemicBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
        }
    }
}
