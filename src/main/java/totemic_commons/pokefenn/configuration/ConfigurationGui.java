package totemic_commons.pokefenn.configuration;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import totemic_commons.pokefenn.Totemic;

public class ConfigurationGui extends GuiConfig
{

    public ConfigurationGui(GuiScreen parent)
    {
        super(parent, getConfigElements(), Totemic.MOD_ID, false, false, "Totemic configuration");
    }

    @SuppressWarnings("rawtypes")
    private static List<IConfigElement> getConfigElements()
    {
        List<IConfigElement> list = new ArrayList<>();

        for(String cat: ConfigurationHandler.conf.getCategoryNames())
            list.add(new ConfigElement<>(ConfigurationHandler.conf.getCategory(cat)));

        return list;
    }
}
