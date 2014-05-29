package totemic_commons.pokefenn.client.rendering.old;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

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
        modelChlorophyllCrystal = AdvancedModelLoader.loadModel(new ResourceLocation("totemic:models/chlorophyllCrystal.obj"));
    }

    public void render()
    {
        modelChlorophyllCrystal.renderAll();
    }


}
