package pokefenn.totemic.item.music;

import net.minecraft.item.ItemStack;

public class ItemInfusedFlute extends ItemFlute {
    public ItemInfusedFlute(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }
}
