package pokefenn.totemic.configuration;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.RequiresMcRestart;
import pokefenn.totemic.Totemic;

@Config(modid = Totemic.MOD_ID, category = "general")
@LangKey("totemic.config.general")
public final class ConfigGeneral
{
    @Comment("Set to false to prevent Skeletons from shooting Buffalos")
    @RequiresMcRestart
    public static boolean skeletonsShouldAttackBuffalos = true;
}
