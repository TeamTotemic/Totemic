package pokefenn.totemic;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;

public final class ModConfig {
    public static class General {
        public final BooleanValue skeletonsShouldAttackBuffalos;

        General(ForgeConfigSpec.Builder builder) {
            builder.comment("Totemic general configuration settings")
                    .translation("totemic.config.general")
                    .push("general");

            skeletonsShouldAttackBuffalos = builder
                    .comment("Set to false to prevent Skeletons from shooting Buffalos")
                    .translation("totemic.config.skeletonsShouldAttackBuffalos")
                    .worldRestart()
                    .define("skeletonsShouldAttackBuffalos", true);
        }
    }

    public static class Client {
        public final ConfigValue<Integer> ceremonyHudPositionX;
        public final ConfigValue<Integer> ceremonyHudPositionY;

        Client(ForgeConfigSpec.Builder builder) {
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

    public static final General GENERAL;
    public static final Client CLIENT;

    private static final ForgeConfigSpec generalSpec;
    private static final ForgeConfigSpec clientSpec;

    static {
        var generalPair = new ForgeConfigSpec.Builder().configure(General::new);
        GENERAL = generalPair.getLeft();
        generalSpec = generalPair.getRight();

        var clientPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT = clientPair.getLeft();
        clientSpec = clientPair.getRight();
    }

    public static void register(ModLoadingContext context) {
        context.registerConfig(Type.COMMON, generalSpec);
        context.registerConfig(Type.CLIENT, clientSpec);
    }
}
