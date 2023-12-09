package pokefenn.totemic.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import pokefenn.totemic.ModConfig;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.block.totem.entity.StateCeremonyEffect;
import pokefenn.totemic.block.totem.entity.StateSelection;
import pokefenn.totemic.block.totem.entity.StateStartup;
import pokefenn.totemic.block.totem.entity.StateTotemEffect;
import pokefenn.totemic.block.totem.entity.TotemBaseBlockEntity;

public enum CeremonyHUD implements IGuiOverlay {
    INSTANCE;

    private static final ResourceLocation SELECTION_HUD_TEXTURE = Totemic.resloc("textures/gui/selection_hud.png");
    private static final ResourceLocation CEREMONY_HUD_TEXTURE = Totemic.resloc("textures/gui/ceremony_hud.png");

    private static final int HUD_WIDTH = 117;
    private static final int HUD_HEIGHT = 30;

    private TotemBaseBlockEntity activeTotem = null;

    public void setActiveTotem(TotemBaseBlockEntity tile) {
        if(tile.getBlockPos().distToCenterSqr(Minecraft.getInstance().getCameraEntity().position()) <= 8*8) {
            activeTotem = tile;
        }
        else if(activeTotem == tile) {
            activeTotem = null;
        }
    }

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        if(activeTotem == null)
            return;
        var mc = gui.getMinecraft();
        mc.getProfiler().push("totemic.ceremonyHUD");

        if(activeTotem.isRemoved() || activeTotem.getLevel() != mc.level || activeTotem.getTotemState() instanceof StateTotemEffect) {
            activeTotem = null;
            return;
        }

        final int hudX = (screenWidth - HUD_WIDTH) / 2 + ModConfig.CLIENT.ceremonyHudPositionX.get();
        final int hudY = (screenHeight - HUD_HEIGHT) / 2 + ModConfig.CLIENT.ceremonyHudPositionY.get();

        var poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(hudX, hudY, 0);

        var state = activeTotem.getTotemState();
        if(state instanceof StateSelection s)
            renderSelectionHUD(s, gui, guiGraphics, partialTick, hudX, hudY);
        else if(state instanceof StateStartup s)
            renderStartupHUD(s, gui, guiGraphics, partialTick);
        else if(state instanceof StateCeremonyEffect s)
            renderCeremonyEffectHUD(s, gui, guiGraphics, partialTick);

        poseStack.popPose();

        mc.getProfiler().pop();
    }

    private void renderSelectionHUD(StateSelection state, ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int hudX, int hudY) {
        final int texW = 128, texH = 64;
        gui.setupOverlayRenderState(true, false);

        //Background
        guiGraphics.blit(SELECTION_HUD_TEXTURE, 0, 0,  0, 0,  HUD_WIDTH, HUD_HEIGHT,  texW, texH);

        //Header text
        var headerText = I18n.get("totemic.hud.selection");
        guiGraphics.drawCenteredString(gui.getFont(), headerText, HUD_WIDTH / 2, 2, 0xC8000000);

        //Instruments
        var selectors = state.getSelectors();
        //Assuming that we have at most 1 selector to render
        if(!selectors.isEmpty()) {
            var item = selectors.get(0).getItem();
            guiGraphics.renderItem(item, hudX + 40, hudY + 12);
        }
    }

    @SuppressWarnings("resource")
    private void renderStartupHUD(StateStartup state, ForgeGui gui, GuiGraphics guiGraphics, float partialTick) {
        final int texW = 128, texH = 64;
        final int barW = 104, barH = 7;
        var cer = state.getCeremony();

        gui.setupOverlayRenderState(true, false);
        RenderSystem.setShaderTexture(0, CEREMONY_HUD_TEXTURE);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        var buf = Tesselator.getInstance().getBuilder();
        buf.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        var poseStack = guiGraphics.pose();

        //Background
        addQuad(buf, poseStack, 0, 0,  HUD_WIDTH, HUD_HEIGHT,  0, 0,  HUD_WIDTH, HUD_HEIGHT,  texW, texH);

        //Symbols
        addQuad(buf, poseStack, 1, 10,  9, 9,  16, 48,   8,  8, texW, texH); //Note
        addQuad(buf, poseStack, 1, 20,  9, 9,   0, 48,  16, 16, texW, texH); //Clock

        //Bars
        float musicW = state.getTotalMusic() / (float)cer.getMusicNeeded() * barW;
        float timeW = Math.min((state.getTime() + partialTick) / cer.getAdjustedMaxStartupTime(gui.getMinecraft().level.getDifficulty()), 1.0F) * barW;
        addQuad(buf, poseStack, 11, 11,  musicW, barH,  0, 32,  musicW, barH, texW, texH);
        addQuad(buf, poseStack, 11, 21,  timeW,  barH,  0, 32,  timeW,  barH, texW, texH);

        BufferUploader.drawWithShader(buf.end());

        //Ceremony name
        var name = cer.getDisplayName().getString();
        guiGraphics.drawCenteredString(gui.getFont(), name, HUD_WIDTH / 2, 2, 0xC8000000);
    }

    private void renderCeremonyEffectHUD(StateCeremonyEffect state, ForgeGui gui, GuiGraphics guiGraphics, float partialTick) {
        final int texW = 128, texH = 64;
        final int barW = 104, barH = 7;
        var cer = state.getCeremony();
        var cerInst = state.getCeremonyInstance();

        gui.setupOverlayRenderState(true, false);
        RenderSystem.setShaderTexture(0, CEREMONY_HUD_TEXTURE);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        var buf = Tesselator.getInstance().getBuilder();
        buf.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        var poseStack = guiGraphics.pose();

        //Background
        addQuad(buf, poseStack, 0, 0,  HUD_WIDTH, HUD_HEIGHT,  0, 0,  HUD_WIDTH, HUD_HEIGHT,  texW, texH);

        //Clock symbols
        addQuad(buf, poseStack, 1, 20,  9, 9,   0, 48,  16, 16, texW, texH);

        //Time bar
        float timeW = Mth.clamp(1.0F - (state.getTime() + partialTick) / cerInst.getEffectTime(), 0.0F, 1.0F) * barW;
        addQuad(buf, poseStack, 11, 21,  timeW,  barH,  0, 32,  timeW,  barH, texW, texH);

        BufferUploader.drawWithShader(buf.end());

        //Ceremony name
        var name = cer.getDisplayName().getString();
        guiGraphics.drawCenteredString(gui.getFont(), name, HUD_WIDTH / 2, 2, 0xC8000000);
    }

    //Like GuiComponent.blit, but using the given BufferBuilder and allowing float values rather than int
    private static void addQuad(BufferBuilder buf, PoseStack ps, float x, float y, float width, float height, float uOffset, float vOffset, float uWidth, float vHeight, int textureWidth, int textureHeight) {
        var mat = ps.last().pose();
        float minU = uOffset / textureWidth,  maxU = (uOffset + uWidth) / textureWidth;
        float minV = vOffset / textureHeight, maxV = (vOffset + vHeight) / textureHeight;
        buf.vertex(mat, x,         y + height, 0).uv(minU, maxV).endVertex();
        buf.vertex(mat, x + width, y + height, 0).uv(maxU, maxV).endVertex();
        buf.vertex(mat, x + width, y,          0).uv(maxU, minV).endVertex();
        buf.vertex(mat, x,         y,          0).uv(minU, minV).endVertex();
    }
}
