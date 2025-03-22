package net.dakotapride.pridemoths.register;

import net.dakotapride.pridemoths.block.FuzzyCarpetBlock;
import net.dakotapride.pridemoths.block.MothEnclosureBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class BlocksRegistrar {
    public static Block FUZZY_CARPET = Registry.register(Registries.BLOCK, Identifier.of("pridemoths", "fuzzy_carpet"),
            new FuzzyCarpetBlock(AbstractBlock.Settings.copy(Blocks.MOSS_CARPET).sounds(BlockSoundGroup.WOOL)));
    public static BlockItem FUZZY_CARPET_ITEM = Registry.register(Registries.ITEM, Identifier.of("pridemoths", "fuzzy_carpet"),
            new BlockItem(FUZZY_CARPET, new Item.Settings()));

    public static Block MOTH_ENCLOSURE = Registry.register(Registries.BLOCK, Identifier.of("pridemoths", "moth_enclosure"),
            new MothEnclosureBlock(AbstractBlock.Settings.copy(Blocks.BEEHIVE)));
    public static BlockItem MOTH_ENCLOSURE_ITEM = Registry.register(Registries.ITEM, Identifier.of("pridemoths", "moth_enclosure"),
            new BlockItem(MOTH_ENCLOSURE, new Item.Settings()));

    public static void yep() {

    }
}
