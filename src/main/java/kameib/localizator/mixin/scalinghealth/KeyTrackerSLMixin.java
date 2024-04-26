package kameib.localizator.mixin.scalinghealth;

import net.silentchaos512.lib.client.key.KeyTrackerSL;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyTrackerSL.class)
public abstract class KeyTrackerSLMixin {
    @Inject(
            method = "<init>",
            at = @At("TAIL"),
            remap = false
    )
    // Just making modName look better
    // Line 20: this.modName = modName;
    private void KeyTrackerSL_init_locModName(String modName, CallbackInfo ci) {
        this.modName = "Scaling Health";
    }
    
    @Mutable
    @Final
    @Shadow(remap = false)
    String modName;
}
