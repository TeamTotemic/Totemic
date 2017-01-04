package totemic_commons.pokefenn.totem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.totem.TotemBase;
import totemic_commons.pokefenn.api.totem.TotemEffectPotion;

public class TotemEffectCow extends TotemEffectPotion
{
    public TotemEffectCow(String name)
    {
        super(name, MobEffects.RESISTANCE);
    }

    @Override
    protected int getAmplifier(World world, BlockPos pos, TotemBase totem, int repetition)
    {
        return 1 + super.getAmplifier(world, pos, totem, repetition);
    }

    @Override
    protected int getAmplifierForMedicineBag(World world, EntityPlayer entity, int charge)
    {
        return 1 + super.getAmplifierForMedicineBag(world, entity, charge);
    }

    @Override
    protected void applyTo(boolean isMedicineBag, EntityPlayer player, int time, int amplifier)
    {
        super.applyTo(isMedicineBag, player, time, amplifier);
        player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, time, 0, true, false));
    }
}
