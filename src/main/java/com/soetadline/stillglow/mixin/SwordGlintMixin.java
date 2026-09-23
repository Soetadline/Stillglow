package com.soetadline.stillglow.mixin;

import com.soetadline.stillglow.Stillglow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * ItemStack lives in common code (not client-only), which is why this
 * mixin isn't under mixin/client — but its only real effect is visual.
 */
@Mixin(ItemStack.class)
public class SwordGlintMixin {

    @Inject(method = "hasGlint", at = @At("RETURN"), cancellable = true)
    private void stillglow$hideSwordGlint(CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ()) return;
        if (!Stillglow.config().visuals.disableSwordEnchantGlint) return;

        ItemStack self = (ItemStack) (Object) this;
        if (self.getItem() instanceof SwordItem) {
            cir.setReturnValue(false);
        }
    }
}
