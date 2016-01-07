package totemic_commons.pokefenn;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.statemap.StateMap.Builder;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
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
import totemic_commons.pokefenn.item.ItemTipi;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TileTipi;
import totemic_commons.pokefenn.tileentity.music.TileWindChime;

public final class ModBlocks
{
    public static BlockCedarLog cedarLog;
    public static BlockTotemBase totemBase;
    public static BlockTotemPole totemPole;
    public static BlockCedarSapling totemSapling;
    public static BlockCedarLeaves totemLeaves;
    public static BlockTotemTorch totemTorch;
    //public static Block flameParticle;
    public static BlockWindChime windChime;
    public static BlockDrum drum;
    public static BlockCedarPlank redCedarPlank;
    public static BlockCedarStripped redCedarStripped;
    public static BlockTipi tipi;
    public static BlockDummyTipi dummyTipi;

    public static void init()
    {
        cedarLog = new BlockCedarLog();
        totemBase = new BlockTotemBase();
        totemPole = new BlockTotemPole();
        totemSapling = new BlockCedarSapling();
        totemLeaves = new BlockCedarLeaves();
        totemTorch = new BlockTotemTorch();
        drum = new BlockDrum();
        windChime = new BlockWindChime();
        redCedarPlank = new BlockCedarPlank();
        redCedarStripped = new BlockCedarStripped();
        tipi = new BlockTipi();
        dummyTipi = new BlockDummyTipi();

        GameRegistry.registerBlock(cedarLog, Strings.CEDAR_LOG_NAME);
        GameRegistry.registerBlock(totemBase, Strings.TOTEM_BASE_NAME);
        GameRegistry.registerBlock(totemPole, Strings.TOTEM_POLE_NAME);
        GameRegistry.registerBlock(totemSapling, Strings.TOTEM_SAPLING_NAME);
        GameRegistry.registerBlock(totemLeaves, Strings.TOTEM_LEAVES_NAME);
        GameRegistry.registerBlock(totemTorch, Strings.TOTEM_TORCH_NAME);
        GameRegistry.registerBlock(drum, Strings.DRUM_NAME);
        GameRegistry.registerBlock(windChime, Strings.WIND_CHIME_NAME);
        GameRegistry.registerBlock(redCedarPlank, Strings.RED_CEDAR_PLANK_NAME);
        GameRegistry.registerBlock(redCedarStripped, Strings.RED_CEDAR_STRIPPED_NAME);
        GameRegistry.registerBlock(tipi, ItemTipi.class, Strings.TIPI_NAME);
        GameRegistry.registerBlock(dummyTipi, Strings.DUMMY_TIPI_NAME);

        Blocks.fire.setFireInfo(cedarLog, 5, 5);
        Blocks.fire.setFireInfo(redCedarStripped, 5, 10);
        Blocks.fire.setFireInfo(redCedarPlank, 5, 20);
        Blocks.fire.setFireInfo(totemLeaves, 30, 60);
        Blocks.fire.setFireInfo(totemBase, 5, 5);
        Blocks.fire.setFireInfo(totemPole, 5, 5);
    }

    @SideOnly(Side.CLIENT)
    public static void setStateMappers()
    {
        ModelLoader.setCustomStateMapper(totemSapling, new Builder().ignore(BlockCedarSapling.TYPE, BlockCedarSapling.STAGE).build());
        ModelLoader.setCustomStateMapper(totemLeaves, new Builder().ignore(BlockCedarLeaves.CHECK_DECAY, BlockCedarLeaves.DECAYABLE).build());

        totemLeaves.setGraphicsLevel(true); //TODO: Fix fast graphics
    }

    @SideOnly(Side.CLIENT)
    public static void setItemModels()
    {
        setDefaultModel(cedarLog);
        setDefaultModel(totemSapling);
        setDefaultModel(totemLeaves);
        setDefaultModel(redCedarPlank);
        setDefaultModel(redCedarStripped);
    }

    @SideOnly(Side.CLIENT)
    public static void setDefaultModel(Block block)
    {
        ModItems.setDefaultModel(Item.getItemFromBlock(block));
    }
}
