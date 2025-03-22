package net.dakotapride.pridemoths.register;

import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.dakotapride.pridemoths.item.FruitfulStewFoodItem;
import net.dakotapride.pridemoths.item.GlassJarItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ItemsRegistrar {
    public static Item MOTH_SPAWN_EGG = Registry.register(Registries.ITEM, Identifier.of("pridemoths", "moth_spawn_egg"),
            new SpawnEggItem(EntityTypeRegistrar.MOTH, 0xCECAC4, 0x82635C, new Item.Settings()));
    public static Item MOTH_FUZZ = Registry.register(Registries.ITEM, Identifier.of("pridemoths", "moth_fuzz"),
            new Item(new Item.Settings()));
    public static Item FRUITFUL_STEW = Registry.register(Registries.ITEM, Identifier.of("pridemoths", "fruitful_stew"),
            new FruitfulStewFoodItem(new Item.Settings().maxCount(1)));
    public static Item GLASS_JAR = Registry.register(Registries.ITEM,
            Identifier.of("pridemoths", "glass_jar"),
            new GlassJarItem(true, new Item.Settings()));
    public static Item MOTH_JAR = Registry.register(Registries.ITEM,
            Identifier.of("pridemoths", "moth_jar"),
            new GlassJarItem(new Item.Settings()));
    public static Item TRANSGENDER_MOTH_JAR = Registry.register(Registries.ITEM,
            Identifier.of("pridemoths", MothVariation.TRANSGENDER.getVariation() + "_moth_jar"),
            new GlassJarItem(new Item.Settings().maxCount(1)));
    public static Item LGBT_MOTH_JAR = Registry.register(Registries.ITEM,
            Identifier.of("pridemoths", MothVariation.LGBT.getVariation() + "_moth_jar"),
            new GlassJarItem(new Item.Settings().maxCount(1)));
    public static Item NON_BINARY_MOTH_JAR = Registry.register(Registries.ITEM,
            Identifier.of("pridemoths", MothVariation.NON_BINARY.getVariation() + "_moth_jar"),
            new GlassJarItem(new Item.Settings().maxCount(1)));
    public static Item LESBIAN_MOTH_JAR = Registry.register(Registries.ITEM,
            Identifier.of("pridemoths", MothVariation.LESBIAN.getVariation() + "_moth_jar"),
            new GlassJarItem(new Item.Settings().maxCount(1)));
    public static Item GAY_MOTH_JAR = Registry.register(Registries.ITEM,
            Identifier.of("pridemoths", MothVariation.GAY.getVariation() + "_moth_jar"),
            new GlassJarItem(new Item.Settings().maxCount(1)));
    public static Item AGENDER_MOTH_JAR = Registry.register(Registries.ITEM,
            Identifier.of("pridemoths", MothVariation.AGENDER.getVariation() + "_moth_jar"),
            new GlassJarItem(new Item.Settings().maxCount(1)));
    public static Item ASEXUAL_MOTH_JAR = Registry.register(Registries.ITEM,
            Identifier.of("pridemoths", MothVariation.ASEXUAL.getVariation() + "_moth_jar"),
            new GlassJarItem(new Item.Settings().maxCount(1)));
    public static Item PANSEXUAL_MOTH_JAR = Registry.register(Registries.ITEM,
            Identifier.of("pridemoths", MothVariation.PANSEXUAL.getVariation() + "_moth_jar"),
            new GlassJarItem(new Item.Settings().maxCount(1)));
    public static Item BISEXUAL_MOTH_JAR = Registry.register(Registries.ITEM,
            Identifier.of("pridemoths", MothVariation.BISEXUAL.getVariation() + "_moth_jar"),
            new GlassJarItem(new Item.Settings().maxCount(1)));
    public static Item POLYAMOROUS_MOTH_JAR = Registry.register(Registries.ITEM,
            Identifier.of("pridemoths", MothVariation.POLYAMOROUS.getVariation() + "_moth_jar"),
            new GlassJarItem(new Item.Settings().maxCount(1)));
    public static Item POLYSEXUAL_MOTH_JAR = Registry.register(Registries.ITEM,
            Identifier.of("pridemoths", MothVariation.POLYSEXUAL.getVariation() + "_moth_jar"),
            new GlassJarItem(new Item.Settings().maxCount(1)));
    public static Item OMNISEXUAL_MOTH_JAR = Registry.register(Registries.ITEM,
            Identifier.of("pridemoths", MothVariation.OMNISEXUAL.getVariation() + "_moth_jar"),
            new GlassJarItem(new Item.Settings().maxCount(1)));
    public static Item AROMANTIC_MOTH_JAR = Registry.register(Registries.ITEM,
            Identifier.of("pridemoths", MothVariation.AROMANTIC.getVariation() + "_moth_jar"),
            new GlassJarItem(new Item.Settings().maxCount(1)));
    public static Item DEMISEXUAL_MOTH_JAR = Registry.register(Registries.ITEM,
            Identifier.of("pridemoths", MothVariation.DEMISEXUAL.getVariation() + "_moth_jar"),
            new GlassJarItem(new Item.Settings().maxCount(1)));
    public static Item DEMIBOY_MOTH_JAR = Registry.register(Registries.ITEM,
            Identifier.of("pridemoths", MothVariation.DEMIBOY.getVariation() + "_moth_jar"),
            new GlassJarItem(new Item.Settings().maxCount(1)));
    public static Item DEMIGIRL_MOTH_JAR = Registry.register(Registries.ITEM,
            Identifier.of("pridemoths", MothVariation.DEMIGIRL.getVariation() + "_moth_jar"),
            new GlassJarItem(new Item.Settings().maxCount(1)));
    public static Item DEMIGENDER_MOTH_JAR = Registry.register(Registries.ITEM,
            Identifier.of("pridemoths", MothVariation.DEMIGENDER.getVariation() + "_moth_jar"),
            new GlassJarItem(new Item.Settings().maxCount(1)));
    public static Item AROACE_MOTH_JAR = Registry.register(Registries.ITEM,
            Identifier.of("pridemoths", MothVariation.AROACE.getVariation() + "_moth_jar"),
            new GlassJarItem(new Item.Settings().maxCount(1)));
    public static Item DEMIROMANTIC_MOTH_JAR = Registry.register(Registries.ITEM,
            Identifier.of("pridemoths", MothVariation.DEMIROMANTIC.getVariation() + "_moth_jar"),
            new GlassJarItem(new Item.Settings().maxCount(1)));
    public static Item ALLY_MOTH_JAR = Registry.register(Registries.ITEM,
            Identifier.of("pridemoths", MothVariation.ALLY.getVariation() + "_moth_jar"),
            new GlassJarItem(new Item.Settings().maxCount(1)));
    public static Item RARE_MOTH_JAR = Registry.register(Registries.ITEM,
            Identifier.of("pridemoths", MothVariation.RARE.getVariation() + "_moth_jar"),
            new GlassJarItem(new Item.Settings().maxCount(1)));

    public static void yep() {}
}
