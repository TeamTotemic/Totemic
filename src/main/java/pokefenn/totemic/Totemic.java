package pokefenn.totemic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.Block.Properties;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.ObjectHolderRegistry;
import pokefenn.totemic.api.WoodType;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.block.totem.BlockTotemBase;
import pokefenn.totemic.block.totem.BlockTotemPole;
import pokefenn.totemic.client.ModelBakeHandler;
import pokefenn.totemic.client.ModelTotemPoleLoader;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModContent;
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
        modBus.register(ModContent.class);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(Totemic::registerTotemBlocks);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        ModelLoaderRegistry.registerLoader(new ModelTotemPoleLoader());
        MinecraftForge.EVENT_BUS.register(ModelBakeHandler.class);
    }

    //Registers Totem Base and Totem Pole blocks
    private static void registerTotemBlocks() {
        ForgeRegistry<Block> blockRegistry = (ForgeRegistry<Block>) ForgeRegistries.BLOCKS;
        ForgeRegistry<Item> itemRegistry = (ForgeRegistry<Item>) ForgeRegistries.ITEMS;
        blockRegistry.unfreeze(); //FIXME: How to do this properly?
        itemRegistry.unfreeze();

        for(WoodType woodType: WoodType.getWoodTypes()) {
            Properties blockProperties = Properties.create(Material.WOOD, woodType.getWoodColor()).hardnessAndResistance(2, 5).sound(SoundType.WOOD);

            Block totemBase = new BlockTotemBase(woodType, blockProperties).setRegistryName(MOD_ID, woodType.getName() + "_totem_base");
            blockRegistry.register(totemBase);
            itemRegistry.register(ModItems.makeItemBlock(totemBase));

            for(TotemEffect totemEffect: GameRegistry.findRegistry(TotemEffect.class)) {
                Block totemPole = new BlockTotemPole(woodType, totemEffect, blockProperties).setRegistryName(MOD_ID, woodType.getName() + "_totem_pole_" + totemEffect.getRegistryName().getPath());
                blockRegistry.register(totemPole);
                itemRegistry.register(ModItems.makeItemBlock(totemPole));
            }
        }

        itemRegistry.freeze();
        blockRegistry.freeze();
        ObjectHolderRegistry.applyObjectHolders(rl -> rl.equals(itemRegistry.getRegistryName()) || rl.equals(blockRegistry.getRegistryName()));
    }
}
