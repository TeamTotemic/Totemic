package totemic_commons.pokefenn.item.equipment.music;

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
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.item.equipment.EquipmentMaterials;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.network.PacketHandler;
import totemic_commons.pokefenn.network.server.PacketJingle;
import totemic_commons.pokefenn.recipe.HandlerInitiation;
import totemic_commons.pokefenn.util.TotemUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemJingleDress extends ItemArmor implements ISpecialArmor
{
    public ItemJingleDress()
    {
        super(EquipmentMaterials.jingleDress, 0, EntityEquipmentSlot.LEGS);
        setRegistryName(Strings.JINGLE_DRESS_NAME);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.JINGLE_DRESS_NAME);
        setCreativeTab(Totemic.tabsTotem);
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot)
    {
        return new ArmorProperties(1, 1, 0);
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
        return EnumRarity.UNCOMMON;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {
        if(world.isRemote)
        {
            if(world.getTotalWorldTime() % 20L == 0)
                if(player.motionX != 0 || player.motionZ != 0)
                    PacketHandler.sendToServer(new PacketJingle(player.motionX, player.motionZ));
        } else
        {
            if(world.getTotalWorldTime() % 20L == 0)
            {
                NBTTagCompound tag = itemStack.getTagCompound();
                if(tag != null)
                {
                    int time = tag.getInteger(Strings.INSTR_TIME_KEY);
                    if(time >= 3 || (player.isPotionActive(MobEffects.SPEED) && time >= 2))
                    {
                        playMusic(world, player, itemStack, false);
                        tag.setInteger(Strings.INSTR_TIME_KEY, 0);
                    }
                }
            }
        }
    }

    public void playMusic(World world, EntityPlayer player, ItemStack itemStack, boolean isSneaking)
    {
        if(!isSneaking)
        {
            TotemUtil.playMusic(world, player.posX, player.posY, player.posZ, HandlerInitiation.jingleDress, 0, 0);
            particlesAllAround((WorldServer)world, player.posX, player.posY, player.posZ);
        }
    }

    public void particlesAllAround(WorldServer world, double x, double y, double z)
    {
        world.spawnParticle(EnumParticleTypes.NOTE, x, y + 0.4D, z, 6, 0.5D, 0.2D, 0.5D, 0.0D);
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot)
    {
        return getArmorMaterial().getDamageReductionAmount(EntityEquipmentSlot.values()[slot]);
    }

    public int getBonusMusic()
    {
        //TODO
        return 0;
    }
}
