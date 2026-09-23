package com.soetadline.stillglow.mixin.client;

import com.soetadline.stillglow.Stillglow;
import net.minecraft.client.renderer.LightmapTextureManager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Deliberately NOT a real MobEffectInstance: that would need server
 * cooperation (or a command-block/permission workaround) to apply
 * legitimately. Instead this redirects the brightness calculation's
 * "does the player have Night Vision?" check to always say yes when the
 * toggle is on — purely a local rendering decision. No potion icon, no
 * effect visible to other players, no server involvement at all.
 */
@Mixin(LightmapTextureManager.class)
public class NightVisionLightmapMixin {

    @Redirect(
            method = "update",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;hasMobEffect(Lnet/minecraft/entity/effect/MobEffect;)Z"
            )
    )
    private boolean stillglow$fakeNightVision(LivingEntity entity, MobEffect effect) {
        if (effect == MobEffects.NIGHT_VISION && Stillglow.config().visuals.infiniteNightVision) {
            return true;
        }
        return entity.hasMobEffect(effect);
    }
}
