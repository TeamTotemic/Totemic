package totemic_commons.pokefenn.item.tool.bauble;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.totem.TotemUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemHerculesBauble extends ItemTotemic implements IBauble
{

    public ItemHerculesBauble()
    {
        super();
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.HERCULESE_BAUBLES_NAME);
        setMaxStackSize(1);
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack)
    {
        return BaubleType.AMULET;
    }

    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return EnumRarity.rare;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player)
    {
        if(player.worldObj.getWorldTime() % 80L == 0)
        {
            EntityPlayer entityPlayer = (EntityPlayer) player;
            if(TotemUtil.getArmourAmounts(entityPlayer) == 0)
            {
                IInventory baubleInventory = BaublesApi.getBaubles(entityPlayer);
                if(baubleInventory.getStackInSlot(0) != null)
                {
                    EntityItem bauble = new EntityItem(player.worldObj, player.posX + 0.5D, player.posY + 0.5D, player.posZ + 0.5D, new ItemStack(ModItems.herculeseBauble));
                    entityPlayer.worldObj.spawnEntityInWorld(bauble);
                    baubleInventory.setInventorySlotContents(0, null);
                }
            }
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
        return TotemUtil.getArmourAmounts((EntityPlayer) player) > 0;
    }

    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player)
    {
        return true;
    }
}
