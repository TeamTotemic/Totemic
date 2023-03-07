package pokefenn.totemic.ceremony;

import java.util.Optional;
import java.util.function.Predicate;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.util.MiscUtil;

public enum FertilityCeremony implements CeremonyInstance {
    INSTANCE;

    private static final int RADIUS = 8;
    private static final int SAPLING_TRANSFORM_RADIUS = 6;

    @Override
    public void effect(Level level, BlockPos pos, CeremonyEffectContext context) {
        if(level.isClientSide)
            return;

        if(context.getTime() % 20 == 0) {
            transformSaplings(level, pos);
            breedAnimalsAndVillagers(level, pos, context);
        }
    }

    private void transformSaplings(Level level, BlockPos pos) {
        BlockPos.betweenClosedStream(TotemicEntityUtil.getBoundingBoxAround(pos, SAPLING_TRANSFORM_RADIUS))
                .filter(p -> {
                    var state = level.getBlockState(p);
                    return state.is(BlockTags.SAPLINGS) && state.getBlock() != ModBlocks.cedar_sapling.get();
                })
                .findAny()
                .ifPresent(p -> {
                    level.setBlock(p, ModBlocks.cedar_sapling.get().defaultBlockState(), Block.UPDATE_ALL);
                    MiscUtil.spawnServerParticles(ParticleTypes.HAPPY_VILLAGER, level, Vec3.atCenterOf(p), 10, new Vec3(0.5, 0.5, 0.5), 0);
                });
    }

    private void breedAnimalsAndVillagers(Level level, BlockPos pos, CeremonyEffectContext context) {
        var aabb = TotemicEntityUtil.getAABBAround(pos, RADIUS);
        for(var animal: level.getEntitiesOfClass(Animal.class, aabb, a -> a.getAge() == 0 && !a.isInLove())) {
            var itemE = findItemEntity(level, pos, animal::isFood);
            if(itemE.isPresent()) {
                if(level.random.nextInt(3) < 2)
                    MiscUtil.shrinkItemEntity(itemE.get());
                animal.setInLove(context.getInitiatingPlayer().orElse(null));
                return; //Limit to one animal or villager per second
            }
        }

        //TODO: Villager breeding
    }

    private static Optional<ItemEntity> findItemEntity(Level level, BlockPos pos, Predicate<ItemStack> predicate) {
        var list = level.getEntities(EntityType.ITEM, TotemicEntityUtil.getAABBAround(pos, RADIUS), e -> predicate.test(e.getItem()));
        return !list.isEmpty() ? Optional.of(list.get(0)) : Optional.empty();
    }

    @Override
    public int getEffectTime() {
        return 20 * 20;
    }
}
