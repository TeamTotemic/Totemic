package totemic_commons.pokefenn.util;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 26/02/14
 * Time: 17:55
 */
public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case 1:
                //return new GuiTotempedia();

            default:
                return null;
        }

    }
}
