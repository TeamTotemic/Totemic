package totemic_commons.pokefenn.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
        return new AxisAlignedBB(pos.add(-1, -1, -1), pos.add(2, 6, 2));
    }

}