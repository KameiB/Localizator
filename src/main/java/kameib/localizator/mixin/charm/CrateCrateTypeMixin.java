package kameib.localizator.mixin.charm;

import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import svenhjol.charm.crafting.feature.Crate;
import svenhjol.charm.crafting.feature.Crate.CrateType;

@Mixin(CrateType.class)
public abstract class CrateCrateTypeMixin {
    @Shadow(remap = false)
    public String name;

    @Inject(
            method = "<init>",
            at = @At(value = "RETURN", target = "Lsvenhjol/charm/crafting/feature/Crate$CrateType;<init>(Lsvenhjol/charm/crafting/feature/Crate;Ljava/lang/String;Lnet/minecraft/util/ResourceLocation;)V"),
            remap = false
    )
    // Use individual lang keys for each crate, instead of smooshing 2 lang keys in a hardcoded order.
    // Line 335: this.name = net.minecraft.util.text.translation.I18n.translateToLocal(id) + " " + net.minecraft.util.text.translation.I18n.translateToLocal("crate");
    private void Charm_CrateType_constructor(Crate this$0, String id, ResourceLocation pool, CallbackInfo ci) {
        name = "charm.crate_" + id + ".name";
    }
}
