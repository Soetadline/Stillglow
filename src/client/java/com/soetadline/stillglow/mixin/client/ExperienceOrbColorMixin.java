package com.soetadline.stillglow.mixin.client;

import com.soetadline.stillglow.Stillglow;
import net.minecraft.client.renderer.entity.ExperienceOrbRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Freezes the XP orb icon selection when the animation toggle is enabled. */
@Mixin(ExperienceOrbRenderer.class)
public final class ExperienceOrbColorMixin {
    @Inject(method = "getIcon", at = @At("RETURN"), cancellable = true)
    private static void stillglow$freezeOrbColor(int value, CallbackInfoReturnable<Integer> cir) {
        if (Stillglow.config().performance.disableOrbColorAnimation) {
            cir.setReturnValue(0);
        }
    }
}
