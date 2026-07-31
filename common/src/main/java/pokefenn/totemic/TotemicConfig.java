package pokefenn.totemic;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.InMemoryFormat;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;
import pokefenn.totemic.api.TotemicAPI;

// This will be made platform-indpendent by using Forge Config API Port
public final class TotemicConfig {
    public static class Startup {
        public final ConfigValue<List<? extends Config>> customTotemWoodTypes;

        Startup(ModConfigSpec.Builder builder) {
            //TODO: This should really be handled by a data pack registry instead. Need to figure out how to load the textures on server load though.

            //The default value will be a list containing an empty table, rather than an empty list, to make the TOML syntax for lists of tables clearer to users.
            //The default TOML file will then contain "[[customTotemWoodTypes]]" rather than "customTotemWoodTypes = []".
            var emptyConfig = Config.wrap(Map.of(), InMemoryFormat.defaultInstance());
            customTotemWoodTypes = builder
                    .comment("""
                            This config option allows you to add custom wood types for Totem Bases and Totem Poles.
                            Must be a list of tables with the following keys:
                              id: Required: The wood type's ID. Must be a valid resource location (Example: "my_mod:my_wood_type").
                              logs: Required: Specifies which log blocks are recognized as belonging to this wood type by the Totem Whittling Knife.
                                    Currently, only block tags are supported. Must be a string starting with '#' specifying a valid block tag
                                    (Example: "#minecraft:crimson_stems").
                              woodColor: Optional: An integer between 0 and 61 specifying the map color of the wood's inside.
                                         See https://minecraft.wiki/w/Map_item_format#Color_table for a list of possible colors. Defaults to 13
                                         (Oak Log's wood color).
                              barkColor: Optional: An integer between 0 and 61 specifying the map color of the wood's bark, see above. Defaults
                                         to 34 (Oak Log's bark color).

                            Textures and language keys for the corresponding Totem Pole/Base blocks need to be added via a resource pack.
                            See here for an example resource pack: https://github.com/TeamTotemic/Totemic/tree/1.20-neo/CustomWoodTypeTestResources

                            Please note that issues will arise if the wood types are not the same on the server and client.

                            Example entry:
                            [[customTotemWoodTypes]]
                                id = "my_mod:crimson"
                                logs = "#minecraft:crimson_stems"
                                woodColor = 53  #optional
                                barkColor = 54  #optional""")
                    .translation("totemic.config.customTotemWoodTypes")
                    .gameRestart()
                    .defineList("customTotemWoodTypes", () -> List.of(emptyConfig), null, o -> o instanceof Config);
        }
    }

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
        public final ConfigValue<List<? extends String>> disabledTotemCarvings;
        public final ConfigValue<List<? extends String>> medicineBagBlacklist;
        public final DoubleValue ceremonyStartupTimeMultiplierEasy;
        public final DoubleValue ceremonyStartupTimeMultiplierNormal;
        public final DoubleValue ceremonyStartupTimeMultiplierHard;

        Server(ModConfigSpec.Builder builder) {
            builder.comment("Totemic server configuration settings. These settings are world specific and are synced from the server to clients.")
                    .translation("totemic.config.server")
                    .push("server");

            disabledCeremonies = builder
                    .comment("List of Ceremonies that should be disabled. Note that disabling some of the Ceremonies will prevent progression in Totemic.")
                    .comment("Example: [\"totemic:rain\", \"totemic:drought\"]")
                    .comment("See the Totempedia with advanced tooltips enabled (F3+H) to look up the Ceremonies' IDs.")
                    .translation("totemic.config.disabledCeremonies")
                    .defineListAllowEmpty("disabledCeremonies", List::of, () -> "totemic:", isValidRegistryKey(TotemicAPI.get().registry().ceremonies()));

            disabledTotemCarvings = builder
                    .comment("List of Totem Carvings that should be disabled from being carved.")
                    .comment("Example: [\"totemic:spider\"]")
                    .comment("Use advanced tooltips (F3+H) to look up the Totem Carvings' IDs.")
                    .translation("totemic.config.disabledTotemCarvings")
                    .defineListAllowEmpty("disabledTotemCarvings", List::of, () -> "totemic:", isValidRegistryKey(TotemicAPI.get().registry().totemCarvings()));

            medicineBagBlacklist = builder
                    .comment("List of Totem Carvings that may not be used in Medicine Bags.")
                    .comment("Example: [\"totemic:spider\"]")
                    .translation("totemic.config.medicineBagBlacklist")
                    .defineListAllowEmpty("medicineBagBlacklist", List::of, () -> "totemic:", isValidRegistryKey(TotemicAPI.get().registry().totemCarvings()));

            ceremonyStartupTimeMultiplierEasy = builder
                    .comment("The Ceremony startup time is multiplied by this value in Peaceful and Easy difficulty.")
                    .comment("Higher values make Ceremonies easier to perform.")
                    .translation("totemic.config.ceremonyStartupTimeMultiplierEasy")
                    .defineInRange("ceremonyStartupTimeMultiplierEasy", 1.1, 0.1, Double.POSITIVE_INFINITY);

            ceremonyStartupTimeMultiplierNormal = builder
                    .comment("The Ceremony startup time is multiplied by this value in Normal difficulty.")
                    .translation("totemic.config.ceremonyStartupTimeMultiplierNormal")
                    .defineInRange("ceremonyStartupTimeMultiplierNormal", 1.0, 0.1, Double.POSITIVE_INFINITY);

            ceremonyStartupTimeMultiplierHard = builder
                    .comment("The Ceremony startup time is multiplied by this value in Hard difficulty.")
                    .translation("totemic.config.ceremonyStartupTimeMultiplierHard")
                    .defineInRange("ceremonyStartupTimeMultiplierHard", 0.875, 0.1, Double.POSITIVE_INFINITY);
        }
    }

    /**
     * Returns a Predicate that checks whether the given object is a valid String defining a valid ResourceLocation
     * contained in the given registry,
     */
    private static Predicate<Object> isValidRegistryKey(Registry<?> registry) {
        Objects.requireNonNull(registry);
        return obj -> {
            if(!(obj instanceof String str))
                return false;
            var key = ResourceLocation.tryParse(str);
            if(key == null)
                return false;
            return registry.containsKey(key);
        };
    }

    public static final Startup STARTUP;
    public static final Client CLIENT;
    public static final Server SERVER;

    public static final ModConfigSpec startupSpec;
    public static final ModConfigSpec clientSpec;
    public static final ModConfigSpec serverSpec;

    static {
        var startupPair = new ModConfigSpec.Builder().configure(Startup::new);
        STARTUP = startupPair.getLeft();
        startupSpec = startupPair.getRight();

        var clientPair = new ModConfigSpec.Builder().configure(Client::new);
        CLIENT = clientPair.getLeft();
        clientSpec = clientPair.getRight();

        var serverPair = new ModConfigSpec.Builder().configure(Server::new);
        SERVER = serverPair.getLeft();
        serverSpec = serverPair.getRight();
    }
}
