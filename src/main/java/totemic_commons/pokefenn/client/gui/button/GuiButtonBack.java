/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Totemic Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Jan 14, 2014, 9:54:21 PM (GMT)]
 */
package totemic_commons.pokefenn.client.gui.button;

import java.util.Collections;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import totemic_commons.pokefenn.client.RenderHelper;
import totemic_commons.pokefenn.client.gui.GuiLexicon;

public class GuiButtonBack extends GuiButton
{
    public GuiButtonBack(int buttonId, int x, int y)
    {
        super(buttonId, x, y, 18, 9, "");
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
        hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
        int k = getHoverState(hovered);

        mc.renderEngine.bindTexture(GuiLexicon.texture);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        drawTexturedModalRect(x, y, 36, k == 2 ? 180 : 189, 18, 9);

        List<String> tooltip = getTooltip();
        int tooltipY = (tooltip.size() - 1) * 10;
        if(k == 2)
            RenderHelper.renderTooltip(mouseX, mouseY + tooltipY, tooltip);
    }

    protected List<String> getTooltip()
    {
        return Collections.singletonList(I18n.format("totemicmisc.back"));
    }

}
