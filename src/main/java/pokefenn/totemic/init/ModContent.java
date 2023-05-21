package pokefenn.totemic.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.TotemicBlockTags;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.registry.TotemicRegisterEvent;
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
    public static final MusicInstrument flute = new MusicInstrument(resloc("flute"), 180, 3000);
    public static final MusicInstrument drum = new MusicInstrument(resloc("drum"), 240, 3300);
    public static final MusicInstrument wind_chime = new MusicInstrument(resloc("wind_chime"), 120, 1500);
    public static final MusicInstrument jingle_dress = new MusicInstrument(resloc("jingle_dress"), 180, 1500);
    public static final MusicInstrument rattle = new MusicInstrument(resloc("rattle"), 300, 3300);
    public static final MusicInstrument eagle_bone_whistle = new MusicInstrument(resloc("eagle_bone_whistle"), 360, 3600);
//    public static final MusicInstrument nether_pipe = new MusicInstrument(resloc("nether_pipe"), 240, 3900);

    public static final TotemWoodType oak = new TotemWoodType(resloc("oak"), MaterialColor.WOOD, MaterialColor.PODZOL, BlockTags.OAK_LOGS);
    public static final TotemWoodType spruce = new TotemWoodType(resloc("spruce"), MaterialColor.PODZOL, MaterialColor.COLOR_BROWN, BlockTags.SPRUCE_LOGS);
    public static final TotemWoodType birch = new TotemWoodType(resloc("birch"), MaterialColor.SAND, MaterialColor.QUARTZ, BlockTags.BIRCH_LOGS);
    public static final TotemWoodType jungle = new TotemWoodType(resloc("jungle"), MaterialColor.DIRT, MaterialColor.PODZOL, BlockTags.JUNGLE_LOGS);
    public static final TotemWoodType acacia = new TotemWoodType(resloc("acacia"), MaterialColor.COLOR_ORANGE, MaterialColor.STONE, BlockTags.ACACIA_LOGS);
    public static final TotemWoodType dark_oak = new TotemWoodType(resloc("dark_oak"), MaterialColor.COLOR_BROWN, MaterialColor.COLOR_BROWN, BlockTags.DARK_OAK_LOGS);
    public static final TotemWoodType mangrove = new TotemWoodType(resloc("mangrove"), MaterialColor.COLOR_RED, MaterialColor.PODZOL, BlockTags.MANGROVE_LOGS);
    public static final TotemWoodType cedar = new TotemWoodType(resloc("cedar"), MaterialColor.COLOR_PINK, MaterialColor.COLOR_ORANGE, TotemicBlockTags.CEDAR_LOGS);

    public static final TotemCarving none = new PortableTotemCarving(resloc("none"));
    public static final TotemCarving bat = new PortableTotemCarving(resloc("bat"), new PotionTotemEffect(() -> MobEffects.SLOW_FALLING));
    public static final TotemCarving blaze = new PortableTotemCarving(resloc("blaze"), new PotionTotemEffect(() -> MobEffects.FIRE_RESISTANCE));
    public static final TotemCarving buffalo = new PortableTotemCarving(resloc("buffalo"), new PotionTotemEffect(() -> MobEffects.DIG_SPEED));
    public static final TotemCarving cow = new PortableTotemCarving(resloc("cow"),
            new PotionTotemEffect(() -> MobEffects.DAMAGE_RESISTANCE),
            new PotionTotemEffect(() -> MobEffects.MOVEMENT_SLOWDOWN, false));
    public static final TotemCarving enderman = new PortableTotemCarving(resloc("enderman"),
            new PotionTotemEffect(() -> MobEffects.NIGHT_VISION, false) {
                @Override
                protected int getLingeringTime() { return 210; }
            });
    public static final TotemCarving horse = new PortableTotemCarving(resloc("horse"), new PotionTotemEffect(() -> MobEffects.MOVEMENT_SPEED));
    public static final TotemCarving ocelot = new TotemCarving(resloc("ocelot"),
            new OcelotTotemEffect(),
            new PotionTotemEffect(ModMobEffects.ocelot, false));
    public static final TotemCarving pig = new PortableTotemCarving(resloc("pig"), new PotionTotemEffect(() -> MobEffects.LUCK));
    public static final TotemCarving rabbit = new PortableTotemCarving(resloc("rabbit"), new PotionTotemEffect(() -> MobEffects.JUMP));
    public static final TotemCarving spider = new PortableTotemCarving(resloc("spider"), new PotionTotemEffect(ModMobEffects.spider));
    public static final TotemCarving squid = new PortableTotemCarving(resloc("squid"), new PotionTotemEffect(() -> MobEffects.WATER_BREATHING));
    public static final TotemCarving wolf = new PortableTotemCarving(resloc("wolf"), new PotionTotemEffect(() -> MobEffects.DAMAGE_BOOST));

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
    public static final Ceremony war_dance = new Ceremony(resloc("war_dance"), 4500, 20 * 20, () -> WarDanceCeremony.INSTANCE, drum, drum);
    public static final Ceremony depths = new Ceremony(resloc("depths"), 4500, 20 * 20, () -> DepthsCeremony.INSTANCE, flute, flute);
    public static final Ceremony fertility = new Ceremony(resloc("fertility"), 5280, 23 * 20, () -> FertilityCeremony.INSTANCE, flute, drum);
    public static final Ceremony zaphkiel_waltz = new Ceremony(resloc("zaphkiel_waltz"), 6720, 20 * 20, () -> ZaphkielWaltzCeremony.INSTANCE, wind_chime, flute);
    public static final Ceremony animal_growth = new Ceremony(resloc("animal_growth"), 6900, 21 * 20, () -> AnimalGrowthCeremony.INSTANCE, flute, wind_chime);
    public static final Ceremony buffalo_dance = new Ceremony(resloc("buffalo_dance"), 7380, 24 * 20, () -> BuffaloDanceCeremony.INSTANCE, drum, wind_chime);
    public static final Ceremony rain = new Ceremony(resloc("rain"), 10980, 26 * 20, () -> WeatherCeremony.RAIN, drum, rattle);
    public static final Ceremony drought = new Ceremony(resloc("drought"), 10980, 26 * 20, () -> WeatherCeremony.DROUGHT, rattle, drum);
    public static final Ceremony flute_infusion = new Ceremony(resloc("flute_infusion"), 11340, 28 * 20, () -> FluteInfusionCeremony.INSTANCE, flute, rattle);
    public static final Ceremony eagle_dance = new Ceremony(resloc("eagle_dance"), 11580, 25 * 20, () -> EagleDanceCeremony.INSTANCE, rattle, wind_chime);
    public static final Ceremony cleansing = new Ceremony(resloc("cleansing"), 14700, 30 * 20, () -> CleansingCeremony.INSTANCE, eagle_bone_whistle, flute);
    public static final Ceremony sun_dance = new Ceremony(resloc("sun_dance"), 14820, 31 * 20, () -> SunDanceCeremony.INSTANCE, drum, eagle_bone_whistle);
    public static final Ceremony danse_macabre = new Ceremony(resloc("danse_macabre"), 14940, 32 * 20, () -> DanseMacabreCeremony.INSTANCE, eagle_bone_whistle, wind_chime);
    public static final Ceremony baykok_summon = new Ceremony(resloc("baykok_summon"), 15060, 32 * 20, () -> BaykokSummonCeremony.INSTANCE, wind_chime, eagle_bone_whistle);

    @SubscribeEvent
    public static void instruments(TotemicRegisterEvent<MusicInstrument> event) {
        event.registerAll(
                flute.setItem(ModItems.flute.get()).setSound(ModSounds.flute),
                drum.setItem(ModBlocks.drum.get()).setSound(ModSounds.drum),
                wind_chime.setItem(ModBlocks.wind_chime.get()).setSound(ModSounds.wind_chime),
                jingle_dress.setItem(ModItems.jingle_dress.get()),
                rattle.setItem(ModItems.rattle.get()).setSound(ModSounds.rattle),
                eagle_bone_whistle.setItem(ModItems.eagle_bone_whistle.get()).setSound(ModSounds.eagle_bone_whistle));
    }

    @SubscribeEvent
    public static void woodTypes(TotemicRegisterEvent<TotemWoodType> event) {
        event.registerAll(oak, spruce, birch, jungle, acacia, dark_oak, mangrove, cedar);
    }

    @SubscribeEvent
    public static void totemCarvings(TotemicRegisterEvent<TotemCarving> event) {
        event.registerAll(none, bat, blaze, buffalo, cow, enderman, horse, ocelot, pig, rabbit, spider, squid, wolf);
    }

    @SubscribeEvent
    public static void ceremonies(TotemicRegisterEvent<Ceremony> event) {
        event.registerAll(war_dance, depths, fertility, zaphkiel_waltz, animal_growth, buffalo_dance, rain, drought,
                flute_infusion, eagle_dance, cleansing, sun_dance, danse_macabre, baykok_summon);
    }

    private static ResourceLocation resloc(String path) {
        return new ResourceLocation(TotemicAPI.MOD_ID, path);
    }
}
