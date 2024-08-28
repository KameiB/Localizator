package kameib.localizator.mixin.xat;

import kameib.localizator.data.Production;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import xzeroair.trinkets.util.helpers.StringUtils;

@Mixin(StringUtils.class)
public abstract class StringUtilsMixin {
    @SuppressWarnings("deprecation")
    @ModifyArg(
            method = "sendMessageToPlayer(Lnet/minecraft/entity/Entity;Ljava/lang/String;Z)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    // Replace TextComponentString with TextComponentTranslation
    // Line 208: ((EntityPlayer)entity).func_146105_b(message, onScreen);
    private static ITextComponent Trinkets_StringUtils_sendMessageToPlayer_sendStatusMessage(ITextComponent message) {
        if (I18n.canTranslate(message.getUnformattedComponentText())) {
            return new TextComponentTranslation(message.getUnformattedComponentText());
        }
        else {
            return message;
        }
        
    }
}
