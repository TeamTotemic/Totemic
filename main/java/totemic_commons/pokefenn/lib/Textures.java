package totemic_commons.pokefenn.lib;

import net.minecraft.util.ResourceLocation;
import totemic_commons.pokefenn.util.ResourceLocationHelper;

public class Textures {

    public static final String MODEL_SHEET_LOCATION = "textures/models/";
    public static final String ARMOR_SHEET_LOCATION = "textures/armor/";
    public static final String GUI_SHEET_LOCATION = "textures/gui/";
    public static final String EFFECTS_LOCATION = "textures/effects/";

    public static final String TEXTURE_LOCATION = "totemic";

    //Blocks

    public static final String BIG_BAD_TOTEM_HEAD_ICON_ALL = "bigBadTotemHead";
    public static final String BIG_BAD_TOTEM_BASE_ICON_ALL = "bigBadTotemBase";

    public static final String TOTEM_WOOD_TOP_ICON = "totemWoodTop";
    public static final String TOTEM_WOOD_SIDE_ICON = "totemWoodSide";

    //Items
    public static final String BUCKET_CHLOROPHYLL_ICON = "bucketChlorophyll";
    public static final String BOTTLE_CHLOROPHYLL_ICON = "bottleChlorophyll";

    //Fluids

    //Guis

    public static final ResourceLocation GUI_TOTEM_BASE = ResourceLocationHelper.getResourceLocation(GUI_SHEET_LOCATION + "totemBaseGui.png");

    public static final ResourceLocation GUI_PAINT_BRUSH = ResourceLocationHelper.getResourceLocation(GUI_SHEET_LOCATION + "paintBrushGui.png");


}
