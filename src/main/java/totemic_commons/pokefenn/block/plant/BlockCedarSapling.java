package totemic_commons.pokefenn.block.plant;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.Totemic;
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
        setUnlocalizedName(Strings.TOTEM_SAPLING_NAME);
        setBlockBounds(0.5F - 0.4F, 0.0F, 0.5F - 0.4F, 0.5F + 0.4F, 0.4F * 2.0F, 0.5F + 0.4F);
        setCreativeTab(Totemic.tabsTotem);
        setStepSound(soundTypeGrass);
    }

    @Override
    public void grow(World world, BlockPos pos, IBlockState state, Random random)
    {
        if(!world.isRemote)
        {
            if(treeGen.growTree(world, random, pos))
            {
                world.setBlockToAir(pos);
                world.setBlockState(pos, ModBlocks.cedarLog.getDefaultState(), 4);
            }
        }
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item blockId, CreativeTabs tab, List<ItemStack> subBlocks)
    {
        subBlocks.add(new ItemStack(blockId, 1, 0));
    }

    @Override
    public Item getItemDropped(IBlockState state, Random random, int fortune)
    {
        return Item.getItemFromBlock(ModBlocks.totemSapling);
    }
}
