package pokefenn.totemic.block.totem.entity;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.api.music.MusicInstrument;

public final class StateCeremonyEffect extends TotemState implements CeremonyEffectContext {
    static final byte ID = 3;

    private Ceremony ceremony;
    private CeremonyInstance instance;
    private Entity initiator;

    private int time = 0;

    StateCeremonyEffect(TotemBaseBlockEntity tile, Ceremony ceremony, CeremonyInstance instance, Entity initiator) {
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
    public MusicResult acceptMusic(MusicInstrument instr, int amount, double x, double y, double z, @Nullable Entity entity) {
        return MusicResult.FAILURE;
    }

    @Override
    public void tick() {
        Level world = tile.getLevel();
        BlockPos pos = tile.getBlockPos();

        instance.effect(world, pos, this);
        time++;

        if(!world.isClientSide) {
            if(time >= instance.getEffectTime()) {
                tile.setTotemState(new StateTotemEffect(tile));
            }
        }
        else {
            //Due to network delay, we want to avoid ticking instant ceremonies more than once on the client side
            if(instance.getEffectTime() == 0)
                tile.setTotemState(new StateTotemEffect(tile));
        }
    }

    @Override
    public int getTime() {
        return time;
    }

    @Override
    public Entity getInitiator() {
        return initiator;
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
        time = tag.getInt("Time");
    }
}
