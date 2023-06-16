package net.dakotapride.pridemoths.client;

import net.dakotapride.pridemoths.PrideMothsInitialize;
import net.dakotapride.pridemoths.client.renderer.MothRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class PrideMothsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        EntityRendererRegistry.register(PrideMothsInitialize.MOTH, MothRenderer::new);

    }
}
