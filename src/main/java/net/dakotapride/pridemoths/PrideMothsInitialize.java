package net.dakotapride.pridemoths;

import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.entity.MothVariant;
import net.dakotapride.pridemoths.item.MothBottleItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.fabricmc.fabric.impl.tag.convention.TagRegistration;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;

public class PrideMothsInitialize implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("Pride Moths");


	public static EntityType<MothEntity> MOTH;
	public static Item MOTH_SPAWN_EGG;
	public static Item MOTH_WING;
	public static Item MOTH_BOTTLE;
	public static Item TRANS_MOTH_BOTTLE;
	public static Item LGBT_MOTH_BOTTLE;
	public static Item NON_BINARY_MOTH_BOTTLE;
	public static Item AGENDER_MOTH_BOTTLE;
	public static Item ASEXUAL_MOTH_BOTTLE;
	public static Item PANSEXUAL_MOTH_BOTTLE;
	public static Item BISEXUAL_MOTH_BOTTLE;
	public static Item LESBIAN_MOTH_BOTTLE;
	public static Item GAY_MOTH_BOTTLE;

	@Override
	public void onInitialize() {

		MOTH = Registry.register(
				Registry.ENTITY_TYPE, new Identifier("pridemoths", "moth"),
				FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MothEntity::new)
						.dimensions(EntityDimensions.fixed(0.2f, 0.2f)).build());
		FabricDefaultAttributeRegistry.register(MOTH, MothEntity.setAttributes());
		BiomeModifications.addSpawn(biome -> biome.hasTag(ConventionalBiomeTags.PLAINS), SpawnGroup.CREATURE, MOTH, 60, 3, 7);

		MOTH_SPAWN_EGG = Registry.register(Registry.ITEM, new Identifier("pridemoths", "moth_spawn_egg"),
				new SpawnEggItem(MOTH, 0xB1552D, 0x921F12, new FabricItemSettings()));

		MOTH_WING = Registry.register(Registry.ITEM, new Identifier("pridemoths", "moth_wing"),
				new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().saturationModifier(0.3F).hunger(1).snack()
						.statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA), 1.0F)
						.statusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING), 1.0F).build())));

		MOTH_BOTTLE = Registry.register(Registry.ITEM, new Identifier("pridemoths", "moth_bottle"),
				new MothBottleItem(MothVariant.DEFAULT.getVariation(), new FabricItemSettings().group(ItemGroup.BREWING), new Identifier("pridemoths", "textures/item/bottle/default.png")));
		TRANS_MOTH_BOTTLE = Registry.register(Registry.ITEM, new Identifier("pridemoths", "trans_moth_bottle"),
				new MothBottleItem(MothVariant.TRANS.getVariation(), new FabricItemSettings().group(ItemGroup.BREWING), new Identifier("pridemoths", "textures/item/bottle/trans.png")));
		LGBT_MOTH_BOTTLE = Registry.register(Registry.ITEM, new Identifier("pridemoths", "lgbt_moth_bottle"),
				new MothBottleItem(MothVariant.LGBT.getVariation(), new FabricItemSettings().group(ItemGroup.BREWING), new Identifier("pridemoths", "textures/item/bottle/lgbt.png")));
		NON_BINARY_MOTH_BOTTLE = Registry.register(Registry.ITEM, new Identifier("pridemoths", "non_binary_moth_bottle"),
				new MothBottleItem(MothVariant.NON_BINARY.getVariation(), new FabricItemSettings().group(ItemGroup.BREWING), new Identifier("pridemoths", "textures/item/bottle/non_binary.png")));
		AGENDER_MOTH_BOTTLE = Registry.register(Registry.ITEM, new Identifier("pridemoths", "agender_moth_bottle"),
				new MothBottleItem(MothVariant.AGENDER.getVariation(), new FabricItemSettings().group(ItemGroup.BREWING), new Identifier("pridemoths", "textures/item/bottle/agender.png")));
		GAY_MOTH_BOTTLE = Registry.register(Registry.ITEM, new Identifier("pridemoths", "gay_moth_bottle"),
				new MothBottleItem(MothVariant.GAY.getVariation(), new FabricItemSettings().group(ItemGroup.BREWING), new Identifier("pridemoths", "textures/item/bottle/gay.png")));
		LESBIAN_MOTH_BOTTLE = Registry.register(Registry.ITEM, new Identifier("pridemoths", "lesbian_moth_bottle"),
				new MothBottleItem(MothVariant.LESBIAN.getVariation(), new FabricItemSettings().group(ItemGroup.BREWING), new Identifier("pridemoths", "textures/item/bottle/lesbian.png")));
		ASEXUAL_MOTH_BOTTLE = Registry.register(Registry.ITEM, new Identifier("pridemoths", "asexual_moth_bottle"),
				new MothBottleItem(MothVariant.ASEXUAL.getVariation(), new FabricItemSettings().group(ItemGroup.BREWING), new Identifier("pridemoths", "textures/item/bottle/asexual.png")));
		BISEXUAL_MOTH_BOTTLE = Registry.register(Registry.ITEM, new Identifier("pridemoths", "bisexual_moth_bottle"),
				new MothBottleItem(MothVariant.BISEXUAL.getVariation(), new FabricItemSettings().group(ItemGroup.BREWING), new Identifier("pridemoths", "textures/item/bottle/bisexual.png")));
		PANSEXUAL_MOTH_BOTTLE = Registry.register(Registry.ITEM, new Identifier("pridemoths", "pansexual_moth_bottle"),
				new MothBottleItem(MothVariant.PANSEXUAL.getVariation(), new FabricItemSettings().group(ItemGroup.BREWING), new Identifier("pridemoths", "textures/item/bottle/pansexual.png")));

		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Happy Pride Moth Mate!");

		GeckoLib.initialize();
	}
}
