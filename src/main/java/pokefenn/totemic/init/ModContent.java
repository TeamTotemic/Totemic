package pokefenn.totemic.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.RegistryBuilder;
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
        event.getRegistry().registerAll(new MusicInstrument("totemic:flute", 3, 50).setItem(ModItems.flute).setRegistryName("flute"));
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

    @SubscribeEvent
    public static void createRegistries(NewRegistryEvent event) {
        // RegistryEvents are fired in alphabetic order.
        // Instruments have to be registered before Ceremonies.
        event.create(new RegistryBuilder<MusicInstrument>()
                .setName(new ResourceLocation(TotemicAPI.MOD_ID, "a_music_instruments"))
                .setType(MusicInstrument.class)
                .setMaxID(Byte.MAX_VALUE));
        event.create(new RegistryBuilder<TotemEffect>()
                .setName(new ResourceLocation(TotemicAPI.MOD_ID, "b_totem_effects"))
                .setType(TotemEffect.class)
                .setMaxID(Byte.MAX_VALUE));
    }
}
