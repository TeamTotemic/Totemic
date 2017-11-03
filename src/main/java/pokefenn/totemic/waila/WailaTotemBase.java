package pokefenn.totemic.waila;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.tileentity.totem.StateCeremonyEffect;
import pokefenn.totemic.tileentity.totem.StateSelection;
import pokefenn.totemic.tileentity.totem.StateStartup;
import pokefenn.totemic.tileentity.totem.TileTotemBase;

@SuppressWarnings("null")
public class WailaTotemBase implements IWailaDataProvider
{
    @Override
    @Nonnull
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        TileTotemBase tile = (TileTotemBase) accessor.getTileEntity();
        if(tile.getState() instanceof StateSelection)
        {
            String selectors = ((StateSelection) tile.getState()).getSelectors().stream()
                    .map(instr -> I18n.format(instr.getUnlocalizedName()))
                    .collect(Collectors.joining(", "));
            currenttip.add(I18n.format("totemicmisc.selection", selectors));
        }
        else if(tile.getState() instanceof StateStartup)
        {
            Ceremony ceremony = ((StateStartup) tile.getState()).getCeremony();
            currenttip.add(I18n.format("totemic.waila.ceremonyStartup", I18n.format(ceremony.getUnlocalizedName())));
        }
        else if(tile.getState() instanceof StateCeremonyEffect)
        {
            Ceremony ceremony = ((StateCeremonyEffect) tile.getState()).getCeremony();
            currenttip.add(I18n.format("totemic.waila.ceremonyEffect", I18n.format(ceremony.getUnlocalizedName())));
        }
        return currenttip;
    }
}
