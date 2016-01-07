package totemic_commons.pokefenn.client.rendering.tileentity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import totemic_commons.pokefenn.client.rendering.model.ModelWindChime;
import totemic_commons.pokefenn.lib.Resources;
import totemic_commons.pokefenn.tileentity.music.TileWindChime;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class TileWindChimeRenderer extends TileEntitySpecialRenderer<TileWindChime>
{
    private final ModelWindChime modelWindChime = new ModelWindChime();

    @Override
    public void renderTileEntityAt(TileWindChime tile, double x, double y, double z, float partialTick, int destroyStage)
    {
        GL11.glPushMatrix();

        if(tile != null) //Block render
        {
            GL11.glTranslated(x, y, z);

            GL11.glTranslatef(0.5F, 1.47F, 0.5F);
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

            if(tile.isPlaying())
            {
                if(getWorld().getTotalWorldTime() % 2L == 0)
                    setRotations();
            }
            else
            {
                resetRotations();
            }
        }
        else //Item render
        {
            GL11.glTranslatef(0.5F, 1.85F, 0.5F);
            GL11.glScalef(1.3F, 1.3F, 1.3F);
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            resetRotations();
        }

        bindTexture(Resources.TEXTURE_WIND_CHIME);
        this.modelWindChime.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
    }

    private void setRotations()
    {
        modelWindChime.chime1.rotateAngleX = getRotationThingy();
        modelWindChime.chime1.rotateAngleZ = getRotationThingy();

        modelWindChime.chime2.rotateAngleX = getRotationThingy();
        modelWindChime.chime2.rotateAngleZ = getRotationThingy();

        modelWindChime.chime3.rotateAngleX = getRotationThingy();
        modelWindChime.chime3.rotateAngleZ = getRotationThingy();

        modelWindChime.chime4.rotateAngleX = getRotationThingy();
        modelWindChime.chime4.rotateAngleZ = getRotationThingy();

        //TODO
        /*
        modelWindChime.chime1.rotateAngleX = (-sinerp) * 90F;
        modelWindChime.chime1.rotateAngleZ = (-sinerp) * 90F;

        modelWindChime.chime2.rotateAngleX = (-sinerp) * 90F;
        modelWindChime.chime2.rotateAngleZ = (-sinerp) * 90F;

        modelWindChime.chime3.rotateAngleX = (-sinerp) * 90F;
        modelWindChime.chime3.rotateAngleZ = (-sinerp) * 90F;

        modelWindChime.chime4.rotateAngleX = (-sinerp) * 90F;
        modelWindChime.chime4.rotateAngleZ = (-sinerp) * 90F;
        */
    }

    private void resetRotations()
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

    private float getRotationThingy()
    {
        Random random = getWorld().rand;
        int multiplier;
        float number = 0;
        float min = 0.01F;
        float max = 0.05F;
        float value = min + (int) (random.nextFloat() * ((1 + max) - min));

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
