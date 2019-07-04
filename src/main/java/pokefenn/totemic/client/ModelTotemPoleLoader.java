package pokefenn.totemic.client;

public enum ModelTotemPoleLoader /*implements ICustomModelLoader*/ { //TODO: Not working properly yet
    INSTANCE;

    /*private static final Pattern modelNamePattern = Pattern.compile("models/block/totem_pole/(\\w+)/(\\w+)");

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
    }

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return modelLocation.getNamespace().equals(Totemic.MOD_ID) && modelLocation.getPath().startsWith("models/block/totem_pole/");
    }

    @Override
    public IUnbakedModel loadModel(ResourceLocation modelLocation) throws Exception {
        Matcher matcher = modelNamePattern.matcher(modelLocation.getPath());
        if(!matcher.matches())
            return ModelLoaderRegistry.getMissingModel();
        String woodType = matcher.group(1);
        String totemEffect = matcher.group(2);

        return new ModelTotemPole(woodType, totemEffect);
    }

    private static class ModelTotemPole implements IUnbakedModel {
        final ResourceLocation woodTypeLoc;
        final ResourceLocation totemEffectLoc;

        public ModelTotemPole(String woodType, String totemEffect) {
            this.woodTypeLoc = new ResourceLocation(Totemic.MOD_ID, "block/" + woodType + "_totem_pole");
            this.totemEffectLoc = new ResourceLocation(Totemic.MOD_ID, "block/totem_pole_" + totemEffect);
        }

        @Override
        @Nullable
        public IBakedModel bake(Function<ResourceLocation, IUnbakedModel> modelGetter,
                Function<ResourceLocation, TextureAtlasSprite> spriteGetter, IModelState state, boolean uvlock, VertexFormat format) {
            return modelGetter.apply(totemEffectLoc).bake(modelGetter, spriteGetter, state, uvlock, format);
        }

        @Override
        public Collection<ResourceLocation> getDependencies() {
            return ImmutableList.of(woodTypeLoc, totemEffectLoc);
        }

        @Override
        public Collection<ResourceLocation> getTextures(Function<ResourceLocation, IUnbakedModel> modelGetter, Set<String> missingTextureErrors) {
            HashSet<ResourceLocation> set = new HashSet<>(modelGetter.apply(woodTypeLoc).getTextures(modelGetter, missingTextureErrors));
            set.addAll(modelGetter.apply(totemEffectLoc).getTextures(modelGetter, missingTextureErrors));
            return set;
        }
    }*/
}
