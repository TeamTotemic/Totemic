package totemic_commons.pokefenn.blessing;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlessingWorldData extends WorldSavedData
{
    public int blessing;
    //public String name;

    public BlessingWorldData(String par1Str)
    {
        super(par1Str);
        blessing = 0;
        //name = "";
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        blessing = nbtTagCompound.getInteger("blessing");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        nbtTagCompound.setInteger("blessing", blessing);
    }
}
