package totemic_commons.pokefenn.client.rendering.item;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import totemic_commons.pokefenn.client.rendering.model.ModelTotemBase;
import totemic_commons.pokefenn.lib.Resources;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
@SideOnly(Side.CLIENT)
public class ItemTotemBaseRenderer implements IItemRenderer
{

    private final ModelTotemBase modelTotemBase;

    public ItemTotemBaseRenderer()
    {
        modelTotemBase = new ModelTotemBase();
    }

    @Override
    public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type)
    {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper)
    {
        return true;
    }

    @Override
    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.4F, 1.4F, 0.3F);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        GL11.glScalef(0.07F, 0.07F, 0.07F);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(Resources.TEXTURE_TOTEM_BASE);

        this.modelTotemBase.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 1);

        GL11.glPopMatrix();
    }
}
