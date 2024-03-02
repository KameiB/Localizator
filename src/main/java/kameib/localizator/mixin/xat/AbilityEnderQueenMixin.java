package kameib.localizator.mixin.xat;

import com.google.common.base.Strings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xzeroair.trinkets.capabilities.Vip.VipStatus;
import xzeroair.trinkets.traits.abilities.AbilityEnderQueen;

@Mixin(AbilityEnderQueen.class)
public abstract class AbilityEnderQueenMixin {
    @ModifyArg(
            method = "attacked(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/util/DamageSource;FZ)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lxzeroair/trinkets/util/helpers/StringUtils;sendMessageToPlayer(Lnet/minecraft/entity/Entity;Ljava/lang/String;Z)V",
                    ordinal = 0,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace the hardcoded "Go, my loyal subject!" with a lang key
    // Line 111: StringUtils.sendMessageToPlayer(attacked, TextFormatting.BOLD + "" + TextFormatting.GOLD + "Go, my loyal subject!", false);
    private String Trinkets_AbilityEnderQueen_attacked_sendMessageToPlayer0(String msg) {
        return "xat.notif.ender_tiara.message.0";
    }
    
    @Inject(
            method = "attacked(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/util/DamageSource;FZ)Z",
            at = @At("HEAD"),
            remap = false
    )
    // Reset localizator$VIPrandomQuote
    // Line 87: boolean client = attacked.func_130014_f_().field_72995_K;
    private void Trinkets_AbilityEnderQueen_attacked_resetMyVIPquote(EntityLivingBase attacked, DamageSource source, float dmg, boolean cancel, CallbackInfoReturnable<Boolean> cir) {
        localizator$VIPrandomQuote = "";
    }
    
    @Redirect(
            method = "attacked(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/util/DamageSource;FZ)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lxzeroair/trinkets/capabilities/Vip/VipStatus;getRandomQuote()Ljava/lang/String;",
                    ordinal = 0,
                    remap = false
            ),
            remap = false
    )
    // Capture VIP random quote
    // Line 102: String quote = vip.getRandomQuote();
    private String Trinkets_AbilityEnderQueen_attacked_captureVIPquote(VipStatus instance) {
        localizator$VIPrandomQuote = instance.getRandomQuote();
        return localizator$VIPrandomQuote;
    }
    
    @ModifyArg(
            method = "attacked(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/util/DamageSource;FZ)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lxzeroair/trinkets/util/helpers/StringUtils;sendMessageToPlayer(Lnet/minecraft/entity/Entity;Ljava/lang/String;Z)V",
                    ordinal = 1,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace the hardcoded "The void protects me!" with a lang key. If player has VIP quote, return the quote as-is.
    // Line 134: StringUtils.sendMessageToPlayer(attacked, TextFormatting.BOLD + "" + TextFormatting.GOLD + string, false);
    private String Trinkets_AbilityEnderQueen_attacked_sendMessageToPlayer1(String msg) {
        if (Strings.isNullOrEmpty(localizator$VIPrandomQuote)) {
            return "xat.notif.ender_tiara.message.1";
        }
        else {
            return TextFormatting.BOLD + "" + TextFormatting.GOLD + localizator$VIPrandomQuote;
        }
    }
    
    @Unique
    private String localizator$VIPrandomQuote;
}
