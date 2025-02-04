package kameib.localizator.mixin.mca;

import mca.enums.EnumMoveState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EnumMoveState.class)
public abstract class EnumMoveStateMixin {
    /**
     * @author KameiB
     * @reason Return the lang key as-is. Will translate on Client side later. 
     */
    @Overwrite(remap = false)
    public String getFriendlyName() {
        return this.friendlyName;
    }
    
    @Shadow(remap = false)
    String friendlyName;
}
