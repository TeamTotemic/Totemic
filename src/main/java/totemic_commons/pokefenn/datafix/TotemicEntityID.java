package totemic_commons.pokefenn.datafix;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;
import totemic_commons.pokefenn.lib.Strings;

public class TotemicEntityID implements IFixableData
{
    @Override
    public int getFixVersion()
    {
        return 800;
    }

    @Override
    public NBTTagCompound fixTagCompound(NBTTagCompound tag)
    {
        switch(tag.getString("id"))
        {
        case "totemic.buffalo":
            tag.setString("id", Strings.RESOURCE_PREFIX + Strings.BUFFALO_NAME);
            break;
        case "totemic.baykok":
            tag.setString("id", Strings.RESOURCE_PREFIX + Strings.BAYKOK_NAME);
            break;
        case "totemic.invis_arrow":
            tag.setString("id", Strings.RESOURCE_PREFIX + Strings.INVIS_ARROW_NAME);
            break;
        }
        return tag;
    }

}
