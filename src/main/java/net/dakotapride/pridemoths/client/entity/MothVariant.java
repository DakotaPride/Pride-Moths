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
    BISEXUAL("bisexual");

    private final String variation;

    MothVariant(String variation) {
        this.variation = variation;
    }

    public String getVariation() {
        return variation;
    }
}