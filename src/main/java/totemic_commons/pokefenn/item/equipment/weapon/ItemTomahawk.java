package totemic_commons.pokefenn.item.equipment.weapon;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemTomahawk extends ItemSword
{
    public ItemTomahawk()
    {
        super(/*EquipmentMaterials.tomahawk*/ToolMaterial.IRON);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.TOMAHAWK_NAME);
        setCreativeTab(Totemic.tabsTotem);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return EnumRarity.UNCOMMON;
    }
}
