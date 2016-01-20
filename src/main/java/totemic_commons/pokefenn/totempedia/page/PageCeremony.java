package totemic_commons.pokefenn.totempedia.page;

import java.util.Objects;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.client.RenderHelper;
import totemic_commons.pokefenn.util.TotemUtil;
import vazkii.botania.totemic_custom.api.internal.IGuiLexiconEntry;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
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
        String musicNeeded = TotemUtil.getMusicNeededLocalized(ceremony.getMusicNeeded());
        MusicInstrument[] instruments = ceremony.getInstruments();

        int instrLeft = gui.getLeft() + gui.getWidth() / 2 - 10 * instruments.length;
        for(int i = 0; i < instruments.length; i++)
        {
            ItemStack item = instruments[i].getItem();
            if(item != null)
                renderItem(gui, instrLeft + 20 * i, gui.getTop() + 31, item, false);
        }

        if(tooltipStack != null)
            RenderHelper.renderTooltip(mx, my, tooltipStack.getTooltip(Minecraft.getMinecraft().thePlayer, false));

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        PageText.renderText(gui.getLeft() + gui.getWidth() / 2 - font.getStringWidth(musicNeeded) / 3, gui.getTop() + 90, font.getStringWidth(musicNeeded), 150, musicNeeded);

        font.drawString(StatCollector.translateToLocal("totemicmisc.musicSelector"), gui.getLeft() + gui.getWidth() / 2 - font.getStringWidth(StatCollector.translateToLocal("totemicmisc.musicSelector")) / 2, gui.getTop() + 14, 0x66000000);
        /*font.drawString(musicNeeded, gui.getLeft() + gui.getWidth() / 2 - font.getStringWidth(musicNeeded) / 2, gui.getTop() + 90, 0x66000000);
        font.drawString(StatCollector.translateToLocal("totemicmisc.timeForCeremony") + time + StatCollector.translateToLocal("totemicmisc.seconds"), gui.getLeft() + gui.getWidth() / 2 - font.getStringWidth(StatCollector.translateToLocal("totemicmisc.timeForCeremony") + time + StatCollector.translateToLocal("totemicmisc.seconds")) / 2, gui.getTop() + 105, 0x66000000);
        font.drawString(StatCollector.translateToLocal("totemicmisc.overTime") + " " + (ceremony.getCeremonyActivation().getTimeState() == TimeStateEnum.OVER_TIME ? StatCollector.translateToLocal("totemicmisc.capitalTrue") : StatCollector.translateToLocal("totemicmisc.capitalFalse")), gui.getLeft() + gui.getWidth() / 2 - font.getStringWidth(StatCollector.translateToLocal("totemicmisc.overTime") + " " + (ceremony.getCeremonyActivation().getTimeState() == TimeStateEnum.OVER_TIME)) / 2, gui.getTop() + 120, 0x66000000);
        //font.drawString(StatCollector.translateToLocal(reference), gui.getLeft() + gui.getWidth() / 2 - font.getStringWidth(StatCollector.translateToLocal(reference)) / 2, gui.getTop() + 161, 0x66000000);
        */

        PageText.renderText(gui.getLeft() + gui.getWidth() / 2 - font.getStringWidth(StatCollector.translateToLocal(getUnlocalizedName())) / 3, gui.getTop() + 150, 150, 150, StatCollector.translateToLocal(getUnlocalizedName()));

        GL11.glDisable(GL11.GL_BLEND);

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
