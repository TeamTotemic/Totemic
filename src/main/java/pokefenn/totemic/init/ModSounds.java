package pokefenn.totemic.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pokefenn.totemic.Totemic;

@EventBusSubscriber(modid = Totemic.MOD_ID)
public final class ModSounds
{
    public static final SoundEvent flute = createSound("flute");
    public static final SoundEvent rattle = createSound("rattle");
    public static final SoundEvent drum = createSound("drum");
    public static final SoundEvent windChime = createSound("wind_chime");
    public static final SoundEvent baldEagleAmbient = createSound("bald_eagle.ambient");
    public static final SoundEvent baldEagleHurt = createSound("bald_eagle.hurt");
    public static final SoundEvent baldEagleDeath = createSound("bald_eagle.death");

    @SubscribeEvent
    public static void init(RegistryEvent.Register<SoundEvent> event)
    {
        event.getRegistry().registerAll(flute, rattle, drum, windChime, baldEagleAmbient, baldEagleHurt);
    }

    private static SoundEvent createSound(String name)
    {
        ResourceLocation loc = new ResourceLocation(Totemic.MOD_ID, name);
        SoundEvent sound = new SoundEvent(loc);
        sound.setRegistryName(loc);
        return sound;
    }
}
