package net.dakotapride.pridemoths.client.renderer;

import com.google.common.collect.Maps;
import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.dakotapride.pridemoths.client.model.MothModel;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

public class MothRenderer extends GeoEntityRenderer<MothEntity> {
    public static final Map<MothVariation, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(MothVariation.class), (map) -> {
                map.put(MothVariation.DEFAULT, new Identifier("pridemoths", "textures/model/moth.png"));
                map.put(MothVariation.RARE, new Identifier("pridemoths", "textures/model/rare.png"));

                map.put(MothVariation.AGENDER, new Identifier("pridemoths", "textures/model/pride/agender.png"));
                map.put(MothVariation.AROACE, new Identifier("pridemoths", "textures/model/pride/aroace.png"));
                map.put(MothVariation.AROMANTIC, new Identifier("pridemoths", "textures/model/pride/aromantic.png"));
                map.put(MothVariation.ASEXUAL, new Identifier("pridemoths", "textures/model/pride/asexual.png"));
                map.put(MothVariation.BISEXUAL, new Identifier("pridemoths", "textures/model/pride/bisexual.png"));
                map.put(MothVariation.DEMIBOY, new Identifier("pridemoths", "textures/model/pride/demiboy.png"));
                map.put(MothVariation.DEMIGENDER, new Identifier("pridemoths", "textures/model/pride/demigender.png"));
                map.put(MothVariation.DEMIGIRL, new Identifier("pridemoths", "textures/model/pride/demigirl.png"));
                map.put(MothVariation.DEMIROMANTIC, new Identifier("pridemoths", "textures/model/pride/demiromantic.png"));
                map.put(MothVariation.DEMISEXUAL, new Identifier("pridemoths", "textures/model/pride/demisexual.png"));
                map.put(MothVariation.GAY, new Identifier("pridemoths", "textures/model/pride/gay.png"));
                map.put(MothVariation.LESBIAN, new Identifier("pridemoths", "textures/model/pride/lesbian.png"));
                map.put(MothVariation.LGBT, new Identifier("pridemoths", "textures/model/pride/lgbt.png"));
                map.put(MothVariation.NON_BINARY, new Identifier("pridemoths", "textures/model/pride/non_binary.png"));
                map.put(MothVariation.OMNISEXUAL, new Identifier("pridemoths", "textures/model/pride/omnisexual.png"));
                map.put(MothVariation.PANSEXUAL, new Identifier("pridemoths", "textures/model/pride/pansexual.png"));
                map.put(MothVariation.POLYAMOROUS, new Identifier("pridemoths", "textures/model/pride/polyamorous.png"));
                map.put(MothVariation.POLYSEXUAL, new Identifier("pridemoths", "textures/model/pride/polysexual.png"));
                map.put(MothVariation.TRANSGENDER, new Identifier("pridemoths", "textures/model/pride/transgender.png"));
                map.put(MothVariation.ALLY, new Identifier("pridemoths", "textures/model/pride/ally.png"));
            });

    public MothRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new MothModel());
    }

    @Override
    public Identifier getTextureLocation(MothEntity entity) {
        if (entity.isBaby()) {
            if (entity.getMothVariant() == MothVariation.RARE) {
                return new Identifier("pridemoths", "textures/model/baby/rare.png");
            } else {
                return new Identifier("pridemoths", "textures/model/baby/moth.png");
            }
        }

        return LOCATION_BY_VARIANT.get(entity.getMothVariant());
    }
}