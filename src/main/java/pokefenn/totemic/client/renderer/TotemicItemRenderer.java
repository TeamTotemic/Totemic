package pokefenn.totemic.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModItems;
import pokefenn.totemic.tile.WindChimeBlockEntity;

public class TotemicItemRenderer extends BlockEntityWithoutLevelRenderer {
    public static final TotemicItemRenderer INSTANCE = new TotemicItemRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());

    private final BlockEntityRenderDispatcher blockEntityRenderer;

    private final WindChimeBlockEntity windChime = new WindChimeBlockEntity(BlockPos.ZERO, ModBlocks.wind_chime.get().defaultBlockState());

    public TotemicItemRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
        this.blockEntityRenderer = pBlockEntityRenderDispatcher;
    }

    @Override
    public void renderByItem(ItemStack pStack, TransformType pTransformType, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        if(pStack.is(ModItems.wind_chime.get())) {
            blockEntityRenderer.renderItem(windChime, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
        }
    }
}
