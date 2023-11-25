package locentitynamemixin.mixin.vanilla;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {
    /**
     * @author KameiB
     * @reason If CustomName tag contains a lang key, translate it at rendering.
     */
    @ModifyVariable(
            method = "drawNameplate",
            at = @At("HEAD"),
            argsOnly = true,
            ordinal = 0
    )
    //public static void drawNameplate(FontRenderer fontRendererIn, String str, float x, float y, float z, int verticalShift, float viewerYaw, float viewerPitch, boolean isThirdPersonFrontal, boolean isSneaking)
    private static String locentitynamemixinEntityRender_CustomName(String str) {
        int lastIndex = str.lastIndexOf("§r");
        String langKey = lastIndex == (str.length() - 2) ? str.substring(0,lastIndex) : str;
        String translated = I18n.hasKey(langKey) ? I18n.format(langKey) : str;
        return translated;
    }
}
