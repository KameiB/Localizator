package localizator.mixin.armorunder;

import net.minecraft.util.text.translation.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.jwaresoftware.mcmods.armorunder.runtime.LinedArmorTooltip;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LinedArmorTooltip.class)
public abstract class LinedArmorTooltipMixin {
    @ModifyArg(
            method = "addPlainLinedArmorTooltip(Lnet/minecraftforge/event/entity/player/ItemTooltipEvent;)V",
            at = @At(value = "INVOKE", target = "Lorg/jwaresoftware/mcmods/lib/Strings;translateFormatted(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;"),
            index = 1,
            remap = false
    )
    // Line 31: tip = Strings.translateFormatted("tooltip.auw.xlining.ozzy", new Object[]{what});
    private Object[] localizator_LinedArmorTooltip_addPlainLinedArmorTooltip(Object[] what) {
        if  (what[0] instanceof String) {
            String modifier = (String)what[0];
            modifier = "tooltip.auw.xlining.ozzy_" + modifier.toLowerCase();
            what[0] = I18n.translateToLocal(modifier);
        }
        return what;
    }
}
