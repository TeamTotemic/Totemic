package pokefenn.totemic;

import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

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

    //public static final General GENERAL;
    public static final Client CLIENT;

    //private static final ModConfigSpec generalSpec;
    private static final ModConfigSpec clientSpec;

    static {
        /*var generalPair = new ForgeConfigSpec.Builder().configure(General::new);
        GENERAL = generalPair.getLeft();
        generalSpec = generalPair.getRight();*/

        var clientPair = new ModConfigSpec.Builder().configure(Client::new);
        CLIENT = clientPair.getLeft();
        clientSpec = clientPair.getRight();
    }

    public static void register(ModLoadingContext context) {
        //context.registerConfig(Type.COMMON, generalSpec);
        context.registerConfig(Type.CLIENT, clientSpec);
    }
}
