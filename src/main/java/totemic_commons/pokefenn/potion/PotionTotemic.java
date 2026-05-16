package totemic_commons.pokefenn.potion;

import javax.annotation.Nullable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionTotemic extends Potion
{
    private final @Nullable ResourceLocation texture;

    public PotionTotemic(int id, boolean isBadEffect, int color, String textureName)
    {
        super(id, isBadEffect, color);
        this.texture = new ResourceLocation("totemic", "textures/potions/" + textureName);
    }

    public PotionTotemic(int id, boolean isBadEffect, int color)
    {
        super(id, isBadEffect, color);
        this.texture = null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc)
    {
        if(texture != null)
        {
            mc.getTextureManager().bindTexture(texture);
            Gui.func_146110_a(x + 6, y + 7, 0.0f, 0.0f, 18, 18, 18f, 18f);
        }
    }
}
