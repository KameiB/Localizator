package kameib.localizator.mixin.dynaores;

import kameib.localizator.data.Production;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.smileycorp.dynaores.common.item.ItemRawOre;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemRawOre.class)
public abstract class ItemRawOreMixin extends Item {
    @Inject(
            method = "getItemStackDisplayName(Lnet/minecraft/item/ItemStack;)Ljava/lang/String;",
            at = @At("HEAD"),
            cancellable = true,
            remap = Production.inProduction
    )
    @SuppressWarnings("deprecation")
    // Line 27.
    // If a custom lang key (for example: item.dynaores.RawIron.name) exists, use it.
    // Else, use the default system (which takes the Material Ingot lang key, strips the "Ingot" text from it and sends it to the items.dynaores.RawOreBlock.name lang key)
    public void DynaOres_ItemRawOre_getItemStackDisplayName(ItemStack stack, CallbackInfoReturnable<String> cir) {
        if (I18n.canTranslate(stack.getTranslationKey() + ".name")) {
           cir.setReturnValue(super.getItemStackDisplayName(stack));
           cir.cancel();
        }
    }
}
