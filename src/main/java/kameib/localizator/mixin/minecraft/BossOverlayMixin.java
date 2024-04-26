package kameib.localizator.mixin.minecraft;

import kameib.localizator.data.Production;
import kameib.localizator.handlers.ForgeConfigHandler;
import net.minecraft.client.gui.GuiBossOverlay;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiBossOverlay.class)
public abstract class BossOverlayMixin {
    /**
     * @author KameiB
     * @reason If Boss's "CustomName" tag contains a lang key, translate it at client rendering.
     * Should support lang keys surrounded by TextComponent formatting.
     * When connected to a server, if you create lang keys for all your server's bosses names,
     * Clients will see the boss name in their language.
     * Lang key example: 
     * Ender Dragon=Enderdragón
     */
    @Redirect(
            method = "renderBossHealth()V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/text/ITextComponent;getFormattedText()Ljava/lang/String;"),
            remap = Production.inProduction
    )
    // Line 43: String s = bossinfoclient.getName().getFormattedText();
    private String Minecraft_GuiBossOverlay_RenderBossHealth_getFormattedTextName(ITextComponent bossName) {
        StringBuilder stringbuilder = new StringBuilder();

        for (ITextComponent itextcomponent : bossName)
        {
            String s = itextcomponent.getUnformattedComponentText();
            if (!s.isEmpty())
            {
                stringbuilder.append(itextcomponent.getStyle().getFormattingCode());
                if (ForgeConfigHandler.clientConfig.minecraftBossLocCustomNames) {
                    stringbuilder.append(I18n.hasKey(s) ? I18n.format(s) : s);
                }
                else { // getFormattedText default behaviour
                    stringbuilder.append(s);
                }
                stringbuilder.append(TextFormatting.RESET);
            }
        }
        return stringbuilder.toString();
    }
}
