# Stillglow

Optional, off-by-default client/server tweaks for Minecraft Java Edition
**26.3**, built on **Fabric** and Java 25. Nothing in this mod does anything until
you turn it on in the config screen.

Developer: **Soetadline** — https://github.com/Soetadline
(there's also a clickable link to this on the mod's own "About" config tab)

---

## ⚠️ Read this before you build

I put this project together without a live, working Minecraft 26.3 dev
environment in front of me (26.3 is a Q3-2026 release and my sandbox has
no internet access to pull the actual game jar / Yarn mappings / test-
compile anything). Everything below is written using the standard,
long-stable Fabric/Yarn conventions — but a couple of the more obscure
method names (noted inline in the Java files with a comment) may need a
one-line tweak once you open the project in an IDE with real 26.3
sources. This is normal for any brand-new Minecraft version, not
specific to this mod — every Fabric mod updating to a fresh drop goes
through this. If something doesn't compile:

1. Open the flagged class in IntelliJ/your IDE after Loom's `genSources`
   runs (happens automatically on first Gradle sync).
2. Find the method the comment describes (by its behavior, not just its
   old name).
3. Update the `method = "..."` string in that one `@Mixin` file.

Everything else — the config screen, the orb-merge logic, the particle
filtering, the glint toggle — uses public, stable API and should just
work.

**Two feature notes, explained honestly:**

- **"Disable grass/leaf wind sway"** — vanilla Java Edition doesn't
  actually animate grass/leaves swaying in the wind; that's a
  shader-pack effect (Iris + something like Complementary Shaders) or a
  resource-pack trick, not a base-game feature this mod can hook. The
  closest *real* vanilla thing to disable is the static per-block random
  position offset on grass/flowers/ferns — that's what the "Still
  plants" toggle actually does. If you're using a shader pack, turn its
  wind option off from the shader's own settings instead.
- **Separate TNT / End Crystal explosion toggles** — vanilla's explosion
  network packet doesn't tell the client which entity caused an
  explosion, so a client-side mod like this genuinely can't tell them
  apart without adding its own custom networking (which would need
  version-fragile hooks into internal entity methods). I shipped one
  combined "explosions" toggle instead of three that might silently not
  work. The Totem-of-Undying particle burst *is* its own distinct
  particle type, so that one **does** get its own separate toggle.

**"PC and mobile processor" compatibility** — Fabric mods only run on
Minecraft **Java Edition** (Windows/macOS/Linux). Bedrock Edition (the
actual phone/console version) has no third-party mod support at all, so
this can't run on a stock Android/iOS Minecraft install. What this *can*
do is run on any machine capable of running Java Edition itself,
including ARM64 hardware (Apple Silicon Macs, ARM Windows/Linux, and
unofficial Java-on-Android launchers) as well as regular x86_64 PCs.

---

## Libraries used (and why)

| Library | Purpose | Required? |
|---|---|---|
| [Fabric Loader](https://fabricmc.net/) | The mod loader itself | **Required** |
| [Fabric API](https://modrinth.com/mod/fabric-api) | Tick events, particle/entity helpers used throughout | **Required** |
| [Cloth Config API](https://modrinth.com/mod/cloth-config) | Builds the in-game config screen and saves it to `config/stillglow.json` | **Required** |
| [Mod Menu](https://modrinth.com/mod/modmenu) | Surfaces Stillglow's config under vanilla's "Mods" button | Optional — without it, use the "Open Stillglow Config" keybind (unbound by default; set one in Options > Controls) |

These are the standard combo the Fabric modding community uses for
in-game config screens — there isn't a more "official" alternative.

## Downloads you'll need before building

Because 26.3 is so recent, pin the exact build numbers from Fabric's own
version picker rather than trusting the placeholders in
`gradle.properties`:

- Yarn mappings + Loader: https://fabricmc.net/develop/ (pick "26.3")
- Fabric API: https://modrinth.com/mod/fabric-api/versions?g=26.3
- Cloth Config: https://modrinth.com/mod/cloth-config/versions?g=26.3
- Mod Menu: https://modrinth.com/mod/modmenu/versions?g=26.3

## Building

```
./gradlew build
```

The built jar lands in `build/libs/stillglow-1.0.0.jar`. Drop it in your
`mods/` folder along with Fabric API, Cloth Config, and (optionally) Mod
Menu.

## Totem of Undying retexture

Shipped as a **separate, optional resource pack** —
`Stillglow-Totem-Retexture.zip` — rather than code, so it's:
- guaranteed to render correctly (no risk of a broken 3D model from a
  guessed UV layout — see below), and
- opt-in the same way any Minecraft player already expects (Options >
  Resource Packs), independent of Stillglow's own config menu.

It currently reskins the **inventory/hand icon** (a flat 2D sprite, so a
from-scratch redraw is 100% safe) in the requested blue/white/
light-blue gradient with black eyes.

The **3D "use" animation** texture (`textures/entity/totem_of_undying.png`)
is UV-mapped to Mojang's exact totem model geometry. I don't have that
source file to recolor accurately in this environment, and guessing the
UV layout risks a garbled-looking model rather than a clean recolor. To
finish that part yourself:
1. Extract `assets/minecraft/textures/entity/totem_of_undying.png` from
   your own installed 26.3 client jar (`.minecraft/versions/26.3/26.3.jar`,
   opened as a zip), or from a resource-pack extraction tool.
2. Recolor it in any image editor using hue-shift/selective-color to
   swap its existing golds/greens for blues, whites, and light blues,
   keeping the eye region dark.
3. Drop the edited file into the same resource pack zip at
   `assets/minecraft/textures/entity/totem_of_undying.png` and re-zip.

## Config

Fully covered by four tabs in the in-game menu: **Performance**,
**Visuals**, **Explosions**, and **About** (developer info + GitHub
link). All settings persist to `config/stillglow.json` and default to
off/vanilla.
