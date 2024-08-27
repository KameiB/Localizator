package kameib.localizator.mixin.morpheus;

import net.quetzi.morpheus.helpers.DateHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DateHandler.Event.class)
public abstract class DateHandlerEventMixin {
    @Inject(
            method = "<init>",
            at = @At("RETURN"),
            remap = false
    )
    // Modify each Event Enums to my desired texts
    // The bytecode shows that the constructor of DateHandler.Event has the following parameters:
    // private <init>(Ljava/lang/String;IIILjava/lang/String;)V
    /**
     * @param String (the name of the enum constant, passed automatically by the JVM)
     * @param int (ordinal, passed automatically by the JVM)
     * @param int day
     * @param int month
     * @param String text
     */
    private void Morpheus_DateHandler_Event_init_toLangKey(String enumName, int enumOrdinal, int day, int month, String text, CallbackInfo ci) {
        switch (enumName) {
            case "XMAS":
                this.text = "notif.morpheus.XmasText";
                break;
            case "NEW_YEAR":
                this.text = "notif.morpheus.NewYearText";
                break;
            case "STPATRICKS":
                this.text = "notif.morpheus.StPatricksText";
                break;
            case "HALLOWEEN":
                this.text = "notif.morpheus.HalloweenText";
                break;
            case "NONE":
                this.text = "notif.morpheus.onMorningText";
                break;
        }
    }
    
    @Mutable
    @Final
    @Shadow(remap = false)
    private String text;
}
