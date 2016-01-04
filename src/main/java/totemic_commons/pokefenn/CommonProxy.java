package totemic_commons.pokefenn;

import net.minecraftforge.fml.common.registry.GameRegistry;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TileTipi;
import totemic_commons.pokefenn.tileentity.TileTotemTorch;
import totemic_commons.pokefenn.tileentity.music.TileDrum;
import totemic_commons.pokefenn.tileentity.music.TileWindChime;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;
import totemic_commons.pokefenn.tileentity.totem.TileTotemPole;

public class CommonProxy
{

    public void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileTotemBase.class, Strings.TILE_TOTEM_INTELLIGENCE);
        GameRegistry.registerTileEntity(TileTotemPole.class, Strings.TILE_TOTEM_SOCKET);
        GameRegistry.registerTileEntity(TileTotemTorch.class, Strings.TOTEM_TORCH_NAME);
        GameRegistry.registerTileEntity(TileDrum.class, Strings.DRUM_NAME);
        GameRegistry.registerTileEntity(TileWindChime.class, Strings.WIND_CHIME_NAME);
        GameRegistry.registerTileEntity(TileTipi.class, Strings.TIPI_NAME);
    }

    public void initRendering()
    {

    }

}
