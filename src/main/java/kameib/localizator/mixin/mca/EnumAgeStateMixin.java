package kameib.localizator.mixin.mca;
import mca.core.Localizer;
import mca.enums.EnumAgeState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnumAgeState.class)
public abstract class EnumAgeStateMixin {
    /**
     * @author KameiB
     * @reason Don't localize on server side. Send the lang key and localize it on client side.
     */
    @Redirect(
            method = "localizedName()Ljava/lang/String;",
            at = @At(
                    value = "INVOKE",
                    target = "Lmca/core/Localizer;localize(Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/String;",
                    remap = false
            ),
            remap = false
    )
    private String MCA_EnumAgeState_localizedName_localize(Localizer instance, String key, String[] vars) {
        return key;
    }
}
