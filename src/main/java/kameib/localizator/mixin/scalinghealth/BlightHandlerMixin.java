package kameib.localizator.mixin.scalinghealth;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.silentchaos512.scalinghealth.event.BlightHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BlightHandler.class)
public abstract class BlightHandlerMixin {
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
    private ITextComponent ScalingHealth_BlightHandler_onBlightKilled_hasDied(ITextComponent message) {
        TextComponentTranslation tempMessage = (TextComponentTranslation) message;
        if (tempMessage.getFormatArgs()[0].toString().contains("Blight ")) {
            return new TextComponentTranslation(tempMessage.getKey().contains("death.attack.mob") ? "death.attack.generic" : tempMessage.getKey(),
                    new TextComponentTranslation("blight.scalinghealth.name", 
                            tempMessage.getFormatArgs()[0].toString().replaceFirst("Blight ", "")));
        }
        return tempMessage;
    }
}
