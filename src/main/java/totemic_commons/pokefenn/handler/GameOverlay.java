package totemic_commons.pokefenn.handler;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.client.RenderHelper;
import totemic_commons.pokefenn.configuration.ConfigurationSettings;
import totemic_commons.pokefenn.item.equipment.weapon.ItemBaykokBow;
import totemic_commons.pokefenn.lib.Resources;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase_old;

public class GameOverlay
{
    public static TileTotemBase_old activeTotem = null;

    private static final ResourceLocation hudTexture = new ResourceLocation(Resources.CEREMONY_HUD);

    @SubscribeEvent
    public void renderHUD(RenderGameOverlayEvent.Post event)
    {
        if(event.getType() == ElementType.ALL)
        {
            Minecraft mc = Minecraft.getMinecraft();
            mc.mcProfiler.startSection("totemicHUD");

            if(activeTotem != null && (!activeTotem.isCeremony || activeTotem.isInvalid()))
                activeTotem = null;

            if(activeTotem != null && (activeTotem.isDoingStartup() || activeTotem.isDoingEndingEffect))
            {
                int w = 117;
                int h = 30;
                double texW = 128;
                double texH = 64;
                float hudX = (event.getResolution().getScaledWidth() - w) / 2 + ConfigurationSettings.CEREMONY_HUD_X;
                float hudY = (event.getResolution().getScaledHeight() - h) / 2 + ConfigurationSettings.CEREMONY_HUD_Y;
                Tessellator tes = Tessellator.getInstance();
                VertexBuffer wr = tes.getBuffer();
                FontRenderer font = mc.fontRendererObj;

                GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_ENABLE_BIT);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glPushMatrix();
                GL11.glTranslatef(hudX, hudY, 0);

                //Background
                mc.renderEngine.bindTexture(hudTexture);
                wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
                RenderHelper.addQuad(wr, 0, 0, 0, w, h, 0, 0, w / texW, h / texH);
                tes.draw();

                int barW = 104;
                int barH = 7;

                if(activeTotem.isDoingStartup())
                {
                    Ceremony cer = activeTotem.startupCeremony;

                    String locName = I18n.format(cer.getUnlocalizedName());
                    int nameX = (w - font.getStringWidth(locName)) / 2;
                    font.drawString(locName, nameX, 1, 0xC8000000);

                    GlStateManager.color(1, 1, 1);
                    mc.renderEngine.bindTexture(hudTexture);

                    wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
                    RenderHelper.addQuad(wr, 1, 10, 0,  9, 9,  16 / texW, 48 / texH,   8 / texW,  8 / texH); //Note
                    RenderHelper.addQuad(wr, 1, 20, 0,  9, 9,   0 / texW, 48 / texH,  16 / texW, 16 / texH); //Clock

                    float musicW = activeTotem.totalCeremonyMelody / (float)cer.getMusicNeeded() * barW;
                    float timeW = Math.min(activeTotem.ceremonyStartupTimer / (float)cer.getAdjustedMaxStartupTime(mc.theWorld.getDifficulty()), 1.0f) * barW;

                    RenderHelper.addQuad(wr, 11, 11, 0,  musicW, barH,  0, 32 / texH,  musicW / texW, barH / texH); //Music bar
                    RenderHelper.addQuad(wr, 11, 21, 0,  timeW,  barH,  0, 32 / texH,  timeW  / texW, barH / texH); //Time bar
                    tes.draw();
                }
                else if(activeTotem.isDoingEndingEffect)
                {
                    Ceremony cer = activeTotem.currentCeremony;

                    String locName = I18n.format(cer.getUnlocalizedName());
                    int nameX = (w - font.getStringWidth(locName)) / 2;
                    font.drawString(locName, nameX, 1, 0xC8000000);
                }

                GL11.glPopMatrix();
                GL11.glPopAttrib();
            }

            mc.mcProfiler.endSection();
        }
    }

    @SubscribeEvent
    public void onFOVUpdate(FOVUpdateEvent event)
    {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if(player.isHandActive() && player.getActiveItemStack() != null && player.getActiveItemStack().getItem() instanceof ItemBaykokBow)
        {
            int bowTicks = player.getItemInUseMaxCount();
            float modifier = bowTicks / 20.0F;

            if (modifier > 1.0F)
                modifier = 1.0F;
            else
                modifier = modifier * modifier;

            event.setNewfov(event.getFov() * (1.0F - 0.15F * modifier));
        }
    }
}
