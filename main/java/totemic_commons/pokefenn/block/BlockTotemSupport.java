package totemic_commons.pokefenn.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.ITotemBlock;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TileTotemSupport;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 28/01/14
 * Time: 17:58
 */
public class BlockTotemSupport extends BlockTile implements ITotemBlock
{
    public BlockTotemSupport(int id)
    {
        super(id, Material.wood);
        setUnlocalizedName(Strings.TOTEM_SUPPORT_NAME);
        setCreativeTab(Totemic.tabsTotem);
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileTotemSupport();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {

        TileTotemSupport tileTotemSupport = (TileTotemSupport) world.getBlockTileEntity(x, y, z);

        ItemStack heldItem = player.inventory.getCurrentItem();

        int SLOT_ONE = TileTotemSupport.SLOT_ONE;

        if (tileTotemSupport != null && !world.isRemote && player != null)
        {

            if (tileTotemSupport.getStackInSlot(SLOT_ONE) == null && heldItem != null && heldItem.itemID == ModItems.chlorophyllCrystal.itemID && heldItem.getItemDamage() != 0)
            {
                tileTotemSupport.setInventorySlotContents(SLOT_ONE, heldItem);
                player.destroyCurrentEquippedItem();


            } else if (tileTotemSupport.getStackInSlot(SLOT_ONE) != null && heldItem == null)
            {
                if (tileTotemSupport.getStackInSlot(SLOT_ONE).itemID == ModItems.chlorophyllCrystal.itemID)
                {

                    player.inventory.addItemStackToInventory(new ItemStack(ModItems.chlorophyllCrystal, 1, tileTotemSupport.getStackInSlot(SLOT_ONE).getItemDamage()));
                    tileTotemSupport.setInventorySlotContents(SLOT_ONE, null);

                }

            }

            world.markBlockForUpdate(x, y, z);

        }


        return !player.isSneaking();
    }
}
