package pokefenn.totemic.init;

import java.util.List;
import java.util.function.Supplier;

import com.electronwill.nightconfig.core.Config;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.TotemicConfig;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.TotemicBlockTags;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.registry.RegistryAPI;
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
    public static final DeferredRegister<MusicInstrument> INSTRUMENTS = DeferredRegister.create(RegistryAPI.MUSIC_INSTRUMENT_REGISTRY, TotemicAPI.MOD_ID);
    public static final Supplier<MusicInstrument> flute = INSTRUMENTS.register("flute", () -> new MusicInstrument(180, 3000).setItem(ModItems.flute.get()).setSound(ModSounds.flute));
    public static final Supplier<MusicInstrument> drum = INSTRUMENTS.register("drum", () -> new MusicInstrument(240, 3300).setItem(ModBlocks.drum.get()).setSound(ModSounds.drum));
    public static final Supplier<MusicInstrument> wind_chime = INSTRUMENTS.register("wind_chime", () -> new MusicInstrument(120, 1500).setItem(ModBlocks.wind_chime.get()).setSound(ModSounds.wind_chime));
    public static final Supplier<MusicInstrument> jingle_dress = INSTRUMENTS.register("jingle_dress", () -> new MusicInstrument(180, 1500).setItem(ModItems.jingle_dress.get()));
    public static final Supplier<MusicInstrument> rattle = INSTRUMENTS.register("rattle", () -> new MusicInstrument(300, 3300).setItem(ModItems.rattle.get()).setSound(ModSounds.rattle));
    public static final Supplier<MusicInstrument> eagle_bone_whistle = INSTRUMENTS.register("eagle_bone_whistle", () -> new MusicInstrument(360, 3600).setItem(ModItems.eagle_bone_whistle.get()).setSound(ModSounds.eagle_bone_whistle));
//    public static final Supplier<MusicInstrument> nether_pipe = INSTRUMENTS.register("nether_pipe", () -> new MusicInstrument(240, 3900));

    public static final DeferredRegister<TotemWoodType> WOOD_TYPES = DeferredRegister.create(RegistryAPI.WOOD_TYPE_REGISTRY, TotemicAPI.MOD_ID);
    public static final DeferredHolder<TotemWoodType, TotemWoodType> oak = WOOD_TYPES.register("oak", () -> new TotemWoodType(MapColor.WOOD, MapColor.PODZOL, BlockTags.OAK_LOGS)); //default value
    public static final Supplier<TotemWoodType> spruce = WOOD_TYPES.register("spruce", () -> new TotemWoodType(MapColor.PODZOL, MapColor.COLOR_BROWN, BlockTags.SPRUCE_LOGS));
    public static final Supplier<TotemWoodType> birch = WOOD_TYPES.register("birch", () -> new TotemWoodType(MapColor.SAND, MapColor.QUARTZ, BlockTags.BIRCH_LOGS));
    public static final Supplier<TotemWoodType> jungle = WOOD_TYPES.register("jungle", () -> new TotemWoodType(MapColor.DIRT, MapColor.PODZOL, BlockTags.JUNGLE_LOGS));
    public static final Supplier<TotemWoodType> acacia = WOOD_TYPES.register("acacia", () -> new TotemWoodType(MapColor.COLOR_ORANGE, MapColor.STONE, BlockTags.ACACIA_LOGS));
    public static final Supplier<TotemWoodType> cherry = WOOD_TYPES.register("cherry", () -> new TotemWoodType(MapColor.TERRACOTTA_WHITE, MapColor.TERRACOTTA_GRAY, BlockTags.CHERRY_LOGS));
    public static final Supplier<TotemWoodType> dark_oak = WOOD_TYPES.register("dark_oak", () -> new TotemWoodType(MapColor.COLOR_BROWN, MapColor.COLOR_BROWN, BlockTags.DARK_OAK_LOGS));
    public static final Supplier<TotemWoodType> mangrove = WOOD_TYPES.register("mangrove", () -> new TotemWoodType(MapColor.COLOR_RED, MapColor.PODZOL, BlockTags.MANGROVE_LOGS));
    public static final Supplier<TotemWoodType> cedar = WOOD_TYPES.register("cedar", () -> new TotemWoodType(MapColor.COLOR_PINK, MapColor.COLOR_ORANGE, TotemicBlockTags.CEDAR_LOGS));

    public static final DeferredRegister<TotemCarving> CARVINGS = DeferredRegister.create(RegistryAPI.TOTEM_CARVING_REGISTRY, TotemicAPI.MOD_ID);
    public static final DeferredHolder<TotemCarving, PortableTotemCarving> none = CARVINGS.register("none", () -> new PortableTotemCarving(List.of()));  //default value
    public static final Supplier<PortableTotemCarving> bat = CARVINGS.register("bat", () -> new PortableTotemCarving(new PotionTotemEffect(MobEffects.SLOW_FALLING)));
    public static final Supplier<PortableTotemCarving> blaze = CARVINGS.register("blaze", () -> new PortableTotemCarving(new PotionTotemEffect(MobEffects.FIRE_RESISTANCE)));
    public static final Supplier<PortableTotemCarving> buffalo = CARVINGS.register("buffalo", () -> new PortableTotemCarving(new PotionTotemEffect(MobEffects.DIG_SPEED)));
    public static final Supplier<PortableTotemCarving> cow = CARVINGS.register("cow", () -> new PortableTotemCarving(
            new PotionTotemEffect(MobEffects.DAMAGE_RESISTANCE),
            new PotionTotemEffect(MobEffects.MOVEMENT_SLOWDOWN, false)));
    public static final Supplier<PortableTotemCarving> enderman = CARVINGS.register("enderman", () -> new PortableTotemCarving(new PotionTotemEffect(MobEffects.NIGHT_VISION, false) {
        @Override
        protected int getLingeringTime() { return 210; }
    }));
    public static final Supplier<PortableTotemCarving> horse = CARVINGS.register("horse", () -> new PortableTotemCarving(new PotionTotemEffect(MobEffects.MOVEMENT_SPEED)));
    public static final Supplier<TotemCarving> ocelot = CARVINGS.register("ocelot", () -> new TotemCarving(
            new OcelotTotemEffect(),
            new PotionTotemEffect(ModMobEffects.ocelot, false)));
    public static final Supplier<PortableTotemCarving> pig = CARVINGS.register("pig", () -> new PortableTotemCarving(new PotionTotemEffect(MobEffects.LUCK)));
    public static final Supplier<PortableTotemCarving> rabbit = CARVINGS.register("rabbit", () -> new PortableTotemCarving(new PotionTotemEffect(MobEffects.JUMP)));
    public static final Supplier<PortableTotemCarving> spider = CARVINGS.register("spider", () -> new PortableTotemCarving(new PotionTotemEffect(ModMobEffects.spider)));
    public static final Supplier<PortableTotemCarving> squid = CARVINGS.register("squid", () -> new PortableTotemCarving(new PotionTotemEffect(MobEffects.WATER_BREATHING)));
    public static final Supplier<PortableTotemCarving> wolf = CARVINGS.register("wolf", () -> new PortableTotemCarving(new PotionTotemEffect(MobEffects.DAMAGE_BOOST)));

    public static final DeferredRegister<Ceremony> CEREMONIES = DeferredRegister.create(RegistryAPI.CEREMONY_REGISTRY, TotemicAPI.MOD_ID);
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
    public static final Supplier<Ceremony> war_dance = CEREMONIES.register("war_dance", () -> new Ceremony(4500, 20 * 20, () -> WarDanceCeremony.INSTANCE, drum, drum));
    public static final Supplier<Ceremony> depths = CEREMONIES.register("depths", () -> new Ceremony(4500, 20 * 20, () -> DepthsCeremony.INSTANCE, flute, flute));
    public static final Supplier<Ceremony> fertility = CEREMONIES.register("fertility", () -> new Ceremony(5280, 23 * 20, () -> FertilityCeremony.INSTANCE, flute, drum));
    public static final Supplier<Ceremony> zaphkiel_waltz = CEREMONIES.register("zaphkiel_waltz", () -> new Ceremony(6720, 20 * 20, () -> ZaphkielWaltzCeremony.INSTANCE, wind_chime, flute));
    public static final Supplier<Ceremony> animal_growth = CEREMONIES.register("animal_growth", () -> new Ceremony(6900, 21 * 20, () -> AnimalGrowthCeremony.INSTANCE, flute, wind_chime));
    public static final Supplier<Ceremony> buffalo_dance = CEREMONIES.register("buffalo_dance", () -> new Ceremony(7380, 24 * 20, () -> BuffaloDanceCeremony.INSTANCE, drum, wind_chime));
    public static final Supplier<Ceremony> rain = CEREMONIES.register("rain", () -> new Ceremony(10980, 26 * 20, () -> WeatherCeremony.RAIN, drum, rattle));
    public static final Supplier<Ceremony> drought = CEREMONIES.register("drought", () -> new Ceremony(10980, 26 * 20, () -> WeatherCeremony.DROUGHT, rattle, drum));
    public static final Supplier<Ceremony> flute_infusion = CEREMONIES.register("flute_infusion", () -> new Ceremony(11340, 28 * 20, () -> FluteInfusionCeremony.INSTANCE, flute, rattle));
    public static final Supplier<Ceremony> eagle_dance = CEREMONIES.register("eagle_dance", () -> new Ceremony(11580, 25 * 20, () -> EagleDanceCeremony.INSTANCE, rattle, wind_chime));
    public static final Supplier<Ceremony> cleansing = CEREMONIES.register("cleansing", () -> new Ceremony(14700, 30 * 20, () -> CleansingCeremony.INSTANCE, eagle_bone_whistle, flute));
    public static final Supplier<Ceremony> sun_dance = CEREMONIES.register("sun_dance", () -> new Ceremony(14820, 31 * 20, () -> SunDanceCeremony.INSTANCE, drum, eagle_bone_whistle));
    public static final Supplier<Ceremony> danse_macabre = CEREMONIES.register("danse_macabre", () -> new Ceremony(14940, 32 * 20, () -> DanseMacabreCeremony.INSTANCE, eagle_bone_whistle, wind_chime));
    public static final Supplier<Ceremony> baykok_summon = CEREMONIES.register("baykok_summon", () -> new Ceremony(15060, 32 * 20, () -> BaykokSummonCeremony.INSTANCE, wind_chime, eagle_bone_whistle));

    public static void registerCustomWoodTypes(RegisterEvent event) {
        event.register(RegistryAPI.WOOD_TYPE_REGISTRY, reg -> {
            for(Config entry: TotemicConfig.COMMON.customTotemWoodTypes.get()) {
                if(entry.isEmpty())
                    continue; //ignore empty default value

                String idStr = entry.get("id");
                if(idStr == null)
                    throw new IllegalArgumentException("Invalid custom Totem Wood Type: Missing entry 'id'. Please check your 'totemic-common.toml' config file.");
                try {
                    String logsStr = entry.get("logs");
                    if(logsStr == null)
                        throw new IllegalArgumentException("Missing entry 'logs'");
                    if(!logsStr.startsWith("#"))
                        throw new IllegalArgumentException("'logs' value must be a valid block tag key starting with '#'");
                    //Note that there is no way for us to check if the tag key actually exists since tags are not loaded until server start
                    int woodColorIndex = entry.getIntOrElse("woodColor", MapColor.WOOD.id);
                    int barkColorIndex = entry.getIntOrElse("barkColor", MapColor.PODZOL.id);

                    var id = ResourceLocation.parse(idStr);
                    var logTagKey = TagKey.create(Registries.BLOCK, ResourceLocation.parse(logsStr.substring(1)));
                    var woodColor = MapColor.byId(woodColorIndex);
                    var barkColor = MapColor.byId(barkColorIndex);

                    reg.register(id, new TotemWoodType(woodColor, barkColor, logTagKey));
                    Totemic.logger.debug("Added custom Totem Wood Type with ID '" + id + "'");
                }
                catch(Exception e) {
                    throw new IllegalArgumentException("Invalid custom Totem Wood Type with ID '" + idStr + "': " + e.getLocalizedMessage() + "\nPlease check your 'totemic-common.toml' config file.", e);
                }
            }
        });
    }
}
