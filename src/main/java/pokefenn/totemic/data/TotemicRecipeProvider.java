package pokefenn.totemic.data;

import java.util.function.Consumer;

import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.Tags;
import pokefenn.totemic.advancements.criterion.CeremonyTrigger;
import pokefenn.totemic.api.TotemicItemTags;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModItems;

public final class TotemicRecipeProvider extends RecipeProvider {
    public TotemicRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> rc) {
        ShapedRecipeBuilder.shaped(ModItems.totempedia.get())
                .pattern("WPW")
                .pattern("WPW")
                .pattern("WPW")
                .define('W', ItemTags.LOGS_THAT_BURN)
                .define('P', Items.PAPER)
                .unlockedBy("has_paper", has(Items.PAPER))
                .save(rc);
        ShapedRecipeBuilder.shaped(ModItems.flute.get())
                .pattern(" LS")
                .pattern(" S ")
                .pattern("S  ")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('L', ItemTags.LEAVES)
                .unlockedBy("has_totempedia", has(ModItems.totempedia.get()))
                .save(rc);
        ShapedRecipeBuilder.shaped(ModItems.jingle_dress.get())
                .pattern(" L ")
                .pattern("BHB")
                .pattern("LBL")
                .define('L', ModBlocks.cedar_leaves.get())
                .define('B', ModItems.iron_bells.get())
                .define('H', Tags.Items.LEATHER)
                .unlockedBy("performed_fertility", performed(ModContent.fertility))
                .unlockedBy("has_cedar_leaves", has(ModBlocks.cedar_leaves.get()))
                .save(rc);
        ShapedRecipeBuilder.shaped(ModItems.iron_bells.get())
                .pattern(" N ")
                .pattern("NNN")
                .pattern(" N ")
                .define('N', Tags.Items.NUGGETS_IRON)
                .unlockedBy("has_jingle_dress_recipe", RecipeUnlockedTrigger.unlocked(ModItems.jingle_dress.getId()))
                .save(rc);
        ShapedRecipeBuilder.shaped(ModItems.rattle.get())
                .pattern(" WW")
                .pattern(" BW")
                .pattern("S  ")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('W', ItemTags.LOGS_THAT_BURN)
                .define('B', ModItems.buffalo_tooth.get())
                .unlockedBy("performed_buffalo_dance", performed(ModContent.buffalo_dance))
                .unlockedBy("has_buffalo_tooth", has(ModItems.buffalo_tooth.get()))
                .save(rc);
        ShapedRecipeBuilder.shaped(ModItems.totem_whittling_knife.get())
                .pattern("  I")
                .pattern(" SF")
                .pattern("S  ")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('S', Tags.Items.RODS_WOODEN)
                .define('F', Items.FLINT)
                .unlockedBy("has_totempedia", has(ModItems.totempedia.get()))
                .save(rc);
        ShapedRecipeBuilder.shaped(ModItems.totemic_staff.get())
                .pattern(" LS")
                .pattern(" S ")
                .pattern("S L")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('L', ItemTags.LEAVES)
                .unlockedBy("has_totempedia", has(ModItems.totempedia.get()))
                .save(rc);
        ShapedRecipeBuilder.shaped(ModBlocks.drum.get())
                .pattern("EEE")
                .pattern("LWL")
                .pattern("WLW")
                .define('E', Tags.Items.LEATHER)
                .define('L', ItemTags.LOGS_THAT_BURN)
                .define('W', ItemTags.WOOL)
                .unlockedBy("has_totempedia", has(ModItems.totempedia.get()))
                .save(rc);
        ShapedRecipeBuilder.shaped(ModBlocks.wind_chime.get())
                .pattern("WWW")
                .pattern("S S")
                .pattern("C C")
                .define('W', TotemicItemTags.CEDAR_LOGS)
                .define('S', Tags.Items.STRING)
                .define('C', Tags.Items.INGOTS_COPPER)
                .unlockedBy("performed_fertility", performed(ModContent.fertility))
                .unlockedBy("has_cedar_logs", has(ModBlocks.cedar_log.get()))
                .save(rc);
        ShapedRecipeBuilder.shaped(ModItems.eagle_bone_whistle.get())
                .pattern("S ")
                .pattern("BF")
                .define('S', Tags.Items.STRING)
                .define('B', ModItems.eagle_bone.get())
                .define('F', ModItems.eagle_feather.get())
                .unlockedBy("performed_eagle_dance", performed(ModContent.eagle_dance))
                .unlockedBy("has_eagle_bone", has(ModItems.eagle_bone.get()))
                .save(rc);
        ShapedRecipeBuilder.shaped(ModItems.medicine_bag.get())
                .pattern("PST")
                .pattern("HDH")
                .pattern(" H ")
                .define('P', ModBlocks.cedar_planks.get())
                .define('S', Tags.Items.STRING)
                .define('T', ModItems.buffalo_tooth.get())
                .define('H', ModItems.buffalo_hide.get())
                .define('D', Tags.Items.GEMS_DIAMOND)
                .unlockedBy("performed_buffalo_dance", performed(ModContent.buffalo_dance))
                .unlockedBy("has_buffalo_hide", has(ModItems.buffalo_hide.get()))
                .unlockedBy("has_buffalo_tooth", has(ModItems.buffalo_tooth.get()))
                .save(rc);
        ShapelessRecipeBuilder.shapeless(Items.LEATHER)
                .requires(ModItems.buffalo_hide.get())
                .unlockedBy("performed_buffalo_dance", performed(ModContent.buffalo_dance))
                .unlockedBy("has_buffalo_hide", has(ModItems.buffalo_hide.get()))
                .save(rc, "totemic:leather_from_hide");

        generateRecipes(rc, ModBlocks.getCedarBlockFamily());

        simpleCookingRecipe(rc, "smelting", RecipeSerializer.SMELTING_RECIPE, 200, ModItems.buffalo_meat.get(), ModItems.cooked_buffalo_meat.get(), 0.35F);
        simpleCookingRecipe(rc, "smoking", RecipeSerializer.SMOKING_RECIPE, 100, ModItems.buffalo_meat.get(), ModItems.cooked_buffalo_meat.get(), 0.35F);
        simpleCookingRecipe(rc, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, 600, ModItems.buffalo_meat.get(), ModItems.cooked_buffalo_meat.get(), 0.35F);
    }

    protected static CeremonyTrigger.TriggerInstance performed(Ceremony ceremony) {
        return CeremonyTrigger.TriggerInstance.performedCeremony(ceremony);
    }

    @Override
    public String getName() {
        return "Totemic Recipes";
    }
}
