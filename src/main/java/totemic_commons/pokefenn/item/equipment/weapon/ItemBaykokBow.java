package totemic_commons.pokefenn.item.equipment.weapon;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.entity.projectile.EntityInvisArrow;
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
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer playerIn, int timeLeft)
    {
        boolean infinity = playerIn.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

        if(infinity || playerIn.inventory.hasItem(Items.arrow))
        {
            int chargeTicks = this.getMaxItemUseDuration(stack) - timeLeft;
            ArrowLooseEvent event = new ArrowLooseEvent(playerIn, stack, chargeTicks);
            if(MinecraftForge.EVENT_BUS.post(event))
                return;
            chargeTicks = event.charge;
            float charge = chargeTicks / 20.0F;
            charge = (charge * charge + charge * 2.0F) / 3.0F;

            if(charge < 0.1F)
                return;

            if(charge > 1.0F)
                charge = 1.0F;

            EntityArrow arrow = new EntityInvisArrow(worldIn, playerIn, charge * 2.0F);
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

            stack.damageItem(1, playerIn);
            worldIn.playSoundAtEntity(playerIn, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + charge * 0.5F);

            if(infinity)
            {
                arrow.canBePickedUp = 2;
            }
            else
            {
                playerIn.inventory.consumeInventoryItem(Items.arrow);
            }

            playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);

            if(!worldIn.isRemote)
            {
                worldIn.spawnEntityInWorld(arrow);
            }
        }
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
