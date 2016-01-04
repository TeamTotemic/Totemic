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
package vazkii.botania.totemic_custom.api.lexicon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * Any block that implements this can be right clicked with
 * a Lexica Botania to open a entry page.
 */
public interface ILexiconable
{

    /**
     * Gets the lexicon entry to open at this location. null works too.
     */
    public LexiconEntry getEntry(World world, BlockPos pos, EntityPlayer player, ItemStack lexicon);

}
