package totemic_commons.pokefenn.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent;
import totemic_commons.pokefenn.potion.ModPotions;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 08/03/14
 * Time: 17:52
 */
public class TotemicEventHooks
{

    @ForgeSubscribe
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event)
    {
        EntityLivingBase entityLiving = event.entityLiving;
        double x = entityLiving.posX;
        double y = entityLiving.posY;
        double z = entityLiving.posZ;

        if (entityLiving instanceof EntityPlayer && ((EntityPlayer) entityLiving).worldObj.isRemote)
        {
            if (event.entityLiving.isPotionActive(ModPotions.horsePotion))
            {
                ((EntityPlayer) entityLiving).capabilities.setPlayerWalkSpeed(((EntityPlayer) entityLiving).capabilities.getWalkSpeed() + 0.1F);
                entityLiving.stepHeight = 0.25F;

            }

        }

        if (event.entityLiving.isPotionActive(ModPotions.batPotion))
        {

            ((EntityPlayer) entityLiving).capabilities.allowFlying = true;
        }

        if (entityLiving instanceof EntityPlayer && !((EntityPlayer) entityLiving).worldObj.isRemote)
        {
            if (event.entityLiving.isPotionActive(ModPotions.horsePotion))
            {
                //((EntityPlayer) entityLiving).capabilities.setPlayerWalkSpeed(((EntityPlayer) entityLiving).capabilities.getWalkSpeed() + 0.1F);
                entityLiving.stepHeight = 0.25F;

            }

        }


    }


}
