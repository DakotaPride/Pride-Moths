package net.dakotapride.pridemoths.client.entity;

public enum MothVariant {
    DEFAULT("default"),
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