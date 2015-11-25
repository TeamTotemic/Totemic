package totemic_commons.pokefenn.compat.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;

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
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        boolean isBase = accessor.getTileEntity() instanceof TileTotemBase;

        if(isBase)
        {
            TileTotemBase totemBase = (TileTotemBase) accessor.getTileEntity();
            if(totemBase.isDoingCeremonyEffect())
            {
                currenttip.add(StatCollector.translateToLocal("totemicmisc.activeCeremony"));
                currenttip.add(totemBase.currentCeremony.getLocalizedName());
            }
            else if(totemBase.isDoingStartup())
            {
                currenttip.add(StatCollector.translateToLocal("totemicmisc.startup"));
                currenttip.add(totemBase.startupCeremony.getLocalizedName());
            }
            /*else
                currenttip.add(TileTotemBase.getMusicName(totemBase.musicForTotemEffect));*/

            /*if(totemBase.isMusicSelecting)
                for(int i = 0; i < 4; i++)
                {
                    //currenttip.add();
                }*/
        }

        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
        te.writeToNBT(tag);
        return tag;
    }
}
