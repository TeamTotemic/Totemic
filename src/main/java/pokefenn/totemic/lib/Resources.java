package pokefenn.totemic.lib;

import net.minecraft.util.ResourceLocation;
import pokefenn.totemic.Totemic;

public final class Resources
{
    public static final String PREFIX_MOD = "totemic:";
    public static final String PREFIX_GUI = PREFIX_MOD + "textures/gui/";
    public static final String MODEL_SHEET_LOCATION = "textures/models/";

    //Totempedia
    public static final String GUI_CRAFTING_OVERLAY = PREFIX_GUI + "crafting_overlay.png";
    public static final String GUI_CEREMONY_OVERLAY = PREFIX_GUI + "ceremony_overlay.png";

    //GUI overlay
    public static final String SELECTION_HUD = PREFIX_GUI + "selection_hud.png";
    public static final String CEREMONY_HUD = PREFIX_GUI + "ceremony_hud.png";

    //Loot tables
    public static final ResourceLocation LOOT_BUFFALO = new ResourceLocation(Totemic.MOD_ID, "entities/buffalo");
    public static final ResourceLocation LOOT_BAYKOK = new ResourceLocation(Totemic.MOD_ID, "entities/baykok");
    public static final ResourceLocation LOOT_BALD_EAGLE = new ResourceLocation(Totemic.MOD_ID, "entities/bald_eagle");
}
