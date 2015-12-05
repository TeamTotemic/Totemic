package totemic_commons.pokefenn.client.rendering.item;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import totemic_commons.pokefenn.client.rendering.model.ModelTotemPole;
import totemic_commons.pokefenn.lib.Resources;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
@SideOnly(Side.CLIENT)
public class ItemTotemPoleRenderer implements IItemRenderer
{
    private final ModelTotemPole modelTotemPole;

    public ItemTotemPoleRenderer()
    {
        modelTotemPole = new ModelTotemPole();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.4F, 1.4F, 0.3F);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        GL11.glScalef(0.07F, 0.07F, 0.07F);
        Minecraft.getMinecraft().renderEngine.bindTexture(Resources.getTotemPole(item.getItemDamage()));

        this.modelTotemPole.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 1);

        GL11.glPopMatrix();
    }
}
