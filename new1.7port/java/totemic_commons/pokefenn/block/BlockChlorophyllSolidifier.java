package totemic_commons.pokefenn.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.fluid.ModFluids;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.Textures;
import totemic_commons.pokefenn.tileentity.TileChlorophyllSolidifier;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 28/01/14
 * Time: 13:11
 */
public class BlockChlorophyllSolidifier extends BlockTileTotemic
{
    public BlockChlorophyllSolidifier()
    {
        super(Material.rock);
        setBlockName(Strings.CHLOROPHYLL_SOLIDIFIER_NAME);
        setCreativeTab(Totemic.tabsTotem);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {

        TileChlorophyllSolidifier tileChlorophyllSolidifier = (TileChlorophyllSolidifier) world.getTileEntity(x, y, z);

        ItemStack heldItem = player.inventory.getCurrentItem();

        int SLOT_ONE = TileChlorophyllSolidifier.SLOT_ONE;

        if (tileChlorophyllSolidifier != null && !world.isRemote)
        {
            if (tileChlorophyllSolidifier.getStackInSlot(SLOT_ONE) == null && heldItem != null && heldItem.getItem() != ModItems.bottleChlorophyll && heldItem.getItem() != ModItems.bucketChlorophyll && !heldItem.hasTagCompound())
            {
                tileChlorophyllSolidifier.setInventorySlotContents(SLOT_ONE, new ItemStack(heldItem.getItem(), 1, heldItem.getItemDamage()));
                heldItem.stackSize--;

            } else if (tileChlorophyllSolidifier.getStackInSlot(SLOT_ONE) != null && heldItem == null)
            {
                EntityItem entityitem = new EntityItem(player.worldObj, player.posX + 0.5D, player.posY + 0.5D, player.posZ + 0.5D, tileChlorophyllSolidifier.getStackInSlot(SLOT_ONE).copy());
                world.spawnEntityInWorld(entityitem);
                tileChlorophyllSolidifier.setInventorySlotContents(SLOT_ONE, null);

                //System.out.println("hrm");

            }
            if (heldItem != null && tileChlorophyllSolidifier.getStackInSlot(SLOT_ONE) == null && heldItem.getItem() == ModItems.bottleChlorophyll || heldItem != null && heldItem.getItem() == ModItems.bucketChlorophyll)
            {
                if (tileChlorophyllSolidifier.tank.getFluidAmount() + 1000 <= 16000)
                {
                    if (heldItem.getItem() == ModItems.bottleChlorophyll)
                    {
                        tileChlorophyllSolidifier.fill(ForgeDirection.getOrientation(side), new FluidStack(ModFluids.fluidChlorophyll, 1000), true);
                        if (!player.capabilities.isCreativeMode)
                            heldItem.stackSize--;

                    } else
                    {
                        tileChlorophyllSolidifier.fill(ForgeDirection.getOrientation(side), new FluidStack(ModFluids.fluidChlorophyll, 1000), true);
                        if (!player.capabilities.isCreativeMode)
                            player.destroyCurrentEquippedItem();
                        EntityItem entityBucket = new EntityItem(player.worldObj, player.posX + 0.5D, player.posY + 0.5D, player.posZ + 0.5D, new ItemStack(Items.bucket).copy());
                        world.spawnEntityInWorld(entityBucket);

                    }

                }
            }

            world.markBlockForUpdate(x, y, z);
        }

        return !player.isSneaking();
    }

    @SideOnly(Side.CLIENT)
    private IIcon topAndBotIIcon;
    @SideOnly(Side.CLIENT)
    private IIcon sideIIcon;

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register)
    {
        topAndBotIIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.CHLOROPHYLL_SOLIDIFIER_TOP_AND_BOT);
        sideIIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.CHLOROPHYLL_SOLIDIFIER_SIDES);

    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        if (side == 0 || side == 1)
        {
            return topAndBotIIcon;
        } else
            return sideIIcon;
    }

    Random rand = new Random();

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {

        dropInventory(world, x, y, z);

        if (world.getTileEntity(x, y, z) instanceof TileChlorophyllSolidifier)
        {
            world.markBlockForUpdate(x, y, z);
            //world.updateLi(x, y, z);
        }

        super.breakBlock(world, x, y, z, block, meta);
    }

    private void dropInventory(World world, int x, int y, int z)
    {

        TileEntity tileEntity = world.getTileEntity(x, y, z);

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

                EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z + dZ, new ItemStack(itemStack.getItem(), itemStack.stackSize, itemStack.getItemDamage()));

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


    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileChlorophyllSolidifier();
    }
}
