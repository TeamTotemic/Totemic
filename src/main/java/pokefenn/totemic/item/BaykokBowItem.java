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
        var pulling = new ResourceLocation("pulling");
        var pull = new ResourceLocation("pull");
        ItemProperties.register(this, pulling, ItemProperties.getProperty(Items.BOW, pulling));
        ItemProperties.register(this, pull, ItemProperties.getProperty(Items.BOW, pull));
    }

    @Override
    public AbstractArrow customArrow(AbstractArrow arrow) {
        if(arrow.getType() == EntityType.ARROW && ((Arrow) arrow).getColor() <= 0) //Non-tipped arrow
            return InvisibleArrow.copyArrow(arrow);
        else
            return super.customArrow(arrow);
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
