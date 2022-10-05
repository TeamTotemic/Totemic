package pokefenn.totemic.ceremony;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;

public class CeremonyRain extends CeremonyInstance {
    private final boolean doRain;

    public CeremonyRain(boolean doRain) {
        this.doRain = doRain;
    }

    @Override
    public void effect(Level level, BlockPos pos, CeremonyEffectContext context) {
        if(level.isRaining() != doRain) {
            level.getLevelData().setRaining(doRain);
            level.setRainLevel(doRain ? 1.0F : 0.0F);
        }
    }
}
