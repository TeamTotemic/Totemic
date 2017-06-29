package pokefenn.totemic.totempedia.page;

import java.util.Objects;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag.TooltipFlags;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.internal.IGuiLexiconEntry;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.client.RenderHelper;
import pokefenn.totemic.lib.Resources;
import pokefenn.totemic.util.TotemUtil;

public class PageCeremony extends PageRecipe
{
    private static final ResourceLocation ceremonyOverlay = new ResourceLocation(Resources.GUI_CEREMONY_OVERLAY);

    public Ceremony ceremony;

    public PageCeremony(String unlocalizedName, Ceremony ceremony)
    {
        super(unlocalizedName);
        this.ceremony = Objects.requireNonNull(ceremony);
    }

    @Override
    public void renderScreen(IGuiLexiconEntry gui, int mx, int my)
    {
        relativeMouseX = mx;
        relativeMouseY = my;

        Minecraft mc = Minecraft.getMinecraft();
        TextureManager render = mc.renderEngine;
        FontRenderer font = mc.fontRenderer;
        MusicInstrument[] instruments = ceremony.getInstruments();

        int instrLeft = gui.getLeft() + gui.getWidth() / 2 - 10 * instruments.length;
        for(int i = 0; i < instruments.length; i++)
        {
            ItemStack item = instruments[i].getItem();
            if(!item.isEmpty())
                renderItem(gui, instrLeft + 20 * i, gui.getTop() + 31, item, false);
        }

        if(!tooltipStack.isEmpty())
            RenderHelper.renderTooltip(mx, my, tooltipStack.getTooltip(mc.player, TooltipFlags.NORMAL));

        String text = I18n.format("totemicmisc.musicSelector");
        font.drawString(text, gui.getLeft() + gui.getWidth() / 2 - font.getStringWidth(text) / 2, gui.getTop() + 14, 0x999999);

        boolean unicode = font.getUnicodeFlag();
        font.setUnicodeFlag(true);

        text = I18n.format(TotemUtil.getMusicNeeded(ceremony.getMusicNeeded()));
        font.drawString(text, gui.getLeft() + gui.getWidth() / 2 - font.getStringWidth(text) / 2, gui.getTop() + 90, 0x000000);

        text = I18n.format(getUnlocalizedName());
        font.drawString(text, gui.getLeft() + gui.getWidth() / 2 - font.getStringWidth(text) / 2, gui.getTop() + 150, 0x000000);

        font.setUnicodeFlag(unicode);

        render.bindTexture(ceremonyOverlay);

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1F, 1F, 1F, 1F);
        ((GuiScreen) gui).drawTexturedModalRect(gui.getLeft(), gui.getTop(), 0, 0, gui.getWidth(), gui.getHeight());
        GlStateManager.disableBlend();

        tooltipStack = tooltipContainerStack = ItemStack.EMPTY;
        tooltipEntry = false;
        mouseDownLastTick = Mouse.isButtonDown(0);
    }
}
