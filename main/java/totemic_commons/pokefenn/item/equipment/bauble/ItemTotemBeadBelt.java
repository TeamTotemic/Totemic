package totemic_commons.pokefenn.item.equipment.bauble;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.bauble.ITotemBauble;
import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemTotemBeadBelt extends ItemTotemic implements IBauble, ITotemBauble
{

    public ItemTotemBeadBelt()
    {
        setMaxStackSize(1);
        setUnlocalizedName(Strings.TOTEM_BEAD_BELT_NAME);
    }

    @Override
    public int getTotemEfficiency(World world, ItemStack itemStack, EntityPlayer player)
    {
        return 1;
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
}
