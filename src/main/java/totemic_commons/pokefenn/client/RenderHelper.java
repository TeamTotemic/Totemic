package totemic_commons.pokefenn.client;

import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class RenderHelper
{

    public static void renderTooltip(int x, int y, List<String> tooltipData)
    {
        int color = 0x505000ff;
        int color2 = 0xf0100010;

        renderTooltip(x, y, tooltipData, color, color2);
    }

    public static void renderTooltipOrange(int x, int y, List<String> tooltipData)
    {
        int color = 0x50a06600;
        int color2 = 0xf01e1200;

        renderTooltip(x, y, tooltipData, color, color2);
    }

    public static void renderTooltipGreen(int x, int y, List<String> tooltipData)
    {
        int color = 0x5000a000;
        int color2 = 0xf0001e00;

        renderTooltip(x, y, tooltipData, color, color2);
    }

    public static void renderTooltip(int x, int y, List<String> tooltipData, int color, int color2)
    {
        boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
        if(lighting)
            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();

        if(!tooltipData.isEmpty())
        {
            int var5 = 0;
            int var6;
            int var7;
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
            for(var6 = 0; var6 < tooltipData.size(); ++var6)
            {
                var7 = fontRenderer.getStringWidth(tooltipData.get(var6));
                if(var7 > var5)
                    var5 = var7;
            }
            var6 = x + 12;
            var7 = y - 12;
            int var9 = 8;
            if(tooltipData.size() > 1)
                var9 += 2 + (tooltipData.size() - 1) * 10;
            float z = 300F;
            drawGradientRect(var6 - 3, var7 - 4, z, var6 + var5 + 3, var7 - 3, color2, color2);
            drawGradientRect(var6 - 3, var7 + var9 + 3, z, var6 + var5 + 3, var7 + var9 + 4, color2, color2);
            drawGradientRect(var6 - 3, var7 - 3, z, var6 + var5 + 3, var7 + var9 + 3, color2, color2);
            drawGradientRect(var6 - 4, var7 - 3, z, var6 - 3, var7 + var9 + 3, color2, color2);
            drawGradientRect(var6 + var5 + 3, var7 - 3, z, var6 + var5 + 4, var7 + var9 + 3, color2, color2);
            int var12 = (color & 0xFFFFFF) >> 1 | color & -16777216;
            drawGradientRect(var6 - 3, var7 - 3 + 1, z, var6 - 3 + 1, var7 + var9 + 3 - 1, color, var12);
            drawGradientRect(var6 + var5 + 2, var7 - 3 + 1, z, var6 + var5 + 3, var7 + var9 + 3 - 1, color, var12);
            drawGradientRect(var6 - 3, var7 - 3, z, var6 + var5 + 3, var7 - 3 + 1, color, color);
            drawGradientRect(var6 - 3, var7 + var9 + 2, z, var6 + var5 + 3, var7 + var9 + 3, var12, var12);

            GL11.glDisable(GL11.GL_DEPTH_TEST);
            for(int var13 = 0; var13 < tooltipData.size(); ++var13)
            {
                String var14 = tooltipData.get(var13);
                fontRenderer.drawStringWithShadow(var14, var6, var7, -1);
                if(var13 == 0)
                    var7 += 2;
                var7 += 10;
            }
            GL11.glEnable(GL11.GL_DEPTH_TEST);
        }
        if(!lighting)
            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
        GL11.glColor4f(1F, 1F, 1F, 1F);
    }

    public static void drawGradientRect(int x1, int y1, float z, int x2, int y2, int color1, int color2)
    {
        float var7 = (color1 >> 24 & 255) / 255F;
        float var8 = (color1 >> 16 & 255) / 255F;
        float var9 = (color1 >> 8 & 255) / 255F;
        float var10 = (color1 & 255) / 255F;
        float var11 = (color2 >> 24 & 255) / 255F;
        float var12 = (color2 >> 16 & 255) / 255F;
        float var13 = (color2 >> 8 & 255) / 255F;
        float var14 = (color2 & 255) / 255F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator var15 = Tessellator.instance;
        var15.startDrawingQuads();
        var15.setColorRGBA_F(var8, var9, var10, var7);
        var15.addVertex(x2, y1, z);
        var15.addVertex(x1, y1, z);
        var15.setColorRGBA_F(var12, var13, var14, var11);
        var15.addVertex(x1, y2, z);
        var15.addVertex(x2, y2, z);
        var15.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public static void addQuad(Tessellator tes, double x, double y, double z, double w, double h)
    {
        tes.addVertex(x, y, z);
        tes.addVertex(x, y + h, z);
        tes.addVertex(x + w, y + h, z);
        tes.addVertex(x + w, y, z);
    }
}
