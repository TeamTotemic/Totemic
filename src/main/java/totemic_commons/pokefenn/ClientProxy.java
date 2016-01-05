package totemic_commons.pokefenn;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import totemic_commons.pokefenn.client.rendering.entity.BuffaloRendering;
import totemic_commons.pokefenn.client.rendering.model.ModelBuffalo;
import totemic_commons.pokefenn.client.rendering.tileentity.TileTipiRenderer;
import totemic_commons.pokefenn.client.rendering.tileentity.TileWindChimeRenderer;
import totemic_commons.pokefenn.entity.animal.EntityBuffalo;
import totemic_commons.pokefenn.tileentity.TileTipi;
import totemic_commons.pokefenn.tileentity.music.TileWindChime;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit()
    {
        super.preInit();
        RenderingRegistry.registerEntityRenderingHandler(EntityBuffalo.class, mgr -> new BuffaloRendering(mgr, new ModelBuffalo(), 0.5F));
    }

    @Override
    public void initRendering()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileWindChime.class, new TileWindChimeRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTipi.class, new TileTipiRenderer());
    }
}
