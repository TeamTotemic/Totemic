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
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import totemic_commons.pokefenn.api.music.IMusic;
import totemic_commons.pokefenn.api.music.MusicEnum;
import totemic_commons.pokefenn.item.equipment.EquipmentMaterials;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.network.PacketHandler;
import totemic_commons.pokefenn.network.server.PacketJingle;
import totemic_commons.pokefenn.util.TotemUtil;

import java.util.List;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemJingleDress extends ItemArmor implements ISpecialArmor, IMusic
{
    public int time = 0;
    public boolean hasSpeed = false;


    public ItemJingleDress()
    {
        super(EquipmentMaterials.jingleDress, 0, 2);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.JINGLE_DRESS_NAME);
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
        if(world.isRemote)
        {
            if(world.getWorldTime() % 20L == 0)
                if(player.motionX > 0 || player.motionZ > 0)
                    PacketHandler.sendToServer(new PacketJingle(player.motionX, player.motionZ));
        } else
        {
            if(world.getWorldTime() % 20L == 0)
            {
                hasSpeed = player.isPotionActive(Potion.moveSpeed);
            }

            if(time > 2 || (hasSpeed && time > 1))
            {
                time = 0;
                playMusic(world, player, itemStack, player.isSneaking());
            }
        }
    }

    public void playMusic(World world, EntityPlayer player, ItemStack itemStack, boolean isSneaking)
    {
        if(!isSneaking)
        {
            TotemUtil.playMusicFromItem(world, player, this.musicEnum(itemStack, world, (int) player.posX, (int) player.posY, (int) player.posZ, true, player), (int) player.posX, (int) player.posY, (int) player.posZ, this.getRange(world, (int) player.posX, (int) player.posY, (int) player.posZ, true, player), this.getMaximumMusic(world, (int) player.posX, (int) player.posY, (int) player.posZ, true, player), this.getMusicOutput(world, (int) player.posX, (int) player.posY, (int) player.posZ, true, player));
            particlesAllAround(world, player.posX, player.posY, player.posZ);
        } else
        {
            TotemUtil.playMusicFromItemForCeremonySelector(itemStack, player, (int) player.posX, (int) player.posY, (int) player.posZ, musicEnum(new ItemStack(this, 1, 0), world, (int) player.posX, (int) player.posY, (int) player.posZ, true, player), this.getRange(world, (int) player.posX, (int) player.posY, (int) player.posZ, true, player));
            particlesAllAround(world, player.posX, player.posY, player.posZ);
        }
    }

    public void particlesAllAround(World world, double x, double y, double z)
    {
        MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("note", x + 0.5D, y + 0.4D, z + 0.5D, 2, 0.5D, 0.2D, 0.5D, 0.0D);
        MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("note", x, y + 0.4, z, 2, 0.0D, 0.2D, 0.0D, 0.0D);
        MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("note", x + 0.5D, y + 0.4D, z, 2, 0.0D, 0.2D, 0.0D, 0.0D);
        MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("note", x, y + 0.4D, z + 0.5D, 2, 0.0D, 0.2D, 0.0D, 0.0D);
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot)
    {
        return EquipmentMaterials.totemArmour.getDamageReductionAmount(slot);
    }

    @Override
    public MusicEnum musicEnum(ItemStack itemStack, World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return MusicEnum.JINGLE_DRESS;
    }

    @Override
    public int getMaximumMusic(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return 90;
    }

    @Override
    public int getMusicOutput(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return isFromPlayer && player != null && player.getCurrentArmor(3) != null ? 7 : 0;
    }

    @Override
    public int getRange(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return 8;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon(getUnlocalizedName().substring(getUnlocalizedName().indexOf(".") + 1));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        int musicOutput = getMusicOutput(player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ, true, player);
        if(musicOutput < 5)
            list.add(StatCollector.translateToLocal("totemic.music.lowMelody"));
        else if(musicOutput == 6)
            list.add(StatCollector.translateToLocal("totemic.music.mediumMelody"));
        else if(musicOutput == 7)
            list.add(StatCollector.translateToLocal("totemic.music.highMelody"));
        else if(musicOutput > 7)
            list.add(StatCollector.translateToLocal("totemic.music.veryHighMelody"));
    }
}
