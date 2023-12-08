package localizator.mixin.forgottenitems;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import tschipp.forgottenitems.items.ItemHeatTalisman;

import java.util.List;

@Mixin(ItemHeatTalisman.class)
public abstract class ItemHeatTalismanMixin {
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
    // Line 38: super("heat_talisman", "Stores extreme amounts of heat", 8, ItemList.fireGem);
    private static String localizator_ForgottenItems_ItemHeatTalisman_Constructor(String lore) {
        return "item.heat_talisman.lore";
    }
    
    
    @Redirect(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z")
    )
    @SideOnly(Side.CLIENT)
    // Line 72: tooltip.add(TextFormatting.RED + "It is not possible to charge the Talisman in Creative mode");
    private boolean localizator_ForgottenItems_ItemHeatTalisman_addInformation_tooltipAdd(List tooltip, Object e) {
        return tooltip.add(TextFormatting.RED + I18n.format("item.heat_talisman.desc"));
    }
}
