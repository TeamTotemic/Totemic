package pokefenn.totemic.ceremony;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.init.ModEntityTypes;

public enum BaykokSummonCeremony implements CeremonyInstance {
    INSTANCE;

    @Override
    public void effect(Level level, BlockPos pos, CeremonyEffectContext context) {
        if(!level.isClientSide && context.getTime() == getEffectTime()-1) {
            level.globalLevelEvent(1023, pos, 0); //Wither spawn sound
            var spawnPos = pos.relative(Direction.Plane.HORIZONTAL.getRandomDirection(level.random));
            spawnPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, spawnPos);
            ModEntityTypes.baykok.get().spawn((ServerLevel) level, spawnPos, MobSpawnType.MOB_SUMMONED);
        }
    }

    @Override
    public int getEffectTime() {
        return 4 * 20;
    }

    @Override
    public boolean canSelect(Level level, BlockPos pos, Entity initiator) {
        if(level.getDifficulty() == Difficulty.PEACEFUL) {
            initiator.sendSystemMessage(Component.translatable("totemic.cantSpawnBaykokOnPeaceful"));
            return false;
        }
        else
            return true;
    }
}
