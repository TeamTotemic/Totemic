/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Totemic Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * The files have been edited for use in Totemic, but was created by Vazkii in the start, so go give him hugs!
 */
package pokefenn.totemic.api.lexicon;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pokefenn.totemic.api.internal.IGuiLexiconEntry;

public abstract class LexiconPage
{
    public String unlocalizedName;

    public LexiconPage(String unlocalizedName)
    {
        this.unlocalizedName = unlocalizedName;
    }

    /**
     * Does the rendering for this page.
     *
     * @param gui The active GuiScreen
     * @param mx  The mouse's relative X position.
     * @param my  The mouse's relative Y position.
     */
    @SideOnly(Side.CLIENT)
    public abstract void renderScreen(IGuiLexiconEntry gui, int mx, int my);

    /**
     * Called per update tick.
     */
    @SideOnly(Side.CLIENT)
    public void updateScreen()
    {
        // NO-OP
    }

    /**
     * Called when this page is added to a lexicon entry
     * @param entry The entry this page has been added to
     * @param index The page number
     */
    public void onPageAdded(LexiconEntry entry, int index)
    {
    }

    public String getUnlocalizedName()
    {
        return unlocalizedName;
    }
}
