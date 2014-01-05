package totemic_commons.pokefenn.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import rukalib_commons.pokefenn.block.BlockTile;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.fluid.ModFluids;
import totemic_commons.pokefenn.item.ModItems;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TileChlorophyllSolidifier;
import totemic_commons.pokefenn.tileentity.TileTotemic;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: ${Pokefenn}
 * Date: 13/11/13
 * Time: 16:10
 */
public class BlockChlorophyllSolidifier extends BlockTile {

    public BlockChlorophyllSolidifier(int id)
    {

        super(id, Material.iron);
        setUnlocalizedName(Strings.CHLOROPHYLL_SOLIDIFIER_NAME);
        setHardness(1F);
        setCreativeTab(Totemic.tabsTotem);


    }

    private Random rand = new Random();

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileChlorophyllSolidifier();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {

        TileChlorophyllSolidifier tileChlorophyllSolidifier = (TileChlorophyllSolidifier) world.getBlockTileEntity(x, y, z);

        ItemStack heldItem = player.inventory.getCurrentItem();

        ForgeDirection[] directions = new ForgeDirection[]{ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.UP, ForgeDirection.DOWN};

        if (tileChlorophyllSolidifier != null)
        {

            if (heldItem != null && tileChlorophyllSolidifier.getStackInSlot(tileChlorophyllSolidifier.SLOT_ONE) == null && !ItemStack.areItemStacksEqual(heldItem, new ItemStack(ModItems.bottleChlorophyll)) || !ItemStack.areItemStacksEqual(heldItem, new ItemStack(ModItems.bucketChlorophyll)) && !world.isRemote)
            {

                if (ItemStack.areItemStacksEqual(heldItem, tileChlorophyllSolidifier.getStackInSlot(tileChlorophyllSolidifier.SLOT_ONE)))
                {
                    /*This code will add the players current slot to the inventory +*/
                    tileChlorophyllSolidifier.setInventorySlotContents(tileChlorophyllSolidifier.SLOT_ONE, new ItemStack(heldItem.getItem(), tileChlorophyllSolidifier.getStackInSlot(tileChlorophyllSolidifier.SLOT_ONE).stackSize + heldItem.stackSize, heldItem.getItemDamage()));

                    player.destroyCurrentEquippedItem();

                } else if(tileChlorophyllSolidifier.getStackInSlot(tileChlorophyllSolidifier.SLOT_ONE) == null)
                {

                    tileChlorophyllSolidifier.setInventorySlotContents(tileChlorophyllSolidifier.SLOT_ONE, heldItem);

                    //System.out.println("SettingInventoryOfSolidifier");

                    player.destroyCurrentEquippedItem();

                }

            } else if (world.isRemote && ItemStack.areItemStacksEqual(new ItemStack(ModItems.bottleChlorophyll), tileChlorophyllSolidifier.getStackInSlot(tileChlorophyllSolidifier.SLOT_ONE)) || ItemStack.areItemStacksEqual(new ItemStack(ModItems.bucketChlorophyll), tileChlorophyllSolidifier.getStackInSlot(tileChlorophyllSolidifier.SLOT_ONE)))
            {

                if (ItemStack.areItemStacksEqual(heldItem, new ItemStack(ModItems.bottleChlorophyll)))
                {

                    //System.out.println("trying to fill");

                    tileChlorophyllSolidifier.fill(ForgeDirection.DOWN, new FluidStack(ModFluids.fluidChlorophyll, FluidContainerRegistry.BUCKET_VOLUME), true);

                    player.destroyCurrentEquippedItem();

                } else if (ItemStack.areItemStacksEqual(heldItem, new ItemStack(ModItems.bucketChlorophyll)))
                {

                    //System.out.println("trying to fill");

                    tileChlorophyllSolidifier.fill(ForgeDirection.DOWN, new FluidStack(ModFluids.fluidChlorophyll, FluidContainerRegistry.BUCKET_VOLUME), true);

                    player.destroyCurrentEquippedItem();

                    player.inventory.addItemStackToInventory(new ItemStack(Item.bucketEmpty));


                }
            } else if (heldItem == null && tileChlorophyllSolidifier.getStackInSlot(tileChlorophyllSolidifier.SLOT_ONE) != null && world.isRemote)
            {

                /* This is what adds the inventory of the solidifier into your players*/
                System.out.println("Trying to give the player the stack in the inventory");

                player.inventory.addItemStackToInventory((tileChlorophyllSolidifier.getStackInSlot(tileChlorophyllSolidifier.SLOT_ONE)));

                tileChlorophyllSolidifier.decrStackSize(tileChlorophyllSolidifier.SLOT_ONE, tileChlorophyllSolidifier.getStackInSlot(tileChlorophyllSolidifier.SLOT_ONE).stackSize);


            }


        }

        if (player.isSneaking())
        {
            return false;

        } else
        {

            return true;

        }

    }


    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack)
    {

        int direction = 0;
        int facing = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

        switch (facing)
        {

            case 0:
                direction = ForgeDirection.NORTH.ordinal();
                break;

            case 1:
                direction = ForgeDirection.EAST.ordinal();
                break;

            case 2:
                direction = ForgeDirection.SOUTH.ordinal();
                break;

            case 3:
                direction = ForgeDirection.WEST.ordinal();
                break;

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

        if (world.getBlockTileEntity(x, y, z) instanceof TileChlorophyllSolidifier)
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