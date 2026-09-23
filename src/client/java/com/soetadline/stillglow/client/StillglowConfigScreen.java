package com.soetadline.stillglow.client;

import com.soetadline.stillglow.config.StillglowConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;

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
                .setTitle(Text.literal("Stillglow"))
                .setSavingRunnable(holder::save);

        // ---- Performance -----------------------------------------------
        ConfigCategory performance = builder.getOrCreateCategory(Text.literal("Performance"));

        performance.addEntry(eb.startBooleanToggle(Text.literal("Merge XP orbs"), cfg.performance.mergeExperienceOrbs)
                .setDefaultValue(false)
                .setTooltip(Text.literal("Combines nearby ground XP orbs into one entity, about once a second."))
                .setSaveConsumer(v -> cfg.performance.mergeExperienceOrbs = v)
                .build());

        performance.addEntry(eb.startIntSlider(Text.literal("Merge radius"), cfg.performance.mergeRadius, 1, 16)
                .setDefaultValue(4)
                .setTooltip(Text.literal("Orbs within this many blocks of each other get combined."))
                .setSaveConsumer(v -> cfg.performance.mergeRadius = v)
                .build());

        performance.addEntry(eb.startBooleanToggle(Text.literal("Disable XP orb color-cycle"), cfg.performance.disableOrbColorAnimation)
                .setDefaultValue(false)
                .setTooltip(Text.literal("Removes the shimmering color change. The bobbing motion is unaffected."))
                .setSaveConsumer(v -> cfg.performance.disableOrbColorAnimation = v)
                .build());

        // ---- Visuals ------------------------------------------------------
        ConfigCategory visuals = builder.getOrCreateCategory(Text.literal("Visuals"));

        visuals.addEntry(eb.startBooleanToggle(Text.literal("Infinite Night Vision"), cfg.visuals.infiniteNightVision)
                .setDefaultValue(false)
                .setTooltip(Text.literal("Client-side brightness only — does not apply a real status effect."))
                .setSaveConsumer(v -> cfg.visuals.infiniteNightVision = v)
                .build());

        visuals.addEntry(eb.startBooleanToggle(Text.literal("Disable sword enchant glint"), cfg.visuals.disableSwordEnchantGlint)
                .setDefaultValue(false)
                .setSaveConsumer(v -> cfg.visuals.disableSwordEnchantGlint = v)
                .build());

        visuals.addEntry(eb.startBooleanToggle(Text.literal("Still plants (remove random offset)"), cfg.visuals.stillPlants)
                .setDefaultValue(false)
                .setTooltip(Text.literal("Vanilla has no wind-sway to disable; this removes the static per-block "
                        + "jitter on grass/flowers instead. For shader-pack wind, use the shader pack's own settings."))
                .setSaveConsumer(v -> cfg.visuals.stillPlants = v)
                .build());

        visuals.addEntry(eb.startBooleanToggle(Text.literal("Dark Mode UI"), cfg.visuals.darkModeUI)
                .setDefaultValue(false)
                .setTooltip(Text.literal("Translucent dark tint over inventories and menus."))
                .setSaveConsumer(v -> cfg.visuals.darkModeUI = v)
                .build());

        // ---- Explosions -----------------------------------------------
        ConfigCategory explosions = builder.getOrCreateCategory(Text.literal("Explosions"));

        explosions.addEntry(eb.startBooleanToggle(Text.literal("Hide Totem-use particles"), cfg.explosions.hideTotemParticles)
                .setDefaultValue(false)
                .setSaveConsumer(v -> cfg.explosions.hideTotemParticles = v)
                .build());

        explosions.addEntry(eb.startBooleanToggle(
                        Text.literal("Hide explosion particles (TNT / End Crystal / other)"),
                        cfg.explosions.hideExplosionParticles)
                .setDefaultValue(false)
                .setTooltip(Text.literal("One combined toggle: vanilla's explosion packet doesn't say which "
                        + "entity caused it, so TNT, End Crystals and other explosives share this switch."))
                .setSaveConsumer(v -> cfg.explosions.hideExplosionParticles = v)
                .build());

        // ---- About ----------------------------------------------------
        ConfigCategory about = builder.getOrCreateCategory(Text.literal("About"));

        about.addEntry(eb.startTextDescription(Text.literal("Stillglow — developed by Soetadline")).build());

        Text link = Text.literal("github.com/Soetadline").styled(style -> style
                .withUnderline(true)
                .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/Soetadline")));
        about.addEntry(eb.startTextDescription(link).build());

        return builder.build();
    }
}
