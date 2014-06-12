package totemic_commons.pokefenn.item.equipment.weapon;

import net.minecraft.item.ItemSword;
import totemic_commons.pokefenn.item.equipment.EquipmentMaterials;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemTomahawk extends ItemSword
{
    public ItemTomahawk()
    {
        super(EquipmentMaterials.tomahawk);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.TOMAHAWK_NAME);
    }
}
