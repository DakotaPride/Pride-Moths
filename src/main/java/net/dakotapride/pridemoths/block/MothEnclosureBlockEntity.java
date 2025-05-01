package net.dakotapride.pridemoths.block;

import net.dakotapride.pridemoths.PrideMothsInitialize;
import net.dakotapride.pridemoths.register.BlockEntityTypeRegistrar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EnchantingTableBlockEntity;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.Nameable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Predicate;

public class MothEnclosureBlockEntity extends BlockEntity implements Inventory, Nameable {
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
    private int lastInteractedSlot = -1;
    @Nullable
    private Text customName;

    public MothEnclosureBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypeRegistrar.MOTH_ENCLOSURE_BLOCK_ENTITY, pos, state);
    }

    public static int getMothFuzzLevel(BlockState state) {
        return state.get(MothEnclosureBlock.FUZZ_LEVEL);
    }

    public DefaultedList<ItemStack> getInventory() {
        return inventory;
    }

    private void updateState(int interactedSlot) {
        if (interactedSlot >= 0 && interactedSlot < 3) {
            this.lastInteractedSlot = interactedSlot;
            BlockState blockState = this.getCachedState();

            for (int i = 0; i < MothEnclosureBlock.SLOT_OCCUPIED_PROPERTIES.size(); i++) {
                boolean bl = !this.getStack(i).isEmpty();
                BooleanProperty booleanProperty = MothEnclosureBlock.SLOT_OCCUPIED_PROPERTIES.get(i);
                blockState = blockState.with(booleanProperty, bl);
            }

            (Objects.requireNonNull(this.world)).setBlockState(this.pos, blockState, Block.NOTIFY_ALL);
            this.world.emitGameEvent(GameEvent.BLOCK_CHANGE, this.pos, GameEvent.Emitter.of(blockState));
        }
    }

    public void addStack(ItemStack stack) {
        this.inventory.add(stack);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        //this.inventory.clear();
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory, registryLookup);

        this.lastInteractedSlot = nbt.getInt("last_interacted_slot", -1);
        if (nbt.contains("CustomName")) {
            //this.customName = tryParseCustomName(nbt.getString("CustomName"), registryLookup);
            this.customName = tryParseCustomName(nbt.get("CustomName"), registryLookup);
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, this.inventory, false, registryLookup);
        nbt.putInt("last_interacted_slot", this.lastInteractedSlot);
        if (this.hasCustomName()) {
            //nbt.putString("CustomName", Text.Serialization.toJsonString(this.customName, registryLookup));
            nbt.put("CustomName", TextCodecs.CODEC, registryLookup.getOps(NbtOps.INSTANCE), this.customName);
        }
    }

    public int getFilledSlotCount() {
        return (int)this.inventory.stream().filter(Predicate.not(ItemStack::isEmpty)).count();
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    @Override
    public int size() {
        return this.inventory.size();
    }


    @Override
    public boolean isEmpty() {
        return this.inventory.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack itemStack = Objects.requireNonNullElse(this.inventory.get(slot), ItemStack.EMPTY);
        this.inventory.set(slot, ItemStack.EMPTY);
        if (!itemStack.isEmpty()) {
            this.updateState(slot);
        }

        return itemStack;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return this.removeStack(slot, 1);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if (stack.isIn(PrideMothsInitialize.MOTH_JARS)) {
            this.inventory.set(slot, stack);
            this.updateState(slot);
        } else if (stack.isEmpty()) {
            this.removeStack(slot, 1);
        }
    }

    @Override
    public boolean canTransferTo(Inventory hopperInventory, int slot, ItemStack stack) {
        return hopperInventory.containsAny(
                stack2 -> stack2.isEmpty() || ItemStack.areItemsAndComponentsEqual(stack, stack2) && stack2.getCount() + stack.getCount() <= hopperInventory.getMaxCount(stack2)
        );
    }

    @Override
    public int getMaxCountPerStack() {
        return 1;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return Inventory.canPlayerUse(this, player);
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        return stack.isIn(PrideMothsInitialize.MOTH_JARS) && this.getStack(slot).isEmpty() && stack.getCount() == this.getMaxCountPerStack();
    }

    public int getLastInteractedSlot() {
        return this.lastInteractedSlot;
    }

    @Override
    protected void readComponents(ComponentsAccess components) {
        super.readComponents(components);
        this.customName = components.get(DataComponentTypes.CUSTOM_NAME);
        components.getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).copyTo(this.inventory);
    }

    @Override
    protected void addComponents(ComponentMap.Builder componentMapBuilder) {
        super.addComponents(componentMapBuilder);
        componentMapBuilder.add(DataComponentTypes.CUSTOM_NAME, this.customName);
        componentMapBuilder.add(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(this.inventory));
    }

    @Override
    public void removeFromCopiedStackNbt(NbtCompound nbt) {
        nbt.remove("CustomName");
        nbt.remove("Items");
    }

//    @Nullable
//    @Override
//    public Packet<ClientPlayPacketListener> toUpdatePacket() {
//        return BlockEntityUpdateS2CPacket.create(this);
//    }

//    @Override
//    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
//        return createNbt(registryLookup);
//    }


    @Override
    public Text getName() {
        return this.customName != null ? this.customName : this.getDisplayName();
    }

    @Override
    public Text getDisplayName() {
        return this.getName();
    }

    @Nullable
    @Override
    public Text getCustomName() {
        return this.customName;
    }
}
