package totemic_commons.pokefenn.totempedia.page;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import totemic_commons.pokefenn.api.music.MusicEnum;
import totemic_commons.pokefenn.api.recipe.CeremonyRegistry;
import totemic_commons.pokefenn.util.TotemUtil;
import vazkii.botania.totemic_custom.api.internal.IGuiLexiconEntry;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class PageCeremony extends PageRecipe
{
    public ResourceLocation ceremonyOverlay = new ResourceLocation("totemic:textures/gui/ceremonyOverlay.png");

    public int ceremonyId;

    public PageCeremony(String unlocalizedName, int ceremonyId)
    {
        super(unlocalizedName);
        this.ceremonyId = ceremonyId;
    }

    @Override
    public void renderScreen(IGuiLexiconEntry gui, int mx, int my)
    {
        if(ceremonyId >= 0)
        {
            CeremonyRegistry ceremony = CeremonyRegistry.ceremonyRegistry.get(ceremonyId);

            TextureManager render = Minecraft.getMinecraft().renderEngine;
            FontRenderer font = Minecraft.getMinecraft().fontRenderer;
            MusicEnum[] musicEnums = ceremony.getCeremonyEffect().getMusicEnums();
            String musicNeeded = TotemUtil.getMusicNeeded(ceremony.getCeremonyActivation().getMusicNeeded());
            String time = Integer.toString(ceremony.getCeremonyActivation().getMaximumStartupTime().getTime() / 20);

            renderItemAtGridPos(gui, 2, 0, TotemUtil.getItemStackFromEnum(musicEnums[0]), false);
            renderItemAtGridPos(gui, 2, 1, TotemUtil.getItemStackFromEnum(musicEnums[1]), false);
            renderItemAtGridPos(gui, 2, 2, TotemUtil.getItemStackFromEnum(musicEnums[2]), false);
            renderItemAtGridPos(gui, 2, 3, TotemUtil.getItemStackFromEnum(musicEnums[3]), false);

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            //String musicNeeded = StatCollector.translateToLocal("totemicmisc.musicNeeded");
            font.drawString(musicNeeded, gui.getLeft() + gui.getWidth() / 2 - font.getStringWidth(musicNeeded) / 2, gui.getTop() + 135, 0x66000000);
            font.drawString(StatCollector.translateToLocal("totemicmisc.timeForCeremony")+ " " + time + StatCollector.translateToLocal("totemicmisc.seconds"), gui.getLeft() + gui.getWidth() / 2 - font.getStringWidth(StatCollector.translateToLocal("totemicmisc.timeForCeremony") + " " + time + StatCollector.translateToLocal("totemicmisc.seconds")) / 2, gui.getTop() + 145, 0x66000000);

            GL11.glDisable(GL11.GL_BLEND);

            render.bindTexture(ceremonyOverlay);

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(1F, 1F, 1F, 1F);
            ((GuiScreen) gui).drawTexturedModalRect(gui.getLeft(), gui.getTop(), 0, 0, gui.getWidth(), gui.getHeight());
            GL11.glDisable(GL11.GL_BLEND);

        }
    }
}
