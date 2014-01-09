package totemic_commons.pokefenn.rendering.tileentity;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.tileentity.TileTotemBase;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 03/01/14
 * Time: 17:58
 */
public class TileTotemBaseRenderer extends TileEntitySpecialRenderer {

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f)
    {

        TileTotemBase tileTotemBase = (TileTotemBase) tileentity.worldObj.getBlockTileEntity(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);

        if (ItemStack.areItemStacksEqual(tileTotemBase.getStackInSlot(tileTotemBase.SLOT_ONE), new ItemStack(ModItems.totems)))
        {

        } else if (ItemStack.areItemStacksEqual(tileTotemBase.getStackInSlot(tileTotemBase.SLOT_ONE), new ItemStack(ModItems.totems, 1, 1)))
        {


        } else if (ItemStack.areItemStacksEqual(tileTotemBase.getStackInSlot(tileTotemBase.SLOT_ONE), new ItemStack(ModItems.totems, 1, 2)))
        {


        } else if (ItemStack.areItemStacksEqual(tileTotemBase.getStackInSlot(tileTotemBase.SLOT_ONE), new ItemStack(ModItems.totems, 1, 3)))
        {

            //this.setTileEntityRenderer(this.);


        }

    }

}
