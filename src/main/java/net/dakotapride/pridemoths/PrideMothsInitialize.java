package net.dakotapride.pridemoths;

import net.dakotapride.pridemoths.block.FuzzyCarpetBlock;
import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.dakotapride.pridemoths.item.FruitfulStewFoodItem;
import net.dakotapride.pridemoths.item.GlassJarItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.BiomeKeys;
import software.bernie.geckolib3.GeckoLib;

import java.util.logging.Logger;

public class PrideMothsInitialize implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = Logger.getLogger("Pride Moths");

	public static TagKey<Block> LIGHT_SOURCES_TAG = TagKey.of(Registry.BLOCK.getKey(), new Identifier("pridemoths", "light_sources"));

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
	public static Block FUZZY_CARPET;
	public static BlockItem FUZZY_CARPET_ITEM;

	@Override
	public void onInitialize() {

		MOTH = Registry.register(
				Registry.ENTITY_TYPE, new Identifier("pridemoths", "moth"),
				FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MothEntity::new)
						.dimensions(EntityDimensions.fixed(0.3F, 0.3F)).build());
		FabricDefaultAttributeRegistry.register(MOTH, MothEntity.setAttributes());
		// BiomeModifications.addSpawn(biome -> biome.getBiomeKey().equals(BiomeKeys.CHERRY_GROVE),
		//				SpawnGroup.CREATURE, MOTH, 60, 3, 7);
		BiomeModifications.addSpawn(biome -> biome.getBiomeKey().equals(BiomeKeys.PLAINS),
				SpawnGroup.CREATURE, MOTH, 100, 3, 7);

		FUZZY_CARPET = Registry.register(Registry.BLOCK, new Identifier("pridemoths", "fuzzy_carpet"),
				new FuzzyCarpetBlock(FabricBlockSettings.copyOf(Blocks.MOSS_CARPET).sounds(BlockSoundGroup.WOOL)));
		FUZZY_CARPET_ITEM = Registry.register(Registry.ITEM, new Identifier("pridemoths", "fuzzy_carpet"),
				new BlockItem(FUZZY_CARPET, new FabricItemSettings().group(ItemGroup.MISC)));

		MOTH_SPAWN_EGG = Registry.register(Registry.ITEM, new Identifier("pridemoths", "moth_spawn_egg"),
				new SpawnEggItem(MOTH, 0xCECAC4, 0x82635C, new FabricItemSettings().group(ItemGroup.MISC)));
		MOTH_FUZZ = Registry.register(Registry.ITEM, new Identifier("pridemoths", "moth_fuzz"),
				new Item(new FabricItemSettings().group(ItemGroup.MISC)));
		FRUITFUL_STEW = Registry.register(Registry.ITEM, new Identifier("pridemoths", "fruitful_stew"),
				new FruitfulStewFoodItem(new FabricItemSettings().maxCount(1).group(ItemGroup.FOOD)));
		GLASS_JAR = Registry.register(Registry.ITEM,
				new Identifier("pridemoths", "glass_jar"),
				new GlassJarItem(new FabricItemSettings().group(ItemGroup.TOOLS)));
		MOTH_JAR = Registry.register(Registry.ITEM,
				new Identifier("pridemoths", "moth_jar"),
				new GlassJarItem(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS)));
		TRANSGENDER_MOTH_JAR = Registry.register(Registry.ITEM,
				new Identifier("pridemoths", MothVariation.TRANSGENDER.getVariation() + "_moth_jar"),
				new GlassJarItem(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS)));
		LGBT_MOTH_JAR = Registry.register(Registry.ITEM,
				new Identifier("pridemoths", MothVariation.LGBT.getVariation() + "_moth_jar"),
				new GlassJarItem(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS)));
		NON_BINARY_MOTH_JAR = Registry.register(Registry.ITEM,
				new Identifier("pridemoths", MothVariation.NON_BINARY.getVariation() + "_moth_jar"),
				new GlassJarItem(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS)));
		LESBIAN_MOTH_JAR = Registry.register(Registry.ITEM,
				new Identifier("pridemoths", MothVariation.LESBIAN.getVariation() + "_moth_jar"),
				new GlassJarItem(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS)));
		GAY_MOTH_JAR = Registry.register(Registry.ITEM,
				new Identifier("pridemoths", MothVariation.GAY.getVariation() + "_moth_jar"),
				new GlassJarItem(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS)));
		AGENDER_MOTH_JAR = Registry.register(Registry.ITEM,
				new Identifier("pridemoths", MothVariation.AGENDER.getVariation() + "_moth_jar"),
				new GlassJarItem(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS)));
		ASEXUAL_MOTH_JAR = Registry.register(Registry.ITEM,
				new Identifier("pridemoths", MothVariation.ASEXUAL.getVariation() + "_moth_jar"),
				new GlassJarItem(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS)));
		PANSEXUAL_MOTH_JAR = Registry.register(Registry.ITEM,
				new Identifier("pridemoths", MothVariation.PANSEXUAL.getVariation() + "_moth_jar"),
				new GlassJarItem(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS)));
		BISEXUAL_MOTH_JAR = Registry.register(Registry.ITEM,
				new Identifier("pridemoths", MothVariation.BISEXUAL.getVariation() + "_moth_jar"),
				new GlassJarItem(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS)));
		POLYAMOROUS_MOTH_JAR = Registry.register(Registry.ITEM,
				new Identifier("pridemoths", MothVariation.POLYAMOROUS.getVariation() + "_moth_jar"),
				new GlassJarItem(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS)));
		POLYSEXUAL_MOTH_JAR = Registry.register(Registry.ITEM,
				new Identifier("pridemoths", MothVariation.POLYSEXUAL.getVariation() + "_moth_jar"),
				new GlassJarItem(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS)));
		OMNISEXUAL_MOTH_JAR = Registry.register(Registry.ITEM,
				new Identifier("pridemoths", MothVariation.OMNISEXUAL.getVariation() + "_moth_jar"),
				new GlassJarItem(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS)));
		AROMANTIC_MOTH_JAR = Registry.register(Registry.ITEM,
				new Identifier("pridemoths", MothVariation.AROMANTIC.getVariation() + "_moth_jar"),
				new GlassJarItem(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS)));
		DEMISEXUAL_MOTH_JAR = Registry.register(Registry.ITEM,
				new Identifier("pridemoths", MothVariation.DEMISEXUAL.getVariation() + "_moth_jar"),
				new GlassJarItem(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS)));
		DEMIBOY_MOTH_JAR = Registry.register(Registry.ITEM,
				new Identifier("pridemoths", MothVariation.DEMIBOY.getVariation() + "_moth_jar"),
				new GlassJarItem(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS)));
		DEMIGIRL_MOTH_JAR = Registry.register(Registry.ITEM,
				new Identifier("pridemoths", MothVariation.DEMIGIRL.getVariation() + "_moth_jar"),
				new GlassJarItem(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS)));
		DEMIGENDER_MOTH_JAR = Registry.register(Registry.ITEM,
				new Identifier("pridemoths", MothVariation.DEMIGENDER.getVariation() + "_moth_jar"),
				new GlassJarItem(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS)));
		AROACE_MOTH_JAR = Registry.register(Registry.ITEM,
				new Identifier("pridemoths", MothVariation.AROACE.getVariation() + "_moth_jar"),
				new GlassJarItem(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS)));
		DEMIROMANTIC_MOTH_JAR = Registry.register(Registry.ITEM,
				new Identifier("pridemoths", MothVariation.DEMIROMANTIC.getVariation() + "_moth_jar"),
				new GlassJarItem(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS)));
		RARE_MOTH_JAR = Registry.register(Registry.ITEM,
				new Identifier("pridemoths", MothVariation.RARE.getVariation() + "_moth_jar"),
				new GlassJarItem(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS)));

		GeckoLib.initialize();

		if (!FabricLoader.getInstance().isDevelopmentEnvironment()) {
			System.out.println("[Happy Pride Moth!] Development Tool not present. If this is a development environment, you can ignore this message.");
		}
	}
}
