package pokefenn.totemic;

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
import pokefenn.totemic.client.ModModelLayers;
import pokefenn.totemic.handler.ClientInitHandlers;
import pokefenn.totemic.handler.ClientInteract;
import pokefenn.totemic.handler.ClientRenderHandler;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModItems;

@Mod(value = TotemicAPI.MOD_ID, dist = Dist.CLIENT)
public final class ClientTotemicMod {
    public ClientTotemicMod(IEventBus modBus, ModContainer container) {
        modBus.addListener(this::clientSetup);

        modBus.register(ClientInitHandlers.class);
        modBus.register(ModModelLayers.class);

        container.registerExtensionPoint(IConfigScreenFactory.class, (mod, parent) -> new ConfigurationScreen(mod, parent,
                //filter out customTotemWoodTypes from the config GUI
                (context, key, original) -> key.equals("customTotemWoodTypes") ? null : original));
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
}
