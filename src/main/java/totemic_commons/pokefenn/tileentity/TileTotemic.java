package totemic_commons.pokefenn.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;

public class TileTotemic extends TileEntity
{
    public void markForUpdate()
    {
        IBlockState state = worldObj.getBlockState(pos);
        worldObj.notifyBlockUpdate(pos, state, state, 3);
    }
}
