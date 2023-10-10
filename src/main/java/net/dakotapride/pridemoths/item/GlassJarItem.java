package net.dakotapride.pridemoths.item;

import net.dakotapride.pridemoths.PrideMothsInitialize;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GlassJarItem extends Item {
    public GlassJarItem(Settings settings) {
        super(settings);
    }

    public static MothVariation getMothVariant(Item item) {
        MothVariation variant = null;
        ItemStack stack = item.getDefaultStack();

        if (stack.isOf(PrideMothsInitialize.MOTH_JAR)) {
            variant = MothVariation.DEFAULT;
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
        }

        return variant;
    }

    @Override
    public String getTranslationKey() {
        return "item.pridemoths.glass_jar";
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("text.pridemoths.net.description"));

        if (!stack.isOf(PrideMothsInitialize.GLASS_JAR)) {
            tooltip.add(Text.translatable(getMothVariant(stack.getItem()).getVariation()));
        }
    }
}
