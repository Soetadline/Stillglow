package com.soetadline.stillglow.mixin.client;

import com.soetadline.stillglow.Stillglow;
import net.minecraft.client.renderer.entity.ExperienceOrbRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Vanilla cycles the orb's rendered tint through a small palette keyed
 * off its age — the "shimmer". Freezing that lookup's return value
 * leaves everything else in the renderer alone, including the vertical
 * bob/squish motion, which is computed separately inside render() and
 * never touched here.
 *
 * If Loom's genSources shows a different name for this palette lookup
 * on your build of 26.3, update `method` below to match it.
 */
@Mixin(ExperienceOrbRenderer.class)
public class ExperienceOrbColorMixin {

    @Inject(method = "getColor", at = @At("RETURN"), cancellable = true)
    private static void stillglow$freezeOrbColor(int index, CallbackInfoReturnable<Integer> cir) {
        if (Stillglow.config().performance.disableOrbColorAnimation) {
            cir.setReturnValue(0xFFE080); // fixed warm gold — no cycling
        }
    }
}
