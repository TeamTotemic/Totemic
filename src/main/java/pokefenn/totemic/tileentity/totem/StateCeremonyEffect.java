package pokefenn.totemic.tileentity.totem;

import static pokefenn.totemic.Totemic.logger;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import pokefenn.totemic.api.TotemicRegistries;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.music.MusicInstrument;

public final class StateCeremonyEffect extends TotemState implements CeremonyEffectContext
{
    static final int ID = 3;

    private Ceremony ceremony;
    private int time = 0;

    StateCeremonyEffect(TileTotemBase tile)
    {
        super(tile);
    }

    StateCeremonyEffect(TileTotemBase tile, Ceremony ceremony)
    {
        this(tile);
        this.ceremony = ceremony;
    }

    @Override
    public void update()
    {
        ceremony.effect(tile.getWorld(), tile.getPos(), this);

        if(!tile.getWorld().isRemote)
        {
            if(time >= ceremony.getEffectTime())
            {
                ceremony.onEffectEnd(tile.getWorld(), tile.getPos(), this);
                tile.setState(new StateTotemEffect(tile));
            }
        }
        else
        {
            //Due to network delay, we want to avoid ticking instant ceremonies more than once on the client side
            if(ceremony.getEffectTime() == 0)
                tile.setState(new StateTotemEffect(tile));

            tile.setCeremonyOverlay();
        }

        time++;
    }

    @Override
    public boolean acceptMusic(MusicInstrument instr, int amount, double x, double y, double z, @Nullable Entity entity)
    {
        spawnParticles(EnumParticleTypes.NOTE, 6);
        return true; //TODO: Implement melody draining
    }

    @Override
    int getID()
    {
        return ID;
    }

    @Override
    void writeToNBT(NBTTagCompound tag)
    {
        tag.setString("ceremony", ceremony.getRegistryName().toString());
        tag.setInteger("time", time);
    }

    @Override
    void readFromNBT(NBTTagCompound tag)
    {
        ceremony = TotemicRegistries.ceremonies().getValue(new ResourceLocation(tag.getString("ceremony")));
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

    @Override
    public int getTime()
    {
        return time;
    }
}
