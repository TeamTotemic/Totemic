package pokefenn.totemic.data;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
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
                .unlockedBy("has_totemic_staff", has(ModItems.totemic_staff.get())) //TODO
                .save(rc);
        //ShapedRecipeBuilder.shaped(ModItems.rattle.get()) TODO
        ShapedRecipeBuilder.shaped(ModItems.totem_whittling_knife.get())
                .pattern("  I")
                .pattern(" SF")
                .pattern("S  ")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('S', Tags.Items.RODS_WOODEN)
                .define('F', Items.FLINT)
                .unlockedBy("has_totemic_staff", has(ModItems.totemic_staff.get())) //TODO
                .save(rc);
        ShapedRecipeBuilder.shaped(ModItems.totemic_staff.get())
                .pattern(" LS")
                .pattern(" S ")
                .pattern("S L")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('L', ItemTags.LEAVES)
                .unlockedBy("has_sticks", has(Tags.Items.RODS_WOODEN)) //TODO
                .save(rc);
        ShapedRecipeBuilder.shaped(ModBlocks.drum.get())
                .pattern("EEE")
                .pattern("LWL")
                .pattern("WLW")
                .define('E', Tags.Items.LEATHER)
                .define('L', ItemTags.LOGS_THAT_BURN)
                .define('W', ItemTags.WOOL)
                .unlockedBy("has_totemic_staff", has(ModItems.totemic_staff.get())) //TODO
                .save(rc);
    }
}
