package kameib.localizator.mixin.bettersurvival;

import com.mujmajnkraft.bettersurvival.client.ModClientHandler;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ModClientHandler.class)
public abstract class ModClientHandlerMixin {
    /**
     * @author KameiB
     * @reason Localize the hardcoded "hits remaining" text on a potion-imbued weapon tooltip
     */
    @ModifyConstant(
            method = "onTooltipRender(Lnet/minecraftforge/event/entity/player/ItemTooltipEvent;)V",
            constant = @Constant(stringValue = " hits remaining"),            
            remap = false
    )
    @SideOnly(Side.CLIENT)
    // Replace the hardcoded " hits remaining" text with a translated lang key
    // Line 112: event.getToolTip().add(h + "/" + ForgeConfigHandler.potions.maximumPotionHits + " hits remaining");
    private String localizator_BetterSurvival_ModClientHandler_onTooltipRender_addHitsRemaining(String constant) {
            return " " + I18n.format("mujmajnkraftsbettersurvival.imbuedweapon.hitsRemaining");
    }
}
