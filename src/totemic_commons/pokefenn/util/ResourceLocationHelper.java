package totemic_commons.pokefenn.util;

import net.minecraft.util.ResourceLocation;
import totemic_commons.pokefenn.lib.Reference;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 25/11/13
 * Time: 21:01
 */
public class ResourceLocationHelper {

    public static ResourceLocation getResourceLocation(String modId, String path) {

        return new ResourceLocation(modId, path);
    }

    public static ResourceLocation getResourceLocation(String path) {

        return getResourceLocation(Reference.MOD_NAME.toLowerCase(), path);
    }

}
