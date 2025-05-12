package net.dakotapride.pridemoths.block;

import com.mojang.serialization.MapCodec;
import net.dakotapride.pridemoths.PrideMothsInitialize;
import net.dakotapride.pridemoths.item.GlassJarItem;
import net.dakotapride.pridemoths.register.BlockEntityTypeRegistrar;
import net.dakotapride.pridemoths.register.DataComponentsRegistrar;
import net.dakotapride.pridemoths.register.ItemsRegistrar;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlockStateComponent;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Consumer;

public class MothEnclosureBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final MapCodec<MothEnclosureBlock> CODEC = createCodec(MothEnclosureBlock::new);
    public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;
    public static final IntProperty FUZZ_LEVEL = PrideMothsInitialize.FUZZ_LEVEL;
    public static final List<BooleanProperty> SLOT_OCCUPIED_PROPERTIES = List.of(
            Properties.SLOT_0_OCCUPIED,
            Properties.SLOT_1_OCCUPIED,
            Properties.SLOT_2_OCCUPIED
    );

    public MothEnclosureBlock(Settings settings) {
        super(settings);
        BlockState blockState = this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(FUZZ_LEVEL, 0);

        for (BooleanProperty booleanProperty : SLOT_OCCUPIED_PROPERTIES) {
            blockState = blockState.with(booleanProperty, Boolean.FALSE);
        }

        this.setDefaultState(blockState);
    }

    public static class MothEnclosureBlockItem extends BlockItem {
        public MothEnclosureBlockItem(Block block, Settings settings) {
            super(block, settings.registryKey(PrideMothsInitialize.keyOfItem("moth_enclosure")).translationKey("block.pridemoths.moth_enclosure"));
        }

        @Override
        public void appendTooltip(ItemStack itemStack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
            for (ItemStack stack : itemStack.getOrDefault(DataComponentsRegistrar.MOTH_CONTAINER, ContainerComponent.DEFAULT).iterateNonEmpty()) {
                if (stack.isIn(PrideMothsInitialize.MOTH_JARS) && stack.getItem() instanceof GlassJarItem jarItem) {
                    textConsumer.accept(Text.translatable("container.mothEnclosure.itemCount." +
                            GlassJarItem.getMothVariant(jarItem).getVariation()).formatted(Formatting.ITALIC, Formatting.GRAY));
                }
            }
        }
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return SLOT_OCCUPIED_PROPERTIES != null;
    }
    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextFloat() < 0.8F) {
            if (checkIfOnlyOneSlotIsFilled(state)) {
                if (state.isIn(PrideMothsInitialize.MOTH_ENCLOSURES, statex -> statex.contains(FUZZ_LEVEL))) {
                    int i = MothEnclosureBlockEntity.getMothFuzzLevel(state);
                    if (i < 1) {
                        int j = world.random.nextInt(100) == 0 ? 2 : 1;
                        if (i + j > 1) {
                            j--;
                        }

                        world.setBlockState(pos, state.with(FUZZ_LEVEL, i + j));
                    }
                }
            } else if (checkIfOnlyTwoSlotsAreFilled(state)) {
                if (state.isIn(PrideMothsInitialize.MOTH_ENCLOSURES, statex -> statex.contains(FUZZ_LEVEL))) {
                    int i = MothEnclosureBlockEntity.getMothFuzzLevel(state);
                    if (i < 2) {
                        int j = world.random.nextInt(100) == 0 ? 2 : 1;
                        if (i + j > 2) {
                            j--;
                        }

                        world.setBlockState(pos, state.with(FUZZ_LEVEL, i + j));
                    }
                }
            } else if (checkIfAllSlotsAreFilled(state)) {
                if (state.isIn(PrideMothsInitialize.MOTH_ENCLOSURES, statex -> statex.contains(FUZZ_LEVEL))) {
                    int i = MothEnclosureBlockEntity.getMothFuzzLevel(state);
                    if (i < 3) {
                        int j = world.random.nextInt(100) == 0 ? 2 : 1;
                        if (i + j > 3) {
                            j--;
                        }

                        world.setBlockState(pos, state.with(FUZZ_LEVEL, i + j));
                    }
                }
            }
        }

        super.randomTick(state, world, pos, random);
    }

    private static boolean checkIfOnlyOneSlotIsFilled(BlockState state) {
        if (state.get(Properties.SLOT_0_OCCUPIED) && !state.get(Properties.SLOT_1_OCCUPIED) && !state.get(Properties.SLOT_2_OCCUPIED))
            return true;
        if (!state.get(Properties.SLOT_0_OCCUPIED) && state.get(Properties.SLOT_1_OCCUPIED) && !state.get(Properties.SLOT_2_OCCUPIED))
            return true;
        return !state.get(Properties.SLOT_0_OCCUPIED) && !state.get(Properties.SLOT_1_OCCUPIED) && state.get(Properties.SLOT_2_OCCUPIED);
    }

    private static boolean checkIfOnlyTwoSlotsAreFilled(BlockState state) {
        if (state.get(Properties.SLOT_0_OCCUPIED) && state.get(Properties.SLOT_1_OCCUPIED) && !state.get(Properties.SLOT_2_OCCUPIED))
            return true;
        if (!state.get(Properties.SLOT_0_OCCUPIED) && state.get(Properties.SLOT_1_OCCUPIED) && state.get(Properties.SLOT_2_OCCUPIED))
            return true;
        return state.get(Properties.SLOT_0_OCCUPIED) && !state.get(Properties.SLOT_1_OCCUPIED) && state.get(Properties.SLOT_2_OCCUPIED);
    }

    private static boolean checkIfAllSlotsAreFilled(BlockState state) {
        return state.get(Properties.SLOT_0_OCCUPIED) && state.get(Properties.SLOT_1_OCCUPIED) && state.get(Properties.SLOT_2_OCCUPIED);
    }

    public static void dropMothFuzz(World world, BlockPos pos, BlockState state) {
        if (state.get(FUZZ_LEVEL) == 1) {
            dropStack(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ, 1));
        }
        if (state.get(FUZZ_LEVEL) == 2) {
            if (state.get(Properties.SLOT_0_OCCUPIED) && !state.get(Properties.SLOT_1_OCCUPIED) && !state.get(Properties.SLOT_2_OCCUPIED))
                dropStack(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ, 1));
            if (!state.get(Properties.SLOT_0_OCCUPIED) && state.get(Properties.SLOT_1_OCCUPIED) && !state.get(Properties.SLOT_2_OCCUPIED))
                dropStack(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ, 1));
            if (!state.get(Properties.SLOT_0_OCCUPIED) && !state.get(Properties.SLOT_1_OCCUPIED) && state.get(Properties.SLOT_2_OCCUPIED))
                dropStack(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ, 1));

            if (state.get(Properties.SLOT_0_OCCUPIED) && state.get(Properties.SLOT_1_OCCUPIED) && !state.get(Properties.SLOT_2_OCCUPIED))
                dropStack(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ, 2));
            if (!state.get(Properties.SLOT_0_OCCUPIED) && state.get(Properties.SLOT_1_OCCUPIED) && state.get(Properties.SLOT_2_OCCUPIED))
                dropStack(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ, 2));
            if (state.get(Properties.SLOT_0_OCCUPIED) && !state.get(Properties.SLOT_1_OCCUPIED) && state.get(Properties.SLOT_2_OCCUPIED))
                dropStack(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ, 2));

            if (state.get(Properties.SLOT_0_OCCUPIED) && state.get(Properties.SLOT_1_OCCUPIED) && state.get(Properties.SLOT_2_OCCUPIED))
                dropStack(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ, 2));
        }
        if (state.get(FUZZ_LEVEL) == 3) {
            if (state.get(Properties.SLOT_0_OCCUPIED) && !state.get(Properties.SLOT_1_OCCUPIED) && !state.get(Properties.SLOT_2_OCCUPIED))
                dropStack(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ, 1));
            if (!state.get(Properties.SLOT_0_OCCUPIED) && state.get(Properties.SLOT_1_OCCUPIED) && !state.get(Properties.SLOT_2_OCCUPIED))
                dropStack(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ, 1));
            if (!state.get(Properties.SLOT_0_OCCUPIED) && !state.get(Properties.SLOT_1_OCCUPIED) && state.get(Properties.SLOT_2_OCCUPIED))
                dropStack(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ, 1));

            if (state.get(Properties.SLOT_0_OCCUPIED) && state.get(Properties.SLOT_1_OCCUPIED) && !state.get(Properties.SLOT_2_OCCUPIED))
                dropStack(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ, 2));
            if (!state.get(Properties.SLOT_0_OCCUPIED) && state.get(Properties.SLOT_1_OCCUPIED) && state.get(Properties.SLOT_2_OCCUPIED))
                dropStack(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ, 2));
            if (state.get(Properties.SLOT_0_OCCUPIED) && !state.get(Properties.SLOT_1_OCCUPIED) && state.get(Properties.SLOT_2_OCCUPIED))
                dropStack(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ, 2));

            if (state.get(Properties.SLOT_0_OCCUPIED) && state.get(Properties.SLOT_1_OCCUPIED) && state.get(Properties.SLOT_2_OCCUPIED))
                dropStack(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ, 3));
        }
    }

    public void takeMothFuzz(World world, BlockState state, BlockPos pos) {
        world.setBlockState(pos, state.with(FUZZ_LEVEL, 0), Block.NOTIFY_ALL);
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof MothEnclosureBlockEntity mothEnclosureBlockEntity) {
            if (!stack.isIn(PrideMothsInitialize.MOTH_JARS)) {
                int i = state.get(FUZZ_LEVEL);
                boolean bl = false;
                if (i >= 1) {
                    if (stack.isIn(ConventionalItemTags.SHEAR_TOOLS)) {
                        world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        dropMothFuzz(world, pos, state);
                        stack.damage(1, player, LivingEntity.getSlotForHand(hand));
                        bl = true;
                        world.emitGameEvent(player, GameEvent.SHEAR, pos);
                    }
                }

                if (bl) {
                    this.takeMothFuzz(world, state, pos);

                    return ActionResult.SUCCESS;
                }

                return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
            } else {
                OptionalInt optionalInt = this.getSlotForHitPos(hit, state);
                if (optionalInt.isEmpty()) {
                    return ActionResult.PASS;
                } else if (state.get(SLOT_OCCUPIED_PROPERTIES.get(optionalInt.getAsInt()))) {
                    return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
                } else {
                    tryAddGlassJarWithMothInside(world, pos, player, mothEnclosureBlockEntity, stack, optionalInt.getAsInt());
                    return ActionResult.SUCCESS;
                }
            }
        } else {
            return ActionResult.PASS;
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof MothEnclosureBlockEntity mothEnclosureBlockEntity) {
            OptionalInt optionalInt = this.getSlotForHitPos(hit, state);
            if (optionalInt.isEmpty()) {
                return ActionResult.PASS;
            } else if (!(Boolean)state.get(SLOT_OCCUPIED_PROPERTIES.get(optionalInt.getAsInt()))) {
                return ActionResult.CONSUME;
            } else {
                if (!player.getMainHandStack().isOf(Items.SHEARS)) {
                    tryRemoveGlassJarWithMothInside(world, pos, player, mothEnclosureBlockEntity, optionalInt.getAsInt());
                }
                return ActionResult.SUCCESS;
            }
        } else {
            return ActionResult.PASS;
        }
    }

    private OptionalInt getSlotForHitPos(BlockHitResult hit, BlockState state) {
        return getHitPos(hit, state.get(HorizontalFacingBlock.FACING)).map(hitPos -> {
            //int i = 0;
            int j = getColumn(hitPos.x);
            return OptionalInt.of(j);
        }).orElseGet(OptionalInt::empty);
    }

    private static Optional<Vec2f> getHitPos(BlockHitResult hit, Direction facing) {
        Direction direction = hit.getSide();
        if (facing != direction) {
            return Optional.empty();
        } else {
            BlockPos blockPos = hit.getBlockPos().offset(direction);
            Vec3d vec3d = hit.getPos().subtract(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            double d = vec3d.getX();
            double e = vec3d.getY();
            double f = vec3d.getZ();

            return switch (direction) {
                case NORTH -> Optional.of(new Vec2f((float)(1.0 - d), (float)e));
                case SOUTH -> Optional.of(new Vec2f((float)d, (float)e));
                case WEST -> Optional.of(new Vec2f((float)f, (float)e));
                case EAST -> Optional.of(new Vec2f((float)(1.0 - f), (float)e));
                case DOWN, UP -> Optional.empty();
            };
        }
    }

    private static int getColumn(float x) {
        if (x < 0.375F) {
            return 0;
        } else {
            return x < 0.6875F ? 1 : 2;
        }
    }

    private static void tryAddGlassJarWithMothInside(World world, BlockPos pos, PlayerEntity player, MothEnclosureBlockEntity blockEntity, ItemStack stack, int slot) {
        if (!world.isClient) {
            player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
            SoundEvent soundEvent = SoundEvents.ITEM_BOTTLE_EMPTY;
            blockEntity.setStack(slot, stack.splitUnlessCreative(1, player));
            world.playSound(null, pos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    private static void tryRemoveGlassJarWithMothInside(World world, BlockPos pos, PlayerEntity player, MothEnclosureBlockEntity blockEntity, int slot) {
        if (!world.isClient) {
            ItemStack itemStack = blockEntity.removeStack(slot, 1);
            SoundEvent soundEvent = SoundEvents.ITEM_BOTTLE_FILL;
            world.playSound(null, pos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!player.getInventory().insertStack(itemStack)) {
                player.dropItem(itemStack, false);
            }

            world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        }
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient && world instanceof ServerWorld serverWorld
                //&& player.isCreative()
                && serverWorld.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)
                && world.getBlockEntity(pos) instanceof MothEnclosureBlockEntity mothEnclosureBlockEntity) {
            //int i = state.get(FUZZ_LEVEL);
            boolean slot0 = state.get(SLOT_OCCUPIED_PROPERTIES.getFirst());
            boolean slot1 = state.get(SLOT_OCCUPIED_PROPERTIES.get(1));
            boolean slot2 = state.get(SLOT_OCCUPIED_PROPERTIES.get(2));
            if (slot0 || slot1 || slot2) {
                ItemStack itemStack = new ItemStack(this);
                itemStack.applyComponentsFrom(mothEnclosureBlockEntity.createComponentMap());
                world.getBlockEntity(pos, BlockEntityTypeRegistrar.MOTH_ENCLOSURE_BLOCK_ENTITY).ifPresent(blockEntity -> blockEntity.readComponents(itemStack));
                itemStack.set(DataComponentTypes.BLOCK_STATE, BlockStateComponent.DEFAULT
                        .with(SLOT_OCCUPIED_PROPERTIES.getFirst(), slot0)
                        .with(SLOT_OCCUPIED_PROPERTIES.get(1), slot1)
                        .with(SLOT_OCCUPIED_PROPERTIES.get(2), slot2));
                if (mothEnclosureBlockEntity.hasCustomName()) {
                    itemStack.set(DataComponentTypes.CUSTOM_NAME, mothEnclosureBlockEntity.getCustomName());
                }

                ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, itemStack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            }
        }

        return super.onBreak(world, pos, state, player);
    }

    @Override
    protected void onStateReplaced(BlockState state, ServerWorld world, BlockPos pos, boolean moved) {
        ItemScatterer.onStateReplaced(state, world, pos);
    }

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state, boolean includeData) {
        ItemStack itemStack = super.getPickStack(world, pos, state, includeData);
        world.getBlockEntity(pos, BlockEntityTypeRegistrar.MOTH_ENCLOSURE_BLOCK_ENTITY).ifPresent(blockEntity -> blockEntity.readComponents(itemStack));

//        if (includeData) {
//            itemStack.set(DataComponentTypes.BLOCK_STATE, BlockStateComponent.DEFAULT.with(SLOT_OCCUPIED_PROPERTIES.getFirst(), state.get(SLOT_OCCUPIED_PROPERTIES.getFirst())));
//            itemStack.set(DataComponentTypes.BLOCK_STATE, BlockStateComponent.DEFAULT.with(SLOT_OCCUPIED_PROPERTIES.get(1), state.get(SLOT_OCCUPIED_PROPERTIES.get(1))));
//            itemStack.set(DataComponentTypes.BLOCK_STATE, BlockStateComponent.DEFAULT.with(SLOT_OCCUPIED_PROPERTIES.get(2), state.get(SLOT_OCCUPIED_PROPERTIES.get(2))));
//        }

        return itemStack;
    }

//    @Override
//    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
//        super.appendTooltip(stack, context, tooltip, options);
//
//        for (ItemStack itemStack : stack.getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).iterateNonEmpty()) {
//            if (itemStack.isIn(PrideMothsInitialize.MOTH_JARS) && itemStack.getItem() instanceof GlassJarItem jarItem) {
//                tooltip.add(Text.translatable("container.mothEnclosure.itemCount." + GlassJarItem.getMothVariant(jarItem).getVariation()).formatted(Formatting.ITALIC, Formatting.GRAY));
//            }
//        }
//    }

    @Override
    public MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MothEnclosureBlockEntity(pos, state);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, FUZZ_LEVEL);
        SLOT_OCCUPIED_PROPERTIES.forEach(builder::add);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    public static int getRedstoneAnalogOutput(BlockState state) {
        if (state.get(Properties.SLOT_0_OCCUPIED) && !state.get(Properties.SLOT_1_OCCUPIED) && !state.get(Properties.SLOT_2_OCCUPIED))
            return 1;
        if (!state.get(Properties.SLOT_0_OCCUPIED) && state.get(Properties.SLOT_1_OCCUPIED) && !state.get(Properties.SLOT_2_OCCUPIED))
            return 1;
        if (!state.get(Properties.SLOT_0_OCCUPIED) && !state.get(Properties.SLOT_1_OCCUPIED) && state.get(Properties.SLOT_2_OCCUPIED))
            return 1;

        if (state.get(Properties.SLOT_0_OCCUPIED) && state.get(Properties.SLOT_1_OCCUPIED) && !state.get(Properties.SLOT_2_OCCUPIED))
            return 2;
        if (!state.get(Properties.SLOT_0_OCCUPIED) && state.get(Properties.SLOT_1_OCCUPIED) && state.get(Properties.SLOT_2_OCCUPIED))
            return 2;
        if (state.get(Properties.SLOT_0_OCCUPIED) && !state.get(Properties.SLOT_1_OCCUPIED) && state.get(Properties.SLOT_2_OCCUPIED))
            return 2;

        if (state.get(Properties.SLOT_0_OCCUPIED) && state.get(Properties.SLOT_1_OCCUPIED) && state.get(Properties.SLOT_2_OCCUPIED))
            return 3;

        return 0;
    }

    @Override
    protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return getRedstoneAnalogOutput(state);
    }
}
