package com.soetadline.stillglow.mixin.client;

import com.soetadline.stillglow.Stillglow;
import com.soetadline.stillglow.config.StillglowConfig;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Every particle the client spawns funnels through here, which makes it
 * a safe, stable place to filter by particle type.
 *
 * We only cancel two families:
 *  - TOTEM_OF_UNDYING — the totem "use" burst.
 *  - EXPLOSION / EXPLOSION_EMITTER — shared by TNT, End Crystals,
 *    creepers, beds, wind charges, respawn anchors, etc.
 *
 * Vanilla's explosion network packet doesn't carry a "which entity
 * caused this" field, so the client genuinely can't tell TNT apart from
 * an End Crystal here — splitting those into separate toggles would
 * need this mod's own server<->client packet sent from a mixin at each
 * entity's explosion trigger, which risks breaking on 26.3's still-
 * settling internal method names. One combined switch is the reliable
 * version; see the README for the trade-off.
 */
@Mixin(ParticleEngine.class)
public class ExplosionParticleMixin {

    @Inject(
            method = "addParticle(Lnet/minecraft/particle/ParticleOptions;DDDDDD)Lnet/minecraft/client/particle/Particle;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void stillglow$filterParticles(ParticleOptions parameters, double x, double y, double z,
                                            double velocityX, double velocityY, double velocityZ,
                                            CallbackInfoReturnable<Particle> cir) {
        StillglowConfig.Explosions cfg = Stillglow.config().explosions;

        if (cfg.hideTotemParticles && parameters == ParticleTypes.TOTEM_OF_UNDYING) {
            cir.setReturnValue(null);
            return;
        }
        if (cfg.hideExplosionParticles
                && (parameters == ParticleTypes.EXPLOSION || parameters == ParticleTypes.EXPLOSION_EMITTER)) {
            cir.setReturnValue(null);
        }
    }
}
