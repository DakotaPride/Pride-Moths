package net.dakotapride.pridemoths.client.entity;

import net.dakotapride.pridemoths.PrideMothsInitialize;
import net.dakotapride.pridemoths.client.entity.pride.IPrideMoths;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.dakotapride.pridemoths.config.PrideMothsConfigs;
import net.dakotapride.pridemoths.register.EntityTypeRegistrar;
import net.dakotapride.pridemoths.register.ItemsRegistrar;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
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
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animatable.processing.AnimationTest;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.EnumSet;
import java.util.List;

public class MothEntity extends AnimalEntity implements GeoEntity, Flutterer, IPrideMoths {
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(MothEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public static final TrackedData<Boolean> FROM_JAR = DataTracker.registerData(MothEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final List<MothVariation> PRIDE_VARIATIONS = List.of(
            MothVariation.TRANSGENDER, MothVariation.LGBT, MothVariation.NON_BINARY, MothVariation.AGENDER, MothVariation.ASEXUAL,
            MothVariation.GAY, MothVariation.LESBIAN, MothVariation.BISEXUAL, MothVariation.PANSEXUAL, MothVariation.POLYAMOROUS,
            MothVariation.POLYSEXUAL, MothVariation.OMNISEXUAL, MothVariation.AROMANTIC, MothVariation.AROACE, MothVariation.DEMIGIRL,
            MothVariation.DEMISEXUAL, MothVariation.DEMIGENDER, MothVariation.DEMIROMANTIC, MothVariation.GENDERFLUID, MothVariation.INTERSEX,
            MothVariation.XENOGENDER, MothVariation.GENDER_QUEER, MothVariation.GENDERFAE, MothVariation.GENDERFAUN, MothVariation.BIGENDER,
            MothVariation.PANGENDER);

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
        this.goalSelector.add(5, new SwimGoal(this));
        this.goalSelector.add(3, new TemptGoal(this, 1.25, stack -> stack.isIn(PrideMothsInitialize.CAN_MOTH_EAT), false));
        this.targetSelector.add(2, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(8, new MothWanderAroundGoal());
    }

    public static MothVariation getPrideVariation(Random random) {
        return PRIDE_VARIATIONS.get(random.nextInt(PRIDE_VARIATIONS.size()));
    }

    public static final List<MothVariation> ASEXUAL_VISIBILITY_DAY_VARIATIONS = List.of(
            MothVariation.ASEXUAL, MothVariation.DEMISEXUAL, MothVariation.AROACE);
    public static final List<MothVariation> AROMANTIC_VISIBILITY_DAY_VARIATIONS = List.of(
            MothVariation.AROMANTIC, MothVariation.DEMIROMANTIC, MothVariation.AROACE);

    public static MothVariation getAceVariation(Random random) {
        return ASEXUAL_VISIBILITY_DAY_VARIATIONS.get(random.nextInt(ASEXUAL_VISIBILITY_DAY_VARIATIONS.size()));
    }

    public static MothVariation getAroVariation(Random random) {
        return AROMANTIC_VISIBILITY_DAY_VARIATIONS.get(random.nextInt(AROMANTIC_VISIBILITY_DAY_VARIATIONS.size()));
    }

    public static final List<MothVariation> DEMIGENDER_VISIBILITY_DAY_VARIATIONS = List.of(
            MothVariation.DEMIBOY, MothVariation.DEMIGIRL, MothVariation.DEMIGENDER);

    public static MothVariation getDemigenderVariation(Random random) {
        return DEMIGENDER_VISIBILITY_DAY_VARIATIONS.get(random.nextInt(DEMIGENDER_VISIBILITY_DAY_VARIATIONS.size()));
    }

    public static final List<MothVariation> TRANSGENDER_VISIBILITY_DAY_VARIATIONS = List.of(
            MothVariation.TRANSGENDER, MothVariation.NON_BINARY, MothVariation.AGENDER,
            MothVariation.DEMIBOY, MothVariation.DEMIGIRL, MothVariation.DEMIGENDER,
            MothVariation.GENDERFLUID, MothVariation.GENDER_QUEER, MothVariation.GENDERFAE, MothVariation.GENDERFAUN,
            MothVariation.BIGENDER, MothVariation.PANGENDER);

    public static MothVariation getTransgenderVariation(Random random) {
        return TRANSGENDER_VISIBILITY_DAY_VARIATIONS.get(random.nextInt(TRANSGENDER_VISIBILITY_DAY_VARIATIONS.size()));
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
        return EntityTypeRegistrar.MOTH.create(world, SpawnReason.BREEDING);
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
        return EntityDimensions.fixed(0.45F, 0.45F);
    }

    @Override
    protected void onGrowUp() {
        super.onGrowUp();
        if (!this.isBaby() && this.getWorld() instanceof ServerWorld serverWorld && serverWorld.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
            this.dropItem(serverWorld, ItemsRegistrar.MOTH_FUZZ);
        }

    }

    public void setFromInitialize() {
        LocalDate date = LocalDate.now();
        int getLocalMonthFromUser = date.get(ChronoField.MONTH_OF_YEAR);

        if (IPrideMoths.isAgenderDayOfVisibility()) {
            setMothVariant(MothVariation.AGENDER);
        } else if (IPrideMoths.isBisexualDayOfVisibility()) {
            setMothVariant(MothVariation.BISEXUAL);
        } else if (IPrideMoths.isGayDayOfVisibility()) {
            setMothVariant(MothVariation.GAY);
        } else if (IPrideMoths.isLesbianDayOfVisibility()) {
            setMothVariant(MothVariation.LESBIAN);
        } else if (IPrideMoths.isPansexualDayOfVisibility()) {
            setMothVariant(MothVariation.PANSEXUAL);
        } else if (IPrideMoths.isOmnisexualDayOfVisibility()) {
            setMothVariant(MothVariation.OMNISEXUAL);
        } else if (IPrideMoths.isPolyamorousDayOfVisibility()) {
            setMothVariant(MothVariation.POLYAMOROUS);
        } else if (IPrideMoths.isPolysexualDayOfVisibility()) {
            setMothVariant(MothVariation.POLYSEXUAL);
        } else if (IPrideMoths.isIntersexDayOfVisibility()) {
            setMothVariant(MothVariation.INTERSEX);
        } else if (IPrideMoths.isXenogenderDayOfVisibility()) {
            setMothVariant(MothVariation.XENOGENDER);
        } else if (IPrideMoths.isGenderQueerDayOfVisibility()) {
            setMothVariant(MothVariation.GENDER_QUEER);
        } else if (IPrideMoths.isGenderfluidWeekOfVisibility()) {
            setMothVariant(MothVariation.GENDERFLUID);
        }

        else if (IPrideMoths.isTransgenderDayOfVisibility()) {
            setMothVariant(getTransgenderVariation(random));
        } else if (IPrideMoths.isAsexualDayOfVisibility()) {
            setMothVariant(getAceVariation(random));
        } else if (IPrideMoths.isAromanticDayOfVisibility()) {
            setMothVariant(getAroVariation(random));
        } else if (IPrideMoths.isDemigenderDayOfVisibility()) {
            setMothVariant(getDemigenderVariation(random));
        }

        else if (getLocalMonthFromUser == 6 || PrideMothsConfigs.GENERATE_PRIDE_VARIANTS_OUTSIDE_OF_PRIDE_MONTH) {
            setMothVariant(getPrideVariation(random));
        }

        else {
            setMothVariant(getOtherVariation(random));
        }
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        setFromInitialize();

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

        if (player.getStackInHand(hand).getItem() == ItemsRegistrar.GLASS_JAR && !this.isBaby()) {
            ItemStack itemStack = getMothJarItemFromVariation();
            if (this.hasCustomName()) {
                itemStack.set(DataComponentTypes.CUSTOM_NAME, this.getCustomName());
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

    @Override
    public @Nullable ItemStack getPickBlockStack() {
        return getMothJarItemFromVariation();
    }

    private @NotNull ItemStack getMothJarItemFromVariation() {
        Item item;

        switch (this.getMothVariant()) {
            case RARE -> item = ItemsRegistrar.RARE_MOTH_JAR;
            case AGENDER -> item = ItemsRegistrar.AGENDER_MOTH_JAR;
            case AROACE -> item = ItemsRegistrar.AROACE_MOTH_JAR;
            case AROMANTIC -> item = ItemsRegistrar.AROMANTIC_MOTH_JAR;
            case ASEXUAL -> item = ItemsRegistrar.ASEXUAL_MOTH_JAR;
            case BISEXUAL -> item = ItemsRegistrar.BISEXUAL_MOTH_JAR;
            case DEMIBOY -> item = ItemsRegistrar.DEMIBOY_MOTH_JAR;
            case DEMIGENDER -> item = ItemsRegistrar.DEMIGENDER_MOTH_JAR;
            case DEMIGIRL -> item = ItemsRegistrar.DEMIGIRL_MOTH_JAR;
            case DEMIROMANTIC -> item = ItemsRegistrar.DEMIROMANTIC_MOTH_JAR;
            case DEMISEXUAL -> item = ItemsRegistrar.DEMISEXUAL_MOTH_JAR;
            case GAY -> item = ItemsRegistrar.GAY_MOTH_JAR;
            case LESBIAN -> item = ItemsRegistrar.LESBIAN_MOTH_JAR;
            case LGBT -> item = ItemsRegistrar.LGBT_MOTH_JAR;
            case NON_BINARY -> item = ItemsRegistrar.NON_BINARY_MOTH_JAR;
            case OMNISEXUAL -> item = ItemsRegistrar.OMNISEXUAL_MOTH_JAR;
            case PANSEXUAL -> item = ItemsRegistrar.PANSEXUAL_MOTH_JAR;
            case POLYAMOROUS -> item = ItemsRegistrar.POLYAMOROUS_MOTH_JAR;
            case POLYSEXUAL -> item = ItemsRegistrar.POLYSEXUAL_MOTH_JAR;
            case TRANSGENDER -> item = ItemsRegistrar.TRANSGENDER_MOTH_JAR;
            case GENDERFLUID -> item = ItemsRegistrar.GENDERFLUID_MOTH_JAR;
            case INTERSEX -> item = ItemsRegistrar.INTERSEX_MOTH_JAR;
            case XENOGENDER -> item = ItemsRegistrar.XENOGENDER_MOTH_JAR;
            case GENDER_QUEER -> item = ItemsRegistrar.GENDER_QUEER_MOTH_JAR;
            case GENDERFAE -> item = ItemsRegistrar.GENDERFAE_MOTH_JAR;
            case GENDERFAUN -> item = ItemsRegistrar.GENDERFAUN_MOTH_JAR;
            case BIGENDER -> item = ItemsRegistrar.BIGENDER_MOTH_JAR;
            case PANGENDER -> item = ItemsRegistrar.PANGENDER_MOTH_JAR;
            default -> item = ItemsRegistrar.MOTH_JAR;
        }

        return new ItemStack(item);
    }

    public void setMothVariant(MothVariation type) {
        this.dataTracker.set(VARIANT, type.getIndex());
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);

        builder.add(VARIANT, MothVariation.DEFAULT.getIndex());
        builder.add(FROM_JAR, false);

        // this.dataTracker.startTracking(VARIANT, MothVariation.DEFAULT.toString());
    }

    public boolean isFromGlassJar() {
        return this.dataTracker.get(FROM_JAR);
    }

    public void setFromGlassJar(boolean b) {
        this.dataTracker.set(FROM_JAR, b);
    }

    @Override
    protected void readCustomData(ReadView view) {
        super.readCustomData(view);
        this.setFromGlassJar(view.getBoolean("FromGlassJar", false));
        this.setMothVariant(view.read("MothVariant", MothVariation.INDEX_CODEC).orElse(MothVariation.DEFAULT));
    }

    @Override
    protected void writeCustomData(WriteView view) {
        super.writeCustomData(view);

        view.putBoolean("FromGlassJar", this.isFromGlassJar());
        view.put("MothVariant", MothVariation.INDEX_CODEC, this.getMothVariant());
    }

    public MothVariation getMothVariant() {
        return MothVariation.byIndex(this.dataTracker.get(VARIANT));
    }

    @Override
    public boolean handleFallDamage(double fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    private boolean queerNames() {
        String name = this.getName().getString();
        return name.equalsIgnoreCase("lgbt")
                || name.equalsIgnoreCase("lgbtq")
                || name.equalsIgnoreCase("lgbtqia")
                || name.equalsIgnoreCase("lgbtqia+");
    }

    private boolean nonBinaryNames() {
        String name = this.getName().getString();
        return name.equalsIgnoreCase("non-binary")
                || name.equalsIgnoreCase("non_binary")
                || name.equalsIgnoreCase("nonbinary")
                || name.equalsIgnoreCase("nyan-binary");
    }

    private boolean twoNames(String i0, String i1) {
        String name = this.getName().getString();
        return name.equalsIgnoreCase(i0) || name.equalsIgnoreCase(i1);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.hasCustomName() && !this.isBaby()) {
            if (this.getMothVariant() != MothVariation.NON_BINARY && nonBinaryNames()) {
                this.setMothVariant(MothVariation.NON_BINARY);
            } else if (this.getMothVariant() != MothVariation.TRANSGENDER && twoNames("trans", "transgender")) {
                this.setMothVariant(MothVariation.TRANSGENDER);
            } else if (this.getMothVariant() != MothVariation.LGBT && queerNames()) {
                this.setMothVariant(MothVariation.LGBT);
            } else if (this.getMothVariant() != MothVariation.GAY && twoNames("gay", "mlm")) {
                this.setMothVariant(MothVariation.GAY);
            } else if (this.getMothVariant() != MothVariation.LESBIAN && twoNames("lesbian", "wlw")) {
                this.setMothVariant(MothVariation.LESBIAN);
            } else if (this.getMothVariant() != MothVariation.AGENDER && this.getName().getString().equalsIgnoreCase("agender")) {
                this.setMothVariant(MothVariation.AGENDER);
            } else if (this.getMothVariant() != MothVariation.ASEXUAL && twoNames("asexual", "ace")) {
                this.setMothVariant(MothVariation.ASEXUAL);
            } else if (this.getMothVariant() != MothVariation.BISEXUAL && twoNames("bisexual", "bi")) {
                this.setMothVariant(MothVariation.BISEXUAL);
            } else if (this.getMothVariant() != MothVariation.PANSEXUAL && twoNames("pansexual", "pan")) {
                this.setMothVariant(MothVariation.PANSEXUAL);
            } else if (this.getMothVariant() != MothVariation.POLYAMOROUS && twoNames("polyamorous", "polygamous")) {
                this.setMothVariant(MothVariation.POLYAMOROUS);
            } else if (this.getMothVariant() != MothVariation.POLYSEXUAL && twoNames("polysexual", "poly")) {
                this.setMothVariant(MothVariation.POLYSEXUAL);
            } else if (this.getMothVariant() != MothVariation.OMNISEXUAL && twoNames("omnisexual", "omni")) {
                this.setMothVariant(MothVariation.OMNISEXUAL);
            } else if (this.getMothVariant() != MothVariation.AROMANTIC && twoNames("aromantic", "aro")) {
                this.setMothVariant(MothVariation.AROMANTIC);
            } else if (this.getMothVariant() != MothVariation.DEMISEXUAL && twoNames("demisexual", "demi")) {
                this.setMothVariant(MothVariation.DEMISEXUAL);
            } else if (this.getMothVariant() != MothVariation.DEMIROMANTIC && twoNames("demiromantic", "demiro")) {
                this.setMothVariant(MothVariation.DEMIROMANTIC);
            } else if (this.getMothVariant() != MothVariation.DEMIBOY && this.getName().getString().equalsIgnoreCase("demiboy")) {
                this.setMothVariant(MothVariation.DEMIBOY);
            } else if (this.getMothVariant() != MothVariation.DEMIGIRL && this.getName().getString().equalsIgnoreCase("demigirl")) {
                this.setMothVariant(MothVariation.DEMIGIRL);
            } else if (this.getMothVariant() != MothVariation.DEMIGENDER && this.getName().getString().equalsIgnoreCase("demigender")) {
                this.setMothVariant(MothVariation.DEMIGENDER);
            } else if (this.getMothVariant() != MothVariation.AROACE && this.getName().getString().equalsIgnoreCase("aroace")) {
                this.setMothVariant(MothVariation.AROACE);
            } else if (this.getMothVariant() != MothVariation.GENDERFLUID && twoNames("gender_fluid", "genderfluid")) {
                this.setMothVariant(MothVariation.GENDERFLUID);
            } else if (this.getMothVariant() != MothVariation.INTERSEX && this.getName().getString().equalsIgnoreCase("intersex")) {
                this.setMothVariant(MothVariation.INTERSEX);
            } else if (this.getMothVariant() != MothVariation.XENOGENDER && this.getName().getString().equalsIgnoreCase("xenogender")) {
                this.setMothVariant(MothVariation.XENOGENDER);
            } else if (this.getMothVariant() != MothVariation.GENDER_QUEER && twoNames("gender_queer", "genderqueer")) {
                this.setMothVariant(MothVariation.GENDER_QUEER);
            } else if (this.getMothVariant() != MothVariation.GENDERFAE && this.getName().getString().equalsIgnoreCase("genderfae")) {
                this.setMothVariant(MothVariation.GENDERFAE);
            } else if (this.getMothVariant() != MothVariation.GENDERFAUN && this.getName().getString().equalsIgnoreCase("genderfaun")) {
                this.setMothVariant(MothVariation.GENDERFAUN);
            } else if (this.getMothVariant() != MothVariation.BIGENDER && this.getName().getString().equalsIgnoreCase("bigender")) {
                this.setMothVariant(MothVariation.BIGENDER);
            } else if (this.getMothVariant() != MothVariation.PANGENDER && this.getName().getString().equalsIgnoreCase("pangender")) {
                this.setMothVariant(MothVariation.PANGENDER);
            }

            if (this.getMothVariant() != MothVariation.ALLY && twoNames("ally", "straight")) {
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

    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanOpenDoors(false);
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
    public void registerControllers(final AnimatableManager.ControllerRegistrar controller) {
        //controller.add(new AnimationController<>(this, "controller", 0, this::predicate));
        controller.add(new AnimationController<>("controller", 0, this::animController));
    }

    protected PlayState animController(final AnimationTest<GeoAnimatable> animTest) {
        animTest.setAndContinue(RawAnimation.begin().thenLoop("animation.moth.idle"));

        return PlayState.CONTINUE;
    }

//    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
//        // if (event.isMoving()) {
//        //            event.getController().setAnimation(RawAnimation.begin().then("animation.moth.flight", Animation.LoopType.LOOP));
//        //        } else {
//        //            event.getController().setAnimation(RawAnimation.begin().then("animation.moth.idle", Animation.LoopType.LOOP));
//        //        }
//
//        event.getController().setAnimation(RawAnimation.begin().then("animation.moth.idle", Animation.LoopType.LOOP));
//
//        return PlayState.CONTINUE;
//    }

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

    // Fake target
    @Override
    public boolean canTarget(EntityType<?> type) {
        return type == EntityType.PLAYER;
    }

    class MothWanderAroundGoal extends Goal {
        MothWanderAroundGoal() {
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            return MothEntity.this.navigation.isIdle() && MothEntity.this.random.nextInt(10) == 0;
        }

        @Override
        public boolean shouldContinue() {
            return MothEntity.this.navigation.isFollowingPath();
        }

        @Override
        public void start() {
            Vec3d vec3d = this.getRandomLocation();
            if (vec3d != null) {
                MothEntity.this.navigation.startMovingAlong(MothEntity.this.navigation.findPathTo(BlockPos.ofFloored(vec3d), 1), 1.0);
            }
        }

        @Nullable
        private Vec3d getRandomLocation() {
            Vec3d vec3d2 = MothEntity.this.getRotationVec(0.35F);

            Vec3d vec3d3 = AboveGroundTargeting.find(MothEntity.this, 8, 7, vec3d2.x, vec3d2.z, (float) (Math.PI / 2), 3, 1);
            return vec3d3 != null ? vec3d3 : NoPenaltySolidTargeting.find(MothEntity.this, 8, 4, -2, vec3d2.x, vec3d2.z, (float) (Math.PI / 2));
        }
    }

}
