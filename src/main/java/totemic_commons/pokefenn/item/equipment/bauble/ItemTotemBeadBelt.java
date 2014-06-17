package totemic_commons.pokefenn.item.equipment.bauble;

import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemTotemBeadBelt extends ItemTotemic// implements IBauble, ITotemBauble
{

    public ItemTotemBeadBelt()
    {
        super(Strings.TOTEM_BEAD_BELT_NAME);
        setMaxStackSize(1);
    }

    /*
    @Override
    public int getTotemEfficiency(World world, ItemStack itemStack, EntityPlayer player)
    {
        return 1;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player)
    {
        IInventory baubleInventory = BaublesApi.getBaubles(player);
        if(baubleInventory.getStackInSlot(3) == null && !player.worldObj.isRemote && canEquip(par1ItemStack, player) && baubleInventory.isItemValidForSlot(3, par1ItemStack))
        {
            baubleInventory.setInventorySlotContents(3, new ItemStack(this, 1, 0));
            player.destroyCurrentEquippedItem();
            onEquipped(par1ItemStack, player);
        }

        return par1ItemStack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        itemIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Strings.TOTEM_BEAD_BELT_NAME);
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack)
    {
        return BaubleType.BELT;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player)
    {
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player)
    {

    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player)
    {

    }

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player)
    {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player)
    {
        return true;
    }
    */
}
