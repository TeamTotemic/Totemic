package totemic_commons.pokefenn.client.rendering.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import totemic_commons.pokefenn.lib.Models;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 08/02/14
 * Time: 21:49
 */
@SideOnly(Side.CLIENT)
public class ModelTotemSocket
{

    private IModelCustom modelTotemSupport;

    public ModelTotemSocket()
    {
        modelTotemSupport = AdvancedModelLoader.loadModel(Models.TOTEM_SOCKET);
    }

    public void render()
    {
        modelTotemSupport.renderAll();
    }

}
