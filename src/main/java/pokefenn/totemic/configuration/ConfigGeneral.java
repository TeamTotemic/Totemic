package pokefenn.totemic.configuration;

import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.RequiresMcRestart;

public class ConfigGeneral
{
    @Comment("Set to false to prevent Skeletons from shooting Buffalos")
    @RequiresMcRestart
    public boolean skeletonsShouldAttackBuffalos = true;
}
