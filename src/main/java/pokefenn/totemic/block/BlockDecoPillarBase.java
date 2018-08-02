package pokefenn.totemic.block;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.lib.Strings;
import pokefenn.totemic.lib.WoodVariant;
import pokefenn.totemic.tileentity.TileDecoPillar;

public class BlockDecoPillarBase extends BlockDirectional implements ITileEntityProvider
{
    public static final PropertyEnum<WoodVariant> WOOD = BlockDecoPillar.WOOD;
    public static final PropertyBool STRIPPED = BlockDecoPillar.STRIPPED;

    private static final AxisAlignedBB X_BB = new AxisAlignedBB(0.0F, 0.125F, 0.125F,  1.0F, 0.875F, 0.875F);
    private static final AxisAlignedBB Y_BB = new AxisAlignedBB(0.125F, 0.0F, 0.125F,  0.875F, 1.0F, 0.875F);
    private static final AxisAlignedBB Z_BB = new AxisAlignedBB(0.125F, 0.125F, 0.0F,  0.875F, 0.875F, 1.0F);
    private static final AxisAlignedBB BASE_UP_BB = new AxisAlignedBB(0.125F, 0.0F, 0.125F,  0.875F, 0.3125F, 0.875F);
    private static final AxisAlignedBB BASE_DOWN_BB = new AxisAlignedBB(0.125F, 0.6875F, 0.125F,  0.875F, 1.0F, 0.875F);
    private static final AxisAlignedBB BASE_SOUTH_BB = new AxisAlignedBB(0.125F, 0.125F, 0.0F,  0.875F, 0.875F, 0.3125F);
    private static final AxisAlignedBB BASE_NORTH_BB = new AxisAlignedBB(0.125F, 0.125F, 0.6875F,  0.875F, 0.875F, 1.0F);
    private static final AxisAlignedBB BASE_EAST_BB = new AxisAlignedBB(0.0F, 0.125F, 0.125F,  0.3125F, 0.875F, 0.875F);
    private static final AxisAlignedBB BASE_WEST_BB = new AxisAlignedBB(0.6875F, 0.125F, 0.125F,  1.0F, 0.875F, 0.875F);
    private static final AxisAlignedBB POLE_UP_BB = new AxisAlignedBB(0.1875F, 0.3125F, 0.1875F,  0.8125F, 1.0F, 0.8125F);
    private static final AxisAlignedBB POLE_DOWN_BB = new AxisAlignedBB(0.1875F, 0.0F, 0.1875F,  0.8125F, 0.6875F, 0.8125F);
    private static final AxisAlignedBB POLE_SOUTH_BB = new AxisAlignedBB(0.1875F, 0.1875F, 0.3125F,  0.8125F, 0.8125F, 1.0F);
    private static final AxisAlignedBB POLE_NORTH_BB = new AxisAlignedBB(0.1875F, 0.1875F, 0.0F,  0.8125F, 0.8125F, 0.6875F);
    private static final AxisAlignedBB POLE_EAST_BB = new AxisAlignedBB(0.3125F, 0.1875F, 0.1875F,  1.0F, 0.8125F, 0.8125F);
    private static final AxisAlignedBB POLE_WEST_BB = new AxisAlignedBB(0.0F, 0.1875F, 0.1875F,  0.6875F, 0.8125F, 0.8125F);

    public BlockDecoPillarBase()
    {
        super(Material.WOOD);
        setRegistryName(Strings.WOODEN_PILLAR_BASE_NAME);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.WOODEN_PILLAR_BASE_NAME);
        setCreativeTab(Totemic.tabsTotem);
        setHardness(2);
        setResistance(5);
        setSoundType(SoundType.WOOD);
        Blocks.FIRE.setFireInfo(this, 5, 5);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.UP).withProperty(WOOD, WoodVariant.OAK).withProperty(STRIPPED, false));
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
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        for(int i = 0; i < WoodVariant.values().length; i++)
            items.add(new ItemStack(this, 1, 2*i));
        for(int i = 0; i < WoodVariant.values().length; i++)
            items.add(new ItemStack(this, 1, 2*i + 1));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        switch(state.getValue(FACING))
        {
        case UP:
        case DOWN:
        default:
            return Y_BB;
        case SOUTH:
        case NORTH:
            return Z_BB;
        case EAST:
        case WEST:
            return X_BB;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
            List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState)
    {
        switch(state.getValue(FACING))
        {
        case UP:
            addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE_UP_BB);
            addCollisionBoxToList(pos, entityBox, collidingBoxes, POLE_UP_BB);
            break;
        case DOWN:
            addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE_DOWN_BB);
            addCollisionBoxToList(pos, entityBox, collidingBoxes, POLE_DOWN_BB);
            break;
        case SOUTH:
            addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE_SOUTH_BB);
            addCollisionBoxToList(pos, entityBox, collidingBoxes, POLE_SOUTH_BB);
            break;
        case NORTH:
            addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE_NORTH_BB);
            addCollisionBoxToList(pos, entityBox, collidingBoxes, POLE_NORTH_BB);
            break;
        case EAST:
            addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE_EAST_BB);
            addCollisionBoxToList(pos, entityBox, collidingBoxes, POLE_EAST_BB);
            break;
        case WEST:
            addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE_WEST_BB);
            addCollisionBoxToList(pos, entityBox, collidingBoxes, POLE_WEST_BB);
            break;
        }
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        TileDecoPillar tile = (TileDecoPillar) world.getTileEntity(pos);
        if(tile.isStripped() || state.getValue(FACING).getAxis() == Axis.Y)
            return tile.getWoodType().getMapColor();
        else
            return BlockDecoPillar.getBarkColor(tile.getWoodType());
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, WOOD, STRIPPED);
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
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return getDefaultState().withProperty(FACING, facing);
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
        if(face.getAxis() == state.getValue(FACING).getAxis())
            return BlockFaceShape.CENTER_BIG;
        else
            return BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return state.getValue(FACING).getAxis() == Axis.Y;
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
