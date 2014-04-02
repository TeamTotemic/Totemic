package totemic_commons.pokefenn.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import totemic_commons.pokefenn.ModItems;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 23/12/13
 * Time: 13:54
 */
public class OreDictionaryTotemic
{

    public static void init()
    {

        OreDictionary.registerOre("plant", new ItemStack(Block.vine));
        OreDictionary.registerOre("plant", new ItemStack(Item.seeds));
        OreDictionary.registerOre("plant", new ItemStack(Item.carrot));
        OreDictionary.registerOre("plant", new ItemStack(Item.melonSeeds));
        OreDictionary.registerOre("plant", new ItemStack(Item.pumpkinSeeds));
        OreDictionary.registerOre("plant", new ItemStack(Item.potato));
        OreDictionary.registerOre("plant", new ItemStack(Item.poisonousPotato));
        for (int i = 0; i <= 3; i++)
        {
            OreDictionary.registerOre("plant", new ItemStack(Block.sapling, 1, i));
        }

        for (int i = 0; i <= 4; i++)
        {
            OreDictionary.registerOre("knife", new ItemStack(ModItems.totemWhittlingKnife, 1, i));
        }

        //OreDictionary.registerOre("plant", new ItemStack(GameRegistry.findItem("XyCraft", "tomato")));

    }

}
