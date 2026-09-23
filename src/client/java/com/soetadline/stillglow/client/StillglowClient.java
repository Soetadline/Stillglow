package com.soetadline.stillglow.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

/**
 * Registers an (unbound by default) keybind to open the config screen
 * directly, so players who don't have Mod Menu installed can still
 * reach every toggle — set a key for "Stillglow" under Options >
 * Controls if you want one.
 */
public class StillglowClient implements ClientModInitializer {

    private static KeyBinding openConfigKey;

    @Override
    public void onInitializeClient() {
        openConfigKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.stillglow.open_config",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                "category.stillglow"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openConfigKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(StillglowConfigScreen.build(null));
                }
            }
        });
    }
}
