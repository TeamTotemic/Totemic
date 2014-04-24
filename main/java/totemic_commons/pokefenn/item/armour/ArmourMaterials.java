package totemic_commons.pokefenn.item.armour;

import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ArmourMaterials
{
    public static ItemArmor.ArmorMaterial totemArmour = EnumHelper.addArmorMaterial("totemArmour", 10, new int[]{2, 4, 3, 1}, 18);
}
