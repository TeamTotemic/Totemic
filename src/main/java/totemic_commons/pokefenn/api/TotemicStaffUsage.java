package totemic_commons.pokefenn.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/*
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
/**
 * To be implemented by blocks that react when they're right-clicked with a Totemic Staff
 */
public interface TotemicStaffUsage
{

    /**
     * Gets called when a player right-clicks the block with a Totemic Staff
     * @param world     the world
     * @param player    the player who clicked
     * @param itemStack the Totemic Staff that was clicked
     */
    public void onTotemicStaffRightClick(World world, int x, int y, int z, EntityPlayer player, ItemStack itemStack);

}
