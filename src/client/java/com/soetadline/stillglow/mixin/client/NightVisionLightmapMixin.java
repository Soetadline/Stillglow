package com.soetadline.stillglow.mixin.client;

import com.soetadline.stillglow.Stillglow;
import net.minecraft.client.renderer.Lightmap;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/** Keeps the night-vision brightness check local to rendering, without applying an effect. */
@Mixin(Lightmap.class)
public final class NightVisionLightmapMixin {
    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;hasEffect(Lnet/minecraft/core/Holder;)Z"
            )
    )
    private boolean stillglow$fakeNightVision(LivingEntity entity, Holder<MobEffect> effect) {
        if (effect == MobEffects.NIGHT_VISION && Stillglow.config().visuals.infiniteNightVision) {
            return true;
        }
        return entity.hasEffect(effect);
    }
}
