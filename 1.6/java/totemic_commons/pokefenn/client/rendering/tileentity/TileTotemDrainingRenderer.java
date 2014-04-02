package totemic_commons.pokefenn.client.rendering.tileentity;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import totemic_commons.pokefenn.client.rendering.model.ModelTotemDraining;
import totemic_commons.pokefenn.lib.Textures;
import totemic_commons.pokefenn.tileentity.TileTotemDraining;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 09/02/14
 * Time: 21:44
 */
public class TileTotemDrainingRenderer extends TileEntitySpecialRenderer
{


    private final ModelTotemDraining modelTotemDraining = new ModelTotemDraining();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick)
    {
        if (tileEntity instanceof TileTotemDraining)
        {

            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glPushMatrix();

            // Scale, Translate, Rotate
            GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
            GL11.glScalef(0.09F, 0.0625F, 0.09F);
            //GL11.glRotatef(240F, 1F, 1F, 1F);

            // Bind texture
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(Textures.TEXTURE_TOTEM_DRAINING);

            // Render
            modelTotemDraining.render();

            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
    }
}
