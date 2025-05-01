package net.dakotapride.pridemoths.client.entity.pride;

import com.mojang.serialization.Codec;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;

import java.util.function.IntFunction;

public enum MothVariation implements StringIdentifiable {
    DEFAULT(0, "default"),
    RARE(1, "rare"),

    TRANSGENDER(2, "transgender"),
    LGBT(3, "lgbt"),
    NON_BINARY(4, "non_binary"),
    LESBIAN(5, "lesbian"),
    GAY(6, "gay"),
    AGENDER(7, "agender"),
    ASEXUAL(8, "asexual"),
    PANSEXUAL(9, "pansexual"),
    BISEXUAL(10, "bisexual"),
    POLYAMOROUS(11, "polyamorous"),
    POLYSEXUAL(12, "polysexual"),
    OMNISEXUAL(13, "omnisexual"),
    AROMANTIC(14, "aromantic"),
    DEMISEXUAL(15, "demisexual"),
    DEMIBOY(16, "demiboy"),
    DEMIGIRL(17, "demigirl"),
    DEMIGENDER(18, "demigender"),
    AROACE(19, "aroace"),
    DEMIROMANTIC(20, "demiromantic"),
    GENDERFLUID(21, "genderfluid"),
    INTERSEX(22, "intersex"),
    XENOGENDER(23, "xenogender"),
    GENDER_QUEER(24, "gender_queer"),
    GENDERFAE(25, "genderfae"),
    GENDERFAUN(26, "genderfaun"),
    BIGENDER(27, "bigender"),
    PANGENDER(28, "pangender"),

    ALLY(29, "ally");

    private final String variation;
    private final int index;
    public static final Codec<MothVariation> CODEC;
    public static final Codec<MothVariation> INDEX_CODEC;
    private static final IntFunction<MothVariation> INDEX_MAPPER;

    MothVariation(int index, String variation) {
        this.variation = variation;
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public static MothVariation byIndex(int index) {
        return INDEX_MAPPER.apply(index);
    }

    public String getVariation() {
        return variation;
    }

    static {
        INDEX_MAPPER = ValueLists.createIndexToValueFunction(MothVariation::getIndex, MothVariation.values(), ValueLists.OutOfBoundsHandling.ZERO);
        CODEC = StringIdentifiable.createCodec(MothVariation::values);
        INDEX_CODEC = Codec.INT.xmap(INDEX_MAPPER::apply, MothVariation::getIndex);
    }

    @Override
    public String asString() {
        return this.variation;
    }
}
