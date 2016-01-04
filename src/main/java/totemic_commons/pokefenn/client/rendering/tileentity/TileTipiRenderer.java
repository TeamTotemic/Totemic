package totemic_commons.pokefenn.client.rendering.tileentity;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import totemic_commons.pokefenn.client.rendering.model.ModelTipi;
import totemic_commons.pokefenn.tileentity.TileTipi;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class TileTipiRenderer extends TileEntitySpecialRenderer<TileTipi>
{
    private final ModelTipi modelTipi = new ModelTipi();

    @Override
    public void renderTileEntityAt(TileTipi tile, double x, double y, double z, float f, int destroyStage)
    {
        GL11.glPushMatrix();

        GL11.glTranslated(x, y, z);
        GL11.glScaled(2.85, 2.85, 2.85);
        GL11.glTranslatef(0.18F, 1.55F, 0.18F);
        //GL11.glRotatef(dir * (-90F), 0F, 1F, 0F);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        this.modelTipi.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
    }

    /*public void renderBlockYour(TileTipi tl, World world, int i, int j, int k, Block block)
    {
        Tessellator tessellator = Tessellator.instance;
        float f = block.getMixedBrightnessForBlock(world, i, j, k);
        int l = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
        int l1 = l % 65536;
        int l2 = l / 65536;
        tessellator.setColorOpaque_F(f, f, f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, l1, l2);

        int dir = world.getBlockMetadata(i, j, k);

        GL11.glPushMatrix();
        GL11.glScaled(2.85, 2.85, 2.85);
        GL11.glTranslatef(0.18F, 1.55F, 0.18F);
        GL11.glRotatef(dir * (-90F), 0F, 1F, 0F);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        //GL11.glTranslatef(-0.5F, 0, -0.5F);
        bindTexture(Resources.TEXTURE_TIPI);

        this.modelTipi.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
    }*/



}