package totemic_commons.pokefenn.client.rendering.model;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 17/02/14
 * Time: 11:00
 */
public class ModelTotemicStaff
{

    private IModelCustom modelTotemicStaff;

    public ModelTotemicStaff()
    {
        modelTotemicStaff = AdvancedModelLoader.loadModel(new ResourceLocation("totemic:models/totemicStaff.obj"));
    }

    public void render()
    {
        modelTotemicStaff.renderAll();
    }

}
