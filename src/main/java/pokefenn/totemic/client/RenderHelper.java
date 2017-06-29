package pokefenn.totemic.client;

import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

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
        if(tooltipData.isEmpty())
            return;

        GL11.glPushAttrib(GL11.GL_LIGHTING_BIT | GL11.GL_DEPTH_BUFFER_BIT);

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

        GL11.glPopAttrib();
        GlStateManager.color(1F, 1F, 1F, 1F);
    }

    public static void drawGradientRect(int x1, int y1, float z, int x2, int y2, int color1, int color2)
    {
        float r1 = (color1 >> 24 & 255) / 255F;
        float g1 = (color1 >> 16 & 255) / 255F;
        float b1 = (color1 >> 8 & 255) / 255F;
        float a1 = (color1 & 255) / 255F;
        float r2 = (color2 >> 24 & 255) / 255F;
        float g2 = (color2 >> 16 & 255) / 255F;
        float b2 = (color2 >> 8 & 255) / 255F;
        float a2 = (color2 & 255) / 255F;
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        Tessellator tes = Tessellator.getInstance();
        BufferBuilder buf = tes.getBuffer();
        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        buf.pos(x2, y1, z).color(g1, b1, a1, r1).endVertex();
        buf.pos(x1, y1, z).color(g1, b1, a1, r1).endVertex();
        buf.pos(x1, y2, z).color(g2, b2, a2, r2).endVertex();
        buf.pos(x2, y2, z).color(g2, b2, a2, r2).endVertex();
        tes.draw();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawTexturedModalRect(int x, int y, float z, int u, int v, int w, int h)
    {
        float tx = 0.00390625F;
        float ty = 0.00390625F;
        Tessellator tes = Tessellator.getInstance();
        BufferBuilder buf = tes.getBuffer();
        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buf.pos(x + 0, y + h, z).tex((u + 0) * tx, (v + h) * ty).endVertex();
        buf.pos(x + w, y + h, z).tex((u + w) * tx, (v + h) * ty).endVertex();
        buf.pos(x + w, y + 0, z).tex((u + w) * tx, (v + 0) * ty).endVertex();
        buf.pos(x + 0, y + 0, z).tex((u + 0) * tx, (v + 0) * ty).endVertex();
        tes.draw();
    }

    //Currently unused
    /*public static void renderStar(int color, float xScale, float yScale, float zScale, long seed)
    {
        Tessellator tes = Tessellator.getInstance();
        BufferBuilder buf = tes.getBuffer();

        int ticks = (int) (Minecraft.getMinecraft().world.getTotalWorldTime() % 200);
        if(ticks >= 100)
            ticks = 200 - ticks - 1;

        float f1 = ticks / 200F;
        float f2 = 0F;
        if(f1 > 0.7F)
            f2 = (f1 - 0.7F) / 0.2F;
        Random random = new Random(seed);

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(false);
        GL11.glTranslatef(0F, -1F, -2F);
        GL11.glScalef(xScale, yScale, zScale);

        for(int i = 0; i < (f1 + f1 * f1) / 2F * 90F + 30F; i++)
        {
            GL11.glRotatef(random.nextFloat() * 360F, 1F, 0F, 0F);
            GL11.glRotatef(random.nextFloat() * 360F, 0F, 1F, 0F);
            GL11.glRotatef(random.nextFloat() * 360F, 0F, 0F, 1F);
            GL11.glRotatef(random.nextFloat() * 360F, 1F, 0F, 0F);
            GL11.glRotatef(random.nextFloat() * 360F, 0F, 1F, 0F);
            GL11.glRotatef(random.nextFloat() * 360F + f1 * 90F, 0F, 0F, 1F);
            buf.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
            float f3 = random.nextFloat() * 20F + 5F + f2 * 10F;
            float f4 = random.nextFloat() * 2F + 1F + f2 * 2F;
            buf.pos(0, 0, 0).putColor4((color << 8) | (int) (255F * (1F - f2))); buf.endVertex();

            buf.pos(-0.866D * f4, f3, -0.5F * f4).color(0, 0, 0, 0).endVertex();
            buf.pos(0.866D * f4, f3, -0.5F * f4).color(0, 0, 0, 0).endVertex();
            buf.pos(0, f3, 1F * f4).color(0, 0, 0, 0).endVertex();
            buf.pos(-0.866D * f4, f3, -0.5F * f4).color(0, 0, 0, 0).endVertex();
            tes.draw();
        }

        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glPopMatrix();
    }*/

    /**
     * Adds an untextured quad to the WorldRenderer. Needs the POSITION_COLOR vertex format.
     */
    public static void addColoredQuad(BufferBuilder buf, double x, double y, double z, double w, double h, int color)
    {
        float r = (color >> 24 & 255) / 255.0F;
        float g = (color >> 16 & 255) / 255.0F;
        float b = (color >> 8 & 255) / 255.0F;
        float a = (color & 255) / 255.0F;
        buf.pos(x, y, z).color(r, g, b, a).endVertex();
        buf.pos(x, y + h, z).color(r, g, b, a).endVertex();
        buf.pos(x + w, y + h, z).color(r, g, b, a).endVertex();
        buf.pos(x + w, y, z).color(r, g, b, a).endVertex();
    }

    /**
     * Adds a textured quad to the WorldRenderer. Needs the POSITION_TEX vertex format.
     */
    public static void addQuad(BufferBuilder buf, double x, double y, double z, double w, double h, double u, double v,
            double texW, double texH)
    {
        buf.pos(x + 0, y + 0, z).tex(u + 0,    v + 0).endVertex();
        buf.pos(x + 0, y + h, z).tex(u + 0,    v + texH).endVertex();
        buf.pos(x + w, y + h, z).tex(u + texW, v + texH).endVertex();
        buf.pos(x + w, y + 0, z).tex(u + texW, v + 0).endVertex();
    }
}
