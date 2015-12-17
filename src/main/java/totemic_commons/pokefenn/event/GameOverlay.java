package totemic_commons.pokefenn.event;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
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
            if(activeTotem != null && !activeTotem.isCeremony)
                activeTotem = null;

            if(activeTotem != null && (activeTotem.isDoingStartup() || activeTotem.isDoingEndingEffect))
            {
                int w = 117;
                int h = 30;
                float x = (event.resolution.getScaledWidth() - w) / 2 + ConfigurationSettings.CEREMONY_HUD_X;
                float y = (event.resolution.getScaledHeight() - h) / 2 + ConfigurationSettings.CEREMONY_HUD_Y;
                Tessellator tes = Tessellator.instance;
                Minecraft mc = Minecraft.getMinecraft();
                FontRenderer font = mc.fontRenderer;

                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glPushMatrix();
                GL11.glTranslatef(x, y, 0);

                GL11.glDisable(GL11.GL_TEXTURE_2D);
                tes.startDrawingQuads();
                tes.setColorRGBA(80, 180, 70, 128); //Background
                RenderHelper.addQuad(tes, 0, 0, 0, w, h);
                tes.draw();
                GL11.glEnable(GL11.GL_TEXTURE_2D);

                int barWidth = 104;

                if(activeTotem.isDoingStartup())
                {
                    Ceremony cer = activeTotem.startupCeremony;

                    int nameX = (w - font.getStringWidth(cer.getLocalizedName())) / 2;
                    font.drawString(cer.getLocalizedName(), nameX, 1, 0xC8000000);

                    mc.renderEngine.bindTexture(hudTexture);
                    tes.startDrawingQuads();
                    drawNote(tes);
                    drawClock(tes);
                    tes.draw();

                    float musicW = activeTotem.totalCeremonyMelody / (float)cer.getMusicNeeded() * barWidth;
                    float timeW = Math.min(activeTotem.ceremonyStartupTimer / (float)cer.getMaxStartupTime().getTime(), 1.0f) * barWidth;

                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    tes.startDrawingQuads();
                    tes.setColorRGBA(80, 255, 200, 80);
                    RenderHelper.addQuad(tes, 11, 11, 0, barWidth, 7);

                    tes.setColorRGBA(60, 60, 255, 160);
                    RenderHelper.addQuad(tes, 11, 11, 0, musicW, 7);

                    tes.setColorRGBA(80, 255, 200, 80);
                    RenderHelper.addQuad(tes, 11, 21, 0, barWidth, 7);

                    tes.setColorRGBA(60, 60, 255, 160);
                    RenderHelper.addQuad(tes, 11, 21, 0, timeW, 7);
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

    private void drawNote(Tessellator tes)
    {
        tes.setColorRGBA(0, 255, 0, 200);
        tes.addVertexWithUV(1 + 0, 10 + 0, 0, 16.0 / 32, 0.0 / 32);
        tes.addVertexWithUV(1 + 0, 10 + 9, 0, 16.0 / 32, 8.0 / 32);
        tes.addVertexWithUV(1 + 9, 10 + 9, 0, 24.0 / 32, 8.0 / 32);
        tes.addVertexWithUV(1 + 9, 10 + 0, 0, 24.0 / 32, 0.0 / 32);
    }

    private void drawClock(Tessellator tes)
    {
        tes.setColorRGBA(255, 255, 255, 200);
        tes.addVertexWithUV(1 + 0, 20 + 0, 0, 0.0 / 32, 0.0 / 32);
        tes.addVertexWithUV(1 + 0, 20 + 9, 0, 0.0 / 32, 16.0 / 32);
        tes.addVertexWithUV(1 + 9, 20 + 9, 0, 16.0 / 32, 16.0 / 32);
        tes.addVertexWithUV(1 + 9, 20 + 0, 0, 16.0 / 32, 0.0 / 32);
    }
}
