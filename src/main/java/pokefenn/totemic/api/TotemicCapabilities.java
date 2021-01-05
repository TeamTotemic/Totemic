package pokefenn.totemic.api;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import pokefenn.totemic.api.music.DefaultMusicAcceptor;
import pokefenn.totemic.api.music.MusicAcceptor;

public final class TotemicCapabilities {
    /**
     * Capability for {@link MusicAcceptor}.
     *
     * <p>
     * The default implementations are of type {@link DefaultMusicAcceptor}. The default storage handler can only serialize instances of that class.
     */
    @CapabilityInject(MusicAcceptor.class)
    public static final Capability<MusicAcceptor> MUSIC_ACCEPTOR = null;
}
