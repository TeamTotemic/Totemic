package pokefenn.totemic.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.block.totem.TotemPoleBlock;
import pokefenn.totemic.init.ModContent;

public class TotemPoleItem extends BlockItem { //TODO: Sneak+Scroll behavior, similar to carving knife
    public static final String POLE_CARVING_KEY = TotemKnifeItem.KNIFE_CARVING_KEY;

    public TotemPoleItem(TotemPoleBlock block, Properties props) {
        super(block, props);
    }

    public static TotemCarving getCarving(ItemStack stack) {
        return TotemKnifeItem.getCarving(stack).orElse(ModContent.none);
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(getDescriptionId(stack), getCarving(stack).getDisplayName());
    }
}
