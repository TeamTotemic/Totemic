package pokefenn.totemic.configuration;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import pokefenn.totemic.Totemic;

@Config(modid = Totemic.MOD_ID, category = "client")
public final class ConfigClient
{
    @Comment("Horizontal position of the ceremony HUD (offset from center of the screen)")
    public static int ceremonyHudPositionX = 0;

    @Comment("Vertical position of the ceremony HUD (offset from center of the screen)")
    public static int ceremonyHudPositionY = -70;

    @Comment("Enables an occasional \"special interaction\" with Botania")
    public static boolean enableFloweyEasteregg = true;
}
