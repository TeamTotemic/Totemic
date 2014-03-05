package totemic_commons.pokefenn.client.rendering.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import totemic_commons.pokefenn.lib.Models;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 01/03/14
 * Time: 20:40
 */

@SideOnly(Side.CLIENT)
public class ModelChlorophyllCrystal
{

    private IModelCustom modelChlorophyllCrystal;

    public ModelChlorophyllCrystal()
    {
        modelChlorophyllCrystal = AdvancedModelLoader.loadModel(Models.CHLOROPHYLL_CRYSTAL);
    }

    public void render()
    {
        modelChlorophyllCrystal.renderAll();
    }


}
