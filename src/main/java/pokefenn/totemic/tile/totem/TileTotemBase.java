package pokefenn.totemic.tile.totem;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.math.IntMath;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.api.totem.TotemEffectAPI;
import pokefenn.totemic.block.totem.BlockTotemPole;
import pokefenn.totemic.init.ModTileEntities;

public class TileTotemBase extends TileEntity implements ITickable {
    private final List<TotemEffect> totemEffectList = new ArrayList<>(TotemEffectAPI.MAX_POLE_SIZE);
    private final Multiset<TotemEffect> totemEffects = HashMultiset.create(TotemEffectAPI.MAX_POLE_SIZE);
    private int commonTotemEffectInterval = Integer.MAX_VALUE;

    public TileTotemBase() {
        super(ModTileEntities.totem_base);
    }

    @Override
    public void tick() {
    }

    @Override
    public void onLoad() {
        calculateTotemEffects();
    }

    private void calculateTotemEffects() {
        totemEffectList.clear();
        totemEffects.clear();

        for(int i = 0; i < TotemEffectAPI.MAX_POLE_SIZE; i++) {
            Block block = world.getBlockState(pos.up(i + 1)).getBlock();
            if(block instanceof BlockTotemPole) {
                TotemEffect effect = ((BlockTotemPole) block).effect;
                totemEffectList.add(effect);
                totemEffects.add(effect);
            }
            else
                break;
        }

        //Calculate the greatest common divisor of all the intervals of the effects
        commonTotemEffectInterval = totemEffects.elementSet().stream()
                .mapToInt(TotemEffect::getInterval)
                .filter(i -> i != Integer.MAX_VALUE) //Integer.MAX_VALUE is a prime number, so we don't want it in the GCD calculation
                .reduce(IntMath::gcd)
                .orElse(Integer.MAX_VALUE);
    }
}
