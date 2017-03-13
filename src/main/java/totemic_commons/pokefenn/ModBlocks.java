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

    public static void init()
    {
        cedar_log = new BlockCedarLog();
        stripped_cedar_log = new BlockCedarStripped();
        cedar_plank = new BlockCedarPlank();
        cedar_sapling = new BlockCedarSapling();
        cedar_leaves = new BlockCedarLeaves();
        totem_base = new BlockTotemBase();
        totem_pole = new BlockTotemPole();
        totem_torch = new BlockTotemTorch();
        drum = new BlockDrum();
        wind_chime = new BlockWindChime();
        tipi = new BlockTipi();
        dummy_tipi = new BlockDummyTipi();

        GameRegistry.register(cedar_log);
        GameRegistry.register(makeItem(cedar_log));
        GameRegistry.register(stripped_cedar_log);
        GameRegistry.register(makeItem(stripped_cedar_log));
        GameRegistry.register(cedar_plank);
        GameRegistry.register(makeItem(cedar_plank));
        GameRegistry.register(cedar_sapling);
        GameRegistry.register(makeItem(cedar_sapling));
        GameRegistry.register(cedar_leaves);
        GameRegistry.register(makeItem(cedar_leaves));
        GameRegistry.register(totem_base);
        GameRegistry.register(new ItemBlockVariants(totem_base).setRegistryName(totem_base.getRegistryName()));
        GameRegistry.register(totem_pole);
        GameRegistry.register(new ItemBlockVariants(totem_pole).setRegistryName(totem_pole.getRegistryName()));
        GameRegistry.register(totem_torch);
        GameRegistry.register(makeItem(totem_torch));
        GameRegistry.register(drum);
        GameRegistry.register(makeItem(drum));
        GameRegistry.register(wind_chime);
        GameRegistry.register(makeItem(wind_chime));
        GameRegistry.register(tipi);
        GameRegistry.register(new ItemTipi(tipi).setRegistryName(tipi.getRegistryName()));
        GameRegistry.register(dummy_tipi);

        Blocks.FIRE.setFireInfo(cedar_log, 5, 5);
        Blocks.FIRE.setFireInfo(stripped_cedar_log, 5, 10);
        Blocks.FIRE.setFireInfo(cedar_plank, 5, 20);
        Blocks.FIRE.setFireInfo(cedar_leaves, 30, 60);
        Blocks.FIRE.setFireInfo(totem_base, 5, 5);
        Blocks.FIRE.setFireInfo(totem_pole, 5, 5);
    }

    private static ItemBlock makeItem(Block block)
    {
        return (ItemBlock) new ItemBlock(block).setRegistryName(block.getRegistryName());
    }

    @SideOnly(Side.CLIENT)
    public static void setStateMappers()
    {
        ModelLoader.setCustomStateMapper(cedar_sapling, new Builder().ignore(BlockCedarSapling.TYPE, BlockCedarSapling.STAGE).build());
        ModelLoader.setCustomStateMapper(cedar_leaves, new Builder().ignore(BlockCedarLeaves.CHECK_DECAY, BlockCedarLeaves.DECAYABLE).build());
    }

    @SideOnly(Side.CLIENT)
    public static void setItemModels()
    {
        setDefaultModel(cedar_log);
        setDefaultModel(stripped_cedar_log);
        setDefaultModel(cedar_plank);
        setDefaultModel(cedar_sapling);
        setDefaultModel(cedar_leaves);
        setDefaultModel(totem_torch);
        setDefaultModel(drum);
        setDefaultModel(wind_chime);
        setDefaultModel(tipi);

        for(int i = 0; i < WoodVariant.values().length; i++)
        {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(totem_base), i,
                    new ModelResourceLocation(totem_base.getRegistryName(), "wood=" + WoodVariant.values()[i].getName()));
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(totem_pole), i,
                    new ModelResourceLocation(totem_pole.getRegistryName(), "wood=" + WoodVariant.values()[i].getName()));
        }
    }

    @SideOnly(Side.CLIENT)
    public static void setDefaultModel(Block block)
    {
        ModItems.setDefaultModel(Item.getItemFromBlock(block));
    }
}
