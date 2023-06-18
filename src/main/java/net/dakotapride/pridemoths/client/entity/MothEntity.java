package net.dakotapride.pridemoths.client.entity;

import com.google.common.collect.ImmutableList;
import net.dakotapride.pridemoths.PrideMothsInitialize;
import net.dakotapride.pridemoths.client.entity.pride.IPrideMoths;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.FlyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;

public class MothEntity extends AnimalEntity implements GeoEntity, Flutterer, IPrideMoths {
    private static final TrackedData<String> VARIANT = DataTracker.registerData(MothEntity.class, TrackedDataHandlerRegistry.STRING);
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    public boolean fromBottle = false;
    public static final List<MothVariant> PRIDE_MOTH = ImmutableList.of(
            MothVariant.TRANS, MothVariant.LGBT, MothVariant.NON_BINARY, MothVariant.AGENDER, MothVariant.ASEXUAL,
            MothVariant.GAY, MothVariant.LESBIAN, MothVariant.BISEXUAL, MothVariant.PANSEXUAL);
    public static final List<MothVariant> NATURAL = ImmutableList.of(
            MothVariant.DEFAULT, MothVariant.YELLOW, MothVariant.BLUE, MothVariant.GREEN);

    public static MothVariant getPrideMothGeneration(Random random) {
        return PRIDE_MOTH.get(random.nextInt(PRIDE_MOTH.size()));
    }

    public static MothVariant getNaturalGeneration(Random random) {
        int rarePatternChance = 120;
        if (IPrideMoths.isWorldMothWeek()) {
            rarePatternChance = 40;
        }


        if (random.nextInt(rarePatternChance) == 0) {
            return MothVariant.PALOS_VERDES_BLUE;
        } else {
            return NATURAL.get(random.nextInt(NATURAL.size()));
        }
    }

    public MothEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.moveControl = new FlightMoveControl(this, 20, true);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0F);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0F);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0F);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4F)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.25F);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MothFlyGoal(this, 1.0));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0));
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    public static class MothFlyGoal extends FlyGoal {
        public MothFlyGoal(PathAwareEntity pathAwareEntity, double d) {
            super(pathAwareEntity, d);
        }

        @Nullable
        @Override
        protected Vec3d getWanderTarget() {
            Vec3d vec3d = this.mob.getRotationVec(0.0F);
            boolean i = true;
            Vec3d vec3d2 = AboveGroundTargeting.find(this.mob, 8, 7,
                    vec3d.x, vec3d.z, 1.5707964F, 3, 1);
            return vec3d2 != null ? vec3d2 : NoPenaltySolidTargeting.find(this.mob, 8,
                    4, -2, vec3d.x, vec3d.z, 1.5707963705062866);
        }
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(false);
        birdNavigation.setCanEnterOpenDoors(false);

        return birdNavigation;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controller) {
        controller.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.moth.flight", Animation.LoopType.LOOP));
        } else {
            event.getController().setAnimation(RawAnimation.begin().then("animation.moth.idle", Animation.LoopType.LOOP));
        }
        return PlayState.CONTINUE;
    }



    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    protected void initDataTracker() {
        LocalDate date;
        date = LocalDate.now();
        int getLocalMonthFromUser = date.get(ChronoField.MONTH_OF_YEAR);

        super.initDataTracker();

        if (getLocalMonthFromUser == 6) {
            this.dataTracker.startTracking(VARIANT, getPrideMothGeneration(random).toString());
        } else {
            this.dataTracker.startTracking(VARIANT, getNaturalGeneration(random).toString());
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.hasCustomName()) {
            if (this.getMothVariant() != MothVariant.NON_BINARY && this.getCustomName().getString().equals("non-binary")) {
                this.setMothVariant(MothVariant.NON_BINARY);
            } else if (this.getMothVariant() != MothVariant.TRANS && this.getCustomName().getString().equals("trans")) {
                this.setMothVariant(MothVariant.TRANS);
            } else if (this.getMothVariant() != MothVariant.LGBT && this.getCustomName().getString().equals("lgbt")) {
                this.setMothVariant(MothVariant.LGBT);
            } else if (this.getMothVariant() != MothVariant.GAY && this.getCustomName().getString().equals("gay")) {
                this.setMothVariant(MothVariant.GAY);
            } else if (this.getMothVariant() != MothVariant.LESBIAN && this.getCustomName().getString().equals("lesbian")) {
                this.setMothVariant(MothVariant.LESBIAN);
            } else if (this.getMothVariant() != MothVariant.AGENDER && this.getCustomName().getString().equals("agender")) {
                this.setMothVariant(MothVariant.AGENDER);
            } else if (this.getMothVariant() != MothVariant.ASEXUAL && this.getCustomName().getString().equals("asexual")) {
                this.setMothVariant(MothVariant.ASEXUAL);
            } else if (this.getMothVariant() != MothVariant.BISEXUAL && this.getCustomName().getString().equals("bisexual")) {
                this.setMothVariant(MothVariant.BISEXUAL);
            } else if (this.getMothVariant() != MothVariant.PANSEXUAL && this.getCustomName().getString().equals("pansexual")) {
                this.setMothVariant(MothVariant.PANSEXUAL);
            }
        }

    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (player.getStackInHand(hand).getItem() == Items.GLASS_BOTTLE) {
            Item item = Items.GLASS_BOTTLE;

            switch (this.getMothVariant()) {
                case DEFAULT -> item = PrideMothsInitialize.ORANGE_MOTH_BOTTLE;
                case TRANS -> item = PrideMothsInitialize.TRANS_MOTH_BOTTLE;
                case NON_BINARY -> item = PrideMothsInitialize.NON_BINARY_MOTH_BOTTLE;
                case AGENDER -> item = PrideMothsInitialize.AGENDER_MOTH_BOTTLE;
                case ASEXUAL -> item = PrideMothsInitialize.ASEXUAL_MOTH_BOTTLE;
                case GAY -> item = PrideMothsInitialize.GAY_MOTH_BOTTLE;
                case LESBIAN -> item = PrideMothsInitialize.LESBIAN_MOTH_BOTTLE;
                case BISEXUAL -> item = PrideMothsInitialize.BISEXUAL_MOTH_BOTTLE;
                case PANSEXUAL -> item = PrideMothsInitialize.PANSEXUAL_MOTH_BOTTLE;
                case LGBT -> item = PrideMothsInitialize.LGBT_MOTH_BOTTLE;
            }

            ItemStack itemStack = new ItemStack(item);
            if (this.hasCustomName()) {
                itemStack.setCustomName(this.getCustomName());
            }

            if (!player.getAbilities().creativeMode) {
                if (player.getStackInHand(hand).getCount() > 1) {
                    player.getStackInHand(hand).decrement(1);
                    if (!player.getInventory().insertStack(itemStack)) {
                        player.dropItem(itemStack, true);
                    }
                } else {
                    player.setStackInHand(hand, itemStack);
                }
            } else {
                if (!player.getInventory().insertStack(itemStack)) {
                    player.dropItem(itemStack, true);
                }
            }

            this.getWorld().playSound(player, player.getBlockPos(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0f, 1.0f);
            this.discard();
            return ActionResult.SUCCESS;
        }

        return super.interactMob(player, hand);
    }

    public MothVariant getMothVariant() {
        return MothVariant.valueOf(this.dataTracker.get(VARIANT));
    }

    public void setMothVariant(MothVariant type) {
        this.dataTracker.set(VARIANT, type.toString());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);

        this.fromBottle = tag.getBoolean("FromBottle");
        if (tag.contains("MothVariant")) {
            this.setMothVariant(MothVariant.valueOf(tag.getString("MothVariant")));
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);

        tag.putBoolean("FromBottle", fromBottle);
        tag.putString("MothVariant", this.getMothVariant().toString());
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_AXOLOTL_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_AXOLOTL_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    @Override
    public boolean isInAir() {
        return !this.onGround;
    }
}