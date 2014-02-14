package totemic_commons.pokefenn.client.rendering.model;

import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import totemic_commons.pokefenn.lib.Models;

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
        modelTotemDraining = AdvancedModelLoader.loadModel(Models.TOTEM_DRAINING);
    }

    public void render()
    {
        modelTotemDraining.renderAll();
    }

}
