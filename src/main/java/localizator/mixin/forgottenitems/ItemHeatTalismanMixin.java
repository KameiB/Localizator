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
    // it originally calls to super("ender_talisman", "Teleports you where you're looking", 18, ItemList.enderGem);
    private static String localizator_ForgottenItems_ItemEnderTalisman_Constructor(String lore) {
        return "item.heat_talisman.lore";
    }
    
    
    @Redirect(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z")
    )
    @SideOnly(Side.CLIENT)
    private boolean localizator_ForgottenItems_ItemEnderTalisman_addInformation_tooltipAdd(List tooltip, Object e) {
        return tooltip.add(TextFormatting.RED + I18n.format("item.heat_talisman.desc"));
    }
}
