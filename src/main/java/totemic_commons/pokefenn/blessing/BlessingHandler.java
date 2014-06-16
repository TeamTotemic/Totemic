package totemic_commons.pokefenn.blessing;

import net.minecraft.world.World;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlessingHandler
{
    public static void increaseBlessing(int amount, String player, World world)
    {
        BlessingWorldData blessing = (BlessingWorldData) world.loadItemData(BlessingWorldData.class, player);

        if(blessing == null)
        {
            blessing = new BlessingWorldData(player);
            world.setItemData(player, blessing);
        }

        blessing.blessing += amount;
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

    public int getBlessing(String player, World world)
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
