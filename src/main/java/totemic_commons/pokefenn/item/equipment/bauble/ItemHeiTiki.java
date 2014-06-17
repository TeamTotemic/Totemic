package totemic_commons.pokefenn.item.equipment.bauble;

import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemHeiTiki extends ItemTotemic// implements IBauble
{

    public ItemHeiTiki()
    {
        super(Strings.HEI_TIKI_NAME);
        setMaxStackSize(1);
    }

    /*
    @Override
    public BaubleType getBaubleType(ItemStack itemstack)
    {
        return BaubleType.AMULET;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player)
    {
        if(player instanceof EntityPlayer && player.isInWater())
        {

        }

        if(!player.worldObj.isRemote && player.isInWater())
        {
            player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 20, 0));
            player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 30, 0));


        }

        if(player.isInWater() && player.isSneaking())
        {
            player.moveFlying(0F, 1F, 0.1F);
        }
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
