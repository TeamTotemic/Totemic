package pokefenn.totemic.tile.totem;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.math.IntMath;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import pokefenn.totemic.api.TotemicCapabilities;
import pokefenn.totemic.api.music.MusicAcceptor;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.api.totem.TotemEffectAPI;
import pokefenn.totemic.block.totem.TotemPoleBlock;
import pokefenn.totemic.init.ModTileEntities;

public class TileTotemBase extends BlockEntity {
    private boolean firstTick = true;

    private final List<TotemEffect> totemEffectList = new ArrayList<>(TotemEffectAPI.MAX_POLE_SIZE);
    private final Multiset<TotemEffect> totemEffects = HashMultiset.create(TotemEffectAPI.MAX_POLE_SIZE);
    private int commonTotemEffectInterval = Integer.MAX_VALUE;

    private TotemState state = new StateTotemEffect(this);

    private LazyOptional<MusicAcceptor> musicHandler = LazyOptional.of(() -> this.state);

    public TileTotemBase(BlockPos pos, BlockState state) {
        super(ModTileEntities.totem_base.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState blockState, TileTotemBase tile) {
        if(tile.firstTick) {
            tile.calculateTotemEffects();
            tile.firstTick = false;
        }

        tile.state.tick();
    }

    private void calculateTotemEffects() {
        totemEffectList.clear();
        totemEffects.clear();

        for(int i = 0; i < TotemEffectAPI.MAX_POLE_SIZE; i++) {
            Block block = level.getBlockState(worldPosition.above(i + 1)).getBlock();
            if(block instanceof TotemPoleBlock) {
                TotemEffect effect = ((TotemPoleBlock) block).effect;
                totemEffectList.add(effect);
                totemEffects.add(effect);
            }
            else
                break;
        }

        // Calculate the greatest common divisor of all the intervals of the effects
        commonTotemEffectInterval = totemEffects.elementSet().stream()
                .mapToInt(TotemEffect::getInterval)
                .filter(i -> i != Integer.MAX_VALUE) //Integer.MAX_VALUE is a prime number, so we don't want it in the GCD calculation
                .reduce(IntMath::gcd)
                .orElse(Integer.MAX_VALUE);
    }

    public void onPoleChange() {
        calculateTotemEffects();
    }

    public List<TotemEffect> getTotemEffectList() {
        return totemEffectList;
    }

    public Multiset<TotemEffect> getTotemEffects() {
        return totemEffects;
    }

    public int getPoleSize() {
        return totemEffectList.size();
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

        tag.putByte("State", state.getID());
        state.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);

        if(tag.contains("State", Tag.TAG_ANY_NUMERIC)) {
            state = TotemState.fromID(tag.getByte("State"), this);
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
