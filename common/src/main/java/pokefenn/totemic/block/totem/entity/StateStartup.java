package pokefenn.totemic.block.totem.entity;

import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.redstone.Redstone;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.advancements.ModCriteriaTriggers;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.api.ceremony.StartupContext;
import pokefenn.totemic.api.music.DefaultMusicAcceptor;
import pokefenn.totemic.api.music.MusicAcceptor;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.client.CeremonyHUD;
import pokefenn.totemic.network.ClientboundPacketStartupMusic;
import pokefenn.totemic.util.MiscUtil;

public final class StateStartup extends TotemState implements StartupContext {
    private static final int ADVANCEMENT_TRIGGER_RANGE = 8;

    static final byte ID = 2;

    private Ceremony ceremony;
    private CeremonyInstance instance;
    private @Nullable Entity initiator;

    private final DefaultMusicAcceptor musicHandler = new DefaultMusicAcceptor();
    private int time = 0;

    StateStartup(TotemBaseBlockEntity tile, Ceremony ceremony, CeremonyInstance instance, @Nullable Entity initiator) {
        super(tile);
        this.ceremony = ceremony;
        this.instance = instance;
        this.initiator = initiator;
    }

    StateStartup(TotemBaseBlockEntity tile) {
        super(tile);
    }

    @Override
    public boolean canAcceptMusic(MusicInstrument instr) {
        return musicHandler.canAcceptMusic(instr);
    }

    @Override
    public MusicResult acceptMusic(MusicInstrument instr, int amount, Vec3 from, @Nullable Entity entity) {
        var result = musicHandler.acceptMusic(instr, amount, from, entity);
        if(result.isSuccess()) {
            Totemic.platform().sendPacktToPlayersTrackingChunk(tile.getLevel(), ChunkPos.containing(tile.getBlockPos()),
                    new ClientboundPacketStartupMusic(tile.getBlockPos(), instr, musicHandler.getMusicAmount(instr)));
            tile.setChanged();
        }
        return result;
    }

    @Override
    public int getPriority() {
        return MusicAcceptor.CEREMONY_PRIORITY;
    }

    @Override
    public void tick() {
        Level world = tile.getLevel();
        BlockPos pos = tile.getBlockPos();

        if(!world.isClientSide()) { //server side
            if(musicHandler.getTotalMusic() >= ceremony.getMusicNeeded()) {
                if(instance.canStartEffect(world, pos, this) && Totemic.platform().events().fireCeremonyStartupSuccess(world, pos, ceremony, instance, this))
                    startCeremony();
                else
                    failCeremony(); //TODO: For 1.21.5, this else branch should be removed, to give the canStartEffect method the option to hold off on starting the effect without completely aborting the Ceremony
            }
            else if(time >= ceremony.getAdjustedMaxStartupTime(world.getDifficulty())) {
                Totemic.platform().events().fireCeremonyStartupFail(world, pos, ceremony, instance, this);
                instance.onStartupFail(world, pos, this);
                failCeremony();
            }
            else {
                startupTick(world, pos);
            }
        }
        else { //client side
            //do not change state based on time on the client side (to account for TPS lag)
            startupTick(world, pos);
            CeremonyHUD.INSTANCE.setActiveTotem(tile);
        }

        time++;
    }

    private void startupTick(Level world, BlockPos pos) {
        if(Totemic.platform().events().fireCeremonyStartupTick(world, pos, ceremony, instance, this))
            instance.onStartup(world, pos, this);
    }

    @Override
    public int getTime() {
        return time;
    }

    @Override
    public int getTotalMusic() {
        return Math.min(musicHandler.getTotalMusic(), ceremony.getMusicNeeded());
    }

    @Override
    public int getMusic(MusicInstrument instrument) {
        return musicHandler.getMusicAmount(instrument);
    }

    public void setMusic(MusicInstrument instrument, int amount) {
        musicHandler.setMusicAmount(instrument, amount);
    }

    @Override
    public int getAnalogOutputSignal() {
        return (getTotalMusic() * Redstone.SIGNAL_MAX) / ceremony.getMusicNeeded();
    }

    @Override
    public Optional<Player> getInitiatingPlayer() {
        if(initiator instanceof Player player)
            return Optional.of(player);
        else
            return Optional.empty();
    }

    @Override
    public Optional<Entity> getInitiator() {
        return Optional.ofNullable(initiator);
    }

    @Override
    public void failCeremony() {
        if(tile.getLevel().isClientSide())
            return;
        MiscUtil.spawnAlwaysVisibleServerParticles(ParticleTypes.LARGE_SMOKE, tile.getLevel(), getPosition(), 16, new Vec3(0.6, 0.5, 0.6), 0.0);
        tile.setTotemState(new StateTotemEffect(tile));
    }

    @Override
    public void startCeremony() {
        if(tile.getLevel().isClientSide())
            return;
        MiscUtil.spawnAlwaysVisibleServerParticles(ParticleTypes.HAPPY_VILLAGER, tile.getLevel(), getPosition(), 16, new Vec3(0.6, 0.5, 0.6), 1.0);
        tile.setTotemState(new StateCeremonyEffect(tile, ceremony, instance, initiator));
        TotemicEntityUtil.getPlayersIn(tile.getLevel(), TotemicEntityUtil.getAABBAround(tile.getBlockPos(), ADVANCEMENT_TRIGGER_RANGE))
            .forEach(player -> ModCriteriaTriggers.PERFORM_CEREMONY.trigger((ServerPlayer) player, ceremony));
    }

    public Ceremony getCeremony() {
        return ceremony;
    }

    public CeremonyInstance getCeremonyInstance() {
        return instance;
    }

    @Override
    byte getID() {
        return ID;
    }

    @Override
    void save(ValueOutput out) {
        out.store("Ceremony", TotemicAPI.get().registry().ceremonies().byNameCodec(), ceremony);
        musicHandler.save(out.childrenList("Music"));
        out.putInt("Time", time);
        //For simplicity, we won't save the initiator, since on loading, the block entity's level will be null.
    }

    @Override
    void load(ValueInput in) {
        var optCer = in.read("Ceremony", TotemicAPI.get().registry().ceremonies().byNameCodec());
        if(optCer.isPresent())
            ceremony = optCer.get();
        else {
            Totemic.logger.error("Unknown Ceremony: '{}'", in.getStringOr("Ceremony", "<missing>"));
            tile.setTotemState(new StateTotemEffect(tile));
            return;
        }
        instance = ceremony.createInstance();
        musicHandler.load(in.childrenListOrEmpty("Music")); // TODO: since the new serialization format for is incompatible with the one from 1.21.1, test what happens when attempting to deserialize the old format
        time = in.getIntOr("Time", 0);
    }
}
