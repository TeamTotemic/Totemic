package pokefenn.totemic.datafix;

import static pokefenn.totemic.Totemic.logger;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.datafix.IFixableData;
import net.minecraft.world.chunk.NibbleArray;

public class TotemWoodToTileEntity implements IFixableData
{
    @Override
    public int getFixVersion()
    {
        return 1020;
    }

    @Override
    public NBTTagCompound fixTagCompound(NBTTagCompound compound)
    {
        NBTTagCompound level = compound.getCompoundTag("Level");
        NBTTagList tiles = level.getTagList("TileEntities", 10);
        NBTTagList sections = level.getTagList("Sections", 10);

        for(NBTBase t: tiles)
        {
            NBTTagCompound tile = (NBTTagCompound) t;
            String id = tile.getString("id");
            if("totemic:totem_base".equals(id) || "totemic:totem_pole".equals(id))
            {
                int x = tile.getInteger("x");
                int y = tile.getInteger("y");
                int z = tile.getInteger("z");

                NBTTagCompound section = getSectionForYCoord(sections, y);
                NibbleArray dataArr = new NibbleArray(section.getByteArray("Data"));

                int arrX = x & 15;
                int arrY = y & 15;
                int arrZ = z & 15;

                int meta = dataArr.get(arrX, arrY, arrZ);
                tile.setByte("wood", (byte) meta);
                dataArr.set(arrX, arrY, arrZ, EnumFacing.NORTH.getHorizontalIndex());

                logger.debug("Moved wood type from metadata to tile entity: {} at ({}, {}, {})", id, x, y, z);
            }
        }

        return compound;
    }

    private NBTTagCompound getSectionForYCoord(NBTTagList sections, int y)
    {
        int sectionY = y >> 4;
        for(NBTBase s: sections)
        {
            NBTTagCompound section = (NBTTagCompound) s;
            if(section.getInteger("Y") == sectionY)
                return section;
        }
        logger.warn("No section present for y coordinate {}", y);
        return new NBTTagCompound();
    }
}
