package pokefenn.totemic.block.totem;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicRegistries;
import pokefenn.totemic.api.TotemicStaffUsage;
import pokefenn.totemic.api.totem.TotemBase;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.item.equipment.ItemTotemWhittlingKnife;
import pokefenn.totemic.lib.Strings;
import pokefenn.totemic.lib.WoodVariant;
import pokefenn.totemic.tileentity.totem.TileTotemPole;

public class BlockTotemPole extends Block implements ITileEntityProvider, TotemicStaffUsage
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyEnum<WoodVariant> WOOD = PropertyEnum.create("wood", WoodVariant.class);
    public static final IUnlistedProperty<TotemEffect> TOTEM = new IUnlistedProperty<TotemEffect>()
    {
        @Override
        public String getName()
        { return "totem"; }

        @Override
        public boolean isValid(TotemEffect value)
        { return true; }

        @Override
        public Class<TotemEffect> getType()
        { return TotemEffect.class; }

        @Override
        public String valueToString(TotemEffect value)
        { return (value != null) ? value.getRegistryName().toString() : "blank"; }
    };

    public BlockTotemPole()
    {
        super(Material.WOOD);
        setRegistryName(Strings.TOTEM_POLE_NAME);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.TOTEM_POLE_NAME);
        setCreativeTab(Totemic.tabsTotem);
        setHardness(2);
        setSoundType(SoundType.WOOD);
        Blocks.FIRE.setFireInfo(this, 5, 5);
    }

    @Override
    public EnumActionResult onTotemicStaffRightClick(World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(world.isRemote)
        {
            TileTotemPole pole = (TileTotemPole) world.getTileEntity(pos);
            String name = pole.getEffect() != null ? pole.getEffect().getUnlocalizedName() : "totemicmisc.noEffect";
            player.sendStatusMessage(new TextComponentTranslation("totemicmisc.activeEffect", new TextComponentTranslation(name)), true);
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entityLiving, ItemStack stack)
    {
        if(stack.getItem() == Item.getItemFromBlock(this))
        {
            TileTotemPole tile = (TileTotemPole) world.getTileEntity(pos);
            tile.setWoodType(WoodVariant.fromID(stack.getMetadata()));
            tile.setEffect(ItemTotemWhittlingKnife.getCarvingEffect(stack));
        }

        notifyTotemBase(world, pos);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        super.breakBlock(world, pos, state);
        notifyTotemBase(world, pos);
    }

    private void notifyTotemBase(World world, BlockPos pos)
    {
        for(int i = 0; i < TotemBase.MAX_POLE_SIZE; i++)
        {
            Block block = world.getBlockState(pos.down(i + 1)).getBlock();
            if(block instanceof BlockTotemBase)
            {
                world.addBlockEvent(pos.down(i + 1), block, BlockTotemBase.EVENT_POLE_CHANGE_ID, 0);
                break;
            }
            else if(!(block instanceof BlockTotemPole))
                break;
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        TileTotemPole tile = (TileTotemPole) world.getTileEntity(pos);
        ItemStack stack = new ItemStack(this, 1, tile.getWoodType().getID());
        TotemEffect effect = tile.getEffect();
        String effectName = (effect != null) ? effect.getRegistryName().toString() : ItemTotemWhittlingKnife.TOTEM_BASE_PLACEHOLDER_NAME;
        stack.setTagInfo(ItemTotemWhittlingKnife.KNIFE_TOTEM_KEY, new NBTTagString(effectName));
        return stack;
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        for(TotemEffect effect: TotemicRegistries.totemEffects())
        {
            ItemStack stack = new ItemStack(this, 1, 0);
            stack.setTagInfo(ItemTotemWhittlingKnife.KNIFE_TOTEM_KEY, new NBTTagString(effect.getRegistryName().toString()));
            items.add(stack);
        }

        ItemStack blankStack = new ItemStack(this, 1, 0);
        blankStack.setTagInfo(ItemTotemWhittlingKnife.KNIFE_TOTEM_KEY, new NBTTagString(ItemTotemWhittlingKnife.TOTEM_BASE_PLACEHOLDER_NAME));
        items.add(blankStack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
    {
        TotemEffect effect = ItemTotemWhittlingKnife.getCarvingEffect(stack);
        if(effect != null)
            tooltip.add(I18n.format(effect.getUnlocalizedName()));
        else
            tooltip.add(I18n.format(getUnlocalizedName() + ".noEffect"));
    }

    @Override
    public int quantityDropped(Random rand)
    {
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer.Builder(this).add(FACING, WOOD).add(TOTEM).build();
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

    //We actually need both getActualState and getExtendedState
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TileTotemPole)
            return state.withProperty(WOOD, ((TileTotemPole) tile).getWoodType());
        return state;
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        IExtendedBlockState extState = (IExtendedBlockState) state;
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TileTotemPole)
            return extState.withProperty(TOTEM, ((TileTotemPole) tile).getEffect());
        return extState.withProperty(TOTEM, null);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
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
        TileTotemPole tile = new TileTotemPole();
        tile.setWoodType(state.getValue(WOOD));
        return tile;
    }

    //Necessary for ITileEntityProvider
    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileTotemPole();
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
