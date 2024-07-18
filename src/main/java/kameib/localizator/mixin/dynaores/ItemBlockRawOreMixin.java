package kameib.localizator.mixin.dynaores;

import kameib.localizator.data.Production;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.smileycorp.dynaores.common.item.ItemBlockRawOre;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemBlockRawOre.class)
public abstract class ItemBlockRawOreMixin extends ItemBlock {
    public ItemBlockRawOreMixin(Block block) {
        super(block);
    }

    @Inject(
            method = "getItemStackDisplayName(Lnet/minecraft/item/ItemStack;)Ljava/lang/String;",
            at = @At("HEAD"),
            cancellable = true,
            remap = Production.inProduction
    )
    @SuppressWarnings("deprecation")
    // Line 23.
    // If a custom lang key (for example: tile.dynaores.RawIronBlock.name) exists, use it.
    // Else, use the default system (which takes the Material Ingot lang key, strips the "Ingot" text from it and sends it to the items.dynaores.RawOreBlock.name lang key)
    public void DynaOres_ItemBlockRawOre_getItemStackDisplayName(ItemStack stack, CallbackInfoReturnable<String> cir) {
        if (I18n.canTranslate(stack.getTranslationKey() + ".name")) {
            cir.setReturnValue(super.getItemStackDisplayName(stack));
            cir.cancel();
        }
    }
}
