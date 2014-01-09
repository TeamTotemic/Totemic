package totemic_commons.pokefenn.rendering.tileentity;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.tileentity.TileChlorophyllSolidifier;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 03/01/14
 * Time: 17:58
 */
public class TileChlorophyllSolidifierRenderer extends TileEntitySpecialRenderer {


    @Override
    public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f)
    {

        TileChlorophyllSolidifier tileChlorophyllSolidifier = (TileChlorophyllSolidifier) tileentity.worldObj.getBlockTileEntity(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);

    }


}
