package pokefenn.totemic.neoforge.datagen.neoforge;

import java.util.concurrent.CompletableFuture;

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

public class TotemicNeoForgeRecipeProvider extends RecipeProvider {
    public TotemicNeoForgeRecipeProvider(PackOutput pOutput, CompletableFuture<Provider> registries) {
        super(pOutput, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput rc) {
        var totempedia = PatchouliAPI.get().getBookStack(Totemic.resloc("totempedia"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, totempedia)
                .pattern("WPW")
                .pattern("WPW")
                .pattern("WPW")
                .define('P', Items.PAPER)
                .define('W', ItemTags.LOGS_THAT_BURN)
                .unlockedBy("has_paper", has(Items.PAPER))
                .unlockedBy("has_totem_knife", has(ModItems.totem_whittling_knife.get()))
                .save(rc.withConditions(new ModLoadedCondition(PatchouliAPI.MOD_ID)), "totemic:totempedia");
    }
}
