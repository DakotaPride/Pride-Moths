package net.dakotapride.pridemoths.client.renderer;

import com.google.common.collect.Maps;
import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.entity.MothVariant;
import net.dakotapride.pridemoths.client.model.MothModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class MothRenderer extends GeoEntityRenderer<MothEntity> {
    public static final Map<MothVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(MothVariant.class), (map) -> {
                map.put(MothVariant.DEFAULT,
                        new Identifier("pridemoths", "textures/model/orange.png"));
                // Pride Variants
                map.put(MothVariant.TRANS,
                        new Identifier("pridemoths", "textures/model/pride/trans.png"));
                map.put(MothVariant.LGBT,
                        new Identifier("pridemoths", "textures/model/pride/lgbt.png"));
                map.put(MothVariant.NON_BINARY,
                        new Identifier("pridemoths", "textures/model/pride/non_binary.png"));
                map.put(MothVariant.LESBIAN,
                        new Identifier("pridemoths", "textures/model/pride/lesbian.png"));
                map.put(MothVariant.GAY,
                        new Identifier("pridemoths", "textures/model/pride/gay.png"));
                map.put(MothVariant.AGENDER,
                        new Identifier("pridemoths", "textures/model/pride/agender.png"));
                map.put(MothVariant.ASEXUAL,
                        new Identifier("pridemoths", "textures/model/pride/asexual.png"));
                map.put(MothVariant.PANSEXUAL,
                        new Identifier("pridemoths", "textures/model/pride/pansexual.png"));
                map.put(MothVariant.BISEXUAL,
                        new Identifier("pridemoths", "textures/model/pride/bisexual.png"));
            });

    public MothRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new MothModel());
    }

    @Override
    public Identifier getTextureLocation(MothEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getMothVariant());
    }
}