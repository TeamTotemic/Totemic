package pokefenn.totemic.init;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ObjectHolder;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.totem.PotionTotemEffect;
import pokefenn.totemic.api.totem.RegisterTotemEffectsEvent;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.totem.EmptyTotemEffect;
import pokefenn.totemic.totem.OcelotTotemEffect;

@ObjectHolder(TotemicAPI.MOD_ID)
public final class ModContent {
    public static final MusicInstrument flute = null;
    /*
     * public static MusicInstrument drum;
     * public static MusicInstrument windChime;
     * public static MusicInstrument jingleDress;
     * public static MusicInstrument rattle;
     * public static MusicInstrument eagleBoneWhistle;
     * public static MusicInstrument netherPipe;
     */

    public static final TotemEffect none = null;
    public static final TotemEffect bat = null;
    public static final TotemEffect blaze = null;
    public static final TotemEffect buffalo = null;
    public static final TotemEffect cow = null;
    public static final TotemEffect enderman = null;
    public static final TotemEffect horse = null;
    public static final TotemEffect ocelot = null;
    public static final TotemEffect pig = null;
    public static final TotemEffect rabbit = null;
    public static final TotemEffect spider = null;
    public static final TotemEffect squid = null;
    public static final TotemEffect wolf = null;

    @SubscribeEvent
    public static void instruments(RegistryEvent.Register<MusicInstrument> event) {
        event.getRegistry().registerAll(new MusicInstrument("totemic:flute", 3, 50).setItem(ModItems.flute.get()).setRegistryName("flute"));
    }

    @SubscribeEvent
    public static void totemEffects(RegisterTotemEffectsEvent event) {
        event.registerAll(
                new EmptyTotemEffect().setRegistryName("none"),
                new PotionTotemEffect(MobEffects.SLOW_FALLING).setRegistryName("bat"),
                new PotionTotemEffect(MobEffects.FIRE_RESISTANCE).setRegistryName("blaze"),
                new PotionTotemEffect(MobEffects.DIG_SPEED).setRegistryName("buffalo"),
                new PotionTotemEffect(MobEffects.DAMAGE_RESISTANCE) {
                    @Override
                    protected void applyTo(boolean isMedicineBag, Player player, int time, int amplifier) {
                        super.applyTo(isMedicineBag, player, time, amplifier);
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, time, 0, true, false));
                    }
                }.setRegistryName("cow"),
                new PotionTotemEffect(MobEffects.NIGHT_VISION) {
                    @Override
                    protected int getLingeringTime() {
                        return 210;
                    }
                }.setRegistryName("enderman"),
                new PotionTotemEffect(MobEffects.MOVEMENT_SPEED).setRegistryName("horse"),
                new OcelotTotemEffect().setRegistryName("ocelot"),
                new PotionTotemEffect(MobEffects.LUCK).setRegistryName("pig"),
                new PotionTotemEffect(MobEffects.JUMP).setRegistryName("rabbit"),
                new PotionTotemEffect(ModEffects.spider).setRegistryName("spider"),
                new PotionTotemEffect(MobEffects.WATER_BREATHING).setRegistryName("squid"),
                new PotionTotemEffect(MobEffects.DAMAGE_BOOST).setRegistryName("wolf"));
    }
}
