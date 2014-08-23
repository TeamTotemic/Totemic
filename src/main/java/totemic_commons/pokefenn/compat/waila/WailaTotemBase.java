package totemic_commons.pokefenn.compat.waila;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.api.music.MusicEnum;
import totemic_commons.pokefenn.api.ceremony.CeremonyRegistry;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;

import java.util.List;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class WailaTotemBase implements IWailaDataProvider
{
    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return null;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        boolean isBase = accessor.getTileEntity() instanceof TileTotemBase;

        if(isBase)
        {
            TileTotemBase totemBase = (TileTotemBase) accessor.getTileEntity();
            if(totemBase.isDoingEffect)
                currenttip.add(CeremonyRegistry.ceremonyRegistry.get(totemBase.currentCeremony).getName());
            else if(totemBase.isDoingStartup)
                currenttip.add(CeremonyRegistry.ceremonyRegistry.get(totemBase.tryingCeremonyID).getName());
            currenttip.add(TileTotemBase.getMusicName(totemBase.musicForTotemEffect));

            if(totemBase.isMusicSelecting)
                for(int i = 0; i < 4; i++)
                {
                    currenttip.add(MusicEnum.values()[i].getMusicName());
                }
        }

        return null;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return null;
    }
}
