package net.dakotapride.pridemoths.client.model;

import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MothModel extends AnimatedGeoModel<MothEntity> {

    @Override
    public Identifier getModelResource(MothEntity entity) {
        if (entity.isBaby()) {
            return new Identifier("pridemoths", "geo/baby_moth.geo.json");
        }

        return new Identifier("pridemoths", "geo/moth.geo.json");
    }

    @Override
    public Identifier getTextureResource(MothEntity entity) {
        if (entity.isBaby()) {
            if (entity.getMothVariant() == MothVariation.RARE) {
                return new Identifier("pridemoths", "textures/model/baby/rare.png");
            } else {
                return new Identifier("pridemoths", "textures/model/baby/moth.png");
            }
        }

        if (entity.getMothVariant() == MothVariation.RARE && !entity.isBaby()) {
            return new Identifier("pridemoths", "textures/model/rare.png");
        } else {
            return new Identifier("pridemoths", "textures/model/moth.png");
        }
    }

    @Override
    public Identifier getAnimationResource(MothEntity entity) {
        if (entity.isBaby()) {
            return new Identifier("pridemoths", "animations/baby_moth.animation.json");
        }

        return new Identifier("pridemoths", "animations/moth.animation.json");
    }
}