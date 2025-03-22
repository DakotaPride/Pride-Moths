package net.dakotapride.pridemoths.register;

import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;

public class EntityTypeRegistrar {
    public static EntityType<MothEntity> MOTH = Registry.register(
            Registries.ENTITY_TYPE, Identifier.of("pridemoths", "moth"),
            EntityType.Builder.create(MothEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.45F, 0.45F).build());

    public static void yep() {
        FabricDefaultAttributeRegistry.register(MOTH, MothEntity.setAttributes());
        BiomeModifications.addSpawn(biome -> biome.getBiomeKey().equals(BiomeKeys.CHERRY_GROVE),
                SpawnGroup.CREATURE, MOTH, 60, 3, 7);
        BiomeModifications.addSpawn(biome -> biome.getBiomeKey().equals(BiomeKeys.PLAINS),
                SpawnGroup.CREATURE, MOTH, 100, 3, 7);
    }
}
