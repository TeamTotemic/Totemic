package pokefenn.totemic.configuration;

import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.RequiresMcRestart;

public class ConfigClient
{
    @Comment("Horizontal position of the ceremony HUD (offset from center of the screen)")
    public int ceremonyHudPositionX = 0;

    @Comment("Vertical position of the ceremony HUD (offset from center of the screen)")
    public int ceremonyHudPositionY = -70;

    @Comment("Removes the screen flashing when the Night Vision/Enderman effect is about to end. Disable this if you experience problems.\n"
            + "Has no effect when the \"No Night Vision Flashing\" mod is installed.")
    @RequiresMcRestart
    public boolean removeNightVisionFlashing = true;
}
