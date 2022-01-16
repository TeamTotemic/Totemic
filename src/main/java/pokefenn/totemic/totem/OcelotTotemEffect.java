package pokefenn.totemic.totem;

import java.lang.reflect.Field;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.api.totem.TotemEffectContext;

public class OcelotTotemEffect extends TotemEffect {
    public OcelotTotemEffect() {
        super(false, 10);
    }

    private static final Field timeSinceIgnited = ObfuscationReflectionHelper.findField(Creeper.class, "swell");

    @Override
    public void effect(Level world, BlockPos pos, int repetition, TotemEffectContext context) {
        if(world.isClientSide)
            return;

        int range = TotemicAPI.get().totemEffect().getDefaultRange(this, repetition, context);
        TotemicEntityUtil.getEntitiesInRange(Creeper.class, world, pos, range, range)
            .forEach(creeper -> {
                try {
                    int ignited = (Integer) timeSinceIgnited.get(creeper);

                    if(ignited > 15)
                    {
                        timeSinceIgnited.setInt(creeper, 0);
                        creeper.setSwellDir(-1);
                    }
                }
                catch (ReflectiveOperationException e) {
                }
            });
    }

}
