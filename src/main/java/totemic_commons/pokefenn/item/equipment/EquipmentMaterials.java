package totemic_commons.pokefenn.item.equipment;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class EquipmentMaterials
{
    //EnumHelper is broken at the moment
    public static final ArmorMaterial jingleDress = EnumHelper.addEnum(ArmorMaterial.class, "jingleDress", new Class<?>[] {String.class, int.class, int[].class, int.class, SoundEvent.class, float.class},
            "jingleDress", 126, new int[]{1, 1, 1, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0f);
}
