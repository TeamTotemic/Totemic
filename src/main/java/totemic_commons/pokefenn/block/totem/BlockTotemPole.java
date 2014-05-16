package totemic_commons.pokefenn.block.totem;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.block.BlockTileTotemic;
import totemic_commons.pokefenn.configuration.ConfigurationSettings;
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
                player.addChatComponentMessage(new ChatComponentText("Active Totem: " + ItemTotems.TOTEM_NAMES[tileTotemSocket.getStackInSlot(0).getItemDamage()]));
            }

            /*
            if(tileTotemSocket.getStackInSlot(SLOT_ONE) == null && heldItem != null && tileTotemSocket.isItemValidForSlot(0, heldItem))
            {
                tileTotemSocket.setInventorySlotContents(SLOT_ONE, heldItem);
                player.destroyCurrentEquippedItem();

            } else if(tileTotemSocket.getStackInSlot(SLOT_ONE) != null && heldItem == null)
            {
                EntityItem entityitem = new EntityItem(player.worldObj, player.posX + 0.5D, player.posY + 0.5D, player.posZ + 0.5D, tileTotemSocket.getStackInSlot(SLOT_ONE));
                player.worldObj.spawnEntityInWorld(entityitem);
                tileTotemSocket.setInventorySlotContents(SLOT_ONE, null);

            }
            */
            world.markBlockForUpdate(x, y, z);
        }

        return true;
    }

    private Random rand = new Random();

    /*
    @Override
    public void breakBlock(World world, int x, int y, int z, Block id, int meta)
    {

        dropInventory(world, x, y, z);

        if(world.getTileEntity(x, y, z) instanceof TileTotemPole)
        {
            world.markBlockForUpdate(x, y, z);
            //world.updateAllLightTypes(x, y, z);
        }

        super.breakBlock(world, x, y, z, id, meta);
    }

    private void dropInventory(World world, int x, int y, int z)
    {

        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if(!(tileEntity instanceof IInventory))
            return;

        IInventory inventory = (IInventory) tileEntity;

        for(int i = 0; i < inventory.getSizeInventory(); i++)
        {

            ItemStack itemStack = inventory.getStackInSlot(i);

            if(itemStack != null && itemStack.stackSize > 0)
            {
                float dX = rand.nextFloat() * 0.8F + 0.1F;
                float dY = rand.nextFloat() * 0.8F + 0.1F;
                float dZ = rand.nextFloat() * 0.8F + 0.1F;

                EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z + dZ, new ItemStack(itemStack.getItem(), itemStack.stackSize, itemStack.getItemDamage()));

                if(itemStack.hasTagCompound())
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
    */
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
    @Override
    public Icon getIcon(int side, int meta)
    {
        return sideIcon;
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
        if(!ConfigurationSettings.RENDER_CUBE_SOCKET)
            return RenderIds.RENDER_ID_TOTEM_POLE;
        else
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
