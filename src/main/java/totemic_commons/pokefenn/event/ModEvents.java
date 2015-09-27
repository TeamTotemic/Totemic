package totemic_commons.pokefenn.event;

import net.minecraftforge.common.MinecraftForge;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ModEvents
{

    public static void init()
    {
        MinecraftForge.EVENT_BUS.register(new EntityUpdate());
        MinecraftForge.EVENT_BUS.register(new EntityHurt());
        MinecraftForge.EVENT_BUS.register(new EntityFall());
        MinecraftForge.EVENT_BUS.register(new EntityJump());
        MinecraftForge.EVENT_BUS.register(new BlockBroken());
        MinecraftForge.EVENT_BUS.register(new EntityRightClick());
        MinecraftForge.EVENT_BUS.register(new EntitySpawn());
    }
}
