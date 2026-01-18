package pokefenn.totemic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.resources.ResourceLocation;
import pokefenn.totemic.api.TotemicAPI;

public final class Totemic {
    public static final Logger logger = LogManager.getLogger(Totemic.class);

    public static ResourceLocation resloc(String path) {
        return ResourceLocation.fromNamespaceAndPath(TotemicAPI.MOD_ID, path);
    }
}
