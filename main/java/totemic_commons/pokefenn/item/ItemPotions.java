package totemic_commons.pokefenn.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.potion.ModPotions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemPotions extends Item
{
    public String[] POTIONS = {"Antidote", "Resistance", "WaterBreathing", "Regeneration", "Speed"};
    public Potion[] POTION_EFFECTS = {ModPotions.antidotePotion, Potion.resistance, Potion.waterBreathing, Potion.regeneration, Potion.moveSpeed};

    public int[] LENGTHS = {90 * 20, 180 * 20, 300 * 20, 360 * 20, 500 * 20};
    public int[] STRENGTHS = {0, 1};

    public IIcon[] icons;

    public ItemPotions()
    {
        super();
        //setCreativeTab(Totemic.tabsPotionTotem);
        setUnlocalizedName(Strings.TOTEMIC_POTION_NAME);
    }

    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.drink;
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));

        return stack;
    }

    public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer player)
    {
        int damage = itemStack.getItemDamage();

        int potionLength = damage % 5;
        //int potionStrength = damage % 5;

        if(!world.isRemote)
        {
            if(damage == 0 || damage == 1 || damage == 2)
                player.addPotionEffect(new PotionEffect(ModPotions.antidotePotion.getId(), LENGTHS[potionLength], STRENGTHS[0]));
            if(damage == 3 || damage == 4 || damage == 5)
                player.addPotionEffect(new PotionEffect(POTION_EFFECTS[1].id, LENGTHS[potionLength], STRENGTHS[potionLength]));
            if(damage == 6 || damage == 7 || damage == 8)
                player.addPotionEffect(new PotionEffect(POTION_EFFECTS[2].id, LENGTHS[potionLength], STRENGTHS[potionLength]));
            if(damage == 9 || damage == 10 || damage == 11)
                player.addPotionEffect(new PotionEffect(POTION_EFFECTS[3].id, LENGTHS[potionLength], STRENGTHS[potionLength]));
            if(damage == 12 || damage == 13 || damage == 14)
                player.addPotionEffect(new PotionEffect(POTION_EFFECTS[4].id, LENGTHS[potionLength], STRENGTHS[potionLength]));

            System.out.println("yes");

            if(!player.capabilities.isCreativeMode)
            {
                player.getHeldItem().stackSize--;

                EntityItem entityPotion = new EntityItem(player.worldObj, player.posX + 0.5D, player.posY + 0.5D, player.posZ + 0.5D, new ItemStack(Items.glass_bottle).copy());
                world.spawnEntityInWorld(entityPotion);
            }
        }

        return itemStack;
    }

    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 32;
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        int damage = stack.getItemDamage();

        if(damage == 0 || damage == 1 || damage == 2)
            list.add(getName(damage % 3) + POTIONS[0]);
        if(damage == 3 || damage == 4 || damage == 5)
            list.add(getName(damage % 3) + POTIONS[1]);
        if(damage == 6 || damage == 7 || damage == 8)
            list.add(getName(damage % 3) + POTIONS[2]);
        if(damage == 9 || damage == 10 || damage == 11)
            list.add(getName(damage % 3) + POTIONS[3]);
        if(damage == 12 || damage == 13 || damage == 14)
            list.add(getName(damage % 3) + POTIONS[4]);
    }

    public String getName(int i)
    {
        switch(i)
        {
            case 0:
                return "90 Seconds of ";

            case 1:
                return "180 Seconds of ";

            case 2:
                return "360 Seconds of ";

            default:
                return "wat";
        }

    }
/*
    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        StringBuilder unlocalizedName = new StringBuilder();
        int meta = MathHelper.clamp_int(itemStack.getItemDamage(), 0, POTIONS.length - 1);

        unlocalizedName.append("item.");
        unlocalizedName.append(Strings.RESOURCE_PREFIX);
        unlocalizedName.append("potion");
        unlocalizedName.append(POTIONS[meta]);

        return unlocalizedName.toString();
    }
    */

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta)
    {
        int j = MathHelper.clamp_int(meta, 0, POTIONS.length - 1);
        return icons[j];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        icons = new IIcon[POTIONS.length];

        for(int i = 0; i < POTIONS.length; i++)
            icons[i] = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + "potion" + POTIONS[i]);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item id, CreativeTabs creativeTab, List list)
    {

        for(int eachPotion = 0; eachPotion < (3 * POTIONS.length); eachPotion++)
        {
            list.add(new ItemStack(id, 1, eachPotion));
        }
    }


}
