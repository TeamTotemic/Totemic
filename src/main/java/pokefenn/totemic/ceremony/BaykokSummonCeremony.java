package pokefenn.totemic.ceremony;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;
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
            ModEntityTypes.baykok.get().spawn((ServerLevel) level, null, null, null, spawnPos, MobSpawnType.MOB_SUMMONED, false, false);
        }
    }

    @Override
    public int getEffectTime() {
        return 4 * 20;
    }

    @Override
    public boolean canSelect(Level level, BlockPos pos, Entity initiator) {
        if(level.getDifficulty() == Difficulty.PEACEFUL) {
            initiator.sendMessage(new TranslatableComponent("totemic.cantSpawnBaykokOnPeaceful"), Util.NIL_UUID);
            return false;
        }
        else
            return true;
    }
}
