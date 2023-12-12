package net.dakotapride.pridemoths.item;

import net.dakotapride.pridemoths.PrideMothsInitialize;
import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GlassJarItem extends Item {
    public GlassJarItem(boolean i, Settings settings) {
        super(settings.maxCount(16));
    }

    public GlassJarItem(Settings settings) {
        super(settings.maxCount(1));
    }

    public static MothVariation getMothVariant(Item item) {
        MothVariation variant = null;
        ItemStack stack = item.getDefaultStack();

        if (stack.isOf(PrideMothsInitialize.MOTH_JAR)) {
            variant = MothVariation.DEFAULT;
        } else if (stack.isOf(PrideMothsInitialize.RARE_MOTH_JAR)) {
            variant = MothVariation.RARE;
        } else if (stack.isOf(PrideMothsInitialize.AGENDER_MOTH_JAR)) {
            variant = MothVariation.AGENDER;
        } else if (stack.isOf(PrideMothsInitialize.AROACE_MOTH_JAR)) {
            variant = MothVariation.AROACE;
        } else if (stack.isOf(PrideMothsInitialize.AROMANTIC_MOTH_JAR)) {
            variant = MothVariation.AROMANTIC;
        } else if (stack.isOf(PrideMothsInitialize.ASEXUAL_MOTH_JAR)) {
            variant = MothVariation.ASEXUAL;
        } else if (stack.isOf(PrideMothsInitialize.BISEXUAL_MOTH_JAR)) {
            variant = MothVariation.BISEXUAL;
        } else if (stack.isOf(PrideMothsInitialize.DEMIBOY_MOTH_JAR)) {
            variant = MothVariation.DEMIBOY;
        } else if (stack.isOf(PrideMothsInitialize.DEMIGENDER_MOTH_JAR)) {
            variant = MothVariation.DEMIGENDER;
        } else if (stack.isOf(PrideMothsInitialize.DEMIGIRL_MOTH_JAR)) {
            variant = MothVariation.DEMIGIRL;
        } else if (stack.isOf(PrideMothsInitialize.DEMIROMANTIC_MOTH_JAR)) {
            variant = MothVariation.DEMIROMANTIC;
        } else if (stack.isOf(PrideMothsInitialize.DEMISEXUAL_MOTH_JAR)) {
            variant = MothVariation.DEMISEXUAL;
        } else if (stack.isOf(PrideMothsInitialize.GAY_MOTH_JAR)) {
            variant = MothVariation.GAY;
        } else if (stack.isOf(PrideMothsInitialize.LESBIAN_MOTH_JAR)) {
            variant = MothVariation.LESBIAN;
        } else if (stack.isOf(PrideMothsInitialize.LGBT_MOTH_JAR)) {
            variant = MothVariation.LGBT;
        } else if (stack.isOf(PrideMothsInitialize.NON_BINARY_MOTH_JAR)) {
            variant = MothVariation.NON_BINARY;
        } else if (stack.isOf(PrideMothsInitialize.OMNISEXUAL_MOTH_JAR)) {
            variant = MothVariation.OMNISEXUAL;
        } else if (stack.isOf(PrideMothsInitialize.PANSEXUAL_MOTH_JAR)) {
            variant = MothVariation.PANSEXUAL;
        } else if (stack.isOf(PrideMothsInitialize.POLYAMOROUS_MOTH_JAR)) {
            variant = MothVariation.POLYAMOROUS;
        } else if (stack.isOf(PrideMothsInitialize.POLYSEXUAL_MOTH_JAR)) {
            variant = MothVariation.POLYSEXUAL;
        } else if (stack.isOf(PrideMothsInitialize.TRANSGENDER_MOTH_JAR)) {
            variant = MothVariation.TRANSGENDER;
        } else if (stack.isOf(PrideMothsInitialize.ALLY_MOTH_JAR)) {
            variant = MothVariation.ALLY;
        }

        return variant;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        MothVariation variation = getMothVariant(context.getStack().getItem());
        if (variation != null && context.getPlayer() != null && context.getPlayer().isSneaking()) {
            MothEntity moth = new MothEntity(PrideMothsInitialize.MOTH, context.getWorld());

            BlockHitResult blockHitResult = BucketItem.raycast(context.getWorld(), context.getPlayer(), RaycastContext.FluidHandling.SOURCE_ONLY);
            BlockPos blockPos = blockHitResult.getBlockPos();
            Direction direction = blockHitResult.getSide();
            BlockPos blockPos2 = blockPos.offset(direction);

            moth.setPosition(blockPos2.getX() + .5f, blockPos2.getY(), blockPos2.getZ() + .5f);
            moth.setMothVariant(variation);
            moth.fromJar = true;

            if (context.getStack().hasCustomName()) {
                moth.setCustomName(context.getStack().getName());
            }

            context.getWorld().playSound(context.getPlayer(), context.getBlockPos(), SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.NEUTRAL, 1.0f, 1.4f);
            context.getWorld().spawnEntity(moth);

            if (context.getPlayer() != null && !context.getPlayer().getAbilities().creativeMode) {
                context.getPlayer().setStackInHand(context.getHand(), new ItemStack(PrideMothsInitialize.GLASS_JAR));
            }

            return ActionResult.SUCCESS;
        }

        return super.useOnBlock(context);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("text.pridemoths.jar.details").formatted(Formatting.ITALIC).formatted(Formatting.GRAY));

        if (!stack.isOf(PrideMothsInitialize.GLASS_JAR)) {
            tooltip.add(Text.translatable("text.pridemoths.jar." + getMothVariant(stack.getItem()).getVariation()).formatted(Formatting.ITALIC).formatted(Formatting.GRAY));
        }
    }
}
