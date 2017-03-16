package totemic_commons.pokefenn.lib;

import net.minecraft.util.ResourceLocation;
import totemic_commons.pokefenn.Totemic;

public final class Resources
{
    public static final String PREFIX_MOD = "totemic:";
    public static final String PREFIX_GUI = PREFIX_MOD + "textures/gui/";
    public static final String MODEL_SHEET_LOCATION = "textures/models/";

    //Totempedia
    public static final String GUI_CRAFTING_OVERLAY = PREFIX_GUI + "crafting_overlay.png";
    public static final String GUI_CEREMONY_OVERLAY = PREFIX_GUI + "ceremony_overlay.png";

    //GUI overlay
    public static final String CEREMONY_HUD = PREFIX_GUI + "ceremony_hud.png";

    //Models
    public static final ResourceLocation TEXTURE_BAYKOK_ARROW = new ResourceLocation(Totemic.MOD_ID, MODEL_SHEET_LOCATION + "baykok_arrow.png");
    public static final ResourceLocation TEXTURE_WIND_CHIME = new ResourceLocation(Totemic.MOD_ID, MODEL_SHEET_LOCATION + "wind_chime.png");
    public static final ResourceLocation TEXTURE_TIPI = new ResourceLocation(Totemic.MOD_ID, MODEL_SHEET_LOCATION + "tipi.png");
}
