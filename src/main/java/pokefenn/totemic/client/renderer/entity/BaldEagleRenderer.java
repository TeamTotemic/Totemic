package pokefenn.totemic.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.client.ModModelLayers;
import pokefenn.totemic.client.model.BaldEagleModel;
import pokefenn.totemic.entity.BaldEagle;

public class BaldEagleRenderer extends MobRenderer<BaldEagle, BaldEagleModel<BaldEagle>> {
    private static final ResourceLocation BALD_EAGLE_TEXTURE = Totemic.resloc("textures/entity/bald_eagle.png");

    public BaldEagleRenderer(Context ctx) {
        super(ctx, new BaldEagleModel<>(ctx.bakeLayer(ModModelLayers.BALD_EAGLE)), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(BaldEagle entity) {
        return BALD_EAGLE_TEXTURE;
    }

    @Override
    public float getBob(BaldEagle pLivingBase, float pPartialTicks) {
        float f = Mth.lerp(pPartialTicks, pLivingBase.oFlap, pLivingBase.flap);
        float f1 = Mth.lerp(pPartialTicks, pLivingBase.oFlapSpeed, pLivingBase.flapSpeed);
        return (Mth.sin(f) + 1.0F) * f1;
    }
}
