package totemic_commons.pokefenn.block

import net.minecraft.block.material.Material
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import rukalib_commons.pokefenn.block.BlockTile
import totemic_commons.pokefenn.tileentity.{TileChlorophyllSolidifier, TileTotemDraining}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import totemic_commons.pokefenn.{Totemic, ModItems}
import totemic_commons.pokefenn.lib.Strings
import net.minecraft.inventory.IInventory
import net.minecraft.entity.item.EntityItem
import net.minecraft.nbt.NBTTagCompound
import java.util.Random

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 12/01/14
 * Time: 22:47
 */
class BlockTotemDraining(id: Int) extends BlockTile(id, Material.wood) {

    this.setUnlocalizedName(Strings.TOTEM_DRAINING_NAME)
    this.setCreativeTab(Totemic.tabsTotem)
    this.setHardness(1F)

    var rand: Random = new Random

    def createNewTileEntity(world: World): TileEntity = {
        return new TileTotemDraining
    }

    override def onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, side: Int, hitX: Float, hitY: Float, hitZ: Float): Boolean = {

        val tileTotemDraining: TileTotemDraining = world.getBlockTileEntity(x, y, z).asInstanceOf[TileTotemDraining]

        val heldItem: ItemStack = player.inventory.getCurrentItem

        val SLOT_ONE: Int = TileTotemDraining.SLOT_ONE

        if (tileTotemDraining != null)
        {

            if (tileTotemDraining.getStackInSlot(SLOT_ONE) == null && heldItem != null && heldItem.itemID == ModItems.chlorophyllCrystal.itemID)
            {
                tileTotemDraining.setInventorySlotContents(SLOT_ONE, heldItem)
                player.destroyCurrentEquippedItem()

            } else if (tileTotemDraining.getStackInSlot(SLOT_ONE) != null && heldItem == null && tileTotemDraining.getStackInSlot(SLOT_ONE).itemID == ModItems.chlorophyllCrystal.itemID)
            {

                player.inventory.addItemStackToInventory(tileTotemDraining.getStackInSlot(SLOT_ONE))
                tileTotemDraining.setInventorySlotContents(SLOT_ONE, null)
            }

        }

        if (player.isSneaking)
        {
            false
        } else
        {
            true
        }
    }

    def dropInventory(world: World, x: Int, y: Int, z: Int) {
        val tileEntity: TileEntity = world.getBlockTileEntity(x, y, z)
        if (!tileEntity.isInstanceOf[IInventory]) return
        val inventory: IInventory = tileEntity.asInstanceOf[IInventory]

        var i: Int = 0
        while (i < inventory.getSizeInventory)
        {
            {
                val itemStack: ItemStack = inventory.getStackInSlot(i)
                if (itemStack != null && itemStack.stackSize > 0)
                {
                    val dX: Float = rand.nextFloat * 0.8F + 0.1F
                    val dY: Float = rand.nextFloat * 0.8F + 0.1F
                    val dZ: Float = rand.nextFloat * 0.8F + 0.1F
                    val entityItem: EntityItem = new EntityItem(world, x + dX, y + dY, z + dZ, new ItemStack(itemStack.itemID, itemStack.stackSize, itemStack.getItemDamage))
                    if (itemStack.hasTagCompound)
                    {
                        entityItem.getEntityItem.setTagCompound(itemStack.getTagCompound.copy.asInstanceOf[NBTTagCompound])
                    }
                    val factor: Float = 0.05F
                    entityItem.motionX = rand.nextGaussian * factor
                    entityItem.motionY = rand.nextGaussian * factor + 0.2F
                    entityItem.motionZ = rand.nextGaussian * factor
                    world.spawnEntityInWorld(entityItem)
                    itemStack.stackSize = 0
                }
            }
            {
                i += 1;
                i - 1
            }
        }

    }

    override def breakBlock(world: World, x: Int, y: Int, z: Int, id: Int, meta: Int) {
        dropInventory(world, x, y, z)
        if (world.getBlockTileEntity(x, y, z).isInstanceOf[TileChlorophyllSolidifier])
        {
            world.markBlockForUpdate(x, y, z)
            world.updateAllLightTypes(x, y, z)
        }
        super.breakBlock(world, x, y, z, id, meta)
    }

}