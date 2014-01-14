package totemic_commons.pokefenn.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import totemic_commons.pokefenn.inventory.ContainerTotemBase;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.Textures;
import totemic_commons.pokefenn.tileentity.TileTotemBase;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 25/11/13
 * Time: 20:01
 */


@SideOnly(Side.CLIENT)
public class GuiTotemBase extends GuiContainer {

    private TileTotemBase tileTotemBase;

    public GuiTotemBase(InventoryPlayer inventoryPlayer, TileTotemBase tileTotemBase)
    {

        super(new ContainerTotemBase(inventoryPlayer, tileTotemBase));
        this.tileTotemBase = tileTotemBase;
        xSize = 176;
        ySize = 166;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {

        String containerName = tileTotemBase.isInvNameLocalized() ? tileTotemBase.getInvName() : StatCollector.translateToLocal(tileTotemBase.getInvName());
        fontRenderer.drawString(containerName, xSize / 2 - fontRenderer.getStringWidth(containerName) / 2, 6, 4210752);
        fontRenderer.drawString(StatCollector.translateToLocal(Strings.CONTAINER_INVENTORY), 8, ySize - 93, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(Textures.GUI_TOTEM_BASE);
        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
    }


}
