package totemic_commons.pokefenn;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap.Builder;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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

        GameRegistry.register(cedarLog);
        GameRegistry.register(new ItemBlock(cedarLog).setRegistryName(cedarLog.getRegistryName()));
        GameRegistry.register(cedarLogStripped);
        GameRegistry.register(new ItemBlock(cedarLogStripped).setRegistryName(cedarLogStripped.getRegistryName()));
        GameRegistry.register(cedarPlank);
        GameRegistry.register(new ItemBlock(cedarPlank).setRegistryName(cedarPlank.getRegistryName()));
        GameRegistry.register(cedarSapling);
        GameRegistry.register(new ItemBlock(cedarSapling).setRegistryName(cedarSapling.getRegistryName()));
        GameRegistry.register(cedarLeaves);
        GameRegistry.register(new ItemBlock(cedarLeaves).setRegistryName(cedarLeaves.getRegistryName()));
        GameRegistry.register(totemBase);
        GameRegistry.register(new ItemBlockVariants(totemBase).setRegistryName(totemBase.getRegistryName()));
        GameRegistry.register(totemPole);
        GameRegistry.register(new ItemBlockVariants(totemPole).setRegistryName(totemPole.getRegistryName()));
        GameRegistry.register(totemTorch);
        GameRegistry.register(new ItemBlock(totemTorch).setRegistryName(totemTorch.getRegistryName()));
        GameRegistry.register(drum);
        GameRegistry.register(new ItemBlock(drum).setRegistryName(drum.getRegistryName()));
        GameRegistry.register(windChime);
        GameRegistry.register(new ItemBlock(windChime).setRegistryName(windChime.getRegistryName()));
        GameRegistry.register(tipi);
        GameRegistry.register(new ItemTipi(tipi).setRegistryName(tipi.getRegistryName()));
        GameRegistry.register(dummyTipi);

        Blocks.FIRE.setFireInfo(cedarLog, 5, 5);
        Blocks.FIRE.setFireInfo(cedarLogStripped, 5, 10);
        Blocks.FIRE.setFireInfo(cedarPlank, 5, 20);
        Blocks.FIRE.setFireInfo(cedarLeaves, 30, 60);
        Blocks.FIRE.setFireInfo(totemBase, 5, 5);
        Blocks.FIRE.setFireInfo(totemPole, 5, 5);
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
