package pokefenn.totemic.init;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

import net.minecraft.core.Direction.Axis;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import pokefenn.totemic.api.TotemWoodType;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.TotemicRegistries;
import pokefenn.totemic.api.totem.RegisterTotemEffectsEvent;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.block.totem.TotemBaseBlock;
import pokefenn.totemic.block.totem.TotemPoleBlock;

public final class ModBlocks {
    public static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, TotemicAPI.MOD_ID);

    public static final RegistryObject<RotatedPillarBlock> cedar_log = REGISTER.register("cedar_log", () -> new RotatedPillarBlock(Properties.of(Material.WOOD, state -> {
        return state.getValue(RotatedPillarBlock.AXIS) == Axis.Y ? MaterialColor.COLOR_PINK : MaterialColor.COLOR_ORANGE;
    }).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> stripped_cedar_log = REGISTER.register("stripped_cedar_log", () -> new RotatedPillarBlock(Properties.of(Material.WOOD, MaterialColor.COLOR_PINK).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> cedar_wood = REGISTER.register("cedar_wood", () -> new RotatedPillarBlock(Properties.of(Material.WOOD, MaterialColor.COLOR_ORANGE).strength(2.0F).sound(SoundType.WOOD)));

    // Totem Effects registered with the RegisterTotemEffectsEvent are collected here. Later, the Totem Effects
    // are registered to the appropriate Forge registry.
    // After everything is done, we check that no Totem Effects have been registered the wrong way and then set
    // this field to null.
    // TODO: We don't need all of this if Forge some day allows registering things before blocks have been registered.
    private static @Nullable Map<ResourceLocation, TotemEffect> totemEffectsToRegister = new LinkedHashMap<>();

    private static Map<TotemWoodType, RegistryObject<TotemBaseBlock>> totemBases;
    private static Table<TotemWoodType, TotemEffect, RegistryObject<TotemPoleBlock>> totemPoles;

    public static Map<TotemWoodType, RegistryObject<TotemBaseBlock>> getTotemBases() {
        return totemBases;
    }

    public static Table<TotemWoodType, TotemEffect, RegistryObject<TotemPoleBlock>>getTotemPoles() {
        return totemPoles;
    }

    public static TotemBaseBlock getTotemBase(TotemWoodType woodType) {
        return totemBases.get(woodType).get();
    }

    public static TotemPoleBlock getTotemPole(TotemWoodType woodType, TotemEffect effect) {
        return totemPoles.get(woodType, effect).get();
    }

    @SubscribeEvent
    public static void init(RegisterEvent event) {
        if(!event.getRegistryKey().equals(ForgeRegistries.Keys.BLOCKS))
            return;

        internallyRegisterTotemEffects();

        var totemBasesBuilder = ImmutableMap.<TotemWoodType, RegistryObject<TotemBaseBlock>>builderWithExpectedSize(TotemWoodType.getWoodTypes().size());
        var totemPolesBuilder = ImmutableTable.<TotemWoodType, TotemEffect, RegistryObject<TotemPoleBlock>>builder();

        for(TotemWoodType woodType: TotemWoodType.getWoodTypes()) {
            Properties blockProperties = Properties.of(Material.WOOD, woodType.getWoodColor()).strength(2, 3).sound(SoundType.WOOD);

            TotemBaseBlock totemBase = new TotemBaseBlock(woodType, blockProperties);
            ResourceLocation totemBaseName = new ResourceLocation(TotemicAPI.MOD_ID, woodType.getName() + "_totem_base");
            event.getForgeRegistry().register(totemBaseName, totemBase);
            totemBasesBuilder.put(woodType, RegistryObject.create(totemBaseName, event.getForgeRegistry()));

            for(var entry: totemEffectsToRegister.entrySet()) {
                var totemEffectKey = entry.getKey();
                var totemEffect = entry.getValue();
                TotemPoleBlock totemPole = new TotemPoleBlock(woodType, totemEffect, blockProperties);
                ResourceLocation totemPoleName = new ResourceLocation(TotemicAPI.MOD_ID, woodType.getName() + "_totem_pole_" + totemEffectKey.getPath());
                event.getForgeRegistry().register(totemPoleName, totemPole);
                totemPolesBuilder.put(woodType, totemEffect, RegistryObject.create(totemPoleName, event.getForgeRegistry()));
            }
        }

        totemBases = totemBasesBuilder.build();
        totemPoles = totemPolesBuilder.build();
    }

    public static void setFireInfo() {
        try {
            FireBlock fire = (FireBlock) Blocks.FIRE;
            Method setFlammableM = ObfuscationReflectionHelper.findMethod(FireBlock.class, "m_53444_", Block.class, int.class, int.class);

            setFlammableM.invoke(fire, cedar_log.get(), 5, 5);
            setFlammableM.invoke(fire, stripped_cedar_log.get(), 5, 5);
            setFlammableM.invoke(fire, cedar_wood.get(), 5, 5);
        }
        catch(ReflectiveOperationException e) {
            throw new RuntimeException("Could not set flammability for Totemic blocks", e);
        }
    }

    // Fires the RegisterTotemEffectsEvent and collects them in the internal set
    private static void internallyRegisterTotemEffects() {
        FMLJavaModLoadingContext.get().getModEventBus().post(new RegisterTotemEffectsEvent((name, effect) -> {
            Objects.requireNonNull(name);
            Objects.requireNonNull(effect);
            totemEffectsToRegister.put(name, effect);
        }));
    }

    // Registers the collected Totem Effects to the Forge registry
    @SubscribeEvent
    public static void registerTotemEffects(RegisterEvent event) {
        event.register(TotemicRegistries.TOTEM_EFFECTS_KEY, helper -> {
            for(var entry: totemEffectsToRegister.entrySet())
                helper.register(entry.getKey(), entry.getValue());
        });
    }

    // Checks if all Totem Effects have been registered with the appropriate event and then frees up the internal set
    public static void checkRegisteredTotemEffects() {
        for(ResourceLocation key: TotemicRegistries.totemEffects().getKeys()) {
            if(!totemEffectsToRegister.containsKey(key)) {
                throw new IllegalStateException(
                        "The Totem Effect \"" + key + "\" has not been registered with Totemic's RegisterTotemEffectsEvent");
            }
        }
        totemEffectsToRegister = null; // Free up space
    }
}
