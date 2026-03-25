package totemic_commons.pokefenn.item.equipment.music;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.network.PacketHandler;
import totemic_commons.pokefenn.network.client.PacketSound;
import totemic_commons.pokefenn.recipe.HandlerInitiation;
import totemic_commons.pokefenn.util.ItemUtil;
import totemic_commons.pokefenn.util.TotemUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemEagleBoneWhistle extends ItemMusic
{
    public ItemEagleBoneWhistle()
    {
        super(Strings.EAGLE_BONE_WHISTLE_NAME, HandlerInitiation.eagleBoneWhistle);
        setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if(!world.isRemote)
        {
            int x = (int) player.posX;
            int y = (int) player.posY;
            int z = (int) player.posZ;

            NBTTagCompound tag = ItemUtil.getOrCreateTag(itemStack);
            int time = tag.getInteger(Strings.INSTR_TIME_KEY);

            time++;
            if(time >= 5 && !player.isSneaking())
            {
                time = 0;
                TotemUtil.playMusic(world, player.posX, player.posY, player.posZ, musicHandler, 0, 0);
                particlesAllAround((WorldServer)world, player.posX, player.posY, player.posZ, false);
                PacketHandler.sendAround(new PacketSound(x, y, z, "eagleBoneWhistle"), player.worldObj.provider.dimensionId, x, y, z);
            }
            if(time >= 5 && player.isSneaking())
            {
                time = 0;
                TotemUtil.playMusicForSelector(player.worldObj, player.posX, player.posY, player.posZ, musicHandler, 0);
                particlesAllAround((WorldServer)world, player.posX, player.posY, player.posZ, true);
                PacketHandler.sendAround(new PacketSound(x, y, z, "eagleBoneWhistle"), player.worldObj.provider.dimensionId, x, y, z);
            }

            tag.setInteger(Strings.INSTR_TIME_KEY, time);
        }
        return itemStack;
    }

    public void particlesAllAround(WorldServer world, double x, double y, double z, boolean firework)
    {
        TotemUtil.particlePacket(world, "note", x, y + 1.2D, z, 6, 0.5D, 0.0D, 0.5D, 0.0D);

        if(firework)
        {
            TotemUtil.particlePacket(world, "fireworksSpark", x, y + 1.2D, z, 8, 0.5D, 0.0D, 0.5D, 0.0D);
        }
    }
}
