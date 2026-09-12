package kameib.localizator.mixin.forgottenitems;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import tschipp.forgottenitems.items.ItemShockTalisman;

@Mixin(ItemShockTalisman.class)
public abstract class ItemShockTalismanMixin {
    @ModifyConstant(
            method = "<init>",
            constant = @Constant(stringValue = "Converts fall damage into an explosion"),
            remap = false
    )
    // Replace the hardcoded "Converts fall damage into an explosion" with a lang key. Don't translate it yet.
    // Line 12: super("shock_talisman", "Converts fall damage into an explosion", 6, ItemList.shockGem);
    private static String localizator_ForgottenItems_ItemShockTalisman_Constructor(String lore) {
        return "item.shock_talisman.lore";
    }
}
