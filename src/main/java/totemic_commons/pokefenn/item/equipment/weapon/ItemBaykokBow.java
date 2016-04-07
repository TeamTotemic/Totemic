package totemic_commons.pokefenn.item.equipment.weapon;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

public class ItemBaykokBow extends ItemBow
{
    public ItemBaykokBow()
    {
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.BAYKOK_BOW_NAME);
        setCreativeTab(Totemic.tabsTotem);
        setMaxDamage(576);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase player, int timeLeft)
    {
        //TODO: Copy code from ItemBow
        /*boolean infinity = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.infinity, stack) > 0;

        if(infinity || player.inventory.hasItem(Items.arrow))
        {
            int chargeTicks = this.getMaxItemUseDuration(stack) - timeLeft;
            ArrowLooseEvent event = new ArrowLooseEvent(player, stack, chargeTicks);
            if(MinecraftForge.EVENT_BUS.post(event))
                return;
            chargeTicks = event.charge;
            float charge = chargeTicks / 20.0F;
            charge = (charge * charge + charge * 2.0F) / 3.0F;

            if(charge < 0.1F)
                return;

            if(charge > 1.0F)
                charge = 1.0F;

            EntityArrow arrow = new EntityInvisArrow(world, player, charge * 2.0F);
            arrow.setDamage(2.5);

            if(charge == 1.0F)
            {
                arrow.setIsCritical(true);
            }

            int power = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);

            if(power > 0)
            {
                arrow.setDamage(arrow.getDamage() + power * 0.5 + 0.5);
            }

            int punch = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);

            if(punch > 0)
            {
                arrow.setKnockbackStrength(punch);
            }

            if(EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0)
            {
                arrow.setFire(100);
            }

            stack.damageItem(1, player);
            world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + charge * 0.5F);

            if(infinity)
            {
                arrow.canBePickedUp = 2;
            }
            else
            {
                player.inventory.consumeInventoryItem(Items.arrow);
            }

            player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);

            if(!world.isRemote)
            {
                world.spawnEntityInWorld(arrow);
            }
        }*/
    }

    @Override
    public int getItemEnchantability()
    {
        return 5;
    }

    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }
}
