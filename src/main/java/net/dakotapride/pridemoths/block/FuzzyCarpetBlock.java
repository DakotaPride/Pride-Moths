package net.dakotapride.pridemoths.block;

import net.dakotapride.pridemoths.PrideMothsInitialize;
import net.dakotapride.pridemoths.register.BlocksRegistrar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CarpetBlock;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCollisionHandler;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Consumer;

public class FuzzyCarpetBlock extends CarpetBlock {
    public FuzzyCarpetBlock(Settings settings) {
        super(settings);
    }

//    @Override
//    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
//        tooltip.add(Text.translatable("text.pridemoths.fuzzy_carpet.details").formatted(Formatting.ITALIC).formatted(Formatting.GRAY));
//    }


    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, EntityCollisionHandler handler, boolean bl) {
        boolean isHostile = entity instanceof HostileEntity;
        if (isHostile) {
            ((HostileEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 600, 9));
        }
    }

    public static class FuzzyCarpetBlockItem extends BlockItem {
        public FuzzyCarpetBlockItem(Block block, Settings settings) {
            super(block, settings);
        }

        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
            textConsumer.accept(Text.translatable("text.pridemoths.fuzzy_carpet.details").formatted(Formatting.ITALIC).formatted(Formatting.GRAY));
        }
    }
}
