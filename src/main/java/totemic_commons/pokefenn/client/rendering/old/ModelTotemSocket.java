package totemic_commons.pokefenn.client.rendering.old;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

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
        modelTotemSupport = AdvancedModelLoader.loadModel(new ResourceLocation("totemic:models/totemSocket.obj"));
    }

    public void render()
    {
        modelTotemSupport.renderAll();
    }

}
