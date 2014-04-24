package totemic_commons.pokefenn.item.armour;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemTotemArmour extends ItemArmor implements ISpecialArmor
{
    public ItemTotemArmour(int armourType, String name)
    {
        super(ArmourMaterials.totemArmour, -1, armourType);
        setUnlocalizedName(name);
        setCreativeTab(Totemic.tabsTotem);
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot)
    {
        return new ArmorProperties(-1, 0, 0);
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
    }
}
