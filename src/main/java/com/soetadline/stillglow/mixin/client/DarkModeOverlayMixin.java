package com.soetadline.stillglow.mixin.client;

import com.soetadline.stillglow.Stillglow;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * A tint, not a re-skin: draws a translucent dark rectangle over every
 * vanilla screen after everything else has rendered, so slot/button
 * textures underneath stay vanilla. Skips the title screen so the logo
 * isn't muddied.
 */
@Mixin(Screen.class)
public class DarkModeOverlayMixin {

    @Inject(method = "render", at = @At("TAIL"))
    private void stillglow$darkModeTint(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (!Stillglow.config().visuals.darkModeUI) return;

        Screen self = (Screen) (Object) this;
        if (self instanceof TitleScreen) return;

        context.fill(0, 0, self.width, self.height, 0x66000000);
    }
}
