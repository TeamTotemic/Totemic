package totemic_commons.pokefenn.lib;

import net.minecraft.util.ResourceLocation;
import totemic_commons.pokefenn.Totemic;

public class Resources
{
    public static final String PREFIX_MOD = "totemic:";
    public static final String PREFIX_GUI = PREFIX_MOD + "textures/gui/";

    public static final String GUI_CRAFTING_OVERLAY = PREFIX_GUI + "craftingOverlay.png";

    public static final String MODEL_SHEET_LOCATION = "textures/models/";

    public static final String TEXTURE_LOCATION = "totemic";

    //GUI overlay
    public static final String CEREMONY_HUD = PREFIX_GUI + "ceremonyHUD.png";

    //Models
    public static final ResourceLocation TEXTURE_WIND_CHIME = new ResourceLocation(Totemic.MOD_ID, MODEL_SHEET_LOCATION + "windChime.png");
    public static final ResourceLocation TEXTURE_TIPI = new ResourceLocation(Totemic.MOD_ID, MODEL_SHEET_LOCATION + "tipi.png");
}
