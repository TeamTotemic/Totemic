package pokefenn.totemic.entity;

import javax.annotation.Nullable;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import pokefenn.totemic.init.ModEntityTypes;

public class InvisibleArrow extends AbstractArrow {
    public InvisibleArrow(EntityType<? extends InvisibleArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public InvisibleArrow(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(ModEntityTypes.invisible_arrow.get(), owner, level, pickupItemStack, firedFromWeapon);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity pTarget) {
        var owner = getOwner();
        if(owner != null && owner.getType() == ModEntityTypes.baykok.get())
            pTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 1), getEffectSource());
        super.doPostHurtEffects(pTarget);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.ARROW);
    }
}
