package pokefenn.totemic.block.plant;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockSapling;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.lib.Strings;
import pokefenn.totemic.world.TotemTreeGeneration;

import javax.annotation.Nullable;

public class BlockCedarSapling extends BlockSapling
{
    private static final TotemTreeGeneration treeGen = new TotemTreeGeneration(true);

    public BlockCedarSapling()
    {
        setRegistryName(Strings.CEDAR_SAPLING_NAME);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.CEDAR_SAPLING_NAME);
        setCreativeTab(Totemic.tabsTotem);
        setSoundType(SoundType.PLANT);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        tooltip.add(I18n.format(getUnlocalizedName() + ".tooltip"));
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

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        list.add(new ItemStack(this));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return new AxisAlignedBB(0.5F - 0.4F, 0.0F, 0.5F - 0.4F, 0.5F + 0.4F, 0.4F * 2.0F, 0.5F + 0.4F);
    }
}
