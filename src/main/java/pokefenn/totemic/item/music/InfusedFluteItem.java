package pokefenn.totemic.item.music;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.WeakHashMap;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.init.ModContent;

public class InfusedFluteItem extends FluteItem {
    //Entities that have been tempted by the infused flute get stored in this weak set
    //so as not to add the same AI task multiple times
    private final Set<Entity> temptedEntities = Collections.newSetFromMap(new WeakHashMap<>(4));

    public InfusedFluteItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if(!world.isClientSide && !player.isShiftKeyDown())
            temptEntities(world, player.position());

        return super.use(world, player, hand);
    }

    @Override
    protected int getMusicAmount(Random rand) {
        return ModContent.flute.getBaseOutput() + rand.nextInt(120);
    }

    private void temptEntities(Level level, Vec3 pos) {
        level.getEntitiesOfClass(AgeableMob.class, new AABB(pos, pos).inflate(2),
                entity -> ((entity instanceof Animal && entity.getNavigation() instanceof GroundPathNavigation) || entity instanceof Villager)
                            && !temptedEntities.contains(entity))
            .forEach(entity -> {
                double speed = (entity instanceof Animal) ? 1 : 0.5;
                entity.goalSelector.addGoal(5, new TemptGoal(entity, speed, Ingredient.of(this), false));

                temptedEntities.add(entity);
            });
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(new TranslatableComponent(getDescriptionId() + ".tooltip"));
        super.appendHoverText(stack, level, tooltip, flag);
    }

    @Override
    public boolean isFoil(ItemStack stack) { //Makes the item glow
        return true;
    }
}
