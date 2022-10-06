package pokefenn.totemic.data;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.RegistryObject;
import pokefenn.totemic.init.ModBlocks;

public final class TotemicLootTableProvider extends LootTableProvider {
    public TotemicLootTableProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> getTables() { //Yo dawg
        return ImmutableList.of(
                Pair.of(TotemicBlockLoot::new, LootContextParamSets.BLOCK));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
        map.forEach((name, table) -> LootTables.validate(validationtracker, name, table));
    }

    private static class TotemicBlockLoot extends BlockLoot {
        @Override
        protected void addTables() {
            dropSelf(ModBlocks.cedar_log.get());
            dropSelf(ModBlocks.stripped_cedar_log.get());
            dropSelf(ModBlocks.cedar_wood.get());
            dropSelf(ModBlocks.drum.get());
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ModBlocks.REGISTER.getEntries().stream().map(RegistryObject::get).toList();
        }
    }
}
