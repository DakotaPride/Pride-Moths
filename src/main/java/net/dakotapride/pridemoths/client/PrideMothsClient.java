package net.dakotapride.pridemoths.client;

import net.dakotapride.pridemoths.PrideMothsInitialize;
import net.dakotapride.pridemoths.client.renderer.MothRenderer;
import net.dakotapride.pridemoths.item.GlassJarItem;
import net.dakotapride.pridemoths.register.BlocksRegistrar;
import net.dakotapride.pridemoths.register.EntityTypeRegistrar;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class PrideMothsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
            if (!itemStack.isOf(BlocksRegistrar.FUZZY_CARPET.asItem()))
                return;
            list.add(Text.translatable("text.pridemoths.fuzzy_carpet.details").formatted(Formatting.ITALIC).formatted(Formatting.GRAY));
        });
        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
            if (!itemStack.isOf(BlocksRegistrar.MOTH_ENCLOSURE.asItem()))
                return;
            for (ItemStack ignored : itemStack.getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).iterateNonEmpty()) {
                if (itemStack.isIn(PrideMothsInitialize.MOTH_JARS) && itemStack.getItem() instanceof GlassJarItem jarItem) {
                    list.add(Text.translatable("container.mothEnclosure.itemCount." +
                            GlassJarItem.getMothVariant(jarItem).getVariation()).formatted(Formatting.ITALIC, Formatting.GRAY));
                }
            }
        });

        EntityRendererRegistry.register(EntityTypeRegistrar.MOTH, MothRenderer::new);

    }
}
