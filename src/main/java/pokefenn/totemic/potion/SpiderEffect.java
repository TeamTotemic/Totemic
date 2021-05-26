package pokefenn.totemic.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class SpiderEffect extends Effect {
    public SpiderEffect() {
        super(EffectType.BENEFICIAL, 0x524354);
        setRegistryName("spider");
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if(entity.horizontalCollision) {
            entity.fallDistance = 0.0F;

            float factor = 0.15F;

            Vector3d motion = entity.getDeltaMovement();

            double motionX = MathHelper.clamp(motion.x, -factor, factor);
            double motionY = 0.2;
            double motionZ = MathHelper.clamp(motion.z, -factor, factor);
            if(entity.isSuppressingSlidingDownLadder()) {
                motionY = 0.0;
            }

            entity.setDeltaMovement(motionX, motionY, motionZ);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
