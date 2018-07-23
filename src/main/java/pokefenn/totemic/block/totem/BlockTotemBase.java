package pokefenn.totemic.block.totem;

import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicStaffUsage;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.init.ModItems;
import pokefenn.totemic.lib.Strings;
import pokefenn.totemic.lib.WoodVariant;
import pokefenn.totemic.tileentity.totem.*;

public class BlockTotemBase extends Block implements ITileEntityProvider, TotemicStaffUsage
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyEnum<WoodVariant> WOOD = PropertyEnum.create("wood", WoodVariant.class);

    public static final int EVENT_POLE_CHANGE_ID = 0;

    public BlockTotemBase()
    {
        super(Material.WOOD);
        setRegistryName(Strings.TOTEM_BASE_NAME);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.TOTEM_BASE_NAME);
        setCreativeTab(Totemic.tabsTotem);
        setHardness(2);
        setSoundType(SoundType.WOOD);
        Blocks.FIRE.setFireInfo(this, 5, 5);
    }

    @Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer player)
    {
        if(!world.isRemote)
        {
            TileTotemBase tile = (TileTotemBase) world.getTileEntity(pos);
            if(tile != null)
                if(player.getHeldItemMainhand().getItem() == ModItems.totemic_staff && !(tile.getState() instanceof StateTotemEffect))
                {
                    ((WorldServer) world).spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16, 0.6D, 0.5D, 0.6D, 0.0D);
                    tile.resetState();
                }
        }
    }

    @Override
    public EnumActionResult onTotemicStaffRightClick(World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!world.isRemote)
            return EnumActionResult.SUCCESS;
        TileTotemBase tile = (TileTotemBase) world.getTileEntity(pos);

        if(tile.getState() instanceof StateTotemEffect)
        {
            player.sendStatusMessage(new TextComponentTranslation("totemicmisc.isDoingNoCeremony"), true);
        }
        else if(tile.getState() instanceof StateSelection)
        {
            String selectors = ((StateSelection) tile.getState()).getSelectors().stream()
                    .map(instr -> I18n.format(instr.getUnlocalizedName()))
                    .collect(Collectors.joining(", "));
            player.sendStatusMessage(new TextComponentTranslation("totemicmisc.isDoingSelection"), false);
            player.sendStatusMessage(new TextComponentTranslation("totemicmisc.selection", selectors), false);
        }
        else if(tile.getState() instanceof StateStartup)
        {
            Ceremony ceremony = ((StateStartup) tile.getState()).getCeremony();
            player.sendStatusMessage(new TextComponentTranslation("totemicmisc.isDoingStartup"), false);
            player.sendStatusMessage(new TextComponentTranslation(ceremony.getUnlocalizedName()), false);
        }
        else if(tile.getState() instanceof StateCeremonyEffect)
        {
            Ceremony ceremony = ((StateCeremonyEffect) tile.getState()).getCeremony();
            player.sendStatusMessage(new TextComponentTranslation("totemicmisc.isDoingCeremony"), false);
            player.sendStatusMessage(new TextComponentTranslation(ceremony.getUnlocalizedName()), false);
        }

        return EnumActionResult.SUCCESS;
    }

    @Override
    public boolean eventReceived(IBlockState state, World world, BlockPos pos, int id, int param)
    {
        switch(id)
        {
        case EVENT_POLE_CHANGE_ID:
            TileEntity tile = world.getTileEntity(pos);
            if(tile instanceof TileTotemBase)
                ((TileTotemBase) tile).onPoleChange();
            return true;
        }
        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if(stack.getItem() == Item.getItemFromBlock(this))
        {
            TileTotemBase tile = (TileTotemBase) world.getTileEntity(pos);
            tile.setWoodType(WoodVariant.fromID(stack.getMetadata()));
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(this, 1, ((TileTotemBase) world.getTileEntity(pos)).getWoodType().getID());
    }

    @Override
    public int quantityDropped(Random rand)
    {
        return 0;
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return ((TileTotemBase) world.getTileEntity(pos)).getWoodType().getMapColor();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, WOOD);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TileTotemBase)
            return state.withProperty(WOOD, ((TileTotemBase) tile).getWoodType());
        return state;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirror)
    {
        return state.withRotation(mirror.toRotation(state.getValue(FACING)));
    }

    @Override
    @Nullable
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        TileTotemBase tile = new TileTotemBase();
        tile.setWoodType(state.getValue(WOOD));
        return tile;
    }

    //Necessary for ITileEntityProvider
    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileTotemBase();
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return new AxisAlignedBB(0.125F, 0.0F, 0.125F, 0.875F, 1.0F, 0.875F);
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
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing facing)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return true;
    }
}
