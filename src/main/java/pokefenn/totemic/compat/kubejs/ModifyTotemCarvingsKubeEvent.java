package pokefenn.totemic.compat.kubejs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import dev.latvian.mods.kubejs.event.StartupEventJS;
import dev.latvian.mods.kubejs.typings.Info;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemEffect;

public class ModifyTotemCarvingsKubeEvent extends StartupEventJS {
    @Info("Modifies the given Totem Carving.")
    public void modify(TotemCarving carving, Consumer<TotemCarvingModification> c) {
        var mod = new TotemCarvingModification(carving);
        c.accept(mod);
        if(mod.effectList != null)
            carving.setEffects(mod.effectList);
    }

    public class TotemCarvingModification {
        private final TotemCarving carving;
        private List<TotemEffect> effectList = null; // we can't expose access to this list in 1.20.1 since KubeJS/Rhino doesn't enforce type safety

        public TotemCarvingModification(TotemCarving carving) {
            this.carving = carving;
        }

        @Info("Sets the carving's effects.")
        public void setEffects(TotemEffect[] effects) {
            if(effectList == null)
                effectList = new ArrayList<>(Arrays.asList(effects));
            else {
                effectList.clear();
                effectList.addAll(Arrays.asList(effects));
            }
        }

        @Info("Adds an effect to the carving.")
        public void addEffect(TotemEffect effect) {
            if(effectList == null)
                effectList = new ArrayList<>(carving.getEffects());
            effectList.add(effect);
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
