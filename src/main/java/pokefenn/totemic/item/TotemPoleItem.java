package pokefenn.totemic.item;

import java.util.Optional;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import pokefenn.totemic.api.TotemWoodType;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.block.totem.TotemPoleBlock;
import pokefenn.totemic.init.ModContent;

public class TotemPoleItem extends BlockItem {
    public static final String POLE_WOOD_KEY = "Wood";
    public static final String POLE_CARVING_KEY = TotemKnifeItem.KNIFE_CARVING_KEY;

    public TotemPoleItem(TotemPoleBlock block, Properties props) {
        super(block, props);
    }

    public static TotemWoodType getWoodType(ItemStack stack) {
        final var woodTypeRegistry = TotemWoodType.getWoodTypes();
        return Optional.ofNullable(stack.getTag())
                .map(tag -> tag.getString(POLE_WOOD_KEY))
                .map(ResourceLocation::tryParse)
                .map(ResourceLocation::getPath)
                .flatMap(name -> woodTypeRegistry.stream().filter(wt -> wt.getName().equals(name)).findAny())
                .orElse(TotemWoodType.OAK);
    }

    public static TotemCarving getCarving(ItemStack stack) {
        return TotemKnifeItem.getCarving(stack).orElse(ModContent.none);
    }

    @Override
    public Component getName(ItemStack stack) {
        var woodType = getWoodType(stack);
        var carving = getCarving(stack);
        return Component.translatable("block.totemic." + woodType.getName() + "_totem_pole", carving.getDisplayName());
    }
}
