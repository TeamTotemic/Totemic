package totemic_commons.pokefenn.client.rendering.tileentity;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.client.rendering.model.ModelWindChime;
import totemic_commons.pokefenn.lib.Resources;
import totemic_commons.pokefenn.tileentity.music.TileWindChime;

import java.util.Random;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class TileWindChimeRenderer extends TileEntitySpecialRenderer
{
    private final ModelWindChime modelWindChime = new ModelWindChime();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double d, double d1, double d2, float f)
    {
        GL11.glPushMatrix();

        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        TileWindChime tile = (TileWindChime) tileEntity;

        render(tile, tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, ModBlocks.totemBase);
        GL11.glPopMatrix();
    }

    public void render(TileWindChime tileEntity, World world, int i, int j, int k, Block block)
    {
        Tessellator tessellator = Tessellator.instance;
        float f = block.getMixedBrightnessForBlock(world, i, j, k);
        int l = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
        int l1 = l % 65536;
        int l2 = l / 65536;
        tessellator.setColorOpaque_F(f, f, f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) l1, (float) l2);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.5F, 1.47F, 0.5F);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(Resources.TEXTURE_WIND_CHIME);

        if(tileEntity.isPlaying)
        {
            if(tileEntity.getWorldObj().getWorldTime() % 2L == 0)
            {
                //for(int s = 0; i < 10; i++)
                {
                    modelWindChime.chime1.rotateAngleX = getRotationThingy(modelWindChime.chime1.rotateAngleX);
                    modelWindChime.chime1.rotateAngleZ = getRotationThingy(modelWindChime.chime1.rotateAngleZ);

                    modelWindChime.chime2.rotateAngleX = getRotationThingy(modelWindChime.chime2.rotateAngleX);
                    modelWindChime.chime2.rotateAngleZ = getRotationThingy(modelWindChime.chime2.rotateAngleZ);

                    modelWindChime.chime3.rotateAngleX = getRotationThingy(modelWindChime.chime3.rotateAngleX);
                    modelWindChime.chime3.rotateAngleZ = getRotationThingy(modelWindChime.chime3.rotateAngleZ);

                    modelWindChime.chime4.rotateAngleX = getRotationThingy(modelWindChime.chime4.rotateAngleX);
                    modelWindChime.chime4.rotateAngleZ = getRotationThingy(modelWindChime.chime4.rotateAngleZ);
                }
            }
        } else
        {
            resetRotations();
        }

        this.modelWindChime.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
    }

    public void resetRotations()
    {
        modelWindChime.chime1.rotateAngleX = 0.0F;
        modelWindChime.chime1.rotateAngleZ = 0.0F;

        modelWindChime.chime2.rotateAngleX = 0.0F;
        modelWindChime.chime2.rotateAngleZ = 0.0F;

        modelWindChime.chime3.rotateAngleX = 0.0F;
        modelWindChime.chime3.rotateAngleZ = 0.0F;

        modelWindChime.chime4.rotateAngleX = 0.0F;
        modelWindChime.chime4.rotateAngleZ = 0.0F;
    }

    public float getRotationThingy(float i)
    {
        Random random = new Random();
        int multiplier;
        float number = 0;
        float min = 0.01F;
        float max = 0.05F;
        float value = min + (int) (Math.random() * ((1 + max) - min));

        if(random.nextInt(2) + 1 == 1)
            multiplier = 1;
        else
            multiplier = -1;

        number += value;
        number *= multiplier;
        if(number > 1 || number < -1)
            number = 0;

        return number;
    }
}
