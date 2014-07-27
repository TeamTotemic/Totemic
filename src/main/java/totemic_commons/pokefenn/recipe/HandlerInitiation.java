package totemic_commons.pokefenn.recipe;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.StatCollector;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.api.TotemicAPI;
import totemic_commons.pokefenn.api.ceremony.CeremonyTimeEnum;
import totemic_commons.pokefenn.api.ceremony.TimeStateEnum;
import totemic_commons.pokefenn.api.music.MusicEnum;
import totemic_commons.pokefenn.api.recipe.CeremonyActivation;
import totemic_commons.pokefenn.api.recipe.CeremonyEffect;
import totemic_commons.pokefenn.api.recipe.CeremonyRegistry;
import totemic_commons.pokefenn.api.recipe.TotemRegistry;
import totemic_commons.pokefenn.ceremony.*;
import totemic_commons.pokefenn.totem.*;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class HandlerInitiation
{
    public static CeremonyRegistry ghostDance;
    public static CeremonyRegistry rainDance;
    public static CeremonyRegistry drought;
    public static CeremonyRegistry flute;
    public static CeremonyRegistry flowingTime;
    public static CeremonyRegistry warDance;
    public static CeremonyRegistry buffaloDance;

    public static TotemRegistry horseTotem;
    public static TotemRegistry squidTotem;
    public static TotemRegistry blazeTotem;
    public static TotemRegistry ocelotTotem;
    public static TotemRegistry batTotem;
    public static TotemRegistry spiderTotem;
    public static TotemRegistry cowTotem;

    public static void init()
    {
        totemRegistry();
        ceremonyHandler();

        furnaceRecipes();
    }

    static void ceremonyHandler()
    {
        flute = TotemicAPI.addCeremony(StatCollector.translateToLocal("totemic.ceremony.flute"), 2, new CeremonyEffect(new CeremonyFluteInfusion(), new MusicEnum[]{MusicEnum.FLUTE, MusicEnum.FLUTE, MusicEnum.FLUTE, MusicEnum.FLUTE}), new CeremonyActivation(TimeStateEnum.INSTANT, 140, CeremonyTimeEnum.MEDIUM));
        rainDance = TotemicAPI.addCeremony(StatCollector.translateToLocal("totemic.ceremony.rainDance"), 3, new CeremonyEffect(new CeremonyRain(), new MusicEnum[]{MusicEnum.RATTLE, MusicEnum.RATTLE, MusicEnum.FLUTE, MusicEnum.FLUTE}), new CeremonyActivation(TimeStateEnum.INSTANT, 130, CeremonyTimeEnum.MEDIUM));
        drought = TotemicAPI.addCeremony(StatCollector.translateToLocal("totemic.ceremony.drought"), 4, new CeremonyEffect(new CeremonyRainRemoval(), new MusicEnum[]{MusicEnum.FLUTE, MusicEnum.FLUTE, MusicEnum.RATTLE, MusicEnum.RATTLE}), new CeremonyActivation(TimeStateEnum.INSTANT, 130, CeremonyTimeEnum.MEDIUM));
        ghostDance = TotemicAPI.addCeremony(StatCollector.translateToLocal("totemic.ceremony.ghostDance"), 5, new CeremonyEffect(new CeremonyGhostDance(), new MusicEnum[]{MusicEnum.RATTLE, MusicEnum.RATTLE, MusicEnum.RATTLE, MusicEnum.RATTLE}), new CeremonyActivation(TimeStateEnum.INSTANT, 160, CeremonyTimeEnum.SHORT_MEDIUM));
        flowingTime = TotemicAPI.addCeremony("totemic.ceremony.flowingTime", 6, new CeremonyEffect(new CeremonyFlowingTime(), new MusicEnum[]{MusicEnum.FLUTE, MusicEnum.DRUM, MusicEnum.DRUM, MusicEnum.FLUTE}), new CeremonyActivation(TimeStateEnum.OVER_TIME, CeremonyTimeEnum.SHORT_MEDIUM, 110, CeremonyTimeEnum.MEDIUM, 6));
        warDance = TotemicAPI.addCeremony("totemic.ceremony.warDance", 7, new CeremonyEffect(new CeremonyWarDance(), new MusicEnum[]{MusicEnum.DRUM, MusicEnum.DRUM, MusicEnum.DRUM, MusicEnum.DRUM}), new CeremonyActivation(TimeStateEnum.INSTANT, 120, CeremonyTimeEnum.MEDIUM));
        buffaloDance = TotemicAPI.addCeremony("totemic.ceremony.buffaloDance", 8, new CeremonyEffect(new CeremonyBuffaloDance(), new MusicEnum[]{MusicEnum.JINGLE_DRESS, MusicEnum.FLUTE, MusicEnum.DRUM, MusicEnum.WIND_CHIME}), new CeremonyActivation(TimeStateEnum.INSTANT, 150, CeremonyTimeEnum.MEDIUM));
    }

    static void totemRegistry()
    {
        horseTotem = TotemicAPI.addTotem(1, 4, 4, new TotemEffectHorse(), 1, "totemic.totem.horse");
        squidTotem = TotemicAPI.addTotem(2, 4, 4, new TotemEffectSquid(), 1, "totemic.totem.squid");
        blazeTotem = TotemicAPI.addTotem(3, 4, 4, new TotemEffectBlaze(), 2, "totemic.totem.blaze");
        ocelotTotem = TotemicAPI.addTotem(4, 4, 4, new TotemEffectOcelot(), 2, "totemic.totem.ocelot");
        batTotem = TotemicAPI.addTotem(5, 4, 8, new TotemEffectBat(), 2, "totemic.totem.bat");
        spiderTotem = TotemicAPI.addTotem(6, 4, 4, new TotemEffectSpider(), 2, "totemic.totem.spider");
        cowTotem = TotemicAPI.addTotem(7, 4, 4, new TotemEffectCow(), 1, "totemic.totem.cow");
    }

    static void furnaceRecipes()
    {
        FurnaceRecipes.smelting().func_151394_a(new ItemStack(ModBlocks.redCedarStripped), new ItemStack(Items.coal, 1, 1), 0.5F);
        FurnaceRecipes.smelting().func_151394_a(new ItemStack(ModBlocks.cedarLog), new ItemStack(Items.coal, 1, 1), 0.5F);
    }

}
