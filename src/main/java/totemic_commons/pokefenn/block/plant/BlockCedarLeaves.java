package totemic_commons.pokefenn.block.plant;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Resources;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 16/02/14
 * Time: 17:48
 */
public class BlockCedarLeaves extends BlockLeaves
{
    public BlockCedarLeaves()
    {
        setCreativeTab(Totemic.tabsTotem);
        setBlockName(Strings.TOTEM_LEAVES_NAME);
    }

    @SideOnly(Side.CLIENT)
    private IIcon opaqueIcon;
    @SideOnly(Side.CLIENT)
    private IIcon transparentIcon;

    @Override
    public Item getItemDropped(int meta, Random random, int fortune)
    {
        return Item.getItemFromBlock(ModBlocks.totemSapling);
    }

    //The sapling drop chance
    @Override
    protected int func_150123_b(int meta)
    {
        return 80;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess iba, int x, int y, int z, int side)
    {
        return !Blocks.leaves.isOpaqueCube() || super.shouldSideBeRendered(iba, x, y, z, side);
    }

    @Override
    public boolean isOpaqueCube()
    {
        return Blocks.leaves.isOpaqueCube();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return !Blocks.leaves.isOpaqueCube() ? transparentIcon : opaqueIcon;
    }


    @Override
    public String[] func_150125_e()
    {
        return new String[0];
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        opaqueIcon = register.registerIcon(Resources.TEXTURE_LOCATION + ":" + Resources.TOTEM_LEAVES_OPAQUE);
        transparentIcon = register.registerIcon(Resources.TEXTURE_LOCATION + ":" + Resources.TOTEM_LEAVES_TRANSPARENT);
    }


}
