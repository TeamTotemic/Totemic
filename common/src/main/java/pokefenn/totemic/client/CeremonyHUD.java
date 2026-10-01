package pokefenn.totemic.client;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.Profiler;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.TotemicConfig;
import pokefenn.totemic.block.totem.entity.StateCeremonyEffect;
import pokefenn.totemic.block.totem.entity.StateSelection;
import pokefenn.totemic.block.totem.entity.StateStartup;
import pokefenn.totemic.block.totem.entity.StateTotemEffect;
import pokefenn.totemic.block.totem.entity.TotemBaseBlockEntity;

public enum CeremonyHUD {
    INSTANCE;

    private static final Identifier SELECTION_HUD_TEXTURE = Totemic.resloc("textures/gui/selection_hud.png");
    private static final Identifier CEREMONY_HUD_TEXTURE = Totemic.resloc("textures/gui/ceremony_hud.png");

    private static final Component SELECTION_TEXT = Component.translatable("totemic.hud.selection");

    private static final int HUD_WIDTH = 117;
    private static final int HUD_HEIGHT = 30;

    private TotemBaseBlockEntity activeTotem = null;

    public void setActiveTotem(TotemBaseBlockEntity tile) {
        final double hudRange = 8.0;
        var camera = Minecraft.getInstance().getCameraEntity();
        if(camera != null && tile.getBlockPos().distToCenterSqr(camera.position()) <= hudRange*hudRange) {
            activeTotem = tile;
        }
        else if(activeTotem == tile) {
            activeTotem = null;
        }
    }

    public void render(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker) {
        if(activeTotem == null)
            return;
        var mc = Minecraft.getInstance();
        Profiler.get().push("totemic.ceremonyHUD");

        if(activeTotem.isRemoved() || activeTotem.getLevel() != mc.level || activeTotem.getTotemState() instanceof StateTotemEffect) {
            activeTotem = null;
            Profiler.get().pop();
            return;
        }

        final int hudX = (guiGraphics.guiWidth() - HUD_WIDTH) / 2 + TotemicConfig.CLIENT.ceremonyHudPositionX.get();
        final int hudY = (guiGraphics.guiHeight() - HUD_HEIGHT) / 2 + TotemicConfig.CLIENT.ceremonyHudPositionY.get();

        var poseStack = guiGraphics.pose();
        poseStack.translate(hudX, hudY);

        var state = activeTotem.getTotemState();
        if(state instanceof StateSelection s)
            renderSelectionHUD(s, guiGraphics, deltaTracker);
        else if(state instanceof StateStartup s)
            renderStartupHUD(s, guiGraphics, deltaTracker);
        else if(state instanceof StateCeremonyEffect s)
            renderCeremonyEffectHUD(s, guiGraphics, deltaTracker);

        Profiler.get().pop();
    }

    private void renderSelectionHUD(StateSelection state, GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker) {
        final int texW = 128, texH = 64;

        //Background
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, SELECTION_HUD_TEXTURE, 0, 0,  0, 0,  HUD_WIDTH, HUD_HEIGHT,  texW, texH);

        var font = Minecraft.getInstance().font;
        int headerX = (HUD_WIDTH - font.width(SELECTION_TEXT)) / 2;
        guiGraphics.text(font, SELECTION_TEXT, headerX, 2, 0xC8000000, false);

        //Instruments
        var selectors = state.getSelectors();
        //Assuming that we have at most 1 selector to render
        if(!selectors.isEmpty()) {
            var item = selectors.get(0).getItem();
            guiGraphics.fakeItem(item, 40, 12);
        }
    }

    private void renderStartupHUD(StateStartup state, GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker) {
        final int texW = 128, texH = 64;
        final int barW = 104, barH = 7;
        var cer = state.getCeremony();

        //Background
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, CEREMONY_HUD_TEXTURE, 0, 0,  0, 0,  HUD_WIDTH, HUD_HEIGHT,  texW, texH);

        //Symbols
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, CEREMONY_HUD_TEXTURE, 1, 10,  16, 48,  9, 9,   8,  8,  texW, texH); //Note
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, CEREMONY_HUD_TEXTURE, 1, 20,   0, 48,  9, 9,  16, 16,  texW, texH); //Clock

        //Bars
        // TODO: Add an alternative to BlitRenderState that supports float values for width and height
        int musicW = Math.round(state.getTotalMusic() / (float)cer.getMusicNeeded() * barW);
        float partialTick = deltaTracker.getGameTimeDeltaPartialTick(true);
        int timeW = Math.round(Math.min((state.getTime() + partialTick) / cer.getAdjustedMaxStartupTime(Minecraft.getInstance().level.getDifficulty()), 1.0F) * barW);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, CEREMONY_HUD_TEXTURE, 11, 11,  0, 32,  musicW, barH,  musicW, barH,  texW, texH);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, CEREMONY_HUD_TEXTURE, 11, 21,  0, 32,  timeW,  barH,  timeW,  barH,  texW, texH);

        //Ceremony name
        var name = cer.getDisplayName();
        var font = Minecraft.getInstance().font;
        int nameX = (HUD_WIDTH - font.width(name)) / 2;
        guiGraphics.text(font, name, nameX, 2, 0xC8000000, false);
    }

    private void renderCeremonyEffectHUD(StateCeremonyEffect state, GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker) {
        final int texW = 128, texH = 64;
        final int barW = 104, barH = 7;
        var cer = state.getCeremony();

        //Background
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, CEREMONY_HUD_TEXTURE, 0, 0,  0, 0,  HUD_WIDTH, HUD_HEIGHT,  texW, texH);

        //Clock symbol
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, CEREMONY_HUD_TEXTURE, 1, 20,   0, 48,  9, 9,  16, 16,  texW, texH);

        //Time bar
        float partialTick = deltaTracker.getGameTimeDeltaPartialTick(true);
        int timeW = Math.round(Mth.clamp(1.0F - (state.getTime() + partialTick) / state.getEffectTime(), 0.0F, 1.0F) * barW);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, CEREMONY_HUD_TEXTURE, 11, 21,  0, 32,  timeW,  barH,  timeW,  barH,  texW, texH);

        //Ceremony name
        var name = cer.getDisplayName();
        var font = Minecraft.getInstance().font;
        int nameX = (HUD_WIDTH - font.width(name)) / 2;
        guiGraphics.text(font, name, nameX, 2, 0xC8000000, false);
    }
}
