package totemic_commons.pokefenn.client.rendering.model;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 09/02/14
 * Time: 21:45
 */
public class ModelTotemDraining
{

    private IModelCustom modelTotemDraining;

    public ModelTotemDraining()
    {
        modelTotemDraining = AdvancedModelLoader.loadModel(new ResourceLocation("totemic:models/totemSocket.obj"));
    }

    public void render()
    {
        modelTotemDraining.renderAll();
    }

}
