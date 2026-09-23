package com.soetadline.stillglow.client;

import com.soetadline.stillglow.config.StillglowConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * Built by hand (rather than AutoConfig's auto-generated screen) so the
 * "About" tab can carry a real clickable link to the dev's GitHub,
 * which plain data-annotation screens can't do.
 */
public final class StillglowConfigScreen {

    private StillglowConfigScreen() {
    }

    public static Screen build(Screen parent) {
        ConfigHolder<StillglowConfig> holder = AutoConfig.getConfigHolder(StillglowConfig.class);
        StillglowConfig cfg = holder.getConfig();
        ConfigEntryBuilder eb = ConfigEntryBuilder.create();

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.literal("Stillglow"))
                .setSavingRunnable(holder::save);

        // ---- Performance -----------------------------------------------
        ConfigCategory performance = builder.getOrCreateCategory(Component.literal("Performance"));

        performance.addEntry(eb.startBooleanToggle(Component.literal("Merge XP orbs"), cfg.performance.mergeExperienceOrbs)
                .setDefaultValue(false)
                .setTooltip(Component.literal("Combines nearby ground XP orbs into one entity, about once a second."))
                .setSaveConsumer(v -> cfg.performance.mergeExperienceOrbs = v)
                .build());

        performance.addEntry(eb.startIntSlider(Component.literal("Merge radius"), cfg.performance.mergeRadius, 1, 16)
                .setDefaultValue(4)
                .setTooltip(Component.literal("Orbs within this many blocks of each other get combined."))
                .setSaveConsumer(v -> cfg.performance.mergeRadius = v)
                .build());

        performance.addEntry(eb.startBooleanToggle(Component.literal("Disable XP orb color-cycle"), cfg.performance.disableOrbColorAnimation)
                .setDefaultValue(false)
                .setTooltip(Component.literal("Removes the shimmering color change. The bobbing motion is unaffected."))
                .setSaveConsumer(v -> cfg.performance.disableOrbColorAnimation = v)
                .build());

        // ---- Visuals ------------------------------------------------------
        ConfigCategory visuals = builder.getOrCreateCategory(Component.literal("Visuals"));

        visuals.addEntry(eb.startBooleanToggle(Component.literal("Infinite Night Vision"), cfg.visuals.infiniteNightVision)
                .setDefaultValue(false)
                .setTooltip(Component.literal("Client-side brightness only — does not apply a real status effect."))
                .setSaveConsumer(v -> cfg.visuals.infiniteNightVision = v)
                .build());

        visuals.addEntry(eb.startBooleanToggle(Component.literal("Disable sword enchant glint"), cfg.visuals.disableSwordEnchantGlint)
                .setDefaultValue(false)
                .setSaveConsumer(v -> cfg.visuals.disableSwordEnchantGlint = v)
                .build());

        visuals.addEntry(eb.startBooleanToggle(Component.literal("Still plants (remove random offset)"), cfg.visuals.stillPlants)
                .setDefaultValue(false)
                .setTooltip(Component.literal("Vanilla has no wind-sway to disable; this removes the static per-block "
                        + "jitter on grass/flowers instead. For shader-pack wind, use the shader pack's own settings."))
                .setSaveConsumer(v -> cfg.visuals.stillPlants = v)
                .build());

        visuals.addEntry(eb.startBooleanToggle(Component.literal("Dark Mode UI"), cfg.visuals.darkModeUI)
                .setDefaultValue(false)
                .setTooltip(Component.literal("Translucent dark tint over inventories and menus."))
                .setSaveConsumer(v -> cfg.visuals.darkModeUI = v)
                .build());

        // ---- Explosions -----------------------------------------------
        ConfigCategory explosions = builder.getOrCreateCategory(Component.literal("Explosions"));

        explosions.addEntry(eb.startBooleanToggle(Component.literal("Hide Totem-use particles"), cfg.explosions.hideTotemParticles)
                .setDefaultValue(false)
                .setSaveConsumer(v -> cfg.explosions.hideTotemParticles = v)
                .build());

        explosions.addEntry(eb.startBooleanToggle(
                        Component.literal("Hide explosion particles (TNT / End Crystal / other)"),
                        cfg.explosions.hideExplosionParticles)
                .setDefaultValue(false)
                .setTooltip(Component.literal("One combined toggle: vanilla's explosion packet doesn't say which "
                        + "entity caused it, so TNT, End Crystals and other explosives share this switch."))
                .setSaveConsumer(v -> cfg.explosions.hideExplosionParticles = v)
                .build());

        // ---- About ----------------------------------------------------
        ConfigCategory about = builder.getOrCreateCategory(Component.literal("About"));

        about.addEntry(eb.startTextDescription(Component.literal("Stillglow — developed by Soetadline")).build());

        about.addEntry(eb.startTextDescription(Component.literal("github.com/Soetadline")).build());

        return builder.build();
    }
}
