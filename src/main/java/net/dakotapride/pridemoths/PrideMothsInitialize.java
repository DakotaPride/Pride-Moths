package net.dakotapride.pridemoths;

import net.dakotapride.pridemoths.config.PrideMothsConfigs;
import net.dakotapride.pridemoths.register.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;

import java.util.logging.Logger;

public class PrideMothsInitialize implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static String MOD_ID = "pridemoths";
	public static final Logger LOGGER = Logger.getLogger("Pride Moths");

	public static TagKey<Block> LIGHT_SOURCES_TAG = TagKey.of(Registries.BLOCK.getKey(), Identifier.of(PrideMothsInitialize.MOD_ID, "light_sources"));
	public static TagKey<Block> MOTH_ENCLOSURES = TagKey.of(Registries.BLOCK.getKey(), Identifier.of(PrideMothsInitialize.MOD_ID, "moth_enclosures"));
	public static TagKey<Item> CAN_MOTH_EAT = TagKey.of(Registries.ITEM.getKey(), Identifier.of(PrideMothsInitialize.MOD_ID, "can_moth_eat"));
	public static TagKey<Item> MOTH_JARS = TagKey.of(Registries.ITEM.getKey(), Identifier.of(PrideMothsInitialize.MOD_ID, "moth_jars"));
	//public static TagKey<PointOfInterestType> MOTH_HOME = TagKey.of(Registries.POINT_OF_INTEREST_TYPE.getKey(), Identifier.of(PrideMothsInitialize.MOD_ID, "moth_home"));

	public static final IntProperty FUZZ_LEVEL = IntProperty.of("fuzz_level", 0, 3);

	public static RegistryKey<EntityType<?>> keyOf(String id) {
		return RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(MOD_ID, id));
	}

	public static RegistryKey<Block> keyOfBlock(String name) {
		return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(MOD_ID, name));
	}

	public static RegistryKey<Item> keyOfItem(String name) {
		return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, name));
	}

	@Override
	public void onInitialize() {
		PrideMothsConfigs.registerConfigs();

		ItemsRegistrar.yep();
		BlocksRegistrar.yep();
		BlockEntityTypeRegistrar.yep();
		EntityTypeRegistrar.yep();
		DataComponentsRegistrar.yep();

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> entries.add(ItemsRegistrar.MOTH_SPAWN_EGG));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.GLASS_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.RARE_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.TRANSGENDER_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.LGBT_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.NON_BINARY_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.LESBIAN_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.GAY_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.AGENDER_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.ASEXUAL_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.PANSEXUAL_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.BISEXUAL_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.POLYAMOROUS_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.POLYSEXUAL_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.OMNISEXUAL_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.AROMANTIC_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.DEMISEXUAL_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.DEMIBOY_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.DEMIGIRL_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.DEMIGENDER_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.AROACE_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.DEMIROMANTIC_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.GENDERFLUID_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.INTERSEX_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.XENOGENDER_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.GENDER_QUEER_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.GENDERFAE_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.GENDERFAUN_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.BIGENDER_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.PANGENDER_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ItemsRegistrar.ALLY_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> entries.add(BlocksRegistrar.FUZZY_CARPET));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> entries.add(BlocksRegistrar.MOTH_ENCLOSURE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> entries.add(ItemsRegistrar.MOTH_FUZZ));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.add(ItemsRegistrar.FRUITFUL_STEW));

		// GeckoLib.initialize();

		if (!FabricLoader.getInstance().isDevelopmentEnvironment()) {
			System.out.println("[Happy Pride Moth!] Development Tool not present. If this is a development environment, you can ignore this message.");
		}
	}
}
