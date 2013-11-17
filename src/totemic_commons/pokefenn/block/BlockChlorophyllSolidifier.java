package totemic_commons.pokefenn.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.fluid.ItemBucketChlorophyll;
import totemic_commons.pokefenn.item.ModItems;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TileChlorophyllSolidifier;

/**
 * Created with IntelliJ IDEA.
 * User: ${Pokefenn}
 * Date: 13/11/13
 * Time: 16:10
 */
public class BlockChlorophyllSolidifier extends BlockTotemic {

    public BlockChlorophyllSolidifier(int id){

        super(id, Material.wood);
        this.setUnlocalizedName(Strings.CHLOROPHYLL_SOLIDIFIER_NAME);
        this.setHardness(1F);
        this.setCreativeTab(Totemic.tabsTotem);



    }


    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileChlorophyllSolidifier();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){

        if (player.isSneaking())
            return false;

        else {
            if (!world.isRemote) {
                TileChlorophyllSolidifier tileChlorophyllSolidifier = (TileChlorophyllSolidifier) world.getBlockTileEntity(x, y, z);


                if (/*tileChlorophyllSolidifier.getStackInSlot(TileChlorophyllSolidifier.INVENTORY_SLOT_INDEX) == null &&*/ tileChlorophyllSolidifier != null){



                    if(player.getHeldItem().itemID == ModItems.chlorophyllCrystal.itemID){



                      player.clearItemInUse();


                    }

                    }


                }

            }
            return true;
        }
    }




//tileChlorophyllSolidifier.setInventorySlotContents(int slotIndex, int ItemStack TileChlorophyllSolidifier.INVENTORY_SLOT_INDEX);
//tileChlorophyllSolidifier.fill(ForgeDirection from, FluidStack resource, boolean doFill);