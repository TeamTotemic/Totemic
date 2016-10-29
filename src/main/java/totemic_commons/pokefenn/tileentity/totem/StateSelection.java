package totemic_commons.pokefenn.tileentity.totem;

import static totemic_commons.pokefenn.Totemic.logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicInstrument;

public final class StateSelection extends TotemState
{
    public static final int ID = 1;

    private final List<MusicInstrument> selectors = new ArrayList<>(Ceremony.MAX_SELECTORS);

    public StateSelection(TileTotemBase tile)
    {
        super(tile);
    }

    public StateSelection(TileTotemBase tile, MusicInstrument firstSelector)
    {
        this(tile);
        addSelector(firstSelector);
    }

    @Override
    public void update()
    { }

    @Override
    public boolean canSelect()
    {
        return true;
    }

    @Override
    public void addSelector(MusicInstrument instr)
    {
        BlockPos pos = tile.getPos();
        WorldServer world = (WorldServer) tile.getWorld();

        world.spawnParticle(EnumParticleTypes.NOTE, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 6, 0.5, 0.5, 0.5, 0.0);
        selectors.add(instr);
        tile.markDirty();

        if(selectors.size() >= Ceremony.MIN_SELECTORS)
        {
            Optional<Ceremony> match = Totemic.api.registry().getCeremonies().values().stream()
                .filter(cer -> selectorsMatch(cer.getInstruments()))
                .findAny();

            if(match.isPresent())
            {
                world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16, 0.7D, 0.5D, 0.7D, 0.0D);
                tile.setState(new StateStartup(tile, match.get()));
            }
            else if(selectors.size() >= Ceremony.MAX_SELECTORS) //No match found - only reset if the maximum number of selectors is reached
            {
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16, 0.6D, 0.5D, 0.6D, 0.0D);
                tile.setState(new StateTotemEffect(tile));
            }
        }
    }

    private boolean selectorsMatch(MusicInstrument[] instrs)
    {
        return selectors.size() == instrs.length && selectors.equals(Arrays.asList(instrs));
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
        NBTTagList selectorsTag = new NBTTagList();
        selectors.forEach(instr -> selectorsTag.appendTag(new NBTTagString(instr.getName())));
        tag.setTag("selectors", selectorsTag);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        NBTTagList selectorsTag = tag.getTagList("selectors", 8);
        for(int i = 0; i < selectorsTag.tagCount(); i++)
        {
            MusicInstrument instr = Totemic.api.registry().getInstrument(selectorsTag.getStringTagAt(i));
            if(instr != null)
                selectors.add(instr);
            else
                logger.warn("Unknown music instrument: {}", selectorsTag.getStringTagAt(i));
        }
    }
}
