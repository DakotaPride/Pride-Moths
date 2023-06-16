package net.dakotapride.pridemoths.client.model;

import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.renderer.MothRenderer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MothModel extends AnimatedGeoModel<MothEntity> {

    @Override
    public Identifier getModelResource(MothEntity entity) {
        return new Identifier("pridemoths", "geo/moth.geo.json");
    }

    @Override
    public Identifier getTextureResource(MothEntity entity) {
        return MothRenderer.LOCATION_BY_VARIANT.get(entity.getMothVariant());
    }

    @Override
    public Identifier getAnimationResource(MothEntity entity) {
        return new Identifier("pridemoths", "animations/moth.animation.json");
    }
}