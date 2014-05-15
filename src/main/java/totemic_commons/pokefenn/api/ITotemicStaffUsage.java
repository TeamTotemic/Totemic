package totemic_commons.pokefenn.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public interface ITotemicStaffUsage
{

    /**
     * @param x
     * @param y
     * @param z
     * @param player
     * @param world
     */
    public void onBasicRightClick(int x, int y, int z, EntityPlayer player, World world);

    /**
     * @param x
     * @param y
     * @param z
     * @param player
     * @param world
     */
    public void onInfusedRightClick(int x, int y, int z, EntityPlayer player, World world);

}
