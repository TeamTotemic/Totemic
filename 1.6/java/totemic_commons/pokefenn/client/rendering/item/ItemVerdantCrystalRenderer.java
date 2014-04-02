package totemic_commons.pokefenn.client.rendering.item;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import totemic_commons.pokefenn.client.rendering.model.ModelChlorophyllCrystal;
import totemic_commons.pokefenn.lib.Textures;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 01/03/14
 * Time: 20:42
 */
@SideOnly(Side.CLIENT)
public class ItemVerdantCrystalRenderer implements IItemRenderer
{

    private final ModelChlorophyllCrystal modelChlorophyllCrystal;

    public ItemVerdantCrystalRenderer()
    {
        modelChlorophyllCrystal = new ModelChlorophyllCrystal();
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
                renderChlorophyllCrystal(1F, 1F, 1F, 0, -1.7F, 0, 0.3F, 0.3F, 0.3F, 1F, 1F, 1F);
                return;
            }
            case EQUIPPED:
            {
                renderChlorophyllCrystal(1F, 1F, 1F, 0.7F, -0.9F, 0.6F, 0.3F, 0.3F, 0.3F, 1F, 1F, 1F);
                return;
            }
            case EQUIPPED_FIRST_PERSON:
            {
                renderChlorophyllCrystal(1F, 1F, 1F, 0.85F, -1.05F, 0.75F, 0.3F, 0.3F, 0.3F, 1F, 1F, 1F);
                return;
            }
            case INVENTORY:
            {
                renderChlorophyllCrystal(1F, 1F, 1F, 0, -1.95F, 0, 0.25F, 0.25F, 0.25F, 1F, 1F, 1F);
                return;
            }
            default:
            {
            }
        }
    }

    private void renderChlorophyllCrystal(float x, float y, float z, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4)
    {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);

        // Scale, Translate, Rotate
        GL11.glRotatef(360, x, y, z);
        GL11.glTranslatef(x2, y2, z2);
        GL11.glScalef(x3, y3, z3);


        // Bind texture
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(Textures.TEXTURE_CHLOROPHYLL_CRYSTAL);

        // Render
        modelChlorophyllCrystal.render();

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

}
