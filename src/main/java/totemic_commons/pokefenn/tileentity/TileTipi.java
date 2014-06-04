package totemic_commons.pokefenn.tileentity;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class TileTipi extends TileTotemic
{
    public int colour;

    public TileTipi()
    {
        colour = 0;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("colour", colour);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        colour = nbtTagCompound.getInteger("colour");
    }

}