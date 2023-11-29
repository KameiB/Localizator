package locentitynamemixin.mixin.vanilla;

import net.minecraft.client.gui.GuiBossOverlay;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import locentitynamemixin.handlers.ForgeConfigHandler;

@Mixin(GuiBossOverlay.class)
public abstract class BossOverlayMixin {
    /**
     * @author KameiB
     * @reason If Boss's CustomName contains a lang key, translate it at rendering.
     * Should now support lang keys surrounded by TextComponent formatting.
     */
    @Redirect(
            method = "renderBossHealth()V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/text/ITextComponent;getFormattedText()Ljava/lang/String;")
    )
    // Line 43: String s = bossinfoclient.getName().getFormattedText();
    public String locentitynameRenderBossHealth_getFormattedTextName(ITextComponent bossName) {
        StringBuilder stringbuilder = new StringBuilder();

        for (ITextComponent itextcomponent : bossName)
        {
            String s = itextcomponent.getUnformattedComponentText();
            if (!s.isEmpty())
            {
                stringbuilder.append(itextcomponent.getStyle().getFormattingCode());
                if (ForgeConfigHandler.clientConfig.vanillaBossLocCustomNames) {
                    stringbuilder.append(I18n.hasKey(s) ? I18n.format(s) : s);
                }
                else { // getFormattedText default behaviour
                    stringbuilder.append(s);
                }
                stringbuilder.append((Object) TextFormatting.RESET);
            }
        }
        return stringbuilder.toString();
    }
}
