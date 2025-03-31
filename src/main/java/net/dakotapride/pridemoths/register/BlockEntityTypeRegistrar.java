package net.dakotapride.pridemoths.register;

import net.dakotapride.pridemoths.PrideMothsInitialize;
import net.dakotapride.pridemoths.block.MothEnclosureBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BlockEntityTypeRegistrar {
//    public static BlockEntityType<MothEnclosureBlockEntity> MOTH_ENCLOSURE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of("pridemoths", "moth_enclosure"),
//            BlockEntityType.Builder.create(MothEnclosureBlockEntity::new, BlocksRegistrar.MOTH_ENCLOSURE).build(null));

    public static final BlockEntityType<MothEnclosureBlockEntity> MOTH_ENCLOSURE_BLOCK_ENTITY =
            register("moth_enclosure", MothEnclosureBlockEntity::new, BlocksRegistrar.MOTH_ENCLOSURE);

    private static <T extends BlockEntity> BlockEntityType<T> register(String name,
                                                                       FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
                                                                       Block... blocks) {
        Identifier id = Identifier.of(PrideMothsInitialize.ID, name);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }

    public static void yep() {

    }
}
