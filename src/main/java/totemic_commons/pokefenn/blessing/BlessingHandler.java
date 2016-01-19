package totemic_commons.pokefenn.blessing;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 *
 * TODO: This class is currently unused, but when it gets used again, it needs to be updated,
 * e.g. use UUIDs instead of nicknames
 */
public class BlessingHandler
{
    public static void increaseBlessing(int amount, String player, World world, BlockPos pos)
    {
        //Only send this method from server > client
        BlessingWorldData blessing = (BlessingWorldData) world.loadItemData(BlessingWorldData.class, player);

        if(blessing == null)
        {
            blessing = new BlessingWorldData(player);
            world.setItemData(player, blessing);
        }

        blessing.blessing += amount;
       // PacketHandler.sendAround(new PacketClientBlessingSync(getBlessing(player, world)), world.provider.dimensionId, pos);
    }

    public static void increaseBlessingNearby(int amount, World world, BlockPos pos, int range)
    {
        for(EntityPlayer entity : EntityUtil.getEntitiesInRange(EntityPlayer.class, world, pos, range, range))
        {
            String player = entity.getName();
            increaseBlessing(amount, player, world, pos);
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
