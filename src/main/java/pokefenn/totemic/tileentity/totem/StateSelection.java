package pokefenn.totemic.tileentity.totem;

import static pokefenn.totemic.Totemic.logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import pokefenn.totemic.api.TotemicRegistries;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.music.MusicInstrument;

public final class StateSelection extends TotemState
{
    static final int ID = 1;

    private static final int ACTUAL_MAX_SELECTORS =
            TotemicRegistries.ceremonies().getValues().stream()
                .mapToInt(cer -> cer.getSelectors().size())
                .max()
                .getAsInt();

    private final List<MusicInstrument> selectors = new ArrayList<>(ACTUAL_MAX_SELECTORS);
    private Entity initiator;
    private int time = 0; //Time since last selection

    StateSelection(TileTotemBase tile)
    {
        super(tile);
    }

    StateSelection(TileTotemBase tile, @Nullable Entity initiator, MusicInstrument firstSelector)
    {
        this(tile);
        this.initiator = initiator;
        addSelector(initiator, firstSelector);
    }

    @Override
    public void update()
    {
        if(time++ >= 60 * 20)
            tile.setState(new StateTotemEffect(tile));
    }

    @Override
    boolean canSelect()
    {
        return true;
    }

    @Override
    void addSelector(@Nullable Entity entity, MusicInstrument instr)
    {
        BlockPos pos = tile.getPos();
        WorldServer world = (WorldServer) tile.getWorld();

        world.spawnParticle(EnumParticleTypes.NOTE, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 6, 0.5, 0.5, 0.5, 0.0);
        selectors.add(instr);
        time = 0;
        tile.markDirty();

        if(selectors.size() >= Ceremony.MIN_SELECTORS)
        {
            Optional<Ceremony> match = TotemicRegistries.ceremonies().getValues().stream()
                .filter(this::selectorsMatch)
                .findAny();

            if(match.isPresent())
            {
                world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16, 0.7D, 0.5D, 0.7D, 0.0D);
                tile.setState(new StateStartup(tile, initiator, match.get()));
            }
            else if(selectors.size() >= ACTUAL_MAX_SELECTORS) //No match found - only reset if the maximum number of selectors is reached
            {
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16, 0.6D, 0.5D, 0.6D, 0.0D);
                tile.setState(new StateTotemEffect(tile));
            }
        }
    }

    private boolean selectorsMatch(Ceremony cer)
    {
        return cer.getSelectors().equals(selectors);
    }

    @Override
    public boolean addMusic(MusicInstrument instr, int amount)
    {
        return false;
    }

    @Override
    int getID()
    {
        return ID;
    }

    @Override
    void writeToNBT(NBTTagCompound tag)
    {
        NBTTagList selectorsTag = new NBTTagList();
        for(MusicInstrument instr: selectors)
            selectorsTag.appendTag(new NBTTagString(instr.getRegistryName().toString()));
        tag.setTag("selectors", selectorsTag);
    }

    @Override
    void readFromNBT(NBTTagCompound tag)
    {
        NBTTagList selectorsTag = tag.getTagList("selectors", 8);
        for(int i = 0; i < selectorsTag.tagCount(); i++)
        {
            MusicInstrument instr = TotemicRegistries.instruments().getValue(new ResourceLocation(selectorsTag.getStringTagAt(i)));
            if(instr != null)
                selectors.add(instr);
            else
                logger.warn("Unknown music instrument: {}", selectorsTag.getStringTagAt(i));
        }
    }

    public List<MusicInstrument> getSelectors()
    {
        return Collections.unmodifiableList(selectors);
    }
}
