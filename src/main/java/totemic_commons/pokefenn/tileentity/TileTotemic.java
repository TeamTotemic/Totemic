package totemic_commons.pokefenn.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import totemic_commons.pokefenn.lib.Strings;

public class TileTotemic extends TileEntity
{
    protected EnumFacing orientation;
    protected String customName;

    public TileTotemic()
    {
        customName = "";
        orientation = EnumFacing.SOUTH;
    }

    public void setOrientation(EnumFacing orientation)
    {
        this.orientation = orientation;
    }

    public void setOrientation(int orientation)
    {
        this.orientation = EnumFacing.getFront(orientation);
    }

    public boolean hasCustomName()
    {
        return customName != null && customName.length() > 0;
    }

    public String getCustomName()
    {
        return customName;
    }

    public void setCustomName(String customName)
    {
        this.customName = customName;
    }

    public void markForUpdate()
    {
        IBlockState state = worldObj.getBlockState(pos);
        worldObj.notifyBlockUpdate(pos, state, state, 3);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        if(nbtTagCompound.hasKey(Strings.NBT_TE_DIRECTION_KEY))
        {
            orientation = EnumFacing.getFront(nbtTagCompound.getByte(Strings.NBT_TE_DIRECTION_KEY));
        }

        if(nbtTagCompound.hasKey(Strings.NBT_TE_CUSTOM_NAME))
        {
            customName = nbtTagCompound.getString(Strings.NBT_TE_CUSTOM_NAME);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setByte(Strings.NBT_TE_DIRECTION_KEY, (byte) orientation.ordinal());

        if(this.hasCustomName())
        {
            nbtTagCompound.setString(Strings.NBT_TE_CUSTOM_NAME, customName);
        }
    }
}
