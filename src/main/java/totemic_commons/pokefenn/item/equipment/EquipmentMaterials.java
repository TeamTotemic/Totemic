package totemic_commons.pokefenn.item.equipment;

import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.util.EnumHelper;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class EquipmentMaterials
{
    public static ItemArmor.ArmorMaterial totemArmour = EnumHelper.addArmorMaterial("totemArmour", 100, new int[]{1, 4, 3, 1}, 18);

    public static ItemTool.ToolMaterial huntingKnife = EnumHelper.addToolMaterial("huntingKnife", 0, 126, 0, -2, 0);

    public static ItemArmor.ArmorMaterial jingleDress = EnumHelper.addArmorMaterial("bellShoe", 126, new int[]{2, 2, 2, 2}, 15);

    public static ItemArmor.ArmorMaterial warBonnet = EnumHelper.addArmorMaterial("warBonnet", 256, new int[]{3, 0, 0, 0}, 18);

    public static ItemTool.ToolMaterial tomahawk = EnumHelper.addToolMaterial("tomahawk", 0, 256, 0, 5, 0);

    public static ItemArmor.ArmorMaterial buffalo = EnumHelper.addArmorMaterial("buffalo", 128, new int[]{2, 4, 3, 1}, 21);
}
