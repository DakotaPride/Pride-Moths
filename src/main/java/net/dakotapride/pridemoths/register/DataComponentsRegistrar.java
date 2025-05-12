package net.dakotapride.pridemoths.register;

import net.dakotapride.pridemoths.PrideMothsInitialize;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

public class DataComponentsRegistrar {
//    public static final ComponentType<List<MothEnclosureBlockEntity.MothData>> MOTHS = register(
//            "moths",
//            builder -> builder.codec(MothEnclosureBlockEntity.MothData.LIST_CODEC).packetCodec(MothEnclosureBlockEntity.MothData.PACKET_CODEC.collect(PacketCodecs.toList())).cache()
//    );
    public static final ComponentType<ContainerComponent> MOTH_CONTAINER = register("container",
        builder -> builder.codec(ContainerComponent.CODEC).packetCodec(ContainerComponent.PACKET_CODEC).cache());

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(PrideMothsInitialize.MOD_ID, id), builderOperator.apply(ComponentType.builder()).build());
    }

    public static void yep() {}
}
