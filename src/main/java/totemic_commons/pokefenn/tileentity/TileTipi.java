package totemic_commons.pokefenn.tileentity;

import net.minecraft.nbt.NBTTagCompound;

public class TileTipi extends TileTotemic
{
    //public int colour;
    //public boolean hasCatcher;

    public TileTipi()
    {
        //colour = 0;
        //hasCatcher = false;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        //nbtTagCompound.setInteger("colour", colour);
        //nbtTagCompound.setBoolean("hasCatcher", hasCatcher);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        //colour = nbtTagCompound.getInteger("colour");
        //hasCatcher = nbtTagCompound.getBoolean("hasCatcher");
    }

    @Override
    public boolean canRenderBreaking()
    {
        return true; //This fixes the weird breaking animation caused by the oversized model
    }
}