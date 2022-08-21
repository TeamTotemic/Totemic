package pokefenn.totemic.api.ceremony;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class CeremonyInstance implements INBTSerializable<Tag> {
    protected final Level level;
    protected final BlockPos pos;

    public CeremonyInstance(Level level, BlockPos pos) {
        this.level = level;
        this.pos = pos;
    }

    public boolean canSelect() {
        return true;
    }

    public void onStartup(StartupContext context) { }

    public void onStartupFail(StartupContext context) { }

    public boolean canStartEffect(StartupContext context) {
        return true;
    }

    public void onEffectEnd(CeremonyEffectContext context) { }

    public abstract void effect(CeremonyEffectContext context);

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
