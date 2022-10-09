package pokefenn.totemic.init;

import java.lang.reflect.Method;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

import net.minecraft.core.Direction.Axis;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import pokefenn.totemic.api.TotemWoodType;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.apiimpl.registry.RegistryApiImpl;
import pokefenn.totemic.block.grower.CedarTreeGrower;
import pokefenn.totemic.block.music.DrumBlock;
import pokefenn.totemic.block.totem.TotemBaseBlock;
import pokefenn.totemic.block.totem.TotemPoleBlock;

public final class ModBlocks {
    public static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, TotemicAPI.MOD_ID);

    public static final RegistryObject<RotatedPillarBlock> cedar_log = REGISTER.register("cedar_log", () -> new RotatedPillarBlock(Properties.of(Material.WOOD, state -> {
        return state.getValue(RotatedPillarBlock.AXIS) == Axis.Y ? MaterialColor.COLOR_PINK : MaterialColor.COLOR_ORANGE;
    }).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> stripped_cedar_log = REGISTER.register("stripped_cedar_log", () -> new RotatedPillarBlock(Properties.of(Material.WOOD, MaterialColor.COLOR_PINK).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> cedar_wood = REGISTER.register("cedar_wood", () -> new RotatedPillarBlock(Properties.of(Material.WOOD, MaterialColor.COLOR_ORANGE).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> stripped_cedar_wood = REGISTER.register("stripped_cedar_wood", () -> new RotatedPillarBlock(Properties.of(Material.WOOD, MaterialColor.COLOR_PINK).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<LeavesBlock> cedar_leaves = REGISTER.register("cedar_leaves", () -> new LeavesBlock(Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn((s, g, p, type) -> type == EntityType.OCELOT || type == EntityType.PARROT).isSuffocating((s, g, p) -> false).isViewBlocking((s, g, p) -> false)));
    public static final RegistryObject<SaplingBlock> cedar_sapling = REGISTER.register("cedar_sapling", () -> new SaplingBlock(new CedarTreeGrower(), Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<DrumBlock> drum = REGISTER.register("drum", () -> new DrumBlock(Properties.of(Material.WOOD).strength(2.0F).sound(SoundType.WOOD)));

    private static Map<TotemWoodType, RegistryObject<TotemBaseBlock>> totemBases;
    private static Table<TotemWoodType, TotemEffect, RegistryObject<TotemPoleBlock>> totemPoles;

    public static Map<TotemWoodType, RegistryObject<TotemBaseBlock>> getTotemBases() {
        return totemBases;
    }

    public static Table<TotemWoodType, TotemEffect, RegistryObject<TotemPoleBlock>> getTotemPoles() {
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

        RegistryApiImpl.INSTANCE.registerTotemEffects();

        var totemBasesBuilder = ImmutableMap.<TotemWoodType, RegistryObject<TotemBaseBlock>>builderWithExpectedSize(TotemWoodType.getWoodTypes().size());
        var totemPolesBuilder = ImmutableTable.<TotemWoodType, TotemEffect, RegistryObject<TotemPoleBlock>>builder();

        for(TotemWoodType woodType: TotemWoodType.getWoodTypes()) {
            Properties blockProperties = Properties.of(Material.WOOD, woodType.getWoodColor()).strength(2, 3).sound(SoundType.WOOD);

            TotemBaseBlock totemBase = new TotemBaseBlock(woodType, blockProperties);
            ResourceLocation totemBaseName = new ResourceLocation(TotemicAPI.MOD_ID, woodType.getName() + "_totem_base");
            event.getForgeRegistry().register(totemBaseName, totemBase);
            totemBasesBuilder.put(woodType, RegistryObject.create(totemBaseName, event.getForgeRegistry()));

            for(TotemEffect effect: TotemicAPI.get().registry().totemEffects().values()) {
                ResourceLocation effectName = effect.getRegistryName();
                TotemPoleBlock totemPole = new TotemPoleBlock(woodType, effect, blockProperties);
                ResourceLocation totemPoleName = new ResourceLocation(effectName.getNamespace(), woodType.getName() + "_totem_pole_" + effectName.getPath());
                event.getForgeRegistry().register(totemPoleName, totemPole);
                totemPolesBuilder.put(woodType, effect, RegistryObject.create(totemPoleName, event.getForgeRegistry()));
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
}
