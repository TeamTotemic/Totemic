package pokefenn.totemic.advancements.criterion;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;
import pokefenn.totemic.advancements.ModCriteriaTriggers;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.apiimpl.registry.RegistryApiImpl;

public class CeremonyTrigger extends SimpleCriterionTrigger<CeremonyTrigger.TriggerInstance> {
    @Override
    public Codec<TriggerInstance> codec() {
        // TODO Auto-generated method stub
        return null;
    }

    public void trigger(ServerPlayer player, Ceremony ceremony) {
        trigger(player, ti -> ti.matches(ceremony));
    }

    public static record TriggerInstance(Optional<ContextAwarePredicate> player, Ceremony ceremony) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player),
                    RegistryApiImpl.INSTANCE.ceremonies().byNameCodec().fieldOf("ceremony").forGetter(TriggerInstance::ceremony)
            ).apply(instance, TriggerInstance::new));

        public static Criterion<TriggerInstance> performedCeremony(Ceremony ceremony) {
            return ModCriteriaTriggers.PERFORM_CEREMONY.createCriterion(new TriggerInstance(Optional.empty(), ceremony));
        }

        public boolean matches(Ceremony cer) {
            return this.ceremony == cer;
        }
    }
}
