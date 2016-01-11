package totemic_commons.pokefenn;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import totemic_commons.pokefenn.client.rendering.entity.BuffaloRendering;
import totemic_commons.pokefenn.client.rendering.model.ModelBuffalo;
import totemic_commons.pokefenn.client.rendering.tileentity.TileTipiRenderer;
import totemic_commons.pokefenn.client.rendering.tileentity.TileWindChimeRenderer;
import totemic_commons.pokefenn.entity.animal.EntityBuffalo;
import totemic_commons.pokefenn.event.GameOverlay;
import totemic_commons.pokefenn.tileentity.TileTipi;
import totemic_commons.pokefenn.tileentity.music.TileWindChime;
import totemic_commons.pokefenn.totempedia.LexiconData;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        RenderingRegistry.registerEntityRenderingHandler(EntityBuffalo.class, mgr -> new BuffaloRendering(mgr, new ModelBuffalo(), 0.5F));
        ModBlocks.setStateMappers();
        ModBlocks.setItemModels();
        ModItems.setItemModels();
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
        initTESRs();
        LexiconData.init();
    }

    @Override
    protected void registerEventHandlers()
    {
        super.registerEventHandlers();
        MinecraftForge.EVENT_BUS.register(new GameOverlay());
    }

    private void initTESRs()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileWindChime.class, new TileWindChimeRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTipi.class, new TileTipiRenderer());
    }
}
