package totemic_commons.pokefenn.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.ITotemBlock;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.Textures;
import totemic_commons.pokefenn.tileentity.TileTotemIntelligence;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 29/01/14
 * Time: 20:20
 */
public class BlockTotemIntelligence extends BlockTile implements ITotemBlock
{

    private Random rand = new Random();

    public BlockTotemIntelligence(int id)
    {
        super(id, Material.wood);
        setUnlocalizedName(Strings.TOTEM_INTELLIGENCE_NAME);
        setCreativeTab(Totemic.tabsTotem);

    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileTotemIntelligence();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {

        int SLOT_ONE = TileTotemIntelligence.SLOT_ONE;

        TileTotemIntelligence tileTotemIntelligence = (TileTotemIntelligence) world.getBlockTileEntity(x, y, z);


        ItemStack heldItem = player.inventory.getCurrentItem();


        if (tileTotemIntelligence != null && !world.isRemote)
        {

            if (tileTotemIntelligence.getStackInSlot(SLOT_ONE) == null && heldItem != null && heldItem.itemID == ModItems.chlorophyllCrystal.itemID)
            {
                tileTotemIntelligence.setInventorySlotContents(SLOT_ONE, heldItem);
                player.destroyCurrentEquippedItem();


            } else if (tileTotemIntelligence.getStackInSlot(SLOT_ONE) != null && heldItem == null)
            {
                if (tileTotemIntelligence.getStackInSlot(SLOT_ONE).itemID == ModItems.chlorophyllCrystal.itemID)
                {
                    EntityItem entityitem = new EntityItem(player.worldObj, player.posX + 0.5D, player.posY + 0.5D, player.posZ + 0.5D, tileTotemIntelligence.getStackInSlot(SLOT_ONE));
                    player.worldObj.spawnEntityInWorld(entityitem);
                    tileTotemIntelligence.setInventorySlotContents(SLOT_ONE, null);

                }

            }

            world.markBlockForUpdate(x, y, z);

        }


        return !player.isSneaking();
    }


    @Override
    public void breakBlock(World world, int x, int y, int z, int id, int meta)
    {

        dropInventory(world, x, y, z);

        if (world.getBlockTileEntity(x, y, z) instanceof TileTotemIntelligence)
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
        return topIcon;
    }
}
