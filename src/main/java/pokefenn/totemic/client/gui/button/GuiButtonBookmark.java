package pokefenn.totemic.client.gui.button;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import pokefenn.totemic.client.TotemicRenderHelper;
import pokefenn.totemic.client.gui.GuiLexicon;

public class GuiButtonBookmark extends GuiButton
{
    private GuiLexicon gui = new GuiLexicon();

    public GuiButtonBookmark(int buttonId, int x, int y, GuiLexicon gui, String str)
    {
        super(buttonId, x, y, gui.bookmarkWidth(str) + 5, 11, str);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
        gui.drawBookmark(x, y, displayString, false);
        hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
        int k = getHoverState(hovered);

        List<String> tooltip = new ArrayList<>();
        if(displayString.equals("+"))
            tooltip.add(I18n.format("totemicmisc.clickToAdd"));
        else
        {
            tooltip.add(I18n.format("totemicmisc.bookmark", id - GuiLexicon.BOOKMARK_START + 1));
            tooltip.add(TextFormatting.GRAY + I18n.format("totemicmisc.clickToSee"));
            tooltip.add(TextFormatting.GRAY + I18n.format("totemicmisc.shiftToRemove"));
        }

        int tooltipY = (tooltip.size() + 1) * 5;
        if(k == 2)
            TotemicRenderHelper.renderTooltip(mouseX, mouseY + tooltipY, tooltip);
    }

}
