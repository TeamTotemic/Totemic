package totemic_commons.pokefenn.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.ITotemBlock;
import totemic_commons.pokefenn.lib.RenderIds;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TileTotemDraining;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 28/01/14
 * Time: 13:14
 */
public class BlockTotemDraining extends BlockTile implements ITotemBlock
{
    public BlockTotemDraining(int id)
    {
        super(id, Material.wood);
        setUnlocalizedName(Strings.TOTEM_DRAINING_NAME);
        setCreativeTab(Totemic.tabsTotem);

    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileTotemDraining();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {

        TileTotemDraining tileTotemDraining = (TileTotemDraining) world.getBlockTileEntity(x, y, z);

        ItemStack heldItem = player.inventory.getCurrentItem();

        int SLOT_ONE = TileTotemDraining.SLOT_ONE;

        if (tileTotemDraining != null && !world.isRemote && player != null)
        {

            if (tileTotemDraining.getStackInSlot(SLOT_ONE) == null && heldItem != null && heldItem.itemID == ModItems.chlorophyllCrystal.itemID || tileTotemDraining.getStackInSlot(SLOT_ONE) == null && heldItem != null && heldItem.itemID == ModItems.blazingChlorophyllCrystal.itemID)
            {
                tileTotemDraining.setInventorySlotContents(SLOT_ONE, heldItem);
                player.destroyCurrentEquippedItem();


            } else if (tileTotemDraining.getStackInSlot(SLOT_ONE) != null && heldItem == null)
            {
                if (tileTotemDraining.getStackInSlot(SLOT_ONE).itemID == ModItems.chlorophyllCrystal.itemID || tileTotemDraining.getStackInSlot(SLOT_ONE).itemID == ModItems.blazingChlorophyllCrystal.itemID)
                {
                    if (tileTotemDraining.getStackInSlot(SLOT_ONE).itemID == ModItems.chlorophyllCrystal.itemID)
                    {

                        EntityItem entityitem = new EntityItem(player.worldObj, player.posX + 0.5D, player.posY + 0.5D, player.posZ + 0.5D, tileTotemDraining.getStackInSlot(SLOT_ONE));
                        player.worldObj.spawnEntityInWorld(entityitem);
                        tileTotemDraining.setInventorySlotContents(SLOT_ONE, null);

                    } else
                    {
                        EntityItem entityitem = new EntityItem(player.worldObj, player.posX + 0.5D, player.posY + 0.5D, player.posZ + 0.5D, new ItemStack(ModItems.blazingChlorophyllCrystal, 1, tileTotemDraining.getStackInSlot(SLOT_ONE).getItemDamage()/* - 2*/));
                        player.worldObj.spawnEntityInWorld(entityitem);
                        tileTotemDraining.setInventorySlotContents(SLOT_ONE, null);
                    }
                }

            }

            world.markBlockForUpdate(x, y, z);

        }


        return !(heldItem != null && heldItem.itemID == ModItems.totemicStaff.itemID);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int id, int meta)
    {

        dropInventory(world, x, y, z);

        if (world.getBlockTileEntity(x, y, z) instanceof TileTotemDraining)
        {
            world.markBlockForUpdate(x, y, z);
            world.updateAllLightTypes(x, y, z);
        }

        super.breakBlock(world, x, y, z, id, meta);
    }

    private void dropInventory(World world, int x, int y, int z)
    {

        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if (!(tileEntity instanceof IInventory))
            return;

        IInventory inventory = (IInventory) tileEntity;

        for (int i = 0; i < inventory.getSizeInventory(); i++)
        {

            ItemStack itemStack = inventory.getStackInSlot(i);

            if (itemStack != null && itemStack.stackSize > 0)
            {
                float dX = rand.nextFloat() * 0.8F + 0.1F;
                float dY = rand.nextFloat() * 0.8F + 0.1F;
                float dZ = rand.nextFloat() * 0.8F + 0.1F;

                EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z + dZ, new ItemStack(itemStack.itemID, itemStack.stackSize, itemStack.getItemDamage()));

                if (itemStack.hasTagCompound())
                {
                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
                }

                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                itemStack.stackSize = 0;
            }
        }

    }
    /*

    @SideOnly(Side.CLIENT)
    private Icon topIcon;
    @SideOnly(Side.CLIENT)
    private Icon sideIcon;
    @SideOnly(Side.CLIENT)
    private Icon bottomIcon;

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister register)
    {
        topIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.TOTEM_TABLE_TOP);
        sideIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.TOTEM_TABLE_SIDE);
        bottomIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.TOTEM_TABLE_BOTTOM);
    }

    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta)
    {
        return topIcon;
    }

    */

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

        return RenderIds.RENDER_ID_TOTEM_POLE;
    }

    private Random rand = new Random();

}
