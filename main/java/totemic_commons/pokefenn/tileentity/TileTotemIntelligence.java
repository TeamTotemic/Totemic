package totemic_commons.pokefenn.tileentity;

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
        super.updateEntity();

        if (!this.worldObj.isRemote)
        {

            System.out.println("Scanned");

        }

    }

    public boolean canUpdate()
    {
        return true;
    }

    protected boolean scanAbove()
    {
        return !worldObj.isAirBlock(this.xCoord, this.yCoord + 1, this.zCoord);
    }

    protected boolean scanBelow()
    {
        return !worldObj.isAirBlock(this.xCoord, this.yCoord - 1, this.zCoord);
    }

}
