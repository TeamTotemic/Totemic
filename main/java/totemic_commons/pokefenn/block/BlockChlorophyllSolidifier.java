package totemic_commons.pokefenn.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

/**
 * Created with IntelliJ IDEA.
 * User: ${Pokefenn}
 * Date: 13/11/13
 * Time: 16:10
 */
public class BlockChlorophyllSolidifier extends BlockTile {

    public BlockChlorophyllSolidifier(int id) {

        super(id, Material.iron);
        setUnlocalizedName(Strings.CHLOROPHYLL_SOLIDIFIER_NAME);
        setHardness(1F);
        setCreativeTab(Totemic.tabsTotem);


    }


    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileChlorophyllSolidifier();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

        TileChlorophyllSolidifier tileChlorophyllSolidifier = (TileChlorophyllSolidifier) world.getBlockTileEntity(x, y, z);

        ItemStack heldItem = player.inventory.getCurrentItem();

        if (tileChlorophyllSolidifier != null && heldItem != null) {

            if (tileChlorophyllSolidifier.getStackInSlot(tileChlorophyllSolidifier.INVENTORY_SLOT_INDEX) == null && !ItemStack.areItemStacksEqual(heldItem, new ItemStack(ModItems.bottleChlorophyll)) || !ItemStack.areItemStacksEqual(heldItem, new ItemStack(ModItems.bucketChlorophyll))) {

                if (world.isRemote) {

                    tileChlorophyllSolidifier.setInventorySlotContents(tileChlorophyllSolidifier.INVENTORY_SLOT_INDEX, heldItem);

                    System.out.println("SettingInventoryOfSolidifier");


                    player.clearItemInUse();
                }

            } else if (ItemStack.areItemStacksEqual(heldItem, new ItemStack(ModItems.bottleChlorophyll)) || ItemStack.areItemStacksEqual(heldItem, new ItemStack(ModItems.bucketChlorophyll))) {

                if (!world.isRemote) {

                    if (ItemStack.areItemStacksEqual(heldItem, new ItemStack(ModItems.bottleChlorophyll))) {


                        System.out.println("trying to fill");

                        tileChlorophyllSolidifier.fill(ForgeDirection.DOWN, new FluidStack(ModFluids.fluidChlorophyll, FluidContainerRegistry.BUCKET_VOLUME), true);

                        player.clearItemInUse();

                        player.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));


                    } else if (ItemStack.areItemStacksEqual(heldItem, new ItemStack(ModItems.bucketChlorophyll))) {

                        System.out.println("trying to fill");

                        tileChlorophyllSolidifier.fill(ForgeDirection.DOWN, new FluidStack(ModFluids.fluidChlorophyll, FluidContainerRegistry.BUCKET_VOLUME), true);

                        player.clearItemInUse();

                        player.inventory.addItemStackToInventory(new ItemStack(Item.bucketEmpty));


                    }

                }
            }


            world.markBlockForUpdate(x, y, z);

        } else {

            if (world.isRemote) {


                System.out.println("trying to take inventory");

                player.inventory.addItemStackToInventory((tileChlorophyllSolidifier.getStackInSlot(tileChlorophyllSolidifier.INVENTORY_SLOT_INDEX)));

                tileChlorophyllSolidifier.decrStackSize(tileChlorophyllSolidifier.INVENTORY_SLOT_INDEX, 64);

            }

        }

        return true;

    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {

        int direction = 0;
        int facing = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

        switch (facing) {

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

        if (itemStack.hasDisplayName()) {
            ((TileTotemic) world.getBlockTileEntity(x, y, z)).setCustomName(itemStack.getDisplayName());
        }

        ((TileTotemic) world.getBlockTileEntity(x, y, z)).setOrientation(direction);
    }


}


//tileChlorophyllSolidifier.setInventorySlotContents(int slotIndex, int ItemStack TileChlorophyllSolidifier.INVENTORY_SLOT_INDEX);
//tileChlorophyllSolidifier.fill(ForgeDirection from, FluidStack resource, boolean doFill);