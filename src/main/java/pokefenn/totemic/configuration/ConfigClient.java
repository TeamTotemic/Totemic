package pokefenn.totemic.configuration;

import net.minecraftforge.common.config.Config.Comment;

public class ConfigClient
{
    @Comment("Horizontal position of the ceremony HUD (offset from center of the screen)")
    public int ceremonyHudPositionX = 0;

    @Comment("Vertical position of the ceremony HUD (offset from center of the screen)")
    public int ceremonyHudPositionY = -70;

    @Comment("Enables an occasional \"special interaction\" with Botania")
    public boolean enableFloweyEasteregg = true;
}
