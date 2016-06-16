package totemic_commons.pokefenn.tileentity;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class TileTipi extends TileTotemic
{
    //public int colour = 0;
    //public boolean hasCatcher = false;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        tag = super.writeToNBT(tag);
        //tag.setInteger("colour", colour);
        //tag.setBoolean("hasCatcher", hasCatcher);
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        //colour = nbtTagCompound.getInteger("colour");
        //hasCatcher = nbtTagCompound.getBoolean("hasCatcher");
    }

}