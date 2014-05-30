package totemic_commons.pokefenn;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TileTotemTorch;
import totemic_commons.pokefenn.tileentity.music.TileDrum;
import totemic_commons.pokefenn.tileentity.music.TileWindChime;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;
import totemic_commons.pokefenn.tileentity.totem.TileTotemPole;

public class CommonProxy implements IGuiHandler
{

    public static int totempediaGuiID = -1;

    public void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileTotemBase.class, Strings.TILE_TOTEM_INTELLIGENCE);
        GameRegistry.registerTileEntity(TileTotemPole.class, Strings.TILE_TOTEM_SOCKET);
        GameRegistry.registerTileEntity(TileTotemTorch.class, Strings.TOTEM_TORCH_NAME);
        GameRegistry.registerTileEntity(TileDrum.class, Strings.DRUM_NAME);
        GameRegistry.registerTileEntity(TileWindChime.class, Strings.WIND_CHIME_NAME);
    }

    public void initRendering()
    {

    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }

    public void readManuals()
    {

    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }
}
