package totemic_commons.pokefenn.event;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.profiler.Profiler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class GameOverlay
{
    @SubscribeEvent
    public void renderHUD(RenderGameOverlayEvent.Post event)
    {
        if(event.type == ElementType.ALL)
        {
            float w = 55;
            float h = 34;
            float x = event.resolution.getScaledWidth() / 2 - w/2;
            float y = event.resolution.getScaledHeight() / 2 - 100;
            float z = 1;
            Tessellator tes = Tessellator.instance;

            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            tes.startDrawingQuads();
            tes.setColorRGBA(0, 160, 255, 128);
            tes.addVertex(x, y, z);
            tes.addVertex(x, y + h, z);
            tes.addVertex(x + w, y + h, z);
            tes.addVertex(x + w, y, z);
            tes.draw();
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }
    }
}
