package net.dakotapride.pridemoths.client.renderer;

import com.google.common.collect.Maps;
import net.dakotapride.pridemoths.PrideMothsInitialize;
import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.dakotapride.pridemoths.client.model.MothModel;
import net.dakotapride.pridemoths.client.model.MothRenderState;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class MothRenderer extends MobEntityRenderer<MothEntity, MothRenderState, MothModel> {
    public static final Map<MothVariation, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(MothVariation.class), (map) -> {
                map.put(MothVariation.DEFAULT, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/moth.png"));
                map.put(MothVariation.RARE, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/rare.png"));

                map.put(MothVariation.AGENDER, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/agender.png"));
                map.put(MothVariation.AROACE, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/aroace.png"));
                map.put(MothVariation.AROMANTIC, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/aromantic.png"));
                map.put(MothVariation.ASEXUAL, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/asexual.png"));
                map.put(MothVariation.BISEXUAL, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/bisexual.png"));
                map.put(MothVariation.DEMIBOY, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/demiboy.png"));
                map.put(MothVariation.DEMIGENDER, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/demigender.png"));
                map.put(MothVariation.DEMIGIRL, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/demigirl.png"));
                map.put(MothVariation.DEMIROMANTIC, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/demiromantic.png"));
                map.put(MothVariation.DEMISEXUAL, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/demisexual.png"));
                map.put(MothVariation.GAY, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/gay.png"));
                map.put(MothVariation.LESBIAN, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/lesbian.png"));
                map.put(MothVariation.LGBT, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/lgbt.png"));
                map.put(MothVariation.NON_BINARY, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/non_binary.png"));
                map.put(MothVariation.OMNISEXUAL, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/omnisexual.png"));
                map.put(MothVariation.PANSEXUAL, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/pansexual.png"));
                map.put(MothVariation.POLYAMOROUS, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/polyamorous.png"));
                map.put(MothVariation.POLYSEXUAL, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/polysexual.png"));
                map.put(MothVariation.TRANSGENDER, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/transgender.png"));
                map.put(MothVariation.GENDERFLUID, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/genderfluid.png"));
                map.put(MothVariation.INTERSEX, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/intersex.png"));
                map.put(MothVariation.XENOGENDER, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/xenogender.png"));
                map.put(MothVariation.GENDER_QUEER, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/gender_queer.png"));
                map.put(MothVariation.GENDERFAE, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/genderfae.png"));
                map.put(MothVariation.GENDERFAUN, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/genderfaun.png"));
                map.put(MothVariation.BIGENDER, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/bigender.png"));
                map.put(MothVariation.PANGENDER, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/pangender.png"));
                map.put(MothVariation.ALLY, Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/pride/ally.png"));
            });

    public MothRenderer(EntityRendererFactory.Context context) {
        super(context, new MothModel(context.getPart(MothModel.LAYER_LOCATION)), 0.25f);
    }

    //    @Override
//    public void updateRenderState(MothEntity entity, MothRenderState entityRenderState, float partialTick) {
//        entityRenderState.variation = entity.getMothVariant();
//        entityRenderState.baby = entity.isBaby();
//    }

    @Override
    public void render(MothRenderState state, MatrixStack matrixStack,
                       OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState) {
        if(state.baby) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f);
        }

        super.render(state, matrixStack, orderedRenderCommandQueue, cameraRenderState);
    }

    @Override
    public Identifier getTexture(MothRenderState state) {
        return LOCATION_BY_VARIANT.get(state.variant);
    }

    @Override
    public MothRenderState createRenderState() {
        return new MothRenderState();
    }

    @Override
    public void updateRenderState(MothEntity livingEntity, MothRenderState livingEntityRenderState, float f) {
        super.updateRenderState(livingEntity, livingEntityRenderState, f);
        livingEntityRenderState.idleAnimationState.copyFrom(livingEntity.idleAnimationState);
        livingEntityRenderState.variant = livingEntity.getMothVariant();
    }
}