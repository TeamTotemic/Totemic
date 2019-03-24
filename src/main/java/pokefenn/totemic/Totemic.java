package pokefenn.totemic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import pokefenn.totemic.client.ModelBakeHandler;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModItems;

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

        modBus.register(ModBlocks.class);
        modBus.register(ModItems.class);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }

    private void clientSetup(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(ModelBakeHandler.class);
    }
}
