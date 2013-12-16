package totemic_commons.pokefenn.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hinalib_commons.pokefenn.block.BlockTile;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import totemic_commons.pokefenn.Totemic;
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

    public BlockChlorophyllSolidifier(int id){

        super(id, Material.iron);
        this.setUnlocalizedName(Strings.CHLOROPHYLL_SOLIDIFIER_NAME);
        this.setHardness(1F);
        this.setCreativeTab(Totemic.tabsTotem);



    }


    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileChlorophyllSolidifier();
    }

    @Override
    @SideOnly(Side.SERVER)
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){



                TileChlorophyllSolidifier tileChlorophyllSolidifier = (TileChlorophyllSolidifier) world.getBlockTileEntity(x, y, z);


            return true;

    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {

        int direction = 0;
        int facing = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

        if (facing == 0) {
            direction = ForgeDirection.NORTH.ordinal();
        }
        else if (facing == 1) {
            direction = ForgeDirection.EAST.ordinal();
        }
        else if (facing == 2) {
            direction = ForgeDirection.SOUTH.ordinal();
        }
        else if (facing == 3) {
            direction = ForgeDirection.WEST.ordinal();
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