package pokefenn.totemic.api;

import org.jetbrains.annotations.Nullable;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import pokefenn.totemic.api.music.DefaultMusicAcceptor;
import pokefenn.totemic.api.music.MusicAcceptor;

/**
 * Provides Capabilities added by Totemic.
 */
public final class TotemicCapabilities {
    /**
     * Capability for {@link MusicAcceptor}.
     * <p>
     * Note that for performance reasons, the Capability is only recognized when it is exposed by a block entity (using {@link RegisterCapabilitiesEvent#registerBlockEntity}).
     * <p>
     * A reference implementation is provided by {@link DefaultMusicAcceptor}.
     */
    public static final BlockCapability<MusicAcceptor, @Nullable Void> MUSIC_ACCEPTOR = BlockCapability.createVoid(ResourceLocation.fromNamespaceAndPath(TotemicAPI.MOD_ID, "music_acceptor"), MusicAcceptor.class);
}
