package totemic_commons.pokefenn.totempedia.page;

import java.util.Objects;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.client.RenderHelper;
import totemic_commons.pokefenn.util.TotemUtil;
import vazkii.botania.totemic_custom.api.internal.IGuiLexiconEntry;

public class PageCeremony extends PageRecipe
{
    public ResourceLocation ceremonyOverlay = new ResourceLocation("totemic:textures/gui/ceremonyOverlay.png");

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

        TextureManager render = Minecraft.getMinecraft().renderEngine;
        FontRenderer font = Minecraft.getMinecraft().fontRendererObj;
        MusicInstrument[] instruments = ceremony.getInstruments();

        int instrLeft = gui.getLeft() + gui.getWidth() / 2 - 10 * instruments.length;
        for(int i = 0; i < instruments.length; i++)
        {
            ItemStack item = instruments[i].getItem();
            if(item != null)
                renderItem(gui, instrLeft + 20 * i, gui.getTop() + 31, item, false);
        }

        if(tooltipStack != null)
            RenderHelper.renderTooltip(mx, my, tooltipStack.getTooltip(Minecraft.getMinecraft().player, false));

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

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        ((GuiScreen) gui).drawTexturedModalRect(gui.getLeft(), gui.getTop(), 0, 0, gui.getWidth(), gui.getHeight());
        GL11.glDisable(GL11.GL_BLEND);

        tooltipStack = tooltipContainerStack = null;
        tooltipEntry = false;
        mouseDownLastTick = Mouse.isButtonDown(0);
    }
}
