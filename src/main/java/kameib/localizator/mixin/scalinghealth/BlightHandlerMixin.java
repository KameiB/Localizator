package kameib.localizator.mixin.scalinghealth;

import com.google.common.base.Strings;
import kameib.localizator.data.Production;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.silentchaos512.scalinghealth.event.BlightHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlightHandler.class)
public abstract class BlightHandlerMixin {
    @Inject(
            method = "onBlightKilled(Lnet/minecraftforge/event/entity/living/LivingDeathEvent;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/DamageSource;getTrueSource()Lnet/minecraft/entity/Entity;",
                    ordinal = 0,
                    shift = At.Shift.AFTER,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Get Killer and damage source names
    // Line 127: public void onBlightKilled(LivingDeathEvent event) {
    private void ScalingHealth_BlightHandler_onBlightKilled_catchEvent(LivingDeathEvent event, CallbackInfo ci) {
        localizator$myEntitySource = "mob";
        localizator$myDamageType = "generic";
        if (event.getSource() != null) {
            if (event.getSource().getTrueSource() != null) {
                //localizator$myEntitySource = event.getSource().getTrueSource().getName(); // Killer
                localizator$myEntitySource = event.getSource().getTrueSource().getDisplayName().getUnformattedComponentText();
            }
            if (!Strings.isNullOrEmpty(event.getSource().getDamageType())) {
                localizator$myDamageType = event.getSource().getDamageType(); // Damage type?
            }
        }
    }
    
    @ModifyArg(
            method = "onBlightKilled(Lnet/minecraftforge/event/entity/living/LivingDeathEvent;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/silentchaos512/lib/util/ChatHelper;sendMessage(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 0,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Remove the hardcoded "Blight " and use the existing "blight.scalinghealth.name" lang key
    // Send parameters for all types of death messages
    // Line 146: ChatHelper.sendMessage(p, newMessage);
    private ITextComponent ScalingHealth_BlightHandler_onBlightKilled_hasDied(ITextComponent message) {
        TextComponentTranslation tempMessage = (TextComponentTranslation) message;
        if (tempMessage.getFormatArgs()[0].toString().contains("Blight ")) {
            return new TextComponentTranslation(tempMessage.getKey(),
                    new TextComponentTranslation("blight.scalinghealth.name", tempMessage.getFormatArgs()[0].toString().replaceFirst("Blight ", "")),
                    localizator$myEntitySource, 
                    new TextComponentTranslation(localizator$myDamageType));
        }
        return tempMessage;
    }
    
    @Unique
    private String localizator$myEntitySource;
    
    @Unique
    private String localizator$myDamageType;
}
