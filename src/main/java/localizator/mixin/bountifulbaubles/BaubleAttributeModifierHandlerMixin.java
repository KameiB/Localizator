package localizator.mixin.bountifulbaubles;

import cursedflames.bountifulbaubles.baubleeffect.BaubleAttributeModifierHandler;
import localizator.handlers.ForgeConfigHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaubleAttributeModifierHandler.class)
public abstract class BaubleAttributeModifierHandlerMixin {    
    @Inject(
            method = "onItemTooltip(Lnet/minecraftforge/event/entity/player/ItemTooltipEvent;)V",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", shift = At.Shift.AFTER),
            cancellable = true,
            remap = false
    )
    private static void localizator_BountifulBaubles_AttributeModifierHandler_onItemTooltip_noModifierAtName(CallbackInfo ci) {
        if (ForgeConfigHandler.clientConfig.bountifulbaublesRemoveModifierFromName) {
            ci.cancel();
        }
    }
}
