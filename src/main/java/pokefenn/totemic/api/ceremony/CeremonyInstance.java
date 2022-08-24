package pokefenn.totemic.api.ceremony;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class CeremonyInstance implements INBTSerializable<Tag> {
    public CeremonyInstance() {
    }

    public boolean canSelect(Level level, BlockPos pos) {
        return true;
    }

    public void onStartup(Level level, BlockPos pos, StartupContext context) { }

    public void onStartupFail(Level level, BlockPos pos, StartupContext context) { }

    public boolean canStartEffect(Level level, BlockPos pos, StartupContext context) {
        return true;
    }

    public void onEffectEnd(Level level, BlockPos pos, CeremonyEffectContext context) { }

    public abstract void effect(Level level, BlockPos pos, CeremonyEffectContext context);

    public int getEffectTime() {
        return 0;
    }

    @Override
    public Tag serializeNBT() {
        return EndTag.INSTANCE;
    }

    @Override
    public void deserializeNBT(Tag nbt) { }
}
