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
                map.put(MothVariant.BLUE,
                        new Identifier("pridemoths", "textures/model/blue.png"));
                map.put(MothVariant.YELLOW,
                        new Identifier("pridemoths", "textures/model/yellow.png"));
                map.put(MothVariant.GREEN,
                        new Identifier("pridemoths", "textures/model/green.png"));
                // Rare Variant
                map.put(MothVariant.PALOS_VERDES_BLUE,
                        new Identifier("pridemoths", "textures/model/palos_verdes_blue.png"));
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
                map.put(MothVariant.POLYAMOROUS,
                        new Identifier("pridemoths", "textures/model/pride/polyamorous.png"));
                map.put(MothVariant.POLYSEXUAL,
                        new Identifier("pridemoths", "textures/model/pride/polysexual.png"));
                map.put(MothVariant.OMNISEXUAL,
                        new Identifier("pridemoths", "textures/model/pride/omnisexual.png"));
                map.put(MothVariant.DEMISEXUAL,
                        new Identifier("pridemoths", "textures/model/pride/demisexual.png"));
                map.put(MothVariant.DEMIBOY,
                        new Identifier("pridemoths", "textures/model/pride/demiboy.png"));
                map.put(MothVariant.DEMIGIRL,
                        new Identifier("pridemoths", "textures/model/pride/demigirl.png"));
                map.put(MothVariant.DEMIGENDER,
                        new Identifier("pridemoths", "textures/model/pride/demigender.png"));
                map.put(MothVariant.DEMIROMANTIC,
                        new Identifier("pridemoths", "textures/model/pride/demiromantic.png"));
                map.put(MothVariant.AROACE,
                        new Identifier("pridemoths", "textures/model/pride/aroace.png"));
                map.put(MothVariant.AROMANTIC,
                        new Identifier("pridemoths", "textures/model/pride/aromantic.png"));
            });

    public MothRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new MothModel());
    }

    @Override
    public Identifier getTextureLocation(MothEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getMothVariant());
    }
}