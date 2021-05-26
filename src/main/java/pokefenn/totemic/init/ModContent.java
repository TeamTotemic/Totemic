package pokefenn.totemic.init;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.RegistryBuilder;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.totem.PotionTotemEffect;
import pokefenn.totemic.api.totem.RegisterTotemEffectsEvent;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.totem.EmptyTotemEffect;
import pokefenn.totemic.totem.OcelotTotemEffect;

@ObjectHolder(Totemic.MOD_ID)
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
                new EmptyTotemEffect().setRegistryName("bat"), //TODO
                new PotionTotemEffect(Effects.FIRE_RESISTANCE).setRegistryName("blaze"),
                new PotionTotemEffect(Effects.DIG_SPEED).setRegistryName("buffalo"),
                new PotionTotemEffect(Effects.DAMAGE_RESISTANCE) {
                    @Override
                    protected void applyTo(boolean isMedicineBag, PlayerEntity player, int time, int amplifier) {
                        super.applyTo(isMedicineBag, player, time, amplifier);
                        player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, time, 0, true, false));
                    };
                }.setRegistryName("cow"),
                new PotionTotemEffect(Effects.NIGHT_VISION) {
                    @Override
                    protected int getLingeringTime() {
                        return 210;
                    };
                }.setRegistryName("enderman"),
                new PotionTotemEffect(Effects.MOVEMENT_SPEED).setRegistryName("horse"),
                new OcelotTotemEffect().setRegistryName("ocelot"),
                new PotionTotemEffect(Effects.LUCK).setRegistryName("pig"),
                new PotionTotemEffect(Effects.JUMP).setRegistryName("rabbit"),
                new PotionTotemEffect(ModEffects.spider).setRegistryName("spider"),
                new PotionTotemEffect(Effects.WATER_BREATHING).setRegistryName("squid"),
                new PotionTotemEffect(Effects.DAMAGE_BOOST).setRegistryName("wolf"));
    }

    @SubscribeEvent
    public static void createRegistries(RegistryEvent.NewRegistry event) {
        // RegistryEvents are fired in alphabetic order.
        // Instruments have to be registered before Ceremonies.
        new RegistryBuilder<MusicInstrument>().setName(new ResourceLocation(Totemic.MOD_ID, "a_music_instruments")).setType(MusicInstrument.class).setMaxID(Byte.MAX_VALUE).create();
        new RegistryBuilder<TotemEffect>().setName(new ResourceLocation(Totemic.MOD_ID, "b_totem_effects")).setType(TotemEffect.class).setMaxID(Byte.MAX_VALUE).create();
        // new RegistryBuilder<Ceremony>().setName(new ResourceLocation(Totemic.MOD_ID, "c_ceremonies")).setType(Ceremony.class).setMaxID(Byte.MAX_VALUE).create();
    }
}
