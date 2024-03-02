package kameib.localizator.mixin.armorunder;

import net.minecraft.util.text.translation.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.jwaresoftware.mcmods.armorunder.runtime.LinedArmorTooltip;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LinedArmorTooltip.class)
public abstract class LinedArmorTooltipMixin {
    /**
     * @author KameiB
     * @reason Localize the hardcoded "MILD", "COOL", "WARM" texts when a piece of armor
     * has an Ozzy liner
     */
    @Redirect(
            method = "addPlainLinedArmorTooltip(Lnet/minecraftforge/event/entity/player/ItemTooltipEvent;)V",
            at = @At(
                    value = "INVOKE", 
                    target = "Lorg/jwaresoftware/mcmods/lib/Strings;translateFormatted(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
                    remap = false
            ),            
            remap = false
    )
    @SuppressWarnings("deprecation")
    // Line 31: tip = Strings.translateFormatted("tooltip.auw.xlining.ozzy", new Object[]{what});
    private String localizator_LinedArmorTooltip_addPlainLinedArmorTooltip(String key, Object[] format) {
        // This 'if' is in case the mod's author applies this fix.
        // https://github.com/Wabbit0101/mods_hoardercraft/issues/151
        if (format[0].toString().length()<5) {
            return I18n.translateToLocalFormatted(key,
                    I18n.translateToLocal("tooltip.auw.xlining.ozzy_" + format[0].toString().toLowerCase()));
        }
        return I18n.translateToLocalFormatted(key,format);
    }
}
