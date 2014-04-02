package totemic_commons.pokefenn.client.rendering.tileentity;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import totemic_commons.pokefenn.client.rendering.model.ModelTotemSocket;
import totemic_commons.pokefenn.lib.Textures;
import totemic_commons.pokefenn.tileentity.TileTotemSocket;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 08/02/14
 * Time: 22:23
 */
public class TileTotemSocketRenderer extends TileEntitySpecialRenderer
{

    private final ModelTotemSocket modelTotemSocket = new ModelTotemSocket();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick)
    {
        if (tileEntity instanceof TileTotemSocket)
        {

            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glPushMatrix();

            // Scale, Translate, Rotate
            GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
            GL11.glScalef(0.09F, 0.0625F, 0.09F);
            //GL11.glRotatef(240F, 1.6F, 1.6F, 1.6F);

            // Bind texture
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(Textures.TEXTURE_TOTEM_SOCKET);

            // Render
            modelTotemSocket.render();

            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
    }

}
