package totemic_commons.pokefenn.block

import rukalib_commons.pokefenn.block.BlockTile
import net.minecraft.block.material.Material
import net.minecraft.world.World
import net.minecraft.tileentity.TileEntity
import totemic_commons.pokefenn.lib.Strings
import totemic_commons.pokefenn.{ModBlocks, ModItems, Totemic}
import totemic_commons.pokefenn.tileentity.{TileTotemic, TileChlorophyllSolidifier, TileTotemTable}
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.MathHelper
import net.minecraftforge.common.ForgeDirection
import net.minecraft.inventory.IInventory
import net.minecraft.entity.item.EntityItem
import net.minecraft.nbt.NBTTagCompound
import java.util.Random

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 14/01/14
 * Time: 20:50

 */
class BlockTotemTable(id: Int) extends BlockTile(id, Material.wood) {

    setUnlocalizedName(Strings.TOTEM_TABLE_NAME)
    setHardness(1F)
    setCreativeTab(Totemic.tabsTotem)

    def createNewTileEntity(world: World): TileEntity = {

        new TileTotemTable
    }

    /*
    override def isOpaqueCube() {
        false
    }

    override def getRenderType() {
        -1
    }

    override def renderAsNormalBlock() {
        false
    }
    */

    override def onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, side: Int, hitX: Float, hitY: Float, hitZ: Float): Boolean = {

        val tileTotemTable: TileTotemTable = world.getBlockTileEntity(x, y, z).asInstanceOf[TileTotemTable]

        val heldItem: ItemStack = player.inventory.getCurrentItem

        val SLOT_ONE: Int = TileChlorophyllSolidifier.SLOT_ONE

        if (tileTotemTable != null && !world.isRemote)
        {
            if (tileTotemTable.getStackInSlot(SLOT_ONE) != null && heldItem == null)
            {
                player.inventory.addItemStackToInventory(tileTotemTable.getStackInSlot(SLOT_ONE))
                tileTotemTable.setInventorySlotContents(SLOT_ONE, null)

            } else if (tileTotemTable.getStackInSlot(SLOT_ONE) == null && heldItem != null)
            {
                tileTotemTable.setInventorySlotContents(SLOT_ONE, new ItemStack(heldItem.getItem, 1, heldItem.getItemDamage))
                heldItem.stackSize -= 1

            } else if (tileTotemTable.getStackInSlot(SLOT_ONE) != null && heldItem != null && ItemStack.areItemStacksEqual(heldItem, tileTotemTable.getStackInSlot(SLOT_ONE)))
            {
                heldItem.stackSize += 1
                tileTotemTable.decrStackSize(SLOT_ONE, 1)

            } else if (tileTotemTable.getStackInSlot(SLOT_ONE) != null && heldItem != null)
            {

                handleRecipes(world, x, y, z, player, side, hitX, hitY, hitZ)
                System.out.println("trying to enter handleRecipe")
            }


        }

        world.markBlockForUpdate(x, y, z)

        if (player.isSneaking)
        {
            false
        } else
        {
            true
        }

    }

    override def onBlockPlacedBy(world: World, x: Int, y: Int, z: Int, entityLiving: EntityLivingBase, itemStack: ItemStack) {
        var direction: Int = 0
        val facing: Int = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3
        facing match
        {
            case 0 =>
                direction = ForgeDirection.NORTH.ordinal

            case 1 =>
                direction = ForgeDirection.EAST.ordinal

            case 2 =>
                direction = ForgeDirection.SOUTH.ordinal

            case 3 =>
                direction = ForgeDirection.WEST.ordinal

        }
        world.setBlockMetadataWithNotify(x, y, z, direction, 3)
        if (itemStack.hasDisplayName)
        {
            world.getBlockTileEntity(x, y, z).asInstanceOf[TileTotemic].setCustomName(itemStack.getDisplayName)
        }
        world.getBlockTileEntity(x, y, z).asInstanceOf[TileTotemic].setOrientation(direction)
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
                i += 1
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

    var rand: Random = new Random

    def handleRecipes(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, side: Int, hitX: Float, hitY: Float, hitZ: Float) {

        //Todo recipe handler that is good >.>

        System.out.println("Entered handleRecipes")

        val tileTotemTable: TileTotemTable = world.getBlockTileEntity(x, y, z).asInstanceOf[TileTotemTable]

        val heldItem: ItemStack = player.inventory.getCurrentItem

        val SLOT_ONE: Int = TileChlorophyllSolidifier.SLOT_ONE

        if (tileTotemTable.getStackInSlot(SLOT_ONE).isItemEqual(new ItemStack(ModItems.totems, 1)) && heldItem.isItemEqual(new ItemStack(ModItems.totemWhittlingKnife, 1, 3)))
        {
            System.out.println("if 1")
            tileTotemTable.decrStackSize(SLOT_ONE, 1)
            tileTotemTable.setInventorySlotContents(SLOT_ONE, new ItemStack(ModItems.totems, 1, 1))

        } else if (ItemStack.areItemStacksEqual(tileTotemTable.getStackInSlot(SLOT_ONE), new ItemStack(ModItems.totems, 1, 1)) && ItemStack.areItemStacksEqual(heldItem, new ItemStack(ModItems.totemWhittlingKnife, 1, 3)))
        {
            System.out.println("if 2")
            tileTotemTable.decrStackSize(SLOT_ONE, 1)
            tileTotemTable.setInventorySlotContents(SLOT_ONE, new ItemStack(ModItems.totems, 1, 2))

        } else if (ItemStack.areItemStacksEqual(tileTotemTable.getStackInSlot(SLOT_ONE), new ItemStack(ModItems.totems, 1, 2)) && ItemStack.areItemStacksEqual(heldItem, new ItemStack(ModItems.totemWhittlingKnife, 1, 3)))
        {
            System.out.println("if 3")
            tileTotemTable.decrStackSize(SLOT_ONE, 1)
            tileTotemTable.setInventorySlotContents(SLOT_ONE, new ItemStack(ModItems.totems, 1, 3))

        } else if (ItemStack.areItemStacksEqual(tileTotemTable.getStackInSlot(SLOT_ONE), new ItemStack(ModBlocks.totemWoods, 1)) && ItemStack.areItemStacksEqual(heldItem, new ItemStack(ModItems.totemWhittlingKnife, 1, 3)))
        {
            System.out.println("if 4")
            tileTotemTable.decrStackSize(SLOT_ONE, 1)
            tileTotemTable.setInventorySlotContents(SLOT_ONE, new ItemStack(ModItems.totems, 1))

        }

    }

}
