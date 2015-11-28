package totemic_commons.pokefenn;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
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
import totemic_commons.pokefenn.lib.Strings;

public final class ModBlocks
{

    public static Block cedarLog;
    public static Block totemBase;
    public static Block totemPole;
    public static Block totemSapling;
    public static Block totemLeaves;
    public static Block totemTorch;
    public static Block flameParticle;
    public static Block windChime;
    public static Block drum;
    public static Block redCedarPlank;
    public static Block redCedarStripped;
    public static Block tipi;
    public static Block dummyTipi;

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
        GameRegistry.registerBlock(tipi, Strings.TIPI_NAME);
        GameRegistry.registerBlock(dummyTipi, Strings.DUMMY_TIPI_NAME);

        Blocks.fire.setFireInfo(cedarLog, 5, 5);
        Blocks.fire.setFireInfo(redCedarStripped, 5, 10);
        Blocks.fire.setFireInfo(redCedarPlank, 5, 20);
        Blocks.fire.setFireInfo(totemLeaves, 30, 60);
        Blocks.fire.setFireInfo(totemBase, 5, 5);
        Blocks.fire.setFireInfo(totemPole, 5, 5);
    }

}
