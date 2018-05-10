package pokefenn.totemic.handler;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.client.TotemicRenderHelper;
import pokefenn.totemic.configuration.ModConfig;
import pokefenn.totemic.item.equipment.weapon.ItemBaykokBow;
import pokefenn.totemic.lib.Resources;
import pokefenn.totemic.tileentity.totem.*;

@SideOnly(Side.CLIENT)
public class GameOverlay
{
    private static TileTotemBase activeTotem = null;

    private static final ResourceLocation SELECTION_HUD_TEXTURE = new ResourceLocation(Resources.SELECTION_HUD);
    private static final ResourceLocation CEREMONY_HUD_TEXTURE = new ResourceLocation(Resources.CEREMONY_HUD);

    public static @Nullable TileTotemBase getActiveTotem()
    {
        return activeTotem;
    }

    public static void setActiveTotem(@Nullable TileTotemBase tile)
    {
        activeTotem = tile;
    }

    @SubscribeEvent
    public void renderHUD(RenderGameOverlayEvent.Post event)
    {
        if(event.getType() != ElementType.ALL)
            return;

        Minecraft mc = Minecraft.getMinecraft();
        mc.mcProfiler.startSection("totemicHUD");

        if(activeTotem != null)
        {
            if(activeTotem.isInvalid() || activeTotem.getState() instanceof StateTotemEffect)
                activeTotem = null;
        }

        if(activeTotem != null)
        {
            int hudWidth = 117;
            int hudHeight = 30;
            float hudX = (event.getResolution().getScaledWidth() - hudWidth) / 2 + ModConfig.client.ceremonyHudPositionX;
            float hudY = (event.getResolution().getScaledHeight() - hudHeight) / 2 + ModConfig.client.ceremonyHudPositionY;
            Tessellator tes = Tessellator.getInstance();
            BufferBuilder buf = tes.getBuffer();
            FontRenderer font = mc.fontRenderer;

            GlStateManager.pushMatrix();
            GlStateManager.translate(hudX, hudY, 0);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);

            TotemState state = activeTotem.getState();
            if(state instanceof StateSelection)
            {
                renderSelectionHUD((StateSelection) state, hudWidth, hudHeight, mc, tes, buf, font);
            }
            else if(state instanceof StateStartup)
            {
                renderStartupHUD((StateStartup) state, hudWidth, hudHeight, mc, tes, buf, font);
            }
            else if(state instanceof StateCeremonyEffect)
            {
                renderCeremonyEffectHUD((StateCeremonyEffect) state, hudWidth, hudHeight, mc, tes, buf, font);
            }

            GlStateManager.popMatrix();
        }

        mc.mcProfiler.endSection();
    }

    private void renderSelectionHUD(StateSelection state, int w, int h, Minecraft mc, Tessellator tes, BufferBuilder buf, FontRenderer font)
    {
        double texW = 128;
        double texH = 64;

        List<MusicInstrument> selectors = state.getSelectors();
        //Assuming that we have only 1 selector to render
        MusicInstrument selector = selectors.get(0);
        ItemStack item = (selector != null) ? selector.getItem() : ItemStack.EMPTY;

        //Background
        mc.renderEngine.bindTexture(SELECTION_HUD_TEXTURE);
        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        TotemicRenderHelper.addQuad(buf, 0, 0, 0, w, h, 0, 0, w / texW, h / texH);
        tes.draw();

        //Header text
        String locHeader = I18n.format("totemic.hud.selection");
        int headerX = (w - font.getStringWidth(locHeader)) / 2;
        font.drawString(locHeader, headerX, 1, 0xC8000000);

        //Instruments
        int selectorX = 40;
        int selectorY = 12;
        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.enableDepth();
        renderItem.renderItemAndEffectIntoGUI(item, selectorX, selectorY);
        renderItem.renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRenderer, item, selectorX, selectorY, null);
        RenderHelper.disableStandardItemLighting();
    }

    private void renderStartupHUD(StateStartup state, int w, int h, Minecraft mc, Tessellator tes, BufferBuilder buf, FontRenderer font)
    {
        double texW = 128;
        double texH = 64;
        int barW = 104;
        int barH = 7;

        Ceremony cer = state.getCeremony();

        //Background
        mc.renderEngine.bindTexture(CEREMONY_HUD_TEXTURE);
        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        TotemicRenderHelper.addQuad(buf, 0, 0, 0, w, h, 0, 0, w / texW, h / texH);

        //Symbols
        TotemicRenderHelper.addQuad(buf, 1, 10, 0,  9, 9,  16 / texW, 48 / texH,   8 / texW,  8 / texH); //Note
        TotemicRenderHelper.addQuad(buf, 1, 20, 0,  9, 9,   0 / texW, 48 / texH,  16 / texW, 16 / texH); //Clock

        //Bars
        float musicW = state.getTotalMusic() / (float)cer.getMusicNeeded() * barW;
        float timeW = Math.min(state.getTime() / (float)cer.getAdjustedMaxStartupTime(mc.world.getDifficulty()), 1.0f) * barW;

        TotemicRenderHelper.addQuad(buf, 11, 11, 0,  musicW, barH,  0, 32 / texH,  musicW / texW, barH / texH); //Music bar
        TotemicRenderHelper.addQuad(buf, 11, 21, 0,  timeW,  barH,  0, 32 / texH,  timeW  / texW, barH / texH); //Time bar
        tes.draw();

        //Ceremony name
        String locName = I18n.format(cer.getUnlocalizedName());
        int nameX = (w - font.getStringWidth(locName)) / 2;
        font.drawString(locName, nameX, 1, 0xC8000000);
    }

    private void renderCeremonyEffectHUD(StateCeremonyEffect state, int w, int h, Minecraft mc, Tessellator tes, BufferBuilder buf, FontRenderer font)
    {
        double texW = 128;
        double texH = 64;

        Ceremony cer = state.getCeremony();

        //Background
        mc.renderEngine.bindTexture(CEREMONY_HUD_TEXTURE);
        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        TotemicRenderHelper.addQuad(buf, 0, 0, 0, w, h, 0, 0, w / texW, h / texH);
        tes.draw();

        //Ceremony name
        String locName = I18n.format(cer.getUnlocalizedName());
        int nameX = (w - font.getStringWidth(locName)) / 2;
        font.drawString(locName, nameX, 1, 0xC8000000);
    }

    @SubscribeEvent
    public void onFOVUpdate(FOVUpdateEvent event)
    {
        EntityPlayer player = Minecraft.getMinecraft().player;
        if(player.isHandActive() && player.getActiveItemStack().getItem() instanceof ItemBaykokBow)
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
