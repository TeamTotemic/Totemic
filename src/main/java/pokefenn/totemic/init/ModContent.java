package pokefenn.totemic.init;

import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.RegistryBuilder;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.api.totem.TotemEffectPotion;
import pokefenn.totemic.ceremony.*;
import pokefenn.totemic.totem.TotemEffectBlaze;
import pokefenn.totemic.totem.TotemEffectCow;
import pokefenn.totemic.totem.TotemEffectOcelot;

@EventBusSubscriber(modid = Totemic.MOD_ID)
public final class ModContent
{
    public static MusicInstrument flute;
    public static MusicInstrument drum;
    public static MusicInstrument windChime;
    public static MusicInstrument jingleDress;
    public static MusicInstrument rattle;
    public static MusicInstrument eagleBoneWhistle;
    public static MusicInstrument netherPipe;

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

    //public static Ceremony ghostDance;
    public static Ceremony rainDance;
    public static Ceremony drought;
    public static Ceremony fluteCeremony;
    public static Ceremony fertility;
    public static Ceremony zaphkielWaltz;
    public static Ceremony warDance;
    public static Ceremony depths;
    public static Ceremony buffaloDance;
    public static Ceremony eagleDance;
    public static Ceremony cleansing;
    public static Ceremony baykokSummon;
    public static Ceremony sunDance;
    public static Ceremony danseMacabre;

    @SubscribeEvent
    public static void instruments(RegistryEvent.Register<MusicInstrument> event)
    {
        event.getRegistry().registerAll(
            flute = new MusicInstrument("totemic:flute", 3, 50).setItem(new ItemStack(ModItems.flute)).setRegistryName("flute"),
            drum = new MusicInstrument("totemic:drum", 4, 55).setItem(new ItemStack(ModBlocks.drum)).setRegistryName("drum"),
            windChime = new MusicInstrument("totemic:windChime", 2, 25).setItem(new ItemStack(ModBlocks.wind_chime)).setRegistryName("wind_chime"),
            jingleDress = new MusicInstrument("totemic:jingleDress", 3, 25).setItem(new ItemStack(ModItems.jingle_dress)).setRegistryName("jingle_dress"),
            rattle = new MusicInstrument("totemic:rattle", 5, 55).setItem(new ItemStack(ModItems.rattle)).setRegistryName("rattle"),
            eagleBoneWhistle = new MusicInstrument("totemic:eagleBoneWhistle", 6, 60).setItem(new ItemStack(ModItems.eagle_bone_whistle)).setRegistryName("eagle_bone_whistle"),
            netherPipe = new MusicInstrument("totemic:netherPipe", 4, 65).setItem(new ItemStack(ModItems.nether_pipe)).setRegistryName("nether_pipe"));
        ModItems.flute.setInstrument(flute);
        ModItems.rattle.setInstrument(rattle);
        ModItems.eagle_bone_whistle.setInstrument(eagleBoneWhistle);
        ModItems.nether_pipe.setInstrument(netherPipe);
    }

    @SubscribeEvent
    public static void totemEffects(RegistryEvent.Register<TotemEffect> event)
    {
        event.getRegistry().registerAll(
            batTotem = new TotemEffectPotion("totemic:bat", true, 9, ModPotions.batPotion, 10, 0).setRegistryName("bat"),
            blazeTotem = new TotemEffectBlaze("totemic:blaze").setRegistryName("blaze"),
            buffaloTotem = new TotemEffectPotion("totemic:buffalo", MobEffects.HASTE).setRegistryName("buffalo"),
            cowTotem = new TotemEffectCow("totemic:cow").setRegistryName("cow"),
            endermanTotem =
                    new TotemEffectPotion("totemic:enderman", MobEffects.NIGHT_VISION)
                    {
                        @Override
                        protected int getLingeringTime() { return 210; }
                    }.setRegistryName("enderman"),
            horseTotem = new TotemEffectPotion("totemic:horse", MobEffects.SPEED).setRegistryName("horse"),
            ocelotTotem = new TotemEffectOcelot("totemic:ocelot").setRegistryName("ocelot"),
            pigTotem = new TotemEffectPotion("totemic:pig", MobEffects.LUCK).setRegistryName("pig"),
            rabbitTotem = new TotemEffectPotion("totemic:rabbit", MobEffects.JUMP_BOOST).setRegistryName("rabbit"),
            spiderTotem = new TotemEffectPotion("totemic:spider", ModPotions.spiderPotion).setRegistryName("spider"),
            squidTotem = new TotemEffectPotion("totemic:squid", MobEffects.WATER_BREATHING).setRegistryName("squid"),
            wolfTotem = new TotemEffectPotion("totemic:wolf", MobEffects.STRENGTH).setRegistryName("wolf"));
    }

    @SubscribeEvent
    public static void ceremonies(RegistryEvent.Register<Ceremony> event)
    {
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
        event.getRegistry().registerAll(
            warDance = new CeremonyWarDance("totemic:warDance", 75, 20 * 20,
                    drum, drum).setRegistryName("war_dance"),
            depths = new CeremonyDepths("totemic:depths", 75, 20 * 20,
                    flute, flute).setRegistryName("depths"),
            fertility = new CeremonyFertility("totemic:fertility", 88, 23 * 20,
                    flute, drum).setRegistryName("fertility"),
            zaphkielWaltz = new CeremonyZaphkielWaltz("totemic:zaphkielWaltz", 112, 20 * 20,
                    windChime, flute).setRegistryName("zaphkiel_waltz"),
            buffaloDance = new CeremonyBuffaloDance("totemic:buffaloDance", 123, 24 * 20,
                    drum, windChime).setRegistryName("buffalo_dance"),
            rainDance = new CeremonyRain(true, "totemic:rainDance", 179, 26 * 20,
                    drum, rattle).setRegistryName("rain_dance"),
            drought = new CeremonyRain(false, "totemic:drought", 179, 26 * 20,
                    rattle, drum).setRegistryName("drought"),
            fluteCeremony = new CeremonyFluteInfusion("totemic:flute", 180, 28 * 20,
                    flute, rattle).setRegistryName("flute"),
            eagleDance = new CeremonyEagleDance("totemic:eagleDance", 185, 29 * 20,
                    rattle, windChime).setRegistryName("eagle_dance"),
            cleansing = new CeremonyCleansing("totemic:cleansing", 230, 35 * 20,
                    eagleBoneWhistle, flute).setRegistryName("cleansing"),
            baykokSummon = new CeremonyBaykok("totemic:baykokSummon", 241, 40 * 20,
                    windChime, eagleBoneWhistle).setRegistryName("baykok_summon"),
            sunDance = new CeremonySunDance("totemic:sunDance", 240, 41 * 20,
                    drum, eagleBoneWhistle).setRegistryName("sun_dance"),
            danseMacabre = new CeremonyDanseMacabre("totemic:danseMacabre", 238, 45 * 20,
                eagleBoneWhistle, windChime).setRegistryName("danseMacabre"));
    }

    @SubscribeEvent
    public static void createRegistries(RegistryEvent.NewRegistry event)
    {
        //RegistryEvents are fired in alphabetic order.
        //Instruments have to be registered before Ceremonies.
        new RegistryBuilder<MusicInstrument>().setName(new ResourceLocation(Totemic.MOD_ID, "a_music_instruments")).setType(MusicInstrument.class).setMaxID(Byte.MAX_VALUE).disableSaving().create();
        new RegistryBuilder<TotemEffect>().setName(new ResourceLocation(Totemic.MOD_ID, "b_totem_effects")).setType(TotemEffect.class).setMaxID(Byte.MAX_VALUE).disableSaving().create();
        new RegistryBuilder<Ceremony>().setName(new ResourceLocation(Totemic.MOD_ID, "c_ceremonies")).setType(Ceremony.class).setMaxID(Byte.MAX_VALUE).disableSaving().create();
    }
}
