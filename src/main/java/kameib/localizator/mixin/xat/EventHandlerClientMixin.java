package kameib.localizator.mixin.xat;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import xzeroair.trinkets.client.events.EventHandlerClient;

@SideOnly(Side.CLIENT)
@Mixin(EventHandlerClient.class)
public abstract class EventHandlerClientMixin {
    @ModifyArg(
            method = "ItemToolTipEvent(Lnet/minecraftforge/event/entity/player/ItemTooltipEvent;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 2,
                    remap = false
            ),
            remap = false
    )
    // Replaces the hardcoded "Weight:" with a lang key
    // Line 262: event.getToolTip().add("Weight: " + color + entry.getWeight());
    private Object Trinkets_EventHandlerClient_ItemTooltipEvent_weightLangKey(Object text) {
        String myText = (String) text;
        return myText.replace("Weight:", I18n.format("xat.tooltip.weight"));
    }
}
