package totemic_commons.pokefenn.client.rendering.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import totemic_commons.pokefenn.lib.Models;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 10/03/14
 * Time: 08:22
 */
@SideOnly(Side.CLIENT)
public class ModelTotemIntelligence
{

    private IModelCustom modelTotemIntelligence;

    public ModelTotemIntelligence()
    {
        modelTotemIntelligence = AdvancedModelLoader.loadModel(Models.TOTEM_INTELLIGENCE);
    }

    public void render()
    {
        modelTotemIntelligence.renderAll();
    }


}
