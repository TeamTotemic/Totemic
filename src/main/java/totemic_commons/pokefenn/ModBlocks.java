package totemic_commons.pokefenn;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.statemap.StateMap.Builder;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
public final class ModBlocks
{
    public static BlockCedarLog cedar_log;
    public static BlockCedarStripped stripped_cedar_log;
    public static BlockCedarPlank cedar_plank;
    public static BlockCedarSapling cedar_sapling;
    public static BlockCedarLeaves cedar_leaves;
    public static BlockTotemBase totem_base;
    public static BlockTotemPole totem_pole;
    public static BlockTotemTorch totem_torch;
    public static BlockDrum drum;
    public static BlockWindChime wind_chime;
    public static BlockTipi tipi;
    public static BlockDummyTipi dummy_tipi;

    @SubscribeEvent
    public static void init(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().registerAll(
            cedar_log = new BlockCedarLog(),
            stripped_cedar_log = new BlockCedarStripped(),
            cedar_plank = new BlockCedarPlank(),
            cedar_sapling = new BlockCedarSapling(),
            cedar_leaves = new BlockCedarLeaves(),
            totem_base = new BlockTotemBase(),
            totem_pole = new BlockTotemPole(),
            totem_torch = new BlockTotemTorch(),
            drum = new BlockDrum(),
            wind_chime = new BlockWindChime(),
            tipi = new BlockTipi(),
            dummy_tipi = new BlockDummyTipi());
    }

    @SideOnly(Side.CLIENT)
    public static void setStateMappers()
    {
        ModelLoader.setCustomStateMapper(cedar_sapling, new Builder().ignore(BlockCedarSapling.TYPE, BlockCedarSapling.STAGE).build());
        ModelLoader.setCustomStateMapper(cedar_leaves, new Builder().ignore(BlockCedarLeaves.CHECK_DECAY, BlockCedarLeaves.DECAYABLE).build());
    }
}
