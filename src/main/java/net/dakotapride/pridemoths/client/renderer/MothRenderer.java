package net.dakotapride.pridemoths.client.renderer;

import com.google.common.collect.Maps;
import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.dakotapride.pridemoths.client.model.MothModel;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class MothRenderer extends GeoEntityRenderer<MothEntity> {
    public static final Map<MothVariation, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(MothVariation.class), (map) -> {
                map.put(MothVariation.DEFAULT, Identifier.of("pridemoths", "textures/model/moth.png"));
                map.put(MothVariation.RARE, Identifier.of("pridemoths", "textures/model/rare.png"));

                map.put(MothVariation.AGENDER, Identifier.of("pridemoths", "textures/model/pride/agender.png"));
                map.put(MothVariation.AROACE, Identifier.of("pridemoths", "textures/model/pride/aroace.png"));
                map.put(MothVariation.AROMANTIC, Identifier.of("pridemoths", "textures/model/pride/aromantic.png"));
                map.put(MothVariation.ASEXUAL, Identifier.of("pridemoths", "textures/model/pride/asexual.png"));
                map.put(MothVariation.BISEXUAL, Identifier.of("pridemoths", "textures/model/pride/bisexual.png"));
                map.put(MothVariation.DEMIBOY, Identifier.of("pridemoths", "textures/model/pride/demiboy.png"));
                map.put(MothVariation.DEMIGENDER, Identifier.of("pridemoths", "textures/model/pride/demigender.png"));
                map.put(MothVariation.DEMIGIRL, Identifier.of("pridemoths", "textures/model/pride/demigirl.png"));
                map.put(MothVariation.DEMIROMANTIC, Identifier.of("pridemoths", "textures/model/pride/demiromantic.png"));
                map.put(MothVariation.DEMISEXUAL, Identifier.of("pridemoths", "textures/model/pride/demisexual.png"));
                map.put(MothVariation.GAY, Identifier.of("pridemoths", "textures/model/pride/gay.png"));
                map.put(MothVariation.LESBIAN, Identifier.of("pridemoths", "textures/model/pride/lesbian.png"));
                map.put(MothVariation.LGBT, Identifier.of("pridemoths", "textures/model/pride/lgbt.png"));
                map.put(MothVariation.NON_BINARY, Identifier.of("pridemoths", "textures/model/pride/non_binary.png"));
                map.put(MothVariation.OMNISEXUAL, Identifier.of("pridemoths", "textures/model/pride/omnisexual.png"));
                map.put(MothVariation.PANSEXUAL, Identifier.of("pridemoths", "textures/model/pride/pansexual.png"));
                map.put(MothVariation.POLYAMOROUS, Identifier.of("pridemoths", "textures/model/pride/polyamorous.png"));
                map.put(MothVariation.POLYSEXUAL, Identifier.of("pridemoths", "textures/model/pride/polysexual.png"));
                map.put(MothVariation.TRANSGENDER, Identifier.of("pridemoths", "textures/model/pride/transgender.png"));
                map.put(MothVariation.ALLY, Identifier.of("pridemoths", "textures/model/pride/ally.png"));
            });

    public MothRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new MothModel());
    }

    @Override
    public Identifier getTextureLocation(MothEntity entity) {
        if (entity.isBaby()) {
            if (entity.getMothVariant() == MothVariation.RARE) {
                return Identifier.of("pridemoths", "textures/model/baby/rare.png");
            } else {
                return Identifier.of("pridemoths", "textures/model/baby/moth.png");
            }
        }

        return LOCATION_BY_VARIANT.get(entity.getMothVariant());
    }

    @Override
    public RenderLayer getRenderType(MothEntity entity, Identifier texture, @Nullable VertexConsumerProvider bufferSource, float partialTick) {

        if (entity.isBaby()) {

        }

        return super.getRenderType(entity, texture, bufferSource, partialTick);
    }
}