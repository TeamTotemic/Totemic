package pokefenn.totemic.totem;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SpellParticleOption;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.api.totem.TotemEffectContext;

public class OcelotTotemEffect implements TotemEffect {
    @Override
    public void effect(Level level, BlockPos pos, int repetition, TotemEffectContext context) {
        int range = TotemicAPI.get().totemEffect().getDefaultRange(repetition, context);
        for(var creeper: level.getEntitiesOfClass(Creeper.class, TotemicEntityUtil.getAABBAround(pos, range))) {
            if(creeper.swell > 15) {
                if(!level.isClientSide()) {
                    creeper.swell = 0;
                    creeper.setSwellDir(-1);
                }
                else {
                    var center = creeper.getBoundingBox().getCenter();
                    var rand = level.getRandom();
                    var particle = SpellParticleOption.create(ParticleTypes.INSTANT_EFFECT, 0xFFDD74, 1.0F);
                    for(int i = 0; i < 10; i++) {
                        double x = center.x + rand.nextGaussian() * 0.5;
                        double y = center.y + rand.nextGaussian() * 0.75;
                        double z = center.z + rand.nextGaussian() * 0.5;
                        level.addParticle(particle, x, y, z, 0.0, 0.0, 0.0);
                    }
                }
            }
        }
    }

    @Override
    public int getInterval() {
        return 10;
    }

    @Override
    public void medicineBagEffect(Player player, ItemStack medicineBag, int charge) { }

    @Override
    public boolean supportsMedicineBag() {
        return false;
    }
}
