package pokefenn.totemic.api;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;

/**
 * An interface for blocks that react when right-clicked with a Totemic Staff.
 */
public interface TotemicStaffUsable
{
    /**
     * Gets called when a player right-clicks the block with a Totemic Staff.
     */
    InteractionResult onTotemicStaffRightClick(UseOnContext context);
}
