package pokefenn.totemic.compat.kubejs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import dev.latvian.mods.kubejs.event.KubeEvent;
import dev.latvian.mods.kubejs.typings.Info;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemEffect;

public class ModifyTotemCarvingsKubeEvent implements KubeEvent {
    @Info("Modifies the given Totem Carving.")
    public void modify(TotemCarving carving, Consumer<TotemCarvingModification> c) {
        var mod = new TotemCarvingModification(carving);
        c.accept(mod);
        if(mod.effectList != null)
            carving.setEffects(mod.effectList);
    }

    public class TotemCarvingModification {
        private final TotemCarving carving;
        private List<TotemEffect> effectList = null; // A lazily created copy of the carving's effects, to enable convenient modification from JavaScript

        public TotemCarvingModification(TotemCarving carving) {
            this.carving = carving;
        }

        @Info("Returns the carving's effects.")
        public List<TotemEffect> getEffects() {
            if(effectList == null)
                effectList = new ArrayList<>(carving.getEffects());
            return effectList;
        }

        @Info("Sets the carving's effects.")
        public void setEffects(List<TotemEffect> effects) {
            effectList = Objects.requireNonNull(effects);
        }

        @Info("Returns how much charge is drained from a Medicine Bag every " + TotemCarving.MEDICINE_BAG_DRAIN_INTERVAL + " ticks (regardless of the effects' intervals).")
        public int getMedicineBagDrain() {
            return carving.getMedicineBagDrain();
        }

        @Info("Sets the amount of charge to drain from a Medicine Bag every " + TotemCarving.MEDICINE_BAG_DRAIN_INTERVAL + " ticks (regardless of the effects' intervals).")
        public void setMedicineBagDrain(int drain) {
            carving.setMedicineBagDrain(drain);
        }
    }
}
