package totemic_commons.pokefenn;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.statemap.StateMap.Builder;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.block.BlockCedarLog;
import totemic_commons.pokefenn.block.BlockCedarPlank;
import totemic_commons.pokefenn.block.BlockCedarStripped;
import totemic_commons.pokefenn.block.BlockTotemTorch;
import totemic_commons.pokefenn.block.music.BlockDrum;
import totemic_commons.pokefenn.block.music.BlockWindChime;
import totemic_commons.pokefenn.block.plant.BlockCedarLeaves;
import totemic_commons.pokefenn.block.plant.BlockCedarSapling;
import totemic_commons.pokefenn.block.tipi.BlockDummyTipi;
import totemic_commons.pokefenn.block.tipi.BlockTipi;
import totemic_commons.pokefenn.block.totem.BlockTotemBase;
import totemic_commons.pokefenn.block.totem.BlockTotemPole;

@EventBusSubscriber(modid = Totemic.MOD_ID)
@ObjectHolder(Totemic.MOD_ID)
public final class ModBlocks
{
    public static final BlockCedarLog cedar_log = null;
    public static final BlockCedarStripped stripped_cedar_log = null;
    public static final BlockCedarPlank cedar_plank = null;
    public static final BlockCedarSapling cedar_sapling = null;
    public static final BlockCedarLeaves cedar_leaves = null;
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
        event.getRegistry().registerAll(
            new BlockCedarLog(),
            new BlockCedarStripped(),
            new BlockCedarPlank(),
            new BlockCedarSapling(),
            new BlockCedarLeaves(),
            new BlockTotemBase(),
            new BlockTotemPole(),
            new BlockTotemTorch(),
            new BlockDrum(),
            new BlockWindChime(),
            new BlockTipi(),
            new BlockDummyTipi());
    }

    @SideOnly(Side.CLIENT)
    public static void setStateMappers()
    {
        ModelLoader.setCustomStateMapper(cedar_sapling, new Builder().ignore(BlockCedarSapling.TYPE, BlockCedarSapling.STAGE).build());
        ModelLoader.setCustomStateMapper(cedar_leaves, new Builder().ignore(BlockCedarLeaves.CHECK_DECAY, BlockCedarLeaves.DECAYABLE).build());
    }
}
