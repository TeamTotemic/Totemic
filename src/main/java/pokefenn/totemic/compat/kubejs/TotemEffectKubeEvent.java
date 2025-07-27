package pokefenn.totemic.compat.kubejs;

import dev.latvian.mods.kubejs.level.KubeLevelEvent;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.event.TotemEffectEvent;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.api.totem.TotemEffectContext;

@Info("""
    This event is fired every time a TotemEffect is applied at a Totem Base.

    When canceled, TotemEffect#effect will not be called.

    Note: This event is currently fired multiple times if a TotemCarving contains multiple TotemEffects (like
    the Cow and Ocelot carvings), and not fired at all for TotemCarvings with no effects (like the 'none' carving).
    This is expected to change in the future.
    """)
public class TotemEffectKubeEvent implements KubeLevelEvent {
    private final TotemEffectEvent event;

    public TotemEffectKubeEvent(TotemEffectEvent event) {
        this.event = event;
    }

    @Override
    @Info("The level where the TotemEffect is applied.")
    public Level getLevel() {
        return (Level) event.getLevel();
    }

    @Info("The position of the Totem Base where the effect is applied.")
    public BlockPos getPos() {
        return event.getPos();
    }

    @Info("The TotemEffect that is being applied.")
    public TotemEffect getEffect() {
        return event.getEffect();
    }

    @Info("The TotemCarving that the effect belongs to.")
    public TotemCarving getCarving() {
        return event.getCarving();
    }

    @Info("The number of times the TotemCarving is repeated on the Totem Pole.")
    public int getRepetition() {
        return event.getRepetition();
    }

    @Info("A TotemEffectContext providing details about the Totem Pole the effect originates from.")
    public TotemEffectContext getContext() {
        return event.getContext();
    }
}
