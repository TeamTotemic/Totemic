package pokefenn.totemic.item;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
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

    // Soft-overrides Neo extension method in ProjectileWeaponItem
    public AbstractArrow customArrow(AbstractArrow arrow, ItemStack projectileStack, ItemStack weaponStack) {
        if(arrow.getType() == EntityType.ARROW && ((Arrow) arrow).getColor() <= 0) //Non-tipped arrow
            return InvisibleArrow.copyArrow(arrow);
        else
            return arrow;
    }

    @Override
    protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
        if(ammo.getItem() == Items.ARROW) {
            var arrow = new InvisibleArrow(level, shooter, ammo.copyWithCount(1), weapon);
            if(isCrit)
                arrow.setCritArrow(isCrit);
            return arrow;
        }
        else
            return super.createProjectile(level, shooter, weapon, ammo, isCrit);
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
