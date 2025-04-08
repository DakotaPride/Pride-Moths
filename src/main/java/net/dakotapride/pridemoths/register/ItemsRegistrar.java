package net.dakotapride.pridemoths.register;

import net.dakotapride.pridemoths.PrideMothsInitialize;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.dakotapride.pridemoths.item.FruitfulStewFoodItem;
import net.dakotapride.pridemoths.item.GlassJarItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ItemsRegistrar {
    public static Item MOTH_SPAWN_EGG = registerItem("moth_spawn_egg", 
            settings -> new SpawnEggItem(EntityTypeRegistrar.MOTH, settings), new Item.Settings());
    public static Item MOTH_FUZZ = registerItem("moth_fuzz",
            Item::new, new Item.Settings());
    public static Item FRUITFUL_STEW = registerItem("fruitful_stew",
            settings -> new FruitfulStewFoodItem(settings.maxCount(1)), new Item.Settings());
    public static Item GLASS_JAR = registerItem("glass_jar",
            settings -> new GlassJarItem(true, settings), new Item.Settings());
    public static Item MOTH_JAR = registerItem("moth_jar",
            GlassJarItem::new, new Item.Settings());
    public static Item TRANSGENDER_MOTH_JAR = registerMothContainedInJarItem(MothVariation.TRANSGENDER);
    public static Item LGBT_MOTH_JAR = registerMothContainedInJarItem(MothVariation.LGBT);
    public static Item NON_BINARY_MOTH_JAR = registerMothContainedInJarItem(MothVariation.NON_BINARY);
    public static Item LESBIAN_MOTH_JAR = registerMothContainedInJarItem(MothVariation.LESBIAN);
    public static Item GAY_MOTH_JAR = registerMothContainedInJarItem(MothVariation.GAY);
    public static Item AGENDER_MOTH_JAR = registerMothContainedInJarItem(MothVariation.AGENDER);
    public static Item ASEXUAL_MOTH_JAR = registerMothContainedInJarItem(MothVariation.ASEXUAL);
    public static Item PANSEXUAL_MOTH_JAR = registerMothContainedInJarItem(MothVariation.PANSEXUAL);
    public static Item BISEXUAL_MOTH_JAR = registerMothContainedInJarItem(MothVariation.BISEXUAL);
    public static Item POLYAMOROUS_MOTH_JAR = registerMothContainedInJarItem(MothVariation.POLYAMOROUS);
    public static Item POLYSEXUAL_MOTH_JAR = registerMothContainedInJarItem(MothVariation.POLYSEXUAL);
    public static Item OMNISEXUAL_MOTH_JAR = registerMothContainedInJarItem(MothVariation.OMNISEXUAL);
    public static Item AROMANTIC_MOTH_JAR = registerMothContainedInJarItem(MothVariation.AROMANTIC);
    public static Item DEMISEXUAL_MOTH_JAR = registerMothContainedInJarItem(MothVariation.DEMISEXUAL);
    public static Item DEMIBOY_MOTH_JAR = registerMothContainedInJarItem(MothVariation.DEMIBOY);
    public static Item DEMIGIRL_MOTH_JAR = registerMothContainedInJarItem(MothVariation.DEMIGIRL);
    public static Item DEMIGENDER_MOTH_JAR = registerMothContainedInJarItem(MothVariation.DEMIGENDER);
    public static Item AROACE_MOTH_JAR = registerMothContainedInJarItem(MothVariation.AROACE);
    public static Item DEMIROMANTIC_MOTH_JAR = registerMothContainedInJarItem(MothVariation.DEMIROMANTIC);
    public static Item GENDERFLUID_MOTH_JAR = registerMothContainedInJarItem(MothVariation.GENDERFLUID);
    public static Item INTERSEX_MOTH_JAR = registerMothContainedInJarItem(MothVariation.INTERSEX);
    public static Item XENOGENDER_MOTH_JAR = registerMothContainedInJarItem(MothVariation.XENOGENDER);
    public static Item GENDER_QUEER_MOTH_JAR = registerMothContainedInJarItem(MothVariation.GENDER_QUEER);
    public static Item GENDERFAE_MOTH_JAR = registerMothContainedInJarItem(MothVariation.GENDERFAE);
    public static Item GENDERFAUN_MOTH_JAR = registerMothContainedInJarItem(MothVariation.GENDERFAUN);
    public static Item BIGENDER_MOTH_JAR = registerMothContainedInJarItem(MothVariation.BIGENDER);
    public static Item PANGENDER_MOTH_JAR = registerMothContainedInJarItem(MothVariation.PANGENDER);
    public static Item ALLY_MOTH_JAR = registerMothContainedInJarItem(MothVariation.ALLY);
    public static Item RARE_MOTH_JAR = registerMothContainedInJarItem(MothVariation.RARE);

    public static Item registerItem(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        // Create the item key.
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(PrideMothsInitialize.MOD_ID, name));

        // Create the item instance.
        Item item = itemFactory.apply(settings.registryKey(itemKey));

        // Register the item.
        Registry.register(Registries.ITEM, itemKey, item);

        return item;
    }

    private static Item registerMothContainedInJarItem(MothVariation variation) {
        return registerItem(variation.getVariation() + "_moth_jar", GlassJarItem::new, new Item.Settings());
    }

    public static void yep() {}
}
