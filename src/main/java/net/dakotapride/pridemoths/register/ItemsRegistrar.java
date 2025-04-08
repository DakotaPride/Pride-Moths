package net.dakotapride.pridemoths.register;

import net.dakotapride.pridemoths.PrideMothsInitialize;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.dakotapride.pridemoths.item.FruitfulStewFoodItem;
import net.dakotapride.pridemoths.item.GlassJarItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ItemsRegistrar {
    public static Item MOTH_SPAWN_EGG = Registry.register(Registries.ITEM, Identifier.of(PrideMothsInitialize.MOD_ID, "moth_spawn_egg"),
            new SpawnEggItem(EntityTypeRegistrar.MOTH, 0xCECAC4, 0x82635C, new Item.Settings()));
    public static Item MOTH_FUZZ = Registry.register(Registries.ITEM, Identifier.of(PrideMothsInitialize.MOD_ID, "moth_fuzz"),
            new Item(new Item.Settings()));
    public static Item FRUITFUL_STEW = Registry.register(Registries.ITEM, Identifier.of(PrideMothsInitialize.MOD_ID, "fruitful_stew"),
            new FruitfulStewFoodItem(new Item.Settings().maxCount(1)));
    public static Item GLASS_JAR = Registry.register(Registries.ITEM,
            Identifier.of(PrideMothsInitialize.MOD_ID, "glass_jar"),
            new GlassJarItem(true, new Item.Settings()));
    public static Item MOTH_JAR = Registry.register(Registries.ITEM,
            Identifier.of(PrideMothsInitialize.MOD_ID, "moth_jar"),
            new GlassJarItem(new Item.Settings()));
    public static Item TRANSGENDER_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.TRANSGENDER);
    public static Item LGBT_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.LGBT);
    public static Item NON_BINARY_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.NON_BINARY);
    public static Item LESBIAN_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.LESBIAN);
    public static Item GAY_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.GAY);
    public static Item AGENDER_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.AGENDER);
    public static Item ASEXUAL_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.ASEXUAL);
    public static Item PANSEXUAL_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.PANSEXUAL);
    public static Item BISEXUAL_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.BISEXUAL);
    public static Item POLYAMOROUS_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.POLYAMOROUS);
    public static Item POLYSEXUAL_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.POLYSEXUAL);
    public static Item OMNISEXUAL_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.OMNISEXUAL);
    public static Item AROMANTIC_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.AROMANTIC);
    public static Item DEMISEXUAL_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.DEMISEXUAL);
    public static Item DEMIBOY_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.DEMIBOY);
    public static Item DEMIGIRL_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.DEMIGIRL);
    public static Item DEMIGENDER_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.DEMIGENDER);
    public static Item AROACE_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.AROACE);
    public static Item DEMIROMANTIC_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.DEMIROMANTIC);
    public static Item GENDERFLUID_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.GENDERFLUID);
    public static Item INTERSEX_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.INTERSEX);
    public static Item XENOGENDER_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.XENOGENDER);
    public static Item GENDER_QUEER_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.GENDER_QUEER);
    public static Item GENDERFAE_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.GENDERFAE);
    public static Item GENDERFAUN_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.GENDERFAUN);
    public static Item BIGENDER_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.BIGENDER);
    public static Item PANGENDER_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.PANGENDER);
    public static Item ALLY_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.ALLY);
    public static Item RARE_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.RARE);

    private static Item registerMothContainedWithinJarItem(MothVariation variation) {
        return Registry.register(Registries.ITEM, Identifier.of(PrideMothsInitialize.MOD_ID, variation.getVariation() + "_moth_jar"), new GlassJarItem(new Item.Settings().maxCount(1)));
    }

    public static void yep() {}
}
