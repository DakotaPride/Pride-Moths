package net.dakotapride.pridemoths.client.model;

import net.dakotapride.pridemoths.PrideMothsInitialize;
import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class MothModel extends GeoModel<MothEntity> {

    MothVariation variation;
    boolean baby;

    @Override
    public void addAdditionalStateData(MothEntity animatable, GeoRenderState renderState) {
        super.addAdditionalStateData(animatable, renderState);
        variation = animatable.getMothVariant();
        baby = animatable.isBaby();
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        if (baby)
            return Identifier.of(PrideMothsInitialize.MOD_ID, "baby_moth");
        return Identifier.of(PrideMothsInitialize.MOD_ID, "moth");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        if (baby) {
            if (variation == MothVariation.RARE) {
                return Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/baby/rare.png");
            } else {
                return Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/baby/moth.png");
            }
        }

        if (variation == MothVariation.RARE) {
            return Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/rare.png");
        } else {
            return Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/moth.png");
        }
        //return Identifier.of(PrideMothsInitialize.MOD_ID, "textures/model/moth.png");
    }

    @Override
    public Identifier getAnimationResource(MothEntity entity) {
        if (entity.isBaby()) {
            return Identifier.of(PrideMothsInitialize.MOD_ID, "baby_moth");
        }

        return Identifier.of(PrideMothsInitialize.MOD_ID, "moth");
    }
}