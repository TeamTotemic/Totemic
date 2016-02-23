package totemic_commons.pokefenn.client.rendering.entity;

import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.IBossDisplayData;

public class BaykokRendering extends RenderEntity
{
    public BaykokRendering(RenderManager renderMgr)
    {
        super(renderMgr);
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        BossStatus.setBossStatus((IBossDisplayData) entity, false);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}
