package totemic_commons.pokefenn.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileTotem extends TileEntity
{
    public ResourceLocation modelLocation;
    public int totemID;
    public String totemName;

    String TAG_TOTEM_ID = "totemID";

    public TileTotem(ResourceLocation location, int totemID, String totemName)
    {
        this.modelLocation = location;
        this.totemID = totemID;
        this.totemName = totemName;

    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {

        super.readFromNBT(nbtTagCompound);

        totemID = nbtTagCompound.getInteger(TAG_TOTEM_ID);

        if(nbtTagCompound.hasKey(TAG_TOTEM_ID))
            totemID = nbtTagCompound.getInteger(TAG_TOTEM_ID);

    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {

        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setInteger(TAG_TOTEM_ID, totemID);

    }

}
