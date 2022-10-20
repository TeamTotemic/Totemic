package pokefenn.totemic.block.totem.entity;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.advancements.criterion.CeremonyTrigger;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.api.ceremony.StartupContext;
import pokefenn.totemic.api.music.DefaultMusicAcceptor;
import pokefenn.totemic.api.music.MusicAcceptor;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.network.ClientboundPacketStartupMusic;
import pokefenn.totemic.network.NetworkHandler;
import pokefenn.totemic.util.MiscUtil;

public final class StateStartup extends TotemState implements StartupContext {
    static final byte ID = 2;

    private Ceremony ceremony;
    private CeremonyInstance instance;
    private Entity initiator;

    private final DefaultMusicAcceptor musicHandler = new DefaultMusicAcceptor();
    private int time = 0;

    StateStartup(TileTotemBase tile, Ceremony ceremony, CeremonyInstance instance, Entity initiator) {
        super(tile);
        this.ceremony = ceremony;
        this.instance = instance;
        this.initiator = initiator;
    }

    StateStartup(TileTotemBase tile) {
        super(tile);
    }

    @Override
    public boolean canAcceptMusic(MusicInstrument instr) {
        return musicHandler.canAcceptMusic(instr);
    }

    @Override
    public boolean acceptMusic(MusicInstrument instr, int amount, double x, double y, double z, @Nullable Entity entity) {
        if(musicHandler.acceptMusic(instr, amount, x, y, z, entity)) {
            NetworkHandler.channel.send(NetworkHandler.nearTile(tile, 16),
                    new ClientboundPacketStartupMusic(tile.getBlockPos(), instr, musicHandler.getMusicAmount(instr)));
            tile.setChanged();
            return true;
        }
        else
            return false;
    }

    @Override
    public int getPriority() {
        return MusicAcceptor.CEREMONY_PRIORITY;
    }

    @Override
    public void tick() {
        Level world = tile.getLevel();
        BlockPos pos = tile.getBlockPos();

        if(!world.isClientSide) {
            if(musicHandler.getTotalMusic() >= ceremony.getMusicNeeded()) {
                if(instance.canStartEffect(world, pos, this))
                    startCeremony();
                else
                    failCeremony();
            }
            else if(time >= ceremony.getAdjustedMaxStartupTime(world.getDifficulty())) {
                instance.onStartupFail(world, pos, this);
                failCeremony();
            }
            else {
                instance.onStartup(world, pos, this);


            }
        }
        else
            instance.onStartup(world, pos, this); //do not change state based on time on the client side (to account for TPS lag)

        time++;
    }

    @Override
    public int getTime() {
        return time;
    }

    @Override
    public int getTotalMusic() {
        return musicHandler.getTotalMusic();
    }

    @Override
    public int getMusic(MusicInstrument instrument) {
        return musicHandler.getMusicAmount(instrument);
    }

    public void setMusic(MusicInstrument instrument, int amount) {
        musicHandler.setMusicAmount(instrument, amount);
    }

    @Override
    public Entity getInitiator() {
        return initiator;
    }

    @SuppressWarnings("resource")
    @Override
    public void failCeremony() {
        if(tile.getLevel().isClientSide)
            return;
        MiscUtil.spawnServerParticles(ParticleTypes.LARGE_SMOKE, tile.getLevel(), getPosition(), 16, new Vec3(0.6, 0.5, 0.6), 0.0);
        tile.setTotemState(new StateTotemEffect(tile));
    }

    @SuppressWarnings("resource")
    @Override
    public void startCeremony() {
        if(tile.getLevel().isClientSide)
            return;
        MiscUtil.spawnServerParticles(ParticleTypes.HAPPY_VILLAGER, tile.getLevel(), getPosition(), 16, new Vec3(0.6, 0.5, 0.6), 1.0);
        tile.setTotemState(new StateCeremonyEffect(tile, ceremony, instance, initiator));
        TotemicEntityUtil.getPlayersInRange(tile.getLevel(), tile.getBlockPos(), 8, 8)
            .forEach(player -> CeremonyTrigger.PERFORM_CEREMONY.trigger((ServerPlayer) player, ceremony));
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
    void save(CompoundTag tag) {
        tag.putString("Ceremony", ceremony.getRegistryName().toString());
        Tag instanceData = instance.serializeNBT();
        if(instanceData != EndTag.INSTANCE)
            tag.put("InstanceData", instanceData);
        tag.put("Music", musicHandler.serializeNBT());
        tag.putInt("Time", time);
        //TODO: Save initiator as well? Do we need the initiator?
    }

    @Override
    void load(CompoundTag tag) {
        var ceremonyName = new ResourceLocation(tag.getString("Ceremony"));
        ceremony = TotemicAPI.get().registry().ceremonies().get(ceremonyName);
        if(ceremony == null) {
            Totemic.logger.error("Unknown Ceremony: {}", ceremonyName);
            tile.setTotemState(new StateTotemEffect(tile));
            return;
        }
        instance = ceremony.createInstance();
        if(tag.contains("InstanceData"))
            instance.deserializeNBT(tag.get("InstanceData"));
        musicHandler.deserializeNBT(tag.getCompound("Music"));
        time = tag.getInt("Time");
    }
}
