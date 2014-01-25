package totemic_commons.pokefenn.block

import net.minecraft.block.material.Material
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.MathHelper
import net.minecraft.world.World
import net.minecraftforge.common.ForgeDirection
import rukalib_commons.pokefenn.block.BlockTile
import totemic_commons.pokefenn.{ModItems, Totemic}
import totemic_commons.pokefenn.lib.Strings
import totemic_commons.pokefenn.tileentity.TileChlorophyllSolidifier
import totemic_commons.pokefenn.tileentity.TileTotemic
import java.util.Random
import net.minecraftforge.fluids.FluidStack
import totemic_commons.pokefenn.fluid.ModFluids

class BlockChlorophyllSolidifier(id: Int) extends BlockTile(id, Material.wood) {

    setUnlocalizedName(Strings.CHLOROPHYLL_SOLIDIFIER_NAME)
    setHardness(1F)
    setCreativeTab(Totemic.tabsTotem)


    def createNewTileEntity(world: World): TileEntity = {
        new TileChlorophyllSolidifier
    }

    override def onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, side: Int, hitX: Float, hitY: Float, hitZ: Float): Boolean = {

        val tileChlorophyllSolidifier: TileChlorophyllSolidifier = world.getBlockTileEntity(x, y, z).asInstanceOf[TileChlorophyllSolidifier]

        val heldItem: ItemStack = player.inventory.getCurrentItem

        val SLOT_ONE: Int = TileChlorophyllSolidifier.SLOT_ONE

        if (tileChlorophyllSolidifier != null)
        {
            //Todo make one slot inventory

            if (tileChlorophyllSolidifier.getStackInSlot(SLOT_ONE) == null && heldItem != null && heldItem.itemID != ModItems.bottleChlorophyll.itemID && heldItem.itemID != ModItems.bucketChlorophyll.itemID)
            {

                tileChlorophyllSolidifier.setInventorySlotContents(SLOT_ONE, null)
                tileChlorophyllSolidifier.setInventorySlotContents(SLOT_ONE, new ItemStack(heldItem.getItem(), 1, heldItem.getItemDamage))
                heldItem.stackSize -= 1

            }
            else if (tileChlorophyllSolidifier.getStackInSlot(SLOT_ONE) != null && heldItem == null)
            {
                player.inventory.addItemStackToInventory(tileChlorophyllSolidifier.getStackInSlot(SLOT_ONE))
                tileChlorophyllSolidifier.setInventorySlotContents(SLOT_ONE, null)


            } else if (heldItem != null)
            {

                if (heldItem.itemID == ModItems.bottleChlorophyll.itemID)
                {
                    tileChlorophyllSolidifier.fill(ForgeDirection.getOrientation(side), new FluidStack(ModFluids.fluidChlorophyll, 1000), true)
                    heldItem.stackSize -= 1

                }
                else if (heldItem.itemID == ModItems.bucketChlorophyll.itemID)
                {
                    tileChlorophyllSolidifier.fill(ForgeDirection.getOrientation(side), new FluidStack(ModFluids.fluidChlorophyll, 1000), true)
                    player.destroyCurrentEquippedItem()
                    player.inventory.addItemStackToInventory(new ItemStack(Item.bucketEmpty))

                }

            }

            world.markBlockForUpdate(x, y, z)

        }

        !player.isSneaking()
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
}