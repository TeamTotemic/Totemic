package totemic_commons.pokefenn.client.rendering.tileentity;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import totemic_commons.pokefenn.client.rendering.model.ModelTipi;
import totemic_commons.pokefenn.lib.Resources;
import totemic_commons.pokefenn.tileentity.TileTipi;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class TileTipiRenderer extends TileEntitySpecialRenderer<TileTipi>
{
    private final ModelTipi modelTipi = new ModelTipi();

    @Override
    public void renderTileEntityAt(TileTipi tile, double x, double y, double z, float partialTick, int destroyStage)
    {
        GL11.glPushMatrix();

        if(tile != null) //Block rendering
        {
            GL11.glTranslated(x, y, z);
            GL11.glScalef(2.85F, 2.85F, 2.85F);
            GL11.glTranslatef(0.18F, 1.55F, 0.18F);
            int dir = tile.getBlockMetadata();
            GL11.glRotatef(dir * (-90F), 0F, 1F, 0F);
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        }
        else //Item rendering
        {
            GL11.glTranslatef(0.5F, 0.9F, 0.5F);
            GL11.glScalef(0.67F, 0.67F, 0.67F);
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        }

        bindTexture(Resources.TEXTURE_TIPI);
        this.modelTipi.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
    }

}