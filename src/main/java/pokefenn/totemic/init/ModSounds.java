package pokefenn.totemic.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import pokefenn.totemic.api.TotemicAPI;

public final class ModSounds {
    public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TotemicAPI.MOD_ID);

    public static final RegistryObject<SoundEvent> flute = createSound("flute");
    public static final RegistryObject<SoundEvent> drum = createSound("drum");

    private static RegistryObject<SoundEvent> createSound(String name) {
        return REGISTER.register(name, () -> new SoundEvent(new ResourceLocation(TotemicAPI.MOD_ID, name)));
    }
}
