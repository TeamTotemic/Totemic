package pokefenn.totemic.init;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import pokefenn.totemic.api.TotemicBlockTags;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.totem.PortableTotemCarving;
import pokefenn.totemic.api.totem.PotionTotemEffect;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemWoodType;
import pokefenn.totemic.ceremony.AnimalGrowthCeremony;
import pokefenn.totemic.ceremony.BaykokSummonCeremony;
import pokefenn.totemic.ceremony.BuffaloDanceCeremony;
import pokefenn.totemic.ceremony.CleansingCeremony;
import pokefenn.totemic.ceremony.DanseMacabreCeremony;
import pokefenn.totemic.ceremony.DepthsCeremony;
import pokefenn.totemic.ceremony.EagleDanceCeremony;
import pokefenn.totemic.ceremony.FertilityCeremony;
import pokefenn.totemic.ceremony.FluteInfusionCeremony;
import pokefenn.totemic.ceremony.SunDanceCeremony;
import pokefenn.totemic.ceremony.WarDanceCeremony;
import pokefenn.totemic.ceremony.WeatherCeremony;
import pokefenn.totemic.ceremony.ZaphkielWaltzCeremony;
import pokefenn.totemic.totem.OcelotTotemEffect;

public final class ModContent {
    public static final MusicInstrument flute = new MusicInstrument(180, 3000);
    public static final MusicInstrument drum = new MusicInstrument(240, 3300);
    public static final MusicInstrument wind_chime = new MusicInstrument(120, 1500);
    public static final MusicInstrument jingle_dress = new MusicInstrument(180, 1500);
    public static final MusicInstrument rattle = new MusicInstrument(300, 3300);
    public static final MusicInstrument eagle_bone_whistle = new MusicInstrument(360, 3600);
//    public static final MusicInstrument nether_pipe = new MusicInstrument(240, 3900);

    public static final TotemWoodType oak = new TotemWoodType(MaterialColor.WOOD, MaterialColor.PODZOL, BlockTags.OAK_LOGS);
    public static final TotemWoodType spruce = new TotemWoodType(MaterialColor.PODZOL, MaterialColor.COLOR_BROWN, BlockTags.SPRUCE_LOGS);
    public static final TotemWoodType birch = new TotemWoodType(MaterialColor.SAND, MaterialColor.QUARTZ, BlockTags.BIRCH_LOGS);
    public static final TotemWoodType jungle = new TotemWoodType(MaterialColor.DIRT, MaterialColor.PODZOL, BlockTags.JUNGLE_LOGS);
    public static final TotemWoodType acacia = new TotemWoodType(MaterialColor.COLOR_ORANGE, MaterialColor.STONE, BlockTags.ACACIA_LOGS);
    public static final TotemWoodType dark_oak = new TotemWoodType(MaterialColor.COLOR_BROWN, MaterialColor.COLOR_BROWN, BlockTags.DARK_OAK_LOGS);
    public static final TotemWoodType cedar = new TotemWoodType(MaterialColor.COLOR_PINK, MaterialColor.COLOR_ORANGE, TotemicBlockTags.CEDAR_LOGS);

    public static final TotemCarving none = new PortableTotemCarving();
    public static final TotemCarving bat = new PortableTotemCarving(new PotionTotemEffect(() -> MobEffects.SLOW_FALLING));
    public static final TotemCarving blaze = new PortableTotemCarving(new PotionTotemEffect(() -> MobEffects.FIRE_RESISTANCE));
    public static final TotemCarving buffalo = new PortableTotemCarving(new PotionTotemEffect(() -> MobEffects.DIG_SPEED));
    public static final TotemCarving cow = new PortableTotemCarving(
            new PotionTotemEffect(() -> MobEffects.DAMAGE_RESISTANCE),
            new PotionTotemEffect(() -> MobEffects.MOVEMENT_SLOWDOWN, false));
    public static final TotemCarving enderman = new PortableTotemCarving(new PotionTotemEffect(() -> MobEffects.NIGHT_VISION, false) {
        @Override
        protected int getLingeringTime() { return 210; }
    });
    public static final TotemCarving horse = new PortableTotemCarving(new PotionTotemEffect(() -> MobEffects.MOVEMENT_SPEED));
    public static final TotemCarving ocelot = new TotemCarving(
            new OcelotTotemEffect(),
            new PotionTotemEffect(ModMobEffects.ocelot, false));
    public static final TotemCarving pig = new PortableTotemCarving(new PotionTotemEffect(() -> MobEffects.LUCK));
    public static final TotemCarving rabbit = new PortableTotemCarving(new PotionTotemEffect(() -> MobEffects.JUMP));
    public static final TotemCarving spider = new PortableTotemCarving(new PotionTotemEffect(ModMobEffects.spider));
    public static final TotemCarving squid = new PortableTotemCarving(new PotionTotemEffect(() -> MobEffects.WATER_BREATHING));
    public static final TotemCarving wolf = new PortableTotemCarving(new PotionTotemEffect(() -> MobEffects.DAMAGE_BOOST));

    //Music amount landmarks:
    //6300: Flute + Drum
    //7800: Flute + Drum + full Wind Chime
    //9300: Flute + Drum + full Wind Chime + Jingle Dress
    //9600: Flute + Drum + Rattle
    //11100: Flute + Drum + Rattle + full Wind Chime
    //12600: Flute + Drum + Rattle + full Wind Chime + Jingle Dress
    //13200: Flute + Drum + Rattle + Eagle-Bone Whistle
    //14700: Flute + Drum + Rattle + Eagle-Bone Whistle + Jingle Dress
    //16200: Flute + Drum + Rattle + Eagle-Bone Whistle + Jingle Dress + full Wind Chime
    public static final Ceremony war_dance = new Ceremony(4500, 20 * 20, () -> WarDanceCeremony.INSTANCE, drum, drum);
    public static final Ceremony depths = new Ceremony(4500, 20 * 20, () -> DepthsCeremony.INSTANCE, flute, flute);
    public static final Ceremony fertility = new Ceremony(5280, 23 * 20, () -> FertilityCeremony.INSTANCE, flute, drum);
    public static final Ceremony zaphkiel_waltz = new Ceremony(6720, 20 * 20, () -> ZaphkielWaltzCeremony.INSTANCE, wind_chime, flute);
    public static final Ceremony animal_growth = new Ceremony(6900, 21 * 20, () -> AnimalGrowthCeremony.INSTANCE, flute, wind_chime);
    public static final Ceremony buffalo_dance = new Ceremony(7380, 24 * 20, () -> BuffaloDanceCeremony.INSTANCE, drum, wind_chime);
    public static final Ceremony rain = new Ceremony(10980, 26 * 20, () -> WeatherCeremony.RAIN, drum, rattle);
    public static final Ceremony drought = new Ceremony(10980, 26 * 20, () -> WeatherCeremony.DROUGHT, rattle, drum);
    public static final Ceremony flute_infusion = new Ceremony(11340, 28 * 20, () -> FluteInfusionCeremony.INSTANCE, flute, rattle);
    public static final Ceremony eagle_dance = new Ceremony(11580, 25 * 20, () -> EagleDanceCeremony.INSTANCE, rattle, wind_chime);
    public static final Ceremony cleansing = new Ceremony(14700, 30 * 20, () -> CleansingCeremony.INSTANCE, eagle_bone_whistle, flute);
    public static final Ceremony sun_dance = new Ceremony(14820, 31 * 20, () -> SunDanceCeremony.INSTANCE, drum, eagle_bone_whistle);
    public static final Ceremony danse_macabre = new Ceremony(14940, 32 * 20, () -> DanseMacabreCeremony.INSTANCE, eagle_bone_whistle, wind_chime);
    public static final Ceremony baykok_summon = new Ceremony(15060, 32 * 20, () -> BaykokSummonCeremony.INSTANCE, wind_chime, eagle_bone_whistle);

    @SubscribeEvent
    public static void instruments(RegistryEvent.Register<MusicInstrument> event) {
        event.getRegistry().registerAll(
                flute.setItem(ModItems.flute.get()).setSound(ModSounds.flute).setRegistryName("flute"),
                drum.setItem(ModBlocks.drum.get()).setSound(ModSounds.drum).setRegistryName("drum"),
                wind_chime.setItem(ModBlocks.wind_chime.get()).setSound(ModSounds.wind_chime).setRegistryName("wind_chime"),
                jingle_dress.setItem(ModItems.jingle_dress.get()).setRegistryName("jingle_dress"),
                rattle.setItem(ModItems.rattle.get()).setSound(ModSounds.rattle).setRegistryName("rattle"),
                eagle_bone_whistle.setItem(ModItems.eagle_bone_whistle.get()).setSound(ModSounds.eagle_bone_whistle).setRegistryName("eagle_bone_whistle")
        );
    }

    @SubscribeEvent
    public static void woodTypes(RegistryEvent.Register<TotemWoodType> event) {
        event.getRegistry().registerAll(
                oak.setRegistryName("oak"),
                spruce.setRegistryName("spruce"),
                birch.setRegistryName("birch"),
                jungle.setRegistryName("jungle"),
                acacia.setRegistryName("acacia"),
                dark_oak.setRegistryName("dark_oak"),
                cedar.setRegistryName("cedar")
        );
    }

    @SubscribeEvent
    public static void carvings(RegistryEvent.Register<TotemCarving> event) {
        event.getRegistry().registerAll(
                none.setRegistryName("none"),
                bat.setRegistryName("bat"),
                blaze.setRegistryName("blaze"),
                buffalo.setRegistryName("buffalo"),
                cow.setRegistryName("cow"),
                enderman.setRegistryName("enderman"),
                horse.setRegistryName("horse"),
                ocelot.setRegistryName("ocelot"),
                pig.setRegistryName("pig"),
                rabbit.setRegistryName("rabbit"),
                spider.setRegistryName("spider"),
                squid.setRegistryName("squid"),
                wolf.setRegistryName("wolf")
        );
    }

    @SubscribeEvent
    public static void ceremonies(RegistryEvent.Register<Ceremony> event) {
        event.getRegistry().registerAll(
                war_dance.setRegistryName("war_dance"),
                depths.setRegistryName("depths"),
                fertility.setRegistryName("fertility"),
                zaphkiel_waltz.setRegistryName("zaphkiel_waltz"),
                animal_growth.setRegistryName("animal_growth"),
                buffalo_dance.setRegistryName("buffalo_dance"),
                rain.setRegistryName("rain"),
                drought.setRegistryName("drought"),
                flute_infusion.setRegistryName("flute_infusion"),
                eagle_dance.setRegistryName("eagle_dance"),
                cleansing.setRegistryName("cleansing"),
                sun_dance.setRegistryName("sun_dance"),
                danse_macabre.setRegistryName("danse_macabre"),
                baykok_summon.setRegistryName("baykok_summon")
        );
    }
}
