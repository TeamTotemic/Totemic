package pokefenn.totemic.ceremony;

import java.util.Optional;
import java.util.function.Predicate;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.util.MiscUtil;

public class FertilityCeremony implements CeremonyInstance {
    private static final int RADIUS = 8;

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
        BlockPos.betweenClosedStream(new AABB(pos).inflate(RADIUS - 1))
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
        var aabb = new AABB(pos).inflate(RADIUS - 1);
        for(var animal: level.getEntitiesOfClass(Animal.class, aabb, a -> a.getAge() == 0 && !a.isInLove())) {
            var itemE = findItemEntity(level, pos, animal::isFood);
            if(itemE.isPresent()) {
                if(level.random.nextInt(3) < 2)
                    MiscUtil.shrinkItemEntity(itemE.get());
                animal.setInLove(context.getInitiatingPlayer());
                return; //Limit to one animal or villager per second
            }
        }

        //TODO: Villager breeding
    }

    private static Optional<ItemEntity> findItemEntity(Level level, BlockPos pos, Predicate<ItemStack> predicate) {
        return TotemicEntityUtil.getEntitiesInRange(ItemEntity.class, level, pos, RADIUS, RADIUS, e -> predicate.test(e.getItem())).findAny();
    }

    @Override
    public int getEffectTime() {
        return 20 * 20;
    }
}
