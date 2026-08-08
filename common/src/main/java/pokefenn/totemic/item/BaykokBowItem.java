package pokefenn.totemic.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import pokefenn.totemic.entity.InvisibleArrow;

public class BaykokBowItem extends BowItem {
    public BaykokBowItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
        final double damageFactor = 1.25;

        if(ammo.getItem() == Items.ARROW) { // regular arrow
            var arrow = new InvisibleArrow(level, shooter, ammo.copyWithCount(1), weapon);
            if(isCrit)
                arrow.setCritArrow(isCrit);
            arrow.setBaseDamage(arrow.getBaseDamage() * damageFactor);
            return arrow;
        }
        else { // tipped or other special kind of arrow
            var projectile = super.createProjectile(level, shooter, weapon, ammo, isCrit);
            if(projectile instanceof AbstractArrow arrow) // the vanilla implementation always returns an AbstractArrow, but with mixins it might not
                arrow.setBaseDamage(arrow.getBaseDamage() * damageFactor);
            return projectile;
        }
    }

    @Override
    public int getEnchantmentValue() {
        return 5;
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }
}
