package pokefenn.totemic.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ObjectHolder;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemWoodType;
import pokefenn.totemic.api.totem.RegisterTotemEffectsEvent;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.block.totem.TotemBaseBlock;
import pokefenn.totemic.block.totem.TotemPoleBlock;

@ObjectHolder(Totemic.MOD_ID)
public final class ModBlocks {

    // Totem Effects registered with the RegisterTotemEffectsEvent are collected here. Later, the Totem Effects
    // are registered to the appropriate Forge registry.
    // After everything is done, we check that no Totem Effects have been registered the wrong way and then set
    // this field to null.
    // TODO: We don't need all of this if Forge some day allows registering things before blocks have been registered.
    private static @Nullable Set<TotemEffect> totemEffectsToRegister = new LinkedHashSet<>();

    // List of blocks for which an ItemBlock will be added
    private static final List<Block> blocksWithItemBlock = new ArrayList<>();

    private static final Map<TotemWoodType, TotemBaseBlock> totemBases = new HashMap<>(TotemWoodType.getWoodTypes().size());
    private static final Table<TotemWoodType, TotemEffect, TotemPoleBlock> totemPoles = HashBasedTable.create(TotemWoodType.getWoodTypes().size(), 16);

    @SubscribeEvent
    public static void init(RegistryEvent.Register<Block> event) {
        internallyRegisterTotemEffects();

        for(TotemWoodType woodType: TotemWoodType.getWoodTypes()) {
            Properties blockProperties = Properties.of(Material.WOOD, woodType.getWoodColor()).strength(2, 5).sound(SoundType.WOOD);

            TotemBaseBlock totemBase = new TotemBaseBlock(woodType, blockProperties);
            totemBase.setRegistryName(Totemic.MOD_ID, woodType.getName() + "_totem_base");

            event.getRegistry().register(totemBase);
            totemBases.put(woodType, totemBase);
            blocksWithItemBlock.add(totemBase);

            for(TotemEffect totemEffect: totemEffectsToRegister) {
                TotemPoleBlock totemPole = new TotemPoleBlock(woodType, totemEffect, blockProperties);
                totemPole.setRegistryName(Totemic.MOD_ID, woodType.getName() + "_totem_pole_" + totemEffect.getRegistryName().getPath());

                event.getRegistry().register(totemPole);
                totemPoles.put(woodType, totemEffect, totemPole);
                blocksWithItemBlock.add(totemPole);
            }
        }
    }

    public static List<Block> getBlocksWithItemBlock() {
        return blocksWithItemBlock;
    }

    public static Map<TotemWoodType, TotemBaseBlock> getTotemBases() {
        return totemBases;
    }

    public static Table<TotemWoodType, TotemEffect, TotemPoleBlock> getTotemPoles() {
        return totemPoles;
    }

    // Fires the RegisterTotemEffectsEvent and collects them in the internal set
    private static void internallyRegisterTotemEffects() {
        FMLJavaModLoadingContext.get().getModEventBus().post(new RegisterTotemEffectsEvent(effect -> {
            Objects.requireNonNull(effect);
            if(effect.getRegistryName() == null)
                throw new IllegalArgumentException("Registry name has not been set for Totem Effect " + effect);
            totemEffectsToRegister.add(effect);
        }));
    }

    // Registers the collected Totem Effects to the Forge registry
    @SubscribeEvent
    public static void registerTotemEffects(RegistryEvent.Register<TotemEffect> event) {
        for(TotemEffect effect: totemEffectsToRegister)
            event.getRegistry().register(effect);
    }

    // Checks if all Totem Effects have been registered with the appropriate event and then frees up the internal set
    public static void checkRegisteredTotemEffects() {
        for(TotemEffect effect: GameRegistry.findRegistry(TotemEffect.class)) {
            if(!totemEffectsToRegister.contains(effect)) {
                throw new IllegalStateException(
                        "The Totem Effect " + effect.getRegistryName() + " has not been registered with Totemic's RegisterTotemEffectsEvent");
            }
        }
        totemEffectsToRegister = null; // Free up space
    }
}
