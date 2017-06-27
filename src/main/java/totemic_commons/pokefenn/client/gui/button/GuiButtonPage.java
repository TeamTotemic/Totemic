/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Totemic Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Jan 16, 2014, 4:52:06 PM (GMT)]
 */
package totemic_commons.pokefenn.client.gui.button;

import java.util.Collections;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import totemic_commons.pokefenn.client.RenderHelper;
import totemic_commons.pokefenn.client.gui.GuiLexicon;

public class GuiButtonPage extends GuiButton
{
    private boolean right;

    public GuiButtonPage(int buttonId, int x, int y, boolean right)
    {
        super(buttonId, x, y, 18, 10, "");
        this.right = right;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
        if(enabled)
        {
            hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
            int k = getHoverState(hovered);

            mc.renderEngine.bindTexture(GuiLexicon.texture);
            GlStateManager.color(1F, 1F, 1F, 1F);
            drawTexturedModalRect(x, y, k == 2 ? 18 : 0, right ? 180 : 190, 18, 10);

            if(k == 2)
                RenderHelper.renderTooltip(mouseX, mouseY, Collections.singletonList(I18n.format(right ? "totemicmisc.nextPage" : "totemicmisc.prevPage")));
        }
    }

}
