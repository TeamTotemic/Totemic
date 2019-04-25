package pokefenn.totemic.tile.totem;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.block.totem.BlockTotemPole;
import pokefenn.totemic.init.ModTileEntities;

public class TileTotemBase extends TileEntity implements ITickable {
    public static final int MAX_POLE_SIZE = 5;

    private final List<TotemEffect> totemEffectList = new ArrayList<>(MAX_POLE_SIZE);
    private final Multiset<TotemEffect> totemEffects = HashMultiset.create(MAX_POLE_SIZE);

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

        for(int i = 0; i < MAX_POLE_SIZE; i++) {
            Block block = world.getBlockState(pos.up(i + 1)).getBlock();
            if(block instanceof BlockTotemPole) {
                TotemEffect effect = ((BlockTotemPole) block).effect;
                totemEffectList.add(effect);
                totemEffects.add(effect);
            }
            else
                break;
        }
    }
}
