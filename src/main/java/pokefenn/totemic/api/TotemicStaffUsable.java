package pokefenn.totemic.api;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.ObjectHolder;

/**
 * An interface for blocks to react when right-clicked with a Totemic Staff.
 *
 * @deprecated Override your block's {@link BlockBehaviour#use use} method instead and test for the Totemic Staff
 * there (possibly using an {@link ObjectHolder} to get an instance of the Totemic Staff item).
 */
@Deprecated
public interface TotemicStaffUsable
{
    /**
     * Gets called when a player right-clicks the block with a Totemic Staff.
     */
    InteractionResult onTotemicStaffRightClick(UseOnContext context);
}
