package pokefenn.totemic.totem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import pokefenn.totemic.api.totem.TotemEffectPotion;

public class TotemEffectCow extends TotemEffectPotion
{
    public TotemEffectCow(String name)
    {
        super(name, true, 6, MobEffects.RESISTANCE, 80, 1);
    }

    @Override
    protected void applyTo(boolean isMedicineBag, EntityPlayer player, int time, int amplifier)
    {
        super.applyTo(isMedicineBag, player, time, amplifier);
        player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, time, 0, true, false));
    }
}
