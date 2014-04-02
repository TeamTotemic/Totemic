package totemic_commons.pokefenn.totem;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.WorldInfo;
import totemic_commons.pokefenn.api.ITotemEffect;
import totemic_commons.pokefenn.tileentity.TileTotemIntelligence;
import totemic_commons.pokefenn.tileentity.TileTotemic;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 23/01/14
 * Time: 14:15
 */
public class TotemEffectSun implements ITotemEffect
{

    public static void effect(TileTotemic totem, int i, boolean intelligence)
    {
        if (totem.getWorldObj().getWorldTime() % 200L == 0)
        {
            if (totem.getWorldObj().isRaining())
            {
                WorldInfo worldinfo = MinecraftServer.getServer().worldServers[0].getWorldInfo();
                worldinfo.setRaining(false);

                if(intelligence)
                {
                    ((TileTotemIntelligence)totem).decreaseChlorophyll(TotemUtil.decrementAmount(i));
                }

            }

        }
    }
}


