package totemic_commons.pokefenn.block;

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
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.Textures;
import totemic_commons.pokefenn.world.TotemTreeGeneration;

import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 12/02/14
 * Time: 12:55
 */
public class BlockTotemSapling extends BlockSapling
{
    private static TotemTreeGeneration treeGen = new TotemTreeGeneration(true);

    public BlockTotemSapling()
    {
        super();
        setBlockName(Strings.TOTEM_SAPLING_NAME);
        setBlockBounds(0.5F - 0.4F, 0.0F, 0.5F - 0.4F, 0.5F + 0.4F, 0.4F * 2.0F, 0.5F + 0.4F);
        setCreativeTab(Totemic.tabsTotem);
    }

    public void func_149878_d(World world, int x, int y, int z, Random random)
    {
        if(!world.isRemote)
        {
            world.setBlockToAir(x, y, z);

            if(!treeGen.growTree(world, random, x, y, z))
            {
                world.setBlock(x, y, z, ModBlocks.totemWoods, 0, 4);
                new TotemTreeGeneration(true).growTree(world, random, x, y, z);
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
        saplingIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.INFUSED_SAPLING);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        return saplingIcon;
    }


}
