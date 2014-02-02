package totemic_commons.pokefenn.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.client.rendering.tileentity.TileTotemTableRenderer;
import totemic_commons.pokefenn.item.ItemTotems;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.Textures;
import totemic_commons.pokefenn.recipe.TotemTableHandler;
import totemic_commons.pokefenn.tileentity.TileTotemTable;
import totemic_commons.pokefenn.tileentity.TileTotemic;

import java.util.Random;

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

    private Random rand = new Random();


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

        int SLOT_ONE = TileTotemTable.SLOT_ONE;

        if (tileTotemTable != null && !world.isRemote)
        {
            if (heldItem == null && tileTotemTable.getStackInSlot(SLOT_ONE) != null)
            {
                player.inventory.addItemStackToInventory(tileTotemTable.getStackInSlot(SLOT_ONE));
                tileTotemTable.setInventorySlotContents(SLOT_ONE, null);

            } else if (tileTotemTable.getStackInSlot(SLOT_ONE) == null && heldItem != null)
            {

                heldItem.stackSize--;
                tileTotemTable.setInventorySlotContents(SLOT_ONE, new ItemStack(heldItem.getItem(), 1, heldItem.getItemDamage()));

            } else if (tileTotemTable.getStackInSlot(SLOT_ONE) != null && heldItem != null)
            {
                //Todo fix eating of slot 2 - 9

                for (TotemTableHandler totemTableHandler : TotemTableHandler.getRecipes())
                {
                    //System.out.println("entered for loop");

                    if (tileTotemTable.getStackInSlot(SLOT_ONE).getItem() instanceof ItemTotems)
                    {
                        if (ItemStack.areItemStacksEqual(heldItem, totemTableHandler.getInputHeldItem()) && ItemStack.areItemStacksEqual(tileTotemTable.getStackInSlot(SLOT_ONE), totemTableHandler.getInputInventory()))
                        {

                            tileTotemTable.setInventorySlotContents(SLOT_ONE, new ItemStack(ModItems.totems, 1, totemTableHandler.getOutput().getItemDamage()));

                        }

                    }
                }

            }

            world.markBlockForUpdate(x, y, z);

        }


        return !player.isSneaking();
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
        return TileTotemTableRenderer.totemTableModelID;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return true;
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
        switch (side)
        {
            case 0:
                return bottomIcon;
            case 1:
                return topIcon;
            default:
                return sideIcon;
        }

    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack)
    {

        int direction = 0;
        int facing = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

        if (facing == 0)
        {
            direction = ForgeDirection.NORTH.ordinal();
        } else if (facing == 1)
        {
            direction = ForgeDirection.EAST.ordinal();
        } else if (facing == 2)
        {
            direction = ForgeDirection.SOUTH.ordinal();
        } else if (facing == 3)
        {
            direction = ForgeDirection.WEST.ordinal();
        }

        world.setBlockMetadataWithNotify(x, y, z, direction, 3);

        if (itemStack.hasDisplayName())
        {
            ((TileTotemic) world.getBlockTileEntity(x, y, z)).setCustomName(itemStack.getDisplayName());
        }

        ((TileTotemic) world.getBlockTileEntity(x, y, z)).setOrientation(direction);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int id, int meta)
    {

        dropInventory(world, x, y, z);

        if (world.getBlockTileEntity(x, y, z) instanceof TileTotemTable)
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

}
