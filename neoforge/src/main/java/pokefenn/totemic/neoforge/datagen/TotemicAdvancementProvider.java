package pokefenn.totemic.neoforge.datagen;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements.Strategy;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.recipes.RecipeBuilder;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModItems;

public class TotemicAdvancementProvider extends AdvancementProvider {
    public TotemicAdvancementProvider(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, registries, List.of(TotemicAdvancementProvider::generate));
    }

    private static void generate(Provider registries, Consumer<AdvancementHolder> saver) {
        // Advacements for unlocking Totempedia Ceremony entries
        var windChimeUnlocked = new Advancement.Builder()
                .requirements(Strategy.OR)
                .addCriterion("performed_fertility", TotemicRecipeProvider.performed(ModContent.fertility.get()))
                .addCriterion("has_wind_chime", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.wind_chime.get()))
                .addCriterion("has_wind_chime_recipe", RecipeUnlockedTrigger.unlocked(RecipeBuilder.getDefaultRecipeId(ModItems.wind_chime.get().getDefaultInstance())))
                .addCriterion("has_rattle", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.rattle.get()))
                .addCriterion("has_eagle_bone_whistle", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.eagle_bone_whistle.get()))
                .save(saver, Totemic.resloc("totempedia/wind_chime_unlocked"));

        var rattleUnlocked = new Advancement.Builder()
                .parent(windChimeUnlocked)
                .requirements(Strategy.OR)
                .addCriterion("performed_buffalo_dance", TotemicRecipeProvider.performed(ModContent.buffalo_dance.get()))
                .addCriterion("has_rattle", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.rattle.get()))
                .addCriterion("has_rattle_recipe", RecipeUnlockedTrigger.unlocked(RecipeBuilder.getDefaultRecipeId(ModItems.rattle.get().getDefaultInstance())))
                .addCriterion("has_eagle_bone_whistle", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.eagle_bone_whistle.get()))
                .save(saver, Totemic.resloc("totempedia/rattle_unlocked"));

        new Advancement.Builder()
                .parent(rattleUnlocked)
                .requirements(Strategy.OR)
                .addCriterion("performed_eagle_dance", TotemicRecipeProvider.performed(ModContent.eagle_dance.get()))
                .addCriterion("has_eagle_bone_whistle", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.eagle_bone_whistle.get()))
                .addCriterion("has_eagle_bone_whistle_recipe", RecipeUnlockedTrigger.unlocked(RecipeBuilder.getDefaultRecipeId(ModItems.eagle_bone_whistle.get().getDefaultInstance())))
                .save(saver, Totemic.resloc("totempedia/eagle_bone_whistle_unlocked"));
    }
}
