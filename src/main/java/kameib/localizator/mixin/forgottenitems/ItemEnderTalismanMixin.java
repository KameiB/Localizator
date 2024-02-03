package kameib.localizator.mixin.forgottenitems;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import tschipp.forgottenitems.items.ItemEnderTalisman;

@Mixin(ItemEnderTalisman.class)
public abstract class ItemEnderTalismanMixin {
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
    // Line 28: super("ender_talisman", "Teleports you where you're looking", 18, ItemList.enderGem);
    private static String ForgottenItems_ItemEnderTalisman(String lore) {
        return "item.ender_talisman.lore";
    }    
}
