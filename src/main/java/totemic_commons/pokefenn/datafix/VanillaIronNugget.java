package totemic_commons.pokefenn.datafix;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class VanillaIronNugget implements IFixableData
{
    @Override
    public int getFixVersion()
    {
        return 901;
    }

    @Override
    public NBTTagCompound fixTagCompound(NBTTagCompound compound)
    {
        String id = compound.getString("id");
        if(("totemic:sub_items".equals(id) || "totemic:subItems".equals(id)) && compound.getShort("Damage") == 0)
        {
            compound.setString("id", "minecraft:iron_nugget");
        }
        return compound;
    }

}
