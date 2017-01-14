package totemic_commons.pokefenn.block.totem;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.TotemicStaffUsage;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.WoodVariant;
import totemic_commons.pokefenn.tileentity.totem.*;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase.State;

public class BlockTotemBase extends Block implements ITileEntityProvider, TotemicStaffUsage
{
    public static final PropertyEnum<WoodVariant> WOOD = PropertyEnum.create("wood", WoodVariant.class);

    public BlockTotemBase()
    {
        super(Material.WOOD);
        setRegistryName(Strings.TOTEM_BASE_NAME);
        setUnlocalizedName(Strings.TOTEM_BASE_NAME);
        setCreativeTab(Totemic.tabsTotem);
        setHardness(2);
        setSoundType(SoundType.WOOD);
    }

    @Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer player)
    {
        if(!world.isRemote)
        {
            TileTotemBase tile = (TileTotemBase) world.getTileEntity(pos);
            if(tile != null)
                if(player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() == ModItems.totemicStaff && tile.getState() != State.TOTEM_EFFECT)
                {
                    ((WorldServer) world).spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16, 0.6D, 0.5D, 0.6D, 0.0D);
                    tile.resetState();
                }
        }
    }

    @Override
    public EnumActionResult onTotemicStaffRightClick(World world, BlockPos pos, EntityPlayer player, ItemStack itemStack)
    {
        if(world.isRemote)
            return EnumActionResult.SUCCESS;
        TileTotemBase tile = (TileTotemBase) world.getTileEntity(pos);

        if(tile.getStateObj() instanceof StateTotemEffect)
        {
            player.addChatComponentMessage(new TextComponentTranslation("totemicmisc.isDoingNoCeremony"));
        }
        else if(tile.getStateObj() instanceof StateSelection)
        {
            String selectors = ((StateSelection) tile.getStateObj()).getSelectors().stream()
                    .map(instr -> I18n.format(instr.getUnlocalizedName()))
                    .collect(Collectors.joining(", "));
            player.addChatComponentMessage(new TextComponentTranslation("totemicmisc.isDoingSelection"));
            player.addChatComponentMessage(new TextComponentTranslation("totemicmisc.selection", selectors));
        }
        else if(tile.getStateObj() instanceof StateStartup)
        {
            Ceremony ceremony = ((StateStartup) tile.getStateObj()).getCeremony();
            player.addChatComponentMessage(new TextComponentTranslation("totemicmisc.isDoingStartup"));
            player.addChatComponentMessage(new TextComponentTranslation(ceremony.getUnlocalizedName()));
        }
        else if(tile.getStateObj() instanceof StateCeremonyEffect)
        {
            Ceremony ceremony = ((StateCeremonyEffect) tile.getStateObj()).getCeremony();
            player.addChatComponentMessage(new TextComponentTranslation("totemicmisc.isDoingCeremony"));
            player.addChatComponentMessage(new TextComponentTranslation(ceremony.getUnlocalizedName()));
        }

        return EnumActionResult.SUCCESS;
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return getMetaFromState(state);
    }

    @Override
    public int quantityDropped(Random rand)
    {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list)
    {
        for(int i = 0; i < WoodVariant.values().length; i++)
            list.add(new ItemStack(item, 1, i));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, WOOD);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(WOOD).ordinal();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(WOOD, WoodVariant.values()[meta]);
    }

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
}
