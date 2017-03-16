package totemic_commons.pokefenn;

import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.api.TotemicRegistry;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.api.totem.TotemEffect;
import totemic_commons.pokefenn.api.totem.TotemEffectPotion;
import totemic_commons.pokefenn.ceremony.*;
import totemic_commons.pokefenn.totem.TotemEffectBlaze;
import totemic_commons.pokefenn.totem.TotemEffectCow;
import totemic_commons.pokefenn.totem.TotemEffectOcelot;

public final class ModContent
{
    public static TotemEffect batTotem;
    public static TotemEffect blazeTotem;
    public static TotemEffect buffaloTotem;
    public static TotemEffect cowTotem;
    public static TotemEffect endermanTotem;
    public static TotemEffect horseTotem;
    public static TotemEffect ocelotTotem;
    public static TotemEffect pigTotem;
    public static TotemEffect rabbitTotem;
    public static TotemEffect spiderTotem;
    public static TotemEffect squidTotem;
    public static TotemEffect wolfTotem;

    public static MusicInstrument flute;
    public static MusicInstrument drum;
    public static MusicInstrument windChime;
    public static MusicInstrument jingleDress;
    public static MusicInstrument rattle;

    public static Ceremony ghostDance;
    public static Ceremony rainDance;
    public static Ceremony drought;
    public static Ceremony fluteCeremony;
    public static Ceremony zaphkielWaltz;
    public static Ceremony warDance;
    public static Ceremony buffaloDance;
    public static Ceremony baykokSummon;

    public static void init()
    {
        totemEffects();
        instruments();
        instrumentItems();
        ceremonies();
    }

    private static void totemEffects()
    {
        TotemicRegistry reg = Totemic.api.registry();

        batTotem = reg.addTotem(new TotemEffectPotion("totemic:bat", true, 9, ModPotions.batPotion, 10, 0));
        blazeTotem = reg.addTotem(new TotemEffectBlaze("totemic:blaze"));
        buffaloTotem = reg.addTotem(new TotemEffectPotion("totemic:buffalo", MobEffects.HASTE));
        cowTotem = reg.addTotem(new TotemEffectCow("totemic:cow"));
        endermanTotem = reg.addTotem(
                new TotemEffectPotion("totemic:enderman", MobEffects.NIGHT_VISION)
                {
                    @Override
                    protected int getLingeringTime() { return 210; }
                });
        horseTotem = reg.addTotem(new TotemEffectPotion("totemic:horse", MobEffects.SPEED));
        ocelotTotem = reg.addTotem(new TotemEffectOcelot("totemic:ocelot"));
        pigTotem = reg.addTotem(new TotemEffectPotion("totemic:pig", MobEffects.LUCK));
        rabbitTotem = reg.addTotem(new TotemEffectPotion("totemic:rabbit", MobEffects.JUMP_BOOST));
        spiderTotem = reg.addTotem(new TotemEffectPotion("totemic:spider", ModPotions.spiderPotion));
        squidTotem = reg.addTotem(new TotemEffectPotion("totemic:squid", MobEffects.WATER_BREATHING));
        wolfTotem = reg.addTotem(new TotemEffectPotion("totemic:wolf", MobEffects.STRENGTH));
    }

    private static void instruments()
    {
        TotemicRegistry reg = Totemic.api.registry();

        flute = reg.addInstrument(new MusicInstrument("totemic:flute", 5, 70, 5).setItem(new ItemStack(ModItems.flute)));
        drum = reg.addInstrument(new MusicInstrument("totemic:drum", 7, 80, 5).setItem(new ItemStack(ModBlocks.drum)));
        windChime = reg.addInstrument(new MusicInstrument("totemic:windChime", 7, 60, 5).setItem(new ItemStack(ModBlocks.wind_chime)));
        jingleDress = reg.addInstrument(new MusicInstrument("totemic:jingleDress", 6, 100, 5).setItem(new ItemStack(ModItems.jingle_dress)));
        rattle = reg.addInstrument(new MusicInstrument("totemic:rattle", 6, 90, 5).setItem(new ItemStack(ModItems.rattle)));
    }

    private static void instrumentItems()
    {
        ModItems.flute.setInstrument(flute);
        ModItems.rattle.setInstrument(rattle);
    }

    private static void ceremonies()
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
                windChime, flute));
    }

}
