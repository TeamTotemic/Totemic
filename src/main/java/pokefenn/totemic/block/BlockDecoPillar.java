package pokefenn.totemic.block;

import javax.annotation.Nullable;

import net.minecraft.block.BlockPlanks;
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
import net.minecraft.creativetab.CreativeTabs;
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
        tile.setFromMetadata(stack.getMetadata());
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TileDecoPillar)
            drops.add(new ItemStack(this, 1, ((TileDecoPillar) tile).getDropMetadata()));
        else
            drops.add(new ItemStack(this));
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
        if(willHarvest)
            return true; //Delay removal of the tile entity until after getDrops
        else
            return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state,
            @Nullable TileEntity te, ItemStack stack)
    {
        super.harvestBlock(world, player, pos, state, te, stack);
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        TileDecoPillar tile = (TileDecoPillar) world.getTileEntity(pos);
        return new ItemStack(this, 1, tile.getDropMetadata());
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
        TileDecoPillar tile = (TileDecoPillar) world.getTileEntity(pos);
        if(tile.isStripped() || state.getValue(AXIS) == Axis.Y)
            return tile.getWoodType().getMapColor();
        else
            return getBarkColor(tile.getWoodType());
    }

    static MapColor getBarkColor(WoodVariant wood)
    {
        //See BlockOldLog.getMapColor and BlockNewLog.getMapColor
        switch(wood)
        {
        case OAK: default: return BlockPlanks.EnumType.SPRUCE.getMapColor();
        case SPRUCE: return BlockPlanks.EnumType.DARK_OAK.getMapColor();
        case BIRCH: return MapColor.QUARTZ;
        case JUNGLE: return BlockPlanks.EnumType.SPRUCE.getMapColor();
        case ACACIA: return MapColor.STONE;
        case DARK_OAK: return BlockPlanks.EnumType.DARK_OAK.getMapColor();
        case CEDAR: return MapColor.ADOBE;
        }
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
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileDecoPillar();
    }
}
