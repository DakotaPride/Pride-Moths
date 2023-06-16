package net.dakotapride.pridemoths.item;

import net.dakotapride.pridemoths.PrideMothsInitialize;
import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.entity.MothVariant;
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
        super(settings);
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

    public static MothVariant getMothVariant(Item item) {
        MothVariant variant = null;

        if (item == PrideMothsInitialize.MOTH_BOTTLE) {
            variant = MothVariant.DEFAULT;
        } else if (item == PrideMothsInitialize.TRANS_MOTH_BOTTLE) {
            variant = MothVariant.TRANS;
        } else if (item == PrideMothsInitialize.LGBT_MOTH_BOTTLE) {
            variant = MothVariant.LGBT;
        } else if (item == PrideMothsInitialize.NON_BINARY_MOTH_BOTTLE) {
            variant = MothVariant.NON_BINARY;
        } else if (item == PrideMothsInitialize.LESBIAN_MOTH_BOTTLE) {
            variant = MothVariant.LESBIAN;
        } else if (item == PrideMothsInitialize.GAY_MOTH_BOTTLE) {
            variant = MothVariant.GAY;
        } else if (item == PrideMothsInitialize.AGENDER_MOTH_BOTTLE) {
            variant = MothVariant.AGENDER;
        } else if (item == PrideMothsInitialize.ASEXUAL_MOTH_BOTTLE) {
            variant = MothVariant.ASEXUAL;
        } else if (item == PrideMothsInitialize.PANSEXUAL_MOTH_BOTTLE) {
            variant = MothVariant.PANSEXUAL;
        } else if (item == PrideMothsInitialize.BISEXUAL_MOTH_BOTTLE) {
            variant = MothVariant.BISEXUAL;
        }

        return variant;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext ctx) {
        MothVariant variant = getMothVariant(ctx.getStack().getItem());
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

            ctx.getWorld().playSound(ctx.getPlayer(), ctx.getBlockPos(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0f, 1.4f);
            ctx.getWorld().spawnEntity(moth);

            if (ctx.getPlayer() != null && !ctx.getPlayer().getAbilities().creativeMode) {
                ctx.getPlayer().setStackInHand(ctx.getHand(), new ItemStack(Items.GLASS_BOTTLE));
            }

            return ActionResult.SUCCESS;
        }

        return super.useOnBlock(ctx);
    }
}