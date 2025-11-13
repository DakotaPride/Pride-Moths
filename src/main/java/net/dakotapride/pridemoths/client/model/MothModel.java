package net.dakotapride.pridemoths.client.model;

import net.dakotapride.pridemoths.PrideMothsInitialize;
import net.dakotapride.pridemoths.client.renderer.MothAnimations;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

// Made with Blockbench 5.0.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class MothModel extends EntityModel<MothRenderState> {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(Identifier.of(PrideMothsInitialize.MOD_ID, "moth"), "main");

	private final ModelPart body;

	private final Animation idlingAnimation;

	public MothModel(ModelPart root) {
        super(root);

        this.body = root.getChild("body");

		this.idlingAnimation = MothAnimations.ANIM_IDLE.createAnimation(root);
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 20.0F, -1.0F));

		ModelPartData antennae = body.addChild("antennae", ModelPartBuilder.create(), ModelTransform.origin(0.5F, 1.0F, 2.0F));

		ModelPartData right2 = antennae.addChild("right2", ModelPartBuilder.create().uv(0, 5).cuboid(0.0F, -5.0F, 0.0F, 3.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(1.5F, -3.0F, -6.0F, 0.0873F, 0.0F, 0.1309F));

		ModelPartData left2 = antennae.addChild("left2", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -5.0F, 0.0F, 3.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, -3.0F, -6.0F, 0.0873F, 0.0F, -0.1309F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 14).cuboid(-2.5F, -4.0F, -6.0F, 5.0F, 5.0F, 8.0F, new Dilation(0.0F)), ModelTransform.origin(0.5F, 1.0F, 2.0F));

		ModelPartData wings = body.addChild("wings", ModelPartBuilder.create(), ModelTransform.origin(0.5F, -3.0F, -2.0F));

		ModelPartData left = wings.addChild("left", ModelPartBuilder.create(), ModelTransform.origin(-0.5F, 0.0F, 0.0F));

		ModelPartData cube_r1 = left.addChild("cube_r1", ModelPartBuilder.create().uv(3, 4).cuboid(-8.0F, 0.0F, 0.0F, 8.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.2618F, -0.1309F, 0.0F));

		ModelPartData right = wings.addChild("right", ModelPartBuilder.create(), ModelTransform.origin(0.5F, 0.0F, 0.0F));

		ModelPartData cube_r2 = right.addChild("cube_r2", ModelPartBuilder.create().uv(3, 9).cuboid(0.0F, 0.0F, 0.0F, 8.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.2618F, 0.1309F, 0.0F));

		ModelPartData legs = body.addChild("legs", ModelPartBuilder.create().uv(18, 18).cuboid(-2.5F, 0.0F, -1.5F, 5.0F, 2.0F, 0.0F, new Dilation(0.0F))
		.uv(18, 16).cuboid(-2.5F, 0.0F, -0.5F, 5.0F, 2.0F, 0.0F, new Dilation(0.0F))
		.uv(18, 14).cuboid(-2.5F, 0.0F, 1.5F, 5.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(0.5F, 2.0F, -1.5F));

		ModelPartData fur = body.addChild("fur", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 4.0F, 1.0F));

		ModelPartData top = fur.addChild("top", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r3 = top.addChild("cube_r3", ModelPartBuilder.create().uv(2, 27).cuboid(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -7.0F, 1.0F, 0.3491F, 0.0F, 0.0F));

		ModelPartData cube_r4 = top.addChild("cube_r4", ModelPartBuilder.create().uv(7, 2).cuboid(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -7.0F, -4.0F, 0.3491F, 0.0F, 0.0F));

		ModelPartData cube_r5 = top.addChild("cube_r5", ModelPartBuilder.create().uv(7, 0).cuboid(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -7.0F, -2.0F, 0.3491F, 0.0F, 0.0F));

		ModelPartData right3 = fur.addChild("right3", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -3.0F, 0.0F));

		ModelPartData cube_r6 = right3.addChild("cube_r6", ModelPartBuilder.create().uv(0, 10).cuboid(0.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -1.5F, -4.0F, 0.0F, -1.2217F, 0.0F));

		ModelPartData cube_r7 = right3.addChild("cube_r7", ModelPartBuilder.create().uv(0, 27).cuboid(0.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -1.5F, 1.0F, 0.0F, -1.2217F, 0.0F));

		ModelPartData cube_r8 = right3.addChild("cube_r8", ModelPartBuilder.create().uv(0, 15).cuboid(0.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -1.5F, -2.0F, 0.0F, -1.2217F, 0.0F));

		ModelPartData left3 = fur.addChild("left3", ModelPartBuilder.create(), ModelTransform.of(-3.0F, -4.5F, -1.6667F, 0.0F, 0.0F, -3.1416F));

		ModelPartData cube_r9 = left3.addChild("cube_r9", ModelPartBuilder.create().uv(0, 10).cuboid(0.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 0.0F, -2.3333F, 0.0F, -1.2217F, 0.0F));

		ModelPartData cube_r10 = left3.addChild("cube_r10", ModelPartBuilder.create().uv(0, 27).cuboid(0.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 0.0F, 2.6667F, 0.0F, -1.2217F, 0.0F));

		ModelPartData cube_r11 = left3.addChild("cube_r11", ModelPartBuilder.create().uv(0, 15).cuboid(0.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 0.0F, -0.3333F, 0.0F, -1.2217F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(MothRenderState state) {
		super.setAngles(state);

		this.idlingAnimation.apply(state.idleAnimationState, state.age, 1f);
	}
}