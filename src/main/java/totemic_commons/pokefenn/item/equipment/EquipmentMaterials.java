package totemic_commons.pokefenn.item.equipment;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class EquipmentMaterials
{
    public static final ArmorMaterial jingleDress = EnumHelper.addArmorMaterial("jingleDress", "totemic:jingleDress", 126, new int[]{1, 1, 1, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0f);
}
