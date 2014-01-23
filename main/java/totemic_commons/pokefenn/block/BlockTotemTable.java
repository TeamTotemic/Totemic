package totemic_commons.pokefenn.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import rukalib_commons.pokefenn.block.BlockTile;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.recipe.TotemTableHandler;
import totemic_commons.pokefenn.tileentity.TileChlorophyllSolidifier;
import totemic_commons.pokefenn.tileentity.TileTotemTable;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 23/01/14
 * Time: 14:40
 */
public class BlockTotemTable extends BlockTile
{

    public BlockTotemTable(int id)
    {
        super(id, Material.wood);
        setUnlocalizedName(Strings.TOTEM_TABLE_NAME);
        setCreativeTab(Totemic.tabsTotem);
        setHardness(1F);

    }


    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileTotemTable();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {

        TileTotemTable tileTotemTable = (TileTotemTable) world.getBlockTileEntity(x, y, z);

        ItemStack heldItem = player.inventory.getCurrentItem();

        int SLOT_ONE = TileChlorophyllSolidifier.SLOT_ONE;

        if (tileTotemTable != null && !world.isRemote)
        {
            if (tileTotemTable.getStackInSlot(SLOT_ONE) != null && heldItem != null)
            {
                System.out.println("entering if");

                for (TotemTableHandler totemTableHandler : TotemTableHandler.totemTableRecipe)
                {
                    System.out.println("entered for loop");

                    if (ItemStack.areItemStackTagsEqual(totemTableHandler.getInput2(), tileTotemTable.getStackInSlot(SLOT_ONE)) && ItemStack.areItemStacksEqual(heldItem, totemTableHandler.getInput()) && tileTotemTable.getStackInSlot(SLOT_ONE) != null && heldItem != null)
                    {

                        System.out.println("entered if for for loop");
                        tileTotemTable.setInventorySlotContents(SLOT_ONE, totemTableHandler.getOutput());

                    }
                }

            } else if (heldItem == null && tileTotemTable.getStackInSlot(SLOT_ONE) != null)
            {
                player.inventory.addItemStackToInventory(tileTotemTable.getStackInSlot(SLOT_ONE));
                tileTotemTable.setInventorySlotContents(SLOT_ONE, null);

            } else if (tileTotemTable.getStackInSlot(SLOT_ONE) == null && heldItem != null)
            {

                heldItem.stackSize--;
                tileTotemTable.setInventorySlotContents(SLOT_ONE, new ItemStack(heldItem.getItem(), 1, heldItem.getItemDamage()));

            }


        }

        return !player.isSneaking();
    }


}
