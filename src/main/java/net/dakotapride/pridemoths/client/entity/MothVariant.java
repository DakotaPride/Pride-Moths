package net.dakotapride.pridemoths.client.entity;

public enum MothVariant {
    DEFAULT("default"),
    BLUE("blue"),
    YELLOW("yellow"),
    GREEN("green"),
    // Rare Pattern
    PALOS_VERDES_BLUE("palos_verdes_blue"),
    // Pride Variations
    TRANS("trans"),
    LGBT("lgbt"),
    NON_BINARY("non_binary"),
    LESBIAN("lesbian"),
    GAY("gay"),
    AGENDER("agender"),
    ASEXUAL("asexual"),
    PANSEXUAL("pansexual"),
    BISEXUAL("bisexual"),
    // 1.3
    POLYAMOROUS("polyamorous"),
    POLYSEXUAL("polysexual"),
    OMNISEXUAL("omnisexual"),
    AROMANTIC("aromantic"),
    DEMISEXUAL("demisexual"),
    DEMIBOY("demiboy"),
    DEMIGIRL("demigirl"),
    DEMIGENDER("demigender"),
    AROACE("aroace"),
    DEMIROMANTIC("demiromantic");

    private final String variation;

    MothVariant(String variation) {
        this.variation = variation;
    }

    public String getVariation() {
        return variation;
    }
}