package kameib.localizator.mixin.forgottenitems;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import tschipp.forgottenitems.items.ItemWaterTalisman;

@Mixin(ItemWaterTalisman.class)
public abstract class ItemWaterTalismanMixin {
    @ModifyConstant(
            method = "<init>",
            constant = @Constant(stringValue = "Creates a Water Block"),
            remap = false
    )
    // Replace the hardcoded "Creates a Water Block" with a lang key for later translation.
    // Line 21: super("water_talisman", "Creates a Water Block", 14, ItemList.waterGem);
    private static String localizator_ForgottenItems_ItemWaterTalisman_Constructor(String lore) {
        return "item.water_talisman.lore";
    }
}
