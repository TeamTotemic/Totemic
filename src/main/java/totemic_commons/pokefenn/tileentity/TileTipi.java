package totemic_commons.pokefenn.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
        return new AxisAlignedBB(pos.add(-1, -1, -1), pos.add(2, 6, 2));
    }

}