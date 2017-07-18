package pokefenn.totemic.datafix;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;
import pokefenn.totemic.item.equipment.ItemMedicineBag;
import pokefenn.totemic.item.equipment.ItemTotemWhittlingKnife;

public class CamelCaseNamesItems implements IFixableData
{
    @Override
    public int getFixVersion()
    {
        return 1010;
    }

    @Override
    public NBTTagCompound fixTagCompound(NBTTagCompound compound)
    {
        NBTTagCompound itemTag = compound.getCompoundTag("tag");
        switch(compound.getString("id"))
        {
        case "totemic:medicine_bag":
            String name = itemTag.getString(ItemMedicineBag.MED_BAG_TOTEM_KEY);
            if(!name.isEmpty())
            itemTag.setString(ItemMedicineBag.MED_BAG_TOTEM_KEY, CamelCaseNamesTiles.checkAndFixName(name, "Totem Effect"));
            break;

        case "totemic:totem_whittling_knife":
            name = itemTag.getString(ItemTotemWhittlingKnife.KNIFE_TOTEM_KEY);
            if(!name.isEmpty())
                itemTag.setString(ItemTotemWhittlingKnife.KNIFE_TOTEM_KEY, CamelCaseNamesTiles.checkAndFixName(name, "Totem Effect"));
            break;
        }
        return compound;
    }

}
