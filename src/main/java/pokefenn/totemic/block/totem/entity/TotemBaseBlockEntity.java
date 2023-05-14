package pokefenn.totemic.block.totem.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.common.math.IntMath;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemWoodType;
import pokefenn.totemic.api.TotemicCapabilities;
import pokefenn.totemic.api.music.MusicAcceptor;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.api.totem.TotemEffectAPI;
import pokefenn.totemic.init.ModBlockEntities;

public class TotemBaseBlockEntity extends BlockEntity {
    private boolean needPoleUpdate = true;

    private TotemWoodType woodType = TotemWoodType.CEDAR;
    private final List<TotemCarving> carvingList = new ArrayList<>(TotemEffectAPI.MAX_POLE_SIZE);
    private Set<TotemCarving> carvingSet = null; //Only needed for Medicine Bags, computed lazily
    private Multiset<TotemEffect> totemEffects = ImmutableMultiset.of();
    private int commonTotemEffectInterval = Integer.MAX_VALUE;

    private TotemState state = new StateTotemEffect(this);

    private LazyOptional<MusicAcceptor> musicHandler = LazyOptional.of(() -> this.state);

    public TotemBaseBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.totem_base.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState blockState, TotemBaseBlockEntity tile) {
        if(tile.needPoleUpdate) {
            tile.calculateTotemEffects();
            tile.needPoleUpdate = false;
        }

        tile.state.tick();
    }

    private void calculateTotemEffects() {
        carvingList.clear();
        carvingSet = null;
        var totemEffectsBuilder = ImmutableMultiset.<TotemEffect>builder();

        for(int i = 0; i < TotemEffectAPI.MAX_POLE_SIZE; i++) {
            var blockEntity = level.getBlockEntity(worldPosition.above(i + 1));
            if(blockEntity instanceof TotemPoleBlockEntity pole) {
                var carving = pole.getCarving();
                carvingList.add(carving);
                totemEffectsBuilder.addAll(carving.getEffects());
            }
            else
                break;
        }

        totemEffects = totemEffectsBuilder.build();

        // Calculate the greatest common divisor of all the intervals of the effects
        commonTotemEffectInterval = totemEffects.elementSet().stream()
                .mapToInt(TotemEffect::getInterval)
                .reduce(IntMath::gcd)
                .orElse(Integer.MAX_VALUE); //if there are no effects
    }

    public void onPoleChange() {
        needPoleUpdate = true;
    }

    public List<TotemCarving> getCarvingList() {
        return carvingList;
    }

    public boolean hasCarving(TotemCarving carving) {
        if(carvingSet == null)
            carvingSet = Set.copyOf(carvingList);
        return carvingSet.contains(carving);
    }

    public Multiset<TotemEffect> getTotemEffects() {
        return totemEffects;
    }

    public int getPoleSize() {
        return carvingList.size();
    }

    public int getCommonTotemEffectInterval() {
        return commonTotemEffectInterval;
    }

    public TotemState getTotemState() {
        return state;
    }

    void setTotemState(TotemState state) {
        if(state != this.state) {
            this.state = state;
            musicHandler.invalidate();
            musicHandler = LazyOptional.of(() -> this.state);
            if(level != null) { //prevent NPE when called during loading
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
                setChanged();
            }
        }
    }

    public void resetTotemState() {
        state.resetTotemState();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putString("Wood", "totemic:" + woodType.getName());
        tag.putByte("State", state.getID());
        state.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        var woodKey = ResourceLocation.tryParse(tag.getString("Wood"));
        var wood = TotemWoodType.getWoodTypes().stream()
                .filter(wt -> wt.getName().equals(woodKey.getPath()))
                .findAny();
        if(wood.isEmpty())
            Totemic.logger.error("Unknown Totem Wood Type: '{}'", tag.getString("Wood"));
        woodType = wood.orElse(TotemWoodType.CEDAR);

        if(tag.contains("State", Tag.TAG_ANY_NUMERIC)) {
            byte id = tag.getByte("State");
            if(id != state.getID())
                state = TotemState.fromID(id, this);
            state.load(tag);
        }
        else
            state = new StateTotemEffect(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        var tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    @Nullable
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        musicHandler.invalidate();
    }

    @SuppressWarnings("null")
    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == TotemicCapabilities.MUSIC_ACCEPTOR)
            return musicHandler.cast();
        else
            return super.getCapability(cap, side);
    }
}
