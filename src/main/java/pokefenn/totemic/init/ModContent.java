package pokefenn.totemic.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.registry.TotemicRegisterEvent;
import pokefenn.totemic.api.totem.PotionTotemEffect;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.ceremony.CeremonyWarDance;
import pokefenn.totemic.totem.EmptyTotemEffect;
import pokefenn.totemic.totem.OcelotTotemEffect;

public final class ModContent {
    public static final MusicInstrument flute = new MusicInstrument(resloc("flute"), 180, 3000);
    public static final MusicInstrument drum = new MusicInstrument(resloc("drum"), 240, 3300);
//    public static final MusicInstrument windChime = new MusicInstrument(resloc("wind_chime"), 120, 1500);
//    public static final MusicInstrument jingleDress = new MusicInstrument(resloc("jingle_dress"), 180, 1500);
//    public static final MusicInstrument rattle = new MusicInstrument(resloc("rattle"), 300, 3300);
//    public static final MusicInstrument eagleBoneWhistle = new MusicInstrument(resloc("eagle_bone_whistle"), 360, 3600);
//    public static final MusicInstrument netherPipe = new MusicInstrument(resloc("nether_pipe"), 240, 3900);

    public static final TotemEffect none = new EmptyTotemEffect(resloc("none"));
    public static final TotemEffect bat = new PotionTotemEffect(resloc("bat"), () -> MobEffects.SLOW_FALLING);
    public static final TotemEffect blaze = new PotionTotemEffect(resloc("blaze"), () -> MobEffects.FIRE_RESISTANCE);
    public static final TotemEffect buffalo = new PotionTotemEffect(resloc("buffalo"), () -> MobEffects.DIG_SPEED);
    public static final TotemEffect cow = new PotionTotemEffect(resloc("cow"), () -> MobEffects.DAMAGE_RESISTANCE) {
        @Override
        protected void applyTo(boolean isMedicineBag, Player player, int time, int amplifier) {
            super.applyTo(isMedicineBag, player, time, amplifier);
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, time, 0, true, false));
        }
    };
    public static final TotemEffect enderman = new PotionTotemEffect(resloc("enderman"), () -> MobEffects.NIGHT_VISION) {
        @Override
        protected int getLingeringTime() {
            return 210;
        }
    };
    public static final TotemEffect horse = new PotionTotemEffect(resloc("horse"), () -> MobEffects.MOVEMENT_SPEED);
    public static final TotemEffect ocelot = new OcelotTotemEffect(resloc("ocelot"));
    public static final TotemEffect pig = new PotionTotemEffect(resloc("pig"), () -> MobEffects.LUCK);
    public static final TotemEffect rabbit = new PotionTotemEffect(resloc("rabbit"), () -> MobEffects.JUMP);
    public static final TotemEffect spider = new PotionTotemEffect(resloc("spider"), ModEffects.spider);
    public static final TotemEffect squid = new PotionTotemEffect(resloc("squid"), () -> MobEffects.WATER_BREATHING);
    public static final TotemEffect wolf = new PotionTotemEffect(resloc("wolf"), () -> MobEffects.DAMAGE_BOOST);

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
    public static final Ceremony warDance = new Ceremony(resloc("war_dance"), 4500, 20 * 20, CeremonyWarDance::new, drum, drum);

    @SubscribeEvent
    public static void instruments(TotemicRegisterEvent<MusicInstrument> event) {
        event.registerAll(
                flute.setItem(ModItems.flute.get()).setSound(ModSounds.flute),
                drum.setItem(ModBlocks.drum.get()).setSound(ModSounds.drum));
    }

    @SubscribeEvent
    public static void totemEffects(TotemicRegisterEvent<TotemEffect> event) {
        event.registerAll(none, bat, blaze, buffalo, cow, enderman, horse, ocelot, pig, rabbit, spider, squid, wolf);
    }

    @SubscribeEvent
    public static void cermeonies(TotemicRegisterEvent<Ceremony> event) {
        event.registerAll(warDance);
    }

    private static ResourceLocation resloc(String path) {
        return new ResourceLocation(TotemicAPI.MOD_ID, path);
    }
}
