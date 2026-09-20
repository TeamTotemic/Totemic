package pokefenn.totemic.block.totem.entity;

import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.redstone.Redstone;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.client.CeremonyHUD;

public final class StateCeremonyEffect extends TotemState implements CeremonyEffectContext {
    static final byte ID = 3;

    private Ceremony ceremony;
    private CeremonyInstance instance;
    private @Nullable Entity initiator;

    private int time = 0;
    private int effectTime; //currently only used on the client side by the CeremonyHUD

    StateCeremonyEffect(TotemBaseBlockEntity tile, Ceremony ceremony, CeremonyInstance instance, @Nullable Entity initiator) {
        super(tile);
        this.ceremony = ceremony;
        this.instance = instance;
        this.initiator = initiator;
    }

    StateCeremonyEffect(TotemBaseBlockEntity tile) {
        super(tile);
    }

    @Override
    public boolean canAcceptMusic(MusicInstrument instr) {
        return false;
    }

    @Override
    public MusicResult acceptMusic(MusicInstrument instr, int amount, Vec3 from, @Nullable Entity entity) {
        return MusicResult.FAILURE;
    }

    @Override
    public void tick() {
        Level world = tile.getLevel();
        BlockPos pos = tile.getBlockPos();

        var eventResult = Totemic.platform().events().fireCeremonyEffectTick(world, pos, ceremony, instance, this);
        if(eventResult.callEffect())
            instance.effect(world, pos, this);
        time++;

        if(!world.isClientSide()) {
            if(time >= eventResult.effectTime()) {
                tile.setTotemState(new StateTotemEffect(tile));
            }
        }
        else {
            effectTime = eventResult.effectTime(); // TODO: It not ideal to set this every tick just in case it changes. May consider requiring fixing the time at the start of the effect.
            //Due to network delay, we want to avoid ticking instant ceremonies more than once on the client side
            if(eventResult.effectTime() == 0)
                tile.setTotemState(new StateTotemEffect(tile));
            else
                CeremonyHUD.INSTANCE.setActiveTotem(tile);
        }
    }

    @Override
    public int getTime() {
        return time;
    }

    public int getEffectTime() {
        return effectTime;
    }

    @Override
    public int getAnalogOutputSignal() {
        return Redstone.SIGNAL_MAX;
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
    public void endCeremony() {
        tile.setTotemState(new StateTotemEffect(tile));
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
        time = in.getIntOr("Time", 0);
    }
}
