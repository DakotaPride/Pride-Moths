package net.dakotapride.pridemoths.client;

import net.dakotapride.pridemoths.client.model.MothModel;
import net.dakotapride.pridemoths.client.renderer.MothRenderer;
import net.dakotapride.pridemoths.register.EntityTypeRegistrar;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class PrideMothsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
//        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
//            if (!itemStack.isOf(BlocksRegistrar.FUZZY_CARPET.asItem()))
//                return;
//            list.add(Text.translatable("text.pridemoths.fuzzy_carpet.details").formatted(Formatting.ITALIC).formatted(Formatting.GRAY));
//        });
//        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
//            if (!itemStack.isOf(BlocksRegistrar.MOTH_ENCLOSURE.asItem()))
//                return;
//            for (ItemStack ignored : itemStack.getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).iterateNonEmpty()) {
//                if (itemStack.isIn(PrideMothsInitialize.MOTH_JARS) && itemStack.getItem() instanceof GlassJarItem jarItem) {
//                    list.add(Text.translatable("container.mothEnclosure.itemCount." +
//                            GlassJarItem.getMothVariant(jarItem).getVariation()).formatted(Formatting.ITALIC, Formatting.GRAY));
//                }
//            }
//        });
        EntityModelLayerRegistry.registerModelLayer(MothModel.LAYER_LOCATION, MothModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityTypeRegistrar.MOTH, MothRenderer::new);

    }
}
