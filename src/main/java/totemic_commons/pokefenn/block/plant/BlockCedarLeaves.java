package totemic_commons.pokefenn.block.plant;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Resources;
import totemic_commons.pokefenn.lib.Strings;

import java.util.ArrayList;
import java.util.Random;

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
        super();
        setCreativeTab(Totemic.tabsTotem);
        setBlockName(Strings.TOTEM_LEAVES_NAME);
        setLightOpacity(0);
        setHardness(0.2F);
    }

    private IIcon opaqueIcon;
    private IIcon transparentIcon;

    @Override
    public Item getItemDropped(int i, Random random, int j)
    {
        return Item.getItemFromBlock(ModBlocks.totemSapling);
    }

    @Override
    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        if(!par1World.isRemote)
        {
            ArrayList<ItemStack> items = getDrops(par1World, par2, par3, par4, par5, par7);

            for(ItemStack item : items)
            {
                Random rand = new Random();
                if(rand.nextInt(12) == 1)
                {
                    this.dropBlockAsItem(par1World, par2, par3, par4, item);
                }
            }
        }
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
