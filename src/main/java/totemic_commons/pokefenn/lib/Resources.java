package totemic_commons.pokefenn.lib;

import net.minecraft.util.ResourceLocation;
import totemic_commons.pokefenn.util.ResourceLocationHelper;

public class Resources
{

    public static final String PREFIX_MOD = "totemic:";
    public static final String PREFIX_GUI = PREFIX_MOD + "textures/gui/";
    public static final String PREFIX_ENTRIES = PREFIX_GUI + "entries/";

    public static final String GUI_CRAFTING_OVERLAY = PREFIX_GUI + "craftingOverlay.png";

    public static final String MODEL_SHEET_LOCATION = "textures/models/";
    public static final String ARMOR_SHEET_LOCATION = "textures/armor/";
    public static final String GUI_SHEET_LOCATION = "textures/gui/";
    public static final String EFFECTS_LOCATION = "textures/effects/";
    public static final String BLOCK_LOCATION = "textures/blocks";

    public static final String TEXTURE_LOCATION = "totemic";

    //Blocks

    public static final String TOTEM_LEAVES_OPAQUE = "cedarLeavesOpaque";
    public static final String TOTEM_LEAVES_TRANSPARENT = "cedarLeavesTransparent";

    public static final String INFUSED_WOOD_TOP_AND_BOT = "cedarWoodTopAndBot";
    public static final String INFUSED_WOOD_SIDE = "cedarWoodSide";

    public static final String INFUSED_SAPLING = "infusedSapling";

    //Models
    public static final ResourceLocation TEXTURE_TOTEM_TORCH = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "totemTorch.png");
    public static final ResourceLocation TEXTURE_TOTEM_SOCKET_CUBE = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "totemSocketCube.png");
    public static final ResourceLocation TEXTURE_DRUM = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "drum.png");
    public static final ResourceLocation TEXTURE_TOTEM_BASE = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "totemBase.png");
    public static final ResourceLocation TEXTURE_WIND_CHIME = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "windChime.png");
}
