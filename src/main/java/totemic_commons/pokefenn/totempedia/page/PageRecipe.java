/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Totemic Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 */
package totemic_commons.pokefenn.totempedia.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.client.RenderHelper;
import totemic_commons.pokefenn.client.gui.GuiLexiconEntry;
import vazkii.botania.totemic_custom.api.internal.IGuiLexiconEntry;
import vazkii.botania.totemic_custom.api.lexicon.LexiconPage;
import vazkii.botania.totemic_custom.api.lexicon.LexiconRecipeMappings;

public class PageRecipe extends LexiconPage
{
    protected int relativeMouseX, relativeMouseY;
    protected ItemStack tooltipStack, tooltipContainerStack;
    protected boolean tooltipEntry;

    static boolean mouseDownLastTick = false;

    public PageRecipe(String unlocalizedName)
    {
        super(unlocalizedName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderScreen(IGuiLexiconEntry gui, int mx, int my)
    {
        relativeMouseX = mx;
        relativeMouseY = my;

        renderRecipe(gui, mx, my);

        int width = gui.getWidth() - 30;
        int height = gui.getHeight();
        int x = gui.getLeft() + 16;
        int y = gui.getTop() + height - 40;
        PageText.renderText(x, y, width, height, getUnlocalizedName());

        if(tooltipStack != null)
        {
            List<String> tooltipData = tooltipStack.getTooltip(Minecraft.getMinecraft().player, false);
            List<String> parsedTooltip = new ArrayList<>();
            boolean first = true;

            for(String s : tooltipData)
            {
                String s_ = s;
                if(!first)
                    s_ = TextFormatting.GRAY + s;
                parsedTooltip.add(s_);
                first = false;
            }

            RenderHelper.renderTooltip(mx, my, parsedTooltip);

            int tooltipY = 8 + tooltipData.size() * 11;

            if(tooltipEntry)
            {
                RenderHelper.renderTooltipOrange(mx, my + tooltipY, Arrays.asList(TextFormatting.GRAY + I18n.format("totemicmisc.clickToRecipe")));
                tooltipY += 18;
            }

            if(tooltipContainerStack != null)
                RenderHelper.renderTooltipGreen(mx, my + tooltipY, Arrays.asList(TextFormatting.AQUA + I18n.format("totemicmisc.craftingContainer"), tooltipContainerStack.getDisplayName()));
        }

        tooltipStack = tooltipContainerStack = null;
        tooltipEntry = false;
        GL11.glDisable(GL11.GL_BLEND);
        mouseDownLastTick = Mouse.isButtonDown(0);
    }

    @SideOnly(Side.CLIENT)
    public void renderRecipe(IGuiLexiconEntry gui, int mx, int my)
    {
    }

    @SideOnly(Side.CLIENT)
    public void renderItemAtAngle(IGuiLexiconEntry gui, int angle, ItemStack stack)
    {
        if(stack == null || stack.getItem() == null)
            return;

        ItemStack workStack = stack.copy();

        if(workStack.getItemDamage() == Short.MAX_VALUE || workStack.getItemDamage() == -1)
            workStack.setItemDamage(0);

        angle -= 90;
        int radius = 32;
        double xPos = gui.getLeft() + Math.cos(angle * Math.PI / 180D) * radius + gui.getWidth() / 2 - 8;
        double yPos = gui.getTop() + Math.sin(angle * Math.PI / 180D) * radius + 53;

        renderItem(gui, (int) xPos, (int) yPos, workStack, false);
    }

    @SideOnly(Side.CLIENT)
    public void renderItemAtGridPos(IGuiLexiconEntry gui, int x, int y, ItemStack stack, boolean accountForContainer)
    {
        if(stack == null || stack.getItem() == null)
            return;
        stack = stack.copy();

        if(stack.getItemDamage() == Short.MAX_VALUE)
            stack.setItemDamage(0);

        int xPos = gui.getLeft() + x * 29 + 7 + (y == 0 && x == 3 ? 10 : 0);
        int yPos = gui.getTop() + y * 29 + 24 - (y == 0 ? 7 : 0);
        ItemStack stack1 = stack.copy();
        if(stack1.getItemDamage() == -1)
            stack1.setItemDamage(0);

        renderItem(gui, xPos, yPos, stack1, accountForContainer);
    }

    @SideOnly(Side.CLIENT)
    public void renderItem(IGuiLexiconEntry gui, int xPos, int yPos, ItemStack stack, boolean accountForContainer)
    {
        RenderItem render = Minecraft.getMinecraft().getRenderItem();
        boolean mouseDown = Mouse.isButtonDown(0);

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        render.renderItemAndEffectIntoGUI(stack, xPos, yPos);
        render.renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRendererObj, stack, xPos, yPos, null);
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();

        if(relativeMouseX >= xPos && relativeMouseY >= yPos && relativeMouseX <= xPos + 16 && relativeMouseY <= yPos + 16)
        {
            tooltipStack = stack;

            LexiconRecipeMappings.EntryData data = LexiconRecipeMappings.getDataForStack(tooltipStack);
            if(data != null && (data.entry != gui.getEntry() || data.page != gui.getPageOn()))
            {
                tooltipEntry = true;

                if(!mouseDownLastTick && mouseDown && GuiScreen.isShiftKeyDown())
                {
                    GuiLexiconEntry newGui = new GuiLexiconEntry(data.entry, (GuiScreen) gui);
                    newGui.page = data.page;
                    Minecraft.getMinecraft().displayGuiScreen(newGui);
                }
            }

            if(accountForContainer)
            {
                ItemStack containerStack = stack.getItem().getContainerItem(stack);
                if(containerStack != null && containerStack.getItem() != null)
                    tooltipContainerStack = containerStack;
            }
        }

        GL11.glDisable(GL11.GL_LIGHTING);
    }

}
