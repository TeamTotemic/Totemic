package pokefenn.totemic.totem;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.api.totem.TotemEffectContext;

public class TotemEffectNone extends TotemEffect {
    public TotemEffectNone() {
        super(true, Integer.MAX_VALUE);
    }

    @Override
    public void effect(IBlockReader world, BlockPos pos, int repetition, TotemEffectContext context) {
    }
}
