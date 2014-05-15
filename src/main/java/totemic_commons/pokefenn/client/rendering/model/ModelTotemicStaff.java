package totemic_commons.pokefenn.client.rendering.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 17/02/14
 * Time: 11:00
 */

@SideOnly(Side.CLIENT)
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
