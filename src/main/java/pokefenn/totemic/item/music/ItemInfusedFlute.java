package pokefenn.totemic.item.music;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pokefenn.totemic.api.TotemicEntityUtil;

public class ItemInfusedFlute extends ItemFlute {
    //Entities that have been tempted by the infused flute get stored in this weak set
    //so as not to add the same AI task multiple times
    private final Set<Entity> temptedEntities = Collections.newSetFromMap(new WeakHashMap<>());

    public ItemInfusedFlute(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if(!world.isClientSide && !player.isShiftKeyDown())
            temptEntities(world, player.getX(), player.getY(), player.getZ());

        return super.use(world, player, hand);
    }

    private void temptEntities(World world, double x, double y, double z) {
        TotemicEntityUtil.getEntitiesInRange(MobEntity.class, world, new BlockPos(x, y, z), 2, 2,
                entity -> ((entity instanceof AnimalEntity && entity.getNavigation() instanceof GroundPathNavigator) || entity instanceof VillagerEntity)
                            && !temptedEntities.contains(entity))
            .forEach(entity -> {
                double speed = (entity instanceof AnimalEntity) ? 1 : 0.5;
                entity.goalSelector.addGoal(5, new TemptGoal((CreatureEntity) entity, speed, Ingredient.of(this), false));

                temptedEntities.add(entity);
            });
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
