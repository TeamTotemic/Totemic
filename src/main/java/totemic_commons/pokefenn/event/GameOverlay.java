package totemic_commons.pokefenn.event;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.client.RenderHelper;
import totemic_commons.pokefenn.configuration.ConfigurationSettings;
import totemic_commons.pokefenn.lib.Resources;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;

public class GameOverlay
{
    public static TileTotemBase activeTotem = null;

    private static final ResourceLocation hudTexture = new ResourceLocation(Resources.CEREMONY_HUD);
    private static final RenderItem itemRenderer = new RenderItem();

    @SubscribeEvent
    public void renderHUD(RenderGameOverlayEvent.Post event)
    {
        if(event.type == ElementType.ALL)
        {
            if(activeTotem != null && (activeTotem.isInvalid() || !activeTotem.isCeremony))
                activeTotem = null;

            if(activeTotem != null)
            {
                final int width = 117;
                final int height = 30;
                float x = (event.resolution.getScaledWidth() - width) / 2 + ConfigurationSettings.CEREMONY_HUD_X;
                float y = (event.resolution.getScaledHeight() - height) / 2 + ConfigurationSettings.CEREMONY_HUD_Y;
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
                RenderHelper.addQuad(tes, 0, 0, 0, width, height);
                tes.draw();
                GL11.glEnable(GL11.GL_TEXTURE_2D);

                if(activeTotem.isDoingSelection())
                    renderSelectionHUD(width, tes, mc, font);
                else if(activeTotem.isDoingStartup())
                    renderStartupHUD(width, tes, mc, font);
                else if(activeTotem.isDoingEndingEffect)
                    renderCeremonyHUD(width, tes, mc, font);

                GL11.glPopMatrix();
                GL11.glDisable(GL11.GL_BLEND);
            }
        }
    }

    private void renderSelectionHUD(int width, Tessellator tes, Minecraft mc, FontRenderer font)
    {
        //Header text
        String headerText = StatCollector.translateToLocal("totemic.hud.selection");
        int headerX = (width - font.getStringWidth(headerText)) / 2;
        font.drawString(headerText, headerX, 1, 0xC8000000);

        final int firstSelectorX = 40;
        final int secondSelectorX = 61;
        final int selectorY = 12;
        final int itemBackgroundSize = 16;

        //Item backgrounds
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        tes.startDrawingQuads();
        tes.setColorRGBA(80, 255, 200, 80);
        RenderHelper.addQuad(tes, firstSelectorX, selectorY, 0, itemBackgroundSize, itemBackgroundSize);
        RenderHelper.addQuad(tes, secondSelectorX, selectorY, 0, itemBackgroundSize, itemBackgroundSize);
        tes.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        //Instruments
        //Assuming that we have only 1 selector to render
        MusicInstrument selector = activeTotem.musicSelector[0];
        ItemStack item = (selector != null) ? selector.getItem() : null;

        net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        itemRenderer.renderItemAndEffectIntoGUI(font, mc.getTextureManager(), item, firstSelectorX, selectorY);
        itemRenderer.renderItemOverlayIntoGUI(font, mc.getTextureManager(), item, firstSelectorX, selectorY);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
    }

    private void renderStartupHUD(int width, Tessellator tes, Minecraft mc, FontRenderer font)
    {
        final int barWidth = 104;
        final int barHeight = 7;

        Ceremony cer = activeTotem.startupCeremony;

        String name = cer.getLocalizedName();
        int nameX = (width - font.getStringWidth(name)) / 2;
        font.drawString(name, nameX, 1, 0xC8000000);

        mc.renderEngine.bindTexture(hudTexture);
        tes.startDrawingQuads();
        drawNote(tes);
        drawClock(tes);
        tes.draw();

        float musicW = activeTotem.totalCeremonyMelody / (float)cer.getMusicNeeded() * barWidth;
        float timeW = Math.min(activeTotem.ceremonyStartupTimer / (float)cer.getMaxStartupTime(), 1.0f) * barWidth;

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        tes.startDrawingQuads();
        tes.setColorRGBA(80, 255, 200, 80);
        RenderHelper.addQuad(tes, 11, 11, 0, barWidth, barHeight);

        tes.setColorRGBA(60, 60, 255, 160);
        RenderHelper.addQuad(tes, 11, 11, 0, musicW, barHeight);

        tes.setColorRGBA(80, 255, 200, 80);
        RenderHelper.addQuad(tes, 11, 21, 0, barWidth, barHeight);

        tes.setColorRGBA(60, 60, 255, 160);
        RenderHelper.addQuad(tes, 11, 21, 0, timeW, barHeight);
        tes.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    private void renderCeremonyHUD(int width, Tessellator tes, Minecraft mc, FontRenderer font)
    {
        Ceremony cer = activeTotem.currentCeremony;

        String name = cer.getLocalizedName();
        int nameX = (width - font.getStringWidth(name)) / 2;
        font.drawString(name, nameX, 1, 0xC8000000);
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

    // See EntityPlayerSP.getFOVMultiplier
    @SubscribeEvent
    public void onFOVUpdate(FOVUpdateEvent event)
    {
        if(event.entity.isUsingItem() && event.entity.getItemInUse().getItem() == ModItems.baykokBow)
        {
            int useDuration = event.entity.getItemInUseDuration();
            float modifier = (float)useDuration / 20.0F;

            if(modifier > 1.0F)
            {
                modifier = 1.0F;
            }
            else
            {
                modifier *= modifier;
            }

            event.newfov *= 1.0F - modifier * 0.15F;
        }
    }
}
