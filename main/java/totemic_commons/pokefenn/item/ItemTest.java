package totemic_commons.pokefenn.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import totemic_commons.pokefenn.Totemic;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemTest extends ItemTotemic
{
    public ItemTest()
    {
        setCreativeTab(Totemic.tabsTotem);
        setUnlocalizedName("test");
    }

    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if(!par2World.isRemote)
        {
            System.out.println(par2World.getBlock(1300, 37, 666));
        }

        return par1ItemStack;
    }
}
