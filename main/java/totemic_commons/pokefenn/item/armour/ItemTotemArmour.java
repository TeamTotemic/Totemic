package totemic_commons.pokefenn.item.armour;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.client.rendering.armour.TotemArmourHead;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemTotemArmour extends ItemArmor implements ISpecialArmor
{
    public int armourType;

    public ItemTotemArmour(int armourType, String name)
    {
        super(ArmourMaterials.totemArmour, 0, armourType);
        setUnlocalizedName(name);
        setCreativeTab(Totemic.tabsTotem);
        //setMaxDamage(ArmourMaterials.totemArmour.getDurability(armourType));
        this.armourType = armourType;
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot)
    {
        return new ArmorProperties(1, 1, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase par1EntityLiving, ItemStack itemStack, int armorSlot)
    {
        if(armorSlot == 0)
        {

        }
        if(armorSlot == 1)
        {

        }
        if(armorSlot == 2)
        {

        }
        if(armorSlot == 3)
        {

        }

        return null;
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot)
    {
        if(slot == 0)
            return 2;
        else if(slot == 1)
            return 4;
        else if(slot == 2)
            return 3;
        else
            return 1;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot)
    {
        stack.damageItem(damage % 2, entity);
    }
}
