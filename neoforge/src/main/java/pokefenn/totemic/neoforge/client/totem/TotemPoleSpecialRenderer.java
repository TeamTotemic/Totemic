package pokefenn.totemic.neoforge.client.totem;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import com.google.common.base.Suppliers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.world.item.ItemStack;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModDataComponents;

public class TotemPoleSpecialRenderer implements SpecialModelRenderer<TotemPoleModelData> {
    // Note: We don't have to worry about the hard problem of cache invalidation when the models are reloaded,
    // since the instance of this renderer is also recreated then.
    @SuppressWarnings("unchecked")
    private final Supplier<@Nullable DataDependentBlockStateModel<TotemPoleModelData>> blockStateModel = Suppliers.memoize(() -> {
        var model = Minecraft.getInstance().getModelManager().getBlockStateModelSet().get(ModBlocks.totem_pole.get().defaultBlockState());
        if(model instanceof DataDependentBlockStateModel dataDepModel)
            return dataDepModel;
        else
            return null;
    });

    @Override
    public void submit(@Nullable TotemPoleModelData argument, PoseStack poseStack, SubmitNodeCollector coll, int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor) {
        var model = blockStateModel.get();
        if(model != null) {
            var part = model.getPart(argument);
            var renderType = (part.materialFlags() & BakedQuad.FLAG_TRANSLUCENT) != 0 ? Sheets.translucentBlockSheet() : Sheets.cutoutBlockSheet();
            coll.submitBlockModel(poseStack, renderType, List.of(part), BlockModelRenderState.EMPTY_TINTS, lightCoords, overlayCoords, outlineColor);
        }
        else {
            // If for some reason the block state model is not of the type dynamic_totem_pole, render a missing model
            var part = Minecraft.getInstance().getModelManager().missingModels.blockPart();
            coll.submitBlockModel(poseStack, Sheets.cutoutBlockSheet(), List.of(part), BlockModelRenderState.EMPTY_TINTS, lightCoords, overlayCoords, outlineColor);
        }
    }

    @Override
    public void getExtents(Consumer<Vector3fc> output) {
        var model = blockStateModel.get();
        if(model != null) {
            // take the union of the extents for all the carvings
            for(var carving: TotemicAPI.get().registry().totemCarvings()) {
                var data = new TotemPoleModelData(ModContent.oak.get(), carving);
                var part = model.getPart(data);
                CuboidItemModelWrapper.computeExtents(part.getQuads(null));
            }
        }
        // else we don't need to do anything, can use the default extents for the missing model
    }

    @Override
    public @Nullable TotemPoleModelData extractArgument(ItemStack stack) {
        var woodType = stack.getOrDefault(ModDataComponents.WOOD_TYPE.get(), ModContent.oak.get());
        var carving = stack.getOrDefault(ModDataComponents.CARVING.get(), ModContent.none.get());
        return new TotemPoleModelData(woodType, carving);
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked<TotemPoleModelData> {
        public static final MapCodec<TotemPoleSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(new TotemPoleSpecialRenderer.Unbaked());

        @Override
        public TotemPoleSpecialRenderer bake(BakingContext context) {
            return new TotemPoleSpecialRenderer();
        }

        @Override
        public MapCodec<TotemPoleSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }
    }
}
