package pokefenn.totemic.neoforge.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
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
    public static final BlockFamily CEDAR_FAMILY = new BlockFamily.Builder(ModBlocks.cedar_planks.get())
            .button(ModBlocks.cedar_button.get())
            .fence(ModBlocks.cedar_fence.get())
            .fenceGate(ModBlocks.cedar_fence_gate.get())
            .pressurePlate(ModBlocks.cedar_pressure_plate.get())
            .sign(ModBlocks.cedar_sign.get(), ModBlocks.cedar_wall_sign.get())
            .slab(ModBlocks.cedar_slab.get())
            .stairs(ModBlocks.cedar_stairs.get())
            .door(ModBlocks.cedar_door.get())
            .trapdoor(ModBlocks.cedar_trapdoor.get())
            .recipeGroupPrefix("totemic:wooden")
            .recipeUnlockedBy("has_planks")
            .getFamily();

    public TotemicRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.flute.get())
                .pattern(" LS")
                .pattern(" S ")
                .pattern("S  ")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('L', ItemTags.LEAVES)
                .unlockedBy("has_totem_knife", has(ModItems.totem_whittling_knife.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.jingle_dress.get())
                .pattern(" L ")
                .pattern("BHB")
                .pattern("LBL")
                .define('L', ModBlocks.cedar_leaves.get())
                .define('B', ModItems.iron_bells.get())
                .define('H', Tags.Items.LEATHERS)
                .unlockedBy("performed_fertility", performed(ModContent.fertility.get()))
                .unlockedBy("has_cedar_leaves", has(ModBlocks.cedar_leaves.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.iron_bells.get())
                .pattern(" N ")
                .pattern("NNN")
                .pattern(" N ")
                .define('N', Tags.Items.NUGGETS_IRON)
                .unlockedBy("has_jingle_dress_recipe", RecipeUnlockedTrigger.unlocked(RecipeBuilder.getDefaultRecipeId(new ItemStackTemplate(ModItems.jingle_dress.get()))))
                .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.rattle.get())
                .pattern(" WW")
                .pattern(" BW")
                .pattern("S  ")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('W', ItemTags.LOGS_THAT_BURN)
                .define('B', ModItems.buffalo_tooth.get())
                .unlockedBy("performed_buffalo_dance", performed(ModContent.buffalo_dance.get()))
                .unlockedBy("has_buffalo_tooth", has(ModItems.buffalo_tooth.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ModItems.totem_whittling_knife.get())
                .pattern("  I")
                .pattern(" SF")
                .pattern("S  ")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('S', Tags.Items.RODS_WOODEN)
                .define('F', Items.FLINT)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ModItems.totemic_staff.get())
                .pattern(" LS")
                .pattern(" S ")
                .pattern("S L")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('L', ItemTags.LEAVES)
                .unlockedBy("has_totem_knife", has(ModItems.totem_whittling_knife.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModBlocks.drum.get())
                .pattern("EEE")
                .pattern("LWL")
                .pattern("WLW")
                .define('E', Tags.Items.LEATHERS)
                .define('L', ItemTags.LOGS_THAT_BURN)
                .define('W', ItemTags.WOOL)
                .unlockedBy("has_totem_knife", has(ModItems.totem_whittling_knife.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModBlocks.wind_chime.get())
                .pattern("WWW")
                .pattern("S S")
                .pattern("C C")
                .define('W', TotemicItemTags.CEDAR_LOGS)
                .define('S', Tags.Items.STRINGS)
                .define('C', Tags.Items.INGOTS_COPPER)
                .unlockedBy("performed_fertility", performed(ModContent.fertility.get()))
                .unlockedBy("has_cedar_logs", has(TotemicItemTags.CEDAR_LOGS))
                .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.eagle_bone_whistle.get())
                .pattern("S ")
                .pattern("BF")
                .define('S', Tags.Items.STRINGS)
                .define('B', ModItems.eagle_bone.get())
                .define('F', ModItems.eagle_feather.get())
                .unlockedBy("performed_eagle_dance", performed(ModContent.eagle_dance.get()))
                .unlockedBy("has_eagle_bone", has(ModItems.eagle_bone.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.medicine_bag.get())
                .pattern("PST")
                .pattern("HDH")
                .pattern(" H ")
                .define('P', ModBlocks.cedar_planks.get())
                .define('S', Tags.Items.STRINGS)
                .define('T', ModItems.buffalo_tooth.get())
                .define('H', ModItems.buffalo_hide.get())
                .define('D', Tags.Items.GEMS_DIAMOND)
                .unlockedBy("performed_buffalo_dance", performed(ModContent.buffalo_dance.get()))
                .unlockedBy("has_buffalo_hide", has(ModItems.buffalo_hide.get()))
                .unlockedBy("has_buffalo_tooth", has(ModItems.buffalo_tooth.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, Items.LEATHER)
                .requires(ModItems.buffalo_hide.get())
                .unlockedBy("performed_buffalo_dance", performed(ModContent.buffalo_dance.get()))
                .unlockedBy("has_buffalo_hide", has(ModItems.buffalo_hide.get()))
                .save(output, "totemic:leather_from_hide");
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.BUILDING_BLOCKS, ModBlocks.cedar_planks.get(), 4)
                .requires(TotemicItemTags.CEDAR_LOGS)
                .unlockedBy("performed_fertility", performed(ModContent.fertility.get()))
                .unlockedBy("has_cedar_logs", has(TotemicItemTags.CEDAR_LOGS))
                .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, ModBlocks.totem_torch.get(), 2)
                .pattern("STS")
                .pattern("SWS")
                .pattern(" S ")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('W', ItemTags.LOGS_THAT_BURN)
                .define('T', Items.TORCH)
                .unlockedBy("has_torch", has(Items.TORCH))
                .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, ModBlocks.tipi.get())
                .pattern(" S ")
                .pattern("SWS")
                .pattern("W W")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('W', ItemTags.WOOL)
                .group("totemic:tipi")
                .unlockedBy("has_wool", has(ItemTags.WOOL))
                .save(output, "totemic:tipi_from_wool");
        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, ModBlocks.tipi.get())
                .pattern(" S ")
                .pattern("SWS")
                .pattern("W W")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('W', ModItems.buffalo_hide.get())
                .group("totemic:tipi")
                .unlockedBy("performed_buffalo_dance", performed(ModContent.buffalo_dance.get()))
                .unlockedBy("has_buffalo_hide", has(ModItems.buffalo_hide.get()))
                .save(output, "totemic:tipi_from_hide");

        generateRecipes(CEDAR_FAMILY, FeatureFlags.DEFAULT_FLAGS);

        hangingSign(ModItems.cedar_hanging_sign.get(), ModBlocks.stripped_cedar_log.get());
        woodFromLogs(ModBlocks.cedar_wood.get(), ModBlocks.cedar_log.get());
        woodFromLogs(ModBlocks.stripped_cedar_wood.get(), ModBlocks.stripped_cedar_log.get());

        simpleCookingRecipe("smelting", SmeltingRecipe::new, 200, ModItems.buffalo_meat.get(), ModItems.cooked_buffalo_meat.get(), 0.35F);
        simpleCookingRecipe("smoking", SmokingRecipe::new, 100, ModItems.buffalo_meat.get(), ModItems.cooked_buffalo_meat.get(), 0.35F);
        simpleCookingRecipe("campfire_cooking", CampfireCookingRecipe::new, 600, ModItems.buffalo_meat.get(), ModItems.cooked_buffalo_meat.get(), 0.35F);
    }

    public static Criterion<CeremonyTrigger.TriggerInstance> performed(Ceremony ceremony) {
        return CeremonyTrigger.TriggerInstance.performedCeremony(ceremony);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(Provider registries, RecipeOutput output) {
            return new TotemicRecipeProvider(registries, output);
        }

        @Override
        public String getName() {
            return "Totemic recipes";
        }
    }
}
