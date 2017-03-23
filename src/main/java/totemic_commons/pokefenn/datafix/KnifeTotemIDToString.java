package totemic_commons.pokefenn.datafix;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.item.equipment.ItemTotemWhittlingKnife;

public class KnifeTotemIDToString implements IFixableData
{
    @Override
    public int getFixVersion()
    {
        return 800;
    }

    @Override
    public NBTTagCompound fixTagCompound(NBTTagCompound compound)
    {
        String id = compound.getString("id");
        if("totemic:totemWhittlingKnife".equals(id) || "totemic:totem_whittling_knife".equals(id))
        {
            List<String> totemList = Totemic.api.registry().getTotemList();
            NBTTagCompound tag = compound.getCompoundTag("tag");
            if(tag.hasKey("totem", 99))
            {
                int index = tag.getInteger("totem");
                String name;
                if(0 <= index && index < totemList.size())
                    name = totemList.get(index);
                else
                    name = ItemTotemWhittlingKnife.TOTEM_BASE_PLACEHOLDER_NAME;
                tag.setString(ItemTotemWhittlingKnife.KNIFE_TOTEM_KEY, name);
                compound.setTag("tag", tag);
            }
        }
        return compound;
    }
}
