package totemic_commons.pokefenn.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

/**
 * Simple ItemBlock that can be used for blocks with multiple metadatas
 */
public class ItemBlockVariants extends ItemBlock
{
    public ItemBlockVariants(Block block)
    {
        super(block);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage)
    {
        return damage;
    }
}
