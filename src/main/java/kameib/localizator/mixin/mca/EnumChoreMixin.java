package kameib.localizator.mixin.mca;

import mca.enums.EnumChore;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EnumChore.class)
public abstract class EnumChoreMixin {
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
