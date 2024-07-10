package pokefenn.totemic.item;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import pokefenn.totemic.entity.InvisibleArrow;

public class BaykokBowItem extends BowItem {
    public BaykokBowItem(Properties pProperties) {
        super(pProperties);
    }

    public void registerItemProperties() {
        var pulling = ResourceLocation.withDefaultNamespace("pulling");
        var pull = ResourceLocation.withDefaultNamespace("pull");
        var bowStack = new ItemStack(Items.BOW);
        ItemProperties.register(this, pulling, ItemProperties.getProperty(bowStack, pulling));
        ItemProperties.register(this, pull, ItemProperties.getProperty(bowStack, pull));
    }

    @Override
    public AbstractArrow customArrow(AbstractArrow arrow, ItemStack projectileStack, ItemStack weaponStack) {
        if(arrow.getType() == EntityType.ARROW && ((Arrow) arrow).getColor() <= 0) //Non-tipped arrow
            return InvisibleArrow.copyArrow(arrow);
        else
            return super.customArrow(arrow, projectileStack, weaponStack);
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
