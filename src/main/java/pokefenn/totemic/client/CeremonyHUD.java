package pokefenn.totemic.client;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.block.totem.entity.StateCeremonyEffect;
import pokefenn.totemic.block.totem.entity.StateSelection;
import pokefenn.totemic.block.totem.entity.StateStartup;
import pokefenn.totemic.block.totem.entity.StateTotemEffect;
import pokefenn.totemic.block.totem.entity.TotemBaseBlockEntity;

public enum CeremonyHUD implements IGuiOverlay {
    INSTANCE;

    private static final ResourceLocation SELECTION_HUD_TEXTURE = new ResourceLocation(TotemicAPI.MOD_ID, "textures/gui/selection_hud.png");
    private static final ResourceLocation CEREMONY_HUD_TEXTURE = new ResourceLocation(TotemicAPI.MOD_ID, "textures/gui/ceremony_hud.png");

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
    public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
        if(activeTotem == null)
            return;
        if(activeTotem.isRemoved() || activeTotem.getTotemState() instanceof StateTotemEffect) {
            activeTotem = null;
            return;
        }

        var mc = gui.getMinecraft();
        gui.getMinecraft().getProfiler().push("totemic.ceremonyHUD");

        //TODO: Make the position on screen configurable
        final int hudX = (screenWidth - HUD_WIDTH) / 2;
        final int hudY = (screenHeight - HUD_HEIGHT) / 2 - 70;

        poseStack.pushPose();
        poseStack.translate(hudX, hudY, 0);

        var state = activeTotem.getTotemState();
        if(state instanceof StateSelection s)
            renderSelectionHUD(s, gui, poseStack, partialTick, hudX, hudY);
        else if(state instanceof StateStartup s)
            renderStartupHUD(s, gui, poseStack, partialTick, hudX, hudY);
        else if(state instanceof StateCeremonyEffect s)
            renderCeremonyEffectHUD(s, gui, poseStack, partialTick, hudX, hudY);

        poseStack.popPose();

        mc.getProfiler().pop();
    }

    private void renderSelectionHUD(StateSelection state, ForgeGui gui, PoseStack poseStack, float partialTick, int hudX, int hudY) {
        final int texW = 128, texH = 64;
        gui.setupOverlayRenderState(true, false, SELECTION_HUD_TEXTURE);

        //Background
        GuiComponent.blit(poseStack, 0, 0,  0, 0,  HUD_WIDTH, HUD_HEIGHT,  texW, texH);

        //Header text
        var headerText = I18n.get("totemic.hud.selection");
        int headerX = (HUD_WIDTH - gui.getFont().width(headerText)) / 2;
        gui.getFont().draw(poseStack, headerText, headerX, 1, 0xC8000000);

        //Instruments
        var selectors = state.getSelectors();
        //Assuming that we have only 1 selector to render
        var item = selectors.get(0).getItem();
        gui.getMinecraft().getItemRenderer().renderGuiItem(item, hudX + 40, hudY + 12);
    }

    private void renderStartupHUD(StateStartup state, ForgeGui gui, PoseStack poseStack, float partialTick, int hudX, int hudY) {

    }

    private void renderCeremonyEffectHUD(StateCeremonyEffect state, ForgeGui gui, PoseStack poseStack, float partialTick, int hudX, int hudY) {

    }
}
