package totemic_commons.pokefenn.client.rendering.tileentity;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import totemic_commons.pokefenn.api.verdant.IVerdantCrystal;
import totemic_commons.pokefenn.client.rendering.model.ModelChlorophyllCrystal;
import totemic_commons.pokefenn.client.rendering.model.ModelTotemIntelligence;
import totemic_commons.pokefenn.lib.Textures;
import totemic_commons.pokefenn.tileentity.TileTotemIntelligence;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 10/03/14
 * Time: 08:27
 */

public class TileTotemIntelligenceRenderer extends TileEntitySpecialRenderer
{

    private final ModelTotemIntelligence modelTotemIntelligence = new ModelTotemIntelligence();
    private final ModelChlorophyllCrystal modelChlorophyllCrystal = new ModelChlorophyllCrystal();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick)
    {
        if (tileEntity instanceof TileTotemIntelligence)
        {

            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glPushMatrix();

            // Scale, Translate, Rotate
            GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
            GL11.glScalef(0.09F, 0.0625F, 0.09F);
            //GL11.glRotatef(0F, 1F, 1F, 1F);

            if(((TileTotemIntelligence) tileEntity).getStackInSlot(0) != null && ((TileTotemIntelligence) tileEntity).getStackInSlot(0).getItem() instanceof IVerdantCrystal)
            {

            }

            // Bind texture
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(Textures.TEXTURE_TOTEM_INTELLIGENCE);

            // Render
            modelTotemIntelligence.render();

            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
    }

}

