package totemic_commons.pokefenn.tileentity.totem;

import static totemic_commons.pokefenn.Totemic.logger;

import net.minecraft.nbt.NBTTagCompound;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicInstrument;

public final class StateCeremonyEffect extends TotemState
{
    public static final int ID = 3;

    private Ceremony ceremony;
    private int time = 0;

    public StateCeremonyEffect(TileTotemBase tile)
    {
        super(tile);
    }

    public StateCeremonyEffect(TileTotemBase tile, Ceremony ceremony)
    {
        this(tile);
        this.ceremony = ceremony;
    }

    @Override
    public void update()
    {
        ceremony.effect(tile.getWorld(), tile.getPos(), time);

        if(!tile.getWorld().isRemote)
        {
            if(time >= ceremony.getEffectTime())
                tile.setState(new StateTotemEffect(tile));
        }
        else
        {
            //Due to network delay, we want to avoid ticking instant ceremonies more than once on the client side
            if(ceremony.getEffectTime() == Ceremony.INSTANT)
                tile.setState(new StateTotemEffect(tile));

            tile.setCeremonyOverlay();
        }

        time++;
    }

    @Override
    public boolean addMusic(MusicInstrument instr, int amount)
    {
        return false;
    }

    @Override
    public int getID()
    {
        return ID;
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        tag.setString("ceremony", ceremony.getName());
        tag.setInteger("time", time);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        ceremony = Totemic.api.registry().getCeremony(tag.getString("ceremony"));
        if(ceremony == null)
        {
            logger.warn("Unknown ceremony: {}", tag.getString("ceremony"));
            tile.setState(new StateTotemEffect(tile));
        }

        time = tag.getInteger("time");
    }

    public Ceremony getCeremony()
    {
        return ceremony;
    }

    public int getTime()
    {
        return time;
    }
}
