package pokefenn.totemic.init;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.core.Direction.Axis;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import pokefenn.totemic.PlatformRegistryHelper;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.block.DummyTipiBlock;
import pokefenn.totemic.block.TipiBlock;
import pokefenn.totemic.block.TotemTorchBlock;
import pokefenn.totemic.block.music.DrumBlock;
import pokefenn.totemic.block.music.WindChimeBlock;
import pokefenn.totemic.block.totem.TotemBaseBlock;
import pokefenn.totemic.block.totem.TotemPoleBlock;

public final class ModBlocks {
    public static final BlockSetType CEDAR_BLOCK_SET_TYPE = BlockSetType.register(new BlockSetType("totemic:cedar"));
    public static final WoodType CEDAR_WOOD_TYPE = WoodType.register(new WoodType("totemic:cedar", CEDAR_BLOCK_SET_TYPE));

    public static final PlatformRegistryHelper<Block> REGISTER = Totemic.platform().createRegistryHelper(Registries.BLOCK);

    // Cedar-related blocks
    public static final Supplier<RotatedPillarBlock> stripped_cedar_log = register(
            "stripped_cedar_log",
            RotatedPillarBlock::new,
            () -> Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD)
    );
    public static final Supplier<RotatedPillarBlock> cedar_log = register(
            "cedar_log",
            RotatedPillarBlock::new,
            () -> Properties.of()
                .mapColor(state -> state.getValue(RotatedPillarBlock.AXIS) == Axis.Y ? MapColor.COLOR_PINK : MapColor.COLOR_ORANGE)
                .ignitedByLava()
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(SoundType.WOOD)
    );
    public static final Supplier<RotatedPillarBlock> stripped_cedar_wood = register(
            "stripped_cedar_wood",
            RotatedPillarBlock::new,
            () -> Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD)
    );
    public static final Supplier<RotatedPillarBlock> cedar_wood = register(
            "cedar_wood",
            RotatedPillarBlock::new,
            () -> Properties.of().mapColor(MapColor.COLOR_ORANGE).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD)
    );
    public static final Supplier<LeavesBlock> cedar_leaves = register(
            "cedar_leaves",
            p -> new TintedParticleLeavesBlock(0.01F, p),
            () -> Properties.of()
                .mapColor(MapColor.PLANT)
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY)
                .strength(0.2F)
                .randomTicks()
                .sound(SoundType.GRASS)
                .noOcclusion()
                .isValidSpawn((_, _, _, type) -> type == EntityType.OCELOT || type == EntityType.PARROT)
                .isSuffocating((_, _, _) -> false)
                .isViewBlocking((_, _, _) -> false)
                .isRedstoneConductor((_, _, _) -> false)
    );
    public static final Supplier<SaplingBlock> cedar_sapling = register(
            "cedar_sapling",
            p -> new SaplingBlock(new TreeGrower("totemic:cedar", Optional.empty(), Optional.of(ModResources.CEDAR_TREE_FEATURE), Optional.empty()), p),
            () -> Properties.of().mapColor(MapColor.PLANT).ignitedByLava().pushReaction(PushReaction.DESTROY).noCollision().randomTicks().instabreak().sound(SoundType.GRASS)
    );
    public static final Supplier<Block> cedar_planks = register(
            "cedar_planks",
            Block::new,
            () -> Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD)
    );
    public static final Supplier<ButtonBlock> cedar_button = register(
            "cedar_button",
            p -> new ButtonBlock(CEDAR_BLOCK_SET_TYPE, 30, p),
            () -> Properties.of().pushReaction(PushReaction.DESTROY).noCollision().strength(0.5F)
    );
    public static final Supplier<FenceBlock> cedar_fence = register(
            "cedar_fence",
            FenceBlock::new,
            () -> Properties.of().mapColor(MapColor.COLOR_PINK).forceSolidOn().ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD)
    );
    public static final Supplier<FenceGateBlock> cedar_fence_gate = register(
            "cedar_fence_gate",
            p -> new FenceGateBlock(CEDAR_WOOD_TYPE, p),
            () -> Properties.of().mapColor(MapColor.COLOR_PINK).forceSolidOn().ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD)
    );
    public static final Supplier<PressurePlateBlock> cedar_pressure_plate = register(
            "cedar_pressure_plate",
            p -> new PressurePlateBlock(CEDAR_BLOCK_SET_TYPE, p),
            () -> Properties.of().mapColor(MapColor.COLOR_PINK).forceSolidOn().ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollision().strength(0.5F).pushReaction(PushReaction.DESTROY)
    );
    public static final Supplier<StandingSignBlock> cedar_sign = register(
            "cedar_sign",
            p -> new StandingSignBlock(CEDAR_WOOD_TYPE, p),
            () -> Properties.of().mapColor(MapColor.COLOR_PINK).forceSolidOn().ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F)
    );
    public static final Supplier<WallSignBlock> cedar_wall_sign = register(
            "cedar_wall_sign",
            p -> new WallSignBlock(CEDAR_WOOD_TYPE, p),
            () -> Properties.of()
                .mapColor(MapColor.COLOR_PINK)
                .forceSolidOn()
                .ignitedByLava()
                .instrument(NoteBlockInstrument.BASS)
                .noCollision()
                .strength(1.0F)
                .overrideLootTable(cedar_sign.get().getLootTable())
                .overrideDescription(cedar_sign.get().getDescriptionId())
    );
    public static final Supplier<CeilingHangingSignBlock> cedar_hanging_sign = register(
            "cedar_hanging_sign",
            p -> new CeilingHangingSignBlock(CEDAR_WOOD_TYPE, p),
            () -> Properties.of().mapColor(MapColor.COLOR_PINK).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).ignitedByLava()
    );
    public static final Supplier<WallHangingSignBlock> cedar_wall_hanging_sign = register(
            "cedar_wall_hanging_sign",
            p -> new WallHangingSignBlock(CEDAR_WOOD_TYPE, p),
            () -> Properties.of()
                .mapColor(MapColor.COLOR_PINK)
                .forceSolidOn()
                .instrument(NoteBlockInstrument.BASS)
                .noCollision()
                .strength(1.0F)
                .ignitedByLava()
                .overrideLootTable(cedar_hanging_sign.get().getLootTable())
                .overrideDescription(cedar_hanging_sign.get().getDescriptionId())
    );
    public static final Supplier<SlabBlock> cedar_slab = register(
            "cedar_slab",
            SlabBlock::new,
            () -> Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD)
    );
    @SuppressWarnings("deprecation")
    public static final Supplier<StairBlock> cedar_stairs = register(
            "cedar_stairs",
            p -> new StairBlock(cedar_planks.get().defaultBlockState(), p),
            () -> Properties.ofLegacyCopy(cedar_planks.get())
    );
    public static final Supplier<DoorBlock> cedar_door = register(
            "cedar_door",
            p -> new DoorBlock(CEDAR_BLOCK_SET_TYPE, p),
            () -> Properties.of()
                .mapColor(MapColor.COLOR_PINK)
                .ignitedByLava()
                .instrument(NoteBlockInstrument.BASS)
                .strength(3.0F)
                .sound(SoundType.WOOD)
                .noOcclusion()
                .pushReaction(PushReaction.DESTROY)
    );
    public static final Supplier<TrapDoorBlock> cedar_trapdoor = register(
            "cedar_trapdoor",
            p -> new TrapDoorBlock(CEDAR_BLOCK_SET_TYPE, p),
            () -> Properties.of()
                .mapColor(MapColor.COLOR_PINK)
                .ignitedByLava()
                .instrument(NoteBlockInstrument.BASS)
                .strength(3.0F)
                .sound(SoundType.WOOD)
                .noOcclusion()
                .isValidSpawn((_, _, _, _) -> false)
    );
    public static final Supplier<FlowerPotBlock> potted_cedar_sapling = register(
            "potted_cedar_sapling",
            p -> new FlowerPotBlock(cedar_sapling.get(), p),
            () -> Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)
    );

    // Other blocks
    public static final Supplier<DrumBlock> drum = register(
            "drum",
            DrumBlock::new,
            () -> Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD)
    );
    public static final Supplier<WindChimeBlock> wind_chime = register(
            "wind_chime",
            WindChimeBlock::new,
            () -> Properties.of().mapColor(MapColor.METAL).strength(1.5F).sound(SoundType.METAL)
    );
    public static final Supplier<TotemTorchBlock> totem_torch = register(
            "totem_torch",
            TotemTorchBlock::new,
            () -> Properties.of().pushReaction(PushReaction.DESTROY).strength(0.05F).lightLevel(_ -> 15).sound(SoundType.WOOD).noCollision()
    );
    public static final Supplier<TipiBlock> tipi = register(
            "tipi",
            TipiBlock::new,
            () -> Properties.of().mapColor(MapColor.WOOL).ignitedByLava().strength(0.2F).sound(SoundType.WOOL).noOcclusion()
    );
    public static final Supplier<DummyTipiBlock> dummy_tipi = register(
            "dummy_tipi",
            DummyTipiBlock::new,
            () -> Properties.of()
                .mapColor(MapColor.WOOL)
                .ignitedByLava()
                .strength(0.2F)
                .sound(SoundType.WOOL)
                .noOcclusion()
                .isValidSpawn((_, _, _, _) -> false)
                .isRedstoneConductor((_, _, _) -> false)
                .isSuffocating((_, _, _) -> false)
                .isViewBlocking((_, _, _) -> false)
                .pushReaction(PushReaction.BLOCK)
                .noLootTable()
                .overrideDescription(tipi.get().getDescriptionId())
    );
    public static final Supplier<TotemBaseBlock> totem_base = register(
            "totem_base",
            TotemBaseBlock::new,
            () -> Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2, 3).sound(SoundType.WOOD)
    );
    public static final Supplier<TotemPoleBlock> totem_pole = register(
            "totem_pole",
            TotemPoleBlock::new,
            () -> Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2, 3).sound(SoundType.WOOD)
    );

    private static <T extends Block> Supplier<T> register(String name, Function<Properties, T> factory, Supplier<Properties> properties) {
        var key = ResourceKey.create(Registries.BLOCK, Totemic.resloc(name));
        return REGISTER.register(name, () -> factory.apply(properties.get().setId(key)));
    }

    public static void setFireInfo() {
        FireBlock fire = (FireBlock) Blocks.FIRE;
        fire.setFlammable(cedar_log.get(), 5, 5);
        fire.setFlammable(stripped_cedar_log.get(), 5, 5);
        fire.setFlammable(cedar_wood.get(), 5, 5);
        fire.setFlammable(stripped_cedar_wood.get(), 5, 5);
        fire.setFlammable(cedar_leaves.get(), 30, 60);
        fire.setFlammable(cedar_planks.get(), 5, 20);
        fire.setFlammable(cedar_fence.get(), 5, 20);
        fire.setFlammable(cedar_fence_gate.get(), 5, 20);
        fire.setFlammable(cedar_slab.get(), 5, 20);
        fire.setFlammable(cedar_stairs.get(), 5, 20);
        fire.setFlammable(drum.get(), 5, 20);
        fire.setFlammable(totem_base.get(), 5, 20);
        fire.setFlammable(totem_pole.get(), 5, 20);
    }

    public static void addBlockEntityValidBlocks(BiConsumer<BlockEntityType<?>, Block> modifier) {
        modifier.accept(BlockEntityType.SIGN, ModBlocks.cedar_sign.get());
        modifier.accept(BlockEntityType.SIGN, ModBlocks.cedar_wall_sign.get());
        modifier.accept(BlockEntityType.HANGING_SIGN, ModBlocks.cedar_hanging_sign.get());
        modifier.accept(BlockEntityType.HANGING_SIGN, ModBlocks.cedar_wall_hanging_sign.get());
    }
}
