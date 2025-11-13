package net.dakotapride.pridemoths.client.model;

import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;

public class MothRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnimationState = new AnimationState();
    public MothVariation variant;
}