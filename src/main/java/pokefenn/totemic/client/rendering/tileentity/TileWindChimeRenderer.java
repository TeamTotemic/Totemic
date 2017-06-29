package pokefenn.totemic.client.rendering.tileentity;

import java.util.Random;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import pokefenn.totemic.client.rendering.model.ModelWindChime;
import pokefenn.totemic.lib.Resources;
import pokefenn.totemic.tileentity.music.TileWindChime;

public class TileWindChimeRenderer extends TileEntitySpecialRenderer<TileWindChime>
{
    private final ModelWindChime modelWindChime = new ModelWindChime();

    @Override
    public void render(TileWindChime tile, double x, double y, double z, float partialTick, int destroyStage, float alpha)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5, y + 1.47, z + 0.5);
        GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);

        if(tile.isPlaying())
        {
            if(getWorld().getTotalWorldTime() % 2L == 0)
                setRotations();
        }
        else
        {
            resetRotations();
        }

        bindTexture(Resources.TEXTURE_WIND_CHIME);
        this.modelWindChime.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GlStateManager.popMatrix();
    }

    private void setRotations()
    {
        modelWindChime.chime1.rotateAngleX = getRandomAngle();
        modelWindChime.chime1.rotateAngleZ = getRandomAngle();

        modelWindChime.chime2.rotateAngleX = getRandomAngle();
        modelWindChime.chime2.rotateAngleZ = getRandomAngle();

        modelWindChime.chime3.rotateAngleX = getRandomAngle();
        modelWindChime.chime3.rotateAngleZ = getRandomAngle();

        modelWindChime.chime4.rotateAngleX = getRandomAngle();
        modelWindChime.chime4.rotateAngleZ = getRandomAngle();
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

    private float getRandomAngle()
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
