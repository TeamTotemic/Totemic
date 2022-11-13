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
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModEntityTypes;
import pokefenn.totemic.init.ModItems;

public final class TotemicLootTableProvider extends LootTableProvider {
    public TotemicLootTableProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> getTables() { //Yo dawg
        return ImmutableList.of(
                Pair.of(TotemicBlockLoot::new, LootContextParamSets.BLOCK),
                Pair.of(TotemicEntityLoot::new, LootContextParamSets.ENTITY));
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
            dropSelf(ModBlocks.stripped_cedar_wood.get());
            add(ModBlocks.cedar_leaves.get(), b -> createLeavesDrops(b, ModBlocks.cedar_sapling.get(), /*NORMAL_LEAVES_SAPLING_CHANCES*/new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F}));
            dropSelf(ModBlocks.cedar_sapling.get());
            dropSelf(ModBlocks.drum.get());
            dropSelf(ModBlocks.wind_chime.get());
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ModBlocks.REGISTER.getEntries().stream().map(RegistryObject::get)::iterator;
        }
    }

    private static class TotemicEntityLoot extends EntityLoot {
        @Override
        protected void addTables() {
            add(ModEntityTypes.buffalo.get(),
                    LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(ModItems.buffalo_hide.get())
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 4.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(ModItems.buffalo_tooth.get())
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 2.0F/3.0F)))))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(ModItems.buffalo_meat.get())
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F)))
                                    .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 2.0F)))))
                    );
            add(ModEntityTypes.bald_eagle.get(),
                    LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(ModItems.eagle_bone.get())
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 2.0F/3.0F)))))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(ModItems.eagle_feather.get())
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
                    );
            add(ModEntityTypes.baykok.get(),
                    LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(ModItems.baykok_bow.get())))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(Items.BONE)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(Items.ROTTEN_FLESH)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(Items.ARROW)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 10.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(AlternativesEntry.alternatives(
                                    LootItem.lootTableItem(Items.WITHER_SKELETON_SKULL).when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.25F, 0.05F)),
                                    LootItem.lootTableItem(Items.SKELETON_SKULL))))
                    );
        }

        @Override
        protected Iterable<EntityType<?>> getKnownEntities() {
            return ModEntityTypes.REGISTER.getEntries().stream().<EntityType<?>>map(RegistryObject::get)::iterator; //Compiler has difficulty with type inference here
        }
    }

    @Override
    public String getName() {
        return "Totemic Loot Tables";
    }
}
