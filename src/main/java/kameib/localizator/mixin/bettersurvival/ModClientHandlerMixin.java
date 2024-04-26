package kameib.localizator.mixin.bettersurvival;

import com.mujmajnkraft.bettersurvival.client.ModClientHandler;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ModClientHandler.class)
public abstract class ModClientHandlerMixin {
    /**
     * @author KameiB
     * @reason Localize the hardcoded "hits remaining" text on a potion-imbued weapon tooltip
     */
    @ModifyArg(
            method = "onTooltipRender(Lnet/minecraftforge/event/entity/player/ItemTooltipEvent;)V",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 3),            
            remap = false
    )
    @SideOnly(Side.CLIENT)
    // Make "hits remaining" text translatable
    // Line 112: event.getToolTip().add(h + "/" + ForgeConfigHandler.potions.maximumPotionHits + " hits remaining");
    private Object localizator_BetterSurvival_ModClientHandler_onTooltipRender_addHitsRemaining(Object hitsRemainingObj) {
            return ((String)hitsRemainingObj).replace("hits remaining", I18n.format("mujmajnkraftsbettersurvival.imbuedweapon.hitsRemaining"));
    }
}
