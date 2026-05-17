package totemic_commons.pokefenn.potion;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionTotemic extends Potion
{
    private static final ResourceLocation texture = new ResourceLocation("totemic", "textures/gui/potions.png");

    private final int textureIndex;

    public PotionTotemic(int id, boolean isBadEffect, int color, int textureIndex)
    {
        super(id, isBadEffect, color);
        this.textureIndex = textureIndex;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc)
    {
        mc.getTextureManager().bindTexture(texture);
        final int iconSize = 18;
        final int texWidth = 64, texHeight = 32;
        Gui.func_146110_a(x + 6, y + 7,  textureIndex * iconSize, 0.0f,  iconSize, iconSize,  texWidth, texHeight);
    }
}
