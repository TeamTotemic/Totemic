package totemic_commons.pokefenn.handler;

import java.util.UUID;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PlayerRender
{
    private static final UUID ljfaUUID = UUID.fromString("462b56b5-3047-4efd-901c-e1ecc062af30");
    private static final String annaSkinId = "skins/4797da64c116258ba4aa30eb2cedddac4c1867e7ed8bec4907b2148f2219a81";

    private int annaHatDisplayList = 0;

    @SubscribeEvent
    public void onPlayerRender(RenderPlayerEvent.Post event)
    {
        AbstractClientPlayer player = (AbstractClientPlayer)event.getEntityPlayer();
        if(ljfaUUID.equals(player.getUniqueID()) && annaSkinId.equals(player.getLocationSkin().getResourcePath()))
        {
            float yaw = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * event.getPartialRenderTick();
            float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * event.getPartialRenderTick();
            float pitchZ = (float)Math.toDegrees(event.getRenderer().getMainModel().bipedHead.rotateAngleZ);

            GlStateManager.pushMatrix();
            if(player.isSneaking())
                GlStateManager.translate(0, -0.25F, 0);
            GlStateManager.translate(event.getX(), event.getY() + 1.501F * 0.9375F, event.getZ());
            GlStateManager.rotate(pitchZ, 0, 0, 1);
            GlStateManager.rotate(yaw - 90, 0, -1, 0);
            GlStateManager.rotate(pitch + 180, 0, 0, 1);
            if(!player.inventory.armorItemInSlot(3).isEmpty())
                GlStateManager.translate(0.01F, -0.04F, 0);

            if(annaHatDisplayList == 0)
                createAnnaHatDisplayList();
            GL11.glCallList(annaHatDisplayList);

            GlStateManager.popMatrix();
        }
    }

    private void createAnnaHatDisplayList()
    {
        annaHatDisplayList = GLAllocation.generateDisplayLists(1);
        GL11.glNewList(annaHatDisplayList, GL11.GL_COMPILE);

        float scale = 1 / 64F;
        float offset = (float) (7 * Math.sin(Math.toRadians(45)) + 7 / 2F) * scale;

        GL11.glTranslatef(15 * scale - offset, -0.7F, 15 * scale - offset);
        GL11.glRotatef(-25, 1, 0, -1);
        GL11.glTranslatef(offset, 0, offset);
        GL11.glScalef(scale, scale, scale);

        GL11.glColor3f(0.5F, 0, 0);
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT | GL11.GL_TRANSFORM_BIT);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);

        Tessellator tes = Tessellator.getInstance();
        VertexBuffer buf = tes.getBuffer();

        float height = 9;
        float inner = 11;
        float outer = 18;
        int points = 12;

        //Cylinder mantle
        buf.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_NORMAL);
        for(int i = 0; i <= points; i++)
        {
            float angle = 2 * (float) Math.PI * (i + 0.5F)/points;
            float cos = (float) Math.cos(angle);
            float sin = (float) Math.sin(angle);
            buf.pos(inner * cos, 0,      inner * sin).normal(0.95F * cos, -0.31225F, 0.95F * sin).endVertex();
            buf.pos(inner * cos, height, inner * sin).normal(0.95F * cos, -0.31225F, 0.95F * sin).endVertex();
        }
        tes.draw();

        //Cylinder top
        buf.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_NORMAL);
        buf.pos(0, 0, 0).normal(0, -1, 0).endVertex();
        for(int i = 0; i <= points; i++)
        {
            float angle = 2 * (float) Math.PI * (i + 0.5F) / points;
            buf.pos(inner * Math.cos(angle), 0, inner * Math.sin(angle)).normal(0, -1, 0).endVertex();
        }
        tes.draw();

        //Outer part
        buf.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_NORMAL);
        buf.pos(0, height, 0).normal(0, -1, 0).endVertex();
        for(int i = 0; i <= points; i++)
        {
            float angle = 2 * (float) Math.PI * (i + 0.5F)/points;
            buf.pos(outer * Math.cos(angle), height, outer * Math.sin(angle)).normal(0, -1, 0).endVertex();
        }
        tes.draw();

        //Bottom
        buf.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_NORMAL);
        buf.pos(0, height, 0).normal(0, 1, 0).endVertex();
        for(int i = 0; i <= points; i++)
        {
            float angle = 2 * (float) Math.PI * -(i + 0.5F)/points;
            buf.pos(outer * Math.cos(angle), height, outer * Math.sin(angle)).normal(0, 1, 0).endVertex();
        }
        tes.draw();

        GL11.glPopAttrib();
        GL11.glColor3f(1, 1, 1);

        GL11.glEndList();
    }
}
