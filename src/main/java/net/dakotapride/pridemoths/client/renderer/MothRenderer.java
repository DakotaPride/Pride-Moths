package net.dakotapride.pridemoths.client.renderer;

import com.google.common.collect.Maps;
import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.model.MothModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class MothRenderer extends GeoEntityRenderer<MothEntity> {
    public static final Map<MothEntity.MothVaration, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(MothEntity.MothVaration.class), (map) -> {
                map.put(MothEntity.MothVaration.DEFAULT,
                        new Identifier("pridemoths", "textures/model/orange.png"));
                map.put(MothEntity.MothVaration.BLUE,
                        new Identifier("pridemoths", "textures/model/blue.png"));
                map.put(MothEntity.MothVaration.YELLOW,
                        new Identifier("pridemoths", "textures/model/yellow.png"));
                map.put(MothEntity.MothVaration.GREEN,
                        new Identifier("pridemoths", "textures/model/green.png"));
                map.put(MothEntity.MothVaration.RED,
                        new Identifier("pridemoths", "textures/model/red.png"));
                // Rare Variant
                map.put(MothEntity.MothVaration.PALOS_VERDES_BLUE,
                        new Identifier("pridemoths", "textures/model/palos_verdes_blue.png"));
                // Pride Variants
                map.put(MothEntity.MothVaration.TRANS,
                        new Identifier("pridemoths", "textures/model/pride/trans.png"));
                map.put(MothEntity.MothVaration.LGBT,
                        new Identifier("pridemoths", "textures/model/pride/lgbt.png"));
                map.put(MothEntity.MothVaration.NON_BINARY,
                        new Identifier("pridemoths", "textures/model/pride/non_binary.png"));
                map.put(MothEntity.MothVaration.LESBIAN,
                        new Identifier("pridemoths", "textures/model/pride/lesbian.png"));
                map.put(MothEntity.MothVaration.GAY,
                        new Identifier("pridemoths", "textures/model/pride/gay.png"));
                map.put(MothEntity.MothVaration.AGENDER,
                        new Identifier("pridemoths", "textures/model/pride/agender.png"));
                map.put(MothEntity.MothVaration.ASEXUAL,
                        new Identifier("pridemoths", "textures/model/pride/asexual.png"));
                map.put(MothEntity.MothVaration.PANSEXUAL,
                        new Identifier("pridemoths", "textures/model/pride/pansexual.png"));
                map.put(MothEntity.MothVaration.BISEXUAL,
                        new Identifier("pridemoths", "textures/model/pride/bisexual.png"));
                map.put(MothEntity.MothVaration.POLYAMOROUS,
                        new Identifier("pridemoths", "textures/model/pride/polyamorous.png"));
                map.put(MothEntity.MothVaration.POLYSEXUAL,
                        new Identifier("pridemoths", "textures/model/pride/polysexual.png"));
                map.put(MothEntity.MothVaration.OMNISEXUAL,
                        new Identifier("pridemoths", "textures/model/pride/omnisexual.png"));
                map.put(MothEntity.MothVaration.DEMISEXUAL,
                        new Identifier("pridemoths", "textures/model/pride/demisexual.png"));
                map.put(MothEntity.MothVaration.DEMIBOY,
                        new Identifier("pridemoths", "textures/model/pride/demiboy.png"));
                map.put(MothEntity.MothVaration.DEMIGIRL,
                        new Identifier("pridemoths", "textures/model/pride/demigirl.png"));
                map.put(MothEntity.MothVaration.DEMIGENDER,
                        new Identifier("pridemoths", "textures/model/pride/demigender.png"));
                map.put(MothEntity.MothVaration.DEMIROMANTIC,
                        new Identifier("pridemoths", "textures/model/pride/demiromantic.png"));
                map.put(MothEntity.MothVaration.AROACE,
                        new Identifier("pridemoths", "textures/model/pride/aroace.png"));
                map.put(MothEntity.MothVaration.AROMANTIC,
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