package totemic_commons.pokefenn;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import totemic_commons.pokefenn.client.rendering.entity.BuffaloRendering;
import totemic_commons.pokefenn.client.rendering.model.ModelBuffalo;
import totemic_commons.pokefenn.client.rendering.tileentity.*;
import totemic_commons.pokefenn.entity.animal.EntityBuffalo;
import totemic_commons.pokefenn.tileentity.TileTipi;
import totemic_commons.pokefenn.tileentity.TileTotemTorch;
import totemic_commons.pokefenn.tileentity.music.TileDrum;
import totemic_commons.pokefenn.tileentity.music.TileWindChime;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;
import totemic_commons.pokefenn.tileentity.totem.TileTotemPole;

public class ClientProxy extends CommonProxy
{
    @Override
    public void initRendering()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityBuffalo.class, new BuffaloRendering(new ModelBuffalo(), 0.5F));

        ClientRegistry.bindTileEntitySpecialRenderer(TileWindChime.class, new TileWindChimeRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTipi.class, new TileTipiRenderer());
    }
}
