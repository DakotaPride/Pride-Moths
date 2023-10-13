package net.dakotapride.pridemoths.client.model;

import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class MothModel extends GeoModel<MothEntity> {

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
            return new Identifier("pridemoths", "textures/model/baby/moth.png");
        }

        return new Identifier("pridemoths", "textures/model/moth.png");
    }

    @Override
    public Identifier getAnimationResource(MothEntity entity) {
        if (entity.isBaby()) {
            return new Identifier("pridemoths", "animations/baby_moth.animation.json");
        }

        return new Identifier("pridemoths", "animations/moth.animation.json");
    }
}