package pokefenn.totemic.tileentity.totem;

import static pokefenn.totemic.Totemic.logger;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

import javax.annotation.Nullable;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import pokefenn.totemic.advancements.ModCriteriaTriggers;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.TotemicRegistries;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.StartupContext;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.network.NetworkHandler;
import pokefenn.totemic.network.server.PacketCeremonyStartupFull;
import pokefenn.totemic.network.server.PacketCeremonyStartupMusic;

public final class StateStartup extends TotemState implements StartupContext
{
    static final int ID = 2;

    private Ceremony ceremony;
    //private Entity initiator;
    private int time = 0;
    private final Object2IntOpenHashMap<MusicInstrument> music = new Object2IntOpenHashMap<>(TotemicRegistries.instruments().getEntries().size());
    private int totalMusic = 0;
    private final Object2IntOpenHashMap<MusicInstrument> timesPlayed = new Object2IntOpenHashMap<>(TotemicRegistries.instruments().getEntries().size());
    //Weak set used to detect when new players enter the range so we send a synchronization packet
    private final Set<EntityPlayerMP> playersInRange = Collections.newSetFromMap(new WeakHashMap<>());

    StateStartup(TileTotemBase tile)
    {
        super(tile);
    }

    StateStartup(TileTotemBase tile, @Nullable Entity initiator, Ceremony ceremony)
    {
        this(tile);
        this.ceremony = ceremony;
        //this.initiator = initiator;
    }

    @Override
    public void update()
    {
        World world = tile.getWorld();
        BlockPos pos = tile.getPos();

        if(!world.isRemote)
        {
            if(totalMusic >= ceremony.getMusicNeeded())
            {
                if(ceremony.canStartEffect(world, pos, this))
                    startCeremony();
                else
                    failCeremony();
            }
            else if(time >= ceremony.getAdjustedMaxStartupTime(world.getDifficulty()))
            {
                ceremony.onStartupFail(world, pos, this);
                failCeremony();
            }
            else
            {
                ceremony.onStartup(world, pos, this);

                if(time % 20 == 0)
                {
                    //Detect when a new player has moved into range and send an update packet.
                    //We have to use the predicate to also include players in Spectator mode.
                    List<EntityPlayerMP> players = TotemicEntityUtil.getPlayersMPInRange((WorldServer) tile.getWorld(), tile.getPos(), 16, 16, Predicates.alwaysTrue())
                            .collect(ImmutableList.toImmutableList());
                    playersInRange.retainAll(players); //Remove players that went out of range
                    for(EntityPlayerMP player: players)
                    {
                        if(!playersInRange.contains(player))
                        {
                            if(time != 0)
                                NetworkHandler.sendToClient(new PacketCeremonyStartupFull(pos, time, music), player);
                            playersInRange.add(player);
                        }
                    }
                }

                if(time != 0 && time % 100 == 0)
                    NetworkHandler.sendAround(new PacketCeremonyStartupFull(pos, time, music), tile, 16);
            }
        }
        else
        {
            tile.setCeremonyOverlay();
        }

        time++;
    }

    private void failCeremony()
    {
        BlockPos pos = tile.getPos();
        ((WorldServer) tile.getWorld()).spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16, 0.6D, 0.5D, 0.6D, 0.0D);
        tile.setState(new StateTotemEffect(tile));
        tile.getState().update();
    }

    public void startCeremony()
    {
        BlockPos pos = tile.getPos();
        ((WorldServer) tile.getWorld()).spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 24, 0.6D, 0.5D, 0.6D, 1.0D);
        tile.setState(new StateCeremonyEffect(tile, ceremony));
        tile.getState().update();

        TotemicEntityUtil.getPlayersMPInRange((WorldServer) tile.getWorld(), tile.getPos(), 8, 8)
            .forEach(player -> ModCriteriaTriggers.PERFORM_CEREMONY.trigger(player, ceremony));
    }

    @Override
    public boolean acceptMusic(MusicInstrument instr, int amount, double x, double y, double z, @Nullable Entity entity)
    {
        timesPlayed.addTo(instr, 1);
        amount = getDiminishedMusic(instr, amount);

        int oldVal = music.getInt(instr);
        int newVal = Math.min(oldVal + amount, instr.getMusicMaximum());
        spawnParticles(newVal < instr.getMusicMaximum() ? EnumParticleTypes.NOTE : EnumParticleTypes.CLOUD, 6);
        if(newVal == oldVal)
            return false;
        music.put(instr, newVal);
        totalMusic += (newVal - oldVal);
        NetworkHandler.sendAround(new PacketCeremonyStartupMusic(tile.getPos(), instr, newVal), tile, 16);
        tile.markDirty();
        return true;
    }

    private int getDiminishedMusic(MusicInstrument instr, int amount)
    {
        if(timesPlayed.getInt(instr) >= amount)
            return amount * 3 / 4;
        else
            return amount;
    }

    @Override
    int getID()
    {
        return ID;
    }

    @Override
    void writeToNBT(NBTTagCompound tag)
    {
        //TODO: Maybe also save the initiator to NBT if they're a player.
        //However this is problematic since the list of players is generally
        //not yet available when the tile entities are loaded.
        tag.setString("ceremony", ceremony.getRegistryName().toString());
        tag.setInteger("time", time);
        tag.setTag("ceremonyMusic", writeInstrumentMap(music));
        tag.setTag("timesPlayed", writeInstrumentMap(timesPlayed));
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

        readInstrumentMap(music, tag.getCompoundTag("ceremonyMusic"));
        totalMusic = 0;
        for(int i: music.values())
            totalMusic += i;

        readInstrumentMap(timesPlayed, tag.getCompoundTag("timesPlayed"));
    }

    private static NBTTagCompound writeInstrumentMap(Object2IntMap<MusicInstrument> map)
    {
        NBTTagCompound tag = new NBTTagCompound();
        for(Entry<MusicInstrument> entry: map.object2IntEntrySet())
            tag.setInteger(entry.getKey().getRegistryName().toString(), entry.getIntValue());
        return tag;
    }

    private static void readInstrumentMap(Object2IntMap<MusicInstrument> map, NBTTagCompound tag)
    {
        map.clear();
        for(String key: tag.getKeySet())
        {
            MusicInstrument instr = TotemicRegistries.instruments().getValue(new ResourceLocation(key));
            if(instr != null)
                map.put(instr, tag.getInteger(key));
            else
                logger.warn("Unknown music instrument: {}", key);
        }
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

    public void setTime(int time)
    {
        this.time = time;
    }

    @Override
    public int getTotalMusic()
    {
        return totalMusic;
    }

    @Override
    public int getMusic(MusicInstrument instrument)
    {
        return music.getInt(instrument);
    }

    public void handleMusicPacket(PacketCeremonyStartupMusic msg)
    {
        int oldVal = music.getInt(msg.getInstrument());
        music.put(msg.getInstrument(), msg.getAmount());
        totalMusic += (msg.getAmount() - oldVal);
    }

    public void handleFullPacket(PacketCeremonyStartupFull msg)
    {
        time = msg.getStartupTime();

        music.clear();
        totalMusic = 0;
        for(int i = 0; i < msg.getCount(); i++)
        {
            MusicInstrument instr = msg.getInstrument(i);
            int value = msg.getValue(i);
            music.put(instr, value);
            totalMusic += value;
        }
    }
}
