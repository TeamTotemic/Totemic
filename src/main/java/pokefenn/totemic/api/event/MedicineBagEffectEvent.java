package pokefenn.totemic.api.event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import pokefenn.totemic.api.totem.MedicineBagEffect;
import pokefenn.totemic.api.totem.PortableTotemCarving;
import pokefenn.totemic.api.totem.TotemCarving;

/**
 * This event is fired every time a {@link MedicineBagEffect} is applied.
 * <p>
 * When canceled, {@link MedicineBagEffect#medicineBagEffect} will not be called, but charge will still be drained from
 * the Medicine Bag.
 * <p>
 * Note: This event is currently fired multiple times if a {@link PortableTotemCarving} contains multiple
 * MedicineBagEffects (like the Cow and Ocelot carvings), and not fired at all for PortableTotemCarvings with no
 * effects (like the 'none' carving). This is expected to change in the future.
 *
 * @see TotemEffectEvent
 */
public class MedicineBagEffectEvent extends Event implements ICancellableEvent {
    private final MedicineBagEffect effect;
    private final TotemCarving carving;
    private final Player player;
    private final ItemStack medicineBag;
    private final int charge;
    private int chargeToDeduct;

    public MedicineBagEffectEvent(MedicineBagEffect effect, TotemCarving carving, Player player, ItemStack medicineBag, int charge, int chargeToDeduct) {
        this.effect = effect;
        this.carving = carving;
        this.player = player;
        this.medicineBag = medicineBag;
        this.charge = charge;
        this.chargeToDeduct = chargeToDeduct;
    }

    /**
     * @return the MedicineBagEffect that is being applied
     */
    public MedicineBagEffect getEffect() {
        return effect;
    }

    /**
     * @return the TotemCarving that the effect belongs to
     */
    public TotemCarving getCarving() {
        return carving;
    }

    /**
     * @return the player the effect is applied to
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return the Medicine Bag item stack the effect originates from
     */
    public ItemStack getMedicineBag() {
        return medicineBag;
    }

    /**
     * @return the Medicine Bag's charge before the application of the effect, or -1 if it is a Creative Medicine Bag
     */
    public int getCharge() {
        return charge;
    }

    /**
     * @return how much charge to deduct from the Medicine Bag. By default, this is equal to {@code getEffect().getInterval()}.
     */
    public int getChargeToDeduct() {
        return chargeToDeduct;
    }

    /**
     * Modifies how much charge will be deducted from the Medicine Bag.
     * Will be ignored for Creative Medicine Bags.
     */
    public void setChargeToDeduct(int chargeToDeduct) {
        this.chargeToDeduct = chargeToDeduct;
    }

    /**
     * @return true if the item is a Creative Medicine Bag as opposed to a regular Medicine Bag
     */
    public boolean isCreativeMedicineBag() {
        return charge == -1;
    }
}
