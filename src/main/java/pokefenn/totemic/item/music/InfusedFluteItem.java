package pokefenn.totemic.item.music;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.TotemicEntityUtil;

public class InfusedFluteItem extends FluteItem {
    //Entities that have been tempted by the infused flute get stored in this weak set
    //so as not to add the same AI task multiple times
    private final Set<Entity> temptedEntities = Collections.newSetFromMap(new WeakHashMap<>());

    public InfusedFluteItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if(!world.isClientSide && !player.isShiftKeyDown())
            temptEntities(world, player.getX(), player.getY(), player.getZ());

        return super.use(world, player, hand);
    }

    private void temptEntities(Level world, double x, double y, double z) {
        TotemicEntityUtil.getEntitiesInRange(Mob.class, world, new BlockPos(x, y, z), 2, 2,
                entity -> ((entity instanceof Animal && entity.getNavigation() instanceof GroundPathNavigation) || entity instanceof Villager)
                            && !temptedEntities.contains(entity))
            .forEach(entity -> {
                double speed = (entity instanceof Animal) ? 1 : 0.5;
                entity.goalSelector.addGoal(5, new TemptGoal((PathfinderMob) entity, speed, Ingredient.of(this), false));

                temptedEntities.add(entity);
            });
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(getDescriptionId() + ".tooltip"));
        super.appendHoverText(stack, level, tooltip, flag);
    }

    @Override
    public boolean isFoil(ItemStack stack) { //Makes the item glow
        return true;
    }
}
