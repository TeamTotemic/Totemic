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
import totemic_commons.pokefenn.api.armour.ITotemArmour;
import totemic_commons.pokefenn.item.ItemTotemicItems;
import totemic_commons.pokefenn.item.equipment.EquipmentMaterials;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.Textures;

import java.util.List;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemTotemArmour extends ItemArmor implements ISpecialArmor, ITotemArmour
{
    public int armourType;

    public ItemTotemArmour(int armourType, String name)
    {
        super(EquipmentMaterials.totemArmour, 0, armourType);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + name);
        setCreativeTab(Totemic.tabsTotem);
        this.armourType = armourType;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        if(this == ModItems.totemArmourHead)
            list.add("Handle potions with the prowess of a Medicine Man");
        if(this == ModItems.totemArmourLeg)
            list.add("Dance like the Diva");
        //if(this == ModItems.totemArmourChest)
        //    list.add("");
    }

    @Override
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return par2ItemStack.getItem() == ModItems.subItems && par2ItemStack.getItemDamage() == ItemTotemicItems.cedarBark;
    }


    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot)
    {
        return new ArmorProperties(1, 1, 0);
    }

    private static IIcon helmetIcon;
    private static IIcon plateIcon;
    private static IIcon leggingsIcon;
    private static IIcon bootsIcon;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        helmetIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + "totemArmourHelmet");
        plateIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + "totemArmourChest");
        bootsIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + "totemArmourBoots");
        leggingsIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + "totemArmourLeggings");
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
        //if(this == ModItems.totemArmourHead || this == ModItems.totemArmourChest || this == ModItems.totemArmourFeet)
        //{
        //} else
        //    return "totemic:models/armour/totemArmourLayer2.png";

        return this == ModItems.totemArmourHead || this == ModItems.totemArmourChest || this == ModItems.totemArmourFeet ? "totemic:models/armour/totemArmourLayer1.png" : "totemic:models/armour/totemArmourLayer2.png";

    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot)
    {
        return EquipmentMaterials.totemArmour.getDamageReductionAmount(slot);
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot)
    {
        stack.damageItem(damage % 2, entity);
    }

    @Override
    public int getEfficiency(ItemStack itemStack)
    {
        return 1;
    }
}
