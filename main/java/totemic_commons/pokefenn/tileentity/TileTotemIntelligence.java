package totemic_commons.pokefenn.tileentity;

import net.minecraft.block.Block;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 29/01/14
 * Time: 20:22
 */
public class TileTotemIntelligence extends TileTotemic
{

    public void onUpdate()
    {

        if (!this.worldObj.isRemote)
        {

        }

    }

    public boolean canUpdate()
    {
        return true;
    }

    protected boolean scanAbove()
    {
        for (int i = this.yCoord; i <= 5; i++)
        {
            Block blockQuery = Block.blocksList[this.worldObj.getBlockId(this.xCoord, i, this.zCoord)];

            return blockQuery != null;

        }

        return false;
    }

    public int amountTotemAbove()
    {

        return 0;
    }

}
