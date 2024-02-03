package kameib.localizator.mixin.forgottenitems;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import tschipp.forgottenitems.items.ItemWaterTalisman;

@Mixin(ItemWaterTalisman.class)
public abstract class ItemWaterTalismanMixin {
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
    // Line 21: super("water_talisman", "Creates a Water Block", 14, ItemList.waterGem);
    private static String ForgottenItems_ItemWaterTalisman_Constructor(String lore) {
        return "item.water_talisman.lore";
    }
    
}
