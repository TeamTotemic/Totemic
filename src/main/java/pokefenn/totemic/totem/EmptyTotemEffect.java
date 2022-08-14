package pokefenn.totemic.totem;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.api.totem.TotemEffectContext;

public class EmptyTotemEffect extends TotemEffect {
    public EmptyTotemEffect(ResourceLocation name) {
        super(name, true, Integer.MAX_VALUE);
    }

    @Override
    public void effect(Level world, BlockPos pos, int repetition, TotemEffectContext context) {
    }
}
