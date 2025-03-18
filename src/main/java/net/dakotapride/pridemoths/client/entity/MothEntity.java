package net.dakotapride.pridemoths.client.entity;

import net.dakotapride.pridemoths.PrideMothsInitialize;
import net.dakotapride.pridemoths.client.entity.pride.IPrideMoths;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.dakotapride.pridemoths.config.PrideMothsConfigs;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
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
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;

public class MothEntity extends AnimalEntity implements GeoEntity, Flutterer, IPrideMoths {
    private static final TrackedData<String> VARIANT = DataTracker.registerData(MothEntity.class, TrackedDataHandlerRegistry.STRING);
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    public boolean fromJar = false;
    public static final List<MothVariation> PRIDE_VARIATIONS = List.of(
            MothVariation.TRANSGENDER, MothVariation.LGBT, MothVariation.NON_BINARY, MothVariation.AGENDER, MothVariation.ASEXUAL,
            MothVariation.GAY, MothVariation.LESBIAN, MothVariation.BISEXUAL, MothVariation.PANSEXUAL, MothVariation.POLYAMOROUS,
            MothVariation.POLYSEXUAL, MothVariation.OMNISEXUAL, MothVariation.AROMANTIC, MothVariation.AROACE, MothVariation.DEMIGIRL,
            MothVariation.DEMISEXUAL, MothVariation.DEMIGENDER, MothVariation.DEMIROMANTIC);

    public BlockPos lightPos;
    private int refreshLightPosIn = 0;

    public MothEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        //this.ignoreCameraFrustum = true;
        this.moveControl = new FlightMoveControl(this, 20, true);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0F);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0F);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0F);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 8.0D)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.4F)
                .add(EntityAttributes.FLYING_SPEED, 0.25F)
                .add(EntityAttributes.TEMPT_RANGE, 10.0);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new MothFlyGoal(this, 1.0));
        this.goalSelector.add(5, new SwimGoal(this));
        this.goalSelector.add(4, new WanderAroundGoal(this, 1.0));
        this.goalSelector.add(2, new TravelToLightSourceGoal(this, 32));
        this.goalSelector.add(3, new TemptGoal(this, 1.25, stack -> stack.isIn(PrideMothsInitialize.CAN_MOTH_EAT), false));
        this.targetSelector.add(2, new AnimalMateGoal(this, 1.0));
    }

    public static MothVariation getPrideVariation(Random random) {
        return PRIDE_VARIATIONS.get(random.nextInt(PRIDE_VARIATIONS.size()));
    }

    public static MothVariation getOtherVariation(Random random) {
        int rarePatternChance = PrideMothsConfigs.BASE_RARE_CHANCE;
        if (IPrideMoths.isWorldMothWeek()) {
            rarePatternChance = PrideMothsConfigs.BASE_RARE_CHANCE_MOTH_WEEK;
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
        return PrideMothsInitialize.MOTH.create(world, SpawnReason.BREEDING);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return isFavouredFoodItem(stack);
    }

    public boolean isFavouredFoodItem(ItemStack stack) {
        return stack.getItem().getDefaultStack().isIn(PrideMothsInitialize.CAN_MOTH_EAT);
    }

    @Override
    protected EntityDimensions getBaseDimensions(EntityPose pose) {
        return EntityDimensions.fixed(0.3F, 0.3F);
    }

    @Override
    protected void onGrowUp() {
        super.onGrowUp();
        if (!this.isBaby() && this.getWorld() instanceof ServerWorld serverWorld && serverWorld.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
            this.dropItem(serverWorld, PrideMothsInitialize.MOTH_FUZZ);
        }

    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        LocalDate date;
        date = LocalDate.now();
        int getLocalMonthFromUser = date.get(ChronoField.MONTH_OF_YEAR);

        if (getLocalMonthFromUser == 6 || PrideMothsConfigs.GENERATE_PRIDE_VARIANTS_OUTSIDE_OF_PRIDE_MONTH) {
            setMothVariant(getPrideVariation(random));
        } else {
            setMothVariant(getOtherVariation(random));
        }

        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getStackInHand(hand);
        if (isBreedingItem(itemstack)) {
            if (isFavouredFoodItem(itemstack)) {
                int i = this.getBreedingAge();
                if (!this.getWorld().isClient && i == 0 && this.canEat()) {
                    this.eat(player, hand, itemstack);
                    this.lovePlayer(player);
                    return ActionResult.SUCCESS_SERVER;
                }

                if (this.isBaby()) {
                    this.eat(player, hand, itemstack);
                    this.growUp(toGrowUpAge(-i), true);
                    //return ActionResult.SUCCESS;
                }

                if (this.getWorld().isClient) {
                    return ActionResult.CONSUME;
                }

            }
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
                this.setCustomName(itemStack.get(DataComponentTypes.CUSTOM_NAME));
                // itemStack.setName(this.getCustomName());
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

        return ActionResult.PASS;
    }

    public void setMothVariant(MothVariation type) {
        this.dataTracker.set(VARIANT, type.toString());
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);

        builder.add(VARIANT, MothVariation.DEFAULT.toString());

        // this.dataTracker.startTracking(VARIANT, MothVariation.DEFAULT.toString());
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
            if (this.getMothVariant() != MothVariation.NON_BINARY && this.getName().getString().equalsIgnoreCase("non-binary")) {
                this.setMothVariant(MothVariation.NON_BINARY);
            } else if (this.getMothVariant() != MothVariation.NON_BINARY && this.getName().getString().equalsIgnoreCase("non binary")) {
                this.setMothVariant(MothVariation.NON_BINARY);
            } else if (this.getMothVariant() != MothVariation.TRANSGENDER && this.getName().getString().equalsIgnoreCase("trans")) {
                this.setMothVariant(MothVariation.TRANSGENDER);
            } else if (this.getMothVariant() != MothVariation.TRANSGENDER && this.getName().getString().equalsIgnoreCase("transgender")) {
                this.setMothVariant(MothVariation.TRANSGENDER);
            } else if (this.getMothVariant() != MothVariation.LGBT && this.getName().getString().equalsIgnoreCase("lgbt")) {
                this.setMothVariant(MothVariation.LGBT);
            } else if (this.getMothVariant() != MothVariation.LGBT && this.getName().getString().equalsIgnoreCase("lgbtq")) {
                this.setMothVariant(MothVariation.LGBT);
            } else if (this.getMothVariant() != MothVariation.LGBT && this.getName().getString().equalsIgnoreCase("lgbtqia")) {
                this.setMothVariant(MothVariation.LGBT);
            } else if (this.getMothVariant() != MothVariation.GAY && this.getName().getString().equalsIgnoreCase("gay")) {
                this.setMothVariant(MothVariation.GAY);
            } else if (this.getMothVariant() != MothVariation.GAY && this.getName().getString().equalsIgnoreCase("mlm")) {
                this.setMothVariant(MothVariation.GAY);
            } else if (this.getMothVariant() != MothVariation.LESBIAN && this.getName().getString().equalsIgnoreCase("lesbian")) {
                this.setMothVariant(MothVariation.LESBIAN);
            } else if (this.getMothVariant() != MothVariation.LESBIAN && this.getName().getString().equalsIgnoreCase("wlw")) {
                this.setMothVariant(MothVariation.LESBIAN);
            } else if (this.getMothVariant() != MothVariation.AGENDER && this.getName().getString().equalsIgnoreCase("agender")) {
                this.setMothVariant(MothVariation.AGENDER);
            } else if (this.getMothVariant() != MothVariation.ASEXUAL && this.getName().getString().equalsIgnoreCase("asexual")) {
                this.setMothVariant(MothVariation.ASEXUAL);
            } else if (this.getMothVariant() != MothVariation.ASEXUAL && this.getName().getString().equalsIgnoreCase("ace")) {
                this.setMothVariant(MothVariation.ASEXUAL);
            } else if (this.getMothVariant() != MothVariation.BISEXUAL && this.getName().getString().equalsIgnoreCase("bisexual")) {
                this.setMothVariant(MothVariation.BISEXUAL);
            } else if (this.getMothVariant() != MothVariation.BISEXUAL && this.getName().getString().equalsIgnoreCase("bi")) {
                this.setMothVariant(MothVariation.BISEXUAL);
            } else if (this.getMothVariant() != MothVariation.PANSEXUAL && this.getName().getString().equalsIgnoreCase("pansexual")) {
                this.setMothVariant(MothVariation.PANSEXUAL);
            } else if (this.getMothVariant() != MothVariation.PANSEXUAL && this.getName().getString().equalsIgnoreCase("pan")) {
                this.setMothVariant(MothVariation.PANSEXUAL);
            } else if (this.getMothVariant() != MothVariation.POLYAMOROUS && this.getName().getString().equalsIgnoreCase("polyamorous")) {
                this.setMothVariant(MothVariation.POLYAMOROUS);
            } else if (this.getMothVariant() != MothVariation.POLYAMOROUS && this.getName().getString().equalsIgnoreCase("polygamous")) {
                this.setMothVariant(MothVariation.POLYAMOROUS);
            } else if (this.getMothVariant() != MothVariation.POLYSEXUAL && this.getName().getString().equalsIgnoreCase("polysexual")) {
                this.setMothVariant(MothVariation.POLYSEXUAL);
            } else if (this.getMothVariant() != MothVariation.POLYSEXUAL && this.getName().getString().equalsIgnoreCase("poly")) {
                this.setMothVariant(MothVariation.POLYSEXUAL);
            } else if (this.getMothVariant() != MothVariation.OMNISEXUAL && this.getName().getString().equalsIgnoreCase("omnisexual")) {
                this.setMothVariant(MothVariation.OMNISEXUAL);
            } else if (this.getMothVariant() != MothVariation.OMNISEXUAL && this.getName().getString().equalsIgnoreCase("omni")) {
                this.setMothVariant(MothVariation.OMNISEXUAL);
            } else if (this.getMothVariant() != MothVariation.DEMISEXUAL && this.getName().getString().equalsIgnoreCase("demisexual")) {
                this.setMothVariant(MothVariation.DEMISEXUAL);
            } else if (this.getMothVariant() != MothVariation.DEMISEXUAL && this.getName().getString().equalsIgnoreCase("demi")) {
                this.setMothVariant(MothVariation.DEMISEXUAL);
            } else if (this.getMothVariant() != MothVariation.DEMIROMANTIC && this.getName().getString().equalsIgnoreCase("demiromantic")) {
                this.setMothVariant(MothVariation.DEMIROMANTIC);
            } else if (this.getMothVariant() != MothVariation.DEMIROMANTIC && this.getName().getString().equalsIgnoreCase("demiro")) {
                this.setMothVariant(MothVariation.DEMIROMANTIC);
            } else if (this.getMothVariant() != MothVariation.DEMIBOY && this.getName().getString().equalsIgnoreCase("demiboy")) {
                this.setMothVariant(MothVariation.DEMIBOY);
            } else if (this.getMothVariant() != MothVariation.DEMIGIRL && this.getName().getString().equalsIgnoreCase("demigirl")) {
                this.setMothVariant(MothVariation.DEMIGIRL);
            } else if (this.getMothVariant() != MothVariation.DEMIGENDER && this.getName().getString().equalsIgnoreCase("demigender")) {
                this.setMothVariant(MothVariation.DEMIGENDER);
            } else if (this.getMothVariant() != MothVariation.AROACE && this.getName().getString().equalsIgnoreCase("aroace")) {
                this.setMothVariant(MothVariation.AROACE);
            }

            if (this.getMothVariant() != MothVariation.ALLY && this.getCustomName().getString().equalsIgnoreCase("ally")) {
                this.setMothVariant(MothVariation.ALLY);
            } else if (this.getMothVariant() != MothVariation.ALLY && this.getCustomName().getString().equalsIgnoreCase("straight")) {
                this.setMothVariant(MothVariation.ALLY);
            }

            if (this.getWorld() instanceof ServerWorld world) {
                if (this.getCustomName().getString().equalsIgnoreCase("super straight")) {
                    this.kill(world);
                } else if (this.getCustomName().getString().equalsIgnoreCase("super_straight")) {
                    this.kill(world);
                } else if (this.getCustomName().getString().equalsIgnoreCase("superstraight")) {
                    this.kill(world);
                } else if (this.getCustomName().getString().equalsIgnoreCase("super-straight")) {
                    this.kill(world);
                }
            }
        }

        if (lightPos != null && this.isAlive() && !getWorld().isClient) {
            if (refreshLightPosIn-- < 0) {
                refreshLightPosIn = 40 + random.nextInt(100);
                if (this.squaredDistanceTo(Vec3d.ofCenter(lightPos)) >= 256 || !getWorld().getBlockState(lightPos).isIn(PrideMothsInitialize.LIGHT_SOURCES_TAG) || getWorld().getLightLevel(lightPos) <= 0) {
                    lightPos = null;
                }
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
        //birdNavigation.setCanEnterOpenDoors(false);

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
    public void registerControllers(AnimatableManager.ControllerRegistrar controller) {
        controller.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
        // if (event.isMoving()) {
        //            event.getController().setAnimation(RawAnimation.begin().then("animation.moth.flight", Animation.LoopType.LOOP));
        //        } else {
        //            event.getController().setAnimation(RawAnimation.begin().then("animation.moth.idle", Animation.LoopType.LOOP));
        //        }

        event.getController().setAnimation(RawAnimation.begin().then("animation.moth.idle", Animation.LoopType.LOOP));

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
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public boolean canTarget(EntityType<?> type) {
        return type == EntityType.PLAYER;
    }

}
