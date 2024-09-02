package pokefenn.totemic.item;

import java.util.function.Consumer;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import pokefenn.totemic.client.renderer.TotemicItemRenderer;

public class CustomRenderedBlockItem extends BlockItem {
    public CustomRenderedBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) { //TODO: This method is deprecated, use RegisterClientExtensionsEvent instead
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return TotemicItemRenderer.INSTANCE;
            }
        });
    }
}
