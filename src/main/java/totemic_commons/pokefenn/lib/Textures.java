package totemic_commons.pokefenn.lib;

import net.minecraft.util.ResourceLocation;
import totemic_commons.pokefenn.util.ResourceLocationHelper;

public class Textures
{

    public static final String MODEL_SHEET_LOCATION = "textures/models/";
    public static final String ARMOR_SHEET_LOCATION = "textures/armor/";
    public static final String GUI_SHEET_LOCATION = "textures/gui/";
    public static final String EFFECTS_LOCATION = "textures/effects/";
    public static final String BLOCK_LOCATION = "textures/blocks";

    public static final String TEXTURE_LOCATION = "totemic";

    //Blocks
    public static final String TOTEM_TABLE_SIDE = "totemTableSide";
    public static final String TOTEM_TABLE_BOTTOM = "totemTableBottom";
    public static final String TOTEM_TABLE_TOP = "totemTableTop";

    public static final String MANA_TOTEM = "manaTotem";

    public static final String TOTEM_LEAVES_OPAQUE = "cedarLeavesOpaque";
    public static final String TOTEM_LEAVES_TRANSPARENT = "cedarLeavesTransparent";

    public static final String INFUSED_WOOD_TOP_AND_BOT = "cedarWoodTopAndBot";
    public static final String INFUSED_WOOD_SIDE = "cedarWoodSide";

    public static final String INFUSED_SAPLING = "infusedSapling";

    //Items
    public static final String BUCKET_CHLOROPHYLL_ICON = "bucketChlorophyll";

    //Fluids
    public static final String FLUID_CHLOROPHYLL_STILL = "chlorophyllStill";
    public static final String FLUID_CHLOROPHYLL_FLOWING = "chlorophyllFlowing";

    //Guis
    public static final ResourceLocation TOTEMPEDIA_PAGE = ResourceLocationHelper.getResourceLocation(GUI_SHEET_LOCATION + "totempedia");

    //Models
    public static final ResourceLocation TEXTURE_TOTEM_SOCKET = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "totemSocket.png");
    public static final ResourceLocation TEXTURE_TOTEMIC_STAFF = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "totemicStaff.png");
    public static final ResourceLocation TEXTURE_INFUSED_TOTEMIC_STAFF = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "infusedTotemicStaff.png");
    public static final ResourceLocation TEXTURE_CHLOROPHYLL_CRYSTAL = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "chlorophyllCrystal.png");
    public static final ResourceLocation TEXTURE_TOTEM_TORCH = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "totemTorch.png");
    public static final ResourceLocation TEXTURE_TOTEM_SOCKET_CUBE = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "totemSocketCube.png");
    public static final ResourceLocation TEXTURE_DRUM = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "drum.png");
    public static final ResourceLocation TEXTURE_TOTEM_BASE = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "totemBase.png");

}
