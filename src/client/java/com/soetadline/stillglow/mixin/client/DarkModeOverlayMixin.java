package com.soetadline.stillglow.mixin.client;

import com.soetadline.stillglow.Stillglow;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Adds a translucent dark tint over vanilla screens, excluding the title screen. */
@Mixin(Screen.class)
public final class DarkModeOverlayMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void stillglow$darkModeTint(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (!Stillglow.config().visuals.darkModeUI) return;
        Screen self = (Screen) (Object) this;
        if (self instanceof TitleScreen) return;
        graphics.fill(0, 0, self.width, self.height, 0x66000000);
    }
}
