package totemic_commons.pokefenn.tileentity.totem;

import net.minecraft.util.ITickable;
import totemic_commons.pokefenn.api.music.MusicAcceptor;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.api.totem.TotemBase;
import totemic_commons.pokefenn.api.totem.TotemEffect;
import totemic_commons.pokefenn.lib.WoodVariant;
import totemic_commons.pokefenn.tileentity.TileTotemic;

public class TileTotemBase extends TileTotemic implements MusicAcceptor, TotemBase, ITickable
{
    public static final int MAX_HEIGHT = 5;
    public static final int MAX_EFFECT_MUSIC = 128;

    private boolean firstTick = true;
    private final TotemEffect[] totemEffects = new TotemEffect[MAX_HEIGHT];
    private int totemPoleHeight = 0;
    private WoodVariant wood;

    @Override
    public void update()
    {
        // TODO Auto-generated method stub

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
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getRepetition(TotemEffect effect)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public TotemEffect[] getEffects()
    {
        // TODO Auto-generated method stub
        return null;
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
