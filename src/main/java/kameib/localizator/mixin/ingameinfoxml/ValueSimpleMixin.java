package kameib.localizator.mixin.ingameinfoxml;

import com.github.lunatrius.ingameinfo.value.Value;
import com.github.lunatrius.ingameinfo.value.ValueSimple;
import com.github.lunatrius.ingameinfo.value.registry.ValueRegistry;
import kameib.localizator.util.IGIValueLangKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ValueSimple.class)
public abstract class ValueSimpleMixin extends Value {
    @Inject(
            method = "register()V",
            at = @At("HEAD"),
            remap = false
    )
    // Register IGIValueLangKey
    // Line 80
    private static void IGI_ValueSimple_register_IGIValueLangKey(CallbackInfo ci) {
        ValueRegistry.INSTANCE.register(new IGIValueLangKey().setName("lang").setAliases("langKey"));
    }
}
