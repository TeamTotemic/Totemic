package totemic_commons.pokefenn.item.equipment.music;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.item.equipment.EquipmentMaterials;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.recipe.HandlerInitiation;
import totemic_commons.pokefenn.util.ItemUtil;
import totemic_commons.pokefenn.util.TotemUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemJingleDress extends ItemArmor implements ISpecialArmor
{
    public ItemJingleDress()
    {
        super(EquipmentMaterials.jingleDress, 0, 2);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.JINGLE_DRESS_NAME);
        setCreativeTab(Totemic.tabsTotem);
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot)
    {
        return new ArmorProperties(1, 1, 0);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        return "totemic:textures/armour/jingleDress.png";
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot)
    {
        stack.damageItem(entity.worldObj.rand.nextInt(4), entity);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return EnumRarity.uncommon;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {
        if(!world.isRemote && player.ticksExisted % 20 == 0)
        {
            final double chargeFactor = 10.0;
            final int maxSingleCharge = 8;
            final int chargeLimit = 10;

            double vx = player.posX - player.field_71094_bP; // chasingPosX
            double vy = player.posY - player.field_71095_bQ; // chasingPosY
            double vz = player.posZ - player.field_71085_bR; // chasingPosZ
            double vel = Math.sqrt(vx*vx + vy*vy + vz*vz);
            if(player.isPotionActive(Potion.moveSpeed))
                vel *= 1.2;

            NBTTagCompound tag = ItemUtil.getOrCreateTag(itemStack);
            int time = tag.getByte(Strings.INSTR_TIME_KEY);
            int prevTime = time;
            time += MathHelper.clamp_int((int) (vel * chargeFactor), 0, maxSingleCharge);

            if(time >= chargeLimit)
            {
                playMusic(world, player, itemStack);
                time %= chargeLimit;
            }

            if(time != prevTime)
                tag.setByte(Strings.INSTR_TIME_KEY, (byte) time);
        }
    }

    private void playMusic(World world, EntityPlayer player, ItemStack itemStack)
    {
        TotemUtil.playMusic(world, player.posX, player.posY, player.posZ, HandlerInitiation.jingleDress, 0, 0);
        TotemUtil.particlePacket(world, "note", player.posX, player.posY + 0.4D, player.posZ, 3, 0.5D, 0.2D, 0.5D, 0.0D);
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot)
    {
        return EquipmentMaterials.totemArmour.getDamageReductionAmount(slot);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon(getUnlocalizedName().substring(getUnlocalizedName().indexOf(".") + 1));
    }
}
