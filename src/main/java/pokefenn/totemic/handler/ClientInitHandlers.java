package pokefenn.totemic.handler;

import com.mojang.datafixers.util.Either;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.block.totem.TotemPoleBlock;
import pokefenn.totemic.init.ModBlocks;

/**
 * Contains event handlers for various client-only events fired during initialization (on the mod event bus).
 */
public class ClientInitHandlers {
    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, tintGetter, pos, tintIndex) -> TotemPoleBlock.getBlockColor(tintIndex),
                ModBlocks.getTotemPoles().values().stream()
                        .map(RegistryObject::get)
                        .toArray(Block[]::new));
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> TotemPoleBlock.getBlockColor(tintIndex),
                ModBlocks.getTotemPoles().values().stream()
                        .map(RegistryObject::get)
                        .toArray(ItemLike[]::new));
    }

    private static final ResourceLocation OPAQUE_CEDAR_LEAVES = new ResourceLocation(TotemicAPI.MOD_ID, "block/cedar_leaves_opaque");

    @SubscribeEvent
    public static void registerAdditionalModels(ModelEvent.RegisterAdditional event) {
        for(var blockO: ModBlocks.getTotemPoles().values())
            event.register(getPoleModelName(blockO.get().effect));

        if(!Minecraft.useFancyGraphics()) {
            event.register(OPAQUE_CEDAR_LEAVES);
        }
    }

    @SuppressWarnings({ "resource", "deprecation" })
    @SubscribeEvent
    //FIXME: This does work but it spams the log since the block state definitions and item models can't be loaded
    public static void onBakingComplete(ModelEvent.BakingCompleted event) {
        for(var blockO: ModBlocks.getTotemPoles().values()) {
            var blockName = blockO.getId();
            var block = blockO.get();
            var woodType = block.woodType;
            var modelName = getPoleModelName(block.effect);
            var unbakedModel = (BlockModel) event.getModelBakery().getModel(modelName);

            var retexturedModel = copyBlockModel(unbakedModel);
            retexturedModel.textureMap.put("wood", Either.left(new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation(woodType.getWoodTexture()))));
            retexturedModel.textureMap.put("bark", Either.left(new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation(woodType.getBarkTexture()))));
            retexturedModel.textureMap.put("top", Either.left(new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation(woodType.getTopTexture()))));
            retexturedModel.textureMap.put("particle", Either.left(new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation(woodType.getParticleTexture()))));

            //Block models
            for(var state: block.getStateDefinition().getPossibleStates()) {
                var bakedModel = retexturedModel.bake(event.getModelBakery(), retexturedModel, event.getModelBakery().getAtlasSet()::getSprite,
                        BlockModelRotation.by(0, (int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180), modelName, false);
                event.getModels().put(BlockModelShaper.stateToModelLocation(blockName, state), bakedModel);
            }
            //Item model
            event.getModels().put(new ModelResourceLocation(blockName, "inventory"),
                    retexturedModel.bake(event.getModelBakery(), retexturedModel, event.getModelBakery().getAtlasSet()::getSprite, BlockModelRotation.X0_Y0, modelName, true));
        }

        if(!Minecraft.useFancyGraphics()) {
            //Replace all the occurrences of the cedar leaves model with opaque ones.
            //Not a perfect solution, since the resources are not reloaded on changing the graphics settings.
            var opaqueLeaves = event.getModels().get(OPAQUE_CEDAR_LEAVES);
            if(opaqueLeaves != null)
                for(var state: ModBlocks.cedar_leaves.get().getStateDefinition().getPossibleStates()) {
                    event.getModels().put(BlockModelShaper.stateToModelLocation(state), opaqueLeaves);
                }
        }
    }

    private static ResourceLocation getPoleModelName(TotemEffect effect) {
        var effectName = effect.getRegistryName();
        return new ResourceLocation(effectName.getNamespace(), "block/totem_pole_" + effectName.getPath());
    }

    @SuppressWarnings("deprecation")
    private static BlockModel copyBlockModel(BlockModel model) {
        return new BlockModel(model.getParentLocation(), model.getElements(), model.textureMap, model.hasAmbientOcclusion, model.getGuiLight(), model.getTransforms(), model.getOverrides());
    }
}
