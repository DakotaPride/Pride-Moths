package net.dakotapride.pridemoths;

import net.dakotapride.pridemoths.block.FuzzyCarpetBlock;
import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.dakotapride.pridemoths.config.PrideMothsConfigs;
import net.dakotapride.pridemoths.item.FruitfulStewFoodItem;
import net.dakotapride.pridemoths.item.GlassJarItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;

import java.util.function.Function;
import java.util.logging.Logger;

public class PrideMothsInitialize implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static String ID = "pridemoths";
	public static final Logger LOGGER = Logger.getLogger("Pride Moths");

	public static TagKey<Block> LIGHT_SOURCES_TAG = TagKey.of(Registries.BLOCK.getKey(), Identifier.of(ID, "light_sources"));
	public static TagKey<Item> CAN_MOTH_EAT = TagKey.of(Registries.ITEM.getKey(), Identifier.of(ID, "can_moth_eat"));

	public static EntityType<MothEntity> MOTH;
	public static Item MOTH_SPAWN_EGG;
	public static Item MOTH_FUZZ;
	public static Item FRUITFUL_STEW;
	public static Item GLASS_JAR;
	public static Item MOTH_JAR;
	public static Item RARE_MOTH_JAR;
	public static Item TRANSGENDER_MOTH_JAR;
	public static Item LGBT_MOTH_JAR;
	public static Item NON_BINARY_MOTH_JAR;
	public static Item LESBIAN_MOTH_JAR;
	public static Item GAY_MOTH_JAR;
	public static Item AGENDER_MOTH_JAR;
	public static Item ASEXUAL_MOTH_JAR;
	public static Item PANSEXUAL_MOTH_JAR;
	public static Item BISEXUAL_MOTH_JAR;
	public static Item POLYAMOROUS_MOTH_JAR;
	public static Item POLYSEXUAL_MOTH_JAR;
	public static Item OMNISEXUAL_MOTH_JAR;
	public static Item AROMANTIC_MOTH_JAR;
	public static Item DEMISEXUAL_MOTH_JAR;
	public static Item DEMIBOY_MOTH_JAR;
	public static Item DEMIGIRL_MOTH_JAR;
	public static Item DEMIGENDER_MOTH_JAR;
	public static Item AROACE_MOTH_JAR;
	public static Item DEMIROMANTIC_MOTH_JAR;
	public static Item ALLY_MOTH_JAR;
	public static Block FUZZY_CARPET;
	//public static BlockItem FUZZY_CARPET_ITEM;

	private static RegistryKey<EntityType<?>> keyOf(String id) {
		return RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(ID, id));
	}

	public static Item registerItem(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
		// Create the item key.
		RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(ID, name));

		// Create the item instance.
		Item item = itemFactory.apply(settings.registryKey(itemKey));

		// Register the item.
		Registry.register(Registries.ITEM, itemKey, item);

		return item;
	}

	private static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings, boolean shouldRegisterItem) {
		// Create a registry key for the block
		RegistryKey<Block> blockKey = keyOfBlock(name);
		// Create the block instance
		Block block = blockFactory.apply(settings.registryKey(blockKey));

		// Sometimes, you may not want to register an item for the block.
		// Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
		if (shouldRegisterItem) {
			// Items need to be registered with a different type of registry key, but the ID
			// can be the same.
			RegistryKey<Item> itemKey = keyOfItem(name);

			BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
			Registry.register(Registries.ITEM, itemKey, blockItem);
		}

		return Registry.register(Registries.BLOCK, blockKey, block);
	}

	private static RegistryKey<Block> keyOfBlock(String name) {
		return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(ID, name));
	}

	private static RegistryKey<Item> keyOfItem(String name) {
		return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(ID, name));
	}

	private static Item registerMothContainedInJarItem(MothVariation variation) {
		return registerItem(variation.getVariation() + "_moth_jar", GlassJarItem::new, new Item.Settings());
	}

	@Override
	public void onInitialize() {
		PrideMothsConfigs.registerConfigs();

		MOTH = Registry.register(
				Registries.ENTITY_TYPE, Identifier.of(ID, "moth"),
				EntityType.Builder.create(MothEntity::new, SpawnGroup.CREATURE)
						.dimensions(0.45F, 0.45F).build(keyOf("moth")));
		FabricDefaultAttributeRegistry.register(MOTH, MothEntity.setAttributes());
		BiomeModifications.addSpawn(biome -> biome.getBiomeKey().equals(BiomeKeys.CHERRY_GROVE),
				SpawnGroup.CREATURE, MOTH, 60, 3, 7);
		BiomeModifications.addSpawn(biome -> biome.getBiomeKey().equals(BiomeKeys.PLAINS),
				SpawnGroup.CREATURE, MOTH, 100, 3, 7);

		FUZZY_CARPET = registerBlock("fuzzy_carpet", FuzzyCarpetBlock::new,
				AbstractBlock.Settings.copy(Blocks.MOSS_CARPET), true);

//		FUZZY_CARPET = Registry.register(Registries.BLOCK, Identifier.of(ID, "fuzzy_carpet"),
//				new FuzzyCarpetBlock(AbstractBlock.Settings.copy(Blocks.MOSS_CARPET).sounds(BlockSoundGroup.WOOL)));
//		FUZZY_CARPET_ITEM = Registry.register(Registries.ITEM, Identifier.of(ID, "fuzzy_carpet"),
//				new BlockItem(FUZZY_CARPET, new Item.Settings()));

		MOTH_SPAWN_EGG = registerItem("moth_spawn_egg", settings -> new SpawnEggItem(MOTH, settings), new Item.Settings());
		MOTH_FUZZ = registerItem("moth_fuzz", Item::new, new Item.Settings());
		FRUITFUL_STEW = registerItem("fruitful_stew", FruitfulStewFoodItem::new, new Item.Settings().maxCount(1));
		GLASS_JAR = registerItem("glass_jar", settings -> new GlassJarItem(true, settings), new Item.Settings());
		MOTH_JAR = registerItem("moth_jar", GlassJarItem::new, new Item.Settings());
		TRANSGENDER_MOTH_JAR = registerMothContainedInJarItem(MothVariation.TRANSGENDER);
		LGBT_MOTH_JAR = registerMothContainedInJarItem(MothVariation.LGBT);
		NON_BINARY_MOTH_JAR = registerMothContainedInJarItem(MothVariation.NON_BINARY);
		LESBIAN_MOTH_JAR = registerMothContainedInJarItem(MothVariation.LESBIAN);
		GAY_MOTH_JAR = registerMothContainedInJarItem(MothVariation.GAY);
		AGENDER_MOTH_JAR = registerMothContainedInJarItem(MothVariation.AGENDER);
		ASEXUAL_MOTH_JAR = registerMothContainedInJarItem(MothVariation.ASEXUAL);
		PANSEXUAL_MOTH_JAR = registerMothContainedInJarItem(MothVariation.PANSEXUAL);
		BISEXUAL_MOTH_JAR = registerMothContainedInJarItem(MothVariation.BISEXUAL);
		POLYAMOROUS_MOTH_JAR = registerMothContainedInJarItem(MothVariation.POLYAMOROUS);
		POLYSEXUAL_MOTH_JAR = registerMothContainedInJarItem(MothVariation.POLYSEXUAL);
		OMNISEXUAL_MOTH_JAR = registerMothContainedInJarItem(MothVariation.OMNISEXUAL);
		AROMANTIC_MOTH_JAR = registerMothContainedInJarItem(MothVariation.AROMANTIC);
		DEMISEXUAL_MOTH_JAR = registerMothContainedInJarItem(MothVariation.DEMISEXUAL);
		DEMIBOY_MOTH_JAR = registerMothContainedInJarItem(MothVariation.DEMIBOY);
		DEMIGIRL_MOTH_JAR = registerMothContainedInJarItem(MothVariation.DEMIGIRL);
		DEMIGENDER_MOTH_JAR = registerMothContainedInJarItem(MothVariation.DEMIGENDER);
		AROACE_MOTH_JAR = registerMothContainedInJarItem(MothVariation.AROACE);
		DEMIROMANTIC_MOTH_JAR = registerMothContainedInJarItem(MothVariation.DEMIROMANTIC);
		ALLY_MOTH_JAR = registerMothContainedInJarItem(MothVariation.ALLY);
		RARE_MOTH_JAR = registerMothContainedInJarItem(MothVariation.RARE);

//		MOTH_SPAWN_EGG = Registry.register(Registries.ITEM, Identifier.of(ID, "moth_spawn_egg"),
//				new SpawnEggItem(MOTH,
//						//0xCECAC4, 0x82635C,
//						new Item.Settings()));
//		MOTH_FUZZ = Registry.register(Registries.ITEM, Identifier.of(ID, "moth_fuzz"),
//				new Item(new Item.Settings()));
//		FRUITFUL_STEW = Registry.register(Registries.ITEM, Identifier.of(ID, "fruitful_stew"),
//				new FruitfulStewFoodItem(new Item.Settings().maxCount(1)));
//		GLASS_JAR = Registry.register(Registries.ITEM,
//				Identifier.of(ID, "glass_jar"),
//				new GlassJarItem(true, new Item.Settings()));
//		MOTH_JAR = Registry.register(Registries.ITEM,
//				Identifier.of(ID, "moth_jar"),
//				new GlassJarItem(new Item.Settings()));
//		TRANSGENDER_MOTH_JAR = Registry.register(Registries.ITEM,
//				Identifier.of(ID, MothVariation.TRANSGENDER.getVariation() + "_moth_jar"),
//				new GlassJarItem(new Item.Settings().maxCount(1)));
//		LGBT_MOTH_JAR = Registry.register(Registries.ITEM,
//				Identifier.of(ID, MothVariation.LGBT.getVariation() + "_moth_jar"),
//				new GlassJarItem(new Item.Settings().maxCount(1)));
//		NON_BINARY_MOTH_JAR = Registry.register(Registries.ITEM,
//				Identifier.of(ID, MothVariation.NON_BINARY.getVariation() + "_moth_jar"),
//				new GlassJarItem(new Item.Settings().maxCount(1)));
//		LESBIAN_MOTH_JAR = Registry.register(Registries.ITEM,
//				Identifier.of(ID, MothVariation.LESBIAN.getVariation() + "_moth_jar"),
//				new GlassJarItem(new Item.Settings().maxCount(1)));
//		GAY_MOTH_JAR = Registry.register(Registries.ITEM,
//				Identifier.of(ID, MothVariation.GAY.getVariation() + "_moth_jar"),
//				new GlassJarItem(new Item.Settings().maxCount(1)));
//		AGENDER_MOTH_JAR = Registry.register(Registries.ITEM,
//				Identifier.of(ID, MothVariation.AGENDER.getVariation() + "_moth_jar"),
//				new GlassJarItem(new Item.Settings().maxCount(1)));
//		ASEXUAL_MOTH_JAR = Registry.register(Registries.ITEM,
//				Identifier.of(ID, MothVariation.ASEXUAL.getVariation() + "_moth_jar"),
//				new GlassJarItem(new Item.Settings().maxCount(1)));
//		PANSEXUAL_MOTH_JAR = Registry.register(Registries.ITEM,
//				Identifier.of(ID, MothVariation.PANSEXUAL.getVariation() + "_moth_jar"),
//				new GlassJarItem(new Item.Settings().maxCount(1)));
//		BISEXUAL_MOTH_JAR = Registry.register(Registries.ITEM,
//				Identifier.of(ID, MothVariation.BISEXUAL.getVariation() + "_moth_jar"),
//				new GlassJarItem(new Item.Settings().maxCount(1)));
//		POLYAMOROUS_MOTH_JAR = Registry.register(Registries.ITEM,
//				Identifier.of(ID, MothVariation.POLYAMOROUS.getVariation() + "_moth_jar"),
//				new GlassJarItem(new Item.Settings().maxCount(1)));
//		POLYSEXUAL_MOTH_JAR = Registry.register(Registries.ITEM,
//				Identifier.of(ID, MothVariation.POLYSEXUAL.getVariation() + "_moth_jar"),
//				new GlassJarItem(new Item.Settings().maxCount(1)));
//		OMNISEXUAL_MOTH_JAR = Registry.register(Registries.ITEM,
//				Identifier.of(ID, MothVariation.OMNISEXUAL.getVariation() + "_moth_jar"),
//				new GlassJarItem(new Item.Settings().maxCount(1)));
//		AROMANTIC_MOTH_JAR = Registry.register(Registries.ITEM,
//				Identifier.of(ID, MothVariation.AROMANTIC.getVariation() + "_moth_jar"),
//				new GlassJarItem(new Item.Settings().maxCount(1)));
//		DEMISEXUAL_MOTH_JAR = Registry.register(Registries.ITEM,
//				Identifier.of(ID, MothVariation.DEMISEXUAL.getVariation() + "_moth_jar"),
//				new GlassJarItem(new Item.Settings().maxCount(1)));
//		DEMIBOY_MOTH_JAR = Registry.register(Registries.ITEM,
//				Identifier.of(ID, MothVariation.DEMIBOY.getVariation() + "_moth_jar"),
//				new GlassJarItem(new Item.Settings().maxCount(1)));
//		DEMIGIRL_MOTH_JAR = Registry.register(Registries.ITEM,
//				Identifier.of(ID, MothVariation.DEMIGIRL.getVariation() + "_moth_jar"),
//				new GlassJarItem(new Item.Settings().maxCount(1)));
//		DEMIGENDER_MOTH_JAR = Registry.register(Registries.ITEM,
//				Identifier.of(ID, MothVariation.DEMIGENDER.getVariation() + "_moth_jar"),
//				new GlassJarItem(new Item.Settings().maxCount(1)));
//		AROACE_MOTH_JAR = Registry.register(Registries.ITEM,
//				Identifier.of(ID, MothVariation.AROACE.getVariation() + "_moth_jar"),
//				new GlassJarItem(new Item.Settings().maxCount(1)));
//		DEMIROMANTIC_MOTH_JAR = Registry.register(Registries.ITEM,
//				Identifier.of(ID, MothVariation.DEMIROMANTIC.getVariation() + "_moth_jar"),
//				new GlassJarItem(new Item.Settings().maxCount(1)));
//		ALLY_MOTH_JAR = Registry.register(Registries.ITEM,
//				Identifier.of(ID, MothVariation.ALLY.getVariation() + "_moth_jar"),
//				new GlassJarItem(new Item.Settings().maxCount(1)));
//		RARE_MOTH_JAR = Registry.register(Registries.ITEM,
//				Identifier.of(ID, MothVariation.RARE.getVariation() + "_moth_jar"),
//				new GlassJarItem(new Item.Settings().maxCount(1)));

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> entries.add(MOTH_SPAWN_EGG));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(GLASS_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(RARE_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(TRANSGENDER_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(LGBT_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(NON_BINARY_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(LESBIAN_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(GAY_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(AGENDER_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ASEXUAL_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(PANSEXUAL_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(BISEXUAL_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(POLYAMOROUS_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(POLYSEXUAL_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(OMNISEXUAL_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(AROMANTIC_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(DEMISEXUAL_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(DEMIBOY_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(DEMIGIRL_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(DEMIGENDER_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(AROACE_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(DEMIROMANTIC_MOTH_JAR));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ALLY_MOTH_JAR));

		// GeckoLib.initialize();

		if (!FabricLoader.getInstance().isDevelopmentEnvironment()) {
			System.out.println("[Happy Pride Moth!] Development Tool not present. If this is a development environment, you can ignore this message.");
		}
	}
}
