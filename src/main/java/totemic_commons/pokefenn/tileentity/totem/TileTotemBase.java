package totemic_commons.pokefenn.tileentity.totem;

import java.util.ArrayList;
import java.util.List;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.music.MusicAcceptor;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.api.totem.TotemBase;
import totemic_commons.pokefenn.api.totem.TotemEffect;
import totemic_commons.pokefenn.block.totem.BlockTotemBase;
import totemic_commons.pokefenn.lib.WoodVariant;
import totemic_commons.pokefenn.tileentity.TileTotemic;

public class TileTotemBase extends TileTotemic implements MusicAcceptor, TotemBase, ITickable
{
    public static final int MAX_HEIGHT = 5;
    public static final int MAX_EFFECT_MUSIC = 128;

    private boolean firstTick = true;

    private TotemState state = new StateTotemEffect(this);

    private int totemPoleHeight = 0;
    private final List<TotemEffect> totemEffectList = new ArrayList<>(MAX_HEIGHT);
    private final TObjectIntMap<TotemEffect> totemEffects = new TObjectIntHashMap<>(Totemic.api.registry().getTotems().size());

    @Override
    public void update()
    {
        if(firstTick)
        {
            calculateTotemEffects();
            firstTick = false;
        }

        if(state != null)
            state.update();
    }

    private void calculateTotemEffects()
    {
        totemPoleHeight = 0;
        totemEffectList.clear();
        totemEffects.clear();

        while(totemPoleHeight < MAX_HEIGHT)
        {
            TileEntity tile = worldObj.getTileEntity(pos.up(totemPoleHeight + 1));
            if(tile instanceof TileTotemPole)
            {
                TotemEffect effect = ((TileTotemPole) tile).getTotemEffect();
                totemEffectList.add(effect);
                totemEffects.adjustOrPutValue(effect, 1, 1);

                totemPoleHeight++;
            }
            else
                break;
        }
    }

    public WoodVariant getWoodType()
    {
        return worldObj.getBlockState(pos).getValue(BlockTotemBase.WOOD);
    }

    public TObjectIntMap<TotemEffect> getTotemEffectMap()
    {
        return totemEffects;
    }

    @Override
    public int getTotemEffectMusic()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getPoleSize()
    {
        return totemPoleHeight;
    }

    @Override
    public int getRepetition(TotemEffect effect)
    {
        return totemEffects.get(effect);
    }

    @Override
    public TotemEffect[] getEffects()
    {
        return totemEffectList.toArray(new TotemEffect[0]);
    }

    @Override
    public int getWoodBonus()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean addMusic(MusicInstrument instr, int amount)
    {
        // TODO Auto-generated method stub
        return false;
    }

}
