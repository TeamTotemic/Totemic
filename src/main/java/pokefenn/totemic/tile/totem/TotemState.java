package pokefenn.totemic.tile.totem;

import javax.annotation.Nonnull;

import net.minecraft.entity.Entity;
import pokefenn.totemic.api.music.MusicAcceptor;
import pokefenn.totemic.api.music.MusicInstrument;

public interface TotemState extends MusicAcceptor {
    boolean canSelect();

    void addSelector(@Nonnull Entity entity, MusicInstrument instr);
}
