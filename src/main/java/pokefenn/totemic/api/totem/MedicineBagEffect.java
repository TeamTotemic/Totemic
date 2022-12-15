package pokefenn.totemic.api.totem;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * An effect that can be applied from a Medicine Bag.
 * <p>
 * Usually this interface will be implemented by {@link TotemEffect} subclasses, but a MedicineBagEffect can also be separate from a TotemEffect.
 */
public interface MedicineBagEffect {
    /**
     * @return the time in ticks between applications of the Medicine Bag effect.
     */
    int getInterval();

    /**
     * Applies the Medicine Bag effect to the given player.
     * @param player      the player carrying the Medicine Bag
     * @param medicineBag the Medicine Bag item stack the effect originates from
     * @param charge      the time in ticks until the Medicine Bag is depleted, or -1 if it is a Creative Medicine Bag
     */
    void medicineBagEffect(Player player, ItemStack medicineBag, int charge);
}
