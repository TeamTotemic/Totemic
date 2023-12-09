package pokefenn.totemic.entity;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.init.ModEntityTypes;
import pokefenn.totemic.init.ModSounds;

public class BaldEagle extends TamableAnimal implements FlyingAnimal {
    public float flap;
    public float flapSpeed;
    public float oFlapSpeed;
    public float oFlap;
    private float flapping = 1.0F;
    private float nextFlap = 1.0F;

    public BaldEagle(EntityType<? extends BaldEagle> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        setTame(false);
        this.moveControl = new FlyingMoveControl(this, 10, false);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0)
                .add(Attributes.FLYING_SPEED, 0.4)
                .add(Attributes.MOVEMENT_SPEED, 0.2);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.0D, 5.0F, 1.0F, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new FollowMobGoal(this, 1.0D, 3.0F, 7.0F));
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, pLevel);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pDimensions) {
        return pDimensions.height * 0.6F;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        calculateFlapping();
    }

    private void calculateFlapping() {
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed += (!this.onGround() && !this.isPassenger() ? 4 : -1) * 0.3F;
        this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
        if(!this.onGround() && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }

        this.flapping *= 0.9F;
        Vec3 vec3 = this.getDeltaMovement();
        if(!this.onGround() && vec3.y < 0.0D) {
            this.setDeltaMovement(vec3.multiply(1.0D, 0.6D, 1.0D));
        }

        this.flap += this.flapping * 2.0F;
    }

    @SuppressWarnings("resource")
    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if(!this.isTame() && itemstack.is(ItemTags.FISHES)) {
            if(!pPlayer.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            if(!this.isSilent()) {
                this.level().playSound((Player) null, this.getX(), this.getY(), this.getZ(), SoundEvents.PARROT_EAT, this.getSoundSource(), 1.0F,
                        1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
            }

            if(!this.level().isClientSide) {
                if(this.random.nextInt(6) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, pPlayer)) {
                    this.tame(pPlayer);
                    this.level().broadcastEntityEvent(this, (byte) 7);
                }
                else {
                    this.level().broadcastEntityEvent(this, (byte) 6);
                }
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        else if(this.isTame() && this.isOwnedBy(pPlayer)) {
            if(isFood(itemstack)) {
                return super.mobInteract(pPlayer, pHand);
            }
            else if(!this.isFlying() && !this.level().isClientSide) {
                this.setOrderedToSit(!this.isOrderedToSit());
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        else {
            return super.mobInteract(pPlayer, pHand);
        }
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.SALMON);
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    @Override
    public boolean canMate(Animal otherAnimal) {
        if(otherAnimal == this)
            return false;
        else if(!isTame())
            return false;
        else if(!(otherAnimal instanceof BaldEagle))
            return false;
        else {
            BaldEagle otherEagle = (BaldEagle) otherAnimal;
            if(!otherEagle.isTame())
                return false;
            else if(otherEagle.isInSittingPose())
                return false;
            else
                return isInLove() && otherEagle.isInLove();
        }
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        var child = ModEntityTypes.bald_eagle.get().create(pLevel);
        var owner = getOwnerUUID();
        if(owner != null) {
            child.setOwnerUUID(owner);
            child.setTame(true);
        }
        return child;
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        return pEntity.hurt(pEntity.damageSources().mobAttack(this), 3.0F);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.bald_eagle_ambient.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return ModSounds.bald_eagle_hurt.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return ModSounds.bald_eagle_death.get();
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pState) {
        this.playSound(SoundEvents.PARROT_STEP, 0.15F, 1.0F);
    }

    @Override
    protected boolean isFlapping() {
        return this.flyDist > this.nextFlap;
    }

    @Override
    protected void onFlap() {
        this.playSound(SoundEvents.PARROT_FLY, 0.15F, 1.0F);
        this.nextFlap = this.flyDist + this.flapSpeed / 2.0F;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    protected void doPush(Entity pEntity) {
        if(!(pEntity instanceof Player)) {
            super.doPush(pEntity);
        }
    }

    @SuppressWarnings("resource")
    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if(this.isInvulnerableTo(pSource)) {
            return false;
        }
        else {
            if(!this.level().isClientSide) {
                this.setOrderedToSit(false);
            }

            return super.hurt(pSource, pAmount);
        }
    }

    @Override
    public boolean isFlying() {
        return !onGround();
    }

    @Override
    public Vec3 getLeashOffset() {
        return new Vec3(0.0D, 0.5F * this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }
}
