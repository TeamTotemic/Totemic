package totemic_commons.pokefenn.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TileTotemCauldron;


public class BlockTotemCauldron extends BlockTileTotemic
{
    public BlockTotemCauldron()
    {
        super(Material.rock);
        setBlockName(Strings.TOTEM_CAULDRON_NAME);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileTotemCauldron();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        TileTotemCauldron tileTotemCauldron = (TileTotemCauldron) world.getTileEntity(x, y, z);

        ItemStack heldItem = player.inventory.getCurrentItem();

        if(tileTotemCauldron != null && !world.isRemote)
        {
            if(heldItem != null && heldItem.getItem() == Items.water_bucket && tileTotemCauldron.tank.getFluidAmount() == 0)
            {
                tileTotemCauldron.fill(ForgeDirection.getOrientation(side), new FluidStack(FluidRegistry.WATER, 1000), true);
                player.destroyCurrentEquippedItem();

                EntityItem entityBucket = new EntityItem(player.worldObj, player.posX + 0.5D, player.posY + 0.5D, player.posZ + 0.5D, new ItemStack(Items.bucket).copy());
                world.spawnEntityInWorld(entityBucket);

            }

            if(heldItem != null && heldItem.getItem() == Items.glass_bottle && tileTotemCauldron.hasPotionReady)
            {


            }

        }

        return true;
    }

}