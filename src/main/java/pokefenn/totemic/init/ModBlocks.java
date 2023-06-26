package pokefenn.totemic.init;

import java.util.HashSet;

import net.minecraft.core.Direction.Axis;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.WoodButtonBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.block.DummyTipiBlock;
import pokefenn.totemic.block.StrippableLogBlock;
import pokefenn.totemic.block.TipiBlock;
import pokefenn.totemic.block.TotemTorchBlock;
import pokefenn.totemic.block.music.DrumBlock;
import pokefenn.totemic.block.music.WindChimeBlock;
import pokefenn.totemic.block.totem.TotemBaseBlock;
import pokefenn.totemic.block.totem.TotemPoleBlock;
import pokefenn.totemic.world.CedarTreeGrower;

public final class ModBlocks {
    public static final WoodType CEDAR_WOOD_TYPE = WoodType.register(WoodType.create("totemic:cedar"));

    public static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, TotemicAPI.MOD_ID);

    public static final RegistryObject<RotatedPillarBlock> stripped_cedar_log = REGISTER.register("stripped_cedar_log", () -> new RotatedPillarBlock(Properties.of(Material.WOOD, MaterialColor.COLOR_PINK).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<StrippableLogBlock> cedar_log = REGISTER.register("cedar_log", () -> new StrippableLogBlock(stripped_cedar_log, Properties.of(Material.WOOD, state -> {
        return state.getValue(RotatedPillarBlock.AXIS) == Axis.Y ? MaterialColor.COLOR_PINK : MaterialColor.COLOR_ORANGE;
    }).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> stripped_cedar_wood = REGISTER.register("stripped_cedar_wood", () -> new RotatedPillarBlock(Properties.of(Material.WOOD, MaterialColor.COLOR_PINK).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<StrippableLogBlock> cedar_wood = REGISTER.register("cedar_wood", () -> new StrippableLogBlock(stripped_cedar_wood, Properties.of(Material.WOOD, MaterialColor.COLOR_ORANGE).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<LeavesBlock> cedar_leaves = REGISTER.register("cedar_leaves", () -> new LeavesBlock(Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn((s, g, p, type) -> type == EntityType.OCELOT || type == EntityType.PARROT).isSuffocating((s, g, p) -> false).isViewBlocking((s, g, p) -> false)));
    public static final RegistryObject<SaplingBlock> cedar_sapling = REGISTER.register("cedar_sapling", () -> new SaplingBlock(new CedarTreeGrower(), Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> cedar_planks = REGISTER.register("cedar_planks", () -> new Block(Properties.of(Material.WOOD, MaterialColor.COLOR_PINK).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<WoodButtonBlock> cedar_button = REGISTER.register("cedar_button", () -> new WoodButtonBlock(Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<FenceBlock> cedar_fence = REGISTER.register("cedar_fence", () -> new FenceBlock(Properties.of(Material.WOOD, MaterialColor.COLOR_PINK).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<FenceGateBlock> cedar_fence_gate = REGISTER.register("cedar_fence_gate", () -> new FenceGateBlock(Properties.of(Material.WOOD, MaterialColor.COLOR_PINK).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<PressurePlateBlock> cedar_pressure_plate = REGISTER.register("cedar_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Properties.of(Material.WOOD, MaterialColor.COLOR_PINK).noCollission().strength(0.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<StandingSignBlock> cedar_sign = REGISTER.register("cedar_sign", () -> new StandingSignBlock(Properties.of(Material.WOOD, MaterialColor.COLOR_PINK).noCollission().strength(1.0F).sound(SoundType.WOOD), CEDAR_WOOD_TYPE));
    public static final RegistryObject<WallSignBlock> cedar_wall_sign = REGISTER.register("cedar_wall_sign", () -> new WallSignBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_PINK).noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(cedar_sign), CEDAR_WOOD_TYPE));
    public static final RegistryObject<SlabBlock> cedar_slab = REGISTER.register("cedar_slab", () -> new SlabBlock(Properties.of(Material.WOOD, MaterialColor.COLOR_PINK).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<StairBlock> cedar_stairs = REGISTER.register("cedar_stairs", () -> new StairBlock(() -> cedar_planks.get().defaultBlockState(), Properties.copy(cedar_planks.get())));
    public static final RegistryObject<DoorBlock> cedar_door = REGISTER.register("cedar_door", () -> new DoorBlock(Properties.of(Material.WOOD, MaterialColor.COLOR_PINK).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistryObject<TrapDoorBlock> cedar_trapdoor = REGISTER.register("cedar_trapdoor", () -> new TrapDoorBlock(Properties.of(Material.WOOD, MaterialColor.COLOR_PINK).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn((s, g, p, t) -> false)));
    public static final RegistryObject<FlowerPotBlock> potted_cedar_sapling = REGISTER.register("potted_cedar_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, cedar_sapling, Properties.of(Material.DECORATION).instabreak().noOcclusion()));
    public static final RegistryObject<DrumBlock> drum = REGISTER.register("drum", () -> new DrumBlock(Properties.of(Material.WOOD).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<WindChimeBlock> wind_chime = REGISTER.register("wind_chime", () -> new WindChimeBlock(Properties.of(Material.METAL).strength(1.5F).sound(SoundType.METAL)));
    public static final RegistryObject<TotemTorchBlock> totem_torch = REGISTER.register("totem_torch", () -> new TotemTorchBlock(Properties.of(Material.DECORATION).strength(0.05F).lightLevel(s -> 15).sound(SoundType.WOOD).noCollission()));
    public static final RegistryObject<TipiBlock> tipi = REGISTER.register("tipi", () -> new TipiBlock(Properties.of(Material.CLOTH_DECORATION).strength(0.2F).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<DummyTipiBlock> dummy_tipi = REGISTER.register("dummy_tipi", () -> new DummyTipiBlock(Properties.of(Material.CLOTH_DECORATION).strength(0.2F).sound(SoundType.WOOL).noOcclusion().isValidSpawn((s, g, p, t) -> false).isRedstoneConductor((s, g, p) -> false).isSuffocating((s, g, p) -> false).isViewBlocking((s, g, p) -> false).noLootTable()));
    public static final RegistryObject<TotemBaseBlock> totem_base = REGISTER.register("totem_base", () -> new TotemBaseBlock(Properties.of(Material.WOOD).strength(2, 3).sound(SoundType.WOOD)));
    public static final RegistryObject<TotemPoleBlock> totem_pole = REGISTER.register("totem_pole", () -> new TotemPoleBlock(Properties.of(Material.WOOD).strength(2, 3).sound(SoundType.WOOD)));

    private static BlockFamily CEDAR_FAMILY;

    public static BlockFamily getCedarBlockFamily() {
        return CEDAR_FAMILY;
    }

    @SubscribeEvent
    public static void init(RegisterEvent event) {
        if(!event.getRegistryKey().equals(ForgeRegistries.Keys.BLOCKS))
            return;

        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(cedar_sapling.getId(), potted_cedar_sapling);

        CEDAR_FAMILY = new BlockFamily.Builder(cedar_planks.get()).button(cedar_button.get()).fence(cedar_fence.get()).fenceGate(cedar_fence_gate.get()).pressurePlate(cedar_pressure_plate.get()).sign(cedar_sign.get(), cedar_wall_sign.get()).slab(cedar_slab.get()).stairs(cedar_stairs.get()).door(cedar_door.get()).trapdoor(cedar_trapdoor.get())
                .recipeGroupPrefix("totemic:wooden").recipeUnlockedBy("has_planks").getFamily();
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

    //Modifies the validBlocks of the sign block entity type to add our own sign block to it
    public static void addCedarSignToSignBlockEntityType() {
        var signValidBlocks = BlockEntityType.SIGN.validBlocks;
        if(!(signValidBlocks instanceof HashSet)) { //another mod might have already made the set mutable
            BlockEntityType.SIGN.validBlocks = signValidBlocks = new HashSet<>(signValidBlocks); //if not, copy into a mutable set
        }

        signValidBlocks.add(ModBlocks.cedar_sign.get());
        signValidBlocks.add(ModBlocks.cedar_wall_sign.get());
    }
}
