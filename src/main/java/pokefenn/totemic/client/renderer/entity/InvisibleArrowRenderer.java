package pokefenn.totemic.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.entity.InvisibleArrow;

public class InvisibleArrowRenderer extends ArrowRenderer<InvisibleArrow> {
    private static final ResourceLocation INVIS_ARROW_TEXTURE = new ResourceLocation(TotemicAPI.MOD_ID, "textures/entity/invis_arrow.png");

    public InvisibleArrowRenderer(Context pContext) {
        super(pContext);
    }

    @SuppressWarnings("resource")
    @Override
    public void render(InvisibleArrow pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.getOwner() == Minecraft.getInstance().player) {
            //Override render type used by super method
            MultiBufferSource translucentBufferSrc = renderType -> pBuffer.getBuffer(RenderType.entityTranslucentCull(INVIS_ARROW_TEXTURE));
            super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, translucentBufferSrc, pPackedLight);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(InvisibleArrow pEntity) {
        return INVIS_ARROW_TEXTURE;
    }
}
