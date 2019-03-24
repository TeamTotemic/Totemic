package pokefenn.totemic.client;

import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import pokefenn.totemic.Totemic;

public class ModelBakeHandler {
    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {
        String[] woodTypes = {"oak", "spruce", "birch", "jungle", "acacia", "dark_oak", "cedar"};

        for(String woodType: woodTypes) {
            IUnbakedModel ubModel = event.getModelLoader().getUnbakedModel(new ResourceLocation(Totemic.MOD_ID, "block/" + woodType + "_totem_base"));

            ResourceLocation blockName = new ResourceLocation(Totemic.MOD_ID, woodType + "_totem_base");
            for(EnumFacing facing: EnumFacing.values()) {
                event.getModelRegistry().put(new ModelResourceLocation(blockName, "facing=" + facing.getName()),
                        ubModel.bake(ModelLoader.defaultModelGetter(), ModelLoader.defaultTextureGetter(), TRSRTransformation.getRotation(facing), false, DefaultVertexFormats.BLOCK));
            }
        }
    }
}
