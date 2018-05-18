/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Totemic Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Jan 19, 2014, 4:58:19 PM (GMT)]
 */
package pokefenn.totemic.totempedia.page;

import java.util.Collections;
import java.util.Objects;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pokefenn.totemic.api.internal.IGuiLexiconEntry;
import pokefenn.totemic.api.lexicon.LexiconEntry;
import pokefenn.totemic.api.lexicon.LexiconRecipeMappings;
import pokefenn.totemic.client.TotemicRenderHelper;
import pokefenn.totemic.lib.Resources;

public class PageCraftingRecipe extends PageRecipe
{
    private static final ResourceLocation craftingOverlay = new ResourceLocation(Resources.GUI_CRAFTING_OVERLAY);

    private final IRecipe recipe;
    private final int recipeWidth;
    private final boolean shapelessRecipe;

    private int ticksElapsed = 0;
    private int recipeIndex = 0;

    private static final IRecipe DUMMY_RECIPE = new ShapelessRecipes("", ItemStack.EMPTY, NonNullList.create());

    private static IRecipe getRecipeOrDummy(ResourceLocation name)
    {
        IRecipe recipe = CraftingManager.getRecipe(name);
        return (recipe != null) ? recipe : DUMMY_RECIPE;
    }

    public PageCraftingRecipe(String unlocalizedName, IRecipe recipe)
    {
        super(unlocalizedName);
        this.recipe = Objects.requireNonNull(recipe);

        if(recipe instanceof IShapedRecipe)
        {
            recipeWidth = ((IShapedRecipe) recipe).getRecipeWidth();
            shapelessRecipe = false;
        }
        else
        {
            recipeWidth = 3;
            shapelessRecipe = true;
        }
    }

    public PageCraftingRecipe(String unlocalizedName, String recipe)
    {
        this(unlocalizedName, getRecipeOrDummy(new ResourceLocation(recipe)));
    }

    @Override
    public void onPageAdded(LexiconEntry entry, int index)
    {
        if(recipe != DUMMY_RECIPE)
            LexiconRecipeMappings.map(recipe.getRecipeOutput(), entry, index);
        else
            unlocalizedName = "totemic.gui.lexicon.recipeNotAvailable";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderRecipe(IGuiLexiconEntry gui, int mx, int my)
    {
        renderCraftingRecipe(gui);

        TextureManager render = Minecraft.getMinecraft().renderEngine;
        render.bindTexture(craftingOverlay);

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1F, 1F, 1F, 1F);
        ((GuiScreen) gui).drawTexturedModalRect(gui.getLeft(), gui.getTop(), 0, 0, gui.getWidth(), gui.getHeight());

        if(shapelessRecipe)
        {
            int iconX = gui.getLeft() + 115;
            int iconY = gui.getTop() + 12;

            ((GuiScreen) gui).drawTexturedModalRect(iconX, iconY, 240, 0, 16, 16);

            if(mx >= iconX && my >= iconY && mx < iconX + 16 && my < iconY + 16)
                TotemicRenderHelper.renderTooltip(mx, my, Collections.singletonList(I18n.format("totemicmisc.shapeless")));
        }

        GlStateManager.disableBlend();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateScreen()
    {
        if(ticksElapsed % 30 == 0)
        {
            recipeIndex++;
        }
        ticksElapsed++;
    }

    @SideOnly(Side.CLIENT)
    private void renderCraftingRecipe(IGuiLexiconEntry gui)
    {
        int x = 0;
        int y = 0;
        for(Ingredient ingredient: recipe.getIngredients())
        {
            if(ingredient != Ingredient.EMPTY)
            {
                ItemStack[] stacks = ingredient.getMatchingStacks();
                renderItemAtGridPos(gui, 1 + x, 1 + y, stacks[recipeIndex % stacks.length], true);
            }

            x++;
            if(x >= recipeWidth)
            {
                x = 0;
                y++;
            }
        }

        renderItemAtGridPos(gui, 2, 0, recipe.getRecipeOutput(), false);
    }
}
