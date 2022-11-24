package pokefenn.totemic.configuration;

import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.RequiresMcRestart;

public class ConfigGeneral
{
    @Comment("Set to false to prevent Skeletons from shooting Buffalos")
    @RequiresMcRestart
    public boolean skeletonsShouldAttackBuffalos = true;

    @Comment("Enables Medicine Men (Totemic Villagers)\nWarning: Disabling this will make all spawned Medicine Men DISAPPEAR FROM THE WORLD.")
    @RequiresMcRestart
    public boolean enableMedicineMen = true;

    @Comment("Enables the generation of Tipis in villages")
    @RequiresMcRestart
    public boolean enableVillageTipi = true;

    @Comment("Enables the generation of Medicine Wheels in villages")
    @RequiresMcRestart
    public boolean enableVillageMedicineWheel = true;

    @Comment("Causes the villagers in a village to be spawned with random professions (independent of the world seed).\n"
            + "Use this option with care as it might have unintended side effects (e.g. spawning nitwits which cannot make any trades).\n"
            + "Works around a bug in Forge/Minecraft where almost all the villagers spawned in a world have the same profession.")
    @RequiresMcRestart
    public boolean randomizeVillagerProfessions = false;
}
