package kameib.localizator.mixin.charm;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import svenhjol.charm.base.integration.jei.ComposterCategory;

@Mixin(ComposterCategory.class)
public abstract class ComposterCategoryMixin {
    /**
     * @author KameiB
     * @reason Composter JEI recipe GUI title is hardcoded
     */
    @SideOnly(Side.CLIENT)
    @Inject(
            method = "getTitle()Ljava/lang/String;",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    // Line 32
    public void getTitle(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(I18n.format("charm.jei.recipe.composter"));
    }
}
