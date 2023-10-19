package net.dakotapride.pridemoths.client.entity;

import net.dakotapride.pridemoths.PrideMothsInitialize;
import net.dakotapride.pridemoths.client.entity.pride.IPrideMoths;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
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
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;

public class MothEntity extends AnimalEntity implements IAnimatable, Flutterer, IPrideMoths {
    private static final TrackedData<String> VARIANT = DataTracker.registerData(MothEntity.class, TrackedDataHandlerRegistry.STRING);
    private final AnimationFactory factory = new AnimationFactory(this);
    public boolean fromJar = false;
    public static final List<MothVariation> PRIDE_VARIATIONS = List.of(
            MothVariation.TRANSGENDER, MothVariation.LGBT, MothVariation.NON_BINARY, MothVariation.AGENDER, MothVariation.ASEXUAL,
            MothVariation.GAY, MothVariation.LESBIAN, MothVariation.BISEXUAL, MothVariation.PANSEXUAL, MothVariation.POLYAMOROUS,
            MothVariation.POLYSEXUAL, MothVariation.OMNISEXUAL, MothVariation.AROMANTIC, MothVariation.AROACE, MothVariation.DEMIGIRL,
            MothVariation.DEMISEXUAL, MothVariation.DEMIGENDER, MothVariation.DEMIROMANTIC);

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
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4F)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.25F);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MothFlyGoal(this, 1.0));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0));
        this.targetSelector.add(1, new AnimalMateGoal(this, 1.0));
    }

    public static MothVariation getPrideVariation(Random random) {
        return PRIDE_VARIATIONS.get(random.nextInt(PRIDE_VARIATIONS.size()));
    }

    public static MothVariation getOtherVariation(Random random) {
        int rarePatternChance = 240;
        if (IPrideMoths.isWorldMothWeek()) {
            rarePatternChance = 120;
        }

        if (random.nextInt(rarePatternChance) == 1) {
            return MothVariation.RARE;
        } else {
            return MothVariation.DEFAULT;
        }
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return PrideMothsInitialize.MOTH.create(world);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return isFavouredFoodItem(stack);
    }

    public boolean isFavouredFoodItem(ItemStack stack) {
        return stack.getItem() == PrideMothsInitialize.FRUITFUL_STEW || stack.getItem() == Items.HONEY_BOTTLE;
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return EntityDimensions.fixed(0.3F, 0.3F);
    }

    @Override
    protected void onGrowUp() {
        super.onGrowUp();
        if (!this.isBaby() && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
            this.dropItem(PrideMothsInitialize.MOTH_FUZZ, 1);
        }

    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        LocalDate date;
        date = LocalDate.now();
        int getLocalMonthFromUser = date.get(ChronoField.MONTH_OF_YEAR);

        if (getLocalMonthFromUser == 6) {
            setMothVariant(getPrideVariation(random));
        } else {
            setMothVariant(getOtherVariation(random));
        }

        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (isBreedingItem(getActiveItem()) && !this.isBaby()) {
            return super.interactMob(player, hand);
        }

        if (player.getStackInHand(hand).getItem() == PrideMothsInitialize.GLASS_JAR && !this.isBaby()) {
            Item item;

            switch (this.getMothVariant()) {
                case RARE -> item = PrideMothsInitialize.RARE_MOTH_JAR;
                case AGENDER -> item = PrideMothsInitialize.AGENDER_MOTH_JAR;
                case AROACE -> item = PrideMothsInitialize.AROACE_MOTH_JAR;
                case AROMANTIC -> item = PrideMothsInitialize.AROMANTIC_MOTH_JAR;
                case ASEXUAL -> item = PrideMothsInitialize.ASEXUAL_MOTH_JAR;
                case BISEXUAL -> item = PrideMothsInitialize.BISEXUAL_MOTH_JAR;
                case DEMIBOY -> item = PrideMothsInitialize.DEMIBOY_MOTH_JAR;
                case DEMIGENDER -> item = PrideMothsInitialize.DEMIGENDER_MOTH_JAR;
                case DEMIGIRL -> item = PrideMothsInitialize.DEMIGIRL_MOTH_JAR;
                case DEMIROMANTIC -> item = PrideMothsInitialize.DEMIROMANTIC_MOTH_JAR;
                case DEMISEXUAL -> item = PrideMothsInitialize.DEMISEXUAL_MOTH_JAR;
                case GAY -> item = PrideMothsInitialize.GAY_MOTH_JAR;
                case LESBIAN -> item = PrideMothsInitialize.LESBIAN_MOTH_JAR;
                case LGBT -> item = PrideMothsInitialize.LGBT_MOTH_JAR;
                case NON_BINARY -> item = PrideMothsInitialize.NON_BINARY_MOTH_JAR;
                case OMNISEXUAL -> item = PrideMothsInitialize.OMNISEXUAL_MOTH_JAR;
                case PANSEXUAL -> item = PrideMothsInitialize.PANSEXUAL_MOTH_JAR;
                case POLYAMOROUS -> item = PrideMothsInitialize.POLYAMOROUS_MOTH_JAR;
                case POLYSEXUAL -> item = PrideMothsInitialize.POLYSEXUAL_MOTH_JAR;
                case TRANSGENDER -> item = PrideMothsInitialize.TRANSGENDER_MOTH_JAR;
                default -> item = PrideMothsInitialize.MOTH_JAR;
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

    public void setMothVariant(MothVariation type) {
        this.dataTracker.set(VARIANT, type.toString());
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();

        this.dataTracker.startTracking(VARIANT, MothVariation.DEFAULT.toString());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);

        this.fromJar = tag.getBoolean("FromGlassJar");
        if (tag.contains("MothVariant")) {
            this.setMothVariant(MothVariation.valueOf(tag.getString("MothVariant")));
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);

        tag.putBoolean("FromGlassJar", fromJar);
        tag.putString("MothVariant", this.getMothVariant().toString());
    }

    public MothVariation getMothVariant() {
        return MothVariation.valueOf(this.dataTracker.get(VARIANT));
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.hasCustomName() && !this.isBaby()) {
            if (this.getMothVariant() != MothVariation.NON_BINARY && this.getCustomName().getString().equals("non-binary")) {
                this.setMothVariant(MothVariation.NON_BINARY);
            } else if (this.getMothVariant() != MothVariation.TRANSGENDER && this.getCustomName().getString().equals("trans")) {
                this.setMothVariant(MothVariation.TRANSGENDER);
            } else if (this.getMothVariant() != MothVariation.LGBT && this.getCustomName().getString().equals("lgbt")) {
                this.setMothVariant(MothVariation.LGBT);
            } else if (this.getMothVariant() != MothVariation.GAY && this.getCustomName().getString().equals("gay")) {
                this.setMothVariant(MothVariation.GAY);
            } else if (this.getMothVariant() != MothVariation.LESBIAN && this.getCustomName().getString().equals("lesbian")) {
                this.setMothVariant(MothVariation.LESBIAN);
            } else if (this.getMothVariant() != MothVariation.AGENDER && this.getCustomName().getString().equals("agender")) {
                this.setMothVariant(MothVariation.AGENDER);
            } else if (this.getMothVariant() != MothVariation.ASEXUAL && this.getCustomName().getString().equals("asexual")) {
                this.setMothVariant(MothVariation.ASEXUAL);
            } else if (this.getMothVariant() != MothVariation.BISEXUAL && this.getCustomName().getString().equals("bisexual")) {
                this.setMothVariant(MothVariation.BISEXUAL);
            } else if (this.getMothVariant() != MothVariation.PANSEXUAL && this.getCustomName().getString().equals("pansexual")) {
                this.setMothVariant(MothVariation.PANSEXUAL);
            } else if (this.getMothVariant() != MothVariation.POLYAMOROUS && this.getCustomName().getString().equals("polyamorous")) {
                this.setMothVariant(MothVariation.POLYAMOROUS);
            } else if (this.getMothVariant() != MothVariation.POLYSEXUAL && this.getCustomName().getString().equals("polysexual")) {
                this.setMothVariant(MothVariation.POLYSEXUAL);
            } else if (this.getMothVariant() != MothVariation.OMNISEXUAL && this.getCustomName().getString().equals("omnisexual")) {
                this.setMothVariant(MothVariation.OMNISEXUAL);
            } else if (this.getMothVariant() != MothVariation.DEMISEXUAL && this.getCustomName().getString().equals("demisexual")) {
                this.setMothVariant(MothVariation.DEMISEXUAL);
            } else if (this.getMothVariant() != MothVariation.DEMIROMANTIC && this.getCustomName().getString().equals("demiromantic")) {
                this.setMothVariant(MothVariation.DEMIROMANTIC);
            } else if (this.getMothVariant() != MothVariation.DEMIBOY && this.getCustomName().getString().equals("demiboy")) {
                this.setMothVariant(MothVariation.DEMIBOY);
            } else if (this.getMothVariant() != MothVariation.DEMIGIRL && this.getCustomName().getString().equals("demigirl")) {
                this.setMothVariant(MothVariation.DEMIGIRL);
            } else if (this.getMothVariant() != MothVariation.DEMIGENDER && this.getCustomName().getString().equals("demigender")) {
                this.setMothVariant(MothVariation.DEMIGENDER);
            } else if (this.getMothVariant() != MothVariation.AROACE && this.getCustomName().getString().equals("aroace")) {
                this.setMothVariant(MothVariation.AROACE);
            }
        }

    }

    public static class MothFlyGoal extends FlyGoal {
        public MothFlyGoal(PathAwareEntity pathAwareEntity, double d) {
            super(pathAwareEntity, d);
        }

        @Nullable
        @Override
        protected Vec3d getWanderTarget() {
            Vec3d vec3d = this.mob.getRotationVec(0.0F);
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

    @Override
    public boolean isInAir() {
        return !this.isOnGround();
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        // if (event.isMoving()) {
        //            event.getController().setAnimation(RawAnimation.begin().then("animation.moth.flight", Animation.LoopType.LOOP));
        //        } else {
        //            event.getController().setAnimation(RawAnimation.begin().then("animation.moth.idle", Animation.LoopType.LOOP));
        //        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.moth.idle", true));

        return PlayState.CONTINUE;
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
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public boolean canTarget(EntityType<?> type) {
        return type == EntityType.PLAYER;
    }

}
