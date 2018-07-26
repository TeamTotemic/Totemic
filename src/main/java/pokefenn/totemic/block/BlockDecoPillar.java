package pokefenn.totemic.block;

import javax.annotation.Nullable;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.block.totem.BlockTotemBase;
import pokefenn.totemic.lib.Strings;
import pokefenn.totemic.lib.WoodVariant;
import pokefenn.totemic.tileentity.TileDecoPillar;

public class BlockDecoPillar extends BlockRotatedPillar implements ITileEntityProvider
{
    public static final PropertyEnum<WoodVariant> WOOD = BlockTotemBase.WOOD;
    public static final PropertyBool STRIPPED = PropertyBool.create("stripped");

    private static final AxisAlignedBB X_BB = new AxisAlignedBB(0.0F, 0.1875F, 0.1875F,  1.0F, 0.8125F, 0.8125F);
    private static final AxisAlignedBB Y_BB = new AxisAlignedBB(0.1875F, 0.0F, 0.1875F,  0.8125F, 1.0F, 0.8125F);
    private static final AxisAlignedBB Z_BB = new AxisAlignedBB(0.1875F, 0.1875F, 0.0F,  0.8125F, 0.8125F, 1.0F);

    public BlockDecoPillar()
    {
        super(Material.WOOD);
        setRegistryName(Strings.WOODEN_PILLAR_NAME);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.WOODEN_PILLAR_NAME);
        setCreativeTab(Totemic.tabsTotem);
        setHardness(2);
        setResistance(5);
        setSoundType(SoundType.WOOD);
        Blocks.FIRE.setFireInfo(this, 5, 5);
        setDefaultState(blockState.getBaseState().withProperty(AXIS, Axis.Y).withProperty(WOOD, WoodVariant.OAK).withProperty(STRIPPED, false));
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        TileDecoPillar tile = (TileDecoPillar) world.getTileEntity(pos);
        int meta = stack.getMetadata();
        tile.setStripped((meta & 1) == 1);
        tile.setWoodType(WoodVariant.fromID(meta >> 1));
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        TileDecoPillar tile = (TileDecoPillar) world.getTileEntity(pos);
        int meta = (tile.isStripped() ? 1 : 0) | (tile.getWoodType().getID() << 1);
        return new ItemStack(this, 1, meta);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        switch(state.getValue(AXIS))
        {
        case X:
            return X_BB;
        case Y:
        default:
            return Y_BB;
        case Z:
            return Z_BB;
        }
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return ((TileDecoPillar) world.getTileEntity(pos)).getWoodType().getMapColor();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, AXIS, WOOD, STRIPPED);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TileDecoPillar)
        {
            TileDecoPillar tdp = (TileDecoPillar) tile;
            return state.withProperty(WOOD, tdp.getWoodType()).withProperty(STRIPPED, tdp.isStripped());
        }
        return state;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face)
    {
        if(face.getAxis() == state.getValue(AXIS))
            return BlockFaceShape.CENTER_BIG;
        else
            return BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return state.getValue(AXIS) == Axis.Y;
    }

    @Override
    @Nullable
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        TileDecoPillar tile = new TileDecoPillar();
        tile.setWoodType(state.getValue(WOOD));
        tile.setStripped(state.getValue(STRIPPED));
        return tile;
    }

    //Necessary for ITileEntityProvider
    @Override
    @Nullable
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileDecoPillar();
    }
}
