package locentitynamemixin.mixin.neat;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLiving;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vazkii.neat.HealthBarRenderer;
import locentitynamemixin.handlers.ForgeConfigHandler;

@Mixin(HealthBarRenderer.class)
public abstract class HealthBarRendererMixin {
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
    private String locentitynameNeatHealthBarRenderer_translatedCustomNameTag(EntityLiving entity) {
        String str = entity.getCustomNameTag();
        if (ForgeConfigHandler.clientConfig.neatLocHealthBar) {
            return I18n.hasKey(str) ? ("§r" + I18n.format(str)) : str;
        }
        else { // Default behaviour
            return str;
        }
    }
}
