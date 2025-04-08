package net.dakotapride.pridemoths.register;

import net.dakotapride.pridemoths.PrideMothsInitialize;
import net.dakotapride.pridemoths.block.MothEnclosureBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BlockEntityTypeRegistrar {
    public static BlockEntityType<MothEnclosureBlockEntity> MOTH_ENCLOSURE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(PrideMothsInitialize.MOD_ID, "moth_enclosure"),
            BlockEntityType.Builder.create(MothEnclosureBlockEntity::new, BlocksRegistrar.MOTH_ENCLOSURE).build(null));

    public static void yep() {

    }
}
