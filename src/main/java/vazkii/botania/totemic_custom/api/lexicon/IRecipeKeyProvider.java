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

import net.minecraft.item.ItemStack;

/**
 * Have an Item implement this to customize how {@link LexiconRecipeMappings}
 * maps your item stacks to a string representation, rather than the default
 * "name@meta". Useful if your item uses NBT tags.
 *
 * TODO: Make it usable as a Capability
 */
public interface IRecipeKeyProvider
{
    String getKey(ItemStack stack);
}
