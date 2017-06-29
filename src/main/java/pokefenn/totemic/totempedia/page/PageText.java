/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Totemic Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 */
package pokefenn.totemic.totempedia.page;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pokefenn.totemic.api.internal.IGuiLexiconEntry;
import pokefenn.totemic.api.lexicon.LexiconPage;

public class PageText extends LexiconPage
{
    public PageText(String unlocalizedName)
    {
        super(unlocalizedName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderScreen(IGuiLexiconEntry gui, int mx, int my)
    {
        int width = gui.getWidth() - 34;
        int x = gui.getLeft() + 18;
        int y = gui.getTop() + 2;

        renderText(x, y, width, gui.getHeight(), getUnlocalizedName());
    }

    @SideOnly(Side.CLIENT)
    public static void renderText(int x, int y, int width, int height, String unlocalizedText)
    {
        FontRenderer renderer = Minecraft.getMinecraft().fontRenderer;
        boolean unicode = renderer.getUnicodeFlag();
        renderer.setUnicodeFlag(true);
        String text = I18n.format(unlocalizedText);
        String[] textEntries = text.split("<br>");

        for(String entry : textEntries)
        {
            for(String line : renderer.listFormattedStringToWidth(entry, width)) // This method handles both format and line wraps, no need to write one yourself. Fix lines going off the edge.
            {
                y += 10;
                renderer.drawString(line, x, y, 0);
            }

            y += 10;
        }

        renderer.setUnicodeFlag(unicode);
    }
}
