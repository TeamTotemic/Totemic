package totemic_commons.pokefenn.totem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import totemic_commons.pokefenn.api.totem.TotemEffectPotion;

public class TotemEffectBlaze extends TotemEffectPotion
{
    public TotemEffectBlaze(String name)
    {
        super(name, true, 6, 6, MobEffects.FIRE_RESISTANCE, 60);
    }

    @Override
    protected void applyTo(boolean isMedicineBag, EntityPlayer player, int time, int amplifier)
    {
        if(player.isBurning())
            player.heal(2);
        super.applyTo(isMedicineBag, player, time, amplifier);
    }
}
