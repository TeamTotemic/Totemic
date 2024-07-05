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

public class TotemPoleItem extends BlockItem {
    public static final String POLE_WOOD_KEY = "Wood";
    public static final String POLE_CARVING_KEY = TotemKnifeItem.KNIFE_CARVING_KEY;

    public TotemPoleItem(TotemPoleBlock block, Properties props) {
        super(block, props);
    }

    public static TotemWoodType getWoodType(ItemStack stack) {
        final var woodTypeRegistry = TotemicAPI.get().registry().woodTypes();
        return Optional.ofNullable(stack.getTag())
                .map(tag -> tag.getString(POLE_WOOD_KEY))
                .filter(str -> !str.isEmpty())
                .map(ResourceLocation::tryParse)
                .map(woodTypeRegistry::getValue)
                .orElseGet(ModContent.oak);
    }

    public static TotemCarving getCarving(ItemStack stack) {
        return TotemKnifeItem.getCarving(stack).orElseGet(ModContent.none);
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
