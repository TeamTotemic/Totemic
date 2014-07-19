package totemic_commons.pokefenn.item.equipment.music;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.music.IMusic;
import totemic_commons.pokefenn.api.music.MusicEnum;
import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.network.PacketHandler;
import totemic_commons.pokefenn.network.PacketRattleSound;
import totemic_commons.pokefenn.util.EntityUtil;
import totemic_commons.pokefenn.util.TotemUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemRattle extends ItemTotemic implements IMusic
{
    public int time;

    //This int will hold the amount of nearby poles, which are sticks and buffalo heads. Each pole will increase the music made.
    public int headPoles;

    public ItemRattle()
    {
        super(Strings.CEREMONY_RATTLE_NAME);
        time = 0;
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack itemStack)
    {
        World world = entityLiving.worldObj;
        int x = (int) entityLiving.posX;
        int y = (int) entityLiving.posY;
        int z = (int) entityLiving.posZ;

        if(!world.isRemote)
        {
            if(entityLiving instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) entityLiving;
                MovingObjectPosition block = EntityUtil.raytraceFromEntity(world, player, true, 5);
                if(block == null)
                {
                    time++;
                    if(time >= 8 && !player.isSneaking())
                    {
                        time = 0;
                        TotemUtil.playMusicFromItem(world, player, this.musicEnum(itemStack, world, (int) player.posX, (int) player.posY, (int) player.posZ, true, player), (int) player.posX, (int) player.posY, (int) player.posZ, this.getRange(world, (int) player.posX, (int) player.posY, (int) player.posZ, true, player), this.getMaximumMusic(world, (int) player.posX, (int) player.posY, (int) player.posZ, true, player), this.getMusicOutput(world, (int) player.posX, (int) player.posY, (int) player.posZ, true, player));
                        particlesAllAround(world, player.posX, player.posY, player.posZ);
                        PacketHandler.sendAround(new PacketRattleSound(x, y, z), ((EntityPlayer) entityLiving).worldObj.provider.dimensionId, x, y, z);
                        return false;
                    }
                    if(time >= 8 && player.isSneaking())
                    {
                        time = 0;
                        TotemUtil.playMusicFromItemForCeremonySelector(itemStack, player, (int) player.posX, (int) player.posY, (int) player.posZ, musicEnum(itemStack, world, (int) player.posX, (int) player.posY, (int) player.posZ, true, player), this.getRange(world, (int) player.posX, (int) player.posY, (int) player.posZ, true, player));
                        particlesAllAround(world, player.posX, player.posY, player.posZ);
                        PacketHandler.sendAround(new PacketRattleSound(x, y, z), ((EntityPlayer) entityLiving).worldObj.provider.dimensionId, x, y, z);
                    }
                }
            }
        }

        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {

        return itemStack;
    }

    public void particlesAllAround(World world, double x, double y, double z)
    {
        MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("note", x + 0.5D, y + 1.2D, z + 0.5D, 2, 0.5D, 0.0D, 0.5D, 0.0D);
        MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("note", x, y + 1.2D, z, 2, 0.0D, 0.0D, 0.0D, 0.0D);
        MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("note", x + 0.5D, y + 1.2D, z, 2, 0.0D, 0.0D, 0.0D, 0.0D);
        MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("note", x, y + 1.2D, z + 0.5D, 2, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public MusicEnum musicEnum(ItemStack itemStack, World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return MusicEnum.RATTLE;
    }

    @Override
    public int getMaximumMusic(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return 110;
    }

    @Override
    public int getMusicOutput(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return 4 + (headPoles * 2);
    }

    @Override
    public int getRange(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return 8;
    }
}
