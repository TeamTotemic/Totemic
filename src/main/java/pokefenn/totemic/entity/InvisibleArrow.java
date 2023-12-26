package pokefenn.totemic.entity;

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
    private static final ItemStack DEFAULT_ARROW_STACK = new ItemStack(Items.ARROW);

    public InvisibleArrow(EntityType<? extends InvisibleArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, DEFAULT_ARROW_STACK);
    }

    public InvisibleArrow(LivingEntity pShooter, Level pLevel) {
        super(ModEntityTypes.invisible_arrow.get(), pShooter, pLevel, DEFAULT_ARROW_STACK);
    }

    public InvisibleArrow(double pX, double pY, double pZ, Level pLevel) {
        super(ModEntityTypes.invisible_arrow.get(), pX, pY, pZ, pLevel, DEFAULT_ARROW_STACK);
    }

    public static InvisibleArrow copyArrow(AbstractArrow arrow) {
        var invisArrow = new InvisibleArrow(arrow.getX(), arrow.getY(), arrow.getZ(), arrow.level());
        invisArrow.setOwner(arrow.getOwner());
        invisArrow.setBaseDamage(arrow.getBaseDamage());
        return invisArrow;
    }

    @Override
    protected void doPostHurtEffects(LivingEntity pTarget) {
        var owner = getOwner();
        if(owner != null && owner.getType() == ModEntityTypes.baykok.get())
            pTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 1), getEffectSource());
        super.doPostHurtEffects(pTarget);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(Items.ARROW);
    }
}
