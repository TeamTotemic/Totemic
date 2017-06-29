package pokefenn.totemic.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
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
     * @param pos the position of the clicked block
     * @param player the player who clicked
     * @param hand the hand in which the player holds the Totemic Staff
     * @param facing which face of the block was clicked
     */
    EnumActionResult onTotemicStaffRightClick(World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ);
}
