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

    private LazyOptional<MusicAcceptor> musicHandler = LazyOptional.of(() -> state);

    public TileTotemBase(BlockPos pos, BlockState state) {
        super(ModTileEntities.totem_base, pos, state);
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

    public TotemState getState() {
        return state;
    }

    void setState(TotemState state) {
        if(state != this.state) {
            this.state = state;
            setChanged();
        }
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
