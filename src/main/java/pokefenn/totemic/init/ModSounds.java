package pokefenn.totemic.init;

import java.util.function.Supplier;

import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicAPI;

public final class ModSounds {
    public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(Registries.SOUND_EVENT, TotemicAPI.MOD_ID);

    public static final Supplier<SoundEvent> flute = createSound("flute");
    public static final Supplier<SoundEvent> drum = createSound("drum");
    public static final Supplier<SoundEvent> wind_chime = createSound("wind_chime");
    public static final Supplier<SoundEvent> rattle = createSound("rattle");
    public static final Supplier<SoundEvent> eagle_bone_whistle = createSound("eagle_bone_whistle");
    public static final Supplier<SoundEvent> bald_eagle_ambient = createSound("bald_eagle.ambient");
    public static final Supplier<SoundEvent> bald_eagle_hurt = createSound("bald_eagle.hurt");
    public static final Supplier<SoundEvent> bald_eagle_death = createSound("bald_eagle.death");

    private static Supplier<SoundEvent> createSound(String name) {
        return REGISTER.register(name, () -> SoundEvent.createVariableRangeEvent(Totemic.resloc(name)));
    }
}
