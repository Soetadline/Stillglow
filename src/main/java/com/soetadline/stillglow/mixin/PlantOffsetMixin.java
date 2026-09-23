package com.soetadline.stillglow.mixin;

import com.soetadline.stillglow.Stillglow;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * READ THIS ONE FIRST: vanilla Java Edition has no built-in wind-sway
 * animation for grass/leaves — that's a shader pack (Iris) or resource
 * pack effect this mod has no hook into. What vanilla DOES do, always,
 * is nudge each grass/fern/flower model a few pixels sideways based on
 * its block position, so a field doesn't look perfectly grid-aligned.
 * This mixin zeroes that static offset out — a genuinely real vanilla
 * effect, just not an animated one. If you use Iris + a shader with
 * wind, disable it from the shader pack's own settings instead; this
 * toggle won't touch that.
 *
 * Target method name/signature may differ slightly across 26.x builds
 * (Mojang has renamed it before between drops) — if compilation fails,
 * search your IDE's decompiled BlockState for the method returning a
 * Vec3 "model offset" and update `method` below to match.
 */
@Mixin(BlockState.class)
public class PlantOffsetMixin {

    @Inject(method = "getModelOffset", at = @At("RETURN"), cancellable = true)
    private void stillglow$stillPlants(BlockGetter world, BlockPos pos, CallbackInfoReturnable<Vec3> cir) {
        if (Stillglow.config().visuals.stillPlants) {
            cir.setReturnValue(Vec3.ZERO);
        }
    }
}
