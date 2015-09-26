package totemic_commons.pokefenn.block.plant;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Resources;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.world.TotemTreeGeneration;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 12/02/14
 * Time: 12:55
 */
public class BlockCedarSapling extends BlockSapling
{
    private static TotemTreeGeneration treeGen = new TotemTreeGeneration(true);

    public BlockCedarSapling()
    {
        super();
        setBlockName(Strings.TOTEM_SAPLING_NAME);
        setBlockBounds(0.5F - 0.4F, 0.0F, 0.5F - 0.4F, 0.5F + 0.4F, 0.4F * 2.0F, 0.5F + 0.4F);
        setCreativeTab(Totemic.tabsTotem);
        setStepSound(soundTypeGrass);
    }

    @Override
    public void func_149878_d(World world, int x, int y, int z, Random random)
    {
        if(!world.isRemote)
        {
            if(treeGen.growTree(world, random, x, y, z))
            {
                world.setBlockToAir(x, y, z);
                world.setBlock(x, y, z, ModBlocks.cedarLog, 0, 4);
            }
        }
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item blockId, CreativeTabs tab, List subBlocks)
    {
        subBlocks.add(new ItemStack(blockId, 1, 0));
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random random, int p_149650_3_)
    {
        return Item.getItemFromBlock(ModBlocks.totemSapling);
    }


    @SideOnly(Side.CLIENT)
    private IIcon saplingIcon;

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register)
    {
        saplingIcon = register.registerIcon(Resources.TEXTURE_LOCATION + ":" + Resources.INFUSED_SAPLING);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        return saplingIcon;
    }


}
