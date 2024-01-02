package pokefenn.totemic.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.neoforged.neoforge.common.Tags;
import pokefenn.totemic.advancements.criterion.CeremonyTrigger;
import pokefenn.totemic.api.TotemicItemTags;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModItems;

public final class TotemicRecipeProvider extends RecipeProvider {
    public TotemicRecipeProvider(PackOutput pOutput, CompletableFuture<Provider> pLookupProvider) {
        super(pOutput, pLookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput rc) {
        //TODO: Patchouli is not yet available
        //var totempedia = PatchouliAPI.get().getBookStack(Totemic.resloc("totempedia"));
        //var hasTotempedia = inventoryTrigger(ItemPredicate.Builder.item().of(totempedia.getItem()).hasNbt(totempedia.getTag()).build());

        //The Totempedia recipe itself is not being generated
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.flute.get())
                .pattern(" LS")
                .pattern(" S ")
                .pattern("S  ")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('L', ItemTags.LEAVES)
                //.unlockedBy("has_totempedia", hasTotempedia)
                .unlockedBy("has_totem_knife", has(ModItems.totem_whittling_knife))
                .save(rc);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.jingle_dress.get())
                .pattern(" L ")
                .pattern("BHB")
                .pattern("LBL")
                .define('L', ModBlocks.cedar_leaves.get())
                .define('B', ModItems.iron_bells.get())
                .define('H', Tags.Items.LEATHER)
                .unlockedBy("performed_fertility", performed(ModContent.fertility))
                .unlockedBy("has_cedar_leaves", has(ModBlocks.cedar_leaves.get()))
                .save(rc);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.iron_bells.get())
                .pattern(" N ")
                .pattern("NNN")
                .pattern(" N ")
                .define('N', Tags.Items.NUGGETS_IRON)
                .unlockedBy("has_jingle_dress_recipe", RecipeUnlockedTrigger.unlocked(ModItems.jingle_dress.getId()))
                .save(rc);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.rattle.get())
                .pattern(" WW")
                .pattern(" BW")
                .pattern("S  ")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('W', ItemTags.LOGS_THAT_BURN)
                .define('B', ModItems.buffalo_tooth.get())
                .unlockedBy("performed_buffalo_dance", performed(ModContent.buffalo_dance))
                .unlockedBy("has_buffalo_tooth", has(ModItems.buffalo_tooth.get()))
                .save(rc);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.totem_whittling_knife.get())
                .pattern("  I")
                .pattern(" SF")
                .pattern("S  ")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('S', Tags.Items.RODS_WOODEN)
                .define('F', Items.FLINT)
                //.unlockedBy("has_totempedia", hasTotempedia)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(rc);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.totemic_staff.get())
                .pattern(" LS")
                .pattern(" S ")
                .pattern("S L")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('L', ItemTags.LEAVES)
                //.unlockedBy("has_totempedia", hasTotempedia)
                .unlockedBy("has_totem_knife", has(ModItems.totem_whittling_knife))
                .save(rc);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.drum.get())
                .pattern("EEE")
                .pattern("LWL")
                .pattern("WLW")
                .define('E', Tags.Items.LEATHER)
                .define('L', ItemTags.LOGS_THAT_BURN)
                .define('W', ItemTags.WOOL)
                //.unlockedBy("has_totempedia", hasTotempedia)
                .unlockedBy("has_totem_knife", has(ModItems.totem_whittling_knife))
                .save(rc);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.wind_chime.get())
                .pattern("WWW")
                .pattern("S S")
                .pattern("C C")
                .define('W', TotemicItemTags.CEDAR_LOGS)
                .define('S', Tags.Items.STRING)
                .define('C', Tags.Items.INGOTS_COPPER)
                .unlockedBy("performed_fertility", performed(ModContent.fertility))
                .unlockedBy("has_cedar_logs", has(TotemicItemTags.CEDAR_LOGS))
                .save(rc);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.eagle_bone_whistle.get())
                .pattern("S ")
                .pattern("BF")
                .define('S', Tags.Items.STRING)
                .define('B', ModItems.eagle_bone.get())
                .define('F', ModItems.eagle_feather.get())
                .unlockedBy("performed_eagle_dance", performed(ModContent.eagle_dance))
                .unlockedBy("has_eagle_bone", has(ModItems.eagle_bone.get()))
                .save(rc);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.medicine_bag.get())
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
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.LEATHER)
                .requires(ModItems.buffalo_hide.get())
                .unlockedBy("performed_buffalo_dance", performed(ModContent.buffalo_dance))
                .unlockedBy("has_buffalo_hide", has(ModItems.buffalo_hide.get()))
                .save(rc, "totemic:leather_from_hide");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.cedar_planks.get(), 4)
                .requires(TotemicItemTags.CEDAR_LOGS)
                .unlockedBy("performed_fertility", performed(ModContent.fertility))
                .unlockedBy("has_cedar_logs", has(TotemicItemTags.CEDAR_LOGS))
                .save(rc);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.totem_torch.get(), 2)
                .pattern("STS")
                .pattern("SWS")
                .pattern(" S ")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('W', ItemTags.LOGS_THAT_BURN)
                .define('T', Items.TORCH)
                //.unlockedBy("has_totempedia", hasTotempedia)
                .unlockedBy("has_torch", has(Items.TORCH))
                .save(rc);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.tipi.get())
                .pattern(" S ")
                .pattern("SWS")
                .pattern("W W")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('W', ItemTags.WOOL)
                .group("totemic:tipi")
                //.unlockedBy("has_totempedia", hasTotempedia)
                .unlockedBy("has_wool", has(ItemTags.WOOL))
                .save(rc, "totemic:tipi_from_wool");
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.tipi.get())
                .pattern(" S ")
                .pattern("SWS")
                .pattern("W W")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('W', ModItems.buffalo_hide.get())
                .group("totemic:tipi")
                .unlockedBy("performed_buffalo_dance", performed(ModContent.buffalo_dance))
                .unlockedBy("has_buffalo_hide", has(ModItems.buffalo_hide.get()))
                .save(rc, "totemic:tipi_from_hide");

        generateRecipes(rc, ModBlocks.getCedarBlockFamily(), FeatureFlags.DEFAULT_FLAGS);

        simpleCookingRecipe(rc, "smelting", RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, 200, ModItems.buffalo_meat.get(), ModItems.cooked_buffalo_meat.get(), 0.35F);
        simpleCookingRecipe(rc, "smoking", RecipeSerializer.SMOKING_RECIPE, SmokingRecipe::new, 100, ModItems.buffalo_meat.get(), ModItems.cooked_buffalo_meat.get(), 0.35F);
        simpleCookingRecipe(rc, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, CampfireCookingRecipe::new, 600, ModItems.buffalo_meat.get(), ModItems.cooked_buffalo_meat.get(), 0.35F);
    }

    protected static Criterion<CeremonyTrigger.TriggerInstance> performed(Ceremony ceremony) {
        return CeremonyTrigger.TriggerInstance.performedCeremony(ceremony);
    }
}
