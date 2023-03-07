package pokefenn.totemic.ceremony;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TurtleEggBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.util.MiscUtil;

public enum AnimalGrowthCeremony implements CeremonyInstance {
    INSTANCE;

    private static final int RADIUS = 8;
    private static final int TURTLE_HATCH_RADIUS = 6;

    @Override
    public void effect(Level level, BlockPos pos, CeremonyEffectContext context) {
        if(context.getTime() % 20 == 0) {
            var aabb = TotemicEntityUtil.getAABBAround(pos, RADIUS);

            //Animal growth
            level.getEntitiesOfClass(Animal.class, aabb, Animal::isBaby)
            .forEach(animal -> {
                if(level.random.nextInt(4) == 0) {
                    if(!level.isClientSide)
                        growAnimal(level, animal);
                    else {
                        var animalBB = animal.getBoundingBox();
                        spawnParticles(level, animal.position().add(0, animalBB.getYsize() * 0.5, 0), animalBB.getXsize() * 0.5, animalBB.getYsize() * 0.5, animalBB.getZsize() * 0.5);
                    }
                }
            });

            //Chicken egg hatching
            if(!level.isClientSide) {
                level.getEntities(EntityType.ITEM, aabb, e -> e.getItem().is(Items.EGG))
                .forEach(egg -> {
                    if(level.random.nextInt(4) == 0)
                        hatchChickenEgg(level, egg);
                });
            }

            //Turtle egg hatching
            TotemicAPI.get().ceremony().forEachBlockIn(level, TotemicEntityUtil.getBoundingBoxAround(pos, TURTLE_HATCH_RADIUS),
            (p, state) -> {
                if(state.is(Blocks.TURTLE_EGG) && TurtleEggBlock.onSand(level, p)) {
                    if(!level.isClientSide) {
                        if(level.random.nextInt(45) == 0) //about once per ceremony usage
                            hatchTurtleEgg(level, p, state);
                    }
                    else {
                        if(level.random.nextInt(4) == 0)
                            spawnParticles(level, Vec3.atBottomCenterOf(p).add(0, 0.25, 0), 0.5, 0.5, 0.5);
                    }
                }
            });
        }
    }

    private static void growAnimal(Level level, Animal animal) {
        animal.ageUp(level.random.nextInt(60));
        //the argument to ageUp is given in seconds,
        //this amounts to aging the animal up by about 337.5 s (out of 1200 s) each time the ceremony is used
    }

    private static void hatchChickenEgg(Level level, ItemEntity egg) {
        MiscUtil.spawnServerParticles(ParticleTypes.HAPPY_VILLAGER, level, egg.position(), 10, new Vec3(0.5, 0.5, 0.5), 1.0);
        var chicken = EntityType.CHICKEN.create(level);
        chicken.setPos(egg.position());
        chicken.setAge(AgeableMob.BABY_START_AGE);
        level.addFreshEntity(chicken);
        MiscUtil.shrinkItemEntity(egg);
    }

    private static void hatchTurtleEgg(Level level, BlockPos pos, BlockState state) {
        int age = state.getValue(TurtleEggBlock.HATCH);
        if(age < 2) {
            level.playSound(null, pos, SoundEvents.TURTLE_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat() * 0.2F);
            level.setBlock(pos, state.setValue(TurtleEggBlock.HATCH, age + 1), 2);
        }
        else {
            level.playSound(null, pos, SoundEvents.TURTLE_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat() * 0.2F);
            level.removeBlock(pos, false);

            for(int j = 0; j < state.getValue(TurtleEggBlock.EGGS); ++j) {
                level.levelEvent(2001, pos, Block.getId(state));
                Turtle turtle = EntityType.TURTLE.create(level);
                turtle.setAge(AgeableMob.BABY_START_AGE);
                turtle.setHomePos(pos);
                turtle.moveTo(pos.getX() + 0.3D + j * 0.2D, pos.getY(), pos.getZ() + 0.3D, 0.0F, 0.0F);
                level.addFreshEntity(turtle);
            }
        }
    }

    private static void spawnParticles(Level level, Vec3 pos, double xSpread, double ySpread, double zSpread) {
        var rand = level.random;
        for(int i = 0; i < 10; i++) {
            var vec = pos.add(rand.nextGaussian() * xSpread, rand.nextGaussian() * ySpread, rand.nextGaussian() * zSpread);
            level.addParticle(ParticleTypes.HAPPY_VILLAGER, vec.x, vec.y, vec.z, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public int getEffectTime() {
        return 45 * 20;
    }
}
