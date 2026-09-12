package kameib.localizator.mixin.forgottenitems;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import tschipp.forgottenitems.items.ItemWindTalisman;

@Mixin(ItemWindTalisman.class)
public abstract class ItemWindTalismanMixin {
    @ModifyConstant(
            method = "<init>",
            constant = @Constant(stringValue = "Fly with the wind"),
            remap = false
    )
    // Replace the hardcoded "Fly with the wind" with a lang key for later translation.
    // Line 20: super("wind_talisman", "Fly with the wind", 16, ItemList.windGem);
    private static String ForgottenItems_ItemWindTalisman_Constructor(String lore) {
        return "item.wind_talisman.lore";
    }
}
