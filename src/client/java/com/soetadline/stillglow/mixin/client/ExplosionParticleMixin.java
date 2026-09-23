package com.soetadline.stillglow.mixin.client;

import com.soetadline.stillglow.Stillglow;
import com.soetadline.stillglow.config.StillglowConfig;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Filters totem and explosion particles before they are added to the client engine. */
@Mixin(ParticleEngine.class)
public final class ExplosionParticleMixin {
    @Inject(method = "add", at = @At("HEAD"), cancellable = true)
    private void stillglow$filterParticles(ParticleOptions parameters, double x, double y, double z,
                                            double velocityX, double velocityY, double velocityZ,
                                            CallbackInfo ci) {
        StillglowConfig.Explosions cfg = Stillglow.config().explosions;
        if (cfg.hideTotemParticles && parameters.getType() == ParticleTypes.TOTEM_OF_UNDYING) {
            ci.cancel();
            return;
        }
        if (cfg.hideExplosionParticles
                && (parameters.getType() == ParticleTypes.EXPLOSION
                || parameters.getType() == ParticleTypes.EXPLOSION_EMITTER)) {
            ci.cancel();
        }
    }
}
