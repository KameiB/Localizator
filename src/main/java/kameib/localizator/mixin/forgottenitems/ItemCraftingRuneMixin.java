package kameib.localizator.mixin.forgottenitems;

import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import tschipp.forgottenitems.items.ItemCraftingRune;

@Mixin(ItemCraftingRune.class)
public abstract class ItemCraftingRuneMixin {
    @ModifyConstant(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            constant = @Constant(stringValue = "Output: "),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Replace the hardcoded "Output: " with a simple lang key.
    // I don't consider this text's order to be language sensitive.
    // Line 80: tooltip.add("Output: " + I18n.translateToLocal(output.getTranslationKey() + ".name"));
    // Line 87: tooltip.add("Output: " + I18n.translateToLocal(output.getTranslationKey() + ".name"));
    private String localizator_ForgottenItems_ItemCraftingRune_addInformation_output(String original) {
        return I18n.format("tooltip.forgottenitems.crafting_rune.output") + " ";
    }
    
    @ModifyConstant(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            constant = @Constant(stringValue = "Output: None/Error"),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Replace the hardcoded "Output: None/Error" with 2 lang keys combined.
    // Since the "Output: " text order is language insensitive, I decided this text also doesn't need more complexity 
    // Line 82: tooltip.add("Output: None/Error");
    // Line 89: tooltip.add("Output: None/Error");
    private String localizator_ForgottenItems_ItemCraftingRune_addInformation_outputNoneError(String original) {
        return I18n.format("tooltip.forgottenitems.crafting_rune.output") + " " + I18n.format("tooltip.forgottenitems.crafting_rune.output_none");
    }
}
