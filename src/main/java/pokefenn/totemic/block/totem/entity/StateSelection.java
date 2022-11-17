package pokefenn.totemic.block.totem.entity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyAPI;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.apiimpl.registry.RegistryApiImpl;
import pokefenn.totemic.client.CeremonyHUD;
import pokefenn.totemic.util.MiscUtil;

public final class StateSelection extends TotemState {
    static final byte ID = 1;

    private StateTotemEffect previousState;
    private final List<MusicInstrument> selectors = new ArrayList<>(CeremonyAPI.MAX_SELECTORS);
    private int time = 0; //Time since last selection

    StateSelection(TotemBaseBlockEntity tile, StateTotemEffect previousState) {
        super(tile);
        this.previousState = previousState;
    }

    StateSelection(TotemBaseBlockEntity tile) {
        this(tile, new StateTotemEffect(tile));
    }

    @Override
    public boolean canSelect() {
        return true;
    }

    @Override
    public void addSelector(@Nonnull Entity entity, MusicInstrument instr) {
        MiscUtil.spawnServerParticles(ParticleTypes.NOTE, tile.getLevel(), getPosition(), 6, new Vec3(0.5, 0.5, 0.5), 0.0);
        selectors.add(instr);
        time = 0;
        tile.setChanged();

        if(selectors.size() >= CeremonyAPI.MIN_SELECTORS) {
            Ceremony match = RegistryApiImpl.getSelectorsToCeremonyMap().get(selectors);
            if(match != null) {
                CeremonyInstance instance = match.createInstance();
                if(instance.canSelect(tile.getLevel(), tile.getBlockPos(), entity)) {
                    MiscUtil.spawnServerParticles(ParticleTypes.FIREWORK, tile.getLevel(), getPosition(), 16, new Vec3(0.6, 0.5, 0.6), 0.0);
                    tile.setTotemState(new StateStartup(tile, match, instance, entity));
                }
                else
                    resetTotemState();
            }
            else if(selectors.size() >= CeremonyAPI.MAX_SELECTORS)
                resetTotemState();
        }
    }

    @Override
    public boolean canAcceptMusic(MusicInstrument instr) {
        return false;
    }

    @Override
    public MusicResult acceptMusic(MusicInstrument instr, int amount, Vec3 from, @Nullable Entity entity) {
        return MusicResult.FAILURE;
    }

    @SuppressWarnings("resource")
    @Override
    public void tick() {
        if(!tile.getLevel().isClientSide) { //do not change state based on time on the client side (to account for TPS lag)
            if(time++ >= 60 * 20)
                tile.setTotemState(previousState);
        }
        else
            CeremonyHUD.INSTANCE.setActiveTotem(tile);
    }

    public List<MusicInstrument> getSelectors() {
        return selectors;
    }

    @Override
    void resetTotemState() {
        MiscUtil.spawnServerParticles(ParticleTypes.LARGE_SMOKE, tile.getLevel(), getPosition(), 16, new Vec3(0.6, 0.5, 0.6), 0.0);
        tile.setTotemState(previousState);
    }

    @Override
    byte getID() {
        return ID;
    }

    @Override
    void save(CompoundTag tag) {
        ListTag selectorsTag = new ListTag();
        for(MusicInstrument instr: selectors)
            selectorsTag.add(StringTag.valueOf(instr.getRegistryName().toString()));
        tag.put("Selectors", selectorsTag);
        tag.putInt("Time", time);
        previousState.save(tag); //Safe since StateTotemEffect only saves the key TotemMusic
    }

    @Override
    void load(CompoundTag tag) {
        selectors.clear();
        ListTag selectorsTag = tag.getList("Selectors", Tag.TAG_STRING);
        for(int i = 0; i < selectorsTag.size(); i++) {
            var name = new ResourceLocation(selectorsTag.getString(i));
            TotemicAPI.get().registry().instruments().getOptional(name)
                    .ifPresentOrElse(selectors::add,
                            () -> Totemic.logger.error("Unknown music instrument: {}", name));
        }
        time = tag.getInt("Time");
        previousState.load(tag); //Safe since StateTotemEffect only saves the key TotemMusic
    }
}
