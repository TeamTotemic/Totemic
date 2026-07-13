package pokefenn.totemic.block.totem.entity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.core.HolderLookup.Provider;
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
import pokefenn.totemic.api.ceremony.CeremonyAPI;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.api.music.MusicAcceptor;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.client.CeremonyHUD;
import pokefenn.totemic.neoforge.apiimpl.NeoRegistryApiImpl;
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
    public void addSelector(Entity entity, MusicInstrument instr) {
        MiscUtil.spawnAlwaysVisibleServerParticles(ParticleTypes.NOTE, tile.getLevel(), getPosition(), 6, new Vec3(0.5, 0.5, 0.5), 0.0);
        MiscUtil.spawnAlwaysVisibleServerParticles(ParticleTypes.FIREWORK, tile.getLevel(), getPosition(), 16, new Vec3(0.6, 0.5, 0.6), 0.0);
        selectors.add(instr);
        time = 0;
        tile.setChanged();

        if(selectors.size() >= CeremonyAPI.MIN_SELECTORS) {
            var eventResult = Totemic.platform().events().fireCeremonySelection(tile.getLevel(), tile.getBlockPos(), entity, selectors,
                    NeoRegistryApiImpl.getCeremony(selectors));

            eventResult.ceremony().ifPresentOrElse(ceremony -> {
                CeremonyInstance instance = ceremony.createInstance();
                if(eventResult.skipSelectionCheck() || instance.canSelect(tile.getLevel(), tile.getBlockPos(), entity)) {
                    tile.setTotemState(new StateStartup(tile, ceremony, instance, entity));
                }
                else
                    resetTotemState();
            },
            () -> {
                //if(selectors.size() >= CeremonyAPI.MAX_SELECTORS) // this check is a no-op since MIN_SELECTORS == MAX_SELECTORS
                    resetTotemState();
            });
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

    @Override
    public int getPriority() {
        return MusicAcceptor.CEREMONY_PRIORITY;
    }

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
        MiscUtil.spawnAlwaysVisibleServerParticles(ParticleTypes.LARGE_SMOKE, tile.getLevel(), getPosition(), 16, new Vec3(0.6, 0.5, 0.6), 0.0);
        tile.setTotemState(previousState);
    }

    @Override
    byte getID() {
        return ID;
    }

    @Override
    void save(CompoundTag tag, Provider regsitries) {
        ListTag selectorsTag = new ListTag();
        for(MusicInstrument instr: selectors)
            selectorsTag.add(StringTag.valueOf(instr.getRegistryName().toString()));
        tag.put("Selectors", selectorsTag);
        tag.putInt("Time", time);
        previousState.save(tag, regsitries); //Safe since StateTotemEffect only saves the key TotemMusic
    }

    @Override
    void load(CompoundTag tag, Provider regsitries) {
        selectors.clear();
        ListTag selectorsTag = tag.getList("Selectors", Tag.TAG_STRING);
        for(int i = 0; i < selectorsTag.size(); i++) {
            var name = selectorsTag.getString(i);
            var instr = TotemicAPI.get().registry().instruments().get(ResourceLocation.tryParse(name));
            if(instr != null)
                selectors.add(instr);
            else
                Totemic.logger.error("Unknown music instrument: '{}'", name);
        }
        time = tag.getInt("Time");
        previousState.load(tag, regsitries); //Safe since StateTotemEffect only saves the key TotemMusic
    }
}
