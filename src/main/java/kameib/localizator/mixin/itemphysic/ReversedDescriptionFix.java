package kameib.localizator.mixin.itemphysic;

import com.creativemd.itemphysic.EventHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Collections;
import java.util.List;

@Mixin(EventHandler.class)
public abstract class ReversedDescriptionFix {
    /**
     * @author KameiB
     * @reason The item description gets reversed when showing over the entity (dropped item).
     * Ths will reverse that, so it shows same as the tooltip.
     */
    @Redirect(
            method = "renderTickFull()V",
            at = @At(
                    value = "INVOKE", 
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 0,
                    remap = false
            ),
            remap = false
    )
    // Line 267: list.add(entity.getItem().getDisplayName());
    private boolean ItemPhysic_EventHandler_reverseItemDescription(List<String> list, Object name) {
        if (list.size() > 1) {
            Collections.reverse(list); // Reverse its contents before adding the name
        }
        
        return list.add((String)name);
    }
}
