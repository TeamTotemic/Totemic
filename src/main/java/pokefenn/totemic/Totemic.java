package pokefenn.totemic;

import java.util.ServiceLoader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.resources.ResourceLocation;
import pokefenn.totemic.api.TotemicAPI;

public final class Totemic {
    private static final PlatformAbstractions platform = ServiceLoader.load(PlatformAbstractions.class).findFirst()
            .orElseThrow(() -> new IllegalStateException("No provider for Totemic's PlatformAbstractions found"));

    public static final Logger logger = LogManager.getLogger(Totemic.class);

    public static ResourceLocation resloc(String path) {
        return ResourceLocation.fromNamespaceAndPath(TotemicAPI.MOD_ID, path);
    }

    public static PlatformAbstractions platform() {
        return platform;
    }
}
