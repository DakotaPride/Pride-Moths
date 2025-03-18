package net.dakotapride.pridemoths.client.model;

import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class MothModel extends GeoModel<MothEntity> {

    @Override
    public Identifier getModelResource(MothEntity entity, @Nullable GeoRenderer<MothEntity> renderer) {
        if (entity.isBaby()) {
            return Identifier.of("pridemoths", "geo/baby_moth.geo.json");
        }

        return Identifier.of("pridemoths", "geo/moth.geo.json");
    }

    @Override
    public Identifier getTextureResource(MothEntity entity, @Nullable GeoRenderer<MothEntity> renderer) {
        if (entity.isBaby()) {
            if (entity.getMothVariant() == MothVariation.RARE) {
                return Identifier.of("pridemoths", "textures/model/baby/rare.png");
            } else {
                return Identifier.of("pridemoths", "textures/model/baby/moth.png");
            }
        }

        if (entity.getMothVariant() == MothVariation.RARE && !entity.isBaby()) {
            return Identifier.of("pridemoths", "textures/model/rare.png");
        } else {
            return Identifier.of("pridemoths", "textures/model/moth.png");
        }
    }

    @Override
    public Identifier getAnimationResource(MothEntity entity) {
        if (entity.isBaby()) {
            return Identifier.of("pridemoths", "animations/baby_moth.animation.json");
        }

        return Identifier.of("pridemoths", "animations/moth.animation.json");
    }
}