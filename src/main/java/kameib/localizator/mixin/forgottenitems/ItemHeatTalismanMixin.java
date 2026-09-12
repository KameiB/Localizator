package kameib.localizator.mixin.forgottenitems;

import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import tschipp.forgottenitems.items.ItemHeatTalisman;

@Mixin(ItemHeatTalisman.class)
public abstract class ItemHeatTalismanMixin {
    @ModifyConstant(
            method = "<init>",
            constant = @Constant(stringValue = "Stores extreme amounts of heat"),
            remap = false
    )
    // Replace the hardcoded "Stores extreme amounts of heat" with a lang key. Don't translate here.
    // Line 38: super("heat_talisman", "Stores extreme amounts of heat", 8, ItemList.fireGem);
    private static String localizator_ForgottenItems_ItemHeatTalisman_Constructor(String lore) {
        return "item.heat_talisman.lore";
    }    
    
    @ModifyConstant(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            constant = @Constant(stringValue = "It is not possible to charge the Talisman in Creative mode"),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Replace the hardcoded "It is not possible to charge the Talisman in Creative mode" with a translated lang key.
    // Line 72: tooltip.add(TextFormatting.RED + "It is not possible to charge the Talisman in Creative mode");
    private String localizator_ForgottenItems_ItemHeatTalisman_addInformation_tooltipAdd(String constant) {
        return I18n.format("item.heat_talisman.desc");
    }
}
