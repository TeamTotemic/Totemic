package pokefenn.totemic.totem;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.api.totem.TotemEffectContext;

public class EmptyTotemEffect extends TotemEffect {
    public EmptyTotemEffect() {
        super(true, Integer.MAX_VALUE);
    }

    @Override
    public void effect(World world, BlockPos pos, int repetition, TotemEffectContext context) {
    }
}
