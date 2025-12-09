package totemic_commons.pokefenn.client.rendering.item;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

/*
 * This custom renderer is necessary for applying the correct transformations when the bow is rendered in third person,
 * since unfortunately the vanilla bow is hardcoded in the renderEquippedItems methods in RenderPlayer and RenderBiped.
 * 
 * This code is adapted from More Bows: Restrung! by NeRdTheNed, which is licensed under CC0.
 */
public class ItemBaykokBowRenderer implements IItemRenderer
{
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return type == ItemRenderType.EQUIPPED;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        // Only BLOCK_3D and EQUIPPED_BLOCK are relevant for equipped items. We don't want the extra transformation
        // from BLOCK_3D (see RenderBiped.renderEquippedItems).
        // We just use EQUIPPED_BLOCK because it does fewer transformations (see ForgeHooksClient.renderEquippedItem)
        // which we undo below anway.
        return helper == ItemRendererHelper.EQUIPPED_BLOCK;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        // Undo the transformation done in ForgeHooksClient.renderEquippedItem by simply popping the matrix
        GL11.glPopMatrix();
        
        EntityLivingBase entity = (EntityLivingBase)data[1];
        boolean isWitch = entity instanceof EntityWitch; // another special case where vanilla bows are hardcoded
        if(isWitch) {
            // Undo the extra item transformation done in RenderWitch.renderEquippedItems
            GL11.glRotatef(-40.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(15.0F, 1.0F, 0.0F, 0.0F);
        }

        // Undo the transformation for regular items in RenderBiped.renderEquippedItems
        float scale = 1.0F / 0.375F;
        GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
        GL11.glScalef(scale, scale, scale);
        GL11.glTranslatef(-0.25F, -0.1875F, 0.1875F);

        // Apply the bow transformation
        scale = 0.625F;
        GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
        GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(scale, -scale, scale);
        GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);

        if(isWitch) {
            // Reapply the Witch transformation
            GL11.glRotatef(-15.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(40.0F, 0.0F, 0.0F, 1.0F);
        }

        // Then we need to render the bow. However, we can't just use ItemRenderer.renderItem like the vanilla code,
        // since this would call this method in infinite recursion.
        // To avoid copying almost the entire item render pipeline, we can use the fact that this renderer only applies
        // to ItemRenderType.EQUIPPED. So we can just use a different ItemRenderType and let vanilla code handle it.
        RenderManager.instance.itemRenderer.renderItem(entity, item, 0, ItemRenderType.ENTITY);

        GL11.glPushMatrix();
    }
}
