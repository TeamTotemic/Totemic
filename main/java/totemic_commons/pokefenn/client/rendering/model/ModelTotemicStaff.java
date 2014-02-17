package totemic_commons.pokefenn.client.rendering.model;

import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import totemic_commons.pokefenn.lib.Models;

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
        modelTotemicStaff = AdvancedModelLoader.loadModel(Models.TOTEMIC_STAFF);
    }

    public void render()
    {
        modelTotemicStaff.renderAll();
    }

}
