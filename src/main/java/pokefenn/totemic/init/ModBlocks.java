package pokefenn.totemic.init;

import java.util.HashSet;
import java.util.Optional;

import net.minecraft.core.Direction.Axis;
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
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.block.DummyTipiBlock;
import pokefenn.totemic.block.StrippableLogBlock;
import pokefenn.totemic.block.TipiBlock;
import pokefenn.totemic.block.TotemTorchBlock;
import pokefenn.totemic.block.music.DrumBlock;
import pokefenn.totemic.block.music.WindChimeBlock;
import pokefenn.totemic.block.totem.TotemBaseBlock;
import pokefenn.totemic.block.totem.TotemPoleBlock;

public final class ModBlocks {
    public static final BlockSetType CEDAR_BLOCK_SET_TYPE = BlockSetType.register(new BlockSetType("totemic:cedar"));
    public static final WoodType CEDAR_WOOD_TYPE = WoodType.register(new WoodType("totemic:cedar", CEDAR_BLOCK_SET_TYPE));

    public static final DeferredRegister.Blocks REGISTER = DeferredRegister.createBlocks(TotemicAPI.MOD_ID);

    public static final DeferredBlock<RotatedPillarBlock> stripped_cedar_log = REGISTER.register("stripped_cedar_log", () -> new RotatedPillarBlock(Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<StrippableLogBlock> cedar_log = REGISTER.register("cedar_log", () -> new StrippableLogBlock(stripped_cedar_log, Properties.of().mapColor(state -> {
        return state.getValue(RotatedPillarBlock.AXIS) == Axis.Y ? MapColor.COLOR_PINK : MapColor.COLOR_ORANGE;
    }).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<RotatedPillarBlock> stripped_cedar_wood = REGISTER.register("stripped_cedar_wood", () -> new RotatedPillarBlock(Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<StrippableLogBlock> cedar_wood = REGISTER.register("cedar_wood", () -> new StrippableLogBlock(stripped_cedar_wood, Properties.of().mapColor(MapColor.COLOR_ORANGE).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<LeavesBlock> cedar_leaves = REGISTER.register("cedar_leaves", () -> new LeavesBlock(Properties.of().mapColor(MapColor.PLANT).ignitedByLava().pushReaction(PushReaction.DESTROY).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn((s, g, p, type) -> type == EntityType.OCELOT || type == EntityType.PARROT).isSuffocating((s, g, p) -> false).isViewBlocking((s, g, p) -> false)));
    public static final DeferredBlock<SaplingBlock> cedar_sapling = REGISTER.register("cedar_sapling", () -> new SaplingBlock(new TreeGrower("totemic:cedar", Optional.empty(), Optional.of(ModResources.CEDAR_TREE_FEATURE), Optional.empty()), Properties.of().mapColor(MapColor.PLANT).ignitedByLava().pushReaction(PushReaction.DESTROY).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
    public static final DeferredBlock<Block> cedar_planks = REGISTER.register("cedar_planks", () -> new Block(Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<ButtonBlock> cedar_button = REGISTER.register("cedar_button", () -> new ButtonBlock(CEDAR_BLOCK_SET_TYPE, 30, Properties.of().pushReaction(PushReaction.DESTROY).noCollission().strength(0.5F).sound(SoundType.WOOD)));
    public static final DeferredBlock<FenceBlock> cedar_fence = REGISTER.register("cedar_fence", () -> new FenceBlock(Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<FenceGateBlock> cedar_fence_gate = REGISTER.register("cedar_fence_gate", () -> new FenceGateBlock(CEDAR_WOOD_TYPE, Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<PressurePlateBlock> cedar_pressure_plate = REGISTER.register("cedar_pressure_plate", () -> new PressurePlateBlock(CEDAR_BLOCK_SET_TYPE, Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(0.5F).sound(SoundType.WOOD)));
    public static final DeferredBlock<StandingSignBlock> cedar_sign = REGISTER.register("cedar_sign", () -> new StandingSignBlock(CEDAR_WOOD_TYPE, Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<WallSignBlock> cedar_wall_sign = REGISTER.register("cedar_wall_sign", () -> new WallSignBlock(CEDAR_WOOD_TYPE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(cedar_sign)));
    public static final DeferredBlock<CeilingHangingSignBlock> cedar_hanging_sign = REGISTER.register("cedar_hanging_sign", () -> new CeilingHangingSignBlock(CEDAR_WOOD_TYPE, Properties.of().mapColor(MapColor.COLOR_PINK).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava()));
    public static final DeferredBlock<WallHangingSignBlock> cedar_wall_hanging_sign = REGISTER.register("cedar_wall_hanging_sign", () -> new WallHangingSignBlock(CEDAR_WOOD_TYPE, Properties.of().mapColor(MapColor.COLOR_PINK).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).lootFrom(cedar_hanging_sign).ignitedByLava()));
    public static final DeferredBlock<SlabBlock> cedar_slab = REGISTER.register("cedar_slab", () -> new SlabBlock(Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<StairBlock> cedar_stairs = REGISTER.register("cedar_stairs", () -> new StairBlock(() -> cedar_planks.get().defaultBlockState(), Properties.ofFullCopy(cedar_planks.get())));
    public static final DeferredBlock<DoorBlock> cedar_door = REGISTER.register("cedar_door", () -> new DoorBlock(CEDAR_BLOCK_SET_TYPE, Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final DeferredBlock<TrapDoorBlock> cedar_trapdoor = REGISTER.register("cedar_trapdoor", () -> new TrapDoorBlock(CEDAR_BLOCK_SET_TYPE, Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn((s, g, p, t) -> false)));
    public static final DeferredBlock<FlowerPotBlock> potted_cedar_sapling = REGISTER.register("potted_cedar_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, cedar_sapling, Properties.of().pushReaction(PushReaction.DESTROY).instabreak().noOcclusion()));
    public static final DeferredBlock<DrumBlock> drum = REGISTER.register("drum", () -> new DrumBlock(Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<WindChimeBlock> wind_chime = REGISTER.register("wind_chime", () -> new WindChimeBlock(Properties.of().mapColor(MapColor.METAL).strength(1.5F).sound(SoundType.METAL)));
    public static final DeferredBlock<TotemTorchBlock> totem_torch = REGISTER.register("totem_torch", () -> new TotemTorchBlock(Properties.of().pushReaction(PushReaction.DESTROY).strength(0.05F).lightLevel(s -> 15).sound(SoundType.WOOD).noCollission()));
    public static final DeferredBlock<TipiBlock> tipi = REGISTER.register("tipi", () -> new TipiBlock(Properties.of().mapColor(MapColor.WOOL).ignitedByLava().strength(0.2F).sound(SoundType.WOOL).noOcclusion()));
    public static final DeferredBlock<DummyTipiBlock> dummy_tipi = REGISTER.register("dummy_tipi", () -> new DummyTipiBlock(Properties.of().mapColor(MapColor.WOOL).ignitedByLava().strength(0.2F).sound(SoundType.WOOL).noOcclusion().isValidSpawn((s, g, p, t) -> false).isRedstoneConductor((s, g, p) -> false).isSuffocating((s, g, p) -> false).isViewBlocking((s, g, p) -> false).noLootTable()));
    public static final DeferredBlock<TotemBaseBlock> totem_base = REGISTER.register("totem_base", () -> new TotemBaseBlock(Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2, 3).sound(SoundType.WOOD)));
    public static final DeferredBlock<TotemPoleBlock> totem_pole = REGISTER.register("totem_pole", () -> new TotemPoleBlock(Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2, 3).sound(SoundType.WOOD)));

    public static void addPlantsToFlowerPot() {
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(cedar_sapling.getId(), potted_cedar_sapling);
    }

    public static void setFireInfo() {
        FireBlock fire = (FireBlock) Blocks.FIRE;
        //We only need to call this method for blocks we don't define our own class for
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
    }

    //Modifies the validBlocks of the sign block entity types to add our own sign blocks to it
    public static void addCedarSignToSignBlockEntityType() {
        var signValidBlocks = BlockEntityType.SIGN.validBlocks;
        if(!(signValidBlocks instanceof HashSet)) { //another mod might have already made the set mutable
            BlockEntityType.SIGN.validBlocks = signValidBlocks = new HashSet<>(signValidBlocks); //if not, copy into a mutable set
        }
        signValidBlocks.add(ModBlocks.cedar_sign.get());
        signValidBlocks.add(ModBlocks.cedar_wall_sign.get());

        var hangingSignValidBlocks = BlockEntityType.HANGING_SIGN.validBlocks;
        if(!(hangingSignValidBlocks instanceof HashSet)) {
            BlockEntityType.HANGING_SIGN.validBlocks = hangingSignValidBlocks = new HashSet<>(hangingSignValidBlocks);
        }
        hangingSignValidBlocks.add(ModBlocks.cedar_hanging_sign.get());
        hangingSignValidBlocks.add(ModBlocks.cedar_wall_hanging_sign.get());
    }
}
