package pokefenn.totemic.datafix;

import static pokefenn.totemic.Totemic.logger;

import java.util.Locale;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.datafix.IFixableData;
import net.minecraftforge.common.util.Constants;
import pokefenn.totemic.util.MiscUtil;

public class CamelCaseNamesTiles implements IFixableData
{
    @Override
    public int getFixVersion()
    {
        return 1010;
    }

    public static String checkAndFixName(String name, String description)
    {
        if(!name.contains(":") || !name.equals(name.toLowerCase(Locale.ROOT)))
        {
            String oldName = name;
            name = MiscUtil.fixResourceName(oldName);
            logger.debug("Fixed {} name: '{}' -> '{}'", description, oldName, name);
        }
        return name;
    }

    private static NBTTagCompound fixCompoundKeys(NBTTagCompound tag, String description)
    {
        NBTTagCompound fixed = new NBTTagCompound();
        for(String name: tag.getKeySet())
        {
            fixed.setTag(checkAndFixName(name, description), tag.getTag(name));
        }
        return fixed;
    }

    @Override
    public NBTTagCompound fixTagCompound(NBTTagCompound compound)
    {
        switch(compound.getString("id"))
        {
        case "totemic:totem_base":
            int state = compound.getByte("state");
            switch(state)
            {
            case 1: //StateSelection
                NBTTagList selectorsTag = compound.getTagList("selectors", 8);
                for(int i = 0; i < selectorsTag.tagCount(); i++)
                {
                    String name = checkAndFixName(selectorsTag.getStringTagAt(i), "Music Instrument");
                    selectorsTag.set(i, new NBTTagString(name));
                }
                break;

            case 2: //StateStartup
                String ceremonyName = compound.getString("ceremony");
                compound.setString("ceremony", checkAndFixName(ceremonyName, "Ceremony"));

                NBTTagCompound ceremonyMusic = compound.getCompoundTag("ceremonyMusic");
                compound.setTag("ceremonyMusic", fixCompoundKeys(ceremonyMusic, "Music Instrument"));

                NBTTagCompound timesPlayed = compound.getCompoundTag("timesPlayed");
                compound.setTag("timesPlayed", fixCompoundKeys(timesPlayed, "Music Instrument"));
                break;

            case 3: //StateCeremonyEffect
                ceremonyName = compound.getString("ceremony");
                compound.setString("ceremony", checkAndFixName(ceremonyName, "Ceremony"));
                break;
            }
            break;

        case "totemic:totem_pole":
            if(compound.hasKey("effect", Constants.NBT.TAG_STRING))
            {
                String name = compound.getString("effect");
                compound.setString("effect", checkAndFixName(name, "Totem Effect"));
            }
            break;
        }
        return compound;
    }

}
