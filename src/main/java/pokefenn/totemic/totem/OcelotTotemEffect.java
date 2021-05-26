package pokefenn.totemic.totem;

import java.lang.reflect.Field;

import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.api.totem.TotemEffectContext;

public class OcelotTotemEffect extends TotemEffect {
    public OcelotTotemEffect() {
        super(false, 10);
    }

    private static final Field timeSinceIgnited = ObfuscationReflectionHelper.findField(CreeperEntity.class, "swell");

    @Override
    public void effect(World world, BlockPos pos, int repetition, TotemEffectContext context) {
        if(world.isClientSide)
            return;

        int range = TotemicAPI.get().totemEffect().getDefaultRange(this, repetition, context);
        TotemicEntityUtil.getEntitiesInRange(CreeperEntity.class, world, pos, range, range)
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
