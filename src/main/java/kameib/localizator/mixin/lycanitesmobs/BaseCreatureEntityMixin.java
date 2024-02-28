package kameib.localizator.mixin.lycanitesmobs;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BaseCreatureEntity.class)
public abstract class BaseCreatureEntityMixin {
    @ModifyArg(
            method = "isDamageEntityApplicable(Lnet/minecraft/entity/Entity;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V",
                    remap = true
            ),
            index = 0,
            remap = false
    )
    // Translate Status Message on client side
    // Line 3971: ((EntityPlayer)entity).sendStatusMessage(new TextComponentString(LanguageManager.translate("boss.damage.protection.range")), true);
    private ITextComponent lycanites_BaseCreatureEntity_isDamageEntityApplicable_protection(ITextComponent message) {
        return new TextComponentTranslation("boss.damage.protection.range");
    }
}
