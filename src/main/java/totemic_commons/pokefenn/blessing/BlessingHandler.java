package totemic_commons.pokefenn.blessing;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import totemic_commons.pokefenn.network.PacketClientBlessingSync;
import totemic_commons.pokefenn.network.PacketHandler;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlessingHandler
{
    public static void increaseBlessing(int amount, String player, World world, int x, int y, int z)
    {
        BlessingWorldData blessing = (BlessingWorldData) world.loadItemData(BlessingWorldData.class, player);

        if(blessing == null)
        {
            blessing = new BlessingWorldData(player);
            world.setItemData(player, blessing);
        }

        blessing.blessing += amount;
        PacketHandler.sendAround(new PacketClientBlessingSync(getBlessing(player, world)), world.provider.dimensionId, x, y, z);
    }

    public static void increaseBlessingNearby(int amount, World world, int x, int y, int z, int range)
    {
        if(EntityUtil.getEntitiesInRange(world, x, y, z, range, range) != null)
        {
            for(Entity entity : EntityUtil.getEntitiesInRange(world, x, y, z, range, range))
            {
                if(entity instanceof EntityPlayer)
                {
                    String player = ((EntityPlayer) entity).getDisplayName();
                    increaseBlessing(amount, player, world, x, y, z);
                }
            }
        }
    }

    public static void setBlessing(int amount, String player, World world)
    {
        BlessingWorldData blessing = (BlessingWorldData) world.loadItemData(BlessingWorldData.class, player);

        if(blessing == null)
        {
            blessing = new BlessingWorldData(player);
            world.setItemData(player, blessing);
        }

        blessing.blessing = amount;
    }

    public static void decreaseBlessing(int amount, String player, World world)
    {
        BlessingWorldData blessing = (BlessingWorldData) world.loadItemData(BlessingWorldData.class, player);

        if(blessing == null)
        {
            blessing = new BlessingWorldData(player);
            world.setItemData(player, blessing);
        }

        blessing.blessing -= amount;
    }

    public static BlessingWorldData getBlessingHandler(String player, World world)
    {
        BlessingWorldData blessing = (BlessingWorldData) world.loadItemData(BlessingWorldData.class, player);

        if(blessing == null)
        {
            blessing = new BlessingWorldData(player);
            world.setItemData(player, blessing);
        }

        return blessing;
    }

    public static int getBlessing(String player, World world)
    {
        return getBlessingHandler(player, world).blessing;
    }

    public static boolean canDoEffect(int amount, String player, World world)
    {
        BlessingWorldData blessing = (BlessingWorldData) world.loadItemData(BlessingWorldData.class, player);

        if(blessing == null)
        {
            blessing = new BlessingWorldData(player);
            world.setItemData(player, blessing);
        }

        return blessing.blessing - amount >= 0;
    }

}
