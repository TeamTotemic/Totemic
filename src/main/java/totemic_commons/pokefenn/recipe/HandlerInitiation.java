package totemic_commons.pokefenn.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.TotemEffect;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.ceremony.CeremonyTime;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.ceremony.*;
import totemic_commons.pokefenn.potion.ModPotions;
import totemic_commons.pokefenn.totem.TotemEffectBlaze;
import totemic_commons.pokefenn.totem.TotemEffectCow;
import totemic_commons.pokefenn.totem.TotemEffectOcelot;
import totemic_commons.pokefenn.totem.TotemEffectPotion;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class HandlerInitiation
{
    public static Ceremony ghostDance;
    public static Ceremony rainDance;
    public static Ceremony drought;
    public static Ceremony fluteCeremony;
    public static Ceremony zaphkielWaltz;
    public static Ceremony warDance;
    public static Ceremony buffaloDance;

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
        fluteCeremony = Totemic.api.addCeremony(new CeremonyFluteInfusion("totemic", "flute", 140, CeremonyTime.MEDIUM, CeremonyTime.INSTANT, 0,
                flute, flute));
        rainDance = Totemic.api.addCeremony(new CeremonyRain(true, "totemic", "rainDance", 130, CeremonyTime.MEDIUM, CeremonyTime.INSTANT, 0,
                rattle, flute));
        drought = Totemic.api.addCeremony(new CeremonyRain(false, "totemic", "drought", 130, CeremonyTime.MEDIUM, CeremonyTime.INSTANT, 0,
                flute, rattle));
        ghostDance = Totemic.api.addCeremony(new CeremonyGhostDance("totemic", "ghostDance", 160, CeremonyTime.SHORT_MEDIUM, CeremonyTime.INSTANT, 0,
                rattle, rattle));
        zaphkielWaltz = Totemic.api.addCeremony(new CeremonyZaphkielWaltz("totemic", "zaphkielWaltz", 110, CeremonyTime.MEDIUM, CeremonyTime.SHORT_MEDIUM, 6,
                flute, drum));
        warDance = Totemic.api.addCeremony(new CeremonyWarDance("totemic", "warDance", 120, CeremonyTime.MEDIUM, CeremonyTime.INSTANT, 0,
                drum, drum));
        buffaloDance = Totemic.api.addCeremony(new CeremonyBuffaloDance("totemic", "buffaloDance", 150, CeremonyTime.MEDIUM, CeremonyTime.INSTANT, 0,
                drum, windChime));
    }

    private static void totemRegistry()
    {
        horseTotem = Totemic.api.addTotem(new TotemEffectPotion("totemic", "horse", 4, 4, 1, ModPotions.horsePotion, 80, 50, 0));
        squidTotem = Totemic.api.addTotem(new TotemEffectPotion("totemic", "squid", 4, 4, 1, Potion.waterBreathing, 80, 40, 0));
        blazeTotem = Totemic.api.addTotem(new TotemEffectBlaze("totemic", "blaze", 4, 4, 2));
        ocelotTotem = Totemic.api.addTotem(new TotemEffectOcelot("totemic", "ocelot", 4, 4, 2));
        batTotem = Totemic.api.addTotem(new TotemEffectPotion("totemic", "bat", 8, 8, 2, ModPotions.batPotion, 10, 20, 0));
        spiderTotem = Totemic.api.addTotem(new TotemEffectPotion("totemic", "spider", 4, 4, 2, ModPotions.spiderPotion, 60, 40, 0));
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
