package pokefenn.totemic.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.registry.TotemicRegisterEvent;
import pokefenn.totemic.api.totem.PotionTotemEffect;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.totem.EmptyTotemEffect;
import pokefenn.totemic.totem.OcelotTotemEffect;

public final class ModContent {
    public static final MusicInstrument flute = new MusicInstrument(new ResourceLocation("totemic", "flute"), 3, 50);
    /*
     * public static MusicInstrument drum;
     * public static MusicInstrument windChime;
     * public static MusicInstrument jingleDress;
     * public static MusicInstrument rattle;
     * public static MusicInstrument eagleBoneWhistle;
     * public static MusicInstrument netherPipe;
     */

    public static final TotemEffect none = new EmptyTotemEffect(new ResourceLocation("totemic", "none"));
    public static final TotemEffect bat = new PotionTotemEffect(new ResourceLocation("totemic", "bat"), () -> MobEffects.SLOW_FALLING);
    public static final TotemEffect blaze = new PotionTotemEffect(new ResourceLocation("totemic", "blaze"), () -> MobEffects.FIRE_RESISTANCE);
    public static final TotemEffect buffalo = new PotionTotemEffect(new ResourceLocation("totemic", "buffalo"), () -> MobEffects.DIG_SPEED);
    public static final TotemEffect cow = new PotionTotemEffect(new ResourceLocation("totemic", "cow"), () -> MobEffects.DAMAGE_RESISTANCE) {
        @Override
        protected void applyTo(boolean isMedicineBag, Player player, int time, int amplifier) {
            super.applyTo(isMedicineBag, player, time, amplifier);
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, time, 0, true, false));
        }
    };
    public static final TotemEffect enderman = new PotionTotemEffect(new ResourceLocation("totemic", "enderman"), () -> MobEffects.NIGHT_VISION) {
        @Override
        protected int getLingeringTime() {
            return 210;
        }
    };
    public static final TotemEffect horse = new PotionTotemEffect(new ResourceLocation("totemic", "horse"), () -> MobEffects.MOVEMENT_SPEED);
    public static final TotemEffect ocelot = new OcelotTotemEffect(new ResourceLocation("totemic", "ocelot"));
    public static final TotemEffect pig = new PotionTotemEffect(new ResourceLocation("totemic", "pig"), () -> MobEffects.LUCK);
    public static final TotemEffect rabbit = new PotionTotemEffect(new ResourceLocation("totemic", "rabbit"), () -> MobEffects.JUMP);
    public static final TotemEffect spider = new PotionTotemEffect(new ResourceLocation("totemic", "spider"), ModEffects.spider);
    public static final TotemEffect squid = new PotionTotemEffect(new ResourceLocation("totemic", "squid"), () -> MobEffects.WATER_BREATHING);
    public static final TotemEffect wolf = new PotionTotemEffect(new ResourceLocation("totemic", "wolf"), () -> MobEffects.DAMAGE_BOOST);

    @SubscribeEvent
    public static void instruments(TotemicRegisterEvent<MusicInstrument> event) {
        event.registerAll(flute.setItem(ModItems.flute.get()));
    }

    @SubscribeEvent
    public static void totemEffects(TotemicRegisterEvent<TotemEffect> event) {
        event.registerAll(none, bat, blaze, buffalo, cow, enderman, horse, ocelot, pig, rabbit, spider, squid, wolf);
    }
}
