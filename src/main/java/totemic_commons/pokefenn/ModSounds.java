package totemic_commons.pokefenn;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModSounds
{
    public static final SoundEvent flute = createSound("flute");
    public static final SoundEvent rattle = createSound("rattle");
    public static final SoundEvent drum = createSound("drum");
    public static final SoundEvent windChime = createSound("wind_chime");

    public static void init()
    {
        GameRegistry.register(flute);
        GameRegistry.register(rattle);
        GameRegistry.register(drum);
        GameRegistry.register(windChime);
    }

    private static SoundEvent createSound(String name)
    {
        ResourceLocation loc = new ResourceLocation(Totemic.MOD_ID, name);
        SoundEvent sound = new SoundEvent(loc);
        sound.setRegistryName(loc);
        return sound;
    }
}
