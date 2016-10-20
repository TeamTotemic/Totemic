package totemic_commons.pokefenn.tileentity.totem;

import static totemic_commons.pokefenn.Totemic.logger;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicInstrument;

public class StateStartup extends TotemState
{
    public static final int ID = 2;

    private Ceremony ceremony;
    private int time = 0;
    private final TObjectIntMap<MusicInstrument> music = new TObjectIntHashMap<>(Totemic.api.registry().getInstruments().size(), 0.75F);
    private int totalMusic = 0;
    private final TObjectIntMap<MusicInstrument> timesPlayed = new TObjectIntHashMap<>(Totemic.api.registry().getInstruments().size(), 0.75F);

    public StateStartup(TileTotemBase tile)
    {
        super(tile);
    }

    public StateStartup(TileTotemBase tile, Ceremony ceremony)
    {
        this(tile);
        this.ceremony = ceremony;
    }

    @Override
    public void update()
    {
        World world = tile.getWorld();
        BlockPos pos = tile.getPos();

        if(!world.isRemote)
        {
            if(!canStartCeremony())
            {
                if(time >= ceremony.getAdjustedMaxStartupTime(world.getDifficulty()))
                {
                    ((WorldServer) world).spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16, 0.6D, 0.5D, 0.6D, 0.0D);
                    tile.setState(new StateTotemEffect(tile));
                }
            }
            else
            {
                ((WorldServer) world).spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 24, 0.6D, 0.5D, 0.6D, 1.0D);
                tile.setState(new StateCeremonyEffect(tile, ceremony));
            }
        }
        else
        {
            tile.setCeremonyOverlay();
        }

        time++;
    }

    private boolean canStartCeremony()
    {
        return totalMusic >= ceremony.getMusicNeeded();
    }

    @Override
    public boolean addMusic(MusicInstrument instr, int amount)
    {
        timesPlayed.adjustOrPutValue(instr, 1, 1);
        amount = getDiminishedMusic(instr, amount);

        int oldVal = music.get(instr);
        int newVal = Math.min(oldVal + amount, instr.getMusicMaximum());
        music.put(instr, newVal);
        totalMusic += (newVal - oldVal);
        return newVal > oldVal;
    }

    private int getDiminishedMusic(MusicInstrument instr, int amount)
    {
        if(timesPlayed.get(instr) >= amount)
            return amount * 3 / 4;
        else
            return amount;
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
        tag.setTag("ceremonyMusic", writeInstrumentMap(music));
        tag.setTag("timesPlayed", writeInstrumentMap(timesPlayed));
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

        readInstrumentMap(music, tag.getCompoundTag("ceremonyMusic"));
        totalMusic = 0;
        music.forEachValue(amount -> { totalMusic += amount; return true; });

        readInstrumentMap(timesPlayed, tag.getCompoundTag("timesPlayed"));
    }

    private static NBTTagCompound writeInstrumentMap(TObjectIntMap<MusicInstrument> map)
    {
        NBTTagCompound tag = new NBTTagCompound();
        map.forEachEntry((instr, amount) -> {
            tag.setInteger(instr.getName(), amount);
            return true;
        });
        return tag;
    }

    private static void readInstrumentMap(TObjectIntMap<MusicInstrument> map, NBTTagCompound tag)
    {
        map.clear();
        for(String key: tag.getKeySet())
        {
            MusicInstrument instr = Totemic.api.registry().getInstrument(key);
            if(instr != null)
                map.put(instr, tag.getInteger(key));
            else
                logger.warn("Unknown music instrument: {}", key);
        }
    }
}
