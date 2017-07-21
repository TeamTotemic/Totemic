package pokefenn.totemic;

import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry.AddCallback;
import net.minecraftforge.registries.RegistryBuilder;
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
    public static Ceremony zaphkielWaltz;
    public static Ceremony warDance;
    public static Ceremony buffaloDance;
    public static Ceremony baykokSummon;

    @SubscribeEvent
    public static void instruments(RegistryEvent.Register<MusicInstrument> event)
    {
        event.getRegistry().registerAll(
            flute = new MusicInstrument("totemic:flute", 5, 70, 5).setItem(new ItemStack(ModItems.flute)).setRegistryName("flute"),
            drum = new MusicInstrument("totemic:drum", 7, 80, 5).setItem(new ItemStack(ModBlocks.drum)).setRegistryName("drum"),
            windChime = new MusicInstrument("totemic:windChime", 6, 60, 5).setItem(new ItemStack(ModBlocks.wind_chime)).setRegistryName("wind_chime"),
            jingleDress = new MusicInstrument("totemic:jingleDress", 7, 100, 5).setItem(new ItemStack(ModItems.jingle_dress)).setRegistryName("jingle_dress"),
            rattle = new MusicInstrument("totemic:rattle", 6, 90, 5).setItem(new ItemStack(ModItems.rattle)).setRegistryName("rattle"));

        ModItems.flute.setInstrument(flute);
        ModItems.rattle.setInstrument(rattle);
    }

    @SubscribeEvent
    public static void totemEffects(RegistryEvent.Register<TotemEffect> event)
    {
        event.getRegistry().registerAll(
            new TotemEffectPotion("camelCaseTest", MobEffects.MINING_FATIGUE).setRegistryName("camel_case_test"),
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
        //150: Flute + Drum only
        //210: Flute + Drum + full Wind Chime
        //240: Flute + Drum + Rattle
        //340: Flute + Drum + Rattle + Jingle Dress
        //400: Flute + Drum + Rattle + Jingle Dress + full Wind Chime
        event.getRegistry().registerAll(
            fluteCeremony = new CeremonyFluteInfusion("totemic:flute", 140, Ceremony.MEDIUM,
                    flute, flute).setRegistryName("flute"),
            rainDance = new CeremonyRain(true, "totemic:rainDance", 180, Ceremony.MEDIUM,
                    rattle, flute).setRegistryName("rain_dance"),
            drought = new CeremonyRain(false, "totemic:drought", 180, Ceremony.MEDIUM,
                    flute, rattle).setRegistryName("drought"),
            /*ghostDance = new CeremonyGhostDance("totemic:ghostDance", 340, CeremonyTime.SHORT_MEDIUM,
                    rattle, rattle).setRegistryName("ghost_dance"),*/
            zaphkielWaltz = new CeremonyZaphkielWaltz("totemic:zaphkielWaltz", 220, Ceremony.LONG,
                    flute, drum).setRegistryName("zaphkiel_waltz"),
            warDance = new CeremonyWarDance("totemic:warDance", 120, Ceremony.SHORT_MEDIUM,
                    drum, drum).setRegistryName("war_dance"),
            buffaloDance = new CeremonyBuffaloDance("totemic:buffaloDance", 150, Ceremony.SHORT_MEDIUM,
                    drum, windChime).setRegistryName("buffalo_dance"),
            baykokSummon = new CeremonyBaykok("totemic:baykokSummon", 255,  40 * 20,
                    windChime, flute).setRegistryName("baykok_summon"));
    }

    @SubscribeEvent
    public static void createRegistries(RegistryEvent.NewRegistry event)
    {
        //RegistryEvents are fired in alphabetic order.
        //Instruments have to be registered before Ceremonies.
        new RegistryBuilder<MusicInstrument>().setName(new ResourceLocation(Totemic.MOD_ID, "a_music_instruments")).setType(MusicInstrument.class).setMaxID(Byte.MAX_VALUE).disableSaving().create();
        new RegistryBuilder<TotemEffect>().setName(new ResourceLocation(Totemic.MOD_ID, "b_totem_effects")).setType(TotemEffect.class).setMaxID(Byte.MAX_VALUE).disableSaving().create();
        new RegistryBuilder<Ceremony>().setName(new ResourceLocation(Totemic.MOD_ID, "c_ceremonies")).setType(Ceremony.class).setMaxID(Byte.MAX_VALUE).disableSaving().add(
                (AddCallback<Ceremony>)(owner, stage, id, obj, oldObj) -> {
                    //TODO: Implement checking for prefixing selectors
                }).create();
    }
}
