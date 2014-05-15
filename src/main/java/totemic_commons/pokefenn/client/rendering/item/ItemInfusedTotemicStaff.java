package totemic_commons.pokefenn.client.rendering.item;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import totemic_commons.pokefenn.client.rendering.model.ModelTotemicStaff;
import totemic_commons.pokefenn.lib.Textures;

@SideOnly(Side.CLIENT)
public class ItemInfusedTotemicStaff implements IItemRenderer
{

    private final ModelTotemicStaff modelTotemicStaff;

    public ItemInfusedTotemicStaff()
    {
        modelTotemicStaff = new ModelTotemicStaff();
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
                renderTotemicStaff(0.4F, -0.3F, 0.3F, 0F, 0F);
                return;
            }
            case EQUIPPED:
            {
                renderTotemicStaff(0.1F, 0.8F, 1.0F, 1F, 0.5F);
                return;
            }
            case EQUIPPED_FIRST_PERSON:
            {
                renderTotemicStaff(0.6F, 0.2F, 1.0F, 0F, 0F);
                return;
            }
            case INVENTORY:
            {
                renderTotemicStaff(0.4F, -0.48F, 0.85F, 0F, 0F);
                return;
            }
            default:
            {
            }
        }
    }

    private void renderTotemicStaff(float x, float y, float z, float x2, float z2)
    {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);

        // Scale, Translate, Rotate
        GL11.glRotatef(-18F, 0.1F, -1.0F, 0.1F);
        GL11.glTranslatef(x, y + 0.8F, z);
        GL11.glScalef(x + 0.4F + x2, 1.24F + y, z + 0.4F + z2);


        // Bind texture
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(Textures.TEXTURE_INFUSED_TOTEMIC_STAFF);

        // Render
        modelTotemicStaff.render();

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

}
