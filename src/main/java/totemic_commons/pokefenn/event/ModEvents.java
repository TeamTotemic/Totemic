package totemic_commons.pokefenn.event;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ModEvents
{

    public static void init()
    {
        MinecraftForge.EVENT_BUS.register(new EntityUpdate());
        //MinecraftForge.EVENT_BUS.register(new EntityHurt());
        MinecraftForge.EVENT_BUS.register(new EntityFall());
        MinecraftForge.EVENT_BUS.register(new PlayerInteract());
        MinecraftForge.EVENT_BUS.register(new EntitySpawn());

        if(FMLCommonHandler.instance().getSide().isClient())
        {
            MinecraftForge.EVENT_BUS.register(new GameOverlay());
        }
    }
}
