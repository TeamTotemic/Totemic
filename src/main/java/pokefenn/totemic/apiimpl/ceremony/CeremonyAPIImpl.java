package pokefenn.totemic.apiimpl.ceremony;

import java.util.function.BiConsumer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import pokefenn.totemic.api.ceremony.CeremonyAPI;
import pokefenn.totemic.util.BlockUtil;

public enum CeremonyAPIImpl implements CeremonyAPI {
    INSTANCE;

    @Override
    public void forEachBlockIn(Level level, BoundingBox box, BiConsumer<BlockPos, BlockState> action) {
        level.getProfiler().incrementCounter("totemic.forEachBlockIn");
        var startSec = SectionPos.of(BlockUtil.lowerCorner(box));
        var endSec = SectionPos.of(BlockUtil.upperCorner(box));
        //iterate over the chunks
        for(int chunkX = startSec.getX(); chunkX <= endSec.getX(); chunkX++)
            for(int chunkZ = startSec.getZ(); chunkZ <= endSec.getZ(); chunkZ++) {
                if(!level.hasChunk(chunkX, chunkZ))
                    continue;
                var chunk = level.getChunk(chunkX, chunkZ);
                //iterate over the sections inside the chunk
                for(int secY = startSec.getY(); secY <= endSec.getY(); secY++) {
                    var section = chunk.getSection(chunk.getSectionIndexFromSectionY(secY));
                    if(section.hasOnlyAir())
                        continue;
                    var secPos = SectionPos.of(chunkX, secY, chunkZ);
                    //compute iteration boundaries for the current section
                    int secMinX = box.minX() <= secPos.minBlockX() ? 0 : SectionPos.sectionRelative(box.minX());
                    int secMinY = box.minY() <= secPos.minBlockY() ? 0 : SectionPos.sectionRelative(box.minY());
                    int secMinZ = box.minZ() <= secPos.minBlockZ() ? 0 : SectionPos.sectionRelative(box.minZ());
                    int secMaxX = box.maxX() >= secPos.maxBlockX() ? 15 : SectionPos.sectionRelative(box.maxX());
                    int secMaxY = box.maxY() >= secPos.maxBlockY() ? 15 : SectionPos.sectionRelative(box.maxY());
                    int secMaxZ = box.maxZ() >= secPos.maxBlockZ() ? 15 : SectionPos.sectionRelative(box.maxZ());
                    //iterate over the blocks inside the section
                    for(int y = secMinY; y <= secMaxY; y++)
                        for(int z = secMinZ; z <= secMaxZ; z++)
                            for(int x = secMinX; x <= secMaxX; x++) {
                                var pos = secPos.origin().offset(x, y, z);
                                var state = section.getStates().get(x, y, z);
                                action.accept(pos, state);
                            }
                }
            }
    }
}
