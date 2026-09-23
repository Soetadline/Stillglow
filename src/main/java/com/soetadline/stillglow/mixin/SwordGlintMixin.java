package com.soetadline.stillglow.mixin;

import com.soetadline.stillglow.Stillglow;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Disables the vanilla foil/glint for enchanted item stacks when enabled. */
@Mixin(ItemStack.class)
public final class SwordGlintMixin {
    @Inject(method = "hasFoil", at = @At("HEAD"), cancellable = true)
    private void stillglow$disableFoil(CallbackInfoReturnable<Boolean> cir) {
        if (Stillglow.config().visuals.disableSwordEnchantGlint) {
            cir.setReturnValue(false);
        }
    }
}
