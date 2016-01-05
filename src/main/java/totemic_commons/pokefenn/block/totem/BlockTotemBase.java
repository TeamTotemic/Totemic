package totemic_commons.pokefenn.block.totem;

import java.util.List;
import java.util.Random;

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
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.TotemicStaffUsage;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.block.BlockTileTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.WoodVariant;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 29/01/14
 * Time: 20:20
 */
public class BlockTotemBase extends BlockTileTotemic implements TotemicStaffUsage
{
    public static final PropertyEnum<WoodVariant> WOOD = PropertyEnum.create("woodVariant", WoodVariant.class);

    public BlockTotemBase()
    {
        super(Material.wood);
        setUnlocalizedName(Strings.TOTEM_BASE_NAME);
        setCreativeTab(Totemic.tabsTotem);
        setBlockBounds(0.125F, 0.0F, 0.125F, 0.875F, 1.0F, 0.875F);
        setStepSound(soundTypeWood);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(this);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return getMetaFromState(state);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list)
    {
        for(int i = 0; i < WoodVariant.values().length; i++)
            list.add(new ItemStack(item, 1, i));
    }

    @Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer player)
    {
        if(!world.isRemote)
        {
            TileTotemBase tileTotemBase = (TileTotemBase) world.getTileEntity(pos);
            if(tileTotemBase != null)
                if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.totemicStaff && tileTotemBase.isCeremony)
                {
                    tileTotemBase.resetAfterCeremony(true);
                }
        }
    }

    @Override
    public int quantityDropped(Random rand)
    {
        return 0;
    }

    @Override
    public boolean onTotemicStaffRightClick(World world, BlockPos pos, EntityPlayer player, ItemStack stack)
    {
        if(world.isRemote)
            return true;
        TileTotemBase tileTotemBase = (TileTotemBase) world.getTileEntity(pos);
        if(tileTotemBase != null)
        {
            if(tileTotemBase.isDoingStartup())
            {
                Ceremony trying = tileTotemBase.startupCeremony;
                player.addChatComponentMessage(new ChatComponentText("The Totem Base is doing startup"));
                player.addChatComponentMessage(new ChatComponentText(trying.getLocalizedName()));
            }
            if(tileTotemBase.isDoingCeremonyEffect())
            {
                player.addChatComponentMessage(new ChatComponentText("The Totem Base is doing its effect"));
                player.addChatComponentMessage(new ChatComponentText(tileTotemBase.currentCeremony.getLocalizedName()));
            }

            if(!tileTotemBase.isDoingCeremonyEffect() && !player.isSneaking())
            {
                if(tileTotemBase.canMusicSelect() && tileTotemBase.musicSelector[0] == null && tileTotemBase.musicSelector[1] == null && !tileTotemBase.isDoingCeremonyEffect() && !tileTotemBase.isDoingStartup())
                {
                    player.addChatComponentMessage(new ChatComponentText("No Musical selection"));
                    return true;
                }
            }
        }
        return true;
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
    public TileEntity createNewTileEntity(World var1, int var2)
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

    //TODO: JSON model
}
