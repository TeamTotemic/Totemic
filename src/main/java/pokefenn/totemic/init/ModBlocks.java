package pokefenn.totemic.init;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.statemap.StateMap.Builder;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.block.*;
import pokefenn.totemic.block.music.BlockDrum;
import pokefenn.totemic.block.music.BlockWindChime;
import pokefenn.totemic.block.plant.BlockCedarLeaves;
import pokefenn.totemic.block.plant.BlockCedarSapling;
import pokefenn.totemic.block.tipi.BlockDummyTipi;
import pokefenn.totemic.block.tipi.BlockTipi;
import pokefenn.totemic.block.totem.BlockTotemBase;
import pokefenn.totemic.block.totem.BlockTotemPole;

@EventBusSubscriber(modid = Totemic.MOD_ID)
@ObjectHolder(Totemic.MOD_ID)
public final class ModBlocks
{
    public static final BlockCedarLog cedar_log = null;
    public static final BlockCedarStripped stripped_cedar_log = null;
    public static final BlockCedarPlank cedar_plank = null;
    public static final BlockCedarSapling cedar_sapling = null;
    public static final BlockCedarLeaves cedar_leaves = null;
    public static final BlockCedarStairs cedar_stairs = null;
    public static final BlockCedarSlab cedar_slab = null;
    public static final BlockCedarSlab double_cedar_slab = null;
    public static final BlockCedarFence cedar_fence = null;
    public static final BlockCedarFenceGate cedar_fence_gate = null;
    public static final BlockTotemBase totem_base = null;
    public static final BlockTotemPole totem_pole = null;
    public static final BlockTotemTorch totem_torch = null;
    public static final BlockDrum drum = null;
    public static final BlockWindChime wind_chime = null;
    public static final BlockTipi tipi = null;
    public static final BlockDummyTipi dummy_tipi = null;

    @SubscribeEvent
    public static void init(RegistryEvent.Register<Block> event)
    {
        BlockCedarPlank plank;
        event.getRegistry().registerAll(
            new BlockCedarLog(),
            new BlockCedarStripped(),
            plank = new BlockCedarPlank(),
            new BlockCedarSapling(),
            new BlockCedarLeaves(),
            new BlockCedarStairs(plank.getDefaultState()),
            new BlockCedarSlab() { @Override public boolean isDouble() { return false; } },
            new BlockCedarSlab() { @Override public boolean isDouble() { return true; } },
            new BlockCedarFence(),
            new BlockCedarFenceGate(),
            new BlockTotemBase(),
            new BlockTotemPole(),
            new BlockTotemTorch(),
            new BlockDrum(),
            new BlockWindChime(),
            new BlockTipi(),
            new BlockDummyTipi());
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void setStateMappers(ModelRegistryEvent event)
    {
        ModelLoader.setCustomStateMapper(cedar_sapling, new Builder().ignore(BlockCedarSapling.TYPE, BlockCedarSapling.STAGE).build());
        ModelLoader.setCustomStateMapper(cedar_leaves, new Builder().ignore(BlockCedarLeaves.CHECK_DECAY, BlockCedarLeaves.DECAYABLE).build());
        ModelLoader.setCustomStateMapper(cedar_slab, new Builder().ignore(BlockCedarSlab.VARIANT).build());
        ModelLoader.setCustomStateMapper(double_cedar_slab, new Builder().ignore(BlockCedarSlab.VARIANT).build());
    }
}
