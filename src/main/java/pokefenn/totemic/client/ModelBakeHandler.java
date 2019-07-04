package pokefenn.totemic.client;

public class ModelBakeHandler { //TODO: Not working properly yet
    /*@SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {
        for(WoodType woodType: WoodType.getWoodTypes()) {
            IUnbakedModel ubModel = event.getModelLoader().getUnbakedModel(new ResourceLocation(Totemic.MOD_ID, "block/" + woodType.getName() + "_totem_base"));

            ResourceLocation blockName = new ResourceLocation(Totemic.MOD_ID, woodType.getName() + "_totem_base");
            for(EnumFacing facing: EnumFacing.values()) {
                event.getModelRegistry().put(new ModelResourceLocation(blockName, "facing=" + facing.getName()),
                        ubModel.bake(event.getModelLoader()::getUnbakedModel, ModelLoader.defaultTextureGetter(), TRSRTransformation.getRotation(facing), false, DefaultVertexFormats.BLOCK));
            }

            for(TotemEffect totemEffect: GameRegistry.findRegistry(TotemEffect.class)) {
                ubModel = ModelLoaderRegistry.getModelOrMissing(new ResourceLocation(Totemic.MOD_ID, "block/totem_pole/" + woodType.getName() + '/' + totemEffect.getRegistryName().getPath()));
                blockName = new ResourceLocation(Totemic.MOD_ID, woodType.getName() + "_totem_pole_" + totemEffect.getRegistryName().getPath());
                for(EnumFacing facing: EnumFacing.values()) {
                    event.getModelRegistry().put(new ModelResourceLocation(blockName, "facing=" + facing.getName()),
                            ubModel.bake(event.getModelLoader()::getUnbakedModel, ModelLoader.defaultTextureGetter(), TRSRTransformation.getRotation(facing), false, DefaultVertexFormats.BLOCK));
                }
            }
        }
    }*/
}
