package net.dakotapride.pridemoths.item;

import net.dakotapride.pridemoths.PrideMothsInitialize;
import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MothBottleItem extends Item {
    public final Identifier texture;
    private final String variantByString;

    public MothBottleItem(String variantByString, Settings settings, Identifier texture) {
        super(settings.maxCount(1));
        this.texture = texture;
        this.variantByString = variantByString;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("text.pridemoths.variant." + getVariantByString()).formatted(Formatting.GRAY));
    }

    private String getVariantByString() {
        return variantByString;
    }

    public static MothEntity.MothVaration getMothVariant(Item item) {
        MothEntity.MothVaration variant = null;

        if (item == PrideMothsInitialize.ORANGE_MOTH_BOTTLE) {
            variant = MothEntity.MothVaration.DEFAULT;
        } else if (item == PrideMothsInitialize.BLUE_MOTH_BOTTLE) {
            variant = MothEntity.MothVaration.BLUE;
        } else if (item == PrideMothsInitialize.YELLOW_MOTH_BOTTLE) {
            variant = MothEntity.MothVaration.YELLOW;
        } else if (item == PrideMothsInitialize.GREEN_MOTH_BOTTLE) {
            variant = MothEntity.MothVaration.GREEN;
        } else if (item == PrideMothsInitialize.RED_MOTH_BOTTLE) {
            variant = MothEntity.MothVaration.RED;
        } else if (item == PrideMothsInitialize.PALOS_VERDES_BLUE_MOTH_BOTTLE) {
            variant = MothEntity.MothVaration.PALOS_VERDES_BLUE;
        } else if (item == PrideMothsInitialize.TRANS_MOTH_BOTTLE) {
            variant = MothEntity.MothVaration.TRANS;
        } else if (item == PrideMothsInitialize.LGBT_MOTH_BOTTLE) {
            variant = MothEntity.MothVaration.LGBT;
        } else if (item == PrideMothsInitialize.NON_BINARY_MOTH_BOTTLE) {
            variant = MothEntity.MothVaration.NON_BINARY;
        } else if (item == PrideMothsInitialize.LESBIAN_MOTH_BOTTLE) {
            variant = MothEntity.MothVaration.LESBIAN;
        } else if (item == PrideMothsInitialize.GAY_MOTH_BOTTLE) {
            variant = MothEntity.MothVaration.GAY;
        } else if (item == PrideMothsInitialize.AGENDER_MOTH_BOTTLE) {
            variant = MothEntity.MothVaration.AGENDER;
        } else if (item == PrideMothsInitialize.ASEXUAL_MOTH_BOTTLE) {
            variant = MothEntity.MothVaration.ASEXUAL;
        } else if (item == PrideMothsInitialize.PANSEXUAL_MOTH_BOTTLE) {
            variant = MothEntity.MothVaration.PANSEXUAL;
        } else if (item == PrideMothsInitialize.BISEXUAL_MOTH_BOTTLE) {
            variant = MothEntity.MothVaration.BISEXUAL;
        } else if (item == PrideMothsInitialize.POLYAMOROUS_MOTH_BOTTLE) {
            variant = MothEntity.MothVaration.POLYAMOROUS;
        } else if (item == PrideMothsInitialize.POLYSEXUAL_MOTH_BOTTLE) {
            variant = MothEntity.MothVaration.POLYSEXUAL;
        } else if (item == PrideMothsInitialize.OMNISEXUAL_MOTH_BOTTLE) {
            variant = MothEntity.MothVaration.OMNISEXUAL;
        } else if (item == PrideMothsInitialize.DEMISEXUAL_MOTH_BOTTLE) {
            variant = MothEntity.MothVaration.DEMISEXUAL;
        } else if (item == PrideMothsInitialize.DEMIROMANTIC_MOTH_BOTTLE) {
            variant = MothEntity.MothVaration.DEMIROMANTIC;
        } else if (item == PrideMothsInitialize.DEMIBOY_MOTH_BOTTLE) {
            variant = MothEntity.MothVaration.DEMIBOY;
        } else if (item == PrideMothsInitialize.DEMIGIRL_MOTH_BOTTLE) {
            variant = MothEntity.MothVaration.DEMIGIRL;
        } else if (item == PrideMothsInitialize.DEMIGENDER_MOTH_BOTTLE) {
            variant = MothEntity.MothVaration.DEMIGENDER;
        } else if (item == PrideMothsInitialize.AROACE_MOTH_BOTTLE) {
            variant = MothEntity.MothVaration.AROACE;
        } else if (item == PrideMothsInitialize.AROMANTIC_MOTH_BOTLE) {
            variant = MothEntity.MothVaration.AROMANTIC;
        }

        return variant;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext ctx) {
        MothEntity.MothVaration variant = getMothVariant(ctx.getStack().getItem());
        if (variant != null && ctx.getPlayer() != null && ctx.getPlayer().isSneaking()) {
            MothEntity moth = new MothEntity(PrideMothsInitialize.MOTH, ctx.getWorld());

            BlockHitResult blockHitResult = BucketItem.raycast(ctx.getWorld(), ctx.getPlayer(), RaycastContext.FluidHandling.SOURCE_ONLY);
            BlockPos blockPos = blockHitResult.getBlockPos();
            Direction direction = blockHitResult.getSide();
            BlockPos blockPos2 = blockPos.offset(direction);

            moth.setPosition(blockPos2.getX() + .5f, blockPos2.getY(), blockPos2.getZ() + .5f);
            moth.setMothVariant(variant);
            moth.fromBottle = true;

            if (ctx.getStack().hasCustomName()) {
                moth.setCustomName(ctx.getStack().getName());
            }

            ctx.getWorld().playSound(ctx.getPlayer(), ctx.getBlockPos(), SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.NEUTRAL, 1.0f, 1.4f);
            ctx.getWorld().spawnEntity(moth);

            if (ctx.getPlayer() != null && !ctx.getPlayer().getAbilities().creativeMode) {
                ctx.getPlayer().getStackInHand(ctx.getHand()).decrement(1);

                ctx.getPlayer().giveItemStack(new ItemStack(Items.GLASS_BOTTLE, 1));
            }

            return ActionResult.SUCCESS;
        }

        return super.useOnBlock(ctx);
    }
}