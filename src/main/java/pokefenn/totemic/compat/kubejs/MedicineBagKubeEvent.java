package pokefenn.totemic.compat.kubejs;

import dev.latvian.mods.kubejs.player.KubePlayerEvent;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import pokefenn.totemic.api.event.MedicineBagEffectEvent;
import pokefenn.totemic.api.totem.MedicineBagEffect;
import pokefenn.totemic.api.totem.TotemCarving;

@Info("""
    This event is fired every time a MedicineBagEffect is applied.

    When canceled, MedicineBagEffect#medicineBagEffect will not be called, but charge will still be drained from
    the Medicine Bag.
    The amount of charge to be drained can be modified.

    Note: This event is currently fired multiple times if a PortableTotemCarving contains multiple
    MedicineBagEffects (like the Cow and Ocelot carvings), and not fired at all for PortableTotemCarvings with no
    effects (like the 'none' carving). This is expected to change in the future.
    """)
public class MedicineBagKubeEvent implements KubePlayerEvent {
    private final MedicineBagEffectEvent event;

    public MedicineBagKubeEvent(MedicineBagEffectEvent event) {
        this.event = event;
    }

    @Info("the MedicineBagEffect that is being applied")
    public MedicineBagEffect getEffect() {
        return event.getEffect();
    }

    @Info("the TotemCarving that the effect belongs to")
    public TotemCarving getCarving() {
        return event.getCarving();
    }

    @Override
    public Player getEntity() {
        return event.getPlayer();
    }

    @Info("the Medicine Bag item stack the effect originates from")
    public ItemStack getMedicineBag() {
        return event.getMedicineBag();
    }

    @Info("the Medicine Bag's charge before the application of the effect, or -1 if it is a Creative Medicine Bag")
    public int getCharge() {
        return event.getCharge();
    }

    @Info("how much charge to deduct from the Medicine Bag. By default, this is equal to effect.interval.")
    public int getChargeToDeduct() {
        return event.getChargeToDeduct();
    }


    @Info("Modifies how much charge will be deducted from the Medicine Bag.\n"
            + "Will be ignored for Creative Medicine Bags.")
    public void setChargeToDeduct(int chargeToDeduct) {
        event.setChargeToDeduct(chargeToDeduct);
    }

    @Info("true if the item is a Creative Medicine Bag as opposed to a regular Medicine Bag")
    public boolean isCreativeMedicineBag() {
        return event.isCreativeMedicineBag();
    }
}
