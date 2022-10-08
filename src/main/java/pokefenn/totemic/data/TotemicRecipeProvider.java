package pokefenn.totemic.data;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.Tags;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModItems;

public final class TotemicRecipeProvider extends RecipeProvider {
    public TotemicRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> rc) {
        ShapedRecipeBuilder.shaped(ModItems.flute.get())
                .pattern(" LS")
                .pattern(" S ")
                .pattern("S  ")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('L', ItemTags.LEAVES)
                .unlockedBy("has_totemic_staff", has(ModItems.totemic_staff.get())) //TODO: Once we have a Totempedia item, it should become the item to unlock the recipes
                .save(rc);
        ShapedRecipeBuilder.shaped(ModItems.rattle.get())
                .pattern(" WW")
                .pattern(" BW")
                .pattern("S  ")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('W', ItemTags.LOGS_THAT_BURN)
                .define('B', ModItems.buffalo_tooth.get())
                .unlockedBy("has_totemic_staff", has(ModItems.totemic_staff.get()))
                .save(rc);
        ShapedRecipeBuilder.shaped(ModItems.totem_whittling_knife.get())
                .pattern("  I")
                .pattern(" SF")
                .pattern("S  ")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('S', Tags.Items.RODS_WOODEN)
                .define('F', Items.FLINT)
                .unlockedBy("has_totemic_staff", has(ModItems.totemic_staff.get()))
                .save(rc);
        ShapedRecipeBuilder.shaped(ModItems.totemic_staff.get())
                .pattern(" LS")
                .pattern(" S ")
                .pattern("S L")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('L', ItemTags.LEAVES)
                .unlockedBy("has_sticks", has(Tags.Items.RODS_WOODEN))
                .save(rc);
        ShapedRecipeBuilder.shaped(ModBlocks.drum.get())
                .pattern("EEE")
                .pattern("LWL")
                .pattern("WLW")
                .define('E', Tags.Items.LEATHER)
                .define('L', ItemTags.LOGS_THAT_BURN)
                .define('W', ItemTags.WOOL)
                .unlockedBy("has_totemic_staff", has(ModItems.totemic_staff.get()))
                .save(rc);

        simpleCookingRecipe(rc, "smelting", RecipeSerializer.SMELTING_RECIPE, 200, ModItems.buffalo_meat.get(), ModItems.cooked_buffalo_meat.get(), 0.35F);
        simpleCookingRecipe(rc, "smoking", RecipeSerializer.SMOKING_RECIPE, 100, ModItems.buffalo_meat.get(), ModItems.cooked_buffalo_meat.get(), 0.35F);
        simpleCookingRecipe(rc, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, 600, ModItems.buffalo_meat.get(), ModItems.cooked_buffalo_meat.get(), 0.35F);
    }
}
