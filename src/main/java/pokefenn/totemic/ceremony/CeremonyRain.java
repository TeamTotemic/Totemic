package pokefenn.totemic.ceremony;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
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
        if(level instanceof ServerLevel slevel && slevel.isRaining() != doRain) {
            slevel.setWeatherParameters(
                    doRain ? 0 : 6000, //Clear weather time
                    doRain ? 6000 : 0, //Rain time
                    doRain, //raining
                    false); //thundering
        }
    }
}
