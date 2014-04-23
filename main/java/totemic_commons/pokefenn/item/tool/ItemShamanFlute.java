package totemic_commons.pokefenn.item.tool;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.util.EntityUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemShamanFlute extends ItemTotemic
{

    public ItemShamanFlute()
    {
        super();
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.SHAMAN_FLUTE_NAME);
        setMaxStackSize(1);
    }

    public void onUpdate(ItemStack itemStack, World world, Entity player, int par4, boolean par5)
    {
        if(!world.isRemote)
        {
            if(player instanceof EntityPlayer)
                if(((EntityPlayer) player).getHeldItem() != null && ((EntityPlayer) player).getHeldItem().getItem() == ModItems.shamanFlute)
                {
                    ((EntityPlayer) player).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 30, 1));
                }

        }

    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if(!world.isRemote)
            if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.shamanFlute)
            {
                player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 30, 1));
                if(EntityUtil.getEntitiesInRange(world, player.posX, player.posY, player.posZ, 2, 2) != null)
                {
                    for(Entity entity : EntityUtil.getEntitiesInRange(world, player.posX, player.posY, player.posZ, 2, 2))
                    {
                        if(entity instanceof EntityAnimal || entity instanceof EntityVillager)
                        {
                            if(entity instanceof EntityAnimal)
                                ((EntityAnimal) entity).targetTasks.addTask(5, new EntityAITempt((EntityCreature) entity, 1, ModItems.shamanFlute, false));
                            if(entity instanceof EntityVillager)
                                ((EntityVillager) entity).targetTasks.addTask(5, new EntityAITempt((EntityCreature) entity, 0.5, ModItems.shamanFlute, false));
                        }

                    }
                }
            }

        if(world.isRemote)
        {
            world.spawnParticle("enchantmenttable", ((EntityPlayer) player).posX, ((EntityPlayer) player).posY, ((EntityPlayer) player).posZ, 0, 0.32, 0);
        }

        return stack;
    }

}
