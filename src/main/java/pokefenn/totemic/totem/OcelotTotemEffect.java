package pokefenn.totemic.totem;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.api.totem.TotemEffectContext;

public class OcelotTotemEffect extends TotemEffect {
    public OcelotTotemEffect(ResourceLocation name) {
        super(name, false, 10);
    }

    private static final VarHandle creeperSwell;

    static {
        try {
            Field swellField = ObfuscationReflectionHelper.findField(Creeper.class, "f_32270_"); //The Creeper#swell field
            creeperSwell = MethodHandles.privateLookupIn(Creeper.class, MethodHandles.lookup()).unreflectVarHandle(swellField);
        }
        catch(Exception e) {
            throw new RuntimeException("Could not get VarHandle for Creeper.swell field", e);
        }
    }

    @Override
    public void effect(Level world, BlockPos pos, int repetition, TotemEffectContext context) {
        if(world.isClientSide)
            return;

        int range = TotemicAPI.get().totemEffect().getDefaultRange(this, repetition, context);
        TotemicEntityUtil.getEntitiesInRange(Creeper.class, world, pos, range, range)
            .forEach(creeper -> {
                int ignited = (int) creeperSwell.get(creeper);

                if(ignited > 15) {
                    creeperSwell.set(creeper, 0);
                    creeper.setSwellDir(-1);
                }
            });
    }

}
