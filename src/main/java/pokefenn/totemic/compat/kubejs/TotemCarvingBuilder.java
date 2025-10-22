package pokefenn.totemic.compat.kubejs;

import java.util.ArrayList;
import java.util.List;

import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import pokefenn.totemic.api.totem.PotionTotemEffect;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemEffect;

public class TotemCarvingBuilder extends BuilderBase<TotemCarving> {
    public transient final List<TotemEffect> effects = new ArrayList<>();
    public transient int medicineBagDrain = TotemCarving.DEFAULT_MEDICINE_BAG_DRAIN;

    public TotemCarvingBuilder(ResourceLocation id) {
        super(id);
    }

    @Override
    public RegistryInfo<TotemCarving> getRegistryType() {
        return TotemicKubeJSPlugin.TOTEM_CARVING;
    }

    @Override
    public TotemCarving createObject() {
        return TotemCarving.of(effects).setMedicineBagDrain(medicineBagDrain);
    }

    @Info("Adds an effect to the carving")
    public TotemCarvingBuilder effect(TotemEffect effect) {
        effects.add(effect);
        return this;
    }

    @Info("Adds a potion effect (`TotemEffect.potion(...)`) to the carving")
    public TotemCarvingBuilder potion(MobEffect mobEffect) {
        return effect(new PotionTotemEffect(mobEffect));
    }

    @Info("Sets the amount of charge to drain from a Medicine Bag every " + TotemCarving.MEDICINE_BAG_DRAIN_INTERVAL + " ticks (regardless of the effects' intervals).")
    public TotemCarvingBuilder medicineBagDrain(int drain) {
        this.medicineBagDrain = drain;
        return this;
    }

    @Override
    public String getTranslationKeyGroup() {
        return "totemic.totem";
    }
}
