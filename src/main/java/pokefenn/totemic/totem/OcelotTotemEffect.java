package pokefenn.totemic.totem;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.totem.PotionTotemEffect;
import pokefenn.totemic.api.totem.TotemEffectAPI;
import pokefenn.totemic.api.totem.TotemEffectContext;
import pokefenn.totemic.init.ModMobEffects;
import pokefenn.totemic.util.MiscUtil;

public class OcelotTotemEffect extends PotionTotemEffect {
    public OcelotTotemEffect(ResourceLocation name) {
        super(name, false, TotemEffectAPI.DEFAULT_BASE_RANGE, ModMobEffects.ocelot, 10, 0);
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
        if(world.getGameTime() % DEFAULT_INTERVAL == 0) //The effect interval is 10 ticks, we only need 80 ticks for the proxy potion effect
            super.effect(world, pos, repetition, context);

        if(world.isClientSide)
            return;

        int range = TotemicAPI.get().totemEffect().getDefaultRange(this, repetition, context);
        TotemicEntityUtil.getEntitiesInRange(Creeper.class, world, pos, range, range)
            .forEach(creeper -> {
                int swell = (int) creeperSwell.get(creeper);
                if(swell > 15) {
                    creeperSwell.set(creeper, 0);
                    creeper.setSwellDir(-1);
                    MiscUtil.spawnServerParticles(ParticleTypes.INSTANT_EFFECT, world, creeper.getBoundingBox().getCenter(), 10, new Vec3(0.5, 0.75, 0.5), 0.0);
                }
            });
    }

    @Override
    protected int getLingeringTime() {
        return super.getLingeringTime() + DEFAULT_INTERVAL - interval; //Adjust for the smaller effect interval
    }
}
