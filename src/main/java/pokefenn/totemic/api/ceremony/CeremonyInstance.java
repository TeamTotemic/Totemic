package pokefenn.totemic.api.ceremony;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;

public interface CeremonyInstance extends INBTSerializable<Tag> {
    void effect(Level level, BlockPos pos, CeremonyEffectContext context);

    default int getEffectTime() {
        return 0;
    }

    default boolean canSelect(Level level, BlockPos pos, Entity initiator) {
        return true;
    }

    default void onStartup(Level level, BlockPos pos, StartupContext context) { }

    default void onStartupFail(Level level, BlockPos pos, StartupContext context) { }

    default boolean canStartEffect(Level level, BlockPos pos, StartupContext context) {
        return true;
    }

    @Override
    default Tag serializeNBT() {
        return EndTag.INSTANCE;
    }

    @Override
    default void deserializeNBT(Tag nbt) { }
}
