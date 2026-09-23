package com.soetadline.stillglow.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

/**
 * Registers an (unbound by default) keybind to open the config screen
 * directly, so players who don't have Mod Menu installed can still
 * reach every toggle — set a key for "Stillglow" under Options >
 * Controls if you want one.
 */
public class StillglowClient implements ClientModInitializer {

    private static KeyMapping openConfigKey;

    @Override
    public void onInitializeClient() {
        openConfigKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.stillglow.open_config",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                "category.stillglow"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openConfigKey.wasPressed()) {
                if (client.screen == null) {
                    client.setScreen(StillglowConfigScreen.build(null));
                }
            }
        });
    }
}
