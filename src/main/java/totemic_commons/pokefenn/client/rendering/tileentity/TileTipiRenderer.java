package totemic_commons.pokefenn.client.rendering.tileentity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
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
        /*GlStateManager.pushMatrix();

        if(tile != null) //Block rendering
        {
            GlStateManager.translate(x+3, y, z);
            GlStateManager.scale(2.85F, 2.85F, 2.85F);
            GlStateManager.translate(0.18F, 1.55F, 0.18F);
            int dir = tile.getBlockMetadata();
            GlStateManager.rotate(dir * (-90F), 0F, 1F, 0F);
            GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);
        }
        else //Item rendering
        {
            GlStateManager.translate(0.5F, 0.9F, 0.5F);
            GlStateManager.scale(0.67F, 0.67F, 0.67F);
            GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);
        }

        bindTexture(Resources.TEXTURE_TIPI);
        this.modelTipi.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GlStateManager.popMatrix();*/
    }

}