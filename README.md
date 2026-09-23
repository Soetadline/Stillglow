# Stillglow

Optional, off-by-default client/server tweaks for Minecraft Java Edition
**26.3**, built on **Fabric** and Java 25. Nothing in this mod does anything until
you turn it on in the config screen.

Developer: **Soetadline** — https://github.com/Soetadline
(there's also a clickable link to this on the mod's own "About" config tab)

---

## Build environment

Minecraft 26.3 requires Java 25. The included Gradle Wrapper uses Gradle 9.1.0,
the first Gradle release with Java 25 runtime support. Run:

```
./gradlew build
```

The built jar lands in `build/libs/stillglow-1.0.0.jar`. Drop it in your
`mods/` folder along with Fabric API, Cloth Config, and (optionally) Mod Menu.

---

## Downloads you'll need before building

Minecraft 26.3 uses Mojang's official mappings; Yarn mappings are not published
for this post-obfuscation release. Compatible dependency builds are pinned in
`gradle.properties`:

- Fabric Loader: https://fabricmc.net/develop/ (pick "26.3")
- Fabric API: https://modrinth.com/mod/fabric-api/versions?g=26.3
- Cloth Config: https://modrinth.com/mod/cloth-config/versions?g=26.3
- Mod Menu: https://modrinth.com/mod/modmenu/versions?g=26.3

---

## Libraries used

| Library | Purpose | Required? |
|---|---|---|
| Fabric Loader | Mod loader | Required |
| Fabric API | Tick events, particle/entity helpers, networking and rendering hooks | Required |
| Cloth Config API | In-game config screen and persistence | Required |
| Mod Menu | Shows Stillglow's config under the Mods button | Optional |

---

## Feature notes

- The Still plants setting disables vanilla per-block random position offsets; actual grass/leaf wind sway is normally produced by shaders or resource packs.
- Explosion filtering is intentionally combined because the vanilla explosion packet does not identify TNT versus End Crystal on the client.
- Fabric mods run on Minecraft Java Edition, including supported ARM64 Java environments, but not stock Bedrock Edition on phones or consoles.

## Totem of Undying retexture

`extras/Stillglow-Totem-Retexture.zip` is an optional resource pack for the inventory/hand icon. The 3D use-animation texture can be added by extracting the matching 26.3 texture from the player's own client jar and placing the recolored file at `assets/minecraft/textures/entity/totem_of_undying.png`.

## Config

Settings are grouped into Performance, Visuals, Explosions, and About tabs and persist to `config/stillglow.json`. All settings default to off/vanilla.
