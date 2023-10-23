package net.dakotapride.pridemoths.client.entity;

import net.dakotapride.pridemoths.PrideMothsInitialize;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class TravelToLightSourceGoal extends MoveToTargetPosGoal {
    private final MothEntity moth;

    TravelToLightSourceGoal(MothEntity moth, int range) {
        super(moth, 1.0F, range, range);
        this.moth = moth;
    }

    @Override
    public BlockPos getTargetPos() {
        return this.targetPos;
    }

    @Override
    public boolean shouldContinue() {
        return this.isTargetPos(this.moth.getWorld(), this.targetPos);
    }

    @Override
    public boolean canStart() {
        return super.canStart();
    }

    @Override
    public boolean shouldResetPath() {
        return this.tryingTime % 20 == 0;
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        return world.getBlockState(pos).isIn(PrideMothsInitialize.LIGHT_SOURCES_TAG);
    }
}
