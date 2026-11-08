package pokefenn.totemic.neoforge.client.totem;

import java.util.List;
import java.util.function.Consumer;

import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

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
import pokefenn.totemic.api.totem.TotemWoodType;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModDataComponents;

public class TotemBaseSpecialRenderer implements SpecialModelRenderer<TotemWoodType> {
    @Override
    public void submit(@Nullable TotemWoodType argument, PoseStack poseStack, SubmitNodeCollector coll, int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor) {
        // TODO: We might want to cache this
        var blockStateModel = Minecraft.getInstance().getModelManager().getBlockStateModelSet().get(ModBlocks.totem_base.get().defaultBlockState());
        if(blockStateModel instanceof DataDependentBlockStateModel dataDepModel) {
            @SuppressWarnings("unchecked")
            var part = dataDepModel.getModel(argument);
            var renderType = (part.materialFlags() & BakedQuad.FLAG_TRANSLUCENT) != 0 ? Sheets.translucentBlockSheet() : Sheets.cutoutBlockSheet();
            coll.submitBlockModel(poseStack, renderType, List.of(part), BlockModelRenderState.EMPTY_TINTS, lightCoords, overlayCoords, outlineColor);
        }
        else {
            // If for some reason the block state model is not of the type dynamic_totem_base, render a missing model
            var part = Minecraft.getInstance().getModelManager().missingModels.blockPart();
            coll.submitBlockModel(poseStack, Sheets.cutoutBlockSheet(), List.of(part), BlockModelRenderState.EMPTY_TINTS, lightCoords, overlayCoords, outlineColor);
        }
    }

    @Override
    public void getExtents(Consumer<Vector3fc> output) {
        var blockStateModel = Minecraft.getInstance().getModelManager().getBlockStateModelSet().get(ModBlocks.totem_base.get().defaultBlockState());
        if(blockStateModel instanceof DataDependentBlockStateModel dataDepModel) {
            @SuppressWarnings("unchecked")
            var part = dataDepModel.getModel(null);
            CuboidItemModelWrapper.computeExtents(part.getQuads(null));
        }
    }

    @Override
    public @Nullable TotemWoodType extractArgument(ItemStack stack) {
        return stack.get(ModDataComponents.WOOD_TYPE.get());
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked<TotemWoodType> {
        public static final MapCodec<TotemBaseSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(new TotemBaseSpecialRenderer.Unbaked());

        @Override
        public TotemBaseSpecialRenderer bake(BakingContext context) {
            return new TotemBaseSpecialRenderer();
        }

        @Override
        public MapCodec<TotemBaseSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }
    }
}
