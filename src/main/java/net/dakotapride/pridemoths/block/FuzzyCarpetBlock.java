package net.dakotapride.pridemoths.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.CarpetBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCollisionHandler;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FuzzyCarpetBlock extends CarpetBlock {
    public FuzzyCarpetBlock(Settings settings) {
        super(settings);
    }

//    @Override
//    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
//        tooltip.add(Text.translatable("text.pridemoths.fuzzy_carpet.details").formatted(Formatting.ITALIC).formatted(Formatting.GRAY));
//    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, EntityCollisionHandler collisionHandler) {
        boolean isHostile = entity instanceof HostileEntity;
        if (isHostile) {
            ((HostileEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 600, 9));
        }
    }
}
