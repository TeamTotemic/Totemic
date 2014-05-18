package totemic_commons.pokefenn.event;

import baubles.api.BaublesApi;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.misc.TotemicDamageSource;
import totemic_commons.pokefenn.util.TotemUtil;

import java.util.Random;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class EntityHurt
{

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onEntityHurt(LivingHurtEvent event)
    {
        Entity sourceDamageEntity = event.source.getSourceOfDamage();

        Random random = new Random();

        if(event.entityLiving instanceof EntityPlayer && sourceDamageEntity != null)
        {
            if(random.nextInt(3) == 1)
                if(TotemUtil.getArmourAmounts((EntityPlayer) event.entityLiving) == 4)
                    sourceDamageEntity.attackEntityFrom(DamageSource.generic, event.ammount % 2);
        }

        if(event.entity != null && event.source!= TotemicDamageSource.vigor && event.entityLiving != null && sourceDamageEntity != null && sourceDamageEntity instanceof EntityPlayer && Totemic.baublesLoaded)
        {
            IInventory baubleInventory = BaublesApi.getBaubles((EntityPlayer) sourceDamageEntity);

            if(baubleInventory.getStackInSlot(3) != null)
            {
                if(baubleInventory.getStackInSlot(3).getItem() == ModItems.baubleArmourDamage)
                {
                    EntityPlayer player = (EntityPlayer) sourceDamageEntity;

                    if(player.getHeldItem().getItem() instanceof ItemSword)
                    {
                        int armour = player.getTotalArmorValue();
                        int damage = player.getHeldItem().getItemDamage();
                        float totalDamage = event.ammount;
                        int totemArmour = TotemUtil.getArmourAmounts(player);

                        if(totemArmour == 0 && armour > 5)
                            totalDamage -= 1;
                        if(totemArmour > 0)
                            totalDamage += (totemArmour % 2);
                        if(damage > 6)
                            totalDamage -= 1;
                        if(armour == 0)
                            totalDamage += 3;
                        if(damage < 6 && damage > 0)
                            totalDamage += 2;

                        event.setResult(Event.Result.DENY);

                        event.entity.attackEntityFrom(TotemicDamageSource.vigor, totalDamage);
                    }
                }
            }
        }
    }

}
