package com.soetadline.stillglow;

import com.soetadline.stillglow.config.StillglowConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stillglow implements ModInitializer {

    public static final String MOD_ID = "stillglow";

    /** Runs the merge sweep once a second (every 20 ticks) per world, not every tick. */
    private final Map<ServerWorld, Integer> tickCounters = new HashMap<>();

    @Override
    public void onInitialize() {
        AutoConfig.register(StillglowConfig.class, GsonConfigSerializer::new);
        ServerTickEvents.END_WORLD_TICK.register(this::mergeOrbs);
    }

    public static StillglowConfig config() {
        return AutoConfig.getConfigHolder(StillglowConfig.class).getConfig();
    }

    /**
     * Groups loose XP orbs into grid cells sized by the configured merge
     * radius, then collapses every cell holding 2+ orbs into a single
     * orb entity carrying their combined value. Only touches orbs that
     * are already loaded and ticking — never forces chunks to load, and
     * never touches any other entity type.
     */
    private void mergeOrbs(ServerWorld world) {
        StillglowConfig cfg = config();
        if (!cfg.performance.mergeExperienceOrbs) return;

        int count = tickCounters.merge(world, 1, Integer::sum);
        if (count < 20) return;
        tickCounters.put(world, 0);

        double cell = Math.max(1, cfg.performance.mergeRadius);
        Map<BlockPos, List<ExperienceOrb>> buckets = new HashMap<>();

        for (Entity entity : world.iterateEntities()) {
            if (!(entity instanceof ExperienceOrb orb) || !orb.isAlive()) continue;
            BlockPos key = new BlockPos(
                    Mth.floor(orb.getX() / cell),
                    Mth.floor(orb.getY() / cell),
                    Mth.floor(orb.getZ() / cell)
            );
            buckets.computeIfAbsent(key, k -> new ArrayList<>()).add(orb);
        }

        for (List<ExperienceOrb> group : buckets.values()) {
            if (group.size() < 2) continue;

            int totalXp = 0;
            Vec3 pos = group.get(0).getPos();
            for (ExperienceOrb orb : group) {
                totalXp += orb.getExperienceAmount();
                orb.discard();
            }
            ExperienceOrb.spawn(world, pos, totalXp);
        }
    }
}
