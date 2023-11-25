package locentitynamemixin.mixin.vanilla;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiBossOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.lang.annotation.Target;

@Mixin(GuiBossOverlay.class)
public abstract class BossOverlayMixin {
    @Shadow @Final private Minecraft client;

    /**
     * @author KameiB
     * @reason If mob's CustomName contains a lang key, translate it at rendering.
     */
    @ModifyArgs(
            method = "renderBossHealth()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I",
                    ordinal = 0
            )
    )
    //this.client.fontRenderer.drawStringWithShadow(s, (float)(i / 2 - this.client.fontRenderer.getStringWidth(s) / 2), (float)(j - 9), 16777215);
    private void locentitynamemixinGuiBossOverlay_bossName(Args args) {
        String text = args.get(0);

        int lastIndex = text.lastIndexOf("§r");
        String langKey = lastIndex == (text.length() - 2) ? text.substring(0,lastIndex) : text;
        String translated = I18n.hasKey(langKey) ? I18n.format(langKey) : text;

        float x = (float)((new ScaledResolution(this.client).getScaledWidth()) / 2 - this.client.fontRenderer.getStringWidth(translated) / 2);
        float y = args.get(2);
        int color = args.get(3);

        args.set(0, translated);
        args.set(1, x);
        args.set(2, y);
        args.set(3, color);
    }
}
