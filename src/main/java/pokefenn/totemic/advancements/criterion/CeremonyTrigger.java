package pokefenn.totemic.advancements.criterion;

import java.util.*;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicRegistries;
import pokefenn.totemic.api.ceremony.Ceremony;

public class CeremonyTrigger implements ICriterionTrigger<CeremonyTrigger.Instance>
{
    private static final ResourceLocation ID = new ResourceLocation(Totemic.MOD_ID, "perform_ceremony");

    private final Map<PlayerAdvancements, Listeners> listeners = new HashMap<>();

    @Override
    public ResourceLocation getId()
    {
        return ID;
    }

    @Override
    public void addListener(PlayerAdvancements advancements, Listener<Instance> listener)
    {
        listeners.computeIfAbsent(advancements, Listeners::new).add(listener);
    }

    @Override
    public void removeListener(PlayerAdvancements advancements, Listener<Instance> listener)
    {
        Listeners ls = listeners.get(advancements);
        if(ls != null)
        {
            ls.remove(listener);
            if(ls.isEmpty())
                listeners.remove(advancements);
        }
    }

    @Override
    public void removeAllListeners(PlayerAdvancements advancements)
    {
        listeners.remove(advancements);
    }

    @Override
    public Instance deserializeInstance(JsonObject json, JsonDeserializationContext context)
    {
        String name = JsonUtils.getString(json, "ceremony");
        Ceremony ceremony = TotemicRegistries.ceremonies().getValue(new ResourceLocation(name));
        if(ceremony == null)
            throw new JsonSyntaxException("Unknown ceremony: '" + name + "'");
        else
            return new Instance(ceremony);
    }

    public void trigger(EntityPlayerMP player, Ceremony ceremony)
    {
        Listeners ls = listeners.get(player.getAdvancements());
        if(ls != null)
            ls.trigger(ceremony);
    }

    public static class Instance extends AbstractCriterionInstance
    {
        private final Ceremony ceremony;

        public Instance(Ceremony ceremony)
        {
            super(ID);
            this.ceremony = ceremony;
        }

        public boolean test(Ceremony ceremony)
        {
            return this.ceremony == ceremony;
        }
    }

    static class Listeners
    {
        private final PlayerAdvancements playerAdvancements;
        private final Set<Listener<Instance>> listeners = new HashSet<>();

        public Listeners(PlayerAdvancements playerAdvancements)
        {
            this.playerAdvancements = playerAdvancements;
        }

        public boolean isEmpty()
        {
            return listeners.isEmpty();
        }

        public void add(Listener<Instance> listener)
        {
            listeners.add(listener);
        }

        public void remove(Listener<Instance> listener)
        {
            listeners.remove(listener);
        }

        public void trigger(Ceremony ceremony)
        {
            List<Listener<Instance>> list = null;

            for(Listener<Instance> listener: listeners)
            {
                if(listener.getCriterionInstance().test(ceremony))
                {
                    if(list == null)
                        list = new ArrayList<>();
                    list.add(listener);
                }
            }

            if(list != null)
            {
                for(Listener<Instance> listener: list)
                    listener.grantCriterion(playerAdvancements);
            }
        }
    }
}
