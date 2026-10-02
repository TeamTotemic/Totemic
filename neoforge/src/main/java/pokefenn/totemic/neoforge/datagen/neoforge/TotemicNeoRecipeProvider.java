package pokefenn.totemic.neoforge.datagen.neoforge;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.init.ModItems;
import vazkii.patchouli.api.PatchouliAPI;

public class TotemicNeoRecipeProvider extends RecipeProvider {
    public TotemicNeoRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        var totempedia = PatchouliAPI.get().getBookStackTemplate(Totemic.resloc("totempedia"));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, totempedia)
                .pattern("WPW")
                .pattern("WPW")
                .pattern("WPW")
                .define('P', Items.PAPER)
                .define('W', ItemTags.LOGS_THAT_BURN)
                .unlockedBy("has_paper", has(Items.PAPER))
                .unlockedBy("has_totem_knife", has(ModItems.totem_whittling_knife.get()))
                .save(output.withConditions(new ModLoadedCondition(PatchouliAPI.MOD_ID)), "totemic:totempedia");
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(Provider registries, RecipeOutput output) {
            return new TotemicNeoRecipeProvider(registries, output);
        }

        @Override
        public String getName() {
            return "Totemic Neo-specific recipes";
        }
    }
}
