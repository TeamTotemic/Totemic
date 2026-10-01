package pokefenn.totemic.neoforge;

import net.minecraft.client.renderer.Sheets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.neoforge.handler.ClientEventHandlers;
import pokefenn.totemic.neoforge.handler.ClientInitHandlers;

@Mod(value = TotemicAPI.MOD_ID, dist = Dist.CLIENT)
public final class TotemicClientNeoMod {
    public TotemicClientNeoMod(IEventBus modBus, ModContainer container) {
        modBus.addListener(this::clientSetup);
        modBus.register(ClientInitHandlers.class);

        container.registerExtensionPoint(IConfigScreenFactory.class, (mod, parent) -> new ConfigurationScreen(mod, parent,
                //filter out customTotemWoodTypes from the config GUI
                (_, key, original) -> key.equals("customTotemWoodTypes") ? null : original));
    }

    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            Sheets.addWoodType(ModBlocks.CEDAR_WOOD_TYPE);
        });

        IEventBus eventBus = NeoForge.EVENT_BUS;
        eventBus.register(ClientEventHandlers.class);
    }
}
