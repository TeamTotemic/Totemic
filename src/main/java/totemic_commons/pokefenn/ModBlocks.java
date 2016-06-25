package totemic_commons.pokefenn;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.statemap.StateMap.Builder;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
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
import totemic_commons.pokefenn.item.ItemBlockVariants;
import totemic_commons.pokefenn.item.ItemTipi;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.WoodVariant;

public final class ModBlocks
{
    public static BlockCedarLog cedarLog;
    public static BlockTotemBase totemBase;
    public static BlockTotemPole totemPole;
    public static BlockCedarSapling cedarSapling;
    public static BlockCedarLeaves cedarLeaves;
    public static BlockTotemTorch totemTorch;
    public static BlockWindChime windChime;
    public static BlockDrum drum;
    public static BlockCedarPlank cedarPlank;
    public static BlockCedarStripped cedarLogStripped;
    public static BlockTipi tipi;
    public static BlockDummyTipi dummyTipi;

    public static void init()
    {
        cedarLog = new BlockCedarLog();
        cedarLogStripped = new BlockCedarStripped();
        cedarPlank = new BlockCedarPlank();
        cedarSapling = new BlockCedarSapling();
        cedarLeaves = new BlockCedarLeaves();
        totemBase = new BlockTotemBase();
        totemPole = new BlockTotemPole();
        totemTorch = new BlockTotemTorch();
        drum = new BlockDrum();
        windChime = new BlockWindChime();
        tipi = new BlockTipi();
        dummyTipi = new BlockDummyTipi();

        GameRegistry.registerBlock(cedarLog, Strings.CEDAR_LOG_NAME);
        GameRegistry.registerBlock(cedarLogStripped, Strings.RED_CEDAR_STRIPPED_NAME);
        GameRegistry.registerBlock(cedarPlank, Strings.RED_CEDAR_PLANK_NAME);
        GameRegistry.registerBlock(cedarSapling, Strings.TOTEM_SAPLING_NAME);
        GameRegistry.registerBlock(cedarLeaves, Strings.TOTEM_LEAVES_NAME);
        GameRegistry.registerBlock(totemBase, ItemBlockVariants.class, Strings.TOTEM_BASE_NAME);
        GameRegistry.registerBlock(totemPole, ItemBlockVariants.class, Strings.TOTEM_POLE_NAME);
        GameRegistry.registerBlock(totemTorch, Strings.TOTEM_TORCH_NAME);
        GameRegistry.registerBlock(drum, Strings.DRUM_NAME);
        GameRegistry.registerBlock(windChime, Strings.WIND_CHIME_NAME);
        GameRegistry.registerBlock(tipi, ItemTipi.class, Strings.TIPI_NAME);
        GameRegistry.registerBlock(dummyTipi, Strings.DUMMY_TIPI_NAME);

        Blocks.fire.setFireInfo(cedarLog, 5, 5);
        Blocks.fire.setFireInfo(cedarLogStripped, 5, 10);
        Blocks.fire.setFireInfo(cedarPlank, 5, 20);
        Blocks.fire.setFireInfo(cedarLeaves, 30, 60);
        Blocks.fire.setFireInfo(totemBase, 5, 5);
        Blocks.fire.setFireInfo(totemPole, 5, 5);
    }

    @SideOnly(Side.CLIENT)
    public static void setStateMappers()
    {
        ModelLoader.setCustomStateMapper(cedarSapling, new Builder().ignore(BlockCedarSapling.TYPE, BlockCedarSapling.STAGE).build());
        ModelLoader.setCustomStateMapper(cedarLeaves, new Builder().ignore(BlockCedarLeaves.CHECK_DECAY, BlockCedarLeaves.DECAYABLE).build());
    }

    @SideOnly(Side.CLIENT)
    public static void setItemModels()
    {
        setDefaultModel(cedarLog);
        setDefaultModel(cedarLogStripped);
        setDefaultModel(cedarPlank);
        setDefaultModel(cedarSapling);
        setDefaultModel(cedarLeaves);
        setDefaultModel(totemTorch);
        setDefaultModel(drum);
        setDefaultModel(windChime);
        setDefaultModel(tipi);

        for(int i = 0; i < WoodVariant.values().length; i++)
        {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(totemBase), i,
                    new ModelResourceLocation(totemBase.getRegistryName(), "wood=" + WoodVariant.values()[i].getName()));
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(totemPole), i,
                    new ModelResourceLocation(totemPole.getRegistryName(), "wood=" + WoodVariant.values()[i].getName()));
        }
    }

    @SideOnly(Side.CLIENT)
    public static void setDefaultModel(Block block)
    {
        ModItems.setDefaultModel(Item.getItemFromBlock(block));
    }
}
