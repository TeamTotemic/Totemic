package pokefenn.totemic.totem;

import java.lang.invoke.VarHandle;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.api.totem.EntityAffectingEffect;
import pokefenn.totemic.api.totem.TotemEffectContext;
import pokefenn.totemic.util.MethodHandleUtil;
import pokefenn.totemic.util.MiscUtil;

public class OcelotTotemEffect extends EntityAffectingEffect<Creeper> {
    public OcelotTotemEffect() {
        super(10, EntityType.CREEPER);
    }

    private static final VarHandle creeperSwell = MethodHandleUtil.findField(Creeper.class, "f_32270_", int.class); //The Creeper.swell field

    @Override
    public void applyTo(Creeper creeper, int repetition, TotemEffectContext context) {
        int swell = (int) creeperSwell.get(creeper);
        if(swell > 15) {
            creeperSwell.set(creeper, 0);
            creeper.setSwellDir(-1);
            MiscUtil.spawnServerParticles(ParticleTypes.INSTANT_EFFECT, creeper.level, creeper.getBoundingBox().getCenter(), 10, new Vec3(0.5, 0.75, 0.5), 0.0);
        }
    }
}
