package totemic_commons.pokefenn.item.equipment;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.util.EnumHelper;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class EquipmentMaterials
{
    public static ItemArmor.ArmorMaterial totemArmour = EnumHelper.addArmorMaterial("totemArmour", 100, new int[]{2, 4, 3, 1}, 18);

    public static ItemTool.ToolMaterial huntingKnife = EnumHelper.addToolMaterial("huntingKnife", 0, 126, 0, -2, 0);
}
