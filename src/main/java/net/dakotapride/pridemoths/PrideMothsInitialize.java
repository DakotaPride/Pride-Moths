package net.dakotapride.pridemoths;

import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.entity.MothVariant;
import net.dakotapride.pridemoths.item.MothBottleItem;
import net.dakotapride.pridemoths.mixin.BrewingRecipeRegistryMixin;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

public class PrideMothsInitialize implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("Pride Moths");


	public static EntityType<MothEntity> MOTH;
	public static Item MOTH_SPAWN_EGG;
	public static Item MOTH_WING;
	public static Item ORANGE_MOTH_BOTTLE;
	public static Item BLUE_MOTH_BOTTLE;
	public static Item YELLOW_MOTH_BOTTLE;
	public static Item GREEN_MOTH_BOTTLE;
	public static Item RED_MOTH_BOTTLE;
	public static Item PALOS_VERDES_BLUE_MOTH_BOTTLE;
	public static Item TRANS_MOTH_BOTTLE;
	public static Item LGBT_MOTH_BOTTLE;
	public static Item NON_BINARY_MOTH_BOTTLE;
	public static Item AGENDER_MOTH_BOTTLE;
	public static Item ASEXUAL_MOTH_BOTTLE;
	public static Item PANSEXUAL_MOTH_BOTTLE;
	public static Item BISEXUAL_MOTH_BOTTLE;
	public static Item LESBIAN_MOTH_BOTTLE;
	public static Item GAY_MOTH_BOTTLE;
	public static Item POLYAMOROUS_MOTH_BOTTLE;
	public static Item POLYSEXUAL_MOTH_BOTTLE;
	public static Item OMNISEXUAL_MOTH_BOTTLE;
	public static Item DEMIBOY_MOTH_BOTTLE;
	public static Item DEMIGIRL_MOTH_BOTTLE;
	public static Item DEMIGENDER_MOTH_BOTTLE;
	public static Item AROACE_MOTH_BOTTLE;
	public static Item DEMISEXUAL_MOTH_BOTTLE;
	public static Item DEMIROMANTIC_MOTH_BOTTLE;
	public static Item AROMANTIC_MOTH_BOTLE;
	public static Potion MOTH_MASTER_POTION;

	@Override
	public void onInitialize() {

		MOTH = Registry.register(
				Registries.ENTITY_TYPE, new Identifier("pridemoths", "moth"),
				FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MothEntity::new)
						.dimensions(EntityDimensions.fixed(0.2f, 0.2f)).build());
		FabricDefaultAttributeRegistry.register(MOTH, MothEntity.setAttributes());
		BiomeModifications.addSpawn(biome -> biome.hasTag(ConventionalBiomeTags.PLAINS), SpawnGroup.CREATURE, MOTH, 60, 3, 7);

		MOTH_SPAWN_EGG = Registry.register(Registries.ITEM, new Identifier("pridemoths", "moth_spawn_egg"),
				new SpawnEggItem(MOTH, 0xB1552D, 0x921F12, new FabricItemSettings()));

		MOTH_WING = Registry.register(Registries.ITEM, new Identifier("pridemoths", "moth_wing"),
				new Item(new FabricItemSettings().food(new FoodComponent.Builder().saturationModifier(0.3F).hunger(1).snack()
						.statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 1), 1.0F)
						.statusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 300), 1.0F).build())));

		ORANGE_MOTH_BOTTLE = Registry.register(Registries.ITEM, new Identifier("pridemoths", "orange_moth_bottle"),
				new MothBottleItem(MothVariant.DEFAULT.getVariation(), new FabricItemSettings(), new Identifier("pridemoths", "textures/item/bottle/orange.png")));
		BLUE_MOTH_BOTTLE = Registry.register(Registries.ITEM, new Identifier("pridemoths", "blue_moth_bottle"),
				new MothBottleItem(MothVariant.BLUE.getVariation(), new FabricItemSettings(), new Identifier("pridemoths", "textures/item/bottle/orange.png")));
		YELLOW_MOTH_BOTTLE = Registry.register(Registries.ITEM, new Identifier("pridemoths", "yellow_moth_bottle"),
				new MothBottleItem(MothVariant.YELLOW.getVariation(), new FabricItemSettings(), new Identifier("pridemoths", "textures/item/bottle/orange.png")));
		GREEN_MOTH_BOTTLE = Registry.register(Registries.ITEM, new Identifier("pridemoths", "green_moth_bottle"),
				new MothBottleItem(MothVariant.GREEN.getVariation(), new FabricItemSettings(), new Identifier("pridemoths", "textures/item/bottle/orange.png")));
		RED_MOTH_BOTTLE = Registry.register(Registries.ITEM, new Identifier("pridemoths", "red_moth_bottle"),
				new MothBottleItem(MothVariant.RED.getVariation(), new FabricItemSettings(), new Identifier("pridemoths", "textures/item/bottle/red.png")));
		PALOS_VERDES_BLUE_MOTH_BOTTLE = Registry.register(Registries.ITEM, new Identifier("pridemoths", "palos_verdes_blue_moth_bottle"),
				new MothBottleItem(MothVariant.PALOS_VERDES_BLUE.getVariation(), new FabricItemSettings(), new Identifier("pridemoths", "textures/item/bottle/palos_verde_blue.png")));
		TRANS_MOTH_BOTTLE = Registry.register(Registries.ITEM, new Identifier("pridemoths", "trans_moth_bottle"),
				new MothBottleItem(MothVariant.TRANS.getVariation(), new FabricItemSettings(), new Identifier("pridemoths", "textures/item/bottle/trans.png")));
		LGBT_MOTH_BOTTLE = Registry.register(Registries.ITEM, new Identifier("pridemoths", "lgbt_moth_bottle"),
				new MothBottleItem(MothVariant.LGBT.getVariation(), new FabricItemSettings(), new Identifier("pridemoths", "textures/item/bottle/lgbt.png")));
		NON_BINARY_MOTH_BOTTLE = Registry.register(Registries.ITEM, new Identifier("pridemoths", "non_binary_moth_bottle"),
				new MothBottleItem(MothVariant.NON_BINARY.getVariation(), new FabricItemSettings(), new Identifier("pridemoths", "textures/item/bottle/non_binary.png")));
		AGENDER_MOTH_BOTTLE = Registry.register(Registries.ITEM, new Identifier("pridemoths", "agender_moth_bottle"),
				new MothBottleItem(MothVariant.AGENDER.getVariation(), new FabricItemSettings(), new Identifier("pridemoths", "textures/item/bottle/agender.png")));
		GAY_MOTH_BOTTLE = Registry.register(Registries.ITEM, new Identifier("pridemoths", "gay_moth_bottle"),
				new MothBottleItem(MothVariant.GAY.getVariation(), new FabricItemSettings(), new Identifier("pridemoths", "textures/item/bottle/gay.png")));
		LESBIAN_MOTH_BOTTLE = Registry.register(Registries.ITEM, new Identifier("pridemoths", "lesbian_moth_bottle"),
				new MothBottleItem(MothVariant.LESBIAN.getVariation(), new FabricItemSettings(), new Identifier("pridemoths", "textures/item/bottle/lesbian.png")));
		ASEXUAL_MOTH_BOTTLE = Registry.register(Registries.ITEM, new Identifier("pridemoths", "asexual_moth_bottle"),
				new MothBottleItem(MothVariant.ASEXUAL.getVariation(), new FabricItemSettings(), new Identifier("pridemoths", "textures/item/bottle/asexual.png")));
		BISEXUAL_MOTH_BOTTLE = Registry.register(Registries.ITEM, new Identifier("pridemoths", "bisexual_moth_bottle"),
				new MothBottleItem(MothVariant.BISEXUAL.getVariation(), new FabricItemSettings(), new Identifier("pridemoths", "textures/item/bottle/bisexual.png")));
		PANSEXUAL_MOTH_BOTTLE = Registry.register(Registries.ITEM, new Identifier("pridemoths", "pansexual_moth_bottle"),
				new MothBottleItem(MothVariant.PANSEXUAL.getVariation(), new FabricItemSettings(), new Identifier("pridemoths", "textures/item/bottle/pansexual.png")));
		POLYAMOROUS_MOTH_BOTTLE = Registry.register(Registries.ITEM, new Identifier("pridemoths", "polyamorous_moth_bottle"),
				new MothBottleItem(MothVariant.POLYAMOROUS.getVariation(), new FabricItemSettings(), new Identifier("pridemoths", "textures/item/bottle/polyamorous.png")));
		POLYSEXUAL_MOTH_BOTTLE = Registry.register(Registries.ITEM, new Identifier("pridemoths", "polysexual_moth_bottle"),
				new MothBottleItem(MothVariant.POLYSEXUAL.getVariation(), new FabricItemSettings(), new Identifier("pridemoths", "textures/item/bottle/polysexual.png")));
		OMNISEXUAL_MOTH_BOTTLE = Registry.register(Registries.ITEM, new Identifier("pridemoths", "omnisexual_moth_bottle"),
				new MothBottleItem(MothVariant.OMNISEXUAL.getVariation(), new FabricItemSettings(), new Identifier("pridemoths", "textures/item/bottle/omnisexual.png")));
		DEMISEXUAL_MOTH_BOTTLE = Registry.register(Registries.ITEM, new Identifier("pridemoths", "demisexual_moth_bottle"),
				new MothBottleItem(MothVariant.DEMISEXUAL.getVariation(), new FabricItemSettings(), new Identifier("pridemoths", "textures/item/bottle/demisexual.png")));
		DEMIROMANTIC_MOTH_BOTTLE = Registry.register(Registries.ITEM, new Identifier("pridemoths", "demiromantic_moth_bottle"),
				new MothBottleItem(MothVariant.DEMIROMANTIC.getVariation(), new FabricItemSettings(), new Identifier("pridemoths", "textures/item/bottle/demiromantic.png")));
		AROACE_MOTH_BOTTLE = Registry.register(Registries.ITEM, new Identifier("pridemoths", "aroace_moth_bottle"),
				new MothBottleItem(MothVariant.AROACE.getVariation(), new FabricItemSettings(), new Identifier("pridemoths", "textures/item/bottle/aroace.png")));
		DEMIBOY_MOTH_BOTTLE = Registry.register(Registries.ITEM, new Identifier("pridemoths", "demiboy_moth_bottle"),
				new MothBottleItem(MothVariant.DEMIBOY.getVariation(), new FabricItemSettings(), new Identifier("pridemoths", "textures/item/bottle/demiboy.png")));
		DEMIGIRL_MOTH_BOTTLE = Registry.register(Registries.ITEM, new Identifier("pridemoths", "demigirl_moth_bottle"),
				new MothBottleItem(MothVariant.DEMIGIRL.getVariation(), new FabricItemSettings(), new Identifier("pridemoths", "textures/item/bottle/demigirl.png")));
		DEMIGENDER_MOTH_BOTTLE = Registry.register(Registries.ITEM, new Identifier("pridemoths", "demigender_moth_bottle"),
				new MothBottleItem(MothVariant.DEMIGENDER.getVariation(), new FabricItemSettings(), new Identifier("pridemoths", "textures/item/bottle/demigender.png")));
		AROMANTIC_MOTH_BOTLE = Registry.register(Registries.ITEM, new Identifier("pridemoths", "aromantic_moth_bottle"),
				new MothBottleItem(MothVariant.AROMANTIC.getVariation(), new FabricItemSettings(), new Identifier("pridemoths", "textures/item/bottle/aromantic.png")));

		MOTH_MASTER_POTION = Registry.register(Registries.POTION, new Identifier("pridemoths", "moth_master"),
				new Potion(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 1200, 0),
						new StatusEffectInstance(StatusEffects.RESISTANCE, 760, 1),
						new StatusEffectInstance(StatusEffects.NIGHT_VISION, 840, 0)));

		BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(Potions.SLOW_FALLING, MOTH_WING, MOTH_MASTER_POTION);

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.add(MOTH_WING));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(ORANGE_MOTH_BOTTLE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(BLUE_MOTH_BOTTLE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(YELLOW_MOTH_BOTTLE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(GREEN_MOTH_BOTTLE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(PALOS_VERDES_BLUE_MOTH_BOTTLE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(TRANS_MOTH_BOTTLE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(LGBT_MOTH_BOTTLE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(NON_BINARY_MOTH_BOTTLE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(AGENDER_MOTH_BOTTLE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(GAY_MOTH_BOTTLE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(LESBIAN_MOTH_BOTTLE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(ASEXUAL_MOTH_BOTTLE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(PANSEXUAL_MOTH_BOTTLE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(BISEXUAL_MOTH_BOTTLE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(POLYAMOROUS_MOTH_BOTTLE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(POLYSEXUAL_MOTH_BOTTLE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(OMNISEXUAL_MOTH_BOTTLE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(DEMISEXUAL_MOTH_BOTTLE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(DEMIROMANTIC_MOTH_BOTTLE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(AROACE_MOTH_BOTTLE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(DEMIBOY_MOTH_BOTTLE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(DEMIGIRL_MOTH_BOTTLE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(DEMIGENDER_MOTH_BOTTLE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> entries.add(MOTH_SPAWN_EGG));

		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Happy Pride Moth Mate!");

		GeckoLib.initialize();
	}
}
