package totemic_commons.pokefenn.item.equipment.weapon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import totemic_commons.pokefenn.entity.projectile.EntityBaseDart;
import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemBlowGun extends ItemTotemic
{

    public ItemBlowGun()
    {
        super(Strings.BLOW_DART_NAME);
        setMaxStackSize(1);
        setMaxDamage(152);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer player, int iDunnoWhatThisDoes)
    {
        int charge = this.getMaxItemUseDuration(itemStack) - iDunnoWhatThisDoes;

        //if(player.inventory.hasItem(ModItems.darts))
        {
            float moveSpeedThingy = (float) charge / 20.0F;
            moveSpeedThingy = (moveSpeedThingy * moveSpeedThingy + moveSpeedThingy * 2.0F) / 3.0F;

            if((double) moveSpeedThingy < 0.1D)
                return;

            if(moveSpeedThingy > 1.0F)
                moveSpeedThingy = 1.0F;

            int metadata = 0;

            for(int i = 0; i < player.inventory.getSizeInventory(); i++)
            {
                if(player.inventory.getStackInSlot(i) != null)
                {
                    ItemStack playerStack = player.inventory.getStackInSlot(i);

                    //if(playerStack.getItem() == ModItems.darts)
                    {
                        metadata = playerStack.getItemDamage();
                        if(!world.isRemote)
                            playerStack.stackSize--;
                        break;
                    }
                }
            }


            EntityBaseDart entity = new EntityBaseDart(world, player, moveSpeedThingy * 2.0F, metadata);
            if(!world.isRemote)
            {
                itemStack.damageItem(1, player);
                world.spawnEntityInWorld(entity);
            }

        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return EnumRarity.uncommon;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        //if(player.inventory.hasItem(ModItems.darts))
        {
            player.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        }

        return itemStack;
    }

    @Override
    public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer player)
    {
        return itemStack;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack itemStack)
    {
        return 72000;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack itemStack)
    {
        return EnumAction.bow;
    }
}
