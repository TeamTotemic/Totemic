package totemic_commons.pokefenn.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.api.TotemicAPI;
import totemic_commons.pokefenn.api.ceremony.CeremonyTimeEnum;
import totemic_commons.pokefenn.api.ceremony.TimeStateEnum;
import totemic_commons.pokefenn.api.music.MusicEnum;
import totemic_commons.pokefenn.api.recipe.CeremonyActivation;
import totemic_commons.pokefenn.api.recipe.CeremonyEffect;
import totemic_commons.pokefenn.api.recipe.CeremonyRegistry;
import totemic_commons.pokefenn.api.recipe.TotemRegistry;
import totemic_commons.pokefenn.ceremony.*;
import totemic_commons.pokefenn.item.ItemTotems;
import totemic_commons.pokefenn.totem.*;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class HandlerInitiation
{
    public static CeremonyRegistry ghostDance;
    public static CeremonyRegistry elixer;
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
    }

    public static void ceremonyHandler()
    {
        elixer = TotemicAPI.addCeremony(StatCollector.translateToLocal("totemic.ceremony.potion"), 1, new CeremonyEffect(new CeremonyPotion(), new MusicEnum[]{MusicEnum.DRUM, MusicEnum.FLUTE, MusicEnum.DRUM, MusicEnum.FLUTE}), new CeremonyActivation(TimeStateEnum.INSTANT, 120, CeremonyTimeEnum.MEDIUM));
        flute = TotemicAPI.addCeremony(StatCollector.translateToLocal("totemic.ceremony.flute"), 2, new CeremonyEffect(new CeremonyFluteInfusion(), new MusicEnum[]{MusicEnum.FLUTE, MusicEnum.FLUTE, MusicEnum.FLUTE, MusicEnum.FLUTE}), new CeremonyActivation(TimeStateEnum.INSTANT, 140, CeremonyTimeEnum.MEDIUM));
        rainDance = TotemicAPI.addCeremony(StatCollector.translateToLocal("totemic.ceremony.rainDance"), 3, new CeremonyEffect(new CeremonyRain(), new MusicEnum[]{MusicEnum.RATTLE, MusicEnum.RATTLE, MusicEnum.FLUTE, MusicEnum.FLUTE}), new CeremonyActivation(TimeStateEnum.INSTANT, 130, CeremonyTimeEnum.MEDIUM));
        drought = TotemicAPI.addCeremony(StatCollector.translateToLocal("totemic.ceremony.drought"), 4, new CeremonyEffect(new CeremonyRainRemoval(), new MusicEnum[]{MusicEnum.FLUTE, MusicEnum.FLUTE, MusicEnum.RATTLE, MusicEnum.RATTLE}), new CeremonyActivation(TimeStateEnum.INSTANT, 130, CeremonyTimeEnum.MEDIUM));
        ghostDance = TotemicAPI.addCeremony(StatCollector.translateToLocal("totemic.ceremony.ghostDance"), 5, new CeremonyEffect(new CeremonyGhostDance(), new MusicEnum[]{MusicEnum.RATTLE, MusicEnum.RATTLE, MusicEnum.RATTLE, MusicEnum.RATTLE}), new CeremonyActivation(TimeStateEnum.INSTANT, 160, CeremonyTimeEnum.SHORT_MEDIUM));
        flowingTime = TotemicAPI.addCeremony("totemic.ceremony.flowingTime", 6, new CeremonyEffect(new CeremonyFlowingTime(), new MusicEnum[]{MusicEnum.FLUTE, MusicEnum.DRUM, MusicEnum.DRUM, MusicEnum.FLUTE}), new CeremonyActivation(TimeStateEnum.OVER_TIME, CeremonyTimeEnum.SHORT_MEDIUM, 110, CeremonyTimeEnum.MEDIUM, 6));
        warDance = TotemicAPI.addCeremony("totemic.ceremony.warDance", 7, new CeremonyEffect(new CeremonyWarDance(), new MusicEnum[]{MusicEnum.DRUM, MusicEnum.DRUM, MusicEnum.DRUM, MusicEnum.DRUM}), new CeremonyActivation(TimeStateEnum.INSTANT, 120, CeremonyTimeEnum.MEDIUM));
        buffaloDance = TotemicAPI.addCeremony("totemic.ceremony.buffaloDance", 8, new CeremonyEffect(new CeremonyBuffaloDance(), new MusicEnum[]{MusicEnum.JINGLE_DRESS, MusicEnum.FLUTE, MusicEnum.DRUM, MusicEnum.WIND_CHIME}), new CeremonyActivation(TimeStateEnum.INSTANT, 150, CeremonyTimeEnum.MEDIUM));
    }

    public static void totemRegistry()
    {
        horseTotem = TotemicAPI.addTotem(0, new ItemStack(ModItems.totems, 1, ItemTotems.horse), 4, 4, new TotemEffectHorse(), 1, ItemTotems.TOTEM_NAMES[ItemTotems.horse]);
        squidTotem = TotemicAPI.addTotem(1, new ItemStack(ModItems.totems, 1, ItemTotems.squid), 4, 4, new TotemEffectSquid(), 1, ItemTotems.TOTEM_NAMES[ItemTotems.squid]);
        blazeTotem = TotemicAPI.addTotem(2, new ItemStack(ModItems.totems, 1, ItemTotems.blaze), 4, 4, new TotemEffectBlaze(), 2, ItemTotems.TOTEM_NAMES[ItemTotems.blaze]);
        ocelotTotem = TotemicAPI.addTotem(3, new ItemStack(ModItems.totems, 1, ItemTotems.ocelot), 4, 4, new TotemEffectOcelot(), 2, ItemTotems.TOTEM_NAMES[ItemTotems.ocelot]);
        batTotem = TotemicAPI.addTotem(4, new ItemStack(ModItems.totems, 1, ItemTotems.bat), 4, 8, new TotemEffectBat(), 2, ItemTotems.TOTEM_NAMES[ItemTotems.bat]);
        spiderTotem = TotemicAPI.addTotem(5, new ItemStack(ModItems.totems, 1, ItemTotems.spider), 4, 4, new TotemEffectSpider(), 2, ItemTotems.TOTEM_NAMES[ItemTotems.spider]);
        cowTotem = TotemicAPI.addTotem(6, new ItemStack(ModItems.totems, 1, ItemTotems.cow), 4, 4, new TotemEffectCow(), 1, ItemTotems.TOTEM_NAMES[ItemTotems.cow]);
    }

}
