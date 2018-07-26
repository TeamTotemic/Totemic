package pokefenn.totemic.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import pokefenn.totemic.lib.WoodVariant;

public class TileDecoPillar extends TileTotemic
{
    private WoodVariant woodType = WoodVariant.OAK;
    private boolean stripped = false;

    public WoodVariant getWoodType()
    {
        return woodType;
    }

    public void setWoodType(WoodVariant woodType)
    {
        this.woodType = woodType;
    }

    public boolean isStripped()
    {
        return stripped;
    }

    public void setStripped(boolean stripped)
    {
        this.stripped = stripped;
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        tag = super.writeToNBT(tag);
        tag.setByte("wood", (byte) woodType.getID());
        tag.setBoolean("stripped", stripped);
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        woodType = WoodVariant.fromID(tag.getByte("wood"));
        stripped = tag.getBoolean("stripped");
    }
}
