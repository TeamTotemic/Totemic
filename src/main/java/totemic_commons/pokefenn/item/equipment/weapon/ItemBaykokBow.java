package totemic_commons.pokefenn.item.equipment.weapon;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
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
        setCreativeTab(Totemic.tabsTotem);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.BAYKOK_BOW_NAME);
        setTextureName(Strings.RESOURCE_PREFIX + Strings.BAYKOK_BOW_NAME);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int useRemaining)
    {
        int useDuration = this.getMaxItemUseDuration(stack) - useRemaining;

        ArrowLooseEvent event = new ArrowLooseEvent(player, stack, useDuration);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
        {
            return;
        }
        useDuration = event.charge;

        boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

        if (flag || player.inventory.hasItem(Items.arrow))
        {
            float f = (float)useDuration / 20.0F;
            f = (f * f + f * 2.0F) / 3.0F;

            if ((double)f < 0.1D)
            {
                return;
            }

            if (f > 1.0F)
            {
                f = 1.0F;
            }

            // Changed here to use EntityInvisArrow
            EntityInvisArrow entityarrow = new EntityInvisArrow(world, player, f * 2.0F);

            if (f == 1.0F)
            {
                entityarrow.setIsCritical(true);
            }

            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);

            if (k > 0)
            {
                entityarrow.setDamage(entityarrow.getDamage() + (double)k * 0.5D + 0.5D);
            }

            int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);

            if (l > 0)
            {
                entityarrow.setKnockbackStrength(l);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0)
            {
                entityarrow.setFire(100);
            }

            stack.damageItem(1, player);
            world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            if (flag)
            {
                entityarrow.canBePickedUp = 2;
            }
            else
            {
                player.inventory.consumeInventoryItem(Items.arrow);
            }

            if (!world.isRemote)
            {
                world.spawnEntityInWorld(entityarrow);
            }
        }
    }

    @Override
    public int getItemEnchantability()
    {
        return 5;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister register)
    {
        this.itemIcon = register.registerIcon(this.getIconString() + "_standby");
        IIcon[] iconArray = new IIcon[bowPullIconNameArray.length];

        for (int i = 0; i < iconArray.length; ++i)
        {
            iconArray[i] = register.registerIcon(this.getIconString() + "_" + bowPullIconNameArray[i]);
        }

        ReflectionHelper.setPrivateValue(ItemBow.class, this, iconArray, "field_94600_b", "iconArray");
    }

    // Need to override this since the vanilla bow is hardcoded in EntityPlayer.getItemIcon
    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
        if(usingItem != null)
        {
            int useDuration = stack.getMaxItemUseDuration() - useRemaining;

            if (useDuration >= 18)
            {
                return this.getItemIconForUseDuration(2);
            }
            else if (useDuration > 13)
            {
                return this.getItemIconForUseDuration(1);
            }
            else if (useDuration > 0)
            {
                return this.getItemIconForUseDuration(0);
            }
        }

        return this.itemIcon;
    }
}
