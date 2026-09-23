package com.soetadline.stillglow.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

/**
 * Every field here defaults to off / vanilla behaviour. Nothing in
 * Stillglow changes anything until the player opts in from the config
 * screen (Mod Menu, or the keybind set up in StillglowClient).
 *
 * This class only defines the *data* — persisted to
 * config/stillglow.json via Cloth Config's AutoConfig. The screen
 * itself is built by hand in the client-only StillglowConfigScreen,
 * which is what lets us add the clickable developer/GitHub link on
 * the "About" tab.
 */
@Config(name = "stillglow")
public class StillglowConfig implements ConfigData {

    public Performance performance = new Performance();
    public Visuals visuals = new Visuals();
    public Explosions explosions = new Explosions();

    public static class Performance {
        /** Combine clustered ground XP orbs into one entity, periodically. */
        public boolean mergeExperienceOrbs = false;
        /** Orbs within this many blocks of each other (same grid cell) get merged. */
        public int mergeRadius = 4;
        /** Freeze the orb's shimmering color-cycle. Movement/bob is unaffected. */
        public boolean disableOrbColorAnimation = false;
    }

    public static class Visuals {
        /** Client-side brightness trick only — no real status effect is applied. */
        public boolean infiniteNightVision = false;
        /** Hides the enchant glint specifically on sword items. */
        public boolean disableSwordEnchantGlint = false;
        /** Removes vanilla's static per-block random offset on grass/flowers/ferns. */
        public boolean stillPlants = false;
        /** Translucent dark overlay drawn over inventories/menus. */
        public boolean darkModeUI = false;
    }

    public static class Explosions {
        public boolean hideTotemParticles = false;
        /** Combined switch — see the tooltip in StillglowConfigScreen for why. */
        public boolean hideExplosionParticles = false;
    }
}
