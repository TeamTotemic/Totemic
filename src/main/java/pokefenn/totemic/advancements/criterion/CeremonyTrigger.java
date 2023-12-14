package pokefenn.totemic.advancements.criterion;

import java.util.Optional;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import pokefenn.totemic.advancements.ModCriteriaTriggers;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.ceremony.Ceremony;

public class CeremonyTrigger extends SimpleCriterionTrigger<CeremonyTrigger.TriggerInstance> {
    @Override
    public TriggerInstance createInstance(JsonObject pJson, Optional<ContextAwarePredicate> pPlayer, DeserializationContext pContext) {
        var name = new ResourceLocation(GsonHelper.getAsString(pJson, "ceremony"));
        var ceremony = TotemicAPI.get().registry().ceremonies().getValue(name);
        if(ceremony == null)
            throw new JsonSyntaxException("Unknown Ceremony '" + name + "'");
        return new TriggerInstance(pPlayer, ceremony);
    }

    public void trigger(ServerPlayer player, Ceremony ceremony) {
        trigger(player, ti -> ti.matches(ceremony));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final Ceremony ceremony;

        public TriggerInstance(Optional<ContextAwarePredicate> pPlayer, Ceremony ceremony) {
            super(pPlayer);
            this.ceremony = ceremony;
        }

        public static Criterion<TriggerInstance> performedCeremony(Ceremony ceremony) {
            return ModCriteriaTriggers.PERFORM_CEREMONY.createCriterion(new TriggerInstance(Optional.empty(), ceremony));
        }

        public boolean matches(Ceremony cer) {
            return this.ceremony == cer;
        }

        @Override
        public JsonObject serializeToJson() {
            var json = super.serializeToJson();
            json.addProperty("ceremony", ceremony.getRegistryName().toString());
            return json;
        }
    }
}
