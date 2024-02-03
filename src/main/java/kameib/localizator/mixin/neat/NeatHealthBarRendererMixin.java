package kameib.localizator.mixin.neat;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.text.TextFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vazkii.neat.HealthBarRenderer;
import kameib.localizator.handlers.ForgeConfigHandler;

@Mixin(HealthBarRenderer.class)
public abstract class NeatHealthBarRendererMixin {
    /**
     * @author KameiB
     * @reason If Entity CustomName is a language key,
     * remove the hardcoded italic format and translate it.
     */
    @Redirect(
        method = "renderHealthBar(Lnet/minecraft/entity/EntityLivingBase;FLnet/minecraft/entity/Entity;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLiving;getCustomNameTag()Ljava/lang/String;")
    )
    // Line 220: name = TextFormatting.ITALIC + ((EntityLiving)entity).getCustomNameTag();
    private String localizatorNeatHealthBarRenderer_getCustomNameTag(EntityLiving entity) {
        String str = entity.getCustomNameTag();
        if (ForgeConfigHandler.clientConfig.neatLocHealthBar) {
            return I18n.hasKey(str) ? (TextFormatting.RESET + I18n.format(str)) : str;
        }
        else { // Default behaviour
            return str;
        }
    }
}
