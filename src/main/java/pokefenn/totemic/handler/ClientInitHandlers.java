package pokefenn.totemic.handler;

import com.google.common.base.Stopwatch;
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
import pokefenn.totemic.Totemic;
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
        var stopwatch = Stopwatch.createStarted();
        for(var totemEffectEntry: ModBlocks.getTotemPoles().columnMap().entrySet()) {
            var modelName = getPoleModelName(totemEffectEntry.getKey());
            var unbakedModel = (BlockModel) event.getModelBakery().getModel(modelName);

            for(var blockO: totemEffectEntry.getValue().values()) {
                var blockName = blockO.getId();
                var block = blockO.get();
                var woodType = block.woodType;

                //The unbaked model will be modified here, but that should be fine since it is not used elsewhere
                unbakedModel.textureMap.replace("wood", Either.left(new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation(woodType.getWoodTexture()))));
                unbakedModel.textureMap.replace("bark", Either.left(new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation(woodType.getBarkTexture()))));
                unbakedModel.textureMap.replace("top", Either.left(new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation(woodType.getTopTexture()))));
                unbakedModel.textureMap.replace("particle", Either.left(new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation(woodType.getParticleTexture()))));

                //Block models
                for(var state: block.getStateDefinition().getPossibleStates()) {
                    var bakedModel = unbakedModel.bake(event.getModelBakery(), unbakedModel, event.getModelBakery().getAtlasSet()::getSprite,
                            BlockModelRotation.by(0, (int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180), modelName, false);
                    event.getModels().put(BlockModelShaper.stateToModelLocation(blockName, state), bakedModel);
                }
                //Item model
                event.getModels().put(new ModelResourceLocation(blockName, "inventory"),
                        unbakedModel.bake(event.getModelBakery(), unbakedModel, event.getModelBakery().getAtlasSet()::getSprite, BlockModelRotation.X0_Y0, modelName, true));
            }
        }
        stopwatch.stop();
        Totemic.logger.info("Totem Pole model baking took {}", stopwatch);

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
}
