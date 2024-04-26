package kameib.localizator.mixin.bountifulbaubles;

import cursedflames.bountifulbaubles.baubleeffect.BaubleAttributeModifierHandler;
import kameib.localizator.handlers.ForgeConfigHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaubleAttributeModifierHandler.class)
public abstract class BaubleAttributeModifierHandlerMixin {
    /**
     * @author KameiB
     * @reason Prevents adding the Modifier name into the Bauble name
     */
    @Inject(
            method = "onItemTooltip(Lnet/minecraftforge/event/entity/player/ItemTooltipEvent;)V",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", shift = At.Shift.AFTER),
            cancellable = true,
            remap = false
    )
    // Line 137: event.getToolTip().add(BountifulBaubles.proxy.translate("bountifulbaubles.modifier." + mod + ".info"));
    @SideOnly(Side.CLIENT)
    private static void localizator_BountifulBaubles_AttributeModifierHandler_onItemTooltip_noModifierAtName(CallbackInfo ci) {
        if (ForgeConfigHandler.clientConfig.bountifulbaublesRemoveModifierFromName) {
            ci.cancel();
        }
    }
}
