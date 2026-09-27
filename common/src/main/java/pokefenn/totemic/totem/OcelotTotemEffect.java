package pokefenn.totemic.totem;

import net.minecraft.core.BlockPos;
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
        if(level.isClientSide())
            return;
        int range = TotemicAPI.get().totemEffect().getDefaultRange(repetition, context);
        for(Creeper creeper: level.getEntitiesOfClass(Creeper.class, TotemicEntityUtil.getAABBAround(pos, range))) {
            if(creeper.swell > 15) {
                creeper.swell = 0;
                creeper.setSwellDir(-1);
                // FIXME: can't use INSTANT_EFFECT with sendParticles anymore. Need to do this on the client side
                // MiscUtil.spawnServerParticles(ParticleTypes.INSTANT_EFFECT, creeper.level(), creeper.getBoundingBox().getCenter(), 10, new Vec3(0.5, 0.75, 0.5), 0.0);
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
