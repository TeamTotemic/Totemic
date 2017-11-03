package pokefenn.totemic.waila;

import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.api.WailaPlugin;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.block.totem.BlockTotemPole;

@WailaPlugin(Totemic.MOD_ID)
public class ModWailaPlugin implements IWailaPlugin
{
    @Override
    public void register(IWailaRegistrar registrar)
    {
        registrar.registerBodyProvider(new WailaTotemPole(), BlockTotemPole.class);
    }
}
