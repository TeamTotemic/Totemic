package totemic_commons.pokefenn.client.rendering.item;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import totemic_commons.pokefenn.client.rendering.model.ModelTotemDraining;
import totemic_commons.pokefenn.lib.Textures;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 09/02/14
 * Time: 21:48
 */
@SideOnly(Side.CLIENT)
public class ItemTotemDrainingRenderer implements IItemRenderer
{
    private final ModelTotemDraining modelTotemDraining;

    public ItemTotemDrainingRenderer()
    {
        modelTotemDraining = new ModelTotemDraining();
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
        switch (type)
        {
            case ENTITY:
            {
                renderTotemDraining(-0.5F, 0.0F, 0.5F);
                return;
            }
            case EQUIPPED:
            {
                renderTotemDraining(0.0F, 0.0F, 1.0F);
                return;
            }
            case EQUIPPED_FIRST_PERSON:
            {
                renderTotemDraining(0.0F, 0.0F, 1.0F);
                return;
            }
            case INVENTORY:
            {
                renderTotemDraining(0.0F, -0.1F, 1.0F);
                return;
            }
            default:
            {
            }
        }
    }

    private void renderTotemDraining(float x, float y, float z)
    {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);

        // Scale, Translate, Rotate
        GL11.glTranslatef(x + 0.3F, y - 0.2F, z - 0.7F);
        GL11.glScalef(0.07F, 0.07F, 0.07F);
        //GL11.glRotatef(240F, 1.5F, 1.5F, 1.5F);


        // Bind texture
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(Textures.TEXTURE_TOTEM_SOCKET);

        // Render
        modelTotemDraining.render();

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

}
