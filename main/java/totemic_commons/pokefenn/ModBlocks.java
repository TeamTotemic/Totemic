package totemic_commons.pokefenn;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import totemic_commons.pokefenn.block.*;
import totemic_commons.pokefenn.block.music.BlockDrum;
import totemic_commons.pokefenn.block.music.BlockWindChime;
import totemic_commons.pokefenn.block.plant.*;
import totemic_commons.pokefenn.block.totem.BlockCeremonyIntelligence;
import totemic_commons.pokefenn.block.totem.BlockTotemIntelligence;
import totemic_commons.pokefenn.block.totem.BlockTotemSocket;
import totemic_commons.pokefenn.lib.Strings;

public final class ModBlocks
{

    public static Block totemWoods;
    public static Block totemTable;
    public static Block totemIntelligence;
    public static Block totemSocket;
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

    public static Block totemMana;

    public static void init()
    {

        totemWoods = new BlockTotemWoods();
        totemIntelligence = new BlockTotemIntelligence();
        totemSocket = new BlockTotemSocket();
        chlorophyll = new BlockChlorophyll();
        totemSapling = new BlockTotemSapling();
        totemLeaves = new BlockTotemLeaves();
        //totemCauldron = new BlockTotemCauldron();
        moonglow = new BlockMoonglow();
        totemCeremonyIntelligence = new BlockCeremonyIntelligence();
        bloodwart = new BlockBloodWart();
        totemTorch = new BlockTotemTorch();
        lotusBlock = new BlockWaterLotus();
        fungusBlock = new BlockFungalPlant();
        flameParticle = new BlockFlameParticle();
        drum = new BlockDrum();
        windChime = new BlockWindChime();

        GameRegistry.registerBlock(totemWoods, Strings.TOTEM_WOODS_NAME);
        GameRegistry.registerBlock(totemIntelligence, Strings.TOTEM_INTELLIGENCE_NAME);
        GameRegistry.registerBlock(totemSocket, Strings.TOTEM_SOCKET_NAME);
        GameRegistry.registerBlock(chlorophyll, Strings.FLUID_CHLOROPHYLL_NAME);
        GameRegistry.registerBlock(totemSapling, Strings.TOTEM_SAPLING_NAME);
        GameRegistry.registerBlock(totemLeaves, Strings.TOTEM_LEAVES_NAME);
        //GameRegistry.registerBlock(totemCauldron, Strings.TOTEM_CAULDRON_NAME);
        GameRegistry.registerBlock(moonglow, Strings.MOONGLOW_NAME);
        GameRegistry.registerBlock(totemCeremonyIntelligence, Strings.CEREMONY_INTELLIGENCE_NAME);
        GameRegistry.registerBlock(bloodwart, Strings.BLOODWART_BLOCK_NAME);
        GameRegistry.registerBlock(totemTorch, Strings.TOTEM_TORCH_NAME);
        GameRegistry.registerBlock(lotusBlock, Strings.LOTUS_BLOCK_NAME);
        GameRegistry.registerBlock(fungusBlock, Strings.FUNGAL_PLANT_BLOCK_NAME);
        GameRegistry.registerBlock(flameParticle, Strings.FLAME_PARTICLE_NAME);
        GameRegistry.registerBlock(drum, Strings.DRUM_NAME);
        GameRegistry.registerBlock(windChime, Strings.WIND_CHIME_NAME);
    }

    public static void initBotania()
    {
        Totemic.botaniaLoaded = true;

        //Totemic.logger.info("Totemic Botania Interaction Enabled");

        //totemMana = new BlockManaTotem();
        //GameRegistry.registerBlock(totemMana, Strings.TOTEM_MANA_NAME);

    }

}
