package totemic_commons.pokefenn.block.totem;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumParticleTypes;
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
import totemic_commons.pokefenn.tileentity.totem.StateCeremonyEffect;
import totemic_commons.pokefenn.tileentity.totem.StateStartup;
import totemic_commons.pokefenn.tileentity.totem.StateTotemEffect;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;

public class BlockTotemBase extends Block implements ITileEntityProvider, TotemicStaffUsage
{
    public static final PropertyEnum<WoodVariant> WOOD = PropertyEnum.create("wood", WoodVariant.class);

    public BlockTotemBase()
    {
        super(Material.wood);
        setUnlocalizedName(Strings.TOTEM_BASE_NAME);
        setCreativeTab(Totemic.tabsTotem);
        setBlockBounds(0.125F, 0.0F, 0.125F, 0.875F, 1.0F, 0.875F);
        setHardness(2);
        setStepSound(soundTypeWood);
    }

    @Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer player)
    {
        if(!world.isRemote)
        {
            TileTotemBase tile = (TileTotemBase) world.getTileEntity(pos);
            if(tile != null)
                if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.totemicStaff && !(tile.getState() instanceof StateTotemEffect))
                {
                    ((WorldServer) world).spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16, 0.6D, 0.5D, 0.6D, 0.0D);
                    tile.setState(new StateTotemEffect(tile));
                }
        }
    }

    @Override
    public boolean onTotemicStaffRightClick(World world, BlockPos pos, EntityPlayer player, ItemStack stack)
    {
        if(world.isRemote)
            return true;
        TileTotemBase tile = (TileTotemBase) world.getTileEntity(pos);
        if(tile != null)
        {
            if(tile.getState() instanceof StateStartup)
            {
                Ceremony ceremony = ((StateStartup) tile.getState()).getCeremony();
                player.addChatComponentMessage(new ChatComponentTranslation("totemicmisc.isDoingStartup"));
                player.addChatComponentMessage(new ChatComponentTranslation(ceremony.getUnlocalizedName()));
            }
            else if(tile.getState() instanceof StateCeremonyEffect)
            {
                Ceremony ceremony = ((StateCeremonyEffect) tile.getState()).getCeremony();
                player.addChatComponentMessage(new ChatComponentTranslation("totemicmisc.isDoingCeremony"));
                player.addChatComponentMessage(new ChatComponentTranslation(ceremony.getUnlocalizedName()));
            }
        }
        return true;
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
    protected BlockState createBlockState()
    {
        return new BlockState(this, WOOD);
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
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean isFullCube()
    {
        return false;
    }
}
