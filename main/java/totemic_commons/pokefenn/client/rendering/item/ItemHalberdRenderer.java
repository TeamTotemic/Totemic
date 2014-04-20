package totemic_commons.pokefenn.client.rendering.item;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import totemic_commons.pokefenn.client.rendering.model.ModelHalberd;
import totemic_commons.pokefenn.item.ItemHalberd;
import totemic_commons.pokefenn.lib.Textures;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
@SideOnly(Side.CLIENT)
public class ItemHalberdRenderer implements IItemRenderer
{
    private final ModelHalberd modelHalberd;

    public ItemHalberdRenderer()
    {
        modelHalberd = new ModelHalberd();
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

        switch(type)
        {
            case ENTITY:
            {
                render(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
                return;
            }
            case EQUIPPED:
            {
                render(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
                return;
            }
            case EQUIPPED_FIRST_PERSON:
            {
                render(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
                return;
            }
            case INVENTORY:
            {
                render(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
                return;
            }
            default:
            {
            }
        }
    }

    private void render(float x, float y, float z, float x2, float y2, float z2)
    {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);

        // Scale, Translate, Rotate
        GL11.glTranslatef(x + 0.3F, y + 1.1F, z - 0.7F);
        //GL11.glScalef(0.07F, 0.07F, 0.07F);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);


        // Bind texture
        //FMLClientHandler.instance().getClient().renderEngine.bindTexture(Textures.TEXTURE_TOTEM_SOCKET);

        modelHalberd.render((Entity) null, 0.0F, 0.0F, 0F, 0.0F, 0.0F, 0.0625F);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

}

