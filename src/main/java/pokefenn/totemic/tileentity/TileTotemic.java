package pokefenn.totemic.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;

public class TileTotemic extends TileEntity
{
    public void markForUpdate()
    {
        IBlockState state = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, state, state, 3);
    }
}
