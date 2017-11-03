package pokefenn.totemic.waila;

import java.util.List;

import javax.annotation.Nonnull;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import pokefenn.totemic.tileentity.totem.TileTotemPole;

@SuppressWarnings("null")
public class WailaTotemPole implements IWailaDataProvider
{
    @Override
    @Nonnull
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        TileTotemPole pole = (TileTotemPole) accessor.getTileEntity();
        if(pole.getEffect() != null)
            currenttip.add(I18n.format(pole.getEffect().getUnlocalizedName()));
        return currenttip;
    }
}
