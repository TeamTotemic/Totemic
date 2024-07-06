package pokefenn.totemic;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import pokefenn.totemic.api.TotemicAPI;

public final class TotemicConfig {
    public static class Common {
        public final ConfigValue<List<? extends Config>> customTotemWoodTypes;

        Common(ForgeConfigSpec.Builder builder) {
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
                            See here for an example resource pack: https://github.com/TeamTotemic/Totemic/tree/1.20.1/CustomWoodTypeTestResources

                            Please note that issues will arise if the wood types are not the same on the server and client.

                            Example entry:
                            [[customTotemWoodTypes]]
                                id = "my_mod:crimson"
                                logs = "#minecraft:crimson_stems"
                                woodColor = 53  #optional
                                barkColor = 54  #optional""")
                    .translation("totemic.config.customTotemWoodTypes")
                    .worldRestart()
                    .defineList(List.of("customTotemWoodTypes"), () -> List.of(emptyConfig), o -> o instanceof Config);
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

        Server(ModConfigSpec.Builder builder) {
            builder.comment("Totemic server configuration settings. These settings are world specific and are synced from the server to clients.")
                    .translation("totemic.config.server")
                    .push("server");

            disabledCeremonies = builder
                    .comment("List of Ceremonies that should be disabled. Note that disabling some of the Ceremonies will prevent progression in Totemic.")
                    .comment("Example: [\"totemic:rain\", \"totemic:drought\"]")
                    .comment("See the Totempedia with advanced tooltips enabled (F3+H) to look up the Ceremonies' IDs.")
                    .translation("totemic.config.disabledCeremonies")
                    .defineListAllowEmpty(List.of("disabledCeremonies"), List::of, isValidRegistryKey(TotemicAPI.get().registry().ceremonies()));

            disabledTotemCarvings = builder
                    .comment("List of Totem Carvings that should be disabled from being carved.")
                    .comment("Example: [\"totemic:spider\"]")
                    .comment("Use advanced tooltips (F3+H) to look up the Totem Carvings' IDs.")
                    .translation("totemic.config.disabledTotemCarvings")
                    .defineListAllowEmpty(List.of("disabledTotemCarvings"), List::of, isValidRegistryKey(TotemicAPI.get().registry().totemCarvings()));
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

    public static final Common COMMON;
    public static final Client CLIENT;
    public static final Server SERVER;

    private static final ModConfigSpec commonSpec;
    private static final ModConfigSpec clientSpec;
    private static final ModConfigSpec serverSpec;

    static {
        var commonPair = new ModConfigSpec.Builder().configure(Common::new);
        COMMON = commonPair.getLeft();
        commonSpec = commonPair.getRight();

        var clientPair = new ModConfigSpec.Builder().configure(Client::new);
        CLIENT = clientPair.getLeft();
        clientSpec = clientPair.getRight();

        var serverPair = new ModConfigSpec.Builder().configure(Server::new);
        SERVER = serverPair.getLeft();
        serverSpec = serverPair.getRight();
    }

    public static void register(ModLoadingContext context) {
        context.registerConfig(ModConfig.Type.CLIENT, clientSpec);
        context.registerConfig(ModConfig.Type.SERVER, serverSpec);

        //Special case for the common config, we need to load it earlier since Forge usually loads the configs after the registry events
        var commonModConfig = new ModConfig(ModConfig.Type.COMMON, commonSpec, context.getActiveContainer());
        context.getActiveContainer().addConfig(commonModConfig);
        var configPath = FMLPaths.CONFIGDIR.get().resolve(commonModConfig.getFileName());
        var configData = CommentedFileConfig.builder(configPath)
                .sync()
                .preserveInsertionOrder()
                .autosave()
                .onFileNotFound(FileNotFoundAction.READ_NOTHING) //if the file does not exist, it will be created by Forge later
                .writingMode(WritingMode.REPLACE)
                .build();
        configData.load();
        commonSpec.acceptConfig(configData);
    }
}
