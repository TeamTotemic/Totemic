package pokefenn.totemic.neoforge.datagen.neoforge;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.loot.CanItemPerformAbility;
import pokefenn.totemic.init.ModBlocks;

public class TotemicNeoForgeLootTableProvider extends LootTableProvider {
    public TotemicNeoForgeLootTableProvider(PackOutput pOutput, CompletableFuture<Provider> registries) {
        super(pOutput, Set.of(), List.of(
                new SubProviderEntry(TotemicNeoForgeBlockLoot::new, LootContextParamSets.BLOCK)),
                registries);
    }

    private static class TotemicNeoForgeBlockLoot extends BlockLootSubProvider {
        public TotemicNeoForgeBlockLoot(HolderLookup.Provider registries) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
        }

        @Override
        protected void generate() {
            // Replace "minecraft:match_tool" condition with "neoforge:can_item_perform_ability" for Cedar Leaves
            HAS_SHEARS = CanItemPerformAbility.canItemPerformAbility(ItemAbilities.SHEARS_DIG);

            add(ModBlocks.cedar_leaves.get(), b -> createLeavesDrops(b, ModBlocks.cedar_sapling.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return List.of(ModBlocks.cedar_leaves.get());
        }
    }
}
