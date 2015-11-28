package totemic_commons.pokefenn.lib;

import net.minecraft.util.ResourceLocation;
import totemic_commons.pokefenn.util.ResourceLocationHelper;

public class Resources
{

    public static final String PREFIX_MOD = "totemic:";
    public static final String PREFIX_GUI = PREFIX_MOD + "textures/gui/";

    public static final String GUI_CRAFTING_OVERLAY = PREFIX_GUI + "craftingOverlay.png";

    public static final String MODEL_SHEET_LOCATION = "textures/models/";

    public static final String TEXTURE_LOCATION = "totemic";

    //Blocks
    public static final String TOTEM_LEAVES_OPAQUE = "cedarLeavesOpaque";
    public static final String TOTEM_LEAVES_TRANSPARENT = "cedarLeavesTransparent";

    public static final String INFUSED_WOOD_TOP_AND_BOT = "cedarWoodTopAndBot";
    public static final String INFUSED_WOOD_SIDE = "cedarWoodSide";

    public static final String INFUSED_SAPLING = "infusedSapling";

    public static final String DUMMY_TIPI = "dummyTipi";

    //Models
    public static final ResourceLocation TEXTURE_TOTEM_TORCH = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "totemTorch.png");
    public static final ResourceLocation TEXTURE_TOTEM_POLE_OAK = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "totemPoleOak.png");
    public static final ResourceLocation TEXTURE_TOTEM_POLE_SPRUCE = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "totemPoleSpruce.png");
    public static final ResourceLocation TEXTURE_TOTEM_POLE_BIRCH = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "totemPoleBirch.png");
    public static final ResourceLocation TEXTURE_TOTEM_POLE_JUNGLE = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "totemPoleJungle.png");
    public static final ResourceLocation TEXTURE_TOTEM_POLE_ACACIA = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "totemPoleAcacia.png");
    public static final ResourceLocation TEXTURE_TOTEM_POLE_DARK_OAK = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "totemPoleDarkOak.png");
    public static final ResourceLocation TEXTURE_TOTEM_POLE_CEDAR = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "totemPoleCedar.png");
    public static final ResourceLocation TEXTURE_DRUM = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "drum.png");
    public static final ResourceLocation TEXTURE_TOTEM_BASE_OAK = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "totemBaseOak.png");
    public static final ResourceLocation TEXTURE_TOTEM_BASE_SPRUCE = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "totemBaseSpruce.png");
    public static final ResourceLocation TEXTURE_TOTEM_BASE_BIRCH = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "totemBaseBirch.png");
    public static final ResourceLocation TEXTURE_TOTEM_BASE_JUNGLE = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "totemBaseJungle.png");
    public static final ResourceLocation TEXTURE_TOTEM_BASE_ACACIA = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "totemBaseAcacia.png");
    public static final ResourceLocation TEXTURE_TOTEM_BASE_DARK_OAK = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "totemBaseDarkOak.png");
    public static final ResourceLocation TEXTURE_TOTEM_BASE_CEDAR = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "totemBaseCedar.png");
    public static final ResourceLocation TEXTURE_WIND_CHIME = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "windChime.png");
    public static final ResourceLocation TEXTURE_TIPI = ResourceLocationHelper.getResourceLocation(MODEL_SHEET_LOCATION + "tipi.png");

    public static ResourceLocation getTotemPole(WoodVariant v)
    {
        switch(v)
        {
        case OAK:
            return TEXTURE_TOTEM_POLE_OAK;
        case SPRUCE:
            return TEXTURE_TOTEM_POLE_SPRUCE;
        case BIRCH:
            return TEXTURE_TOTEM_POLE_BIRCH;
        case JUNGLE:
            return TEXTURE_TOTEM_POLE_JUNGLE;
        case ACACIA:
            return TEXTURE_TOTEM_POLE_ACACIA;
        case DARK_OAK:
            return TEXTURE_TOTEM_POLE_DARK_OAK;
        case CEDAR:
            return TEXTURE_TOTEM_POLE_CEDAR;
        default:
            throw new IllegalArgumentException();
        }
    }

    public static ResourceLocation getTotemPole(int i)
    {
        return getTotemPole(WoodVariant.values()[i]);
    }

    public static ResourceLocation getTotemBase(WoodVariant v)
    {
        switch(v)
        {
        case OAK:
            return TEXTURE_TOTEM_BASE_OAK;
        case SPRUCE:
            return TEXTURE_TOTEM_BASE_SPRUCE;
        case BIRCH:
            return TEXTURE_TOTEM_BASE_BIRCH;
        case JUNGLE:
            return TEXTURE_TOTEM_BASE_JUNGLE;
        case ACACIA:
            return TEXTURE_TOTEM_BASE_ACACIA;
        case DARK_OAK:
            return TEXTURE_TOTEM_BASE_DARK_OAK;
        case CEDAR:
            return TEXTURE_TOTEM_BASE_CEDAR;
        default:
            throw new IllegalArgumentException();
        }
    }

    public static ResourceLocation getTotemBase(int i)
    {
        return getTotemBase(WoodVariant.values()[i]);
    }
}
