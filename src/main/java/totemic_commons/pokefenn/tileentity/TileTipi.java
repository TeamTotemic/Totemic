package totemic_commons.pokefenn.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class TileTipi extends TileTotemic
{
    public int colour;
    public boolean hasCatcher;

    public TileTipi()
    {
        colour = 0;
        hasCatcher = false;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("colour", colour);
        nbtTagCompound.setBoolean("hasCatcher", hasCatcher);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        colour = nbtTagCompound.getInteger("colour");
        hasCatcher = nbtTagCompound.getBoolean("hasCatcher");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
        return AxisAlignedBB.getBoundingBox(this.xCoord - 1, this.yCoord - 1, this.zCoord - 1, this.xCoord + 1, this.yCoord + 6, this.zCoord + 1);
    }

}