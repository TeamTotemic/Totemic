package totemic_commons.pokefenn.api.music;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public interface IMusicItem
{
    public void playMusic(World world, int x, int y, int z, EntityPlayer player);
}
