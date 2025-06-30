package pokefenn.totemic.init;

import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicAPI;

public final class ModSounds {
    public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TotemicAPI.MOD_ID);

    public static final RegistryObject<SoundEvent> flute = createSound("flute");
    public static final RegistryObject<SoundEvent> drum = createSound("drum");
    public static final RegistryObject<SoundEvent> wind_chime = createSound("wind_chime");
    public static final RegistryObject<SoundEvent> rattle = createSound("rattle");
    public static final RegistryObject<SoundEvent> eagle_bone_whistle = createSound("eagle_bone_whistle");
    public static final RegistryObject<SoundEvent> bald_eagle_ambient = createSound("bald_eagle.ambient");
    public static final RegistryObject<SoundEvent> bald_eagle_hurt = createSound("bald_eagle.hurt");
    public static final RegistryObject<SoundEvent> bald_eagle_death = createSound("bald_eagle.death");

    private static RegistryObject<SoundEvent> createSound(String name) {
        return REGISTER.register(name, () -> SoundEvent.createVariableRangeEvent(Totemic.resloc(name)));
    }
}
