package pokefenn.totemic.configuration;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import pokefenn.totemic.Totemic;

@Config(modid = Totemic.MOD_ID, category = "")
public final class ModConfig
{
    @LangKey("totemic.config.general")
    public static final ConfigGeneral general = new ConfigGeneral();

    @LangKey("totemic.config.client")
    public static final ConfigClient client = new ConfigClient();
}
