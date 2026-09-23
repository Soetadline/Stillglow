package com.soetadline.stillglow.mixin.client;

import com.soetadline.stillglow.Stillglow;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Deliberately NOT a real StatusEffectInstance: that would need server
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
                    target = "Lnet/minecraft/entity/LivingEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z"
            )
    )
    private boolean stillglow$fakeNightVision(LivingEntity entity, StatusEffect effect) {
        if (effect == StatusEffects.NIGHT_VISION && Stillglow.config().visuals.infiniteNightVision) {
            return true;
        }
        return entity.hasStatusEffect(effect);
    }
}
