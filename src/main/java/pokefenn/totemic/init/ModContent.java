package pokefenn.totemic.init;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.RegistryBuilder;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.totem.TotemEffect;

@ObjectHolder(Totemic.MOD_ID)
public final class ModContent {
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
    public static void totemEffects(RegistryEvent.Register<TotemEffect> event) {
        event.getRegistry().registerAll(
            new TotemEffect().setRegistryName("none"),
            new TotemEffect().setRegistryName("bat"),
            new TotemEffect().setRegistryName("blaze"),
            new TotemEffect().setRegistryName("buffalo"),
            new TotemEffect().setRegistryName("cow"),
            new TotemEffect().setRegistryName("enderman"),
            new TotemEffect().setRegistryName("horse"),
            new TotemEffect().setRegistryName("ocelot"),
            new TotemEffect().setRegistryName("pig"),
            new TotemEffect().setRegistryName("rabbit"),
            new TotemEffect().setRegistryName("spider"),
            new TotemEffect().setRegistryName("squid"),
            new TotemEffect().setRegistryName("wolf"));
    }

    @SubscribeEvent
    public static void createRegistries(RegistryEvent.NewRegistry event) {
        //RegistryEvents are fired in alphabetic order.
        //Instruments have to be registered before Ceremonies.
        //new RegistryBuilder<MusicInstrument>().setName(new ResourceLocation(Totemic.MOD_ID, "a_music_instruments")).setType(MusicInstrument.class).setMaxID(Byte.MAX_VALUE).create();
        new RegistryBuilder<TotemEffect>().setName(new ResourceLocation(Totemic.MOD_ID, "b_totem_effects")).setType(TotemEffect.class).setMaxID(Byte.MAX_VALUE).create();
        //new RegistryBuilder<Ceremony>().setName(new ResourceLocation(Totemic.MOD_ID, "c_ceremonies")).setType(Ceremony.class).setMaxID(Byte.MAX_VALUE).disableSaving().create();
    }
}
