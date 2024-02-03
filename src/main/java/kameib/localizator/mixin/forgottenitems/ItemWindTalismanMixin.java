package kameib.localizator.mixin.forgottenitems;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import tschipp.forgottenitems.items.ItemWindTalisman;

@Mixin(ItemWindTalisman.class)
public abstract class ItemWindTalismanMixin {
    /**
     * @author KameiB
     * @reason Localize item lore at constructor
     */
    @ModifyArg(
            method = "<init>",
            at = @At(value = "INVOKE", target = "Ltschipp/forgottenitems/items/ItemTalisman;<init>(Ljava/lang/String;Ljava/lang/String;ILnet/minecraft/item/Item;)V"),
            index = 1,
            remap = false
    )
    // Line 20: super("wind_talisman", "Fly with the wind", 16, ItemList.windGem);
    private static String ForgottenItems_ItemWindTalisman_Constructor(String lore) {
        return "item.wind_talisman.lore";
    }
    
}
