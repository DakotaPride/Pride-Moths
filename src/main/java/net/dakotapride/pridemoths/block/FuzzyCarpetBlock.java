package net.dakotapride.pridemoths.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.CarpetBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FuzzyCarpetBlock extends CarpetBlock {
    public FuzzyCarpetBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(Text.translatable("text.pridemoths.fuzzy_carpet.details").formatted(Formatting.ITALIC).formatted(Formatting.GRAY));
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        boolean isHostile = entity instanceof HostileEntity;
        if (isHostile) {
            ((HostileEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 600, 9));
        }
    }
}
