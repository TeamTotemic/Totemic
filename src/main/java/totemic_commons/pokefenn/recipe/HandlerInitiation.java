package totemic_commons.pokefenn.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.TotemicRegistry;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.api.totem.TotemEffect;
import totemic_commons.pokefenn.api.totem.TotemEffectPotion;
import totemic_commons.pokefenn.ceremony.*;
import totemic_commons.pokefenn.potion.ModPotions;
import totemic_commons.pokefenn.totem.TotemEffectBlaze;
import totemic_commons.pokefenn.totem.TotemEffectCow;
import totemic_commons.pokefenn.totem.TotemEffectOcelot;

public class HandlerInitiation
{
    public static Ceremony ghostDance;
    public static Ceremony rainDance;
    public static Ceremony drought;
    public static Ceremony fluteCeremony;
    public static Ceremony zaphkielWaltz;
    public static Ceremony warDance;
    public static Ceremony buffaloDance;
    public static Ceremony baykokSummon;

    public static TotemEffect horseTotem;
    public static TotemEffect squidTotem;
    public static TotemEffect blazeTotem;
    public static TotemEffect ocelotTotem;
    public static TotemEffect batTotem;
    public static TotemEffect spiderTotem;
    public static TotemEffect cowTotem;
    public static TotemEffect rabbitTotem;
    public static TotemEffect wolfTotem;

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
        TotemicRegistry reg = Totemic.api.registry();
        //Music amount landmarks:
        //150: Flute + Drum only
        //210: Flute + Drum + full Wind Chime
        //240: Flute + Drum + Rattle
        //340: Flute + Drum + Rattle + Jingle Dress
        //400: Flute + Drum + Rattle + Jingle Dress + full Wind Chime

        fluteCeremony = reg.addCeremony(new CeremonyFluteInfusion("totemic:flute", 140, Ceremony.MEDIUM,
                flute, flute));
        rainDance = reg.addCeremony(new CeremonyRain(true, "totemic:rainDance", 180, Ceremony.MEDIUM,
                rattle, flute));
        drought = reg.addCeremony(new CeremonyRain(false, "totemic:drought", 180, Ceremony.MEDIUM,
                flute, rattle));
        /*ghostDance = reg.addCeremony(new CeremonyGhostDance("totemic:ghostDance", 340, CeremonyTime.SHORT_MEDIUM,
                rattle, rattle));*/
        zaphkielWaltz = reg.addCeremony(new CeremonyZaphkielWaltz("totemic:zaphkielWaltz", 220, Ceremony.LONG,
                flute, drum));
        warDance = reg.addCeremony(new CeremonyWarDance("totemic:warDance", 120, Ceremony.SHORT_MEDIUM,
                drum, drum));
        buffaloDance = reg.addCeremony(new CeremonyBuffaloDance("totemic:buffaloDance", 150, Ceremony.SHORT_MEDIUM,
                drum, windChime));
        baykokSummon = reg.addCeremony(new CeremonyBaykok("totemic:baykokSummon", 255,  40 * 20,
                windChime, flute)); //Subject to change?
    }

    private static void totemRegistry()
    {
        TotemicRegistry reg = Totemic.api.registry();

        horseTotem = reg.addTotem(new TotemEffectPotion("totemic:horse", 6, 6, ModPotions.horsePotion, 80, 60, 0));
        squidTotem = reg.addTotem(new TotemEffectPotion("totemic:squid", 6, 6, Potion.waterBreathing, 80, 60, 0));
        blazeTotem = reg.addTotem(new TotemEffectBlaze("totemic:blaze", 6, 6));
        ocelotTotem = reg.addTotem(new TotemEffectOcelot("totemic:ocelot", 6, 6));
        batTotem = reg.addTotem(new TotemEffectPotion("totemic:bat", 9, 9, ModPotions.batPotion, 10, 20, 0));
        spiderTotem = reg.addTotem(new TotemEffectPotion("totemic:spider", 6, 6, ModPotions.spiderPotion, 60, 50, 0));
        cowTotem = reg.addTotem(new TotemEffectCow("totemic:cow", 6, 6));
        rabbitTotem = reg.addTotem(new TotemEffectPotion("totemic:rabbit", 6, 6, Potion.jump, 80, 60, 0));
        wolfTotem = reg.addTotem(new TotemEffectPotion("totemic:wolf", 6, 6, Potion.damageBoost, 80, 60, 0));
    }

    private static void instruments()
    {
        TotemicRegistry reg = Totemic.api.registry();

    	flute = reg.addInstrument(new MusicInstrument("totemic:flute", 5, 70, 5));
    	drum = reg.addInstrument(new MusicInstrument("totemic:drum", 7, 80, 5));
    	windChime = reg.addInstrument(new MusicInstrument("totemic:windChime", 7, 60, 5));
    	jingleDress = reg.addInstrument(new MusicInstrument("totemic:jingleDress", 6, 100, 5));
    	rattle = reg.addInstrument(new MusicInstrument("totemic:rattle", 6, 90, 5));
    }

}
