package totemic_commons.pokefenn;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import totemic_commons.pokefenn.block.*;
import totemic_commons.pokefenn.block.music.BlockDrum;
import totemic_commons.pokefenn.block.music.BlockWindChime;
import totemic_commons.pokefenn.block.plant.*;
import totemic_commons.pokefenn.block.totem.BlockTotemBase;
import totemic_commons.pokefenn.block.totem.BlockTotemPole;
import totemic_commons.pokefenn.lib.Strings;

public final class ModBlocks
{

    public static Block totemWoods;
    public static Block totemTable;
    public static Block totemBase;
    public static Block totemPole;
    public static Block chlorophyll;
    public static Block totemSapling;
    public static Block totemLeaves;
    public static Block totemCauldron;
    public static Block moonglow;
    public static Block totemCeremonyIntelligence;
    public static Block bloodwart;
    public static Block totemTorch;
    public static Block lotusBlock;
    public static Block fungusBlock;
    public static Block flameParticle;
    public static Block windChime;
    public static Block drum;
    public static Block redCedarPlank;
    public static Block redCedarStripped;

    public static void init()
    {

        totemWoods = new BlockCedarLog();
        totemBase = new BlockTotemBase();
        totemPole = new BlockTotemPole();
        totemSapling = new BlocCedarSapling();
        totemLeaves = new BlockCedarLeaves();
        totemTorch = new BlockTotemTorch();
        flameParticle = new BlockFlameParticle();
        drum = new BlockDrum();
        windChime = new BlockWindChime();
        redCedarPlank = new BlockCedarPlank();
        redCedarStripped = new BlockCedarStripped();

        GameRegistry.registerBlock(totemWoods, Strings.TOTEM_WOODS_NAME);
        GameRegistry.registerBlock(totemBase, Strings.TOTEM_BASE_NAME);
        GameRegistry.registerBlock(totemPole, Strings.TOTEM_POLE_NAME);
        GameRegistry.registerBlock(totemSapling, Strings.TOTEM_SAPLING_NAME);
        GameRegistry.registerBlock(totemLeaves, Strings.TOTEM_LEAVES_NAME);
        GameRegistry.registerBlock(totemTorch, Strings.TOTEM_TORCH_NAME);
        GameRegistry.registerBlock(flameParticle, Strings.FLAME_PARTICLE_NAME);
        GameRegistry.registerBlock(drum, Strings.DRUM_NAME);
        GameRegistry.registerBlock(windChime, Strings.WIND_CHIME_NAME);
        GameRegistry.registerBlock(redCedarPlank, Strings.RED_CEDAR_PLANK_NAME);
        GameRegistry.registerBlock(redCedarStripped, Strings.RED_CEDAR_STRIPPED_NAME);

        //GameRegistry.registerBlock(lotusBlock, Strings.LOTUS_BLOCK_NAME);
        //GameRegistry.registerBlock(fungusBlock, Strings.FUNGAL_PLANT_BLOCK_NAME);
        //GameRegistry.registerBlock(chlorophyll, Strings.FLUID_CHLOROPHYLL_NAME);
        //GameRegistry.registerBlock(totemCauldron, Strings.TOTEM_CAULDRON_NAME);
        //GameRegistry.registerBlock(moonglow, Strings.MOONGLOW_NAME);
        //GameRegistry.registerBlock(totemCeremonyIntelligence, Strings.CEREMONY_INTELLIGENCE_NAME);
        //GameRegistry.registerBlock(bloodwart, Strings.BLOODWART_BLOCK_NAME);
        //chlorophyll = new BlockChlorophyll();
        //totemCauldron = new BlockTotemCauldron();
        //moonglow = new BlockMoonglow();
        //totemCeremonyIntelligence = new BlockCeremonyIntelligence();
        //bloodwart = new BlockBloodWart();
        //lotusBlock = new BlockWaterLotus();
        //fungusBlock = new BlockFungalPlant();
    }

}
