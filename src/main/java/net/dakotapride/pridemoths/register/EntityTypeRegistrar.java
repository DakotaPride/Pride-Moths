package net.dakotapride.pridemoths.register;

import net.dakotapride.pridemoths.PrideMothsInitialize;
import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;

public class EntityTypeRegistrar {
    public static EntityType<MothEntity> MOTH = registerEntityType("moth", MothEntity::new, SpawnGroup.CREATURE, 0.45F, 0.45F);

    public static <T extends Entity> EntityType<T> registerEntityType(String id, EntityType.EntityFactory<T> entityType, SpawnGroup group, float width, float height) {
        return Registry.register(
                Registries.ENTITY_TYPE, Identifier.of(PrideMothsInitialize.ID, id),
                EntityType.Builder.create(entityType, group)
                        .dimensions(width, height).build(PrideMothsInitialize.keyOf(id)));
    }

    public static void yep() {
        FabricDefaultAttributeRegistry.register(MOTH, MothEntity.setAttributes());
        BiomeModifications.addSpawn(biome -> biome.getBiomeKey().equals(BiomeKeys.CHERRY_GROVE),
                SpawnGroup.CREATURE, MOTH, 60, 3, 7);
        BiomeModifications.addSpawn(biome -> biome.getBiomeKey().equals(BiomeKeys.PLAINS),
                SpawnGroup.CREATURE, MOTH, 100, 3, 7);
    }
}
