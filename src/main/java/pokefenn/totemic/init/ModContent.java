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
    public static final MusicInstrument flute = new MusicInstrument(resloc("flute"), 3, 50);
    public static final MusicInstrument drum = new MusicInstrument(resloc("drum"), 4, 55);
    /*
     * public static MusicInstrument windChime;
     * public static MusicInstrument jingleDress;
     * public static MusicInstrument rattle;
     * public static MusicInstrument eagleBoneWhistle;
     * public static MusicInstrument netherPipe;
     */

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

    public static final Ceremony warDance = new Ceremony(resloc("war_dance"), 75, 20 * 20, CeremonyWarDance::new, /*drum, drum*/flute, flute);

    @SubscribeEvent
    public static void instruments(TotemicRegisterEvent<MusicInstrument> event) {
        event.registerAll(
                flute.setItem(ModItems.flute.get()),
                drum);
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
