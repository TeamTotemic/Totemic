package pokefenn.totemic.init;

import java.util.*;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.Block.Properties;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ObjectHolder;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.WoodType;
import pokefenn.totemic.api.totem.RegisterTotemEffectsEvent;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.block.totem.BlockTotemBase;
import pokefenn.totemic.block.totem.BlockTotemPole;

@ObjectHolder(Totemic.MOD_ID)
public final class ModBlocks {

    //Totem Effects registered with the RegisterTotemEffectsEvent are collected here. Later, the Totem Effects
    //are registered to the appropriate Forge registry.
    //After everything is done, we check that no Totem Effects have been registered the wrong way and then set
    //this field to null.
    //TODO: We don't need all of this if Forge some day allows registering things before blocks have been registered.
    private static @Nullable Set<TotemEffect> totemEffectsToRegister = new LinkedHashSet<>();

    //List of blocks for which an ItemBlock will be added
    private static final List<Block> blocksWithItemBlock = new ArrayList<>();

    @SubscribeEvent
    public static void init(RegistryEvent.Register<Block> event) {
        internallyRegisterTotemEffects();

        for(WoodType woodType: WoodType.getWoodTypes()) {
            Properties blockProperties = Properties.create(Material.WOOD, woodType.getWoodColor()).hardnessAndResistance(2, 5).sound(SoundType.WOOD);

            Block totemBase = new BlockTotemBase(woodType, blockProperties).setRegistryName(Totemic.MOD_ID, woodType.getName() + "_totem_base");
            event.getRegistry().register(totemBase);
            blocksWithItemBlock.add(totemBase);

            for(TotemEffect totemEffect: totemEffectsToRegister) {
                Block totemPole = new BlockTotemPole(woodType, totemEffect, blockProperties).setRegistryName(Totemic.MOD_ID, woodType.getName() + "_totem_pole_" + totemEffect.getRegistryName().getPath());
                event.getRegistry().register(totemPole);
                blocksWithItemBlock.add(totemPole);
            }
        }
    }

    public static List<Block> getBlocksWithItemBlock() {
        return blocksWithItemBlock;
    }

    //Fires the RegisterTotemEffectsEvent and collects them in the internal set
    private static void internallyRegisterTotemEffects() {
        FMLJavaModLoadingContext.get().getModEventBus().post(new RegisterTotemEffectsEvent(effect -> {
            Objects.requireNonNull(effect);
            if(effect.getRegistryName() == null)
                throw new IllegalArgumentException("Registry name has not been set for Totem Effect " + effect);
            totemEffectsToRegister.add(effect);
        }));
    }

    //Registers the collected Totem Effects to the Forge registry
    @SubscribeEvent
    public static void registerTotemEffects(RegistryEvent.Register<TotemEffect> event) {
        for(TotemEffect effect: totemEffectsToRegister)
            event.getRegistry().register(effect);
    }

    //Checks if all Totem Effects have been registered with the appropriate event and then frees up the internal set
    public static void checkRegisteredTotemEffects() {
        for(TotemEffect effect: GameRegistry.findRegistry(TotemEffect.class)) {
            if(!totemEffectsToRegister.contains(effect)) {
                throw new IllegalStateException("The Totem Effect " + effect.getRegistryName() +
                        " has not been registered with Totemic's RegisterTotemEffectsEvent");
            }
        }
        totemEffectsToRegister = null; //Free up space
    }
}
