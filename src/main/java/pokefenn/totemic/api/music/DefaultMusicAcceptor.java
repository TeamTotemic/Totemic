package pokefenn.totemic.api.music;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;

import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import pokefenn.totemic.api.TotemicRegistries;

/**
 * Simple default implementation of {@link MusicAcceptor}.
 *
 * <p>This implementation stores the music it accepts, broken down into the instruments. It will only accept up to the
 * maximum specified by each instrument.
 *
 * <p>The behavior is similar (but simplified) to a Totem Base while starting up a ceremony.
 */
public class DefaultMusicAcceptor implements MusicAcceptor
{
    private final Object2DoubleMap<MusicInstrument> music = new Object2DoubleOpenHashMap<>(TotemicRegistries.instruments().getEntries().size());
    private double totalMusic = 0.0;

    /**
     * Accepts and stores music from the given instrument, up to the maximum specified by the instrument.
     * @return {@code true} if any music was accepted.
     */
    @Override
    public boolean acceptMusic(MusicInstrument instr, double amount, double x, double y, double z, @Nullable Entity entity)
    {
        double oldVal = music.getDouble(instr);
        double newVal = Math.min(oldVal + amount, instr.getMusicMaximum());
        if(newVal != oldVal)
        {
            music.put(instr, newVal);
            totalMusic += (newVal - oldVal);
            return true;
        }
        else
            return false;
    }

    /**
     * @return the amount of music stored from the given instrument.
     */
    public double getMusicAmount(MusicInstrument instr)
    {
        return music.getDouble(instr);
    }

    /**
     * Sets the amount of music for the given instrument. This method does not check if the amount exceeds the maximum.
     */
    public void setMusicAmount(MusicInstrument instr, double amount)
    {
        double oldVal = music.getDouble(instr);
        if(amount != oldVal)
        {
            music.put(instr, amount);
            totalMusic += (amount - oldVal);
        }
    }

    /**
     * @return the total amount of music stored from all instruments.
     */
    public double getTotalMusic()
    {
        return totalMusic;
    }

    /**
     * Capability storage handler for MusicAcceptor.
     */
    public static class Storage implements Capability.IStorage<MusicAcceptor>
    {
        @Override
        @Nullable
        public INBT writeNBT(Capability<MusicAcceptor> capability, MusicAcceptor instance, Direction side)
        {
            if(!(instance instanceof DefaultMusicAcceptor))
                throw new RuntimeException("Cannot serialize to an instance that is not the default implementation");
            DefaultMusicAcceptor acceptor = (DefaultMusicAcceptor) instance;
            CompoundNBT nbt = new CompoundNBT();

            for(Entry<MusicInstrument> entry: acceptor.music.object2DoubleEntrySet())
                nbt.putDouble(entry.getKey().getRegistryName().toString(), entry.getDoubleValue());
            return nbt;
        }

        @Override
        public void readNBT(Capability<MusicAcceptor> capability, MusicAcceptor instance, Direction side, INBT nbt)
        {
            if(!(instance instanceof DefaultMusicAcceptor))
                throw new RuntimeException("Cannot deserialize to an instance that is not the default implementation");
            DefaultMusicAcceptor acceptor = (DefaultMusicAcceptor) instance;
            CompoundNBT tag = (CompoundNBT) nbt;

            acceptor.music.clear();
            acceptor.totalMusic = 0.0;
            for(String key: tag.keySet())
            {
                MusicInstrument instr = TotemicRegistries.instruments().getValue(new ResourceLocation(key));
                if(instr != null)
                {
                    double amount = tag.getDouble(key);
                    acceptor.music.put(instr, amount);
                    acceptor.totalMusic += amount;
                }
                else
                    LogManager.getLogger(Storage.class).warn("Unknown music instrument: {}", key);
            }
        }
    }
}
