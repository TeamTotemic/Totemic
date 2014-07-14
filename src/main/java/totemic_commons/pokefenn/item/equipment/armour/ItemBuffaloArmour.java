package totemic_commons.pokefenn.item.equipment.armour;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.ISpecialArmor;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.item.ItemBuffaloDrops;
import totemic_commons.pokefenn.item.equipment.EquipmentMaterials;
import totemic_commons.pokefenn.lib.Resources;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemBuffaloArmour extends ItemArmor implements ISpecialArmor
{
    public int armourType;

    public ItemBuffaloArmour(int type, String name)
    {
        super(EquipmentMaterials.buffalo, 0, type);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + name);
        setCreativeTab(Totemic.tabsTotem);
        this.armourType = type;
    }

    @Override
    public boolean getIsRepairable(ItemStack itemStack, ItemStack itemStack2)
    {
        return itemStack2.getItem() == ModItems.buffaloItems && itemStack2.getItemDamage() == ItemBuffaloDrops.hide;
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot)
    {
        //TODO
        return new ArmorProperties(0, damageReduceAmount, 1);
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot)
    {
        return EquipmentMaterials.buffalo.getDamageReductionAmount(slot);
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot)
    {

    }

    private static IIcon helmetIcon;
    private static IIcon plateIcon;
    private static IIcon leggingsIcon;
    private static IIcon bootsIcon;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        helmetIcon = register.registerIcon(Resources.TEXTURE_LOCATION + ":" + "totemArmourHelmet");
        plateIcon = register.registerIcon(Resources.TEXTURE_LOCATION + ":" + "totemArmourChest");
        bootsIcon = register.registerIcon(Resources.TEXTURE_LOCATION + ":" + "totemArmourBoots");
        leggingsIcon = register.registerIcon(Resources.TEXTURE_LOCATION + ":" + "totemArmourLeggings");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int par1)
    {
        if(this == ModItems.totemArmourChest)
            return plateIcon;
        if(this == ModItems.totemArmourFeet)
            return bootsIcon;
        if(this == ModItems.totemArmourLeg)
            return leggingsIcon;
        if(this == ModItems.totemArmourHead)
            return helmetIcon;
        return null;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {

        return this == ModItems.buffaloHelmet || this == ModItems.buffaloChest || this == ModItems.buffaloBoots ? "totemic:textures/armour/buffaloArmour1.png" : "totemic:textures/armour/buffaloArmour2.png";

    }
}
