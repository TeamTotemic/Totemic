package pokefenn.totemic.neoforge;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.client.ModModelLayers;
import pokefenn.totemic.handler.ClientInitHandlers;
import pokefenn.totemic.handler.ClientInteract;
import pokefenn.totemic.handler.ClientRenderHandler;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModItems;
import pokefenn.totemic.item.MedicineBagItem;

@Mod(value = TotemicAPI.MOD_ID, dist = Dist.CLIENT)
public final class TotemicClientNeoMod {
    public TotemicClientNeoMod(IEventBus modBus, ModContainer container) {
        modBus.addListener(this::clientSetup);

        modBus.register(ClientInitHandlers.class);
        modBus.register(ModModelLayers.class);

        container.registerExtensionPoint(IConfigScreenFactory.class, (mod, parent) -> new ConfigurationScreen(mod, parent,
                //filter out customTotemWoodTypes from the config GUI
                (context, key, original) -> key.equals("customTotemWoodTypes") ? null : original));
    }

    @SuppressWarnings("deprecation")
    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            Sheets.addWoodType(ModBlocks.CEDAR_WOOD_TYPE);

            //Register item properties
            //Baykok Bow
            var pulling = ResourceLocation.withDefaultNamespace("pulling");
            var pull = ResourceLocation.withDefaultNamespace("pull");
            var bowStack = new ItemStack(Items.BOW);
            ItemProperties.register(ModItems.baykok_bow.get(), pulling, ItemProperties.getProperty(bowStack, pulling));
            ItemProperties.register(ModItems.baykok_bow.get(), pull, ItemProperties.getProperty(bowStack, pull));

            //Medicine Bag
            ItemPropertyFunction isOpenFunc = (stack, level, entity, seed) -> MedicineBagItem.isOpen(stack) ? 1.0F : 0.0F;
            var name = Totemic.resloc("open");
            ItemProperties.register(ModItems.medicine_bag.get(), name, isOpenFunc);
            ItemProperties.register(ModItems.creative_medicine_bag.get(), name, isOpenFunc);
        });

        IEventBus eventBus = NeoForge.EVENT_BUS;
        eventBus.register(ClientInteract.class);
        eventBus.register(ClientRenderHandler.class);
    }
}
