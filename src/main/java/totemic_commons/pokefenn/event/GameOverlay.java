package totemic_commons.pokefenn.event;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;

public class GameOverlay
{
    public static TileTotemBase activeTotem = null;

    @SubscribeEvent
    public void renderHUD(RenderGameOverlayEvent.Post event)
    {
        if(event.type == ElementType.ALL)
        {
            if(activeTotem != null)
            {
                int w = 144;
                int h = 30;
                float x = event.resolution.getScaledWidth() / 2 - w/2;
                float y = event.resolution.getScaledHeight() / 2 - 100;
                float z = 1;
                Tessellator tes = Tessellator.instance;
                FontRenderer font = Minecraft.getMinecraft().fontRenderer;

                GL11.glEnable(GL11.GL_BLEND);
                //GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glPushMatrix();
                GL11.glTranslatef(x, y, z);

                GL11.glDisable(GL11.GL_TEXTURE_2D);
                tes.startDrawingQuads();
                tes.setColorRGBA(80, 180, 70, 128);
                tes.addVertex(0, 0, 0);
                tes.addVertex(0, h, 0);
                tes.addVertex(w, h, 0);
                tes.addVertex(w, 0, 0);
                tes.draw();
                GL11.glEnable(GL11.GL_TEXTURE_2D);

                if(activeTotem.isDoingStartup())
                {
                    Ceremony cer = activeTotem.startupCeremony;
                    int barWidth = 102;

                    font.drawString(cer.getLocalizedName(), 1, 1, 0x000000);
                    font.drawString("Music:", 1, 11, 0x000000);
                    font.drawString("Time:", 1, 21, 0x000000);

                    float musicW = activeTotem.totalCeremonyMelody / (float)cer.getMusicNeeded() * barWidth;
                    float timeW = activeTotem.ceremonyStartupTimer / (float)cer.getMaxStartupTime().getTime() * barWidth;

                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    tes.startDrawingQuads();
                    tes.setColorRGBA(80, 255, 200, 80);
                    tes.addVertex(40, 11, 0);
                    tes.addVertex(40, 11 + 7, 0);
                    tes.addVertex(40 + barWidth, 11 + 7, 0);
                    tes.addVertex(40 + barWidth, 11, 0);

                    tes.setColorRGBA(60, 60, 255, 160);
                    tes.addVertex(40, 11, 0);
                    tes.addVertex(40, 11 + 7, 0);
                    tes.addVertex(40 + musicW, 11 + 7, 0);
                    tes.addVertex(40 + musicW, 11, 0);

                    tes.setColorRGBA(80, 255, 200, 80);
                    tes.addVertex(40, 21, 0);
                    tes.addVertex(40, 21 + 7, 0);
                    tes.addVertex(40 + barWidth, 21 + 7, 0);
                    tes.addVertex(40 + barWidth, 21, 0);

                    tes.setColorRGBA(60, 60, 255, 160);
                    tes.addVertex(40, 21, 0);
                    tes.addVertex(40, 21 + 7, 0);
                    tes.addVertex(40 + timeW, 21 + 7, 0);
                    tes.addVertex(40 + timeW, 21, 0);
                    tes.draw();
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                }

                GL11.glPopMatrix();
                //GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glDisable(GL11.GL_BLEND);
            }
        }
    }
}
