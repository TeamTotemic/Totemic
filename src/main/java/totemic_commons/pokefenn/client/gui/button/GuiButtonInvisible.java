/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Jan 14, 2014, 8:34:01 PM (GMT)]
 */
package totemic_commons.pokefenn.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonInvisible extends GuiButton
{

    public GuiButtonInvisible(int buttonId, int x, int y, int width, int height, String buttonText)
    {
        super(buttonId, x, y, width, height, buttonText);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
        hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
        int k = getHoverState(hovered);

        boolean unicode = mc.fontRenderer.getUnicodeFlag();
        mc.fontRenderer.setUnicodeFlag(true);
        mc.fontRenderer.drawString(displayString, x + (k == 2 ? 5 : 0), y + (height - 8) / 2, 0);
        mc.fontRenderer.setUnicodeFlag(unicode);
    }

}
