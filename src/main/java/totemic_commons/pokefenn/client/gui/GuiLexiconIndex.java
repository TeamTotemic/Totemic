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
package totemic_commons.pokefenn.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import totemic_commons.pokefenn.client.ClientTickHandler;
import totemic_commons.pokefenn.client.gui.button.GuiButtonBack;
import totemic_commons.pokefenn.client.gui.button.GuiButtonInvisible;
import totemic_commons.pokefenn.client.gui.button.GuiButtonPage;
import vazkii.botania.totemic_custom.api.lexicon.ILexicon;
import vazkii.botania.totemic_custom.api.lexicon.LexiconCategory;
import vazkii.botania.totemic_custom.api.lexicon.LexiconEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuiLexiconIndex extends GuiLexicon implements IParented
{

    LexiconCategory category;
    String title;
    int page = 0;

    GuiButton leftButton, rightButton;
    GuiLexicon parent;

    List<LexiconEntry> entriesToDisplay = new ArrayList();

    public GuiLexiconIndex(LexiconCategory category)
    {
        this.category = category;
        title = StatCollector.translateToLocal(category.getUnlocalizedName());
        parent = new GuiLexicon();
    }

    @Override
    void drawHeader()
    {
        // NO-OP
    }

    @Override
    String getTitle()
    {
        return title;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        buttonList.add(new GuiButtonBack(12, left + guiWidth / 2 - 8, top + guiHeight + 2));
        buttonList.add(leftButton = new GuiButtonPage(13, left, top + guiHeight - 10, false));
        buttonList.add(rightButton = new GuiButtonPage(14, left + guiWidth - 18, top + guiHeight - 10, true));

        entriesToDisplay.clear();
        ILexicon lex = (ILexicon) stackUsed.getItem();
        for(LexiconEntry entry : category.entries)
        {
            entriesToDisplay.add(entry);
        }
        Collections.sort(entriesToDisplay);

        updatePageButtons();
        populateIndex();
    }

    @Override
    void populateIndex()
    {
        for(int i = page * 12; i < (page + 1) * 12; i++)
        {
            GuiButtonInvisible button = (GuiButtonInvisible) buttonList.get(i - page * 12);
            LexiconEntry entry = i >= entriesToDisplay.size() ? null : entriesToDisplay.get(i);
            if(entry != null)
                button.displayString = "" + (entry.isPriority() ? EnumChatFormatting.ITALIC : "") + StatCollector.translateToLocal(entry.getUnlocalizedName());
            else button.displayString = "";
        }
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if(par1GuiButton.id >= BOOKMARK_START)
            handleBookmark(par1GuiButton);
        else
            switch(par1GuiButton.id)
            {
                case 12:
                    mc.displayGuiScreen(parent);
                    ClientTickHandler.notifyPageChange();
                    break;
                case 13:
                    page--;
                    updatePageButtons();
                    populateIndex();
                    ClientTickHandler.notifyPageChange();
                    break;
                case 14:
                    page++;
                    updatePageButtons();
                    populateIndex();
                    ClientTickHandler.notifyPageChange();
                    break;
                default:
                    int index = par1GuiButton.id + page * 12;
                    if(index >= entriesToDisplay.size())
                        return;

                    LexiconEntry entry = entriesToDisplay.get(index);
                    mc.displayGuiScreen(new GuiLexiconEntry(entry, this));
                    ClientTickHandler.notifyPageChange();
            }
    }

    public void updatePageButtons()
    {
        leftButton.enabled = page != 0;
        rightButton.enabled = page < (entriesToDisplay.size() - 1) / 12;
    }

    @Override
    public void setParent(GuiLexicon gui)
    {
        parent = gui;
    }
}
