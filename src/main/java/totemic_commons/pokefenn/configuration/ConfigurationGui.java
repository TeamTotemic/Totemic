package totemic_commons.pokefenn.configuration;

import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import totemic_commons.pokefenn.Totemic;

public class ConfigurationGui extends GuiConfig
{
    public ConfigurationGui(GuiScreen parent)
    {
        super(parent, getConfigElements(), Totemic.MOD_ID, false, false, "Totemic configuration");
    }

    private static List<IConfigElement> getConfigElements()
    {
        return ConfigurationHandler.conf.getCategoryNames().stream()
                .map(cat -> new ConfigElement(ConfigurationHandler.conf.getCategory(cat)))
                .collect(Collectors.toList());
    }
}
