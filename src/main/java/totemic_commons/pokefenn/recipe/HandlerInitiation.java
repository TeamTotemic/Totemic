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
import totemic_commons.pokefenn.ceremony.CeremonyBaykok;
import totemic_commons.pokefenn.ceremony.CeremonyBuffaloDance;
import totemic_commons.pokefenn.ceremony.CeremonyDepths;
import totemic_commons.pokefenn.ceremony.CeremonyEagleDance;
import totemic_commons.pokefenn.ceremony.CeremonyFertility;
import totemic_commons.pokefenn.ceremony.CeremonyFluteInfusion;
import totemic_commons.pokefenn.ceremony.CeremonyRain;
import totemic_commons.pokefenn.ceremony.CeremonyWarDance;
import totemic_commons.pokefenn.ceremony.CeremonyZaphkielWaltz;
import totemic_commons.pokefenn.potion.ModPotions;
import totemic_commons.pokefenn.totem.TotemEffectBlaze;
import totemic_commons.pokefenn.totem.TotemEffectCow;
import totemic_commons.pokefenn.totem.TotemEffectOcelot;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class HandlerInitiation
{
    public static Ceremony warDance;
    public static Ceremony depths;
    public static Ceremony fertility;
    public static Ceremony zaphkielWaltz;
    public static Ceremony buffaloDance;
    public static Ceremony rainDance;
    public static Ceremony drought;
    public static Ceremony fluteCeremony;
    public static Ceremony eagleDance;
    public static Ceremony baykokSummon;
    
    public static TotemEffect batTotem;
    public static TotemEffect blazeTotem;
    public static TotemEffect buffaloTotem;
    public static TotemEffect cowTotem;
    public static TotemEffect horseTotem;
    public static TotemEffect ocelotTotem;
    public static TotemEffect rabbitTotem;
    public static TotemEffect spiderTotem;
    public static TotemEffect squidTotem;
    public static TotemEffect wolfTotem;

    public static MusicInstrument flute;
    public static MusicInstrument drum;
    public static MusicInstrument windChime;
    public static MusicInstrument jingleDress;
    public static MusicInstrument rattle;
    public static MusicInstrument eagleBoneWhistle;

    public static void init()
    {
        totemRegistry();
        instruments();
        ceremonyHandler();
    }

    private static void ceremonyHandler()
    {
        TotemicRegistry reg = Totemic.api.registry();
        //Music amount landmarks:
        //105: Flute + Drum
        //130: Flute + Drum + full Wind Chime
        //155: Flute + Drum + full Wind Chime + Jingle Dress
        //160: Flute + Drum + Rattle
        //185: Flute + Drum + Rattle + full Wind Chime
        //210: Flute + Drum + Rattle + full Wind Chime + Jingle Dress
        //220: Flute + Drum + Rattle + Eagle-Bone Whistle
        //245: Flute + Drum + Rattle + Eagle-Bone Whistle + Jingle Dress
        //270: Flute + Drum + Rattle + Eagle-Bone Whistle + Jingle Dress + full Wind Chime

        warDance = reg.addCeremony(new CeremonyWarDance("totemic", "warDance", 75, 20 * 20,
                drum, drum));
        depths = reg.addCeremony(new CeremonyDepths("totemic", "depths", 75, 20 * 20,
                flute, flute));
        fertility = reg.addCeremony(new CeremonyFertility("totemic", "fertility", 88, 23 * 20,  20 * 20, 6,
                flute, drum));
        zaphkielWaltz = reg.addCeremony(new CeremonyZaphkielWaltz("totemic", "zaphkielWaltz", 112, 20 * 20,  45 * 20, 6,
                windChime, flute));
        buffaloDance = reg.addCeremony(new CeremonyBuffaloDance("totemic", "buffaloDance", 123, 24 * 20,
                drum, windChime));
        rainDance = reg.addCeremony(new CeremonyRain(true, "totemic", "rainDance", 183, 26 * 20,
                drum, rattle));
        drought = reg.addCeremony(new CeremonyRain(false, "totemic", "drought", 183, 26 * 20,
                rattle, drum));
        fluteCeremony = reg.addCeremony(new CeremonyFluteInfusion("totemic", "flute", 189, 28 * 20,
                flute, rattle));
        eagleDance = reg.addCeremony(new CeremonyEagleDance("totemic", "eagleDance", 193, 25 * 20,
                rattle, windChime));
        baykokSummon = reg.addCeremony(new CeremonyBaykok("totemic", "baykokSummon", 251, 32 * 20,
                windChime, eagleBoneWhistle));
    }

    private static void totemRegistry()
    {
        TotemicRegistry reg = Totemic.api.registry();
        
        batTotem = reg.addTotem(new TotemEffectPotion("totemic", "bat", 8, 8, 2, ModPotions.batPotion, 10, 20, 0));
        blazeTotem = reg.addTotem(new TotemEffectBlaze("totemic", "blaze", 4, 4, 2));
        buffaloTotem = reg.addTotem(new TotemEffectPotion("totemic", "buffalo", 4, 4, 1, Potion.digSpeed, 80, 60, 0));
        cowTotem = reg.addTotem(new TotemEffectCow("totemic", "cow", 4, 4, 1));
        horseTotem = reg.addTotem(new TotemEffectPotion("totemic", "horse", 4, 4, 1, Potion.moveSpeed, 80, 60, 0));
        ocelotTotem = reg.addTotem(new TotemEffectOcelot("totemic", "ocelot", 4, 4, 2));
        rabbitTotem = reg.addTotem(new TotemEffectPotion("totemic", "rabbit", 4, 4, 1, Potion.jump, 80, 60, 0));
        spiderTotem = reg.addTotem(new TotemEffectPotion("totemic", "spider", 4, 4, 2, ModPotions.spiderPotion, 60, 50, 0));
        squidTotem = reg.addTotem(new TotemEffectPotion("totemic", "squid", 4, 4, 1, Potion.waterBreathing, 80, 60, 0));
        wolfTotem = reg.addTotem(new TotemEffectPotion("totemic", "wolf", 4, 4, 1, Potion.damageBoost, 80, 60, 0));
    }

    private static void instruments()
    {
        TotemicRegistry reg = Totemic.api.registry();

    	flute = reg.addInstrument(new MusicInstrument("totemic", "flute", 3, 50, 5));
    	drum = reg.addInstrument(new MusicInstrument("totemic", "drum", 4, 55, 5));
    	windChime = reg.addInstrument(new MusicInstrument("totemic", "windChime", 2, 25, 5));
    	jingleDress = reg.addInstrument(new MusicInstrument("totemic", "jingleDress", 3, 25, 5));
    	rattle = reg.addInstrument(new MusicInstrument("totemic", "rattle", 5, 55, 5));
    	eagleBoneWhistle = reg.addInstrument(new MusicInstrument("totemic", "eagleBoneWhistle", 6, 60, 5));
    }

    public static void instrumentItems()
    {
        flute.setItem(new ItemStack(ModItems.flute));
        drum.setItem(new ItemStack(ModBlocks.drum));
        windChime.setItem(new ItemStack(ModBlocks.windChime));
        jingleDress.setItem(new ItemStack(ModItems.jingleDress));
        rattle.setItem(new ItemStack(ModItems.ceremonialRattle));
        eagleBoneWhistle.setItem(new ItemStack(ModItems.eagleBoneWhistle));
    }
}
