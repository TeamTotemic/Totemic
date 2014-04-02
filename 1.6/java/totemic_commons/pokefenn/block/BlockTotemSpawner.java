package totemic_commons.pokefenn.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TileTotemSpawner;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockTotemSpawner extends BlockTileTotemic
{
    public BlockTotemSpawner(int id)
    {
        super(id, Material.wood);
        setUnlocalizedName(Strings.TOTEM_SPAWNER_NAME);
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileTotemSpawner();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {

        int SLOT_ONE = TileTotemSpawner.SLOT_ONE;

        TileTotemSpawner tileTotemSpawner = (TileTotemSpawner) world.getBlockTileEntity(x, y, z);


        ItemStack heldItem = player.inventory.getCurrentItem();


        if (tileTotemSpawner != null && !world.isRemote)
        {

            if (tileTotemSpawner.getStackInSlot(SLOT_ONE) == null && heldItem != null && heldItem.itemID == ModItems.chlorophyllCrystal.itemID || tileTotemSpawner.getStackInSlot(SLOT_ONE) == null && heldItem != null && heldItem.itemID == ModItems.blazingChlorophyllCrystal.itemID)
            {
                tileTotemSpawner.setInventorySlotContents(SLOT_ONE, heldItem);
                player.destroyCurrentEquippedItem();

            } else if (tileTotemSpawner.getStackInSlot(SLOT_ONE) != null && heldItem == null)
            {
                EntityItem entityitem = new EntityItem(player.worldObj, player.posX + 0.5D, player.posY + 0.5D, player.posZ + 0.5D, tileTotemSpawner.getStackInSlot(SLOT_ONE));
                world.spawnEntityInWorld(entityitem);
                tileTotemSpawner.setInventorySlotContents(SLOT_ONE, null);

            }

            world.markBlockForUpdate(x, y, z);

        }


        return !(heldItem != null && heldItem.itemID == ModItems.totemicStaff.itemID || heldItem != null && heldItem.itemID == ModItems.infusedTotemicStaff.itemID);

    }
}
