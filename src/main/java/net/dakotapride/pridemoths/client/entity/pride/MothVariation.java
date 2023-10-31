package net.dakotapride.pridemoths.client.entity.pride;

public enum MothVariation {
    DEFAULT("default"),
    RARE("rare"),

    TRANSGENDER("transgender"),
    LGBT("lgbt"),
    NON_BINARY("non_binary"),
    LESBIAN("lesbian"),
    GAY("gay"),
    AGENDER("agender"),
    ASEXUAL("asexual"),
    PANSEXUAL("pansexual"),
    BISEXUAL("bisexual"),
    POLYAMOROUS("polyamorous"),
    POLYSEXUAL("polysexual"),
    OMNISEXUAL("omnisexual"),
    AROMANTIC("aromantic"),
    DEMISEXUAL("demisexual"),
    DEMIBOY("demiboy"),
    DEMIGIRL("demigirl"),
    DEMIGENDER("demigender"),
    AROACE("aroace"),
    DEMIROMANTIC("demiromantic"),

    ALLY("ally");

    private final String variation;

    MothVariation(String variation) {
        this.variation = variation;
    }

    public String getVariation() {
        return variation;
    }
}
