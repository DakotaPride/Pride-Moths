package net.dakotapride.pridemoths.client.entity;

import net.dakotapride.pridemoths.PrideMothsInitialize;
import net.minecraft.block.Blocks;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.NotNull;

public class TravelToLightSourceGoal extends MoveToTargetPosGoal {
    private final MothEntity moth;

    public TravelToLightSourceGoal(MothEntity entity, int range) {
        super(entity, 1.0F, range, range);
        this.moth = entity;
    }

    protected int getInterval(PathAwareEntity mob) {
        return toGoalTicks(50 + moth.getRandom().nextInt(50));
    }

    @Override
    public boolean canStart() {
        return this.moth.lightPos == null && super.canStart() && !isTargetBlocked(targetPos.toCenterPos());
    }

    public boolean isTargetBlocked(Vec3d target) {
        Vec3d Vector3d = new Vec3d(mob.getX(), mob.getEyeY(), mob.getZ());
        return mob.getWorld().raycast(new RaycastContext(Vector3d, target, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, mob)).getType() != HitResult.Type.MISS;
    }

    @Override
    public boolean shouldContinue() {
        return super.shouldContinue() && this.moth.lightPos == null;
    }

    public double getDesiredDistanceToTarget() {
        return moth.getWidth() + 1;
    }

    @Override
    public void tick() {
        super.tick();
        BlockPos target = getTargetPos();
        if (target != null) {
            moth.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, Vec3d.ofCenter(target));
            if (this.hasReached()) {
                moth.lightPos = targetPos;
            }
        }
    }


    public void start() {
        //moth.setFlying(true);
        super.start();
    }

    public void stop() {
        super.stop();
    }

    protected @NotNull BlockPos getTargetPos() {
        return this.targetPos;
    }

    @Override
    protected boolean isTargetPos(WorldView worldIn, BlockPos pos) {
        return pos != null &&
                worldIn.getBlockState(pos).isIn(PrideMothsInitialize.LIGHT_SOURCES_TAG) &&
                worldIn.getLightLevel(pos) > 0 && worldIn instanceof ServerWorld;
    }
}