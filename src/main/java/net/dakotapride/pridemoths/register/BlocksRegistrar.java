package net.dakotapride.pridemoths.register;

import net.dakotapride.pridemoths.PrideMothsInitialize;
import net.dakotapride.pridemoths.block.FuzzyCarpetBlock;
import net.dakotapride.pridemoths.block.MothEnclosureBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class BlocksRegistrar {
    public static Block FUZZY_CARPET = registerBlock("fuzzy_carpet", FuzzyCarpetBlock::new,
            AbstractBlock.Settings.copy(Blocks.MOSS_CARPET).sounds(BlockSoundGroup.WOOL), true);
//    public static BlockItem FUZZY_CARPET_ITEM = ItemsRegistrar.registerItem("fuzzy_carpet",
//            settings -> new BlockItem(FUZZY_CARPET, settings), new Item.Settings());

    public static Block MOTH_ENCLOSURE = registerBlock("moth_enclosure", MothEnclosureBlock::new,
            AbstractBlock.Settings.copy(Blocks.BEEHIVE), true);
//    public static BlockItem MOTH_ENCLOSURE_ITEM = Registry.register(Registries.ITEM, Identifier.of("pridemoths", "moth_enclosure"),
//            new BlockItem(MOTH_ENCLOSURE, new Item.Settings()));

    private static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings, boolean shouldRegisterItem) {
        // Create a registry key for the block
        RegistryKey<Block> blockKey = PrideMothsInitialize.keyOfBlock(name);
        // Create the block instance
        Block block = blockFactory.apply(settings.registryKey(blockKey));

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            // Items need to be registered with a different type of registry key, but the ID
            // can be the same.
            RegistryKey<Item> itemKey = PrideMothsInitialize.keyOfItem(name);

            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey).translationKey("block.pridemoths." + name));
            Registry.register(Registries.ITEM, itemKey, blockItem);
        }

        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    public static void yep() {

    }
}
