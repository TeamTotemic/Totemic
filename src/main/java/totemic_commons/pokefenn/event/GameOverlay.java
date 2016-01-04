package totemic_commons.pokefenn.event;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.client.RenderHelper;
import totemic_commons.pokefenn.configuration.ConfigurationSettings;
import totemic_commons.pokefenn.lib.Resources;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;

public class GameOverlay
{
    public static TileTotemBase activeTotem = null;

    private static final ResourceLocation hudTexture = new ResourceLocation(Resources.CEREMONY_HUD);

    @SubscribeEvent
    public void renderHUD(RenderGameOverlayEvent.Post event)
    {
        if(event.type == ElementType.ALL)
        {
            if(activeTotem != null && (!activeTotem.isCeremony || activeTotem.isInvalid()))
                activeTotem = null;

            if(activeTotem != null && (activeTotem.isDoingStartup() || activeTotem.isDoingEndingEffect))
            {
                int w = 117;
                int h = 30;
                float x = (event.resolution.getScaledWidth() - w) / 2 + ConfigurationSettings.CEREMONY_HUD_X;
                float y = (event.resolution.getScaledHeight() - h) / 2 + ConfigurationSettings.CEREMONY_HUD_Y;
                Tessellator tes = Tessellator.getInstance();
                WorldRenderer wr = tes.getWorldRenderer();
                Minecraft mc = Minecraft.getMinecraft();
                FontRenderer font = mc.fontRendererObj;

                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glPushMatrix();
                GL11.glTranslatef(x, y, 0);

                GL11.glDisable(GL11.GL_TEXTURE_2D);
                wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
                wr.color(80, 180, 70, 128); //Background
                RenderHelper.addQuad(wr, 0, 0, 0, w, h);
                tes.draw();
                GL11.glEnable(GL11.GL_TEXTURE_2D);

                int barWidth = 104;

                if(activeTotem.isDoingStartup())
                {
                    Ceremony cer = activeTotem.startupCeremony;

                    int nameX = (w - font.getStringWidth(cer.getLocalizedName())) / 2;
                    font.drawString(cer.getLocalizedName(), nameX, 1, 0xC8000000);

                    mc.renderEngine.bindTexture(hudTexture);
                    wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
                    drawNote(wr);
                    drawClock(wr);
                    tes.draw();

                    float musicW = activeTotem.totalCeremonyMelody / (float)cer.getMusicNeeded() * barWidth;
                    float timeW = Math.min(activeTotem.ceremonyStartupTimer / (float)cer.getMaxStartupTime(), 1.0f) * barWidth;

                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
                    wr.color(80, 255, 200, 80);
                    RenderHelper.addQuad(wr, 11, 11, 0, barWidth, 7);
                    RenderHelper.addQuad(wr, 11, 21, 0, barWidth, 7);

                    wr.color(60, 60, 255, 160);
                    RenderHelper.addQuad(wr, 11, 11, 0, musicW, 7);
                    RenderHelper.addQuad(wr, 11, 21, 0, timeW, 7);
                    tes.draw();
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                }
                else if(activeTotem.isDoingEndingEffect)
                {
                    Ceremony cer = activeTotem.currentCeremony;

                    int nameX = (w - font.getStringWidth(cer.getLocalizedName())) / 2;
                    font.drawString(cer.getLocalizedName(), nameX, 1, 0xC8000000);
                }

                GL11.glPopMatrix();
                GL11.glDisable(GL11.GL_BLEND);
            }
        }
    }

    private void drawNote(WorldRenderer wr)
    {
        wr.color(0, 255, 0, 200);
        wr.pos(1 + 0, 10 + 0, 0).tex(16.0 / 32, 0.0 / 32);
        wr.pos(1 + 0, 10 + 9, 0).tex(16.0 / 32, 8.0 / 32);
        wr.pos(1 + 9, 10 + 9, 0).tex(24.0 / 32, 8.0 / 32);
        wr.pos(1 + 9, 10 + 0, 0).tex(24.0 / 32, 0.0 / 32);
    }

    private void drawClock(WorldRenderer wr)
    {
        wr.color(255, 255, 255, 200);
        wr.pos(1 + 0, 20 + 0, 0).tex(0.0 / 32, 0.0 / 32);
        wr.pos(1 + 0, 20 + 9, 0).tex(0.0 / 32, 16.0 / 32);
        wr.pos(1 + 9, 20 + 9, 0).tex(16.0 / 32, 16.0 / 32);
        wr.pos(1 + 9, 20 + 0, 0).tex(16.0 / 32, 0.0 / 32);
    }
}
