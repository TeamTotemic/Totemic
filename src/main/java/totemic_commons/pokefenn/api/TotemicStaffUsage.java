package totemic_commons.pokefenn.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * An interface for blocks that react when they are right-clicked with a Totemic Staff
 */
public interface TotemicStaffUsage
{
    /**
     * Gets called when a player right-clicks the block with a Totemic Staff
     * @param world the world
     * @param player the player who clicked
     * @param itemStack the Totemic Staff that was clicked
     */
    EnumActionResult onTotemicStaffRightClick(World world, BlockPos pos, EntityPlayer player, ItemStack itemStack);
}
