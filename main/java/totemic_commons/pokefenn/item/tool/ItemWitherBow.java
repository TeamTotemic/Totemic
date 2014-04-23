package totemic_commons.pokefenn.item.tool;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemWitherBow extends ItemTotemic
{

    public ItemWitherBow()
    {
        super();
        setUnlocalizedName(Strings.WITHER_BOW_NAME);
        setMaxStackSize(1);
        setMaxDamage(250);
    }

    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.bow;
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));

        return stack;
    }

    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }

    public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer player)
    {
        int j = this.getMaxItemUseDuration(itemStack);

        float f = (float) j / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;

        if((double) f < 0.1D)
        {
            return itemStack;
        }

        if(f > 1.0F)
        {
            f = 1.0F;
        }

        EntityWitherSkull head = new EntityWitherSkull(world);

        if(!world.isRemote)
        {
            if(player.inventory.hasItem(Item.getItemFromBlock(Blocks.skull)))
            {

            }
        }

        return itemStack;
    }

}
