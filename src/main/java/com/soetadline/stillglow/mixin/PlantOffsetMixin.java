package com.soetadline.stillglow.mixin;

import com.soetadline.stillglow.Stillglow;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
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
 * Vec3d "model offset" and update `method` below to match.
 */
@Mixin(BlockState.class)
public class PlantOffsetMixin {

    @Inject(method = "getModelOffset", at = @At("RETURN"), cancellable = true)
    private void stillglow$stillPlants(BlockView world, BlockPos pos, CallbackInfoReturnable<Vec3d> cir) {
        if (Stillglow.config().visuals.stillPlants) {
            cir.setReturnValue(Vec3d.ZERO);
        }
    }
}
