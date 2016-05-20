package totemic_commons.pokefenn.block.plant;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockSapling;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
    private static final TotemTreeGeneration treeGen = new TotemTreeGeneration(true);

    public BlockCedarSapling()
    {
        setRegistryName(Strings.TOTEM_SAPLING_NAME);
        setUnlocalizedName(Strings.TOTEM_SAPLING_NAME);
        setCreativeTab(Totemic.tabsTotem);
        setStepSound(SoundType.PLANT);
    }

    @Override
    public void generateTree(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if (!TerrainGen.saplingGrowTree(world, rand, pos))
            return;
        treeGen.growTree(world, rand, pos);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        //TYPE is necessary because otherwise the constructor of BlockSapling will crash due to the missing property
        return new BlockStateContainer(this, TYPE, STAGE);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(STAGE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(STAGE, meta);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return 0;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list)
    {
        list.add(new ItemStack(item));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return new AxisAlignedBB(0.5F - 0.4F, 0.0F, 0.5F - 0.4F, 0.5F + 0.4F, 0.4F * 2.0F, 0.5F + 0.4F);
    }
}
