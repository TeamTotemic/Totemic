package totemic_commons.pokefenn.client.rendering.tileentity;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.client.rendering.model.ModelDrum;
import totemic_commons.pokefenn.lib.Resources;
import totemic_commons.pokefenn.tileentity.music.TileDrum;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class TileDrumRenderer extends TileEntitySpecialRenderer
{
    private final ModelDrum modelDrum = new ModelDrum();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double d, double d1, double d2, float f)
    {
        GL11.glPushMatrix();

        GL11.glTranslated(d, d1, d2);
        TileDrum tile = (TileDrum) tileEntity;

        renderBlockYour(tile, tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, ModBlocks.drum);
        GL11.glPopMatrix();
    }

    public void renderBlockYour(TileDrum tl, World world, int i, int j, int k, Block block)
    {
        Tessellator tessellator = Tessellator.instance;
        float f = block.getMixedBrightnessForBlock(world, i, j, k);
        int l = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
        int l1 = l % 65536;
        int l2 = l / 65536;
        tessellator.setColorOpaque_F(f, f, f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, l1, l2);

        GL11.glPushMatrix();
        GL11.glTranslatef(0.5F, 1.47F, 0.5F);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        //GL11.glTranslatef(-0.5F, 0, -0.5F);
        bindTexture(Resources.TEXTURE_DRUM);

        this.modelDrum.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
    }
}
