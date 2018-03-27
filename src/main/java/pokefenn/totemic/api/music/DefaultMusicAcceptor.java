package pokefenn.totemic.api.music;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
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
    private final Object2IntMap<MusicInstrument> music = new Object2IntOpenHashMap<>(TotemicRegistries.instruments().getEntries().size());
    private int totalMusic = 0;

    /**
     * Accepts and stores music from the given instrument, up to the maximum specified by the instrument.
     * @return {@code true} if any music was accepted.
     */
    @Override
    public boolean acceptMusic(MusicInstrument instr, int amount, double x, double y, double z, @Nullable Entity entity)
    {
        int oldVal = music.getInt(instr);
        int newVal = Math.min(oldVal + amount, instr.getMusicMaximum());
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
    public int getMusicAmount(MusicInstrument instr)
    {
        return music.getInt(instr);
    }

    /**
     * Sets the amount of music for the given instrument. This method does not check if the amount exceeds the maximum.
     */
    public void setMusicAmount(MusicInstrument instr, int amount)
    {
        int oldVal = music.getInt(instr);
        if(amount != oldVal)
        {
            music.put(instr, amount);
            totalMusic += (amount - oldVal);
        }
    }

    /**
     * @return the total amount of music stored from all instruments.
     */
    public int getTotalMusic()
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
        public NBTBase writeNBT(Capability<MusicAcceptor> capability, MusicAcceptor instance, EnumFacing side)
        {
            if(!(instance instanceof DefaultMusicAcceptor))
                throw new IllegalArgumentException("Can only write instances of DefaultMusicAcceptor");
            DefaultMusicAcceptor acceptor = (DefaultMusicAcceptor) instance;
            NBTTagCompound nbt = new NBTTagCompound();

            for(Entry<MusicInstrument> entry: acceptor.music.object2IntEntrySet())
                nbt.setInteger(entry.getKey().getRegistryName().toString(), entry.getIntValue());
            return nbt;
        }

        @Override
        public void readNBT(Capability<MusicAcceptor> capability, MusicAcceptor instance, EnumFacing side, NBTBase nbt)
        {
            if(!(instance instanceof DefaultMusicAcceptor))
                throw new IllegalArgumentException("Can only read instances of DefaultMusicAcceptor");
            DefaultMusicAcceptor acceptor = (DefaultMusicAcceptor) instance;
            NBTTagCompound tag = (NBTTagCompound) nbt;

            acceptor.music.clear();
            acceptor.totalMusic = 0;
            for(String key: tag.getKeySet())
            {
                MusicInstrument instr = TotemicRegistries.instruments().getValue(new ResourceLocation(key));
                if(instr != null)
                {
                    int amount = tag.getInteger(key);
                    acceptor.music.put(instr, amount);
                    acceptor.totalMusic += amount;
                }
                else
                    LogManager.getLogger(Storage.class).warn("Unknown music instrument: {}", key);
            }
        }
    }
}
