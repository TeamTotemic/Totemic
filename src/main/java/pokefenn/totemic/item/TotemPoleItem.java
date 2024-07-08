package pokefenn.totemic.item;

import java.util.Optional;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemWoodType;
import pokefenn.totemic.block.totem.TotemPoleBlock;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModDataComponents;

public class TotemPoleItem extends BlockItem {
    public TotemPoleItem(TotemPoleBlock block, Properties props) {
        super(block, props);
    }

    public static TotemWoodType getWoodType(ItemStack stack) {
        final var woodTypeRegistry = TotemicAPI.get().registry().woodTypes();
        return Optional.ofNullable(stack.getTag())
                .map(tag -> tag.getString(POLE_WOOD_KEY))
                .filter(str -> !str.isEmpty())
                .map(ResourceLocation::tryParse)
                .map(woodTypeRegistry::get)
                .orElseGet(ModContent.oak);
    }

    public static TotemCarving getCarving(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.CARVING, ModContent.none.get());
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        var woodType = getWoodType(stack);
        return "block." + woodType.getRegistryName().toLanguageKey() + "_totem_pole";
    }

    @Override
    public Component getName(ItemStack stack) {
        var carving = getCarving(stack);
        return Component.translatable(getDescriptionId(stack), carving.getDisplayName());
    }
}
