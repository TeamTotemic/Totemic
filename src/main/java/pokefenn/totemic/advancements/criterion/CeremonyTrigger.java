package pokefenn.totemic.advancements.criterion;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.ceremony.Ceremony;

public class CeremonyTrigger extends SimpleCriterionTrigger<CeremonyTrigger.TriggerInstance> {
    private static final ResourceLocation ID = Totemic.resloc("performed_ceremony");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public TriggerInstance createInstance(JsonObject pJson, ContextAwarePredicate pPlayer, DeserializationContext pContext) {
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

        public TriggerInstance(ContextAwarePredicate pPlayer, Ceremony ceremony) {
            super(ID, pPlayer);
            this.ceremony = ceremony;
        }

        public static TriggerInstance performedCeremony(Ceremony ceremony) {
            return new TriggerInstance(ContextAwarePredicate.ANY, ceremony);
        }

        public boolean matches(Ceremony cer) {
            return this.ceremony == cer;
        }

        @Override
        public JsonObject serializeToJson(SerializationContext pConditions) {
            var json = super.serializeToJson(pConditions);
            json.addProperty("ceremony", ceremony.getRegistryName().toString());
            return json;
        }
    }
}
