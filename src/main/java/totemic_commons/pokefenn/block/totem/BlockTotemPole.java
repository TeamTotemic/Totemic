package totemic_commons.pokefenn.block.totem;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.block.BlockTileTotemic;
import totemic_commons.pokefenn.item.ItemTotems;
import totemic_commons.pokefenn.lib.RenderIds;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.totem.TileTotemPole;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 02/02/14
 * Time: 13:03
 */
public class BlockTotemPole extends BlockTileTotemic
{

    public BlockTotemPole()
    {
        super(Material.wood);
        setBlockName(Strings.TOTEM_POLE_NAME);
        setCreativeTab(Totemic.tabsTotem);
        setBlockBounds(0.2F, 0.0F, 0.2F, 0.8F, 0.95F, 0.8F);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        TileTotemPole tileTotemSocket = (TileTotemPole) world.getTileEntity(x, y, z);

        ItemStack heldItem = player.inventory.getCurrentItem();

        int SLOT_ONE = TileTotemPole.SLOT_ONE;

        if(tileTotemSocket != null && !world.isRemote)
        {
            if(player.getHeldItem() != null && tileTotemSocket.getStackInSlot(0) != null && (player.getHeldItem().getItem() == ModItems.totemicStaff || player.getHeldItem().getItem() == ModItems.infusedTotemicStaff))
            {
                player.addChatComponentMessage(new ChatComponentText("Active Totem Effect: " + ItemTotems.TOTEM_NAMES[tileTotemSocket.getStackInSlot(0).getItemDamage()]));
            }
            world.markBlockForUpdate(x, y, z);
        }

        return true;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return RenderIds.RENDER_ID_TOTEM_SOCKET_CUBE;
    }

    @Override
    public int quantityDropped(Random rand)
    {
        return 0;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileTotemPole();
    }
}
