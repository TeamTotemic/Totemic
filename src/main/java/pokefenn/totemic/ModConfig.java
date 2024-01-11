package pokefenn.totemic;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import pokefenn.totemic.api.TotemicAPI;

public final class ModConfig {
    /*public static class General {
        General(ModConfigSpec.Builder builder) {
            builder.comment("Totemic general configuration settings")
                    .translation("totemic.config.general")
                    .push("general");
        }
    }*/

    public static class Client {
        public final ConfigValue<Integer> ceremonyHudPositionX;
        public final ConfigValue<Integer> ceremonyHudPositionY;

        Client(ModConfigSpec.Builder builder) {
            builder.comment("Totemic client-only configuration settings")
                    .translation("totemic.config.client")
                    .push("client");

            ceremonyHudPositionX = builder
                    .comment("Horizontal position of the ceremony HUD (offset from center of the screen)")
                    .translation("totemic.config.ceremonyHudPositionX")
                    .define("ceremonyHudPositionX", 0);

            ceremonyHudPositionY = builder
                    .comment("Vertical position of the ceremony HUD (offset from center of the screen)")
                    .translation("totemic.config.ceremonyHudPositionY")
                    .define("ceremonyHudPositionY", -70);
        }
    }

    public static class Server {
        public final ConfigValue<List<? extends String>> disabledCeremonies;

        Server(ModConfigSpec.Builder builder) {
            builder.comment("Totemic server configuration settings. These settings are world specific and are synced from the server to clients.")
                    .translation("totemic.config.server")
                    .push("server");

            disabledCeremonies = builder
                    .comment("List of Ceremonies that should be disabled. Note that disabling some of the Ceremonies will prevent progression in Totemic.")
                    .comment("Example: [\"totemic:rain\", \"totemic:drought\"]")
                    .translation("totemic.config.disabledCeremonies")
                    .defineListAllowEmpty(List.of("disabledCeremonies"), List::of, isValidRegistryKey(() -> TotemicAPI.get().registry().ceremonies()));
        }
    }

    /**
     * Returns a Predicate that checks whether the given object is a valid String defining a valid ResourceLocation
     * contained in the given registry,
     *
     * Using a Supplier for the registry since our registries are not initialized before the config spec is built.
     */
    private static Predicate<Object> isValidRegistryKey(Supplier<Registry<?>> registry) {
        return obj -> {
            if(!(obj instanceof String str))
                return false;
            var key = ResourceLocation.tryParse(str);
            if(key == null)
                return false;
            return registry.get().containsKey(key);
        };
    }

    //public static final General GENERAL;
    public static final Client CLIENT;
    public static final Server SERVER;

    //private static final ModConfigSpec generalSpec;
    private static final ModConfigSpec clientSpec;
    private static final ModConfigSpec serverSpec;

    static {
        /*var generalPair = new ModConfigSpec.Builder().configure(General::new);
        GENERAL = generalPair.getLeft();
        generalSpec = generalPair.getRight();*/

        var clientPair = new ModConfigSpec.Builder().configure(Client::new);
        CLIENT = clientPair.getLeft();
        clientSpec = clientPair.getRight();

        var serverPair = new ModConfigSpec.Builder().configure(Server::new);
        SERVER = serverPair.getLeft();
        serverSpec = serverPair.getRight();
    }

    public static void register(ModLoadingContext context) {
        //context.registerConfig(Type.COMMON, generalSpec);
        context.registerConfig(Type.CLIENT, clientSpec);
        context.registerConfig(Type.SERVER, serverSpec);
    }
}
