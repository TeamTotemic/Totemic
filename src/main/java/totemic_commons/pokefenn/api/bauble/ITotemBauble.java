package totemic_commons.pokefenn.api.bauble;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public interface ITotemBauble
{

    /**
     * A way to say how much Totem Efficiency a Bauble will give
     *
     * @param player    The player with the bauble
     * @param itemStack The Itemstack of the item
     * @param world     The world that the player is in
     */
    public int getTotemEfficiency(World world, ItemStack itemStack, EntityPlayer player);
}
