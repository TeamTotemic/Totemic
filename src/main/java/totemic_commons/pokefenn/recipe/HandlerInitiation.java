package totemic_commons.pokefenn.recipe;

import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.TotemEffect;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.ceremony.*;
import totemic_commons.pokefenn.legacy_api.TotemicAPI;
import totemic_commons.pokefenn.legacy_api.ceremony.*;
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
    public static CeremonyRegistry fluteCeremony;
    public static CeremonyRegistry zaphkielWaltz;
    public static CeremonyRegistry warDance;
    public static CeremonyRegistry buffaloDance;

    public static TotemEffect horseTotem;
    public static TotemEffect squidTotem;
    public static TotemEffect blazeTotem;
    public static TotemEffect ocelotTotem;
    public static TotemEffect batTotem;
    public static TotemEffect spiderTotem;
    public static TotemEffect cowTotem;

    public static MusicInstrument flute;
    public static MusicInstrument drum;
    public static MusicInstrument windChime;
    public static MusicInstrument jingleDress;
    public static MusicInstrument rattle;

    public static void init()
    {
        totemRegistry();
        instruments();
        ceremonyHandler();
    }

    public static void instrumentItems()
    {
        flute.setItem(new ItemStack(ModItems.flute));
        drum.setItem(new ItemStack(ModBlocks.drum));
        windChime.setItem(new ItemStack(ModBlocks.windChime));
        jingleDress.setItem(new ItemStack(ModItems.jingleDress));
        rattle.setItem(new ItemStack(ModItems.ceremonialRattle));
    }

    private static void ceremonyHandler()
    {
        fluteCeremony = TotemicAPI.addCeremony("totemic.ceremony.flute", 2, new CeremonyEffect(new CeremonyFluteInfusion(),
                new MusicInstrument[]{flute, flute}), new CeremonyActivation(TimeStateEnum.INSTANT, 140, CeremonyTimeEnum.MEDIUM));
        rainDance = TotemicAPI.addCeremony("totemic.ceremony.rainDance", 3, new CeremonyEffect(new CeremonyRain(),
                new MusicInstrument[]{rattle, flute}), new CeremonyActivation(TimeStateEnum.INSTANT, 130, CeremonyTimeEnum.MEDIUM));
        drought = TotemicAPI.addCeremony("totemic.ceremony.drought", 4, new CeremonyEffect(new CeremonyRainRemoval(),
                new MusicInstrument[]{flute, rattle}), new CeremonyActivation(TimeStateEnum.INSTANT, 130, CeremonyTimeEnum.MEDIUM));
        ghostDance = TotemicAPI.addCeremony("totemic.ceremony.ghostDance", 5, new CeremonyEffect(new CeremonyGhostDance(),
                new MusicInstrument[]{rattle, rattle}), new CeremonyActivation(TimeStateEnum.INSTANT, 160, CeremonyTimeEnum.SHORT_MEDIUM));
        zaphkielWaltz = TotemicAPI.addCeremony("totemic.ceremony.zaphkielWaltz", 6, new CeremonyEffect(new CeremonyZaphkielWaltz(),
                new MusicInstrument[]{flute, drum}), new CeremonyActivation(TimeStateEnum.ENDING_EFFECT, CeremonyTimeEnum.SHORT_MEDIUM, 110, CeremonyTimeEnum.MEDIUM, 6));
        warDance = TotemicAPI.addCeremony("totemic.ceremony.warDance", 7, new CeremonyEffect(new CeremonyWarDance(),
                new MusicInstrument[]{drum, drum}), new CeremonyActivation(TimeStateEnum.INSTANT, 120, CeremonyTimeEnum.MEDIUM));
        buffaloDance = TotemicAPI.addCeremony("totemic.ceremony.buffaloDance", 8, new CeremonyEffect(new CeremonyBuffaloDance(),
                new MusicInstrument[]{drum, windChime}), new CeremonyActivation(TimeStateEnum.INSTANT, 150, CeremonyTimeEnum.MEDIUM));
    }

    private static void totemRegistry()
    {
        horseTotem = Totemic.api.addTotem(new TotemEffectHorse("totemic", "horse", 4, 4, 1));
        squidTotem = Totemic.api.addTotem(new TotemEffectSquid("totemic", "squid", 4, 4, 1));
        blazeTotem = Totemic.api.addTotem(new TotemEffectBlaze("totemic", "blaze", 4, 4, 2));
        ocelotTotem = Totemic.api.addTotem(new TotemEffectOcelot("totemic", "ocelot", 4, 4, 2));
        batTotem = Totemic.api.addTotem(new TotemEffectBat("totemic", "bat", 8, 8, 2));
        spiderTotem = Totemic.api.addTotem(new TotemEffectSpider("totemic", "spider", 4, 4, 2));
        cowTotem = Totemic.api.addTotem(new TotemEffectCow("totemic", "cow", 4, 4, 1));
    }

    private static void instruments()
    {
    	flute = Totemic.api.addInstrument(new MusicInstrument("totemic", "flute", 5, 70, 5));
    	drum = Totemic.api.addInstrument(new MusicInstrument("totemic", "drum", 7, 80, 5));
    	windChime = Totemic.api.addInstrument(new MusicInstrument("totemic", "windChime", 7, 60, 5));
    	jingleDress = Totemic.api.addInstrument(new MusicInstrument("totemic", "jingleDress", 6, 100, 5));
    	rattle = Totemic.api.addInstrument(new MusicInstrument("totemic", "rattle", 6, 90, 5));
    }

}
