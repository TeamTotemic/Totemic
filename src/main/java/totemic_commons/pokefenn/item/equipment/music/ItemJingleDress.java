package totemic_commons.pokefenn.item.equipment.music;

import javax.annotation.Nonnull;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.ModContent;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.item.equipment.EquipmentMaterials;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.util.ItemUtil;
import totemic_commons.pokefenn.util.TotemUtil;

public class ItemJingleDress extends ItemArmor implements ISpecialArmor
{
    public static final String TIME_KEY = "time";

    public ItemJingleDress()
    {
        super(EquipmentMaterials.JINGLE_DRESS, 0, EntityEquipmentSlot.LEGS);
        setRegistryName(Strings.JINGLE_DRESS_NAME);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.JINGLE_DRESS_NAME);
        setCreativeTab(Totemic.tabsTotem);
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, @Nonnull ItemStack armor, DamageSource source, double damage, int slot)
    {
        return new ArmorProperties(1, 1, 0);
    }

    @Override
    public void damageArmor(EntityLivingBase entity, @Nonnull ItemStack stack, DamageSource source, int damage, int slot)
    {
        stack.damageItem(entity.world.rand.nextInt(4), entity);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.UNCOMMON;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {
        if(!world.isRemote && world.getTotalWorldTime() % 20L == 0)
        {
            NBTTagCompound tag = ItemUtil.getOrCreateTag(itemStack);
            int time = tag.getByte(TIME_KEY);
            int prevTime = time;

            // "chasingPos" is used for rendering capes. It is pretty much the only way
            // to get the player's movement speed on the server side.
            double vx = player.posX - player.chasingPosX;
            double vy = player.posY - player.chasingPosY;
            double vz = player.posZ - player.chasingPosZ;
            double velSq = vx*vx + vy*vy + vz*vz;
            if(player.isPotionActive(MobEffects.SPEED))
                velSq *= 1.25;

            if(velSq >= 0.6)
                time += 2;
            else if(velSq >= 0.2)
                time += 1;

            if(time >= 3)
            {
                playMusic(world, player, itemStack);
                time %= 3;
            }

            if(time != prevTime)
                tag.setByte(TIME_KEY, (byte) time);
        }
    }

    private void playMusic(World world, EntityPlayer player, ItemStack itemStack)
    {
        TotemUtil.playMusic(world, player.posX, player.posY, player.posZ, ModContent.jingleDress, 0, 0);
        particlesAllAround((WorldServer)world, player.posX, player.posY, player.posZ);
    }

    private void particlesAllAround(WorldServer world, double x, double y, double z)
    {
        world.spawnParticle(EnumParticleTypes.NOTE, x, y + 0.4D, z, 6, 0.5D, 0.2D, 0.5D, 0.0D);
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, @Nonnull ItemStack armor, int slot)
    {
        return getArmorMaterial().getDamageReductionAmount(EntityEquipmentSlot.values()[slot]);
    }
}
