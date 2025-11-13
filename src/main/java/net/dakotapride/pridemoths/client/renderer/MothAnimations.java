package net.dakotapride.pridemoths.client.renderer;

// Save this class in your mod and generate all required imports
import net.minecraft.client.render.entity.animation.*;

/**
 * Made with Blockbench 5.0.4
 * Exported for Minecraft version 1.19 or later with Mojang mappings
 * @author Author
 */
public class MothAnimations {
	public static final AnimationDefinition ANIM_IDLE = AnimationDefinition.Builder.create(3.0F).looping()
		.addBoneAnimation("body", new Transformation(Transformation.Targets.MOVE_ORIGIN,
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("right2", new Transformation(Transformation.Targets.ROTATE,
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(2.5F, 0.0F, 5.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.5F, AnimationHelper.createRotationalVector(5.8278F, -0.254F, 4.1537F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("left2", new Transformation(Transformation.Targets.ROTATE,
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(2.5F, 0.0F, -2.5F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.5F, AnimationHelper.createRotationalVector(5.8113F, 0.5075F, -5.8076F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("left", new Transformation(Transformation.Targets.ROTATE,
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 20.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 20.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 20.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("right", new Transformation(Transformation.Targets.ROTATE,
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -20.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -20.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -20.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("legs", new Transformation(Transformation.Targets.ROTATE,
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(5.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.build();
}