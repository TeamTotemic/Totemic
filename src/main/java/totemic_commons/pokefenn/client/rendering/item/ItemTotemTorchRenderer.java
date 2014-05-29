package totemic_commons.pokefenn.client.rendering.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import totemic_commons.pokefenn.client.rendering.model.ModelTotemTorch;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */

@SideOnly(Side.CLIENT)
public class ItemTotemTorchRenderer implements IItemRenderer
{


    private final ModelTotemTorch modelTotemTorch;

    public ItemTotemTorchRenderer()
    {
        modelTotemTorch = new ModelTotemTorch();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return false;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        //modelTotemTorch.re
    }
}
